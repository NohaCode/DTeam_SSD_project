package command.shell;

import java.util.ArrayList;
import app.SSD;
import exception.SSDException;
import util.Logger;

import static app.SSD.INVALID_COMMAND_MESSAGE;

public abstract class ShellCommand {
    Logger logger = Logger.get(Logger.PRODUCTION);
    protected abstract boolean isValidCommandImpl(ArrayList<String> commandOptionList);
    protected abstract void runImpl(SSD ssd, ArrayList<String> commandOptionList);

    public boolean isValidCommand(ArrayList<String> commandOptionList){
        logger.log("validation 시작", new Object() {}.getClass());
        return isValidCommandImpl(commandOptionList);
    }

    public void run(SSD ssd, ArrayList<String> commandOptionList){
        logger.log("run 시작", new Object() {}.getClass());
        runImpl(ssd, commandOptionList);
    }

    public void process(SSD ssd, ArrayList<String> commandOptionList){
        if(!isValidCommand(commandOptionList)){
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        run(ssd, commandOptionList);
    }
}
