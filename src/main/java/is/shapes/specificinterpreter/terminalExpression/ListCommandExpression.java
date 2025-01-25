package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class ListCommandExpression implements AbstractExpression {
    private final String target;

    public ListCommandExpression(String target) {
        this.target = target;
    }

    @Override
    public void interpret(Context context) throws Exception {
        context.getPanel().listObjects(target);
    }
}