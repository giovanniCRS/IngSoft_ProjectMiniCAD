package is.shapes.specificinterpreter.nonTerminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.interpreter.TerminalExpression;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;

import java.util.ArrayList;
import java.util.List;

public class GroupCommandExpression implements AbstractExpression {
    private final String groupId;
    private final List<AbstractExpression> children = new ArrayList<>();

    public GroupCommandExpression(String groupId) {
        this.groupId = groupId;
    }

    public void addChild(AbstractExpression child) {
        children.add(child);
    }

    public List<AbstractExpression> getChildren() {
        return children;
    }

    @Override
    public void interpret(Context context) throws Exception {
        List<GraphicObject> objects = new ArrayList<>();
        for (AbstractExpression child : children) {
            if (child instanceof TerminalExpression) {
                String objectId = ((TerminalExpression) child).getValue();
                GraphicObject object = context.getPanel().getObjectById(objectId);
                if (object == null) {
                    throw new Exception("Oggetto con ID '" + objectId + "' non trovato.");
                }
                objects.add(object);
            } else {
                child.interpret(context);
            }
        }
        GroupObject group = new GroupObject(objects);
        context.getPanel().add(groupId, group);
        System.out.printf("Gruppo con ID '%s' creato con %d membri.%n", groupId, objects.size());
    }
}
