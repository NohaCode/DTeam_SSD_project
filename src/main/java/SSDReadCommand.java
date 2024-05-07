import java.util.ArrayList;

public class SSDReadCommand implements Command {
    private static final Integer POS_INDEX = 1;
    FileHandler fileHandler;

    public SSDReadCommand(){
        fileHandler = FileHandler.get();
    }

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));

        if (isIncorrectIndex(pos))
            throw new SSDException(SSD.INVALID_INDEX_MESSAGE);

        if (isInvalidLengthParameter(commandOptionList)) {
            throw new SSDException(SSD.INVALID_LENGTH_PARAMETER_MESSAGE);
        }

        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        if(!isValidCommand(commandOptionList))
            return;

        fileHandler.makeFile();

        int index = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(data);
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList == null || commandOptionList.size() != 2;
    }

    private boolean isIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }
}
