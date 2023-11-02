class TakeCommand extends Command {
    private String itemName;
    
    TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    String execute() {
        
        Item item = GameState.instance().getAdventurersCurrentRoom().getItemNamed(itemName);
                
        if(item != null) {
            int weight = item.getWeight();
            for(Item tempItem: GameState.instance().getInventory()){
                weight += tempItem.getWeight();
            }
            if(weight <= 40) {
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
