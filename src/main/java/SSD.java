public class SSD {

    public SSD() {}

    public void write(int index, String value){
        String regex = "^0x[0-9A-F]{8}$";
        if (index < 0 || index > 99) {
            printError();
            return;
        }
        if (value == null || value.isEmpty()) {
            printError();
            return;
        }
        if (!value.matches(regex)) {
            printError();
            return;
        }
    }

    public byte read(int index){
        return 0;
    }

    public void printError() {

    }
}
