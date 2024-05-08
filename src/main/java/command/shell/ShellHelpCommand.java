package command.shell;

import app.SSD;
import util.FileHandler;

import java.util.ArrayList;

public class ShellHelpCommand implements ShellCommand{
    FileHandler fileHandler = FileHandler.get();

    @Override
    public boolean isValidCommand(ArrayList<String> commandOptionList) {
        return true;
    }

    @Override
    public void run(SSD ssd, ArrayList<String> commandOptionList) {
        String helpContext = fileHandler.fileRead(FileHandler.RESOURCES_PATH + "help.txt");
        System.out.println(helpContext);
    }
}
