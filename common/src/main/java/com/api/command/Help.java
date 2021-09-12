package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.LinkedHashSet;
import java.util.Set;

public class Help extends Command {

    public Help(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message ignore) {

        Reflections reflections = new Reflections("com.api");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        StringBuilder result = new StringBuilder("");

        classes.forEach(c -> {

            try {
                Constructor<? extends Command> constructor = c.getConstructor(LinkedHashSet.class, CityService.class);
                Command command = constructor.newInstance(getCityList(), getCityService());
                result.append(command).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result.toString();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoHelp");
    }
}
