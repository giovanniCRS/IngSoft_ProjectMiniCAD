package is.command;

public class NaiveCommandHandler implements CommandHandler{
    @Override
    public void handle(Command cmd) {
        cmd.doIt();
    }

    @Override
    public void undo() {
        // NaiveCommandHandler non supporta undo
    }

    @Override
    public void redo() {
        // NaiveCommandHandler non supporta redo
    }
}
