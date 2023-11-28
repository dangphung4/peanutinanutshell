
import java.util.List;
import java.util.Arrays;

public class CommandFactory {

    private static CommandFactory theInstance;
    public static List<String> MOVEMENT_COMMANDS = Arrays.asList("n", "w", "e", "s", "u", "d");

    public static synchronized CommandFactory instance() {
        if (theInstance == null) {
            theInstance = new CommandFactory();
        }
        return theInstance;
    }

    private CommandFactory() {
    }

    public Command parse(String command) {
        String[] splitCommand = command.split("\\s+"); // Split the command by whitespace
        String cmd = splitCommand.length > 0 ? splitCommand[0] : "";

        if (MOVEMENT_COMMANDS.contains(cmd)) { // If it's a movement command
            return new MovementCommand(cmd);
        } else if (cmd.equals("drop") && splitCommand.length > 1) {
            return new DropCommand(splitCommand[1]);
        } else if (cmd.equals("save") && splitCommand.length > 1) {
            return new SaveCommand(splitCommand[1]);
        } else if (cmd.equals("take")) {
            // If 'take' is used without specifying an item, handle accordingly
            String itemName = splitCommand.length > 1 ? splitCommand[1] : null;
            return new TakeCommand(itemName);
        } else if (cmd.equals("look")) {
            return new LookCommand();
        } else if (cmd.equals("i") || cmd.equals("inventory")) {
            return new InventoryCommand();
        } else if (cmd.equals("health")) {
            return new HealthCommand();
        } else if (cmd.equals("score")) {
            return new ScoreCommand();
        } else if (cmd.equals("verbose") && splitCommand.length > 1) {
            return new VerboseCommand(splitCommand[1]);
        } else if (splitCommand.length == 2) {
            // This is a catch-all for other two-word commands not explicitly checked above
            return new ItemSpecificCommand(splitCommand[0], splitCommand[1]);
        } else {
            // For one-word commands or unknown commands, return UnknownCommand
            return new UnknownCommand(cmd);
        }
    }
}
