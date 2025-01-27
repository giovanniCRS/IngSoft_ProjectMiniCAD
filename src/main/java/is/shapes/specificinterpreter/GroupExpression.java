package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.GroupCommand;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupExpression extends TerminalExpression {

    public GroupExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        List<String> ids = new ArrayList<>();
        for (String arg : args) {
            ids.add(arg);
        }

        List<GraphicObject> objects = new ArrayList<>();
        for (String id : ids) {
            GraphicObject object = context.getPanel().getObjectById(id);
            if (object == null) {
                System.err.println("Oggetto con ID '" + id + "' non trovato.");
                return;
            }
            objects.add(object);
        }

        String groupId = generaId("grp");
        Command command = new GroupCommand(context.getPanel(), objects, groupId);
        context.getHandler().handle(command);
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        List<String> ids = new ArrayList<>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                String[] idParts = tokenizer.sval.split(",");
                for (String id : idParts) {
                    String trimmedId = id.trim();
                    if (!trimmedId.isEmpty()) {
                        ids.add(trimmedId);
                    }
                }
            }
        }
        return ids.toArray(new String[0]);
    }

    private static String generaId(String tipo) {
        Random rnd = new Random();
        char lettera = (char) ('A' + rnd.nextInt(26));
        int number = rnd.nextInt(100000);
        return lettera + String.format("%05d", number);
    }
}