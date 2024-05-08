package command.ssd;

import util.FileHandler;
import java.util.ArrayList;

public interface SSDCommand {
    FileHandler fileHandler = FileHandler.get();
    ArrayList<ArrayList<String>> commandBuffer = SSDCommandBufferFactory.get();

    boolean isValidCommand(ArrayList<String> commandOptionList);
    void run(ArrayList<String> commandOptionList);
}
