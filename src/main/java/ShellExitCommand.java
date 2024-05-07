import java.util.ArrayList;

public class ShellExitCommand implements ShellCommand{
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        System.exit(0);
    }
}
