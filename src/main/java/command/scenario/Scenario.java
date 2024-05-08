package command.scenario;

import app.Shell;
import exception.ShellException;
import util.FileHandler;

public class Scenario {
    private FileHandler fileHandler;

    public Scenario() {
        fileHandler = FileHandler.get();
    }

    public String read(int index) {
        String allScenarioString = fileHandler.readScenario().trim();
        if (isEmptyScenario(allScenarioString)) {
            throw new ShellException();
        }
        String[] scenarioList = allScenarioString.split("\n");
        if (isInvalidReadIndex(index, scenarioList)) {
            throw new ShellException();
        }
        return scenarioList[index];
    }

    private static boolean isEmptyScenario(String allScenarioString) {
        return allScenarioString == null || allScenarioString.isEmpty();
    }

    public String readAll() {
        return fileHandler.readScenario();
    }

    private static boolean isInvalidReadIndex(int index, String[] scenarioList) {
        return scenarioList.length == 0 || scenarioList.length >= index;
    }

}
