class WinEvent extends Event{


	WinEvent(String item){
	}
	String callEvent(){

		System.out.println("CONGRATULATIONS!!! YOU WIN!!!");
		System.exit(0);
		return "";
	}
}