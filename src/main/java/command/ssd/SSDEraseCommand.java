package command.ssd;

import util.FileHandler;
import util.CommandValidation;

import java.util.ArrayList;

import static util.FileHandler.DEFAULT_VALUE;

public class SSDEraseCommand implements SSDCommand{

    private static final Integer POS_INDEX = 1;
    private static final Integer SIZE_INDEX = 2;
    private static final Integer COMMAND_OPTION_LIST_LENGTH = 3;

    FileHandler fileHandler;

    public SSDEraseCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!CommandValidation.isValidLengthParameter(commandOptionList, COMMAND_OPTION_LIST_LENGTH)) {return false;}

        if(!CommandValidation.isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}
        if(!CommandValidation.isValidIntegerParameter(commandOptionList, SIZE_INDEX)){return false;}

        if(!CommandValidation.isValidIndex(commandOptionList, POS_INDEX)) {return false;}
        if(!CommandValidation.isValidEraseSize(commandOptionList, SIZE_INDEX)) {return false;}

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        int size = Integer.parseInt(commandOptionList.get(SIZE_INDEX));

        for(int index = pos; index < pos + size; index++){
            if(index > 99) break;
            fileHandler.writeNAND(index, DEFAULT_VALUE);
        }
    }
}
