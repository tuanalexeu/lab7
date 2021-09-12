package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter @Setter
@AttachedObj
public class UpdateId extends Command {

    public UpdateId(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message message) {

        String id = getArg(message.getCommand());
        City result = getCityList().stream()
                .filter(c -> c.getId().equals(Integer.parseInt(id)))
                .findFirst()
                .orElse(null);

        if(result != null) {
            getCityService().update(result, message.getUser());
            getCityList().add(result);
            return getFormatter().formatBooleanOperation(true);
        }

        return getFormatter().formatBooleanOperation(false);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoUpdateId");
    }
}
