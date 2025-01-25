package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class UndoExpression implements AbstractExpression {
    @Override
    public void interpret(Context context) throws Exception {
        context.getCommandHandler().undo();
        System.out.println("Undo eseguito."); // Feedback visivo come in RedoExpression
    }
}