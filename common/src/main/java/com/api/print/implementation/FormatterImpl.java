package com.api.print.implementation;

import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.print.api.Formatter;

import java.util.Collection;

public class FormatterImpl implements Formatter {

    private static final Messenger messenger = MessengerFactory.getMessenger();

    /**
     * Метод, который выводит коллекцию в более красивом виде
     */
    public String formatCollection(Collection<?> collection) {
        return collection
                .toString()
                .substring(1, collection.toString().length()-1)
                .replaceAll(", ", "\n");
    }

    public String formatBooleanOperation(boolean bool) {
        if(bool) {
            return messenger.getMessage("booleanOpTrue");
        } else {
            return messenger.getMessage("booleanOpFalse");
        }
    }
}
