package is.shapes.specificinterpreter;

import is.interpreter.AbstractExpression;

public abstract class TerminalExpression implements AbstractExpression {
    protected final String[] args;

    public TerminalExpression(String[] args) {
        this.args = args;
    }
}
