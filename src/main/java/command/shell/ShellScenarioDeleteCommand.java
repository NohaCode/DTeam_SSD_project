package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.CommandValidation.*;

public class ShellScenarioDeleteCommand extends ShellCommand {

    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList, 2)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        FileHandler fileHandler = FileHandler.get();

        int index = Integer.parseInt(commandOptionList.get(1));

        String allScenarioString = fileHandler.readScenario().trim();
        String[] scenarioList = allScenarioString.split("\n");
        if (isInvalidIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.remove(index);
        scenarioList = list.toArray(new String[0]);
        fileHandler.writeScenario(String.join("\n", scenarioList));
    }

    private static boolean isInvalidIndex(int index, String[] scenarioList) {
        return scenarioList.length <= index;
    }
}
