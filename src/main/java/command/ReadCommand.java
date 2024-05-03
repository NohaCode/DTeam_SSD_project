package command;

import java.io.File;

public class ReadCommand implements Command {
    @Override
    public boolean isValidInput() {
        return false;
    }

    @Override
    public void run() {
        makeFile();
//        String nandStr = readNandFile();

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
