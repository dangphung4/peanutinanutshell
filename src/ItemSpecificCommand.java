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
            if(item.getMessageForVerb(this.verb) != null) {
                return item.getMessageForVerb(this.verb) + "\n";
            } else {
                return "You can't " + verb + " the " + item.getPrimaryName() + ".\n";
            }
        } catch(Item.NoItemException nie) {
            return "Item not found. \n";
        }

    }
}
        
        
    
    
