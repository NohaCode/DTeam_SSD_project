package command.scenario;

import app.Shell;
import exception.ShellException;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void create(String command) {
        String allContents = readAll();
        allContents = allContents.trim() + "\n" + command;
        fileHandler.writeScenario(allContents);
    }

    public void insert(int index, String command) {
        String allContents = readAll();
        String[] scenarioList = allContents.split("\n");
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.add(index, command);
        scenarioList = list.toArray(new String[0]);
        allContents = String.join("\n", scenarioList);
        fileHandler.writeScenario(allContents);
    }

    public void deleteAll() {
        fileHandler.writeScenario("");
    }

    public void delete(int index) {
        String allContents = readAll();
        String[] scenarioList = allContents.split("\n");
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.remove(index);
        scenarioList = list.toArray(new String[0]);
        allContents = String.join("\n", scenarioList);
        fileHandler.writeScenario(allContents);
    }

    public void update(int index, String command) {
        String allContents = readAll();
        String[] scenarioList = allContents.split("\n");
        scenarioList[index] = command;
        allContents = String.join("\n", scenarioList);
        fileHandler.writeScenario(allContents);
    }
}
