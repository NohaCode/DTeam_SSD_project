package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellFlushCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)) {return false;}

        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        ssd.run("F");
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}
