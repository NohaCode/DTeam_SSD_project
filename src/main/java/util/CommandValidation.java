package util;

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
}
