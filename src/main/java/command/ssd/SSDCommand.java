package command.ssd;

import exception.SSDException;
import util.FileHandler;

import java.util.ArrayList;

import static app.SSD.INVALID_COMMAND_MESSAGE;

public abstract class SSDCommand {
    FileHandler fileHandler = FileHandler.get();
    ArrayList<ArrayList<String>> commandBuffer = SSDCommandBuffer.get();

    abstract boolean isValidCommand(ArrayList<String> commandOptionList);

    abstract void run(ArrayList<String> commandOptionList);

    abstract boolean flushAndCheckAbleBuffering(ArrayList<String> commandOptionList);

    public void process(ArrayList<String> commandOptionList) {
        if (!isValidCommand(commandOptionList)) {
            throw new SSDException(INVALID_COMMAND_MESSAGE);
        }

        if (flushAndCheckAbleBuffering(commandOptionList))
            commandBuffer.add(commandOptionList);
        else
            run(commandOptionList);

        SSDCommandBuffer.run(); // size 10 넘으면 process.run 수행
    }
}
