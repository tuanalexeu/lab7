package com.client;

import com.api.command.Command;
import com.api.command.annotation.AttachedObj;
import com.api.command.annotation.AttachedObjFactory;
import com.api.entity.City;
import com.api.entity.User;
import com.api.message.MessageReq;
import com.api.message.MessageReqObj;
import com.api.message.MessageResp;
import com.api.print.api.Printer;
import com.api.print.implementation.PrinterImpl;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Класс хранит логику клиента приложения.
 * Реализует методы аутентификации клиента, а также послания запросов и принятия ответов от сервера.
 */
public class Client {

    private final Printer printer = new PrinterImpl();
    private SocketChannel server;

    public void start() throws Exception {

        User user = auth();

        System.out.println("Приветствие");

        Scanner sc = new Scanner(System.in);
        String response;
        while (!(response = sc.nextLine()).equals("exit")) {
            try {
                // Вызываем команду и выводим результат
                // В случае, если метод execute_command бросил исключение,
                // оно обрабатывается, а программа продолжает работу.
                if (response.equals("")) {
                    System.out.println("Пожалуйста, введите корректные данные");
                } else {
                    String[] array = response.split(" ");
                    String commandName = array[0];

                    MessageReq message = prepareRequest(
                            response,
                            validateAnnotation(Command.validateCommand(commandName), user)
                    );
                    message.setUser(user);
                    MessageResp result = sendRequest(message);

                    printer.printResult((String) result.getResult());
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        stop("Программа завершилась успешно");
    }

    private City validateAnnotation(Class<? extends Command> c, User user) throws Exception {

        if(c.isAnnotationPresent(AttachedObj.class)) {
            AttachedObj attachedObj = c.getAnnotation(AttachedObj.class);

            return AttachedObjFactory.newInstance(attachedObj.type(), user);
        }

        return null;
    }

    public Client() {
        try {
            open();
        } catch (Exception e) {
            stop("Server isn't available");
        }
    }

    public void open() throws Exception {
        try {
            server = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            server.configureBlocking(true);

        } catch (IOException e) {
            throw new Exception("Сервер недоступен");
        }
    }

    public User auth() throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Перед использованием необходима авторизация.\n" +
                    "1 - Войти в существующий аккаунт\n" +
                    "2 - Зарегистрировать новый аккаунт\n");

            // Вводим ответ
            String response = sc.nextLine();
            int userInput;
            try {
                userInput = Integer.parseInt(response);
            } catch (Exception e) {
                userInput = -1;
            }

            // Исходя из пользовательского выбора, запускаем либо авторизацию, либо регистрацию
            String result;
            User user;
            switch (userInput) {
                case 1: result = signIn(user = enterUser()); break;
                case 2: result = signUp(user = enterUser()); break;
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, введите число еще раз");
                    continue;
            }

            switch (result) {
                case "success_login":
                    System.out.println("Вход выполнен успешно");
                    return user;
                case "user_active":
                    System.err.println("Этот пользователь уже активен. Вы не можете зайти в этот аккаунт");
                    break;
                case "failure_login":
                    System.err.println("Неверные логин или пароль. Повторите попытку");
                    break;
                case "success_registration":
                    System.out.println("Пользователь успешно зарегистрирован");
                    return user;
                case "failure_registration":
                    System.err.println("Пользователь с таким именем уже существует");
                    break;
            }
        }
    }

    public User enterUser() throws Exception {
        if(server.isConnected()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите логин:");
            String login = sc.nextLine();

            System.out.println("Введите пароль:");
            String password = sc.nextLine();

            return new User(login, password);
        }
        throw new Exception("Непредвиденная ошибка");
    }

    // Вход
    public String signIn(User user) {
        MessageResp message = sendRequest(new MessageReq(user, "login"));
        return (String) message.getResult();
    }

    // Регистрация
    public String signUp(User user) {
        MessageResp message = sendRequest(new MessageReq(user,"registration"));
        return (String) message.getResult();
    }

    public MessageResp sendRequest(MessageReq message) {
        // Сериализуем Message и обертываем его в ByteBuffer
        ByteBuffer requestBuffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
        try {
            // Посылаем запрос серверу
            server.write(requestBuffer);
            requestBuffer.clear();

            // Получаем ответ
            return getResponse();
        } catch (Exception e) {
            stop(e.getMessage());
        }
        return null;
    }

    public MessageResp getResponse() throws Exception {
        // Инициализируем ByteBuffer
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024 * 1024);
        responseBuffer.clear();

        // Считываем информацию в ByteBuffer и получаем количество считанных байтов
        int read = server.read(responseBuffer);

        // Если результат -1, бросаем исключение
        if(read == -1) { throw new Exception("Связь прервалась"); }

        // Считываем ByteBuffer в массив байтов
        byte[] bytes = new byte[read];
        responseBuffer.position(0);
        responseBuffer.get(bytes);

        // Десериализуем Message, возвращаем результат
        return SerializationUtils.deserialize(bytes);
    }

    public MessageReq prepareRequest(String command, City city) {
        return new MessageReqObj(command, city);
    }


    public void stop(String message) {
        System.out.println(message);
        System.exit(0);
    }

}