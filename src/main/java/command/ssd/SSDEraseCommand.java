package command.ssd;

import util.CommandValidation;

import java.util.ArrayList;

import static util.FileHandler.DEFAULT_VALUE;

public class SSDEraseCommand extends SSDCommand {

    private static final Integer POS_INDEX = 1;
    private static final Integer SIZE_INDEX = 2;
    private static final Integer COMMAND_OPTION_LIST_LENGTH = 3;

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, COMMAND_OPTION_LIST_LENGTH)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}
        if(!CommandValidation.isValidIntegerParameter(commandOptionList, SIZE_INDEX)){return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}
        if(!CommandValidation.isValidEraseSize(commandOptionList, SIZE_INDEX)) {return false;}

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        int size = Integer.parseInt(commandOptionList.get(SIZE_INDEX));

        for (int index = pos; index < pos + size; index++) {
            if (index > 99) break;
            fileHandler.writeNAND(index, DEFAULT_VALUE);
        }
    }
    @Override
    boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList) {
        if (commandBuffer.isEmpty()){
            return true;
        }

        ignoreWrite(commandOptionList);

        commandBuffer.add(commandOptionList);
        while(mergeErase()){}
        return false;
    }

    private boolean mergeErase() {
        if(commandBuffer.size() <= 1)
            return false;

        ArrayList<String> commandOptionList = commandBuffer.get(commandBuffer.size() - 1);
        commandBuffer.remove(commandBuffer.size() - 1);

        int startIndex = Integer.parseInt(commandOptionList.get(1));
        int endIndex = startIndex + Integer.parseInt(commandOptionList.get(2));

        ArrayList<String> bufferedCommandOptionList = commandBuffer.get(commandBuffer.size() - 1);
        String bufferedCommandStr = bufferedCommandOptionList.get(0);

        if (!bufferedCommandStr.equals("E"))
            return false;

        int bufferedStartIndex = Integer.parseInt(bufferedCommandOptionList.get(1));
        int bufferedEndIndex = bufferedStartIndex + Integer.parseInt(bufferedCommandOptionList.get(2));

        if (bufferedEndIndex == startIndex) {
            commandBuffer.remove(commandBuffer.size() - 1);
            bufferedCommandOptionList.set(1, bufferedCommandOptionList.get(1));
            bufferedCommandOptionList.set(2, String.valueOf(Integer.parseInt(commandOptionList.get(2)) + Integer.parseInt(bufferedCommandOptionList.get(2))));
            commandBuffer.add(bufferedCommandOptionList);
            return true;
        }else if(endIndex == bufferedStartIndex){
            commandBuffer.remove(commandBuffer.size() - 1);
            bufferedCommandOptionList.set(1, commandOptionList.get(1));
            bufferedCommandOptionList.set(2, String.valueOf(Integer.parseInt(bufferedCommandOptionList.get(2)) + Integer.parseInt(commandOptionList.get(2))));
            commandBuffer.add(bufferedCommandOptionList);
            return true;
        }

        commandBuffer.add(commandOptionList);
        return false;
    }

    private void ignoreWrite(ArrayList<String> commandOptionList) {
        int startIndex = Integer.parseInt(commandOptionList.get(1));
        int endIndex = startIndex + Integer.parseInt(commandOptionList.get(2));

        for (int i = commandBuffer.size() - 1; i >= 0; i--) {
            ArrayList<String> bufferedCommandOptionList = commandBuffer.get(i);
            String bufferedCommandStr = bufferedCommandOptionList.get(0);
            int bufferedIndex = Integer.parseInt(bufferedCommandOptionList.get(1));

            if (bufferedCommandStr.equals("W") && startIndex <= bufferedIndex && bufferedIndex < endIndex) {
                commandBuffer.remove(i);
            }
        }
    }

}
