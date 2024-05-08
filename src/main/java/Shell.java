
import java.util.*;


public class Shell {
    private SSD ssd;
    public static final String COMMAND_SEPARATOR = " ";

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

    private void testApp1() throws Exception {
        run("fullwrite 0xABCDFFFF");
        run("fullread");
    }

    private void testApp2() throws Exception {
        for (int i = 0; i < 30 ; i++) {
            for (int j = 0; j < 6; j++) {
                run("write " + j + " 0xAAAABBBB");
            }
        }

        for (int j = 0; j < 6; j++) {
            run("write " + j + " 0x12345678");
        }

        for (int j = 0; j < 6; j++) {
            run("read " + j);
        }
    }

    public void run(String commandLine) throws Exception {
        if (isValidCommandLine(commandLine))
            return;

        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
        String commandStr = commandOptionList.get(0);

        if (commandStr.equals("testapp1")) {
            testApp1();
            return;
        } else if (commandStr.equals("testapp2")) {
            testApp2();
            return;
        }

        ShellCommand command = ShellCommandFactory.of(commandStr);
        if (!command.isValidCommand(commandOptionList)) {
            return;
        }

        command.run(ssd, commandOptionList);
    }

    private boolean isValidCommandLine(String commandLine) {
        return commandLine == null || commandLine.trim().isEmpty();
    }
}
