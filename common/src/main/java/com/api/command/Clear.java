package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;

import java.util.LinkedHashSet;

public class Clear extends Command {

    public Clear(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq ignore) {
        getCityList().clear();
        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoClear");
    }
}
