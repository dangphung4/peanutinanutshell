
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GameState {

    public static class IllegalSaveFormatException extends Exception {
        public IllegalSaveFormatException(String e) {
            super(e);
        }
    }

    static String DEFAULT_SAVE_FILE = "zork_save";
    static String SAVE_FILE_EXTENSION = ".sav";
    static String SAVE_FILE_VERSION = "Zork III save data";

    static String CURRENT_ROOM_LEADER = "Current room: ";

    private static GameState theInstance;
    private Dungeon dungeon;
    private Room adventurersCurrentRoom;
    private ArrayList<Item> inventory = new ArrayList<>();

    static synchronized GameState instance() {
        if (theInstance == null) {
            theInstance = new GameState();
        }
        return theInstance;
    }

    private GameState() {
    }

    void restore(String filename) throws FileNotFoundException,
            IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException {

        Scanner s = new Scanner(new FileReader(filename));
        if (!s.nextLine().equals(SAVE_FILE_VERSION)) {
            throw new IllegalSaveFormatException("Save file not compatible.");
        }

        // read dungeon file
        String dungeonFileLine = s.nextLine();

        if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
            throw new IllegalSaveFormatException("No '" +
                    Dungeon.FILENAME_LEADER +
                    "' after version indicator.");
        }
        // dungeon and read rooms as well, should eat ====
        dungeon = new Dungeon(dungeonFileLine.substring(
                Dungeon.FILENAME_LEADER.length()), false);
        dungeon.restoreState(s);

        // skip adventurer
        // String adventTEST = s.nextLine();
        // System.out.println("Adventurer" + adventTEST);

        String currentRoomLine = s.nextLine();
        System.out.println(currentRoomLine + " GAMESTAE CURRNET ROOM LINE");
        adventurersCurrentRoom = dungeon.getRoom(
                currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));

        // read inventory
        String inventoryLine = s.nextLine();
        if (inventoryLine.startsWith("Inventory:")) {
            String[] itemNames = inventoryLine.substring(inventoryLine.indexOf(":") + 1).split(",");
            for (String itemName : itemNames) {
                Item item = dungeon.getItem(itemName.trim());
                if (item != null) {
                    inventory.add(item);
                }
            }
        }
    }

    void store() throws IOException {
        store(DEFAULT_SAVE_FILE);
    }

    void store(String saveName) throws IOException {
        String filename = saveName + SAVE_FILE_EXTENSION;
        PrintWriter w = new PrintWriter(new FileWriter(filename));
        w.println(SAVE_FILE_VERSION);
        dungeon.storeState(w);

        // print Adventurer:
        w.println("Adventurer:");
        w.println(CURRENT_ROOM_LEADER +
                getAdventurersCurrentRoom().getName());
        // Print inventory
        w.print("Inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            w.print(inventory.get(i).getPrimaryName());
            if (i < inventory.size() - 1) {
                w.print(",");
            }
        }

        w.close();
    }

    void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        adventurersCurrentRoom = dungeon.getEntry();
    }

    Room getAdventurersCurrentRoom() {
        return adventurersCurrentRoom;
    }

    void setAdventurersCurrentRoom(Room room) {
        adventurersCurrentRoom = room;
    }

    Dungeon getDungeon() {
        return dungeon;
    }

    ArrayList<Item> getInventory() {
        return inventory;
    }

    void addToInventory(Item item) {
        inventory.add(item);
    }

    void removeFromInventory(Item item) throws Item.NoItemException {
        if (!inventory.contains(item)) {
            throw new Item.NoItemException("Item not found in inventory.");
        }
        inventory.remove(item);
    }

    Item getItemInVicinityNamed(String name) throws Item.NoItemException {
        Room currentRoom = getAdventurersCurrentRoom();

        // Check the player's inventory
        for (Item item : inventory) {
            if (item.goesByName(name)) {
                return item;
            }
        }

        // Check the current room's contents
        for (Item item : currentRoom.getContents()) {
            if (item.goesByName(name)) {
                return item;
            }
        }

        // if none then throw exception
        throw new Item.NoItemException("Item not found in vicinity.");
    }

    Item getItemFromInventoryNamed(String name) throws Item.NoItemException {
        for (Item item : inventory) {
            if (item.goesByName(name)) {
                return item;
            }
        }
        throw new Item.NoItemException("Item not found in inventory.");
    }
}
