public class UnknownEvent extends Event {
        private String text;
    
        UnknownEvent(String text){
            this.text = text;
        }
    
        String callEvent(){
            return "UNABLE TO PROCESS. YOUR COMPUTER WILL PROCEED TO NOW EXPLODE";
        }
    }

