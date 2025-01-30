package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
//import is.shapes.model.GroupObject;
import is.shapes.view.GraphicObjectPanel;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorCommand implements Command {

    private final GraphicObjectPanel panel;
    private final String target; // ID, tipo o "all"
    private final Color newColor;
    private final Map<GraphicObject, Color> originalColors;

    public ColorCommand(GraphicObjectPanel panel, String target, Color newColor) {
        this.panel = panel;
        this.target = target;
        this.newColor = newColor;
        this.originalColors = new HashMap<>();
    }

    @Override
    public boolean doIt() {
        List<GraphicObject> objects = panel.getAllObjects();
        boolean found = false;

        for (GraphicObject go : objects) {
            if (matchesTarget(go)) {
                saveOriginalColor(go);
                go.setColor(newColor);
                found = true;
            }
        }

        if (found) {
            panel.repaint(); // Aggiorna il disegno
            return true;
        } else {
            System.err.printf("Nessun oggetto %s trovato.%n", target);
            return false;
        }
    }

    @Override
    public boolean undoIt() {
        boolean success = false;
        for (Map.Entry<GraphicObject, Color> entry : originalColors.entrySet()) {
            GraphicObject go = entry.getKey();
            go.setColor(entry.getValue()); // Ripristina il colore originale
            success = true;
        }
        if (success) {
            panel.repaint();
        }
        return success;
    }

    private boolean matchesTarget(GraphicObject go) {
        if (target.equalsIgnoreCase("all")) {
            return true; // Colora tutto
        } 
        
        if (target.equalsIgnoreCase("circle") || target.equalsIgnoreCase("rectangle") || target.equalsIgnoreCase("image")) {
            return go.getType().equalsIgnoreCase(target); // Colora tutti gli oggetti di un certo tipo
        } 
    
        // Controlliamo se l'ID corrisponde a un oggetto
        if (panel.getObjectById(target) == go) {
            return true;
        }
    
        // Controlliamo se l'ID appartiene a un gruppo
        for (Map.Entry<String, GraphicObject> entry : panel.getObjectMap().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(target) && entry.getValue() instanceof GroupObject group) {
                if (group.getMembers().contains(go)) {
                    return true; 
                }
            }
        }
    
        return false; // Non trovato
    }

    private void saveOriginalColor(GraphicObject go) {
        if (!originalColors.containsKey(go)) {
            originalColors.put(go, go.getColor()); // Salva solo se non è già salvato
        }
    }
}