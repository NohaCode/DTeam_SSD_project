package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

public class ShellScenarioDeleteAllCommand implements ShellCommand {
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidCommandOptionListSize(commandOptionList)) {
            return false;
        }
        return true;
    }

    private boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() != 1;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        FileHandler fileHandler = FileHandler.get();
        fileHandler.writeScenario("");
    }
}
