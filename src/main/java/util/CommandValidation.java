package util;

import command.shell.ShellCommand;
import command.shell.ShellCommandFactory;

import java.util.ArrayList;

public class CommandValidation {

    public static final String CORRECT_VALUE_REGEX = "^0x[0-9A-F]{8}$";

    public static boolean isValidLengthParameter(ArrayList<String> commandOptionList, int length) {
        return commandOptionList.size() == length;
    }

    public static boolean isValidIntegerParameter(ArrayList<String> commandOptionList, int index){
        try{
            Integer.parseInt(commandOptionList.get(index));
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidIndex(ArrayList<String> commandOptionList, int commandIndex) {
        try{
            int index = Integer.parseInt(commandOptionList.get(commandIndex));
            return index >= 0 && index <= 99;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidValue(ArrayList<String> commandOptionList, int commandIndex) {
        String value = commandOptionList.get(commandIndex);
        return value != null && !value.isEmpty() && value.matches(CORRECT_VALUE_REGEX);
    }

    public static boolean isValidEraseSize(ArrayList<String> commandOptionList, int commandIndex) {
        try{
            int size = Integer.parseInt(commandOptionList.get(commandIndex));
            return size >= 1 && size <= 10;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isInvalidScenario(ArrayList<String> commandOptionList, int commandStartIndex) {
        ArrayList<String> newCommandOptionList = new ArrayList<>();
        for (int i = commandStartIndex; i < commandOptionList.size(); i++) {
            newCommandOptionList.add(commandOptionList.get(i));
        }
        String command = newCommandOptionList.get(0);
        ShellCommand newCommand = ShellCommandFactory.of(command);
        if (newCommand == null || !newCommand.isValidCommand(newCommandOptionList)) {
            return true;
        }
        return false;
    }
}
