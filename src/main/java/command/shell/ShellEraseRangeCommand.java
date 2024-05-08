package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellEraseRangeCommand implements ShellCommand{
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)){
            return false;
        }
        if(!isValidIndex(commandOptionList)){
            return false;
        }

        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        int start = Integer.parseInt(commandOptionList.get(1));
        int end = Integer.parseInt(commandOptionList.get(2));

        while(end - start > 10) {
            ssd.run("E " + String.valueOf(start) + " 10");
            start += 10;
        }
        ssd.run("E " + String.valueOf(start) + " " + String.valueOf(end - start));

    }

    private boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 3;
    }

    private boolean isValidIndex(ArrayList<String> commandOptionList) {
        int valueStart = Integer.parseInt(commandOptionList.get(1));
        int valueEnd =  Integer.parseInt(commandOptionList.get(2));
        return valueStart >= 0 && valueStart <= 99 && valueEnd >= 0 && valueEnd <= 99;
    }
}
