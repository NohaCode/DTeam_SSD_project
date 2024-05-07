import java.util.HashMap;

public class CommandFactory {
    static HashMap<String, Command> commandMap = new HashMap<>();

    public static Command of(String command) {
        if (!commandMap.containsKey(command)) {
            if (command.equals("R")) {
                commandMap.put(command, new SSDReadCommand());
            } else if (command.equals("W")) {
                commandMap.put(command, new SSDWriteCommand());
            }
        }
        return commandMap.get(command);
    }
}
