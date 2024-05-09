package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

import static util.CommandValidation.*;

public class ShellScenarioDeleteAllCommand extends ShellCommand {

    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList, 1)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        FileHandler fileHandler = FileHandler.get();
        fileHandler.writeScenario("");
    }
}
