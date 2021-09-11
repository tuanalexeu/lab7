package com.api.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessengerFactory {

    private static final Messenger instance = getMessenger();

    public static Messenger getMessenger() {
        if(instance == null) {
            return new Messenger(
                    ResourceBundle.getBundle("com.api.i18n.bundle.Language", new Locale("ru", "RU"))
            );
        }
        return instance;
    }

    public static void changeLanguage(ResourceBundle rb) {
        if(instance == null) throw new RuntimeException("Language haven't been initialized yet");
        instance.setBundle(rb);
    }
}
