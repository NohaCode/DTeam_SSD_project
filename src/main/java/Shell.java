
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
            run("write 0 0xAAAABBBB");
            run("write 1 0xAAAABBBB");
            run("write 2 0xAAAABBBB");
            run("write 3 0xAAAABBBB");
            run("write 4 0xAAAABBBB");
            run("write 5 0xAAAABBBB");
        }

        run("write 0 0x12345678");
        run("write 1 0x12345678");
        run("write 2 0x12345678");
        run("write 3 0x12345678");
        run("write 4 0x12345678");
        run("write 5 0x12345678");

        run("read 0");
        run("read 1");
        run("read 2");
        run("read 3");
        run("read 4");
        run("read 5");
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
