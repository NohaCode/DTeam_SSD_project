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

    public byte read(int index){
        return 0;
    }

    public void printError() {

    }
}
