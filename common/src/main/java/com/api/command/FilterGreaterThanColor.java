package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class FilterGreaterThanColor extends Command {

    public FilterGreaterThanColor(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) {

        String color = getArg(message.getCommand());

        List<City> resultList = getCityList()
                .stream()
                .filter(d -> d.getColor() != null && d.getColor().compareTo(Color.valueOf(color)) > 0)
                .collect(Collectors.toList());

        return (LinkedHashSet<City>) resultList;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoFilterGreaterThanColor");
    }
}
