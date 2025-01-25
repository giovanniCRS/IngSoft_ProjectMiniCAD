package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicObject;

public class DeleteCommandExpression implements AbstractExpression {
    private final String objectId;

    public DeleteCommandExpression(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public void interpret(Context context) throws Exception {
        GraphicObject object = context.getPanel().getObjectById(objectId);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + objectId + "' non trovato.");
        }
        context.getPanel().remove(objectId);
        System.out.printf("Oggetto con ID '%s' eliminato.%n", objectId);
    }
}