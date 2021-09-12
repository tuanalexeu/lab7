package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Clear extends Command {

    public Clear(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message message) {
        List<City> result = getCityList().stream()
                .filter(c -> c.getUser_name().equals(message.getUser().getName()))
                .collect(Collectors.toList());

        // Удаляем все элементы, которые есть в result
        // В итоге, пользователь может удалить только те элементы, которые создал именно он.
        result.forEach(getCityList()::remove);
        return getFormatter().formatBooleanOperation(true);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoClear");
    }
}
