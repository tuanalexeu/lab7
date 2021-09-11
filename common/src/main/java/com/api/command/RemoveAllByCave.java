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
@AttachedObj(type = DragonCave.class)
public class RemoveAllByCave extends Command {

    private UserInput userInput;

    public RemoveAllByCave(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public LinkedHashSet<City> execute(MessageReq message) throws Exception {

        DragonCave dragonCave = ((MessageReqObj) message).getCity().getCave();

        Set<ConstraintViolation<DragonCave>> violations = getValidator().validate(dragonCave);

        if(violations.isEmpty()) {

            for (int i = 0; i < getCityList().size(); i++) {
                if(getCityList().get(i).getCave().equals(dragonCave)
                        && getCityService().delete(getCityList().get(i), message.getUser())) {
                    getCityList().remove(getCityList().get(i));
                }
            }
        }

        violations.forEach(v -> System.err.println(v.getMessage()));
        return getCityList();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoRemoveAllByCave");
    }
}
