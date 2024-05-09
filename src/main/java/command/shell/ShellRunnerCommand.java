package command.shell;

import app.SSD;
import exception.SSDException;
import util.FileHandler;
import util.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static app.SSD.INVALID_COMMAND_MESSAGE;

public class ShellRunnerCommand extends ShellCommand {

    public static final String RUNNER_PASS = "PASS";
    public static final String RUNNER_FAIL = "FAIL";

    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if (!isValidCommandSize(commandOptionList))
            return false;

        return true;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        testRun(FileHandler.RUN_LIST_FILE_PATH);
    }

    @Override
    public void process(SSD ssd, ArrayList<String> commandOptionList) {
        try {
            setLogger(Logger.get(Logger.TEST));
            if (!isValidCommand(commandOptionList)) {
                throw new SSDException(INVALID_COMMAND_MESSAGE);
            }

            run(ssd, commandOptionList);
        } catch (Exception ignored) {
        } finally {
            setLogger(Logger.get(Logger.PRODUCTION));
        }
    }

    private boolean isValidCommandSize(ArrayList<String> commandOptionList) {
        return commandOptionList != null && commandOptionList.size() == 1;
    }

    public String testRun(String runListPath) {
        try {
            Class<?> shellClass = Class.forName("app.Shell");
            Method m = shellClass.getMethod("run", String.class);
            Object shellObject = shellClass.newInstance();

            FileHandler fileHandler = FileHandler.get();

            ArrayList<String> runList = new ArrayList<>(Arrays.asList(
                    fileHandler.fileRead(runListPath).split("\n")
            ));

            for (String testCommand : runList) {
                System.out.print(testCommand + " --- Run ... ");
                m.invoke(shellObject, testCommand);
                System.out.println(RUNNER_PASS);
            }
            return RUNNER_PASS;
        } catch (Exception e) {
            System.out.println(RUNNER_FAIL);
            return RUNNER_FAIL;
        }
    }
}
