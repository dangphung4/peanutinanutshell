class WoundEvent extends Event{
	private int amountOfWound;

	WoundEvent(String amountOfWound){
            int openParenthsis = amountOfWound.indexOf("(");
	    int closeParenthesis = amountOfWound.indexOf(")");
     	    String commandValue= amountOfWound.substring(openParenthsis + 1,closeParenthesis);
	    this.amountOfWound = Integer.parseInt(commandValue);
	}

	String callEvent(){
	    GameState.instance().updateHealth(amountOfWound);
		System.out.println(GameState.instance().getHealth());
	    return "WOUND AMOUNT " + amountOfWound;
	}
}