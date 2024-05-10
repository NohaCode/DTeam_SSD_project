package command.ssd;

import java.util.ArrayList;
import java.util.Arrays;

public class SSDFlushCommand extends SSDCommand{

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidListSize(commandOptionList))
            return false;

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        for(ArrayList<String> bufferedCommandOptionList: commandBuffer){
            String bufferedCommandStr = bufferedCommandOptionList.get(0);
            SSDCommand bufferCommand = SSDCommandFactory.of(bufferedCommandStr);

            bufferCommand.run(bufferedCommandOptionList);
        }
        commandBuffer.clear();
    }

    @Override
    boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList) {
        return false;
    }

    private boolean isValidListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}
