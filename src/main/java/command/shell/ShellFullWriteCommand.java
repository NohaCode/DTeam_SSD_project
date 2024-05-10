package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellFullWriteCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList))
            return false;
        if(!isValidHexData(commandOptionList))
            return false;

        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        for (int addreess = 0; addreess < 100; addreess++) {
            ssd.run("W " + addreess + " " + commandOptionList.get(1));
        }
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 2;
    }

    private boolean isValidHexData(ArrayList<String> commandOptionList) {
        String data = commandOptionList.get(1);
        return data.matches("0x[0-9A-Fa-f]{8}");
    }
}
