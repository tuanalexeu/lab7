package com.api.message;

import com.api.entity.City;
import com.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private User user;
    private String command;
    private City city;
    private String result;

    public Message(String result) {
        this.result = result;
    }

    public Message(User user, String command) {
        this.user = user;
        this.command = command;
    }

    public Message(User user, String command, City city) {
        this.user = user;
        this.command = command;
        this.city = city;
    }
}
