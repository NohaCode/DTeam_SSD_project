package command.scenario;

import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class Scenario {
    private FileHandler fileHandler;
    public static final String LINE_SEPARATOR = "\n";

    public Scenario() {
        fileHandler = FileHandler.get();
    }

    @Deprecated
    public void readAll() {
        String allScenarioString = fileHandler.readScenario().trim();;
        String[] scenarioList = allScenarioString.split(LINE_SEPARATOR);
        for (int i=0 ; i < scenarioList.length ; i++) {
            System.out.println("[" + i + "] " + scenarioList[i]);
        }
    }

//    public void readRange(int start, int end) {
//        String allScenarioString = fileHandler.readScenario().trim();;
//        String[] scenarioList = allScenarioString.split(LINE_SEPARATOR);
//        int maxLength = scenarioList.length;
//        if (start > end) {
//            return;
//        }
//        int newStartIndex = Math.min(start, maxLength);
//        int newEndIndex = Math.min(end, maxLength);
//        for (; newStartIndex < newEndIndex ; newStartIndex++) {
//            System.out.println("[" + newStartIndex+ "] " + scenarioList[newStartIndex]);
//        }
//    }

    @Deprecated
    public void read(int index) {
        String allScenarioString = fileHandler.readScenario().trim();;
        if (isEmptyScenario(allScenarioString)) {
            System.out.println("테스트 시나리오 없음");
            return;
        }
        String[] scenarioList = allScenarioString.split(LINE_SEPARATOR);
        if (isInvalidReadIndex(index, scenarioList)) {
            System.out.println();
            return;
        }
        System.out.println("[" + index + "] " + scenarioList[index]);
    }

    private static boolean isEmptyScenario(String allScenarioString) {
        return allScenarioString == null || allScenarioString.isEmpty();
    }

    private static boolean isInvalidReadIndex(int index, String[] scenarioList) {
        return scenarioList.length <= index;
    }

    private static boolean isInvalidWriteIndex(int index, String[] scenarioList) {
        return scenarioList.length < index;
    }

    @Deprecated
    public void append(String command) {
        String allScenarioString = fileHandler.readScenario().trim();;
        if (isEmptyScenario(allScenarioString)) {
            allScenarioString = command;
        } else {
            allScenarioString = allScenarioString + LINE_SEPARATOR + command;
        }
        fileHandler.writeScenario(allScenarioString);
    }

    @Deprecated
    public void insert(int index, String command) {
        String[] scenarioList = getScenarioList();
        if (isInvalidWriteIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.add(index, command);
        writeScenario(list);
    }

    private String[] getScenarioList() {
        String allScenarioString = fileHandler.readScenario().trim();;
        return allScenarioString.split(LINE_SEPARATOR);
    }

    @Deprecated
    public void deleteAll() {
        fileHandler.writeScenario("");
    }

    @Deprecated
    public void delete(int index) {
        String[] scenarioList = getScenarioList();
        if (isInvalidReadIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.remove(index);
        writeScenario(list);
    }

    @Deprecated
    public void update(int index, String command) {
        String[] scenarioList = getScenarioList();
        if (isInvalidReadIndex(index, scenarioList)) {
            return;
        }
        List<String> list = new ArrayList<>(Arrays.asList(scenarioList));
        list.set(index, command);
        writeScenario(list);
    }

    private void writeScenario(List<String> list) {
        String[] scenarioList = list.toArray(new String[0]);
        fileHandler.writeScenario(String.join(LINE_SEPARATOR, scenarioList));
    }

}
