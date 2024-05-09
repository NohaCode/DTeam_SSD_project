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
                commandMap.put(command, new ShellScenarioAppendCommand());
            } else if (command.equals("scenario_insert")) {
                commandMap.put(command, new ShellScenarioInsertCommand());
            } else if (command.equals("scenario_read")) {
                commandMap.put(command, new ShellScenarioReadCommand());
            } else if (command.equals("scenario_read_all")) {
                commandMap.put(command, new ShellScenarioReadAllCommand());
            } else if (command.equals("scenario_read_range")) {
                commandMap.put(command, new ShellScenarioReadCommand());
            } else if (command.equals("scenario_update")) {
                commandMap.put(command, new ShellScenarioUpdateCommand());
            } else if (command.equals("scenario_delete")) {
                commandMap.put(command, new ShellScenarioDeleteCommand());
            } else if (command.equals("scenario_delete_all")) {
                commandMap.put(command, new ShellScenarioDeleteAllCommand());
            }
        }
        return commandMap.get(command);
    }
}
