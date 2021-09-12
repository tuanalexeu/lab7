package com.api.i18n.bundle;

import java.util.ListResourceBundle;

public class Language_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{

                // General
                {"register", "Регистрация"},
                {"login", "Вход"},
                {"message","Сообщение"},
                {"nickname","Логин"},
                {"password","Пароль"},
                {"warning", "Предупреждение"},
                {"loginFailure","Неверные логин и/или пароль"},
                {"registrationFailure","Пользователь с таким именем уже существует"},
                {"greeting_sever","Вас приветствует терминал управления сервером. Введите save для сохранения коллекции, exit - для остановки сервера."},
                {"language", "Язык"},
                {"help","Помощь"},
                {"about","О нас"},
                {"mode","Режим"},
                {"table","Таблица"},
                {"canvas","Холст"},
                {"logOut","Выход"},
                {"desk","Стол"},
                {"command","Команда"},
                {"action","Действие"},
                {"general","Общее"},
                {"submit","Подтвердить"},
                {"clear","Очистить"},
                {"info","Инфо"},
                {"coordinate_view","Координатное представление"},
                {"enums","Перечисления"},
                {"submit_warning", "Ошибка выполнения команды"},

                // Command description
                {"infoAdd","Добавить новый элемент в коллекцию"},
                {"infoClear","Очистить коллекцию"},
                {"infoExecuteScript","Считать и исполнить скрипт из файла"},
                {"infoExit","Завершить программу (без сохранения в файл)"},
                {"infoHelp","Вывести справку по доступным командам"},
                {"infoInfo","Вывести информацию о коллекции"},
                {"infoRemoveById","Удалить элемент, ID которого равен заданному"},
                {"infoSave","Сохранить коллекцию в файл"},
                {"infoShow","Вывести коллекцию"},
                {"infoUpdateId","Обновить значение элемента с заданным ID"},
                {"infoCountStandardOfLiving", "вывести количество элементов, значение поля standardOfLiving которых равно заданному"},
                {"infoCountGreaterThanMetersAboveSeaLevel", "вывести количество элементов, значение поля metersAboveSeaLevel которых больше заданного"},
                {"infoGroupCountingByEstablishmentDate", "сгруппировать элементы коллекции по значению поля establishmentDate, вывести количество элементов в каждой группе"},
                {"infoHistory", "вывести последние 14 команд (без их аргументов)"},
                {"infoAddIfMax", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"},
                {"infoAddIfMin", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции"},

                // Info
                {"askForNull","Введите NULL, чтобы не вводить характеристики пещеры (Или любое другое значение, чтобы продолжить):"},
                {"type", "тип"},
                {"size", "размер"},

                // Exceptions
                {"scriptNotValid","Некорректный скрипт"},
                {"fileNotFound","Файл не найден"},
                {"invalidInput","Некорректный ввод"},
                {"inputGreaterZero","Значение поля должно быть больше нуля!"},
                {"notNullField","Поле не может быть null"},
                {"noSuchCommand","Неизвестная команда"},
                {"recursiveCallScript","Нельзя вызвать файл из самого себя!"},

                // Obj input
                {"enterName","Введите имя:"},
                {"enterCoordinates","Введите координаты (Double, int через пробел):"},
                {"enterArea","Введите расположение:"},
                {"enterPopulation","Введите популяцию:"},
                {"enterMetersAboveSeaLevel","Введите кол-во метров над уровнем моря:"},
                {"enterEstablishmentDate","Введите дату основания (dd.MM.yyyy):"},
                {"enterAgglomeration","Введите агломерацию:"},
                {"enterStandardOfLiving","Введите уровень жизни:"},
                {"enterGovernor","Введите рост губернатора:"},

                // Bool
                {"booleanOpTrue","Операция выполнена успешно"},
                {"booleanOpFalse","Операция не выполнена"}
        };
    }
}
