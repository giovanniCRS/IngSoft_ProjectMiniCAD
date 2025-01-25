package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class AreaCommandExpression implements AbstractExpression {
    private final String target;

    public AreaCommandExpression(String target) {
        this.target = target;
    }

    @Override
    public void interpret(Context context) throws Exception {
        double area = context.getPanel().calculateArea(target);
        System.out.printf("Area totale per il target '%s': %.2f%n", target, area);
    }
}
