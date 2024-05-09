package app;

import command.ssd.SSDCommand;
import command.ssd.SSDCommandBuffer;
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
    private final ArrayList<ArrayList<String>> commandBuffer = SSDCommandBuffer.get();

    public SSD() {}

    public static void main(String[] args) {
    }

    public void run(String commandLine) {
        if (isInvalidCommandLine(commandLine)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        ArrayList<String> commandOptionList = getCommandOptionList(commandLine);
        String commandStr = commandOptionList.get(0);
        SSDCommand command = SSDCommandFactory.of(commandStr);

        command.process(commandOptionList);
    }

    private boolean isInvalidCommandLine(String commandLine) {
        if(commandLine == null || commandLine.trim().isEmpty())
            return true;

        ArrayList<String> commandOptionList = getCommandOptionList(commandLine);
        String commandStr = commandOptionList.get(0);
        if(!commandStr.equals("R") &&
                !commandStr.equals("W") &&
                !commandStr.equals("E") &&
                !commandStr.equals("F"))
            return true;

        return false;
    }

    private ArrayList<String> getCommandOptionList(String commandLine) {
        return new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
    }
}