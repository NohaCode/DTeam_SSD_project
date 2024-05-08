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
        if (isInvalidLengthParameter(commandOptionList)) {
            throw new SSDException(SSD.INVALID_LENGTH_PARAMETER_MESSAGE);
        }

        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        if (isIncorrectIndex(pos)) {
            throw new SSDException(SSD.INVALID_INDEX_MESSAGE);
        }

        String value = commandOptionList.get(VALUE_INDEX);
        if (isIncorrectValue(value)) {
            throw new SSDException(SSD.INVALID_VALUE_MESSAGE);
        }
        return true;
    }

    @Override
    public void run(ArrayList<String> commandOptionList) {
        int pos = Integer.parseInt(commandOptionList.get(POS_INDEX));
        String value = commandOptionList.get(VALUE_INDEX);

        fileHandler.makeFile();
        fileHandler.writeNAND(pos, value);
    }

    private boolean isInvalidLengthParameter(ArrayList<String> commandOptionList) {
        return commandOptionList.size() != 3;
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(SSD.CORRECT_VALUE_REGEX);
    }

    private boolean isIncorrectIndex(int pos) {
        return pos < 0 || pos > 99;
    }
}
