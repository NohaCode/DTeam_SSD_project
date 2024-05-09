package command.shell;

import java.util.ArrayList;
import app.SSD;
import exception.SSDException;
import util.Logger;

import static app.SSD.INVALID_COMMAND_MESSAGE;

public abstract class ShellCommand {
    Logger logger = Logger.get(Logger.PRODUCTION);
    abstract boolean isValidCommandImpl(ArrayList<String> commandOptionList);
    abstract void runImpl(SSD ssd, ArrayList<String> commandOptionList);

    public boolean isValidCommand(ArrayList<String> commandOptionList){
        logger.log("ShellCommand - Validation Start", this.getClass());
        return isValidCommandImpl(commandOptionList);
    }

    public void run(SSD ssd, ArrayList<String> commandOptionList){
        logger.log("ShellCommand - Run Start", this.getClass());
        runImpl(ssd, commandOptionList);
    }

    public void process(SSD ssd, ArrayList<String> commandOptionList){
        if(!isValidCommand(commandOptionList)){
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        run(ssd, commandOptionList);
    }
}
