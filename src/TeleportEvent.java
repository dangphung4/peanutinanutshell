
import java.util.Random;

public class TeleportEvent extends Event {
    private Dungeon d;

    TeleportEvent(String eventType) {
        this.d = GameState.instance().getDungeon();
    }

    String callEvent() {
        Random random = new Random();
        GameState state = GameState.instance();

        try {
            String[] roomNames = state.getDungeon().getListOfRooms();
            String newRandomRoomName = roomNames[random.nextInt(roomNames.length)];
            Room newRandomRoom = state.getDungeon().getRoom(newRandomRoomName);
            state.setAdventurersCurrentRoom(newRandomRoom);
            return "TELEPORT SUCCESS!!!";
        }

        catch (Exception e) {

            return "TELEPORT FAILED NOO";
        }
    }
}
