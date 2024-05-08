package command.ssd;

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
        if(!isValidLengthParameter(commandOptionList)) {return false;}

        if(!isValidIntegerParameter(commandOptionList, POS_INDEX)) {return false;}

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        if(!isValidIndex(pos)) {return false;}

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        fileHandler.makeFile();

        int index = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(index, data);
    }

    private boolean isValidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 2;
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
}
