public class SSD {
    public static final String REGEX = "^0x[0-9A-F]{8}$";

    public SSD() {}

    public void write(int index, String value){
        if (IsIncorrectIndex(index) || isIncorrectValue(value)) {
            printError();
            return;
        }
    }

    private static boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(REGEX);
    }

    private static boolean IsIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    public String read(int index){
        if(!(0 <= index && index <= 99))
            return "Invalid Address";
        return "0x00000000";
    }

    public static void main(String[] args) {

    }

    public boolean isValidFile(String file) {
        return true;
    }

    public void printError() {

    }
}