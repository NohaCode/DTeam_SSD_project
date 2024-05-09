package command.shell;

import app.SSD;
import util.FileHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ShellRunnerCommand extends ShellCommand {
    @Override
    public boolean isValidCommandImpl(ArrayList<String> commandOptionList) {
        if(!isValidCommandSize(commandOptionList))
            return false;

        return true;
    }

    private boolean isValidCommandSize(ArrayList<String> commandOptionList) {
        return commandOptionList != null && commandOptionList.size() == 1;
    }

    @Override
    public void runImpl(SSD ssd, ArrayList<String> commandOptionList) {
        testRun(FileHandler.RUN_LIST_FILE_PATH);
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
                System.out.println("Pass");
            }
            return "Pass";
        } catch (Exception e) {
            System.out.println("Fail");
            return "Fail";
        }
    }
}
