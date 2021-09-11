package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;

import java.util.Collections;
import java.util.LinkedHashSet;

public class Shuffle extends Command {

    public Shuffle(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq ignore) {

        Collections.shuffle(getCityList());

        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoShuffle");
    }
}
