class ScoreEvent extends Event{
	private int scoreValue;

    ScoreEvent(String score){
        int openParenthsis = score.indexOf("(");
        int closeParenthesis = score.indexOf(")");
     	String commandValue= score.substring(openParenthsis + 1,closeParenthesis);
        this.scoreValue = Integer.parseInt(commandValue);
    }

    String callEvent(){
        GameState.instance().updateScore(scoreValue);
        return "SCORE AMOUNT " + scoreValue;
    }
    
}
