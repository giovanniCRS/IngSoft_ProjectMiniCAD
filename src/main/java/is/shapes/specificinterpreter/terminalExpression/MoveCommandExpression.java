package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicObject;
import java.awt.geom.Point2D;

public class MoveCommandExpression implements AbstractExpression {
    private final String id;
    private final double x;
    private final double y;

    public MoveCommandExpression(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void interpret(Context context) throws Exception {
        GraphicObject object = context.getPanel().getObjectById(id);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + id + "' non trovato.");
        }
        object.moveTo(new Point2D.Double(x, y));
        System.out.printf("Oggetto con ID '%s' spostato a (%.2f, %.2f).%n", id, x, y);
    }
}