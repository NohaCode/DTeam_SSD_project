package command.ssd;

import util.FileHandler;

import java.util.ArrayList;

public class SSDReadCommand extends SSDCommand {
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

    @Override
    boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList) {
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
                return false;
            }
        }

        return false;
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList == null || commandOptionList.size() != 2;
    }

    private boolean isIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }
}
