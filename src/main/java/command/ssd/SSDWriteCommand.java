package command.ssd;

import util.CommandValidation;
import util.FileHandler;

import java.util.ArrayList;

public class SSDWriteCommand extends SSDCommand {
    private static final Integer POS_INDEX = 1;
    private static final Integer VALUE_INDEX = 2;
    private static final Integer COMMAND_OPTION_LIST_LENGTH = 3;

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, COMMAND_OPTION_LIST_LENGTH)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidValue(commandOptionList, VALUE_INDEX)) {return false;}
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

}
