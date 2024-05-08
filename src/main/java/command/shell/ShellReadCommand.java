package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellReadCommand implements ShellCommand {
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)) {return false;}

        if(!isValidIntegerParameter(commandOptionList, 1)) {return false;}

        if(!isValidIndex(commandOptionList)) { return false;}

        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        ssd.run("R " + commandOptionList.get(1));
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 2;
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
