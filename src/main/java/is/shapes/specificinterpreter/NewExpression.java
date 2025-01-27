package is.shapes.specificinterpreter;

import is.interpreter.Context;
import is.command.Command;
import is.shapes.specificcommand.NewObjectCmdWithID;
import is.builder.GraphicObjectDirector;
import is.shapes.model.GraphicObject;

import java.io.StreamTokenizer;
import java.util.Random;

public class NewExpression extends TerminalExpression {

    public NewExpression(StreamTokenizer tokenizer) throws Exception {
        super(parseArguments(tokenizer));
    }

    @Override
    public void interpret(Context context) {
        try {
            String type = args[0];
            String id = args[1];
            GraphicObjectDirector director = new GraphicObjectDirector();
            GraphicObject createdObject;

            switch (type.toLowerCase()) {
                case "circle": {
                    double radius = Double.parseDouble(args[2]);
                    double x = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    createdObject = director.createCircle(x, y, radius);
                    break;
                }
                case "rectangle": {
                    double width = Double.parseDouble(args[2]);
                    double height = Double.parseDouble(args[3]);
                    double x = Double.parseDouble(args[4]);
                    double y = Double.parseDouble(args[5]);
                    createdObject = director.createRectangle(x, y, width, height);
                    break;
                }
                case "img": {
                    String filePath = args[2]; // Percorso immagine
                    double x = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    createdObject = director.createImage(x, y, new javax.swing.ImageIcon(filePath));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Tipo di oggetto non riconosciuto: " + type);
            }

            Command command = new NewObjectCmdWithID(context.getPanel(), createdObject, id);
            context.getHandler().handle(command);

        } catch (Exception e) {
            System.err.println("Errore durante l'interpretazione del comando 'new': " + e.getMessage());
        }
    }

    private static String[] parseArguments(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Tipo di oggetto non valido per il comando 'new'");
        }
        String type = tokenizer.sval.toLowerCase();

        String id = generaId("obj");

        double[] params;
        switch (type) {
            case "circle":
                params = new double[3];
                params[0] = readNumber(tokenizer, "raggio");
                params[1] = readNumber(tokenizer, "X");
                params[2] = readNumber(tokenizer, "Y");
                break;
            case "rectangle":
                params = new double[4];
                params[0] = readNumber(tokenizer, "larghezza");
                params[1] = readNumber(tokenizer, "altezza");
                params[2] = readNumber(tokenizer, "X");
                params[3] = readNumber(tokenizer, "Y");
                break;
            case "img":
                String filePath = readFilePath(tokenizer, "percorso immagine");
                params = new double[2];
                params[0] = readNumber(tokenizer, "X");
                params[1] = readNumber(tokenizer, "Y");
            
                String[] resultImg = new String[params.length + 3]; 
                resultImg[0] = type;
                resultImg[1] = id;
                resultImg[2] = filePath;
                resultImg[3] = String.valueOf(params[0]);
                resultImg[4] = String.valueOf(params[1]);
                return resultImg;
            default:
                throw new Exception("Tipo di oggetto non riconosciuto: " + type);
        }

        String[] result = new String[params.length + 2];
        result[0] = type;
        result[1] = id;
        for (int i = 0; i < params.length; i++) {
            result[i + 2] = String.valueOf(params[i]);
        }
        return result;
    }

    private static double readNumber(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.nval;
    }

    private static String readFilePath(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD && tokenizer.ttype != '"') {
            throw new Exception("Percorso file non valido per " + fieldName + ". Usa virgolette se contiene spazi.");
        }
        return tokenizer.sval;
    }    

    private static String generaId(String tipo) {
        Random rnd = new Random();
        char lettera = (char) ('A' + rnd.nextInt(26));
        int number = rnd.nextInt(1000);
        return lettera + String.format("%03d", number);
    }
}