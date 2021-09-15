package com.server;


import com.api.command.Exit;
import com.api.command.Save;
import com.api.command.manager.CommandManager;
import com.api.dao.CityDao;
import com.api.dao.UserDao;
import com.api.entity.User;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.message.Message;
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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final Messenger messenger = MessengerFactory.getMessenger();

    private ForkJoinPool forkJoinPool;

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

        // Для чтения запроса
        this.forkJoinPool = new ForkJoinPool(10);

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
                        // Считываем request в новом потоке forkAndJoin
                        forkJoinPool.execute(() -> {
                            try {
                                getRequest(key);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
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

        int read = client.read(requestBuffer);


        logger.info("New request from " + ((SocketChannel) key.channel()).socket().getRemoteSocketAddress());

        // Если полученное значение равно -1, то клиент по каким-то
        // причинам потерял связь (Просто отключился или возникла ошибка)
        if(read == -1) {
            throw new Exception("Соединение с одним из клиентов прервано: " +
                    ((SocketChannel) key.channel()).socket().getRemoteSocketAddress());
        }

        AtomicReference<Message> message = null;

        // Обрабатываем запрос в новом потоке
        new Thread(() -> {
            byte[] bytes = new byte[read];
            requestBuffer.position(0);
            requestBuffer.get(bytes);

            // Десериализуем Message и, исходя из команды, запускаем
            // авторизацию, регистрацию, либо обычное выполнение команды
            message.set(SerializationUtils.deserialize(bytes));
            processRequest(client, message.get());
        });

        // Отправляем ответ в новом потоке
        new Thread(() -> {
            try {
                sendResponse(client, message.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void processRequest(SocketChannel client, Message message) {
        switch (message.getCommand()) {
            case "login": login(client, message); break;
            case "registration": registration(client, message); break;
            default:
                try {
                    context.getCommandManager().executeCommand(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Метод отправляющий результат работы нужному клиенту
     * @param client - цель
     * @param message - объект, хранящий результат работы
     * @throws IOException - в случае ошибок отправки ответа
     */
    private void sendResponse(SocketChannel client, Message message) throws IOException {
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

    public void login(SocketChannel client, Message message) {
        writeLock.lock();
        try {
            message.setResult(ServerContext.FL);
            for (User u : context.getUsers()) {
                if (u.getName().equals(message.getUser().getName()) && u.getPassword().equals(message.getUser().getPassword())) {
                    message.setResult(ServerContext.SL);
                    u.setAddress(client.socket().getRemoteSocketAddress().toString());
                    break;
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void registration(SocketChannel client, Message message) {
        writeLock.lock();
        try {
            message.setResult(ServerContext.SR);
            for (User u : context.getUsers()) {
                if (u.getName().equals(message.getUser().getName())) {
                    message.setResult(ServerContext.FR);
                    u.setAddress(client.socket().getRemoteSocketAddress().toString());
                    break;
                }
            }
            if (message.getResult().equals(ServerContext.SR)) {
                context.getUserService().save(message.getUser());
                context.getUsers().add(message.getUser());
            }
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