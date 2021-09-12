package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Formatter;
import java.util.LinkedHashSet;

@Getter @Setter
public class Info extends Command {

    public Info(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message ignore) {
        String elementType = null;

        try {
            Field hashSet = this.getClass().getSuperclass().getDeclaredField("cityList");
            elementType = String.valueOf(hashSet.getGenericType());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return new Formatter()
                .format(getMessenger().getMessage("type") + ": %s\n" +
                                getMessenger().getMessage("size") + ": %d\n",
                        elementType, getCityList().toArray().length)
                .toString();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoInfo");
    }
}
