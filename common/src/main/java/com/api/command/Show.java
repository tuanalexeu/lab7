package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Show extends Command {

    public Show(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq ignore) {
        return (LinkedHashSet<City>) getCityList()
                        .stream()
                        .sorted((o1, o2) -> Comparator.comparing(City::getName).compare(o1, o2))
                        .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoShow");
    }
}
