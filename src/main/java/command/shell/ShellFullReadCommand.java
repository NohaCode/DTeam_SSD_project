package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellFullReadCommand implements ShellCommand{
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList))
            return false;

        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        for (int i = 0; i < 100; i++) {
            ssd.run("R " + i);
        }
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}

