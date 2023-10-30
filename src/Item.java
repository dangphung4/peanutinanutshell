import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Enumeration;

public class Item {
    private String primaryName;
    private int weight;
    private Hashtable<String, String> messages;
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
            if (splitMessage.length == 2) {
                String verb = splitMessage[0];
                String message = splitMessage[1];
                messages.put(verb, message);
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
      /* Enumeration<String> test = messages.keys();
       while(test.hasMoreElements()) {
            String hur = test.nextElement();
            System.out.println(hur + messages.get(hur));
       }*/
      if(!messages.get(verb).equals("")) {
         return messages.get(verb);
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
}
