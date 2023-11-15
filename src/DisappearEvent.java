class DisappearEvent extends Event {
    private String itemName;

    DisappearEvent(String itemName) {
        this.itemName = itemName;
    }

    String callEvent() {
        GameState state = GameState.instance();
        // System.out.println(this.itemName + " THIS ITEM NAMEM@MM@M@M@M");

        try {
            // System.out.println("HELLO?");
            // state.getAdventurersCurrentRoom().remove(state.getItemFromInventoryNamed(itemName));
            // state.removeFromInventory(state.getItemFromInventoryNamed(itemName));
            try{
                state.getAdventurersCurrentRoom().remove(state.getItemInVicinityNamed(itemName));
            }
            catch (Exception e) {
                System.out.println("ITEM NOT IN CURRENT ROOM, but maybe INVENTORY?");
            }
            try{
                state.removeFromInventory(state.getItemFromInventoryNamed(itemName));
            }
            catch (Exception e) {
                // System.out.println("ITEM NOT IN INVENTORY");
            }
            
        } catch (Exception e) {
            // e.printStackTrace();
        }

        // try {
        //     state.getAdventurersCurrentRoom().remove(state.getItemFromInventoryNamed(itemName));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // System.out.println("ITEM SHULD BE DISAPPEARED " + itemName);
        return "ITEM SHULD BE DISAPPEARED " + itemName;
    }
}
