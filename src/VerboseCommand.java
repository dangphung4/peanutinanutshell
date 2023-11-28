public class VerboseCommand extends Command {

    private String text;

    VerboseCommand(String text) {
        this.text = text;
    }

    String execute() {
        GameState state = GameState.instance();

        if (this.text.equals("on")) {
            state.setVerboseMode(true);
            return "Verbose mode on\n";
        }

        else if (this.text.equals("off")) {
            state.setVerboseMode(false);

            return "Verbose mode off\n";
        }

        else {
            return "Invalid input\n";
        }
    }
}
