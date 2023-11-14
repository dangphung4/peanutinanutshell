
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
            return new WoundEvent(eventType);
        }
        if (eventType.contains("Drop")) {
            return new DropEvent(item);
        }
        if (eventType.contains("Teleport")) {
            return new TeleportEvent(eventType);
        }
        if (eventType.contains("Disappear")) {
		String eventArgs = eventType+":"+item;
		// System.out.println("Event- "+eventType+" called");
		// System.out.println("eventArgs:"+eventArgs);
            return new DisappearEvent(eventArgs);
	
        }
        if (eventType.contains("Score")) {
            return new ScoreEvent(eventType);
        }
        if (eventType.contains("Die")) {
            return new DieEvent(eventType);
        }
        if (eventType.contains("Transform")) {
		String eventArgs = eventType+":"+item;
            // return new TransformEvent(eventArgs);
            return new WinEvent(eventType);
        }
        if (eventType.contains("Win")) {
            return new WinEvent(eventType);
        }
	return new UnknownEvent(eventType);
    }
}
