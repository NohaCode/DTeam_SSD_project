package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

public class ShellScenarioAppendCommand implements ShellCommand {

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidCommandOptionListSize(commandOptionList)) {
            return false;
        }
        return true;
    }

    private boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() != 2;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        String command = commandOptionList.get(1);
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();

        if (isEmptyScenario(allScenarioString)) {
            allScenarioString = command;
        } else {
            allScenarioString = allScenarioString + "\n" + command;
        }

        fileHandler.writeScenario(allScenarioString);
    }

    private static boolean isEmptyScenario(String allScenarioString) {
        return allScenarioString == null || allScenarioString.isEmpty();
    }
}
