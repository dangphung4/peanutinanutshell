
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
            return new DisappearEvent(item);
        }
        if (eventType.contains("Score")) {
            return new ScoreEvent(eventType);
        }
        if (eventType.contains("Die")) {
            
            return new DieEvent(eventType);
        }
        if (eventType.contains("Transform")) {
            String[] args = {eventType, item};
            return new TransformEvent(args);
        }
        if (eventType.contains("Win")) {
            return new WinEvent(item);
        }
	return new UnknownEvent(eventType);
    }
}
