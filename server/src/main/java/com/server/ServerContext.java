package com.server;

import com.api.command.manager.CommandManager;
import com.api.entity.City;
import com.api.entity.User;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.print.api.Formatter;
import com.api.print.api.Printer;
import com.api.service.CityService;
import com.api.service.UserService;
import lombok.*;

import java.util.LinkedHashSet;


/**
 * Util класс, хранящий всю необходимую информацию о коллекции, сервисах и зарегистрированных пользователях
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class ServerContext {

    private static final Messenger messenger = MessengerFactory.getMessenger();

    // Константы, служащие флагами при регистрации/авторизации пользователя
    public static final String SL = "success_login";
    public static final String FL = "failure_login";
    public static final String SR = "success_registration";
    public static final String FR = "failure_registration";

    private LinkedHashSet<City> data;
    private LinkedHashSet<User> users;
    private CommandManager commandManager;
    private Formatter formatter;
    private Printer printer;
    private UserService userService;
    private CityService cityService;

    public void init() {
        commandManager.setFormatter(formatter);
        commandManager.setPrinter(printer);
        commandManager.setMDataSet(data);
        commandManager.setCityService(cityService);
    }

}
