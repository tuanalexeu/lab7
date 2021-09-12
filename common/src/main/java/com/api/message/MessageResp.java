package com.api.message;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MessageResp implements Serializable {
    private Object result;
}
