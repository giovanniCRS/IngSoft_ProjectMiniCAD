package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class RedoExpression implements AbstractExpression {
    @Override
    public void interpret(Context context) throws Exception {
        context.getCommandHandler().redo();
        System.out.println("Redo eseguito.");
    }
}
