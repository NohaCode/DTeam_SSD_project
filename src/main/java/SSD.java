import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

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

    public SSD() {}

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(NAND_FILE_PATH);
//        writeResult("TESTTEST");
    }

    public void run(String fullCommandArgument) {
        if (fullCommandArgument == null || fullCommandArgument.trim().isEmpty()) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        String[] fullCommandArgumentArr = fullCommandArgument.trim().split(COMMAND_SEPARATOR);
        String command = fullCommandArgumentArr[0];

        if (command == null || command.trim().isEmpty()) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        try {
            switch (command) {
                case WRITE_COMMAND_SHORTCUT:
                    if (isInvalidLengthParameter(fullCommandArgumentArr, 3)) {
                        throw new SSDException(INVALID_LENGTH_PARAMETER_MESSAGE);
                    }
                    write(Integer.parseInt(fullCommandArgumentArr[1]), fullCommandArgumentArr[2]);
                    break;
                case READ_COMMAND_SHORTCUT:
                    if (isInvalidLengthParameter(fullCommandArgumentArr, 2)) {
                        throw new SSDException(INVALID_LENGTH_PARAMETER_MESSAGE);
                    }
                    read(Integer.parseInt(fullCommandArgumentArr[1]));
                    break;
            }
        } catch (Exception e) {
            throw new SSDException(e);
        }
    }

    private static boolean isInvalidLengthParameter(String[] fullCommandArgumentArr, int expected) {
        return fullCommandArgumentArr.length != expected;
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.trim().isEmpty() || !value.trim().matches(CORRECT_VALUE_REGEX);
    }

    private boolean IsIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    public void write(int index, String value) {
        if (IsIncorrectIndex(index)) {
            throw new SSDException(INVALID_INDEX_MESSAGE);
        }
        if (isIncorrectValue(value)) {
            throw new SSDException(INVALID_VALUE_MESSAGE);
        }
        fileHandler.makeFile();
        fileHandler.writeNAND(index, value);
    }

    public String read(int index) {
        if (IsIncorrectIndex(index))
            throw new SSDException(INVALID_INDEX_MESSAGE);

        fileHandler.makeFile();
        String data = fileHandler.readNAND(index);
        fileHandler.writeResult(data);

        return data;
    }



}