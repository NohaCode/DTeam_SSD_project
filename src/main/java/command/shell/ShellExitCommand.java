package command.shell;

import app.SSD;

import java.util.ArrayList;

public class ShellExitCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        System.exit(0);
    }
}
