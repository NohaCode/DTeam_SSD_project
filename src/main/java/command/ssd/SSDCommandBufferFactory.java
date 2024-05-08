package command.ssd;

import java.util.ArrayList;

public class SSDCommandBufferFactory {
    public static ArrayList<ArrayList<String>> commandBuffer;

    public static ArrayList<ArrayList<String>> get(){
        if(commandBuffer == null)
            commandBuffer = new ArrayList<>(10);
        return commandBuffer;
    }
}
