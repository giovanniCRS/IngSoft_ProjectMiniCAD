package is.command;

public interface CommandHandler {
	void handle(Command cmd);

	// modifiche
	void undo();
	void redo();
}

