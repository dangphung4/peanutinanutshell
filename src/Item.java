import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Item {
    private String primaryName;
    private int weight;
    private Hashtable<String, ItemEvent> messages;
    private ArrayList<String> aliases;

    static class NoItemException extends Exception {
        NoItemException(String e) {
            super(e);
        }
    }

    public Item(Scanner s) throws NoItemException {
        String itemLine = s.nextLine();

        // If the itemLine is TOP_LEVEL_DELIM, throw the exception
        if (itemLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoItemException("none items");
        }

        // split primary name and/or aliases
        String[] itemData = itemLine.split(",");

        primaryName = itemData[0].trim();
        // debugging
        // System.out.println(primaryName);

        aliases = new ArrayList<String>();

        if (itemData.length > 1) {
            for (int i = 1; i < itemData.length; i++) {
                aliases.add(itemData[i].trim());
                // debugging
                // System.out.println(itemData[i].trim());
            }
        }

        // get weight
        weight = Integer.parseInt(s.nextLine().trim());

        messages = new Hashtable<>();

        String aliasesLine = s.nextLine();

        // read and add item specific commands
        while (!aliasesLine.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            String[] splitMessage = aliasesLine.split(":", 2);
            String events = "";
            String message = "";
            if (splitMessage.length == 2) {
                String verb = splitMessage[0];

                int indexStart = verb.indexOf("[");
                // System.out.println(indexStart);
                int indexEnd = verb.indexOf("]");
                // System.out.println(indexEnd);
                if (indexStart == -1 || indexEnd == -1) {
                    events = "_NONE_";
                    message = splitMessage[1];

                }

                else {
                    message = splitMessage[1];
                    // get all events from the verb
                    events = verb.substring(indexStart + 1, indexEnd);

                    // remove the events from the verb
                    verb = verb.substring(0, indexStart);

                }
                String[] eventArray = events.split(",");
                // for (int i = 0; i < eventArray.length; i++) {
                //     System.out.println(eventArray[i]);
                // }
                ItemEvent itemEvent = new ItemEvent(message, eventArray);
                messages.put(verb, itemEvent);
                // System.out.println(verb);
                // System.out.println(messages.get(verb).getMessage());
            }
            aliasesLine = s.nextLine();
        }
    }

    public boolean goesByName(String name) {
        return aliases.contains(name) || primaryName.equals(name);
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public String getMessageForVerb(String verb) {
        if (messages.get(verb) != null) {
            return messages.get(verb).getMessage();
        }
        return null;
    }

    @Override
    public String toString() {
        return primaryName;
    }

    public int getWeight() {
        return weight;
    }

    public void callEvents(String [] events, String ItemName){
        for (int i =0; i<events.length; i++){
            EventFactory.instance().parse(events[i], ItemName).callEvent();
        }

    }
    public String[] getItemEvents(String verb) {
        if (messages.get(verb) != null) {
            return messages.get(verb).getEvents();
        }
        return null;
    }
}
