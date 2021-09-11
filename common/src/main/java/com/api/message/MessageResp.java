package com.api.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageResp implements Serializable {

    private final long version = 1L;

    private Object result;
}
