package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

import static util.CommandValidation.isInvalidScenario;

public class ShellScenarioAppendCommand implements ShellCommand {

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (commandOptionList.size() < 2) {
            return false;
        }
        if (isInvalidScenario(commandOptionList, 1)) {
            return false;
        }
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        StringBuilder command = new StringBuilder();
        for (int i = 1; i < commandOptionList.size(); i++) {
            if (i != 1) {
                command.append(" ");
            }
            command.append(commandOptionList.get(i));
        }
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();

        if (isEmptyScenario(allScenarioString)) {
            allScenarioString = command.toString();
        } else {
            allScenarioString = allScenarioString + "\n" + command;
        }

        fileHandler.writeScenario(allScenarioString);
    }

    private static boolean isEmptyScenario(String allScenarioString) {
        return allScenarioString == null || allScenarioString.isEmpty();
    }
}
