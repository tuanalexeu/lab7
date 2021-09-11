package com.api.command;

import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter @Setter
public class RemoveAtIndex extends Command {

    public RemoveAtIndex(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) {

        int index = Integer.parseInt(getArg(message.getCommand()));

        for (int i = 0; i < getCityList().size(); i++) {
            if(index == i && getCityService().delete(getCityList().get(i), message.getUser())) {
                getCityList().remove(getCityList().get(i));
            }
        }

        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoRemoveAtIndex");
    }
}
