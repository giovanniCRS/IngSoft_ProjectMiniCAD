package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.MoveOffsetCommand;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;

public class MoveOffsetExpression extends TerminalExpression {

    public MoveOffsetExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String id = args[0];
        double offsetX = Double.parseDouble(args[1]);
        double offsetY = Double.parseDouble(args[2]);

        GraphicObject object = context.getPanel().getObjectById(id);
        if (object == null) {
            System.err.println("Oggetto con ID '" + id + "' non trovato.");
            return;
        }

        Command command = new MoveOffsetCommand(object, offsetX, offsetY, id, object.getPosition());
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String id = readWord(tokenizer, "ID");
        double offsetX = readNumber(tokenizer, "offsetX");
        double offsetY = readNumber(tokenizer, "offsetY");
        return new String[]{id, String.valueOf(offsetX), String.valueOf(offsetY)};
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
