package com.api.service;

import com.api.dao.CityDao;
import com.api.entity.City;
import com.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

@AllArgsConstructor
@Getter @Setter
public class CityService {

    private CityDao dao;

    public City save(City city, User user) {

        if(user.getName().equals(city.getUser_name())) {
            return dao.save(city);
        }

        return null;
    }

    public void saveAll(LinkedHashSet<City> entities) {
        dao.saveAll(entities);
    }

    public LinkedHashSet<City> findAll() {
        return dao.findAll();
    }

    public City findById(Integer id) {
        return dao.findById(id);
    }

    public boolean update(City city, User user) {
        if(user.getName().equals(city.getUser_name())) {
            return dao.update(city);
        }
        return false;
    }

    public boolean delete(City city, User user) {
        if(user.getName().equals(city.getUser_name())) {
            return dao.delete(city);
        }
        return false;
    }

    public boolean deleteById(Integer id, User user) {
        City city = findById(id);

        if(user.getName().equals(city.getUser_name())) {
            return dao.deleteById(id);
        }
        return false;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

}
