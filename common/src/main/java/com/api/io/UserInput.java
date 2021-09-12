package com.api.io;

import com.api.entity.*;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Класс, который отвечает за источник ввода элементов
 * Хранит логику ввода в зависимости от источника
 */
@AllArgsConstructor
public abstract class UserInput {

    protected final static Messenger messenger = MessengerFactory.getMessenger();

    private final Scanner in;

    /**
     * Логика ввода элемента. Метод нужно переопределить в суб-классах
     * @return - Введенный элемент
     * @throws Exception - исключение, возникающее в случае некорректного ввода
     */
    public abstract City enterElement(User user) throws Exception;

    // Все последующие методы предоставляют логику ввода отдельных полей
    // Пользователь вводит строку, а метод преобразует ее в нужный тип
    // Методы также хранят логику ограничений значений
    // (Метод не допустит ввод значения -10, если поле должно быть больше нуля)
    public String readName() throws Exception {
        try {
            String name = in.nextLine();
            if(name.equals("")) throw new IllegalArgumentException();
            return name;
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public Coordinates readCoordinates() throws Exception {
        try {
            String[] arr = (in.nextLine()).split(" ");
            Float x = Float.parseFloat(arr[0]);
            Double y = Double.parseDouble(arr[1]);
            return new Coordinates(x,y);
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public Double readArea() throws Exception {
        try {
            double result = Double.parseDouble(in.nextLine());
            if(result <= 0) throw new IllegalArgumentException(messenger.getMessage("inputGreaterZero"));
            return result;
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public Long readPopulation() throws Exception {
        try {
            long result = Long.parseLong(in.nextLine());
            if(result <= 0) throw new IllegalArgumentException(messenger.getMessage("inputGreaterZero"));
            return result;
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public Double readMetersAboveSeaLevel() throws Exception {
        try {
            return Double.parseDouble(in.nextLine());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public LocalDate readEstablishmentDate() throws Exception {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String result = in.nextLine();
            return LocalDate.parse(result, dateFormatter);
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public int readAgglomeration() throws Exception {
        try {
            return Integer.parseInt(in.nextLine());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public StandardOfLiving readStandardOfLiving() throws Exception {
        try {
            String response = in.nextLine();

            if(response == null || response.equals("")) {
                throw new IllegalArgumentException("Must not be empty");
            }

            return StandardOfLiving.valueOf(response);
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }

    public Human readGovernor() throws Exception {
        try {
            long result = Long.parseLong(in.nextLine());
            if(result <= 0) throw new IllegalArgumentException(messenger.getMessage("inputGreaterZero"));
            return new Human(result);
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(messenger.getMessage("invalidInput"));
        }
    }
}
