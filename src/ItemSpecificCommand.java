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
                if(item.getItemEvents(this.verb)[0].equals("_NONE_")) {
                    // System.out.println("NO COMMMAND HERE: " + item.getItemEvents(verb)[0]);

                    return item.getMessageForVerb(this.verb) + "\n";
                } else {
                    String[] events = item.getItemEvents(this.verb);
                    // System.out.println(events + " EVENTS");P
                    // System.out.println(item.getPrimaryName() + " ITEM NAME");
                    // System.out.println("NOUN" + this.noun);
                    item.callEvents(events, this.noun);
                    return item.getMessageForVerb(this.verb) + "\n";
                }
            } else {
                return "You can't " + verb + " the " + item.getPrimaryName() + ".\n";
            }
        } catch(Item.NoItemException nie) {
            return "Item not found. \n";
        }
        // return "You can't " + verb + " the " + noun + ".\n";
    }
}
        
        
    
    
