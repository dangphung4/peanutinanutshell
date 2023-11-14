public class ItemEvents{
    private String message;
	private String [] events;


	ItemEvents(String message, String [] events){
		this.message = message;
		this.events =events;
	}	

	String getMessage(){
	return this.message;}
	
	String[] getEvents(){
	return this.events;
	}


}