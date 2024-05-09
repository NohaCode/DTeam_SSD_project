package command.shell;

import java.util.HashMap;

public class ShellCommandFactory {
    static HashMap<String, ShellCommand> commandMap = new HashMap<>();

    public static ShellCommand of(String command) {
        if (!commandMap.containsKey(command)) {
            if (command.equals("read")) {
                commandMap.put(command, new ShellReadCommand());
            } else if (command.equals("write")) {
                commandMap.put(command, new ShellWriteCommand());
            } else if (command.equals("exit")) {
                commandMap.put(command, new ShellExitCommand());
            } else if (command.equals("help")) {
                commandMap.put(command, new ShellHelpCommand());
            } else if (command.equals("fullwrite")) {
                commandMap.put(command, new ShellFullWriteCommand());
            } else if (command.equals("fullread")) {
                commandMap.put(command, new ShellFullReadCommand());
            } else if (command.equals("erase")) {
                commandMap.put(command, new ShellEraseCommand());
            } else if (command.equals("testapp1")) {
                commandMap.put(command, new ShellTestApp1Command());
            } else if (command.equals("testapp2")) {
                commandMap.put(command, new ShellTestApp2Command());
            } else if (command.equals("erase_range")) {
                commandMap.put(command, new ShellEraseRangeCommand());
            } else if (command.equals("run_list.lst")) {
                commandMap.put(command, new ShellRunnerCommand());
            } else if (command.equals("scenario_append")) {

            } else if (command.equals("scenario_insert")) {

            } else if (command.equals("scenario_read")) {

            } else if (command.equals("scenario_read_all")) {

            } else if (command.equals("scenario_read_range")) {

            } else if (command.equals("scenario_update")) {

            } else if (command.equals("scenario_delete")) {

            } else if (command.equals("scenario_delete_all")) {

            }
        }
        return commandMap.get(command);
    }
}
