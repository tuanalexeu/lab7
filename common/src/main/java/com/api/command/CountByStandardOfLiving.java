package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class CountByStandardOfLiving extends Command {

    public CountByStandardOfLiving(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(Message message) throws Exception {

        City city = message.getCity();

        return (LinkedHashSet<City>) getCityList().stream()
                .filter(c -> c.getStandardOfLiving() == city.getStandardOfLiving())
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoCountStandardOfLiving");
    }
}
