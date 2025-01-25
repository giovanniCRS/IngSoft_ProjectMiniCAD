package is.shapes.specificinterpreter.nonTerminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;

import java.util.List;

public class UngroupCommandExpression implements AbstractExpression {
    private final String groupId;

    public UngroupCommandExpression(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public void interpret(Context context) throws Exception {
        GraphicObject group = context.getPanel().getObjectById(groupId);
        if (!(group instanceof GroupObject)) {
            throw new Exception("L'oggetto con ID '" + groupId + "' non è un gruppo valido.");
        }

        GroupObject groupObject = (GroupObject) group;
        List<GraphicObject> members = groupObject.getMembers();

        context.getPanel().remove(groupId);
        for (GraphicObject member : members) {
            context.getPanel().add(member);
        }
        System.out.printf("Gruppo con ID '%s' sciolto e membri ripristinati.%n", groupId);
    }
}
