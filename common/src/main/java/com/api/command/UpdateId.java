package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.message.MessageReqObj;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter @Setter
@AttachedObj
public class UpdateId extends Command {

    public UpdateId(LinkedHashSet<City> dragonList, CityService cityService) {
        super(dragonList, cityService);
    }

    @Override
    public String execute(MessageReq message) {

        String id = getArg(message.getCommand());

        for (City d: getCityList()) {
            if(d.getId().equals(Integer.parseInt(id))) {
                try {
                    City city = ((MessageReqObj) message).getCity();
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
