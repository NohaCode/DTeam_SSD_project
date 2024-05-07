import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

public class SSD {
    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";
    public static final String DEFAULT_VALUE = "0x00000000";

    public static final String INVALID_INDEX_MESSAGE = "Invalid Address";
    public static final String INVALID_VALUE_MESSAGE = "Invalid Value";

    private FileHandler fileHandler = new FileHandler();

    public SSD() {}

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(NAND_FILE_PATH);
//        writeResult("TESTTEST");
    }

    public String run(String fullCommandArgument) {
        String[] fullCommandArgumentArr = fullCommandArgument.split(" ");
        String command = fullCommandArgumentArr[0];

        if (command == null || command.isEmpty()) {
            return null;
        }

        try {
            switch (command) {
                case "write":
                    write(Integer.parseInt(fullCommandArgumentArr[1]), fullCommandArgumentArr[2]);
                    break;
                case "read":
                    read(Integer.parseInt(fullCommandArgumentArr[1]));
                    break;
            }
        }catch (Exception e){
            return e.getMessage();
        }

        return null;
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(CORRECT_VALUE_REGEX);
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