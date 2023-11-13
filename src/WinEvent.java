class WinEvent extends Event{

	private String winMessage;

	WinEvent(String winMessage){
		// winMessage = "HURRAH YOU HAVE WON!!!";
		this.winMessage = winMessage;
	}
	String callEvent(){
		System.out.println(winMessage);
		System.exit(0);
		return "";
	}
}