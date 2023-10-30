class UnknownCommand extends Command {
    private String bogusCommand;

    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }

    String execute() {
        return bogusCommand + " is not a valid command. \n";
    }
}
