public class ItemEvent{
    private String message;
	private String [] events;


	ItemEvent(String message, String [] events){
		this.message = message;
		this.events =events;
	}	

	String getMessage(){
	return this.message;}
	
	String[] getEvents(){
	return this.events;
	}


}