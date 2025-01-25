package is.interpreter;

public class TerminalExpression implements AbstractExpression {
    private final String value;

    public TerminalExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void interpret(Context context) {
        // I TerminalExpression forniscono solo un valore, non necessitano ulteriori azioni
    }
}