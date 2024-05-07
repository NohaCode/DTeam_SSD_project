import java.util.ArrayList;
import java.util.Arrays;

public class SSD {
    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";
    public static final String DEFAULT_VALUE = "0x00000000";

    public static final String INVALID_INDEX_MESSAGE = "INVALID ADDRESS";
    public static final String INVALID_VALUE_MESSAGE = "INVALID VALUE";
    public static final String INVALID_COMMAND_MESSAGE = "INVALID COMMAND";
    public static final String INVALID_LENGTH_PARAMETER_MESSAGE = "INVALID LENGTH PARAMETER";

    public static final String READ_COMMAND_SHORTCUT = "R";
    public static final String WRITE_COMMAND_SHORTCUT = "W";
    public static final String COMMAND_SEPARATOR = " ";

    private final FileHandler fileHandler = new FileHandler();

    public SSD() {
        fileHandler.initFile();
    }

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(NAND_FILE_PATH);
//        writeResult("TESTTEST");
    }

    public void run(String commandLine) {
        if (isValidCommandLine(commandLine)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
        String commandStr = commandOptionList.get(0);

        SSDCommand command = SSDCommandFactory.of(commandStr);
        if (!command.isValidCommand(commandOptionList)) {
            return;
        }

        command.run(commandOptionList);
    }

    private static boolean isValidCommandLine(String commandLine) {
        return commandLine == null || commandLine.trim().isEmpty();
    }
}