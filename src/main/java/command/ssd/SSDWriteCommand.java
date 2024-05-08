package command.ssd;

import util.FileHandler;

import java.util.ArrayList;

public class SSDWriteCommand implements SSDCommand {
    private static final Integer POS_INDEX = 1;
    private static final Integer VALUE_INDEX = 2;

    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";

    FileHandler fileHandler;

    public SSDWriteCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList)) {
            return false;
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        if (!isValidIndex(pos)) {
            return false;
        }

        String value = commandOptionList.get(VALUE_INDEX);
        if (!isValidValue(value)) {
            return false;
        }
        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String value = commandOptionList.get(VALUE_INDEX);

        fileHandler.makeFile();
        fileHandler.writeNAND(pos, value);
    }

    private boolean isValidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 3;
    }

    private boolean isValidValue(String value) {
        return value != null && !value.isEmpty() && value.matches(CORRECT_VALUE_REGEX);
    }

    private boolean isValidIndex(int index) { return index >= 0 && index <= 99; }
}
