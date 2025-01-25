package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicEvent;
import is.shapes.model.GraphicObject;

public class NewObjectCommandExpression implements AbstractExpression {
    private final String id;
    private final GraphicObject object;

    public NewObjectCommandExpression(String id, GraphicObject object) {
        this.id = id;
        this.object = object;
    }

    @Override
    public void interpret(Context context) throws Exception {
        context.getPanel().add(id, object);

        GraphicEvent event = new GraphicEvent(object);
        context.getLogger().graphicChanged(event); // notifica il logger

        System.out.printf("Oggetto '%s' creato con ID '%s'.%n", object.getType(), id);
    }

    public boolean undo(Context context) {
        context.getPanel().remove(id);
        System.out.printf("Oggetto con ID '%s' rimosso.%n", id);
        return true;
    }

    public boolean redo(Context context) {
        context.getPanel().add(id, object);
        System.out.printf("Oggetto '%s' ricreato con ID '%s'.%n", object.getType(), id);
        return true;
    }
}

