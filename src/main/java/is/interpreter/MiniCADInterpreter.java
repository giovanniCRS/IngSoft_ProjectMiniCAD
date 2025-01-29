package is.interpreter;

import is.command.CommandHandler;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.specificinterpreter.*;

import java.io.StreamTokenizer;

public class MiniCADInterpreter {
    private final Context context;

    public MiniCADInterpreter(GraphicObjectPanel panel, CommandHandler handler) {
        this.context = new Context(panel, handler);
    }

    public void executeCommand(String input) {
        try {
            AbstractExpression expression = parseCommand(input);
            expression.interpret(context);
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    private AbstractExpression parseCommand(String input) throws Exception {
        StreamTokenizer tokenizer = new StreamTokenizer(new java.io.StringReader(input));
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('_', '_');
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.whitespaceChars('\t', '\t');
        tokenizer.eolIsSignificant(false);

        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Comando non valido: " + input);
        }

        String comando = tokenizer.sval.toLowerCase();
        switch (comando) {
            case "new":
                return new NewExpression(tokenizer);
            case "mv":
                return new MoveExpression(tokenizer);
            case "mvoff":
                return new MoveOffsetExpression(tokenizer);
            case "scale":
                return new ScaleExpression(tokenizer);
            case "del":
                return new DeleteExpression(tokenizer);
            case "ls":
                return new ListExpression(tokenizer);
            case "grp":
                return new GroupExpression(tokenizer);
            case "ungrp":
                return new UngroupExpression(tokenizer);
            case "area":
                return new AreaExpression(tokenizer);
            case "perimeter":
                return new PerimeterExpression(tokenizer);
            case "color":
                return new ColorExpression(tokenizer);
            default:
                throw new Exception("Comando '" + comando + "' non riconosciuto");
        }
    }
}