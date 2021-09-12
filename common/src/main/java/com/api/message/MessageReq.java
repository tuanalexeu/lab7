package com.api.message;

import com.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageReq implements Serializable {
    private final User user;
    private final String command;
}
