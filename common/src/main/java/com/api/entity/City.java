package com.api.entity;

import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
public class City implements Comparable<City>, Serializable {

    private static Integer static_id = 1;

    public static Messenger messenger = MessengerFactory.getMessenger();

    @NotNull(message = "id can't be null")
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "Name can't be null")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull(message = "Coordinates can't be null")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull(message = "Creation date can't be null")
    private String creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "Area must not be null")
    private Double area; //Значение поля должно быть больше 0, Поле не может быть null

    @Min(0)
    @NotNull(message = "Population must not be null")
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null

    private Double metersAboveSeaLevel;
    private String establishmentDate;
    private int agglomeration;

    @NotNull(message = "StandardOfLiving must not be null")
    private StandardOfLiving standardOfLiving; //Поле не может быть null

    @NotNull(message = "Human must not be null")
    private Human governor; // Поле не может быть null

    @NotNull
    private String user_name;

    public City(String name,
                Coordinates coordinates,
                Double area,
                Long population,
                Double metersAboveSeaLevel,
                LocalDate establishmentDate,
                int agglomeration,
                StandardOfLiving standardOfLiving,
                Human governor,
                String user_name) {
        this.id = static_id++;
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate.toString();
        this.agglomeration = agglomeration;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.user_name = user_name;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        this.creationDate = LocalDate.now().format(dateFormatter);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return agglomeration == city.agglomeration
                && id.equals(city.id)
                && name.equals(city.name)
                && coordinates.equals(city.coordinates)
                && creationDate.equals(city.creationDate)
                && area.equals(city.area)
                && population.equals(city.population)
                && Objects.equals(metersAboveSeaLevel, city.metersAboveSeaLevel)
                && Objects.equals(establishmentDate, city.establishmentDate)
                && standardOfLiving == city.standardOfLiving
                && governor.equals(city.governor)
                && user_name.equals(city.user_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate,
                area, population, metersAboveSeaLevel, establishmentDate,
                agglomeration, standardOfLiving, governor, user_name);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", coordinates=" + coordinates + "\n" +
                ", creationDate='" + creationDate + '\'' + "\n" +
                ", area=" + area + "\n" +
                ", population=" + population + "\n" +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel + "\n" +
                ", establishmentDate=" + establishmentDate + "\n" +
                ", agglomeration=" + agglomeration + "\n" +
                ", standardOfLiving=" + standardOfLiving + "\n" +
                ", governor=" + governor + "\n" +
                ", user_name='" + user_name + '\'' +
                '}' + "\n\n";
    }

    @Override
    public int compareTo(City o) {
        return Comparator
                .comparing(City::getName)
                .thenComparing(City::getArea)
                .thenComparing(City::getPopulation)
                .compare(this, o);
    }
}