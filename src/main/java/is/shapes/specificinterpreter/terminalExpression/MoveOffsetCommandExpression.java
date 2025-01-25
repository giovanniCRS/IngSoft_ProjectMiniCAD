package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicObject;

import java.awt.geom.Point2D;

public class MoveOffsetCommandExpression implements AbstractExpression {
    private final String objectId;
    private final double offsetX;
    private final double offsetY;

    public MoveOffsetCommandExpression(String objectId, double offsetX, double offsetY) {
        this.objectId = objectId;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void interpret(Context context) throws Exception {
        GraphicObject object = context.getPanel().getObjectById(objectId);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + objectId + "' non trovato.");
        }
        Point2D currentPos = object.getPosition();
        object.moveTo(new Point2D.Double(currentPos.getX() + offsetX, currentPos.getY() + offsetY));
        System.out.printf("Oggetto con ID '%s' spostato di offset (%f, %f).%n", objectId, offsetX, offsetY);
    }
}
