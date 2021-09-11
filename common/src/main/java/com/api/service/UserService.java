package com.api.service;

import com.api.dao.UserDao;
import com.api.entity.User;
import lombok.AllArgsConstructor;

import java.util.LinkedHashSet;

@AllArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User save(User user) {
        return userDao.save(user);
    }

    public void saveAll(LinkedHashSet<User> entities) {
        userDao.saveAll(entities);
    }

    public LinkedHashSet<User> findAll() {
        return userDao.findAll();
    }

    public User findById(String name) {
        return userDao.findById(name);
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public boolean delete(User user) {
        return userDao.delete(user);
    }

    public boolean deleteById(String name) {
        return userDao.deleteById(name);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }
}
