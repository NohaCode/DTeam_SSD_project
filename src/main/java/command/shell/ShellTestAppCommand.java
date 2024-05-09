package command.shell;

import app.SSD;
import command.scenario.Scenario;
import exception.ShellException;
import util.CommandValidation;

import java.util.ArrayList;

import static util.CommandValidation.*;

public class ShellTestAppCommand extends ShellCommand {

    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList,1)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        String name = commandOptionList.get(0);
        try {
            Scenario.runScenario(name, ssd, commandOptionList);
        } catch (Exception e) {
            throw new ShellException();
        }
    }
}
