package app;

import command.shell.ShellCommand;
import command.shell.ShellCommandFactory;

import java.util.*;

import static app.SSD.INVALID_COMMAND_MESSAGE;


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
            shell.run(commandLine);
        }
    }

    public String run(String commandLine) {
        try {
            if (isValidCommandLine(commandLine))
                return commandLine;

            ArrayList<String> commandOptionList = new ArrayList<>(Arrays.asList(commandLine.trim().split(COMMAND_SEPARATOR)));
            String commandStr = commandOptionList.get(0);

            ShellCommand command = ShellCommandFactory.of(commandStr);
            command.process(ssd, commandOptionList);
        } catch (Exception e) {
            System.out.println(INVALID_COMMAND_MESSAGE);
            //throw new RuntimeException();
            //throw new SSDException(INVALID_COMMAND_MESSAGE);
            return INVALID_COMMAND_MESSAGE;

        }
        return commandLine;
    }

    private boolean isValidCommandLine(String commandLine) {
        return commandLine == null || commandLine.trim().isEmpty();
    }
}
