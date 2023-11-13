public class DieEvent extends Event{
    private String endMessage;

    DieEvent(String endMessage){
        // this.endMessage = "DEAD!";
        this.endMessage = endMessage;
}

public String getEndMessage(){
    return this.endMessage;
}
    String callEvent(){
        GameState state = GameState.instance();
        System.out.println(endMessage);
        System.exit(0);
        return endMessage;
    }
}
