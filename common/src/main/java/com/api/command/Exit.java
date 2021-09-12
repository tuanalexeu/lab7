package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;

public class Exit extends Command {

    public Exit(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message ignore) {

        // Сохраняем коллекция перед выходом
        new Save(getCityList(), getCityService()).execute(null);

        System.exit(0);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoExit");
    }
}
