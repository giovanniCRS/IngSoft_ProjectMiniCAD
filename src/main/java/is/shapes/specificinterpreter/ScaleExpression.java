package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.ZoomCommandWithID;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;

public class ScaleExpression extends TerminalExpression {

    public ScaleExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String id = args[0];
        double factor = Double.parseDouble(args[1]);

        GraphicObject object = context.getPanel().getObjectById(id);
        if (object == null) {
            System.err.println("Oggetto con ID '" + id + "' non trovato.");
            return;
        }

        Command command = new ZoomCommandWithID(object, factor, id);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String id = readWord(tokenizer, "ID");
        double factor = readNumber(tokenizer, "fattore di scala");
        return new String[]{id, String.valueOf(factor)};
    }

    private static String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.sval;
    }

    private static double readNumber(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.nval;
    }
}
