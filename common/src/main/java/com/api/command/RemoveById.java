package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter @Setter
public class RemoveById extends Command {

    public RemoveById(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) {

        getCityList()
                .stream()
                .filter(d -> d.getId().equals(Integer.parseInt(message.getCommand().split(" ")[1])))
                .findFirst()
                .ifPresent(dragon -> getCityService().delete(dragon, message.getUser()));

        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoRemoveById");
    }
}
