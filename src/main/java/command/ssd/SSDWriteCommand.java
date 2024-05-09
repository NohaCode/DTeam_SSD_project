package command.ssd;

import util.CommandValidation;
import util.FileHandler;

import java.util.ArrayList;

public class SSDWriteCommand implements SSDCommand {
    private static final Integer POS_INDEX = 1;
    private static final Integer VALUE_INDEX = 2;


    FileHandler fileHandler;

    public SSDWriteCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, 3)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}

        if(!CommandValidation.isValidValue(commandOptionList, VALUE_INDEX)) {return false;}
        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String value = commandOptionList.get(VALUE_INDEX);

        fileHandler.makeFile();
        fileHandler.writeNAND(pos, value);
    }

}
