import java.util.ArrayList;

public class SSDEraseCommand implements SSDCommand{

    private static final Integer POS_INDEX = 1;
    private static final Integer SIZE_INDEX = 2;

    FileHandler fileHandler;

    public SSDEraseCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (isInvalidLengthParameter(commandOptionList)) {
            return false;
        }

        if(isInvalidIntegerParameter(commandOptionList, POS_INDEX)) {
            return false;
        }

        if(isInvalidIntegerParameter(commandOptionList, SIZE_INDEX)){
            return false;
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        int size = Integer.parseInt(commandOptionList.get(SIZE_INDEX));

        if (isIncorrectIndex(pos)) {
            return false;
        }

        if (isIncorrectSize(size)) {
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
            fileHandler.writeNAND(index, SSD.DEFAULT_VALUE);
        }
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList == null || commandOptionList.size() != 3;
    }

    private boolean isInvalidIntegerParameter(ArrayList<String> commandOptionList, int index){
        try{
            Integer.parseInt(commandOptionList.get(index));
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }

    private boolean isIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    private boolean isIncorrectSize(int size) {
        return size < 1 || size > 10;
    }

}
