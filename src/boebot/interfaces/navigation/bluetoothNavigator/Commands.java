package boebot.interfaces.navigation.bluetoothNavigator;

import boebot.interfaces.navigation.bluetoothNavigator.commands.*;

import java.lang.reflect.InvocationTargetException;

public enum Commands {
    FORWARD("w", Forward.class),
    BACKWARDS("s", Backwards.class),
    LEFT("a", Left.class),
    RIGHT("d", Right.class),
    PICKUP("c", Pickup.class),
    DROP("o", Drop.class);


    private final String commandString;
    private final Class<? extends Command> commandClass;
    Commands(String commandString, Class<? extends Command> commandClass) {
        this.commandString = commandString;
        this.commandClass = commandClass;
    }

    public String getCommandString() {
        return commandString;
    }

    public Class<? extends Command> getCommandClass() {
        return commandClass;
    }

    public static Command getCommand(String commandString, CommandCallBack commandCallBack) {
        for(Commands commands : values()) {
            if(commands.getCommandString().equalsIgnoreCase(commandString)) {
                try {
                    return commands.getCommandClass().getDeclaredConstructor(CommandCallBack.class).newInstance(commandCallBack);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    System.out.println("Error on getting command, returning null");
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
