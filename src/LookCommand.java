public class LookCommand extends Command {
    String execute() {
        // fetch current room from roomstate
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();

        // return the description of the current room
        return "\n" + currentRoom.describe(true);
    }
}
