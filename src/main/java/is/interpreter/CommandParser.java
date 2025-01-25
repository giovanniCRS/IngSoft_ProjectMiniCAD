package is.interpreter;

import java.io.StreamTokenizer;
import java.io.StringReader;

import is.shapes.specificinterpreter.nonTerminalExpression.GroupCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.MoveCommandExpression;

public class CommandParser {
    public AbstractExpression parse(String input) throws Exception {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));

        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('_', '_');
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.eolIsSignificant(false);

        tokenizer.nextToken();
        String command = tokenizer.sval.toLowerCase();

        switch (command) {
            case "mv":
                return parseMoveCommand(tokenizer);

            case "grp":
                return parseGroupCommand(tokenizer);

            // Aggiungere altri comandi qui

            default:
                throw new Exception("Comando '" + command + "' non riconosciuto.");
        }
    }

    private AbstractExpression parseMoveCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String objectId = tokenizer.sval;

        tokenizer.nextToken();
        double x = tokenizer.nval;

        tokenizer.nextToken();
        double y = tokenizer.nval;

        return new MoveCommandExpression(objectId, x, y);
    }

    private AbstractExpression parseGroupCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String groupId = tokenizer.sval;

        GroupCommandExpression groupExpr = new GroupCommandExpression(groupId);

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            groupExpr.addChild(new TerminalExpression(tokenizer.sval));
        }
        return groupExpr;
    }
}
