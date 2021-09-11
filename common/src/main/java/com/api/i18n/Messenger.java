package com.api.i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

@AllArgsConstructor
@Getter @Setter
public class Messenger {

    private ResourceBundle bundle;

    public String getMessage(String msg) {
        return bundle.getString(msg);
    }
}
