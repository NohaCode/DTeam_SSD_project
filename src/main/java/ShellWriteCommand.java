import java.util.ArrayList;

public class ShellWriteCommand implements ShellCommand{
    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if(!isValidCommandOptionListSize(commandOptionList)){
            return false;
        }
        if(!isValidIndex(commandOptionList)){
            return false;
        }

        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        ssd.run("W " + commandOptionList.get(1) + " " + commandOptionList.get(2));
    }

    private boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 3;
    }

    private boolean isValidIndex(ArrayList<String> commandOptionList) {
        int value = Integer.parseInt(commandOptionList.get(1));
        return value >= 0 && value <= 99;
    }
}
