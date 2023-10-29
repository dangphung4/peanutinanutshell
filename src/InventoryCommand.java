class InventoryCommand extends Command {
    public String execute() {
        if(GameState.instance().getInventory().size() > 0) {
            String out = "Inventory:\n";
            for(Item item: GameState.instance().getInventory()) {
                out += item.getPrimaryName() + "\n";
            }
            return out.substring(0, out.length() - 1);
        } else {
            return "There is nothing in your inventory";
        }
    }
}
