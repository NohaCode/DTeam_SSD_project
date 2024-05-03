import static com.sun.org.apache.xml.internal.security.utils.XMLUtils.getBytes;

public class Shell {
    public static final String REGEX = "^0x[0-9A-F]{8}$";
    private SSD ssd;

    public Shell(SSD ssd) {
        this.ssd = ssd;
    }

    public void write(int index, String value) {
        if (isIncorrectValue(value)) {
            printValueError();
            return;
        }
        if (IsIncorrectIndex(index)) {
            printIndexError();
            return;
        }
        ssd.write(index, value);
    }

    public void printValueError() {
        System.out.println("10자리 16진수만 입력 가능합니다.");
    }

    public void printIndexError() {
        System.out.println("LBA 범위는 0 ~ 99 입니다.");
    }

    private static boolean isIncorrectValue(String value) {
        return value == null || value.isEmpty() || !value.matches(REGEX);
    }

    private static boolean IsIncorrectIndex(int index) {
        return index < 0 || index > 99;
    }

    public String read(int index) {
        if(!(0 <= index && index <= 99))
            return "Invalid Address";
        return ssd.read(index);
    }

    public String listen(int index) {
        return invalidCommand();
    }

    private String invalidCommand() {
        return "INVALID COMMAND";
    }

}
