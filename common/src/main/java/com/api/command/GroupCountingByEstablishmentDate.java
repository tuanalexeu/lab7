package com.api.command;

import com.api.entity.City;
import com.api.message.Message;
import com.api.service.CityService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCountingByEstablishmentDate extends Command{

    public GroupCountingByEstablishmentDate(LinkedHashSet<City> cityList, CityService cityService) {
        super(cityList, cityService);
    }

    @Override
    public Map<String, List<City>> execute(Message message) throws Exception {
        return getCityList().stream()
                .collect(Collectors.groupingBy(City::getEstablishmentDate));
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getMessenger().getMessage("infoGroupCountingByEstablishmentDate");
    }
}
