package is.shapes.specificinterpreter;

import is.interpreter.AbstractExpression;

public abstract class NonTerminalExpression implements AbstractExpression {
    protected final AbstractExpression[] expressions;

    public NonTerminalExpression(AbstractExpression[] expressions) {
        this.expressions = expressions;
    }
}
