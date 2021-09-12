package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
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
    public LinkedHashSet<City> execute(Message message) {

        getCityList()
                .stream()
                .filter(c -> c.getUser_name().equals(message.getUser().getName()))
                .filter(c -> c.getId().equals(Integer.parseInt(message.getCommand().split(" ")[1])))
                .findFirst()
                .ifPresent(c -> getCityService().delete(c, message.getUser()));

        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoRemoveById");
    }
}
