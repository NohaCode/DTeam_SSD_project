package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.CommandValidation.*;

public class ShellScenarioInsertCommand implements ShellCommand {

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList, 3)) {
            return false;
        }
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        int index = Integer.parseInt(commandOptionList.get(1));
        String command = commandOptionList.get(2);
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();
        String[] scenarioList = allScenarioString.split("\n");
        if (isInvalidIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.add(index, command);
        scenarioList = list.toArray(new String[0]);
        fileHandler.writeScenario(String.join("\n", scenarioList));
    }

    private static boolean isInvalidIndex(int index, String[] scenarioList) {
        return scenarioList.length < index;
    }

}
