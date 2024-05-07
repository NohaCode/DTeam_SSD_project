import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class FileHandler {

    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String NAND_FILE = "nand.txt";
    public static final String RESULT_FILE = "result.txt";
    public static final String RESULT_FILE_PATH = RESOURCES_PATH + RESULT_FILE;
    public static final String NAND_FILE_PATH = RESOURCES_PATH + NAND_FILE;

    private static FileHandler fileHandler;

    public static FileHandler get(){
        if(fileHandler == null){
            fileHandler = new FileHandler();
        }
        return fileHandler;
    }

    public void fileWrite(String filePath, String data) {
        File file = new File(filePath);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
        }
    }

    public String fileRead(String filePath) {
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
                return SSD.DEFAULT_VALUE;
            }
            return sb.toString();
        }
    }

    public void makeFile() {
        checkResultFile();
        checkNANDFile();
    }

    public boolean checkResultFile() {
        File resultFile = new File(RESULT_FILE_PATH);
        try {
            if (!resultFile.exists()) resultFile.createNewFile();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void initFile() {
        File nandFile = new File(NAND_FILE_PATH);
        File resultFile = new File(RESULT_FILE_PATH);
        try {
            Files.deleteIfExists(nandFile.toPath());
            Files.deleteIfExists(resultFile.toPath());
            makeFile();
        } catch (Exception ignored) {
        }
    }

    public boolean checkNANDFile() {
        File nandFile = new File(NAND_FILE_PATH);
        try {
            if (!nandFile.exists()) nandFile.createNewFile();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String readNAND(int index) {
        JSONObject jsonObject = getJSONFromNANDFile();
        String jsonIndex = "" + index;
        if (jsonObject != null && jsonObject.has(jsonIndex)) {
            return (String) jsonObject.get(jsonIndex);
        }
        return SSD.DEFAULT_VALUE;
    }

    public void writeNAND(int index, String data) {
        JSONObject jsonObject = getJSONFromNANDFile();
        String jsonIndex = "" + index;
        if (jsonObject != null) {
            jsonObject.put(jsonIndex, data);
            fileWrite(NAND_FILE_PATH, jsonObject.toString());
        }
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

    public void writeResult(String data) {
        fileWrite(RESULT_FILE_PATH, data);
    }
}
