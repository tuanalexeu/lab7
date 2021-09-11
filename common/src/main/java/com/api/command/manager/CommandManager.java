package com.api.command.manager;

import com.api.command.Command;
import com.api.command.ExecuteScript;
import com.api.command.annotation.AttachedObj;
import com.api.entity.City;
import com.api.exception.NoSuchCommandException;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.message.MessageReq;
import com.api.message.MessageResp;
import com.api.print.api.Formatter;
import com.api.print.api.Printer;
import com.api.service.CityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CommandManager {

    private final static Logger logger = LoggerFactory.getLogger(CommandManager.class);

    private static Messenger messenger = MessengerFactory.getMessenger();

    private Formatter formatter;
    private Printer printer;
    private LinkedHashSet<City> mDataSet;
    private CityService cityService;

    // Используем ReadWriteLock для обеспечения потокобезопасности использования коллекции users
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public MessageResp executeCommand(MessageReq message) throws Exception {
        MessageResp messageResult = new MessageResp();

        logger.info("Execute command: " + message.getCommand());

        String commandName = message.getCommand().split(" ")[0];

        final Command[] command = new Command[1];

        getCommandClassList().forEach(c -> {
            if(c.getSimpleName().equalsIgnoreCase(commandName)) {
                try {
                    if(commandName.equalsIgnoreCase("executescript")) {
                        ExecuteScript executeScript = ExecuteScript.class
                                .getConstructor(LinkedHashSet.class, CityService.class)
                                .newInstance(mDataSet, cityService);
                        executeScript.setCommandManager(this);
                        command[0] = executeScript;
                    } else {
                        command[0] = c
                                .getConstructor(LinkedHashSet.class, CityService.class)
                                .newInstance(mDataSet, cityService);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Блокируем операцию, чтобы другие потоки не могли получить доступ во время записи
        writeLock.lock();
        try {
            messageResult.setResult(
                    command[0] != null
                            ? command[0].execute(message)
                            : messenger.getMessage("noSuchCommand")
            );
        } finally {
            writeLock.unlock();
        }


        return messageResult;
    }

    private static Set<Class<? extends Command>> getCommandClassList() {
        Reflections reflections = new Reflections("com.api");
        return reflections.getSubTypesOf(Command.class);
    }

    public static String[] getCommandNames() {
        return getCommandClassList()
                .stream()
                .map(c -> c.getSimpleName().toLowerCase(Locale.ROOT))
                .toArray(String[]::new);
    }

    public static Class<? extends Command> validateCommand(String commandName) {
        for (Class<? extends Command> c: getCommandClassList()) {
            if(c.getSimpleName().equalsIgnoreCase(commandName)) {
                return c;
            }
        }
        throw new NoSuchCommandException("Такой команды не существует");
    }

    public static boolean checkAttachedAnnotation(Class<? extends Command> c) throws Exception {
        return c.isAnnotationPresent(AttachedObj.class);
    }

}
