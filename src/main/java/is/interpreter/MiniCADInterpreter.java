package is.interpreter;
import is.command.CommandHandler;
//import is.builder.GraphicObjectDirector;
//import is.command.Command;
//import is.command.CommandHandler;
//import is.command.HistoryCommandHandler;
import is.shapes.model.CircleObject;
//import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
//import is.shapes.model.GroupObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.specificcommand.ExpressionCommand;
//import is.shapes.model.ImageObject;
//import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectLogger;
import is.shapes.view.GraphicObjectPanel;
//import is.shapes.specificcommand.AreaCommand;
//import is.shapes.specificcommand.DeleteCommand;
//import is.shapes.specificcommand.GroupCommand;
//import is.shapes.specificcommand.ListCommand;
//import is.shapes.specificcommand.MoveCommand;
//import is.shapes.specificcommand.MoveOffsetCommand;
//import is.shapes.specificcommand.ZoomCommand;
import is.shapes.specificinterpreter.nonTerminalExpression.GroupCommandExpression;
import is.shapes.specificinterpreter.nonTerminalExpression.UngroupCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.AreaCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.DeleteCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.ListCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.MoveCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.MoveOffsetCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.NewObjectCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.PerimeterCommandExpression;
import is.shapes.specificinterpreter.terminalExpression.RedoExpression;
import is.shapes.specificinterpreter.terminalExpression.ScaleCommandExpression;
//import is.shapes.specificcommand.NewObjectCmdWithID;
//import is.shapes.specificcommand.PerimeterCommand;
//import is.shapes.specificcommand.UngroupCommand;
import is.shapes.specificinterpreter.terminalExpression.UndoExpression;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.StreamTokenizer;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
import java.util.Random;
//import java.security.SecureRandom;

public class MiniCADInterpreter {
    private final Context context;

    public MiniCADInterpreter(GraphicObjectPanel gpanel, CommandHandler handler) {
        GraphicObjectLogger logger = new GraphicObjectLogger();
        this.context = new Context(gpanel, logger, handler);
    }

    public void executeCommand(String input) {
        try {
            AbstractExpression command = parseCommand(input);
            if (command instanceof UndoExpression || command instanceof RedoExpression) {
                command.interpret(context); // Interpreta direttamente
            } else {
                context.getCommandHandler().handle(new ExpressionCommand(command, context)); // Salva nella storia
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
        }
    }

    private AbstractExpression parseCommand(String input) throws Exception {
        StreamTokenizer tokenizer = new StreamTokenizer(new java.io.StringReader(input));
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('_', '_');
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.eolIsSignificant(false);

        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Comando non valido: " + input);
        }

        String comando = tokenizer.sval.toLowerCase();
        switch (comando) {
            case "new":
                return createNewObjectCommand(tokenizer);
            case "mv":
                return createMoveCommand(tokenizer);
            case "mvoff":
                return createMoveOffsetCommand(tokenizer);
            case "scale":
                return createScaleCommand(tokenizer);
            case "del":
                return createDeleteCommand(tokenizer);
            case "ls":
                return createListCommand(tokenizer);
            case "grp":
                return createGroupCommand(tokenizer);
            case "ungrp":
                return createUngroupCommand(tokenizer);
            case "area":
                return createAreaCommand(tokenizer);
            case "perimeter":
                return createPerimeterCommand(tokenizer);
            case "undo": 
                return new UndoExpression();
            case "redo": 
                return new RedoExpression();
            default:
                throw new Exception("Comando '" + comando + "' non riconosciuto.");
        }
    }

    private AbstractExpression createNewObjectCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken(); // Tipo di oggetto
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Tipo di oggetto non valido per il comando 'new'");
        }

        String type = tokenizer.sval.toLowerCase();
        String id = generaId("obj");
        GraphicObject object;

        switch (type) {
            case "circle":
                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per il raggio");
                }
                double radius = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                double x = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                double y = tokenizer.nval;

                object = new CircleObject(new Point2D.Double(x, y), radius);
                break;

            case "rectangle":
                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per la larghezza");
                }
                double width = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per l'altezza");
                }
                double height = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                x = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                y = tokenizer.nval;

                object = new RectangleObject(new Point2D.Double(x, y), width, height);
                break;

            case "img":
                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_WORD && tokenizer.ttype != '"') {
                    throw new Exception("Percorso immagine non valido o mancante. Usa virgolette se contiene spazi.");
                }
                String filePath = tokenizer.sval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                x = tokenizer.nval;

                tokenizer.nextToken();
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                y = tokenizer.nval;

                object = new ImageObject(new javax.swing.ImageIcon(filePath), new Point2D.Double(x, y));
                break;

            default:
                throw new Exception("Tipo di oggetto '" + type + "' non riconosciuto per il comando 'new'");
        }

        return new NewObjectCommandExpression(id, object);
    }

    private AbstractExpression createMoveCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String id = tokenizer.sval;

        tokenizer.nextToken();
        double x = tokenizer.nval;

        tokenizer.nextToken();
        double y = tokenizer.nval;

        return new MoveCommandExpression(id, x, y);
    }

    private AbstractExpression createMoveOffsetCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String id = tokenizer.sval;

        tokenizer.nextToken();
        double offsetX = tokenizer.nval;

        tokenizer.nextToken();
        double offsetY = tokenizer.nval;

        return new MoveOffsetCommandExpression(id, offsetX, offsetY);
    }

    private AbstractExpression createScaleCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String id = tokenizer.sval;

        tokenizer.nextToken();
        double factor = tokenizer.nval;

        return new ScaleCommandExpression(id, factor);
    }

    private AbstractExpression createDeleteCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String id = tokenizer.sval;

        return new DeleteCommandExpression(id);
    }

    private AbstractExpression createListCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = tokenizer.sval;

        return new ListCommandExpression(target);
    }

    private AbstractExpression createGroupCommand(StreamTokenizer tokenizer) throws Exception {
        String groupId = generaId("grp");
        GroupCommandExpression groupExpr = new GroupCommandExpression(groupId);

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                String[] ids = tokenizer.sval.split(",");
                for (String id : ids) {
                    groupExpr.addChild(new TerminalExpression(id.trim()));
                }
            }
        }

        if (groupExpr.getChildren().size() < 2) {
            throw new Exception("Il comando 'grp' richiede almeno 2 ID.");
        }

        return groupExpr;
    }

    private AbstractExpression createUngroupCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String groupId = tokenizer.sval;

        return new UngroupCommandExpression(groupId);
    }

    private AbstractExpression createAreaCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = tokenizer.sval;

        return new AreaCommandExpression(target);
    }

    private AbstractExpression createPerimeterCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = tokenizer.sval;

        return new PerimeterCommandExpression(target);
    }

    private String generaId(String tipo) {
        Random rnd = new Random();
        char lettera = (char) ('A' + rnd.nextInt(26));
        if (tipo.equals("obj")) {
            int number = rnd.nextInt(1000);
            return lettera + String.format("%03d", number);
        } else if (tipo.equals("grp")) {
            int number = rnd.nextInt(100000);
            return lettera + String.format("%05d", number);
        } else {
            throw new IllegalArgumentException("Tipo non valido per la generazione di ID: " + tipo);
        }
    }
}