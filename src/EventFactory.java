
public class EventFactory {
    
    private static EventFactory theInstance;
    

    public static synchronized EventFactory instance() {

        if (theInstance == null) {
            theInstance = new EventFactory();
        }
        return theInstance;
    }

    private EventFactory(){
    }

    Event parse(String eventType, String item){
//	eventType = eventType.toLowerCase();

        if (eventType.contains("Wound")){	
            System.out.println("WoundEvent called");
            return new WoundEvent(eventType);
        }
        if (eventType.contains("Drop")) {
            System.out.println("Drop Event called");
            return new DropEvent(item);
        }
        if (eventType.contains("Teleport")) {
            System.out.println("Teleport Event called");
            return new TeleportEvent(eventType);
        }
        if (eventType.contains("Disappear")) {
		String eventArgs = eventType+":"+item;
         System.out.println("Disappear Event called");
		// System.out.println("Event- "+eventType+" called");
		// System.out.println("eventArgs:"+eventArgs);
            return new DisappearEvent(item);
	
        }
        if (eventType.contains("Score")) {
            System.out.println("Score Event called");
            return new ScoreEvent(eventType);
        }
        if (eventType.contains("Die")) {
            System.out.println("Die Event called");
            
            return new DieEvent(eventType);
        }
        if (eventType.contains("Transform")) {
            System.out.println("Transform Event called");
		    String eventArgs = eventType+":"+item;
            return new TransformEvent(eventArgs);
        }
        if (eventType.contains("Win")) {
            System.out.println("Win Event called");
            return new WinEvent(eventType);
        }
	return new UnknownEvent(eventType);
    }
}
