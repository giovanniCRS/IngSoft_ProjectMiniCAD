package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

public class NewObjectCmdWithID implements Command {

    private final GraphicObjectPanel panel;
    private final GraphicObject go;
    private final String id;

    public NewObjectCmdWithID(GraphicObjectPanel panel, GraphicObject go, String id) {
        this.panel = panel;
        this.go = go;
        this.id = id;
    }

    @Override
    public boolean doIt() {
        System.out.printf("[%s] creato con ID: %s.%n", go.getType(), id);
        panel.add(id, go); // Aggiunge l'oggetto con ID
        return true;
    }

    @Override
    public boolean undoIt() {
        System.out.printf("[%s] rimosso con ID: %s.%n", go.getType(), id);
        panel.remove(id); // Rimuove l'oggetto tramite ID
        return true;
    }
}

