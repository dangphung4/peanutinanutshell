
import java.util.List;
import java.util.Arrays;

public class CommandFactory {

    private static CommandFactory theInstance;
    public static List<String> MOVEMENT_COMMANDS = 
        Arrays.asList("n","w","e","s","u","d" );

    public static synchronized CommandFactory instance() {
        if (theInstance == null) {
            theInstance = new CommandFactory();
        }
        return theInstance;
    }

    private CommandFactory() {
    }

    public Command parse(String command) {
        String[] splitCommand = command.split(" ");
        if (MOVEMENT_COMMANDS.contains(command)) { //there's no need to use the split command if the command is just a direction
            return new MovementCommand(command);
        } else if(splitCommand[0].equals("drop")) {
            return new DropCommand(splitCommand[1]);
        } else if(splitCommand[0].equals("save")) {
            
            return new SaveCommand(splitCommand[1]);
        } else if(splitCommand[0].equals("take")) {
        
            return new TakeCommand(splitCommand[1]);
        } else if(splitCommand[0].equals("look")) {  
            return new LookCommand();
        } else if(splitCommand[0].equals("i") || splitCommand[0].equals("inventory")) {
            return new InventoryCommand();
        } else if(splitCommand.length == 2) { 
        //still need to instantiate unknowncommand
            return new ItemSpecificCommand(splitCommand[0], splitCommand[1]);
        }
        return new UnknownCommand(splitCommand[0]);
    }

}
