
import java.util.Scanner;

public class Exit {

    class NoExitException extends Exception {}

    private String dir;
    private Room src, dest;
    private boolean lockable;
    private boolean locked;
    private Item keyItem;

    Exit(String dir, Room src, Room dest) {
        init();
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        src.addExit(this);
    }

    /** Given a Scanner object positioned at the beginning of an "exit" file
        entry, read and return an Exit object representing it. 
        @param d The dungeon that contains this exit (so that Room objects 
        may be obtained.)
        @throws NoExitException The reader object is not positioned at the
        start of an exit entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
     */
    Exit(Scanner s, Dungeon d) throws NoExitException,
        Dungeon.IllegalDungeonFormatException {

        init();
        String srcTitle = s.nextLine().trim();
        if (srcTitle.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoExitException();
        }
        src = d.getRoom(srcTitle);
        dir = s.nextLine().trim();
        dest = d.getRoom(s.nextLine().trim());

        // I'm an Exit object. Add me as an exit to my source Room.
        src.addExit(this);

        String keyItemName = s.nextLine().trim();

        this.lockable = false;
        this.locked = false;
        if(!keyItemName.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            this.keyItem = d.getItem(keyItemName);
            if(this.keyItem != null) {
                this.lockable = true;
                this.locked = true;
            } else {
                throw new Dungeon.IllegalDungeonFormatException("Key item \"" + keyItemName + "\" for the " + this.src.getName() + " " + this.dir + " " + this.dest.getName() + " exit does not exist");
            }
            // throw away delimiter
            if (!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM)) {
                throw new Dungeon.IllegalDungeonFormatException("No '" + Dungeon.SECOND_LEVEL_DELIM + "' after exit.");
            }
        }
        // throw away delimiter
        else if (!keyItemName.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                Dungeon.SECOND_LEVEL_DELIM + "' after exit.");
        }
        
    }

    // Common object initialization tasks.
    private void init() {
    }

    String describe() {
        return "You can go " + this.dir + " to " + dest.getName() + ".";
    }

    String getDir() { return this.dir; }
    Room getSrc() { return this.src; }
    Room getDest() { return this.dest; }
    boolean isLockable() { return this.lockable; }
    boolean isLocked() { return this.locked; }
    Item getKeyItem() { return this.keyItem; }
    void setLocked(boolean value) { this.locked = value; }

}
