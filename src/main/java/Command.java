import java.util.ArrayList;

public interface Command {
    boolean isValidCommand(ArrayList<String> commandOptionList);
    void run(ArrayList<String> commandOptionList);
}
