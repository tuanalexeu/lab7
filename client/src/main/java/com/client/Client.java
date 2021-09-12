package com.client;

import com.api.command.Command;
import com.api.command.manager.CommandManager;
import com.api.entity.City;
import com.api.entity.User;
import com.api.message.Message;
import com.api.print.api.Printer;
import com.api.print.implementation.PrinterImpl;
import lombok.SneakyThrows;
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

    private Printer printer;
    private SocketChannel server;

    public Client() {
        try {
            open();
        } catch (Exception e) {
            stop("Server isn't available");
        }
    }

    private void open() throws Exception {
        try {
            server = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            server.configureBlocking(true);
            printer = new PrinterImpl();
        } catch (IOException e) {
            throw new Exception("Сервер недоступен");
        }
    }

    @SneakyThrows
    public void start() {

        User user = auth();

        Scanner sc = new Scanner(System.in);
        String userRequest;
        while (!(userRequest = sc.nextLine()).equals("exit")) {
            try {
                if (userRequest.equals("")) {
                    System.out.println("Пожалуйста, введите корректные данные");
                } else {
                    Message message = prepareRequest(userRequest, user);  // Подготовка запроса
                    Message result = sendRequest(message);                // Отправка и получение ответа
                    printer.printResult(result.getResult());              // Вывод
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        stop("Программа завершилась успешно");
    }

    private User auth() throws Exception {

        Scanner sc = new Scanner(System.in);
        String userResponse;

        do {

            System.out.println("Перед использованием необходима авторизация.\n" +
                    "1 - Войти в существующий аккаунт\n" +
                    "2 - Зарегистрировать новый аккаунт\n" +
                    "0 - Для выхода\n");

            userResponse = sc.nextLine();
            int userInput;
            try {
                userInput = Integer.parseInt(userResponse);
            } catch (Exception e) {
                userInput = -1;
            }

            // Исходя из пользовательского выбора, запускаем либо авторизацию, либо регистрацию
            String authResult;
            User user;
            switch (userInput) {
                case 0:
                    System.out.println("Выход...");
                    continue;
                case 1: authResult = signIn(user = enterUser()); break;
                case 2: authResult = signUp(user = enterUser()); break;
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, введите число еще раз");
                    continue;
            }

            switch (authResult) {
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
        } while (!userResponse.equals("0"));

        throw new RuntimeException("User finished program");
    }

    private User enterUser() throws Exception {
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
    private String signIn(User user) {
        Message message = sendRequest(new Message(user, "login"));
        return message.getResult();
    }

    // Регистрация
    private String signUp(User user) {
        Message message = sendRequest(new Message(user,"registration"));
        return message.getResult();
    }

    /**
     * С помощью этого метода отправляем запрос серверу в виде объекта Message (в виде массива байтов)
     * @param message - Объект, который необходимо отправить
     * @return - объект, который отправил нам сервер
     */
    public Message sendRequest(Message message) {
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

    /**
     * С помощью этого сервера получаем ответ от сервера в виде объекта Message
     * @return - ответ сервера
     * @throws Exception - в случае ошибок получения результата (Сервер отключился или передал некорректный результат)
     */
    public Message getResponse() throws Exception {
        // Инициализируем ByteBuffer
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024 * 1024);
        responseBuffer.clear();

        // Считываем информацию в ByteBuffer и получаем количество считанных байтов
        int read = server.read(responseBuffer);
        if(read == -1) { throw new Exception("Связь прервалась"); }

        // Считываем ByteBuffer в массив байтов
        byte[] bytes = new byte[read];
        responseBuffer.position(0);
        responseBuffer.get(bytes);

        // Десериализуем Message, возвращаем результат
        return SerializationUtils.deserialize(bytes);
    }

    public Message prepareRequest(String request, User user) throws Exception {
        String commandName = request.split(" ")[0];
        City attachedObj = CommandManager.validateAnnotation(Command.validateCommand(commandName), user);
        return new Message(user, request, attachedObj);
    }

    /**
     * С помощью этого метода мы закрываем подключение по сокету
     * @param message - причина остановки клиента
     */
    public void stop(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public static void main(String[] args) {
        new Client().start();
    }
}