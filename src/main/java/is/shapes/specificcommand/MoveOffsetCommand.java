package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;

import java.awt.geom.Point2D;

public class MoveOffsetCommand implements Command {
    private final GraphicObject object;
    private final double offsetX;
    private final double offsetY;

    public MoveOffsetCommand(GraphicObject object, double offsetX, double offsetY) {
        this.object = object;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public boolean doIt() {
        object.moveTo(new Point2D.Double(object.getPosition().getX() + offsetX,
                object.getPosition().getY() + offsetY));
        return true;
    }

    @Override
    public boolean undoIt() {
        object.moveTo(new Point2D.Double(object.getPosition().getX() - offsetX,
                object.getPosition().getY() - offsetY));
        return true;
    }
}

