class HealthCommand extends Command {
    HealthCommand() {}
    
    String execute() {
        double health = GameState.instance().getHealth();
        double maxHealth = GameState.instance().getMaxHealth();
        System.out.println(health);
        System.out.println(maxHealth);
        if( health/maxHealth  == 1) {
            return "You feel robust and energetic.\n";
        } if( health/maxHealth >= .8) {
            return "You are slightly wounded but feel fine otherwise.\n";
        } if( health/maxHealth >= .6) {
            return "You are moderately injured and feel a decline in you overall performance.\n";
        } if( health/maxHealth >= .4) {
            return "You are severely hurt and in a critical condition.\n";
        } if( health/maxHealth >= .2) {
            return "You are on the verge of collapse.\n";
        }
        return "You are gravely injured and on the verge of death.\n";
    }
}
