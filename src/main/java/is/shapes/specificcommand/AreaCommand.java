package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.view.GraphicObjectPanel;

public class AreaCommand implements Command {
    private final GraphicObjectPanel panel;
    private final String target;

    public AreaCommand(GraphicObjectPanel panel, String target) {
        this.panel = panel;
        this.target = target;
    }

    @Override
public boolean doIt() {
    double area = panel.calculateArea(target);
    System.out.println("Area totale per il target '" + target + "': " + area);
    return true;
}

    @Override
    public boolean undoIt() {
        return false; // Area non è reversibile
    }
}
