package is.shapes.specificcommand;

import java.util.List;
import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.view.GraphicObjectPanel;

public class GroupCommand implements Command {
    private final GraphicObjectPanel panel;
    private final List<GraphicObject> objects;
    private final String groupId; // ID del gruppo
    private GroupObject group; // Il gruppo creato

    public GroupCommand(GraphicObjectPanel panel, List<GraphicObject> objects, String groupId) {
        this.panel = panel;
        this.objects = objects;
        this.groupId = groupId;
    }

    @Override
    public boolean doIt() {
        // Controlla se l'ID del gruppo è già usato
        if (panel.getObjectById(groupId) != null) {
            System.err.printf("Errore: ID '%s' già esistente.%n", groupId);
            return false;
        }
        group = new GroupObject(objects); // Crea il gruppo
        panel.add(groupId, group); // Aggiungi il gruppo al pannello con il suo ID
        System.out.printf("[Gruppo] formato con ID: %s.%n", groupId);
        return true;
    }

    @Override
    public boolean undoIt() {
        if (group != null) {
            panel.remove(groupId); // Rimuovi il gruppo dal pannello
            System.out.printf("[Gruppo] sciolto con ID: %s.%n", groupId);
            return true;
        }
        return false;
    }
}
