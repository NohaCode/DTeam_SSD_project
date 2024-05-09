package command.ssd;

import util.FileHandler;

import java.util.ArrayList;

public class SSDWriteCommand extends SSDCommand {
    private static final Integer POS_INDEX = 1;
    private static final Integer VALUE_INDEX = 2;

    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (isInvalidLengthParameter(commandOptionList)) {
            return false;
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        if (isIncorrectIndex(pos)) {
            return false;
        }

        String value = commandOptionList.get(VALUE_INDEX);
        if (isIncorrectValue(value)) {
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

    @Override
    boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList) {
        String commandIndex = commandOptionList.get(1);

//        성능최적화 예시1. Ignore Write
        ignoreWrite(commandIndex);
        return true;
    }

    private void ignoreWrite(String commandIndex) {
        for (int i = commandBuffer.size() - 1; i >= 0; i--) {
            ArrayList<String> bufferedCommandOptionList = commandBuffer.get(i);
            String bufferedCommandStr = bufferedCommandOptionList.get(0);
            String bufferedCommandIndex = bufferedCommandOptionList.get(1);
            if ("W".equals(bufferedCommandStr) && commandIndex.equals(bufferedCommandIndex)) {
                commandBuffer.remove(i);
            }
        }
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList.size() != 3;
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(CORRECT_VALUE_REGEX);
    }

    private boolean isIncorrectIndex(int pos) {
        return pos < 0 || pos > 99;
    }
}
