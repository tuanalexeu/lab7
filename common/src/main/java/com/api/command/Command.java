package com.api.command;

import com.api.entity.City;
import com.api.exception.NoSuchCommandException;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.message.Message;
import com.api.print.api.Formatter;
import com.api.print.implementation.FormatterImpl;
import com.api.service.CityService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class Command {

    private Messenger messenger = MessengerFactory.getMessenger();

    private LinkedHashSet<City> cityList;

    private CityService cityService;

    private Formatter formatter;

    private Validator validator;

    public Command(LinkedHashSet<City> cityList, CityService cityService) {
        this.cityList = cityList;
        this.cityService = cityService;
        this.formatter = new FormatterImpl();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Запускает логику команды
     * @param message - объект, переданный со стороны клиента
     * @return - результат выполнения команды
     * @throws Exception - в случае возможных исключений
     */
    public abstract Object execute(Message message) throws Exception;

    protected String getArg(String command) {
        return command.split(" ")[1];
    }

    public static Class<? extends Command> validateCommand(String commandName) {

        Reflections reflections = new Reflections("com.api.command");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> c: classes) {
            if(c.getSimpleName().equalsIgnoreCase(commandName)) {
                return c;
            }
        }

        throw new NoSuchCommandException("Такой команды не существует");
    }

    protected void settleIds() {
        int id = 0;
        for (City d : cityList) {
            d.setId(++id);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
