
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Room {

    class NoRoomException extends Exception {
    }

    private String name;
    private String desc;
    private boolean beenHere;
    private ArrayList<Exit> exits;
    private ArrayList<Item> items;

    Room(String name) {
        init();
        this.name = name;
    }

    /**
     * Given a Scanner object positioned at the beginning of a "room" file
     * entry, read and return a Room object representing it.
     * 
     * @throws NoRoomException               The reader object is not positioned at
     *                                       the
     *                                       start of a room entry. A side effect of
     *                                       this is the reader's cursor
     *                                       is now positioned one line past where
     *                                       it was.
     * @throws IllegalDungeonFormatException A structural problem with the
     *                                       dungeon file itself, detected when
     *                                       trying to read this room.
     */
    Room(Scanner s) throws NoRoomException, Dungeon.IllegalDungeonFormatException {
        init();
        name = s.nextLine();
        desc = "";
        System.out.println(name);
        if (name.equals(Dungeon.TOP_LEVEL_DELIM)) {
            // System.out.print("asdfasdf");
            throw new NoRoomException();
        }

        String lineOfDesc = s.nextLine();
        while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
                !lineOfDesc.equals(Dungeon.ITEMS_MARKER) &&
                !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
            desc += lineOfDesc + "\n";
            lineOfDesc = s.nextLine();
        }

        if (lineOfDesc.equals(Dungeon.ITEMS_MARKER)) {
            lineOfDesc = s.nextLine();
            while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
                    !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
                try {
                    Item item = new Item(s);
                    items.add(item);
                    lineOfDesc = s.nextLine();
                } catch (Item.NoItemException e) {
                    e.printStackTrace();
                }

            }
        }

        // throw away delimiter
        if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                    Dungeon.SECOND_LEVEL_DELIM + "' after room.");
        }
    }

    // Common object initialization tasks.
    private void init() {
        exits = new ArrayList<Exit>();
        items = new ArrayList<Item>(); // Initialize items list
        beenHere = false;
    }

    String getName() {
        return name;
    }

    void setDesc(String desc) {
        this.desc = desc;
    }

    /*
     * Store the current (changeable) state of this room to the writer
     * passed.
     */
    void storeState(PrintWriter w) throws IOException {
        // At this point, nothing to save for this room if the user hasn't
        // visited it.
        if (beenHere) {
            w.println(name + ":");
            w.println("beenHere=true");
            w.println(Dungeon.SECOND_LEVEL_DELIM);
        }
    }

    void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

        String line = s.nextLine();
        if (!line.startsWith("beenHere")) {
            throw new GameState.IllegalSaveFormatException("No beenHere.");
        }
        beenHere = Boolean.valueOf(line.substring(line.indexOf("=") + 1));

        s.nextLine(); // consume end-of-room delimiter
    }

    public String describe() {
        String description;
        if (beenHere) {
            description = name;
        } else {
            description = name + "\n" + desc + "\n";
        }
        for (Exit exit : exits) {
            description += "\n" + exit.describe();
        }
        beenHere = true;
        return description;
    }

    public Room leaveBy(String dir) {
        for (Exit exit : exits) {
            if (exit.getDir().equals(dir)) {
                return exit.getDest();
            }
        }
        return null;
    }

    void addExit(Exit exit) {
        exits.add(exit);
    }

    public void add(Item item) {
        items.add(item);
    }

    public Item remove(Item item) {
        items.remove(item);
        return item;
    }

    public Item getItemNamed(String name) {
        for (Item item : items) {
            if (item.goesByName(name)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getContents() {
        return items;
    }
}
