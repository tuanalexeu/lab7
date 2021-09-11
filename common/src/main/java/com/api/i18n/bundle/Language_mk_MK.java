package com.api.i18n.bundle;

import java.util.ListResourceBundle;

public class Language_mk_MK extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
// General
                {"register", "Регистрирај се"},
                {"login", "Логирај Се"},
                {"message","Порака"},
                {"nickname","Прекар"},
                {"password","Лозинка"},
                {"warning", "Предупредување"},
                {"loginFailure","Невалидно корисничко име и/или лозинка"},
                {"registrationFailure","Корисник со исто име веќе постои"},
                {"greeting_sever","Терминалот за контрола на серверот го прима. Внесете зачувај за да ја зачувате колекцијата, излезете за да го запрете серверот"},
                {"language", "Jазик"},
                {"help","Помош"},
                {"about","За"},
                {"mode","Мод"},
                {"table","Табела"},
                {"canvas","Платно"},
                {"logOut","Одјави се"},
                {"desk","Биро"},
                {"command","Команда"},
                {"action","Дејствува"},
                {"general","Генерал"},
                {"submit","Поднесе"},
                {"clear","Jасен"},
                {"info","Инфо"},
                {"coordinate_view","Координирана репрезентација"},
                {"enums","Набројувања"},
                {"submit_warning", "Грешка при извршување на командата"},

// Dragon info
                {"dragon","Змеј"},
                {"dragons","Змејови"},
                {"id","Идентификатор"},
                {"name","Име"},
                {"color","Боја"},
                {"coordinates","Координати"},
                {"creationDate","Датум на производство"},
                {"age","Возраст"},
                {"type","Тип"},
                {"size","Големина"},
                {"character","Карактер"},
                {"cave","Пештера"},
                {"depth","Длабочина"},
                {"numberOfTreasure","Количина на богатство"},
                {"location","Локација"},
                {"xCoordinate","X координата"},
                {"yCoordinate","Y координата"},

// Command description
                {"infoAdd","Додадете нова ставка во колекцијата"},
                {"infoClear","Колекцијата е јасна"},
                {"infoExecuteScript","Прочитајте ја и извршете ја скриптата од датотеката"},
                {"infoExit","Завршете ја програмата (без зачувување во датотека)"},
                {"infoFilterGreaterThanColor","Покажете предмети чија вредност на полето за боја е поголема од наведената"},
                {"infoFilterLessThanCharacter","Покажете елементи чија вредност на полето на карактерот е помала од наведената"},
                {"infoHelp","Покажете помош за достапните команди"},
                {"infoInfo","Покажете информации за колекција"},
                {"infoInsertAtIndex","Додадете ставка во одредена позиција"},
                {"infoRemoveAllByCave","Отстранете го елементот чија вредност пештерата е еднаква на дадената"},
                {"infoRemoveAtIndex","Избришете го елементот на одредена позиција"},
                {"infoRemoveById","Избришете го елементот чијшто идентификатор е еднаков на дадениот"},
                {"infoSave","Зачувајте ја колекцијата во датотека"},
                {"infoShow","Изложба колекција"},
                {"infoShuffle","Измешајте ги предметите за собирање по случаен редослед"},
                {"infoUpdateId","Ажурирајте ја вредноста на елементот со дадениот проект"},

// Info
                {"askForNull","Внесете NULL за да ги прескокнете карактеристиките на пештерата (или која било друга вредност да продолжите):"},

// Exceptions
                {"scriptNotValid","Неважечка скрипта"},
                {"fileNotFound","Документот не е пронајден"},
                {"invalidInput","Невалиден запис"},
                {"inputGreaterZero","Вредноста на полето мора да биде поголема од нула"},
                {"notNullField","Полето не може да биде ништовно"},
                {"noSuchCommand","Непозната опрема"},
                {"recursiveCallScript","Не може да повика датотека од себе"},

// Obj input
                {"enterName","Внесете го вашето име:"},
                {"enterCoordinates","Внесете ги координатите (двојно, меѓусебно одделени со празно место):"},
                {"enterAge","Внесете возраст:"},
                {"enterColor","Внесете боја (RED, BLACK, BLUE, ORANGE):"},
                {"enterType","Внесете го типот (WATER, AIR, FIRE):"},
                {"enterCharacter","Внесете карактер (CUNNING, CHAOTIC, FICKLE):"},
                {"enterDepth","Внесете ја длабочината на пештерата:"},
                {"enterWealth","Внесете ја количината на богатство:"},

// Bool
                {"booleanOpTrue","Операцијата беше успешна"},
                {"booleanOpFalse","Операцијата не успеа"}
        };
    }
}
