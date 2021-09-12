package com.server;


import com.api.command.Exit;
import com.api.command.Save;
import com.api.command.manager.CommandManager;
import com.api.dao.CityDao;
import com.api.dao.UserDao;
import com.api.entity.User;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.message.MessageReq;
import com.api.message.MessageResp;
import com.api.print.implementation.FormatterImpl;
import com.api.print.implementation.PrinterImpl;
import com.api.service.CityService;
import com.api.service.UserService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final Messenger messenger = MessengerFactory.getMessenger();

    private ExecutorService requestExecutor;
    private ExecutorService responseExecutor;

    private ServerSocketChannel serverSocket;
    private Selector selector;

    private final ServerContext context;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public Server(ServerContext context) {
        this.context = context;
        try {
            init();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void init() throws IOException {
        // Получаем Selector
        selector = Selector.open();

        // Получаем ServerSocketChannel, задаем ему адрес, а также регистрируем полученный Selector
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        context.init();

        // Создаем три ExecutorService для принятия, обработки и послания запроса соответственно
        this.requestExecutor = Executors.newCachedThreadPool();
        this.responseExecutor = Executors.newCachedThreadPool();

        logger.info("Server initialized");

        // Запускаем новый поток, который слушает пользовательский ввод
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            String serverCommand;
            while (!(serverCommand = sc.nextLine()).equals("exit")) {
                if ("save".equals(serverCommand)) {
                    System.out.println(new Save(context.getData(), context.getCityService()).execute(null));
                } else {
                    System.out.println("Неизвестная команда: " + serverCommand);
                }
            }
            new Exit(context.getData(), context.getCityService()).execute(null);
        }).start();

    }

    public void start() throws IOException {

        System.out.println(messenger.getMessage("greeting_sever"));

        while (serverSocket.isOpen()) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }
                if (key.isReadable()) {
                    try {
                        getRequest(key);
                    } catch (Exception e) {
                        key.cancel();
                        System.err.println(e.getMessage());
                        key.channel().close();
                    }
                }
                iter.remove();
            }
        }
    }

    private void getRequest(SelectionKey key) throws Exception {
        // Получаем SocketChannel, через который мы будем обмениваться данными (через ByteBuffer) с конкретным клиентом
        SocketChannel client = (SocketChannel) key.channel();

        // Инициализируем ByteBuffer
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024 * 1024);
        requestBuffer.clear();

        // Обрабатываем входящий запрос с помощью cachedThreadPool
        // В качестве параметра методу submit передается не Runnable, а Callable объект
        // Callable может возвращать значение, в отличие от Runnable
        // Внутри Callable мы считываем данные от клиента и возвращаем количество считанных байтов
        Future<Integer> readFuture = requestExecutor.submit(() -> client.read(requestBuffer));

        // Получаем значение из Future, как только оно готово
        int read = readFuture.get();

        logger.info("New request from " + ((SocketChannel) key.channel()).socket().getRemoteSocketAddress());

        // Если полученное значение равно -1, то клиент по каким-то
        // причинам потерял связь (Просто отключился или возникла ошибка)
        if(read == -1) {
            throw new Exception("Соединение с одним из клиентов прервано: " +
                    ((SocketChannel) key.channel()).socket().getRemoteSocketAddress());
        }

        byte[] bytes = new byte[read];
        requestBuffer.position(0);
        requestBuffer.get(bytes);

        // Десериализуем Message и обрабатываем запрос
        MessageReq message = SerializationUtils.deserialize(bytes);
        processRequest(message, client);
    }

    private void processRequest(MessageReq message, SocketChannel client) {

        AtomicReference<MessageResp> response = new AtomicReference<>(new MessageResp());

        // Выполняем необходимую команду в новом потоке
        new Thread(() -> {
            switch (message.getCommand()) {
                case "login": response.set(login(client, message)); break;
                case "registration": response.set(registration(client, message)); break;
                default:
                    try {
                        response.set(context.getCommandManager().executeCommand(message));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }).start();

        // Отправляем результат в новом потоке с помощью cachedThreadPool
        responseExecutor.submit(() -> {
            try {
                sendResponse(client, response.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendResponse(SocketChannel client, MessageResp message) throws IOException {
        // Сериализуем Message и заворачиваем его в ByteBuffer
        // Отправляем результат клиенту
        ByteBuffer responseBuffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
        while (responseBuffer.hasRemaining()) {
            client.write(responseBuffer);
        }

        logger.info("Response sent to " + client.socket().getRemoteSocketAddress());

        responseBuffer.clear();
    }

    /**
     * Регистрирует новое подключение
     * @throws IOException - В случае IO Exception
     */
    private void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        // Принимаем клиента
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        // Регистрируем Selector для этого клиента
        client.register(selector, SelectionKey.OP_READ);
        logger.info("New connection at: " + client.socket().getRemoteSocketAddress());
    }

    public MessageResp login(SocketChannel client, MessageReq message) {
        writeLock.lock();
        try {
            MessageResp result = new MessageResp();
            result.setResult(ServerContext.FL);
            for (User u : context.getUsers()) {
                if (u.getName().equals(message.getUser().getName()) && u.getPassword().equals(message.getUser().getPassword())) {
                    result.setResult(ServerContext.SL);
                    u.setAddress(client.socket().getRemoteSocketAddress().toString());
                    break;
                }
            }
            return result;
        } finally {
            writeLock.unlock();
        }
    }

    public MessageResp registration(SocketChannel client, MessageReq message) {
        writeLock.lock();
        try {
            MessageResp result = new MessageResp();

            result.setResult(ServerContext.SR);
            for (User u : context.getUsers()) {
                if (u.getName().equals(message.getUser().getName())) {
                    result.setResult(ServerContext.FR);
                    u.setAddress(client.socket().getRemoteSocketAddress().toString());
                    break;
                }
            }
            if (result.getResult().equals(ServerContext.SR)) {
                context.getUserService().save(message.getUser());
                context.getUsers().add(message.getUser());
            }

            return result;

        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        // Т.к. мы не используем Spring контейнер, мы должны сами создавать все классы и внедрять их в бизнес-логику
        CityService cityService = new CityService(new CityDao());
        UserService userService = new UserService(new UserDao());

        new Server(ServerContext.builder()
                .data(cityService.findAll())
                .users(userService.findAll())
                .commandManager(new CommandManager())
                .formatter(new FormatterImpl())
                .printer(new PrinterImpl())
                .cityService(cityService)
                .userService(userService)
                .build()
        ).start();
    }

}