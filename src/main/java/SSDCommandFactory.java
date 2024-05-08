import java.util.HashMap;

public class SSDCommandFactory {
    static HashMap<String, SSDCommand> commandMap = new HashMap<>();

    public static SSDCommand of(String command) {
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
