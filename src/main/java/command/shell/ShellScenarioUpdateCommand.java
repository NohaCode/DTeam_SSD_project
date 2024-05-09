package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static util.CommandValidation.*;
public class ShellScenarioUpdateCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (commandOptionList.size() < 3) {
            return false;
        }
        if (isInvalidScenario(commandOptionList, 2)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        int index = Integer.parseInt(commandOptionList.get(1));
        StringBuilder command = new StringBuilder();
        for (int i = 2; i < commandOptionList.size(); i++) {
            if (i != 2) {
                command.append(" ");
            }
            command.append(commandOptionList.get(i));
        }
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();
        String[] scenarioList = allScenarioString.split("\n");
        if (isInvalidIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.set(index, command.toString());
        scenarioList = list.toArray(new String[0]);
        fileHandler.writeScenario(String.join("\n", scenarioList));
    }

    private static boolean isInvalidIndex(int index, String[] scenarioList) {
        return scenarioList.length <= index;
    }

}
