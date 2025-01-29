package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorCommand implements Command {

    private final GraphicObjectPanel panel;
    private final String target; // ID, tipo o "all"
    private final Color newColor;
    private final Map<GraphicObject, Color> originalColors = new HashMap<>();

    public ColorCommand(GraphicObjectPanel panel, String target, Color newColor) {
        this.panel = panel;
        this.target = target;
        this.newColor = newColor;
    }

    @Override
    public boolean doIt() {
        List<GraphicObject> objects = panel.getAllObjects();
        boolean found = false;

        for (GraphicObject go : objects) {
            if (matchesTarget(go)) {
                originalColors.put(go, go.getColor()); // Salva il colore originale
                go.setColor(newColor); // Imposta il nuovo colore
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
        for (Map.Entry<GraphicObject, Color> entry : originalColors.entrySet()) {
            entry.getKey().setColor(entry.getValue()); // Ripristina il colore originale
        }
        panel.repaint(); // Aggiorna il disegno
        return true;
    }

    private boolean matchesTarget(GraphicObject go) {
        if (target.equalsIgnoreCase("all")) {
            return true;
        } else if (target.equalsIgnoreCase("circle") || target.equalsIgnoreCase("rectangle") || target.equalsIgnoreCase("image")) {
            return go.getType().equalsIgnoreCase(target);
        } else {
            return panel.getObjectById(target) == go; // Cerca per ID
        }
    }
}