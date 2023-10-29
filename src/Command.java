
// For now, only direction commands and "save". If the "direction" is bogus,
// then this effectively doubles as an UnknownCommand (to be a subclass
// later).
public abstract class Command {

    Command() {}

    abstract String execute();
}
