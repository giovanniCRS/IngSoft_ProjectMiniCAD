package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.view.GraphicObjectPanel;

public class ListCommand implements Command {
    private final GraphicObjectPanel panel;
    private final String target;

    public ListCommand(GraphicObjectPanel panel, String target) {
        this.panel = panel;
        this.target = target;
    }

    @Override
    public boolean doIt() {
        panel.listObjects(target); // Metodo ipotetico per elencare gli oggetti
        return true;
    }

    @Override
    public boolean undoIt() {
        return false; // List non è reversibile
    }
}
