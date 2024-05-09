package command.ssd;

import util.CommandValidation;
import util.FileHandler;

import java.util.ArrayList;

public class SSDReadCommand implements SSDCommand {
    private static final Integer POS_INDEX = 1;
    FileHandler fileHandler;

    public SSDReadCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, 2)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        fileHandler.makeFile();

        int index = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(index, data);
    }
}
