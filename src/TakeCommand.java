class TakeCommand extends Command {
    private String itemName;
    
    TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    String execute() {
        
        Item item = GameState.instance().getAdventurersCurrentRoom().getItemNamed(itemName);
        
        if(item != null) {
            GameState.instance().getAdventurersCurrentRoom().remove(item);
            GameState.instance().addToInventory(item);
            return itemName + " added to inventory";
        }

        return "Item not found";
    }
}
