package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.view.GraphicObjectPanel;

public class PerimeterCommand implements Command {
    private final GraphicObjectPanel panel;
    private final String target;

    public PerimeterCommand(GraphicObjectPanel panel, String target) {
        this.panel = panel;
        this.target = target;
    }

    @Override
    public boolean doIt() {
        double perimeter = panel.calculatePerimeter(target);
        System.out.println("Perimetro totale per il target '" + target + "': " + perimeter);
    return true;
    }

    @Override
    public boolean undoIt() {
        return false; // Perimeter non è reversibile
    }
}
