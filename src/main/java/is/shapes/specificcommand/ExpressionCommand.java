package is.shapes.specificcommand;

import is.command.Command;
import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class ExpressionCommand implements Command {
    private final AbstractExpression expression;
    private final Context context;

    public ExpressionCommand(AbstractExpression expression, Context context) {
        this.expression = expression;
        this.context = context;
    }

    @Override
    public boolean doIt() {
        try {
            expression.interpret(context);
            return true;
        } catch (Exception e) {
            System.err.println("Errore nell'interpretazione del comando: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean undoIt() {
        // Questo dipende dalla logica delle espressioni; puoi aggiungere undo specifici se necessario
        return false;
    }
}
