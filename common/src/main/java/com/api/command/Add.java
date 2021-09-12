package com.api.command;

import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.io.ConsoleUserInput;
import com.api.io.UserInput;
import com.api.message.Message;
import com.api.service.CityService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter @Setter
@AttachedObj
public class Add extends Command {

    private UserInput consoleUserInput;

    public Add(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);

        consoleUserInput = new ConsoleUserInput();

    }

    @Override
    public String execute(Message message) throws Exception {

        City city = message.getCity();

        Set<ConstraintViolation<City>> violations = getValidator().validate(city);

        if(violations.isEmpty()) {

            if(getCityService().save(city, message.getUser()) != null) {
                getCityList().add(city);
                settleIds();
            }
        }

        violations.forEach(v -> System.err.println(v.getMessage()));

        return getFormatter().formatBooleanOperation(true);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoAdd");
    }
}
