package com.api.message;

import com.api.entity.City;
import com.api.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageReqObj extends MessageReq {

    private City city;

    public MessageReqObj(User user, String command, City city) {
        super(user, command);
        this.city = city;
    }
}
