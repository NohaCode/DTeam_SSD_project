
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;


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

    public void exit() {
    }

    public void help() {
        try {
            ClassLoader classLoader = Shell.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("help.txt")).getFile());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void fullwrite(String value) {
        if(isIncorrectValue(value)){
            System.out.println("10자리 16진수만 입력 가능합니다.");
            return;
        }


        for (int addreess = 0; addreess < 100; addreess++) {
            try{
                ssd.write(addreess, value);
            }catch (Exception e){
                System.out.println("INVALID COMMAND");
            }
        }
    }

    void fullread() {
        makeReadFileBySSD();
        printResult(readFile());
    }

    private void makeReadFileBySSD() {
        for (int addreess = 0; addreess < 100; addreess++) {
            ssd.read(addreess);
        }
    }

    private List<String> readFile() {
        return new ArrayList<>();
    }

    void printResult(List<String> result) {
        for (String s : result) {
            System.out.println(s);
        }
    }

    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        while(scanner.hasNextLine()){
            String commandLine = scanner.nextLine();
            String[] tokens = commandLine.split(" ");

            String cmd = tokens[0];

            switch (cmd) {
                case "write":
                    processWriteCommand(tokens);
                    break;
                case "read":
                    processReadCommand(tokens);
                    break;
                case "exit":
                    System.exit(0);
                case "help":
                    help();
                    break;
                case "fullread":
                    fullread();
                    System.out.println("fullread 실행");
                    break;
                case "fullwrite":
                    processFullWriteCommand(tokens);
                    break;
                default:
                    System.out.println("INVALID COMMAND");
            }
        }
    }
    private void processWriteCommand(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("INVALID COMMAND");
            return;
        }
        try {
            int intValue = Integer.parseInt(tokens[1]);
            if (isValidHex(tokens[2])) {
                ssd.write(intValue, tokens[2]);
                System.out.println("write: 정수=" + intValue + ", 16진수=" + tokens[2]);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } catch (NumberFormatException e) {
            System.out.println("INVALID COMMAND");
        }
    }

    private void processReadCommand(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("INVALID COMMAND");
            return;
        }
        try {
            int intValue = Integer.parseInt(tokens[1]);
            if (isValidInt(intValue)) {
                ssd.read(intValue);
                System.out.println("read: 정수=" + intValue);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } catch (NumberFormatException e) {
            System.out.println("INVALID COMMAND");
        }
    }

    private void processFullWriteCommand(String[] tokens) throws Exception {
        if (tokens.length != 2 || !isValidHex(tokens[1])) {
            System.out.println("INVALID COMMAND");
            return;
        }
        fullwrite(tokens[1]);
        System.out.println("fullwrite: 16진수=" + tokens[1]);
    }

    private boolean isValidInt(int value) {
        return value >= 0 && value <= 99;
    }

    private boolean isValidHex(String hex) {
        return hex.matches("0x[0-9A-Fa-f]{8}");
    }

}
