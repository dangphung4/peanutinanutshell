class DisappearEvent extends Event {
    private String itemName;

    DisappearEvent(String itemName) {
        this.itemName = itemName;
    }

    String callEvent() {
        GameState state = GameState.instance();

        try {
            state.removeFromInventory(state.getItemFromInventoryNamed(itemName));
        } catch (Exception e) {
        }

        try {
            state.getAdventurersCurrentRoom().remove(state.getItemFromInventoryNamed(itemName));
        } catch (Exception e) {
        }

        return "ITEM SHULD BE DISAPPEARED " + itemName;
    }
}
