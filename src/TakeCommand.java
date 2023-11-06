import java.util.ArrayList;
import java.util.List;

class TakeCommand extends Command {
    private String itemName;

    TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    String execute() {
        if (itemName == null) {
            return "Take what?\n";
       } else if (itemName.equalsIgnoreCase("all")) {
            Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
            List<Item> itemsInRoom = new ArrayList<>(currentRoom.getContents()); 
            // System.out.println(itemsInRoom);

            String result = "";
            int currentWeight = 0;

            // Calculate the current weight of the inventory
            for (Item tempItem : GameState.instance().getInventory()) {
                currentWeight += tempItem.getWeight();
            }
            
            // ` iterate through items in the room
            for (Item item : itemsInRoom) {
                currentWeight += item.getWeight();
            }
            if(currentWeight > 40) {
                return "Can't take all, load is too heavy.\n";
            }
            for (Item item : itemsInRoom) {
            
                
                currentRoom.remove(item);
                GameState.instance().addToInventory(item);
                currentWeight += item.getWeight(); // Update the running total weight
                result += item.getPrimaryName() + " added to inventory. \n";
               
                
                
            }
            return result.isEmpty() ? "No items to take. \n" : result;
        }

        // individual item case
        Item item = GameState.instance().getAdventurersCurrentRoom().getItemNamed(itemName);

        if (item != null) {
            int weight = item.getWeight();
            for (Item tempItem : GameState.instance().getInventory()) {
                weight += tempItem.getWeight();
            }
            if (weight <= 40) {
                GameState.instance().getAdventurersCurrentRoom().remove(item);
                GameState.instance().addToInventory(item);
                return itemName + " added to inventory. \n";
            } else {
                return "Your load is too heavy. \n";
            }
        }

        return "Item not found. \n";
    }
}
