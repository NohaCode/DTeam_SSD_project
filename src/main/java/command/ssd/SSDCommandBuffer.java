package command.ssd;

import java.util.ArrayList;

public class SSDCommandBuffer {
    private static ArrayList<ArrayList<String>> commandBuffer;

    public static ArrayList<ArrayList<String>> get() {
        if (commandBuffer == null)
            commandBuffer = new ArrayList<>();
        return commandBuffer;
    }

    public static void run(){
        while (commandBuffer.size() > 10) {
            flushFirstCommand();
        }
    }

    private static void flushFirstCommand() {
        ArrayList<String> bufferedCommandOptionList = commandBuffer.get(0);
        String bufferedCommandStr = bufferedCommandOptionList.get(0);
        SSDCommand bufferedCommand = SSDCommandFactory.of(bufferedCommandStr);
        bufferedCommand.run(bufferedCommandOptionList);

        commandBuffer.remove(0);
    }
}
