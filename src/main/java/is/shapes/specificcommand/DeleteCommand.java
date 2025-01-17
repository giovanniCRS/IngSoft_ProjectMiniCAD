package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.view.GraphicObjectPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteCommand implements Command {
    private final GraphicObjectPanel panel;
    private final GraphicObject target;
    private final String targetId;
    private final List<GraphicObject> removedObjects = new ArrayList<>();
    private final Map<GraphicObject, String> originalIds = new HashMap<>();

    public DeleteCommand(GraphicObjectPanel panel, GraphicObject target, String targetId) {
        this.panel = panel;
        this.target = target;
        this.targetId = targetId;

        // Invertiamo la mappa ID -> Oggetto in Oggetto -> ID
        for (Map.Entry<String, GraphicObject> entry : panel.getObjectMap().entrySet()) {
            originalIds.put(entry.getValue(), entry.getKey());
        }
    }

    @Override
    public boolean doIt() {
        if (target instanceof GroupObject) {
            GroupObject group = (GroupObject) target;

            // Rimuove tutti i membri del gruppo dal pannello
            for (GraphicObject member : group.getMembers()) {
                String memberId = getObjectId(member);
                if (memberId != null) {
                    removedObjects.add(member);
                    panel.remove(memberId);
                }
            }
        }

        // Rimuove il gruppo stesso dal pannello
        panel.remove(targetId);
        removedObjects.add(target);

        if ( targetId.toString().length() > 4){
            System.out.printf("Oggetti del gruppo con ID '%s' rimossi.%n", targetId);
        }else {
            System.out.printf("Oggetto con ID '%s' rimosso.%n", targetId);
        }
        return true;
    }

    @Override
    public boolean undoIt() {
        // Ripristina tutti i membri del gruppo
        for (GraphicObject obj : removedObjects) {
            String id = originalIds.get(obj);
            if (id != null) {
                panel.add(id, obj);
            }
        }
        removedObjects.clear(); // Pulisce la lista dopo l'annullamento

        if ( targetId.toString().length() > 4){
            System.out.printf("Oggetti del gruppo con ID '%s' ripristinati.%n", targetId);
        }else {
            System.out.printf("Oggetto con ID '%s' ripristinato.%n", targetId);
        }
        
        return true;
    }

    private String getObjectId(GraphicObject obj) {
        return originalIds.get(obj); // Ottiene l'ID originale dell'oggetto
    }
}