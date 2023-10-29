import java.io.IOException;

class SaveCommand extends Command {
    private String saveFileName;

    SaveCommand(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    String execute() {
        try {
            GameState.instance().store(this.saveFileName);
            return "Saved to " + this.saveFileName + ".sav";
        } catch(IOException ioe) {
            return "Couldn't save";
        }
    }
}
