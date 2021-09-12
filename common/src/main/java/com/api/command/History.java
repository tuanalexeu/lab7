package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;

public class History extends Command {

    public History(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message message) throws Exception {
        return history();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoHistory");
    }
}
