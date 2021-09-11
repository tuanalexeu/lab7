package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.message.MessageReqObj;
import com.api.service.CityService;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class CountGreaterThanMetersAboveSeaLevel extends Command {

    public CountGreaterThanMetersAboveSeaLevel(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) throws Exception {

        City city = ((MessageReqObj) message).getCity();

        return (LinkedHashSet<City>) getCityList().stream()
                .filter(c -> Double.compare(c.getMetersAboveSeaLevel(), city.getMetersAboveSeaLevel()) > 0)
                .collect(Collectors.toSet());
    }
}
