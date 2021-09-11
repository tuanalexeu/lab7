package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.message.MessageReq;
import com.api.message.MessageReqObj;
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
    public LinkedHashSet<City> execute(MessageReq message) throws Exception {

        City city = ((MessageReqObj) message).getCity();

        Set<ConstraintViolation<City>> violations = getValidator().validate(city);

        if(violations.isEmpty()) {
            if(getCityList().stream().noneMatch(c -> city.compareTo(c) >= 0)) {
                if(getCityService().save(city, message.getUser()) != null) {
                    getCityList().add(city);
                    settleIds();
                }
            }
        }

        violations.forEach(v -> System.err.println(v.getMessage()));
        return getCityList();
    }
}
