package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShellScenarioDeleteCommand implements ShellCommand {
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
        FileHandler fileHandler = FileHandler.get();

        String indexString = commandOptionList.get(1);
        int index = Integer.parseInt(indexString);

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