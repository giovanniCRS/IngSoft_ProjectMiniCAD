package is.shapes.specificinterpreter;

import is.command.Command;
import is.interpreter.Context;
import is.shapes.specificcommand.ColorCommand;
import is.shapes.view.GraphicObjectPanel;

import java.awt.Color;
import java.io.StreamTokenizer;

public class ColorExpression extends TerminalExpression {

    public ColorExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        String target = args[0]; // ID, tipo o "all"
        String colorName = args[1]; // Nome del colore

        // Converte il nome del colore in un oggetto Color
        Color color = parseColor(colorName);
        if (color == null) {
            System.err.println("Colore non valido: " + colorName);
            return;
        }

        // Crea il comando per cambiare il colore
        GraphicObjectPanel panel = context.getPanel();
        Command colorCmd = new ColorCommand(panel, target, color);

        // Esegui il comando
        if (colorCmd.doIt()) {
            context.getHandler().handle(colorCmd); // Passa il comando al gestore
        } else {
            System.err.println("Errore durante l'esecuzione del comando 'color'.");
        }
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        String target = readWord(tokenizer, "target");
        String colorName = readWord(tokenizer, "color");
        return new String[]{target, colorName};
    }

    private static String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.sval;
    }

    private static Color parseColor(String colorName) {
        return switch (colorName.toLowerCase()) {
            case "red" -> Color.RED;
            case "orange" -> new Color(255, 165, 0);
            case "yellow" -> Color.YELLOW;
            case "green" -> Color.GREEN;
            case "blue" -> Color.BLUE;
            case "indigo" -> new Color(75, 0, 130);
            case "violet" -> new Color(138, 43, 226);
            default -> null;
        };
    }
}