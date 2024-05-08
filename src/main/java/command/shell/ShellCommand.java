package command.shell;

import java.util.ArrayList;
import app.SSD;

public interface ShellCommand {
    boolean isValidCommand(ArrayList<String> commandOptionList);
    void run(SSD ssd, ArrayList<String> commandOptionList);
}
