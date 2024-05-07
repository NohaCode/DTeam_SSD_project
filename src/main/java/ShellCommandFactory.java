import java.util.HashMap;

public class ShellCommandFactory {
    static HashMap<String, ShellCommand> commandMap = new HashMap<>();

    public static ShellCommand of(String command) {
        if (!commandMap.containsKey(command)) {
            if (command.equals("read")) {
                commandMap.put(command, new ShellReadCommand());
            } else if (command.equals("write")) {
                commandMap.put(command, new ShellWriteCommand());
            } else if(command.equals("exit")){
                commandMap.put(command, new ShellExitCommand());
            }
            else if(command.equals("help")){
                commandMap.put(command, new ShellHelpCommand());
            }
            else if(command.equals("fullwrite")){
                commandMap.put(command, new ShellFullWriteCommand());
            }
            else if(command.equals("fullread")){
                commandMap.put(command, new ShellFullReadCommand());
            }
        }
        return commandMap.get(command);
    }
}
