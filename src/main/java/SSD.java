import org.json.JSONObject;
import org.omg.CORBA.INVALID_ACTIVITY;

import java.io.*;
import java.util.HashMap;

public class SSD {
    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";
    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String NAND_FILE = "nand.txt";
    public static final String RESULT_FILE = "result.txt";
    public static final String DEFAULT_VALUE = "0x00000000";
    public static final String RESULT_FILE_PATH = RESOURCES_PATH + RESULT_FILE;
    public static final String NAND_FILE_PATH = RESOURCES_PATH + NAND_FILE;

    public static final String INVALID_INDEX_MESSAGE = "Invalid Address";
    public static final String INVALID_VALUE_MESSAGE = "Invalid Value";

    public SSD() {
    }

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
        makeFile();
        writeNAND(index, value);
    }

    public String read(int index) {
        if (IsIncorrectIndex(index))
            throw new SSDException(INVALID_INDEX_MESSAGE);

        makeFile();
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
        String data = fileRead(NAND_FILE_PATH);
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
            fileWrite(NAND_FILE_PATH, jsonObject.toString());
        }
    }

    private void writeResult(String data) {
        fileWrite(RESULT_FILE_PATH, data);
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

    private void fileWrite(String filePath, String data) {
        File file = new File(filePath);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
        }
    }

    private String fileRead(String filePath) {
        File file = new File(filePath);
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException ignored) {
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                return DEFAULT_VALUE;
            }
            return sb.toString();
        }
    }

    private void makeFile() {
        checkResultFile();
        checkNANDFile();
    }

    private void checkResultFile() {
        File resultFile = new File(RESULT_FILE_PATH);
        try {
            if (!resultFile.exists()) resultFile.createNewFile();
        } catch (Exception ignored) {

        }
    }

    private void checkNANDFile() {
        File nandFile = new File(NAND_FILE_PATH);
        try {
            if (!nandFile.exists()) nandFile.createNewFile();
        } catch (Exception ignored) {

        }
    }
}