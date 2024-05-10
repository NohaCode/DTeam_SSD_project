package command.shell;

import java.util.ArrayList;
import java.util.stream.Collectors;

import app.SSD;
import exception.SSDException;
import util.Logger;

import static app.SSD.INVALID_COMMAND_MESSAGE;

public abstract class ShellCommand {
    protected abstract boolean isValidCommandImpl(ArrayList<String> commandOptionList);
    protected abstract void runImpl(SSD ssd, ArrayList<String> commandOptionList);

    static Logger logger = Logger.get(Logger.PRODUCTION);

    public void setLogger(Logger logger){
        ShellCommand.logger = logger;
    }

    public boolean isValidCommand(ArrayList<String> commandOptionList){
        logger.log(makeCommandMessage(commandOptionList), this.getClass());
        return isValidCommandImpl(commandOptionList);
    }

    public void run(SSD ssd, ArrayList<String> commandOptionList){
        logger.log(makeCommandMessage(commandOptionList), this.getClass());
        runImpl(ssd, commandOptionList);
    }

    public void process(SSD ssd, ArrayList<String> commandOptionList){
        if(!isValidCommand(commandOptionList)){
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        run(ssd, commandOptionList);
    }

    private String makeCommandMessage(ArrayList<String> commandOptionList){
        return commandOptionList.stream().collect(Collectors.joining(" "));
    }
}
