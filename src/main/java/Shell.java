
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class Shell {
    public static final String REGEX = "^0x[0-9A-F]{8}$";
    private SSD ssd;
    private FileHandler fileHandler = new FileHandler();

    public Shell(SSD ssd) {
        this.ssd = ssd;
    }

    public static void main(String[] args) {
        Shell shell = new Shell(new SSD());
        Scanner scanner = new Scanner(System.in);
        String commandLine;
        while (true) {
            commandLine = scanner.nextLine();
            try {
                shell.run(commandLine);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run(String commandLine) throws Exception {
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
                exit();
                break;
            case "help":
                help();
                break;
            case "fullread":
                processFullReadCommand(tokens);
                break;
            case "fullwrite":
                processFullWriteCommand(tokens);
                break;
            default:
                System.out.println("INVALID COMMAND");
        }
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

    public String listen(int index) {
        return invalidCommand();
    }

    private String invalidCommand() {
        return "INVALID COMMAND";
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        String helpContext = fileHandler.fileRead(FileHandler.RESOURCES_PATH + "help.txt");
        System.out.println(helpContext);
    }


    public void processWriteCommand(String[] tokens) {
        if (tokens.length != 3 || !isValidInt(Integer.parseInt(tokens[1]))) {
            System.out.println("INVALID COMMAND");
            return;
        }
        try {
            int intValue = Integer.parseInt(tokens[1]);
            if (isValidHex(tokens[2])) {
                ssd.run("W " + tokens[1] + " "+ tokens[2]);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } catch (NumberFormatException e) {
            System.out.println("INVALID COMMAND");
        }
    }

    public void processReadCommand(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("INVALID COMMAND");
            return;
        }
        try {
            int intValue = Integer.parseInt(tokens[1]);
            if (isValidInt(intValue)) {
                ssd.run("R "+intValue);

                /* result.txt에서 읽어서 출력 */
                System.out.println(fileHandler.readRESULT(intValue));
            } else {
                System.out.println("INVALID COMMAND");
            }
        } catch (NumberFormatException e) {
            System.out.println("INVALID COMMAND");
        }
    }

    public void processFullReadCommand(String[] tokens) {
        if (tokens.length != 1) {
            System.out.println("INVALID COMMAND");
            return;
        }

        for (int i = 0; i < 100; i++) {
            ssd.run("R "+i);
            /* result.txt 읽어서 출력*/
            System.out.println("R "+i);
            System.out.println(fileHandler.readRESULT(i));
        }
    }

    public void processFullWriteCommand(String[] tokens) throws Exception {
        if (tokens.length != 2 || !isValidHex(tokens[1])) {
            System.out.println("INVALID COMMAND");
            return;
        }

        for (int addreess = 0; addreess < 100; addreess++) {
            ssd.run("W "+addreess+" "+tokens[1]);
        }
    }

    private boolean isValidInt(int value) {
        return value >= 0 && value <= 99;
    }

    private boolean isValidHex(String hex) {
        return hex.matches("0x[0-9A-Fa-f]{8}");
    }

}
