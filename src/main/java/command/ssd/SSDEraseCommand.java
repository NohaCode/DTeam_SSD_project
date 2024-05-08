package command.ssd;

import util.FileHandler;

import java.util.ArrayList;

import static util.FileHandler.DEFAULT_VALUE;

public class SSDEraseCommand implements SSDCommand{

    private static final Integer POS_INDEX = 1;
    private static final Integer SIZE_INDEX = 2;

    FileHandler fileHandler;

    public SSDEraseCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidLengthParameter(commandOptionList)) {
            return false;
        }

        if(!isValidIntegerParameter(commandOptionList, POS_INDEX)) {
            return false;
        }

        if(!isValidIntegerParameter(commandOptionList, SIZE_INDEX)){
            return false;
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        int size = Integer.parseInt(commandOptionList.get(SIZE_INDEX));

        if (!isValidIndex(pos)) {
            return false;
        }

        if (!isValidSize(size)) {
            return false;
        }

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

    private boolean isValidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 3;
    }

    private boolean isValidIntegerParameter(ArrayList<String> commandOptionList, int index){
        try{
            Integer.parseInt(commandOptionList.get(index));
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isValidIndex(int index) { return index >= 0 && index <= 99; }

    private boolean isValidSize(int size) { return size >= 1 && size <= 10; }

}
