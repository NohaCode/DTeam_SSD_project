package app;

import command.ssd.SSDCommand;
import command.ssd.SSDCommandFactory;
import exception.SSDException;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class SSD {
    public static final String INVALID_COMMAND_MESSAGE = "INVALID COMMAND";

    public static final String READ_COMMAND_SHORTCUT = "R";
    public static final String WRITE_COMMAND_SHORTCUT = "W";
    public static final String ERASE_COMMAND_SHORTCUT = "E";
    public static final String COMMAND_SEPARATOR = " ";

    private final FileHandler fileHandler = FileHandler.get();

    public SSD() {
        fileHandler.initFile();
    }

    public static void main(String[] args) {
//        makeFile();
//        String data = fileRead(NAND_FILE_PATH);
//        writeResult("TESTTEST");
    }

    public void run(String commandLine) {
        if (isInvalidCommandLine(commandLine)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
        String commandStr = commandOptionList.get(0);

        SSDCommand command = SSDCommandFactory.of(commandStr);

        if (isInvalidCommand(command)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        if (!command.isValidCommand(commandOptionList)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        command.run(commandOptionList);
    }

    private static boolean isInvalidCommand(SSDCommand command) {
        return command == null;
    }

    private static boolean isInvalidCommandLine(String commandLine) {
        return commandLine == null || commandLine.trim().isEmpty();
    }
}