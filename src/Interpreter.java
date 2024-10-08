
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interpreter {

    private static GameState state; // not strictly necessary; GameState is
                                    // singleton

    public static String USAGE_MSG = "Usage: Interpreter dungeonFile.zork|saveFile.sav.";
    public static String FILE_NOT_FOUND_MSG = "Please enter a valid file path and file name.";
    public static void main(String args[]) {

        if (args.length < 1) {
            System.err.println(USAGE_MSG);
            System.exit(1);
        }

        String command;
        Scanner commandLine = new Scanner(System.in);

        try {
            state = GameState.instance();
            if (args[0].endsWith(".zork")) {
                state.initialize(new Dungeon(args[0], true));
                System.out.println("\nWelcome to " +
                        state.getDungeon().getTitle() + "!");
            } else if (args[0].endsWith(".sav")) {
                state.restore(args[0]);
                System.out.println("\nWelcome back to " +
                        state.getDungeon().getTitle() + "!");
            } else {
                System.err.println(USAGE_MSG);
                System.exit(2);
            }

            System.out.print("\n" +
                    state.getAdventurersCurrentRoom().describe(false));

            command = promptUser(commandLine);
            while (!command.equals("q")) {

                
                System.out.print(
                        CommandFactory.instance().parse(command).execute());

                command = promptUser(commandLine);
            }

            System.out.println("Bye!");
        
        } catch (FileNotFoundException fnfe) {
            System.out.println(FILE_NOT_FOUND_MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String promptUser(Scanner commandLine) {

        if(GameState.instance().getHealth() <=0){
            // System.out.println("You have died. Game over.");
            System.exit(0);
        }

        System.out.print("> ");
        return commandLine.nextLine();
    }

}
