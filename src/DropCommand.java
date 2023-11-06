import java.util.List;
import java.util.ArrayList;

class DropCommand extends Command {
    private String itemName;

    DropCommand(String itemName) {
        this.itemName = itemName;
    }
    
    String execute() {
        if(this.itemName.equals("all")) {
            if(GameState.instance().getInventory().size() > 0) {
                List<Item> itemsInInventory = new ArrayList<>(GameState.instance().getInventory());
                for(Item item : itemsInInventory) {
                    try {
                        GameState.instance().removeFromInventory(item);
                        GameState.instance().getAdventurersCurrentRoom().add(item);
                    } catch(Item.NoItemException nie) {
                        
                    }
                }
                return "Dropped all.\n";
            } else {
                return "Nothing to drop.\n";
            }
        } else {
            try {
                Item item = GameState.instance().getItemFromInventoryNamed(this.itemName);
                GameState.instance().getAdventurersCurrentRoom().add(item);
                GameState.instance().removeFromInventory(item);
                return itemName + " dropped. \n";
            } catch(Item.NoItemException nie) {
                return "Item not found in inventory. \n";
            }
        }
    }
}
