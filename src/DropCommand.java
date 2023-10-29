class DropCommand extends Command {
    private String itemName;

    DropCommand(String itemName) {
        this.itemName = itemName;
    }
    
    String execute() {
        try {
            Item item = GameState.instance().getItemFromInventoryNamed(this.itemName);
            GameState.instance().getAdventurersCurrentRoom().add(item);
            GameState.instance().removeFromInventory(item);
            return itemName + " dropped";
        } catch(Item.NoItemException nie) {
            return "Item not found in inventory";
        }
    }
}
