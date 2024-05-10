package command.ssd;

import util.CommandValidation;
import util.FileHandler;

import java.util.ArrayList;

public class SSDReadCommand extends SSDCommand {
    private static final Integer POS_INDEX = 1;
    private static final Integer COMMAND_OPTION_LIST_LENGTH = 2;


    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, COMMAND_OPTION_LIST_LENGTH)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        fileHandler.makeFile();

        int index = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(index, data);
    }
    @Override
    boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList) {
        flushWrite(commandOptionList);
        flushErase(commandOptionList);
        return false;
    }

    private void flushWrite(ArrayList<String> commandOptionList) {
        String commandStr = commandOptionList.get(0);
        String commandIndex = commandOptionList.get(1);

        for (int i = commandBuffer.size() - 1; i >= 0 ; i--) {
            ArrayList<String> bufferedCommandOptionList = commandBuffer.get(i);
            String bufferedCommandStr = bufferedCommandOptionList.get(0);
            if(!bufferedCommandStr.equals("W"))
                continue;

            String bufferedCommandIndex = bufferedCommandOptionList.get(1);
            String bufferedCommandData = bufferedCommandOptionList.get(2);

            if (commandIndex.equals(bufferedCommandIndex)) {
                SSDCommand bufferedCommand = SSDCommandFactory.of(bufferedCommandStr);
                bufferedCommand.run(bufferedCommandOptionList);
                commandBuffer.remove(i);
                return;
            }
        }
    }

    private void flushErase(ArrayList<String> commandOptionList) {
        String commandStr = commandOptionList.get(0);
        int commandIndex = Integer.parseInt(commandOptionList.get(1));

        for (int i = commandBuffer.size() - 1; i >= 0 ; i--) {
            ArrayList<String> bufferedCommandOptionList = commandBuffer.get(i);
            String bufferedCommandStr = bufferedCommandOptionList.get(0);
            if(!bufferedCommandStr.equals("E"))
                continue;

            int bufferedCommandStartIndex = Integer.parseInt(bufferedCommandOptionList.get(1));
            int bufferedCommandEndIndex = bufferedCommandStartIndex + Integer.parseInt(bufferedCommandOptionList.get(2));

            if (bufferedCommandStartIndex <= commandIndex && commandIndex < bufferedCommandEndIndex) {
                SSDCommand bufferedCommand = SSDCommandFactory.of(bufferedCommandStr);
                bufferedCommand.run(bufferedCommandOptionList);
                commandBuffer.remove(i);
                return;
            }
        }
    }
}
