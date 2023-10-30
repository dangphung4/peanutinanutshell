import java.util.Hashtable;
import java.util.Enumeration;
class ItemSpecificCommand extends Command {
    String verb = "";
    String noun = "";
    
    ItemSpecificCommand(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }

    String execute() {
        try {
            Item item = GameState.instance().getItemInVicinityNamed(noun);
            if(!item.getMessageForVerb(this.verb).equals("")) {
                return item.getMessageForVerb(this.verb);
            } else {
                return "You can't do that.";
            }
        } catch(Item.NoItemException nie) {
            return "Item not found";
        }

    }
}
        
        
    
    
