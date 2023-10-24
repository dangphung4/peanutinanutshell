
import java.util.Hashtable;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Dungeon {

    public static class IllegalDungeonFormatException extends Exception {
        public IllegalDungeonFormatException(String e) {
            super(e);
        }
    }

    // Variables relating to both dungeon file and game state storage.
    public static String TOP_LEVEL_DELIM = "===";
    public static String SECOND_LEVEL_DELIM = "---";

    // Variables relating to dungeon file (.zork) storage.
    public static String ROOMS_MARKER = "Rooms:";
    public static String EXITS_MARKER = "Exits:";
    
    // Variables relating to game state (.sav) storage.
    static String FILENAME_LEADER = "Dungeon file: ";
    static String ROOM_STATES_MARKER = "Room states:";

    //marker for items
    public static String ITEMS_MARKER = "Items:";


    private String title;
    private Room entry;
    private Hashtable<String,Room> rooms;
    private Hashtable<String, Item> items;  // hashtable to store items
    private String filename;

    Dungeon(String title, Room entry) {
        init();
        this.filename = null;    // null indicates not hydrated from file.
        this.title = title;
        this.entry = entry;
        rooms = new Hashtable<String,Room>();
    }

    /**
     * Read from the .zork filename passed, and instantiate a Dungeon object
     * based on it.
     */
    public Dungeon(String filename) throws FileNotFoundException,
        IllegalDungeonFormatException {

        init();
        this.filename = filename;

        Scanner s = new Scanner(new FileReader(filename));
        title = s.nextLine();

        s.nextLine();   // Throw away version indicator.

        // Throw away delimiter.
        if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
            throw new IllegalDungeonFormatException("No '" +
                TOP_LEVEL_DELIM + "' after version indicator.");
        }

        // Read items
        if (!s.nextLine().equals(ITEMS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                ITEMS_MARKER + "' line where expected.");
        }
        try {
            while (true) {
                Item item = new Item(s);
                items.put(item.getPrimaryName(), item);
            }
        } catch (Item.NoItemException e) { /* end of items */ }


        // Throw away Rooms starter.
        if (!s.nextLine().equals(ROOMS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                ROOMS_MARKER + "' line where expected.");
        }

        try {
            // Instantiate and add first room (the entry).
            entry = new Room(s);
            add(entry);

            // Instantiate and add other rooms.
            while (true) {
                add(new Room(s));
            }
        } catch (Room.NoRoomException e) {  /* end of rooms */ }

        // Throw away Exits starter.
        // if (!s.nextLine().equals(EXITS_MARKER)) {
        //     throw new IllegalDungeonFormatException("No '" +
        //         EXITS_MARKER + "' line where expected.");
        // }

        // try {
        //     // Instantiate exits.
        //     while (true) {
        //         // (Note that the Exit constructor takes care of adding itself
        //         // to its source room.)
        //         Exit exit = new Exit(s, this);
        //     }
        // } catch (Exit.NoExitException e) {  /* end of exits */ }

        s.close();
    }
    
    // Common object initialization tasks, regardless of which constructor
    // is used.
    private void init() {
        rooms = new Hashtable<String,Room>();
        items = new Hashtable<String, Item>();  

    }

    /*
     * Store the current (changeable) state of this dungeon to the writer
     * passed.
     */
    void storeState(PrintWriter w) throws IOException {
        w.println(FILENAME_LEADER + getFilename());
        w.println(ROOM_STATES_MARKER);
        for (Room room : rooms.values()) {
            room.storeState(w);
        }
        w.println(TOP_LEVEL_DELIM);
    }

    /*
     * Restore the (changeable) state of this dungeon to that reflected in the
     * reader passed.
     */
    void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

        // Note: the filename has already been read at this point.
        
        if (!s.nextLine().equals(ROOM_STATES_MARKER)) {
            throw new GameState.IllegalSaveFormatException("No '" +
                ROOM_STATES_MARKER + "' after dungeon filename in save file.");
        }

        String roomName = s.nextLine();
        while (!roomName.equals(TOP_LEVEL_DELIM)) {
            getRoom(roomName.substring(0,roomName.length()-1)).restoreState(s);
            roomName = s.nextLine();
        }
    }

    public Room getEntry() { return entry; }

    public String getTitle() { return title; }

    public String getFilename() { return filename; }

    public void add(Room room) { rooms.put(room.getName(), room); }

    public Room getRoom(String roomName) {
        return rooms.get(roomName); 
    }
    public Item getItem(String itemName) {
        return items.get(itemName);
    }
}
