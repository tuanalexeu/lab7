package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter @Setter
@AttachedObj
public class UpdateId extends Command {

    public UpdateId(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public String execute(Message message) {

        String id = getArg(message.getCommand());

        for (City d: getCityList()) {
            if(d.getId().equals(Integer.parseInt(id))) {
                try {
                    City city = message.getCity();
                    Set<ConstraintViolation<City>> violations = getValidator().validate(city);

                    if(violations.isEmpty() && getCityService().update(city, message.getUser())) {
                        d = city;
                        return getFormatter().formatBooleanOperation(true);
                    }
                    violations.forEach(v -> System.err.println(v.getMessage()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return getFormatter().formatBooleanOperation(false);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoUpdateId");
    }
}
