package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.io.UserInput;
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
public class InsertAtIndex extends Command {

    private UserInput consoleUserInput;

    public InsertAtIndex(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) throws Exception {

        String index = getArg(message.getCommand());

        City city = ((MessageReqObj) message).getCity();

        Set<ConstraintViolation<City>> violations = getValidator().validate(city);

        if(violations.isEmpty()) {

            City city1 = getCityList().set(Integer.parseInt(index), city);
            getCityService().update(city1, message.getUser());
        }

        violations.forEach(v -> System.err.println(v.getMessage()));
        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoInsertAtIndex");
    }
}
