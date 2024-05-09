package app;

import command.shell.ShellCommand;
import command.shell.ShellCommandFactory;
import exception.ShellException;

import java.util.*;

import static app.SSD.INVALID_COMMAND_MESSAGE;


public class Shell {
    private SSD ssd;
    public static final String COMMAND_SEPARATOR = " ";

    public Shell() {
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
                System.out.println(INVALID_COMMAND_MESSAGE);
            }
        }
    }

    public void run(String commandLine) {
        if (isValidCommandLine(commandLine))
            throw new ShellException();

        ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
        String commandStr = commandOptionList.get(0);

        ShellCommand command = ShellCommandFactory.of(commandStr);
        command.process(ssd, commandOptionList);
    }

    private boolean isValidCommandLine(String commandLine) {
        return commandLine == null || commandLine.trim().isEmpty();
    }
}
