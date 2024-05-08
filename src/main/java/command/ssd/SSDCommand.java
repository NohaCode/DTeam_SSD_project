package command.ssd;

import java.util.ArrayList;

public interface SSDCommand {
    boolean isValidCommand(ArrayList<String> commandOptionList);
    void run(ArrayList<String> commandOptionList);
}
