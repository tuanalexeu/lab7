package com.api.dao;

import com.api.entity.City;
import com.api.entity.Coordinates;
import com.api.entity.Human;
import com.api.entity.StandardOfLiving;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class CityDao implements GenericDao<City, Integer> {

    private JdbcConfig jdbc;

    public CityDao() {
        try {
            jdbc = new JdbcConfig();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public City save(City city) {
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement(
                    "INSERT INTO public.city_lab (" +
                            "name, " +
                            "x_coordinate, " +
                            "y_coordinate, " +
                            "creation_date, " +
                            "area, " +
                            "population, " +
                            "meters_above_sea_level, " +
                            "establishment_date, " +
                            "agglomeration, " +
                            "standard_of_living," +
                            "governor_height," +
                            "user_name)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            convertToStatement(city, statement);
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0) {
                return city;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAll(LinkedHashSet<City> entities) {
        entities.forEach(this::save);
    }

    @Override
    public LinkedHashSet<City> findAll() {

        LinkedHashSet<City> cities = new LinkedHashSet<>();
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement("SELECT * FROM public.city_lab");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cities.add(convertToCity(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cities;
    }

    @Override
    public City findById(Integer id) {
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement(
                    "SELECT * FROM public.city_lab WHERE id=?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return convertToCity(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(City city) {

        try {

            PreparedStatement statement = jdbc.getConnection().prepareStatement(
                    "UPDATE public.city_lab SET " +
                            "name=?, " +
                            "x_coordinate=?, " +
                            "y_coordinate=?, " +
                            "creation_date=?, " +
                            "area=?, " +
                            "population=?, " +
                            "meters_above_sea_level=?, " +
                            "establishment_date=?, " +
                            "agglomeration=?, " +
                            "standard_of_living=?, " +
                            "governor_height=?, " +
                            "user_name=?" +
                            "where id=?"
            );

            convertToStatement(city, statement);

            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(City city) {
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement(
                    "DELETE FROM public.city_lab WHERE id=?"
            );
            statement.setLong(1, city.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement(
                    "DELETE FROM public.city_lab WHERE id=?"
            );
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteAll() {
        try {
            PreparedStatement statement = jdbc.getConnection().prepareStatement("TRUNCATE public.city_lab");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private City convertToCity(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getInt(1));
        city.setName(resultSet.getString(2));
        city.setCoordinates(new Coordinates(resultSet.getFloat(3), resultSet.getDouble(4)));
        city.setCreationDate(resultSet.getString(5));
        city.setArea(resultSet.getDouble(6));
        city.setPopulation(resultSet.getLong(7));
        city.setMetersAboveSeaLevel(resultSet.getDouble(8));
        city.setEstablishmentDate(resultSet.getString(9));
        city.setAgglomeration(resultSet.getInt(10));
        city.setStandardOfLiving(StandardOfLiving.valueOf(resultSet.getString(11)));
        city.setGovernor(new Human(resultSet.getLong(12)));
        city.setUser_name(resultSet.getString(13));
        return city;
    }

    private void convertToStatement(City city, PreparedStatement statement) throws SQLException {
        statement.setString(1, city.getName());
        statement.setFloat(2, city.getCoordinates().getX());
        statement.setDouble(3, city.getCoordinates().getY());
        statement.setString(4, city.getCreationDate());
        statement.setDouble(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        statement.setDouble(7, city.getMetersAboveSeaLevel());
        statement.setString(8, city.getEstablishmentDate());
        statement.setInt(9, city.getAgglomeration());
        statement.setString(10, city.getStandardOfLiving().toString());
        statement.setLong(11, city.getGovernor().getHeight());
        statement.setString(12, city.getUser_name());
    }
}
