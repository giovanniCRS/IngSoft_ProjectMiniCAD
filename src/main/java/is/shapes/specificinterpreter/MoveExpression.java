package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.MoveCommandWithID;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;

public class MoveExpression extends TerminalExpression {

    public MoveExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String id = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);

        GraphicObject object = context.getPanel().getObjectById(id);
        if (object == null) {
            System.err.println("Oggetto con ID '" + id + "' non trovato.");
            return;
        }

        Command command = new MoveCommandWithID(object, new java.awt.geom.Point2D.Double(x, y), id);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String id = readWord(tokenizer, "ID");
        double x = readNumber(tokenizer, "X");
        double y = readNumber(tokenizer, "Y");
        return new String[]{id, String.valueOf(x), String.valueOf(y)};
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
