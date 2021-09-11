package com.server;

import com.api.command.manager.CommandManager;
import com.api.dao.CityDao;
import com.api.dao.UserDao;
import com.api.print.implementation.FormatterImpl;
import com.api.print.implementation.PrinterImpl;
import com.api.service.CityService;
import com.api.service.UserService;

public class Application {
    public static void main(String[] args) throws Exception {
        // Т.к. мы не используем Spring контейнер, мы должны сами создавать все классы и внедрять их в бизнес-логику
        CityService cityService = new CityService(new CityDao());
        UserService userService = new UserService(new UserDao());

        new Server(ServerContext.builder()
                .data(cityService.findAll())
                .commandManager(new CommandManager())
                .formatter(new FormatterImpl())
                .printer(new PrinterImpl())
                .cityService(cityService)
                .userService(userService)
                .build()
        ).start();
    }
}
