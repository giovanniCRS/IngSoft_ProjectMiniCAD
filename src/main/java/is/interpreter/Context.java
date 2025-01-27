package is.interpreter;

import is.shapes.view.GraphicObjectPanel;
import is.command.CommandHandler;

public class Context {
    private final GraphicObjectPanel panel;
    private final CommandHandler handler;

    public Context(GraphicObjectPanel panel, CommandHandler handler) {
        this.panel = panel;
        this.handler = handler;
    }

    public GraphicObjectPanel getPanel() {
        return panel;
    }

    public CommandHandler getHandler() {
        return handler;
    }
}
