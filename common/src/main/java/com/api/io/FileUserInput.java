package com.api.io;

import com.api.entity.City;
import com.api.entity.User;

import java.util.Scanner;

/**
 * Класс, предоставляющий логику чтения элементов из файла
 */
public class FileUserInput extends UserInput {

    public FileUserInput(Scanner in) {
        super(in);
    }

    /**
     * Ввод элемента из файла.
     * @return - Введенный элемент (в случае корректных данных в файле)
     * @throws Exception - исключение, брошенное в случае некорректных данных в файле
     */
    @Override
    public City enterElement(User user) throws Exception {
        return new City(readName(),
                readCoordinates(),
                readArea(),
                readPopulation(),
                readMetersAboveSeaLevel(),
                readEstablishmentDate(),
                readAgglomeration(),
                readStandardOfLiving(),
                readGovernor(),
                user.getName()
        );
    }
}

