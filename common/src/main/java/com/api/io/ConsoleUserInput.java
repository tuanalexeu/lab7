package com.api.io;

import com.api.entity.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Класс, предоставляющий логику чтения элементов с консоли
 */
public class ConsoleUserInput extends UserInput {

    public ConsoleUserInput() {
        super(new Scanner(System.in));
    }

    /**
     * Вводя элемент с консоли, мы должны вводить каждое поле в цикле до тех пор, пока ввод не будет корректным.
     * В случае некорректного ввода - бросается исключение и программа снова просит нас ввести значение
     * @return - введенный объект
     */
    @Override
    public City enterElement(User user) {

        String name;
        Coordinates coordinates;
        Double area;
        Long population;
        Double metersAboveSeaLevel;
        LocalDate establishmentDate;
        int agglomeration;
        StandardOfLiving standardOfLiving;
        Human governor;

        while (true) {
            System.out.println(messenger.getMessage("enterName"));
            try {
                name = readName();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterCoordinates"));
            try {
                coordinates = readCoordinates();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterArea"));
            try {
                area = readArea();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterPopulation"));
            try {
                population = readPopulation();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterMetersAboveSeaLevel"));
            try {
                metersAboveSeaLevel = readMetersAboveSeaLevel();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterEstablishmentDate"));
            try {
                establishmentDate = readEstablishmentDate();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterAgglomeration"));
            try {
                agglomeration = readAgglomeration();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterStandardOfLiving"));
            try {
                standardOfLiving = readStandardOfLiving();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println(messenger.getMessage("enterGovernor"));
            try {
                governor = readGovernor();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new City(name, coordinates, area, population, metersAboveSeaLevel,
                establishmentDate, agglomeration, standardOfLiving, governor, user.getName());
    }
}

