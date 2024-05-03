package command;

public interface Command {
    String RESOURCES_PATH = "src/main/resources/";
    String RESULT_FILE = "result.txt";
    String NAND_FILE = "nand.txt";
    boolean isValidInput();
    void run();
}
