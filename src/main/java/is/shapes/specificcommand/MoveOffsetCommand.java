package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;

import java.awt.geom.Point2D;

public class MoveOffsetCommand implements Command {
    private final GraphicObject object;
    private final double offsetX;
    private final double offsetY;
    private final String id;
    private final Point2D oldPos;

    public MoveOffsetCommand(GraphicObject object, double offsetX, double offsetY, String id, Point2D oldPos) {
        this.object = object;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.id = id;
        this.oldPos = oldPos;
    }

    @Override
    public boolean doIt() {
        System.out.printf("[%s] spostato di X=%s e Y=%s con ID: %s.%n", object.getType(), offsetX, offsetY, id);
        object.moveTo(new Point2D.Double(object.getPosition().getX() + offsetX,
                object.getPosition().getY() + offsetY));
        return true;
    }

    @Override
    public boolean undoIt() {
        System.out.printf("[%s] riposizionato in (%s, %s) con ID: %s.%n", object.getType(), oldPos.getX(), oldPos.getY(), id);
        object.moveTo(new Point2D.Double(object.getPosition().getX() - offsetX,
                object.getPosition().getY() - offsetY));
        return true;
    }
}

