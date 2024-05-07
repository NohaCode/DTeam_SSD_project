import java.io.*;

public class FileHandler {

    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String NAND_FILE = "nand.txt";
    public static final String RESULT_FILE = "result.txt";
    public static final String RESULT_FILE_PATH = RESOURCES_PATH + RESULT_FILE;
    public static final String NAND_FILE_PATH = RESOURCES_PATH + NAND_FILE;

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
