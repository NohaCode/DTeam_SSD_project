import java.io.*;

public class SSD {
    public static final String REGEX = "^0x[0-9A-F]{8}$";
    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String NAND_FILE = "nand.txt";
    public static final String RESULT_FILE = "result.txt";
    public static final String DEFAULT_VALUE = "0x00000000";

    public SSD() {
    }

    public void write(int index, String value) {
        if (IsIncorrectIndex(index) || isIncorrectValue(value)) {
            printError();
            return;
        }
    }

    private boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(REGEX);
    }

    private boolean IsIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    public String read(int index) {
        if (!(0 <= index && index <= 99))
            return "Invalid Address";

        makeFile();
        String data = fileRead(RESOURCES_PATH + NAND_FILE);
        fileWrite(RESOURCES_PATH + RESULT_FILE, data);

        return readNand(index);
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
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                return DEFAULT_VALUE;
            }
            return sb.toString();
        }
    }

    private String readNand(int index) {
        return DEFAULT_VALUE;
    }

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(RESOURCES_PATH + NAND_FILE);
//        fileWrite(RESOURCES_PATH + RESULT_FILE, "TESTTEST");
    }

    public boolean isValidFile(String file) {
        return true;
    }

    public void printError() {

    }

    private void makeFile() {
        File resultFile = new File(RESOURCES_PATH + RESULT_FILE);
        File nandFile = new File(RESOURCES_PATH + NAND_FILE);

        try {
            if (!resultFile.exists()) resultFile.createNewFile();
            if (!nandFile.exists()) nandFile.createNewFile();
        } catch (Exception ignored) {
        }
    }
}