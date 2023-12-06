
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
    Room(Scanner s, Dungeon d, Boolean initstate) throws NoRoomException, Dungeon.IllegalDungeonFormatException {
        init();
        name = s.nextLine();
        desc = "";
        if (name.equals(Dungeon.TOP_LEVEL_DELIM)) {
            // System.out.print("asdfasdf");
            throw new NoRoomException();
        }

        String lineOfDesc = s.nextLine();

        if (lineOfDesc.startsWith("Contents:") && initstate) {
            lineOfDesc = lineOfDesc.substring("Contents:".length()).trim();
            String[] itemNames = lineOfDesc.split(",");
            for (String itemName : itemNames) {
                Item item = d.getItem(itemName.trim());

                if (item != null) {
                    items.add(item);
                    // System.out.println("test item " + item);
                }
            }
            // move to descriptions
            lineOfDesc = s.nextLine();
        }

        while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
                !lineOfDesc.equals(Dungeon.ITEMS_MARKER) &&
                !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
            desc += lineOfDesc + "\n";
            lineOfDesc = s.nextLine();
        }

        // if (lineOfDesc.equals(Dungeon.ITEMS_MARKER)) {
        // lineOfDesc = s.nextLine();
        // while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
        // !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
        // try {
        // Item item = new Item(s);
        // items.add(item);
        // lineOfDesc = s.nextLine();
        // } catch (Item.NoItemException e) {
        // e.printStackTrace();
        // }

        // }
        // }

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

            // Check if there are items in the room to save
            if (!items.isEmpty()) {
                w.print("Contents: ");
                boolean firstItem = true;

                // Iterate through items and add them to the line
                for (Item item : items) {
                    if (firstItem) {
                        w.print(item.getPrimaryName());
                        firstItem = false;
                    } else {
                        w.print("," + item.getPrimaryName());
                    }
                }
                w.println(); // End the line for Contents
            }
            for(Exit exit : exits) {
                if(exit.isLockable() && !exit.isLocked()) {
                    w.println(exit.getDir() + ": unlocked");
                }
            }

            w.println(Dungeon.SECOND_LEVEL_DELIM);
        }
    }

    void restoreState(Scanner s, Dungeon d) throws GameState.IllegalSaveFormatException {
        String line = s.nextLine();

        if (!line.startsWith("beenHere")) {
            throw new GameState.IllegalSaveFormatException("No beenHere.");
        }

        beenHere = Boolean.valueOf(line.substring(line.indexOf("=") + 1));

        // Clear items list before restoring the state.
        items.clear();

        // check for contents ex: Contents: DrPepper,burrito - primary name. items in
        // room at save time and not yet picked up in inventory
        line = s.nextLine();

        if (line.startsWith("Contents:")) {
            String contentsLine = line.substring(line.indexOf(":") + 1).trim();
            String[] itemNames = contentsLine.split(",");
            for (String itemName : itemNames) {
                Item item = d.getItem(itemName.trim());
                if (item != null) {
                    items.add(item);
                }
            }

            line = s.nextLine(); // Move to the next line after reading the contents
        }

        boolean validExit;
        // If the next line is the '---', just skip it
        while (!line.equals("---") && line.substring(1).equals(": unlocked")) {
           validExit = false;
            for(Exit exit : exits) {
                if(exit.getDir().equals(line.substring(0, 1))) {
                    exit.setLocked(false);
                    validExit = true;
                }
            }
            if(!validExit) {
                throw new GameState.IllegalSaveFormatException("Invalid Exit dir \"" + line.substring(0, 1) + "\"");
            }

            line = s.nextLine();
        }
        if(!line.equals("---")) {
            throw new GameState.IllegalSaveFormatException("No delimiter");
        }
    }

    public String describe(boolean fulldescription) {
        GameState state = GameState.instance();
        boolean verboseMode = state.getVerboseMode();

        String description;
        if (beenHere && !fulldescription) {
            description = name;
        } else {
            description = name + "\n" + desc;
        }
        for (Item item : items) {
            description += "\nThere is a " + item.getPrimaryName() + " here.";
        }
        description += "\n";

        if (verboseMode) {
            for (Exit exit : exits) {
                if(!exit.isLockable() || !exit.isLocked()) {
                    description += "\n" + exit.describe();
                }
            }
        }
        description += "\n";
        beenHere = true;
        return description;
    }

    public Room leaveBy(String dir) {
        for (Exit exit : exits) {
            if (exit.getDir().equals(dir)) {
                if (!exit.isLockable() || !exit.isLocked() || tryUnlockDoor(exit)) {
                    return exit.getDest();
                }
            }
        }
        return null;
    }
    
    private boolean tryUnlockDoor(Exit exit) {
        if (exit.isLocked()) {
            try {
                GameState.instance().getItemFromInventoryNamed(exit.getKeyItem().getPrimaryName());
                exit.setLocked(false);
                return true;
            } catch (Item.NoItemException nie) {
                return false;
            }
        }
        return true;
    }

    void addExit(Exit exit) {
        exits.add(exit);
    }

    void add(Item item) {
        items.add(item);
    }

    Item remove(Item item) {
        items.remove(item);
        return item;
    }

    Item getItemNamed(String name) {
        for (Item item : items) {
            if (item.goesByName(name)) {
                return item;
            }
        }
        return null;
    }

    ArrayList<Item> getContents() {
        return items;
    }
}

