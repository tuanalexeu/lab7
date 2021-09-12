package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.Set;

@AttachedObj
public class AddIfMin extends Command {

    public AddIfMin(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message message) throws Exception {

        City city = message.getCity();

        Set<ConstraintViolation<City>> violations = getValidator().validate(city);

        if(violations.isEmpty()) {
            if(getCityList().stream().noneMatch(c -> city.compareTo(c) >= 0)) {
                if(getCityService().save(city, message.getUser()) != null) {
                    getCityList().add(city);
                    settleIds();
                    return getFormatter().formatBooleanOperation(true);
                }
            }
        }

        violations.forEach(v -> System.err.println(v.getMessage()));
        return getFormatter().formatBooleanOperation(false);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoAddIfMin");
    }
}
