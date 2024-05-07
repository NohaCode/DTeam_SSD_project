import java.util.ArrayList;

public interface ShellCommand {
    boolean isValidCommand(ArrayList<String> commandOptionList);
    void run(SSD ssd, ArrayList<String> commandOptionList);
}
