package is.interpreter;

import is.command.CommandHandler;
import is.shapes.view.GraphicObjectLogger;
import is.shapes.view.GraphicObjectPanel;

public class Context {
    private final GraphicObjectPanel panel;
    private final GraphicObjectLogger logger;
    private final CommandHandler commandHandler; 

    public Context(GraphicObjectPanel panel, GraphicObjectLogger logger, CommandHandler commandHandler) {
        this.panel = panel;
        this.logger = logger;
        this.commandHandler = commandHandler; 
    }

    public GraphicObjectPanel getPanel() {
        return panel;
    }

    public GraphicObjectLogger getLogger() {
        return logger;
    }

    public CommandHandler getCommandHandler() { 
        return commandHandler;
    }
}
