package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

import static util.CommandValidation.*;

public class ShellScenarioReadAllCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList, 1)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();
        String[] scenarioList = allScenarioString.split("\n");
        for (int i = 0; i < scenarioList.length; i++) {
            System.out.println("[" + i + "] " + scenarioList[i]);
        }
    }
}
