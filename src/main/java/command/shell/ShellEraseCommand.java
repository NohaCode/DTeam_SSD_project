package command.shell;

import java.util.ArrayList;
import app.SSD;

public class ShellEraseCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)) {return false;}

        if(!isValidIntegerParameter(commandOptionList, 1)) {return false;}
        if(!isValidIntegerParameter(commandOptionList, 2)) {return false;}

        if(!isValidIndex(commandOptionList)) {return false;}

        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        int start = Integer.parseInt(commandOptionList.get(1));
        int end = start + Integer.parseInt(commandOptionList.get(2));

        while(end - start > 10) {
            ssd.run("E " + String.valueOf(start) + " 10");
            start += 10;
        }
        ssd.run("E " + String.valueOf(start) + " " + String.valueOf(end - start));

    }

    private boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 3;
    }

    private boolean isValidIntegerParameter(ArrayList<String> commandOptionList, int index){
        try{
            Integer.parseInt(commandOptionList.get(index));
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isValidIndex(ArrayList<String> commandOptionList) {
        int value = Integer.parseInt(commandOptionList.get(1));
        return value >= 0 && value <= 99;
    }
}
