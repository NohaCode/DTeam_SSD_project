package command.ssd;

import java.util.ArrayList;
import java.util.Arrays;

public class SSDFlushCommand implements SSDCommand{

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

            if(!bufferCommand.isValidCommand(bufferedCommandOptionList))
                continue;

            bufferCommand.run(bufferedCommandOptionList);
        }
        commandBuffer.clear();
    }

    private boolean isValidListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}
