public class TransformEvent extends Event {
    String itemName;
    String transformedItemName;

    TransformEvent(String s) {
        int openParenthesis = s.indexOf("(");
        int closeParenthesis = s.indexOf(")");
        int colon = s.indexOf(":");
        this.transformedItemName = s.substring(openParenthesis + 1, closeParenthesis);
        this.itemName = s.substring(colon + 1);
        System.out.println("ITEM NAME TRANSOFRM EVENT " + itemName);
        System.out.println("TRSANSFORMED ITEM  NAME TRANSOFRM EVENT " + transformedItemName);
    }

    String callEvent() {
        GameState state = GameState.instance();
        try {
            try {
                //swap out items in the inventory
            state.removeFromInventory(state.getItemFromInventoryNamed(this.itemName));
            state.addToInventory(state.getDungeon().getItem(this.transformedItemName));
            }
            catch(Exception e){}
            try{
                //swap out items in the room
                state.getAdventurersCurrentRoom().remove(state.getItemInVicinityNamed(this.itemName));
                state.getAdventurersCurrentRoom().add(state.getDungeon().getItem(this.transformedItemName));
            }
            catch(Exception e){}
        } catch (Exception e) {

        }
        return "ITEM TRANSFORMED: " + itemName + " TO " + transformedItemName;
    }
}
