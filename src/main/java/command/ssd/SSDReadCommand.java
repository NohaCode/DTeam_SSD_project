package command.ssd;

import util.FileHandler;

import java.util.ArrayList;

public class SSDReadCommand implements SSDCommand {
    private static final Integer POS_INDEX = 1;

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (isInvalidLengthParameter(commandOptionList)) {
            return false;
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        if (isIncorrectIndex(pos))
            return false;

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        fileHandler.makeFile();

        int index = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(index, data);
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList == null || commandOptionList.size() != 2;
    }

    private boolean isIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }
}
