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
        String[] scenarioList = fileHandler.readScenario().split("\n");
        if (scenarioList == null || scenarioList.length == 0 || scenarioList.length >= index) {
            throw new ShellException();
        }
        return scenarioList[index];
    }

    public String readAll() {
        return fileHandler.readScenario();
    }
}
