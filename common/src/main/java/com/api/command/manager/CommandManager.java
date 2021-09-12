package com.api.command.manager;

import com.api.command.Command;
import com.api.command.ExecuteScript;
import com.api.command.annotation.AttachedObj;
import com.api.command.annotation.AttachedObjFactory;
import com.api.entity.City;
import com.api.entity.User;
import com.api.i18n.Messenger;
import com.api.i18n.MessengerFactory;
import com.api.message.Message;
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

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public String executeCommand(Message message) throws Exception {

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
        setResult(command, message);
        return message.getResult();
    }

    private void setResult(Command[] command,
                                  Message message) throws Exception {
        writeLock.lock();
        try {
            message.setResult(
                    command[0] != null
                            ? command[0].process(message).toString()
                            : messenger.getMessage("noSuchCommand")
            );
        } finally {
            writeLock.unlock();
        }
    }

    private static Set<Class<? extends Command>> getCommandClassList() {
        return new Reflections("com.api").getSubTypesOf(Command.class);
    }

    public static City validateAnnotation(Class<? extends Command> c, User user) throws Exception {
        if(c.isAnnotationPresent(AttachedObj.class)) {
            AttachedObj attachedObj = c.getAnnotation(AttachedObj.class);
            return AttachedObjFactory.newInstance(attachedObj.type(), user);
        }
        return null;
    }

}
