class ScoreCommand extends Command {
    ScoreCommand() {}

    String execute() {
        int score = GameState.instance().getScore();
        if(score < 10) {
            return "You have accumulated " + score + "points. You are a Novice Adventurer.\n";
        } if(score < 25) {
            return "You have accumulated " + score + "points. You are a Journeyman Adventurer.\n";
        } if(score < 50) {
            return "You have accumulated " + score + "points. You are a Heroic Adventurer.\n";
        } if(score < 100) {
            return "You have accumulated " + score + "points. You are a Legendary Adventurer.\n";
        }
        
        return "You have accumulated " + score + "points. You are a Mythical Adventurer.\n";
    }
}
