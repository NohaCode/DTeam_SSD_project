package command;

import java.util.HashMap;

public class CommandFactory {
    private static final HashMap<String, Command> commandMap = new HashMap<>();
    public static Command get(String command){
        if(command.equals("R")){
            if(!commandMap.containsKey("R"))
                commandMap.put("R", new ReadCommand());
            return commandMap.get("R");
        }
        else if(command.equals("W")){
            if(!commandMap.containsKey("W"))
                commandMap.put("W", new WriteCommand());
            return commandMap.get("W");
        }
        return null;
    }
}
