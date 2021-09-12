package com.api.message;

import com.api.entity.City;
import com.api.entity.User;

import java.io.Serializable;

public class Message implements Serializable {

    private User user;
    private String command;
    private City city;
    private String result;

    public Message() {
        // Needed for serialization
    }

    // Response
    public Message(String result) {
        this(null, null, null, result);
    }

    // Request
    public Message(User user, String command) {
        this(user, command, null, null);
    }

    // Request
    public Message(User user, String command, City city) {
        this(user, command, city, null);
    }

    // Common
    public Message(User user, String command, City city, String result) {
        this.user = user;
        this.command = command;
        this.city = city;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
