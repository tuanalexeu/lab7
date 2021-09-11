package com.api.command.annotation;

import com.api.entity.City;
import com.api.entity.User;
import com.api.io.ConsoleUserInput;
import com.api.io.UserInput;

import java.rmi.NoSuchObjectException;

public class AttachedObjFactory {

    private static final UserInput input = new ConsoleUserInput();

    public static City newInstance(Class<?> c, User user) throws Exception {
        if(c.getSimpleName().equals("City")) {
            return input.enterElement(user);
        }
        throw new NoSuchObjectException("Введенные данные некорректны");
    }
}
