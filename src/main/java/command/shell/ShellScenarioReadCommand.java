package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;
import static util.CommandValidation.*;
public class ShellScenarioReadCommand implements ShellCommand {
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList, 2)) {
            return false;
        }
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        FileHandler fileHandler = FileHandler.get();
        String allScenarioString = fileHandler.readScenario().trim();
        int index = Integer.parseInt(commandOptionList.get(1));
        if (isEmptyScenario(allScenarioString)) {
            System.out.println("테스트 시나리오 없음");
            return;
        }
        String[] scenarioList = allScenarioString.split("\n");
        if (isInvalidIndex(index, scenarioList)) {
            System.out.println();
            return;
        }
        System.out.println("[" + index + "] " + scenarioList[index]);
    }

    private static boolean isEmptyScenario(String allScenarioString) {
        return allScenarioString == null || allScenarioString.isEmpty();
    }

    private static boolean isInvalidIndex(int index, String[] scenarioList) {
        return scenarioList.length <= index;
    }

}
