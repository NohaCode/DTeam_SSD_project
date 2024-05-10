import app.SSD;
import command.shell.ShellCommand;
import exception.ShellException;
import util.FileHandler;

import java.util.ArrayList;

public class testapp3 extends ShellCommand {

    private FileHandler fileHandler = FileHandler.get();

    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidCommandOptionListSize(commandOptionList)) {
            return false;
        }
        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        String firstWriteValue = "0xAAAABBBB";
        String overrideValue = "0x12345678";
        int testMaxIndex = 6;
        int testRewriteCount = 30;
        String resultValue = "";
        for (int i = 0; i < testRewriteCount; i++) {
            for (int j = 0; j < testMaxIndex; j++) {
                ssd.run(SSD.WRITE_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + j + SSD.COMMAND_SEPARATOR + firstWriteValue);
            }
        }
        for (int i = 0; i < testMaxIndex; i++) {
            ssd.run(SSD.WRITE_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + i + SSD.COMMAND_SEPARATOR + overrideValue);
        }
        for (int i = 0; i < testMaxIndex; i++) {
            ssd.run(SSD.READ_COMMAND_SHORTCUT + SSD.COMMAND_SEPARATOR + i);
            resultValue = fileHandler.readRESULT(i);
            if (!overrideValue.equals(resultValue)) {
                throw new ShellException();
            }
        }
//        if (false) {
//            throw new exception.ShellException();
//        }
    }

    public boolean isValidCommandOptionListSize(ArrayList<String> commandOptionList) {
        return commandOptionList.size() == 1;
    }
}
