package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.UngroupCommand;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;

public class UngroupExpression extends TerminalExpression {

    public UngroupExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String groupId = args[0];

        GraphicObject group = context.getPanel().getObjectById(groupId);
        if (group == null) {
            System.err.println("Gruppo con ID '" + groupId + "' non trovato.");
            return;
        }

        Command command = new UngroupCommand(context.getPanel(), group);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String groupId = readWord(tokenizer, "ID del gruppo");
        return new String[]{groupId};
    }

    private static String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.sval;
    }
}