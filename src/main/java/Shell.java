
import java.util.*;


public class Shell {
    private SSD ssd;
    public static final String COMMAND_SEPARATOR = " ";

    public Shell(){
        this.ssd = new SSD();
    }

    public Shell(SSD ssd) {
        this.ssd = ssd;
    }

    public SSD getSsd() {
        return this.ssd;
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
        if (isValidCommandLine(commandLine))
            return;

        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
        String commandStr = commandOptionList.get(0);

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
