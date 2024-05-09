package command.shell;

import java.util.ArrayList;
import app.SSD;

public class ShellEraseCommand implements ShellCommand{
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)) {return false;}

        if(!isValidIntegerParameter(commandOptionList, 1)) {return false;}
        if(!isValidIntegerParameter(commandOptionList, 2)) {return false;}

        if(!isValidIndex(commandOptionList)) {return false;}

        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        String index = commandOptionList.get(1);
        String size = commandOptionList.get(2);
        ssd.run("E " + index + " " + size);
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
