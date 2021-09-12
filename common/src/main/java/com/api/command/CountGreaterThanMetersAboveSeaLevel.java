package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class CountGreaterThanMetersAboveSeaLevel extends Command {

    public CountGreaterThanMetersAboveSeaLevel(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(Message message) throws Exception {

        City city = message.getCity();

        return (LinkedHashSet<City>) getCityList().stream()
                .filter(c -> Double.compare(c.getMetersAboveSeaLevel(), city.getMetersAboveSeaLevel()) > 0)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoCountGreaterThanMetersAboveSeaLevel");
    }
}
