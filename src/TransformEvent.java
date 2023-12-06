public class TransformEvent extends Event {
    String itemName;
    String nextItemName;
    Item nextItem;

    //args[0] is event command & args[1] is the item name
    TransformEvent(String[] args) {
        int openParenthesis = args[0].indexOf("(");
        int closeParenthesis = args[0].indexOf(")");
        this.nextItemName = args[0].substring(openParenthesis + 1, closeParenthesis);
        this.itemName = args[1];
    }

    String callEvent() {
        GameState gameState = GameState.instance();

        try{
            gameState.removeFromInventory(gameState.getItemFromInventoryNamed(itemName));
        } catch(Item.NoItemException nie) {}

        try{
            gameState.removeItemInVicinityNamed(itemName);
        } catch(Item.NoItemException nie) {}

        this.nextItem = gameState.getDungeon().getItem(nextItemName);
        if(nextItem != null) {
            int adventurersCurrentWeight = gameState.getWeight();
            if(adventurersCurrentWeight + this.nextItem.getWeight() > 40) { 
                gameState.getAdventurersCurrentRoom().add(nextItem);
            } else {
                gameState.addToInventory(nextItem);
            }
            return itemName + " transformed into " + nextItemName + ".";
        }
        return itemName + "hasn't transformed into anthing and has dissapeared from existance.";
    }
}
