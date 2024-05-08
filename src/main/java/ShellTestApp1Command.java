import java.util.ArrayList;

public class ShellTestApp1Command implements ShellCommand {

    private FileHandler fileHandler = FileHandler.get();

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        if (!isValidCommandOptionListSize(commandOptionList)) {
            return false;
        }
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        String writeValue = "0xABCDFFFF";
        String resultValue = "";
        for (int addreess = 0; addreess < 100; addreess++) {
            ssd.run(SSD.WRITE_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + addreess + SSD.COMMAND_SEPARATOR + writeValue);
        }
        for (int i = 0; i < 100; i++) {
            ssd.run(SSD.READ_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + i);
            resultValue = fileHandler.readRESULT(i);
            if (!writeValue.equals(resultValue)) {
                throw new ShellException();
            }
        }
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}
