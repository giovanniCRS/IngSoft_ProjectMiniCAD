package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicObject;

public class ScaleCommandExpression implements AbstractExpression {
    private final String objectId;
    private final double factor;

    public ScaleCommandExpression(String objectId, double factor) {
        this.objectId = objectId;
        this.factor = factor;
    }

    @Override
    public void interpret(Context context) throws Exception {
        GraphicObject object = context.getPanel().getObjectById(objectId);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + objectId + "' non trovato.");
        }
        object.scale(factor);
        System.out.printf("Oggetto con ID '%s' scalato con fattore %f.%n", objectId, factor);
    }
}