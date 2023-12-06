class DisappearEvent extends Event {
    private String itemName;

    DisappearEvent(String itemName) {
        this.itemName = itemName;
    }

    String callEvent() {
        GameState gameState = GameState.instance();

        try { //attempt removal from inventory
            gameState.removeFromInventory(gameState.getItemFromInventoryNamed(itemName));
        } catch(Item.NoItemException nie) {}

        try { //atempt removal from room
            gameState.getAdventurersCurrentRoom().remove(gameState.getItemInVicinityNamed(itemName));
        } catch(Item.NoItemException nie) {}

        //item has now been poofed from existance in this physical plane
        return "The " + this.itemName + " has been systematically removed from this world.";
    }
}
