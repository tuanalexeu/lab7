package com.api.i18n.bundle;

import java.util.ListResourceBundle;

public class Language_uk_UA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{

                // General
                {"register", "Реєстрація"},
                {"login", "Вхід"},
                {"message","Повідомлення"},
                {"nickname","Логін"},
                {"password","Пароль"},
                {"warning", "попередження"},
                {"loginFailure","Невірні логін і / або пароль"},
                {"registrationFailure","Користувач з таким ім'ям вже існує"},
                {"greeting_sever","Вас вітає термінал управління сервером. Введіть save для збереження колекції, exit - для зупинки сервера."},
                {"language", "Мова"},
                {"help","Допомога"},
                {"about","Про нас"},
                {"mode","Режим"},
                {"table","Таблиця"},
                {"canvas","полотно"},
                {"logOut","вихід"},
                {"desk","стіл"},
                {"command","Команда"},
                {"action","Дія"},
                {"general","Загальна"},
                {"submit","Подтвердить"},
                {"clear","підтвердити"},
                {"info","інфо"},
                {"coordinate_view","Координатное представление"},
                {"enums","координатне уявлення"},
                {"submit_warning", "Помилка виконання команди"},


                // Dragon info
                {"dragon","Дракон"},
                {"dragons","Драконы"},
                {"id","ідентифікатор"},
                {"name","ім'я"},
                {"color","колір"},
                {"coordinates","координати"},
                {"creationDate","дата створення"},
                {"age","вік"},
                {"type","Тип"},
                {"size","Розмір"},
                {"character","Характер"},
                {"cave","Печера"},
                {"depth","глибина"},
                {"numberOfTreasure","кількість багатства"},
                {"location","Локация"},
                {"xCoordinate","X координата"},
                {"yCoordinate","Y координата"},

                // Command description
                {"infoAdd","Додати новий елемент в колекцію"},
                {"infoClear","Очистити колекцію"},
                {"infoExecuteScript","Вважати і виконати скрипт з файлу"},
                {"infoExit","Завершити програму (без збереження у файл)"},
                {"infoFilterGreaterThanColor","Вивести елементи, значення поля color яких більше заданого"},
                {"infoFilterLessThanCharacter","Вивести елементи, значення поля character яких менше заданого"},
                {"infoHelp","Вивести довідку за доступними командам"},
                {"infoInfo","Вивести інформацію про колекцію"},
                {"infoInsertAtIndex","Додати елемент в задану позицію"},
                {"infoRemoveAllByCave","Видалити елемент, значення cave якого дорівнює заданому"},
                {"infoRemoveAtIndex","Видалити елемент в даній точці"},
                {"infoRemoveById","Видалити елемент, ID якого дорівнює заданому"},
                {"infoSave","Зберегти колекцію в файл"},
                {"infoShow","вивести колекцію"},
                {"infoShuffle","Перемішати елементи колекції в випадковому порядку"},
                {"infoUpdateId","Оновити значення елемента з заданим ID"},

                // Info
                {"askForNull","Введіть NULL, щоб не вводити характеристики печери (Або будь-яке інше значення, щоб продовжити):"},

                // Exceptions
                {"scriptNotValid","некоректний скрипт"},
                {"fileNotFound","Файл не знайдено"},
                {"invalidInput","некоректний введення"},
                {"inputGreaterZero","Значення поля повинно бути більше нуля!"},
                {"notNullField","Поле не може бути null"},
                {"noSuchCommand","Невідома команда"},
                {"recursiveCallScript","Не можна викликати файл з самого себе!"},

                // Obj input
                {"enterName","Введіть ім'я:"},
                {"enterCoordinates","Введіть координати (Double, int через пробіл):"},
                {"enterAge","Введіть возраст:"},
                {"enterColor","Введіть цвет (RED, BLACK, BLUE, ORANGE):"},
                {"enterType","Введіть тип (WATER, AIR, FIRE):"},
                {"enterCharacter","Введіть характер (CUNNING, CHAOTIC, FICKLE):"},
                {"enterDepth","Введіть глубину пещеры:"},
                {"enterWealth","Введіть количество богатства:"},

                // Bool
                {"booleanOpTrue","Операція пройшла успішно"},
                {"booleanOpFalse","Операція не виконано"}
        };
    }
}
