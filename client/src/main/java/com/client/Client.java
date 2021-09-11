package com.client;

import com.api.entity.City;
import com.api.entity.User;
import com.api.message.MessageReq;
import com.api.message.MessageReqObj;
import com.api.message.MessageResp;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Класс хранит логику клиента приложения.
 * Реализует методы аутентификации клиента, а также послания запросов и принятия ответов от сервера.
 */
public class Client {

    private SocketChannel server;

    /**
     * Текущий авторизованный пользователь
     */
    @Getter(AccessLevel.PUBLIC)
    private User user;

    public Client() {
        try {
            open();
        } catch (Exception e) {
            stop("Server isn't available");
        }
    }

    public void start() {
        // TODO while loop
    }

    /**
     * Метод открывает соединение с сервером через SocketChannel
     * @throws Exception - В случае, если сервер недоступен
     */
    public void open() throws Exception {
        try {
            server = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            server.configureBlocking(true);

        } catch (IOException e) {
            throw new Exception("Сервер недоступен");
        }
    }

    /**
     * Метод посылает запрос серверу с просьбой войти в уже существующий аккаунт
     * @param user - объект, хранящий пароль и логин от аккаунта
     */
    public void signIn(User user) {

        MessageResp response = sendRequest(new MessageReq(user,"login"));

        if(!response.getResult().equals("success_login")) {
            throw new RuntimeException();
        }

        this.user = user;
    }

    /**
     * Метод посылает запрос серверу с просьбой зарегистрировать новый аккаунт
     * @param user - объект, хранящий пароль и логин от аккаунта
     */
    public void signUp(User user) {

        MessageResp response = sendRequest(new MessageReq(user,"registration"));

        if(!response.getResult().equals("success_registration")) {
            throw new RuntimeException();
        }

        this.user = user;
    }

    /**
     * Метод отправляет запрос на сервер в виде объекта Message
     * @param message - DTO объект
     * @return - объект, хранящий результат выполнения запроса
     */
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

    /**
     * Метод принимает ответ от сервера в виде объекта Message
     * @return - объект, хранящий результат выполнения запроса
     * @throws Exception - В случае внутренней ошибки сервера
     */
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

    /**
     * Метод завершает работу клиентского приложения
     * @param message - Строка, выводимая в консоль после завершения.
     */
    public void stop(String message) {
        System.out.println(message);
        System.exit(0);
    }

}