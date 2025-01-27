package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.DeleteCommand;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;

public class DeleteExpression extends TerminalExpression {

    public DeleteExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String id = args[0];

        GraphicObject object = context.getPanel().getObjectById(id);
        if (object == null) {
            System.err.println("Oggetto con ID '" + id + "' non trovato.");
            return;
        }

        Command command = new DeleteCommand(context.getPanel(), object, id);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String id = readWord(tokenizer, "ID");
        return new String[]{id};
    }

    private static String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.sval;
    }
}