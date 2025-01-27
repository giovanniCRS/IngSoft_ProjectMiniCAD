package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.PerimeterCommand;

import java.io.StreamTokenizer;

public class PerimeterExpression extends TerminalExpression {

    public PerimeterExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String target = args[0];
        Command command = new PerimeterCommand(context.getPanel(), target);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String target = readWord(tokenizer, "target");
        return new String[]{target};
    }

    private static String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.sval;
    }
}