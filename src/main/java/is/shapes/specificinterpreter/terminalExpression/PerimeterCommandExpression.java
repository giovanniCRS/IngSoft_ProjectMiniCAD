package is.shapes.specificinterpreter.terminalExpression;

import is.interpreter.AbstractExpression;
import is.interpreter.Context;

public class PerimeterCommandExpression implements AbstractExpression {
    private final String target;

    public PerimeterCommandExpression(String target) {
        this.target = target;
    }

    @Override
    public void interpret(Context context) throws Exception {
        double perimeter = context.getPanel().calculatePerimeter(target);
        System.out.printf("Perimetro totale per il target '%s': %.2f%n", target, perimeter);
    }
}
