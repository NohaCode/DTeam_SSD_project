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

    public String run(String fullCommandArgument){
        String[] fullCommandArgumentArr = fullCommandArgument.split(" ");
        String command = fullCommandArgumentArr[0];

        if(command == null || command.isEmpty()){
            return null;
        }

        switch (command) {
            case "write":
                write(Integer.parseInt(fullCommandArgumentArr[1]), fullCommandArgumentArr[2]);
                break;
            case "read":
                read(Integer.parseInt(fullCommandArgumentArr[1]));
                break;
            case "exit":
                break;
            case "help":
                break;
            case "fullwrite":
                fullwrite(fullCommandArgumentArr[1]);
                break;
            case "fullread":
                fullread();
                break;
        }

        return null;
    }

    public String fullread(){
        return null;
    }

    public void fullwrite(String value){

    }

    public void write(int index, String value) {
        if (IsIncorrectIndex(index)) {
            throw new SSDException(INVALID_INDEX_MESSAGE);
        }
        if (isIncorrectValue(value)) {
            throw new SSDException(INVALID_VALUE_MESSAGE);
        }
        fileHandler.makeFile();
        writeNAND(index, value);
    }

    public String read(int index) {
        if (IsIncorrectIndex(index))
            throw new SSDException(INVALID_INDEX_MESSAGE);

        fileHandler.makeFile();
        String data = readNAND(index);
        writeResult(data);

        return data;
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(CORRECT_VALUE_REGEX);
    }

    private boolean IsIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    private JSONObject getJSONFromNANDFile() {
        String data = fileHandler.fileRead(FileHandler.NAND_FILE_PATH);
        if (data != null) {
            if (data.isEmpty()) {
                return new JSONObject(new HashMap<>());
            } else {
                return new JSONObject(data);
            }
        }
        return null;
    }

    private String readNAND(int index) {
        JSONObject jsonObject = getJSONFromNANDFile();
        String jsonIndex = "" + index;
        if (jsonObject != null && jsonObject.has(jsonIndex)) {
            return (String) jsonObject.get(jsonIndex);
        }
        return DEFAULT_VALUE;
    }

    private void writeNAND(int index, String data) {
        JSONObject jsonObject = getJSONFromNANDFile();
        String jsonIndex = "" + index;
        if (jsonObject != null) {
            jsonObject.put(jsonIndex, data);
            fileHandler.fileWrite(FileHandler.NAND_FILE_PATH, jsonObject.toString());
        }
    }

    private void writeResult(String data) {
        fileHandler.fileWrite(FileHandler.RESULT_FILE_PATH, data);
    }

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(NAND_FILE_PATH);
//        writeResult("TESTTEST");
    }

    public boolean isValidFile(String file) {
        return true;
    }

    public void printError() {

    }
}