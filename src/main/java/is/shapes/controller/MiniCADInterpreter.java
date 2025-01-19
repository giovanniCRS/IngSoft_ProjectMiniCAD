package is.shapes.controller;
import is.command.Command;
import is.command.CommandHandler;
import is.command.HistoryCommandHandler;
import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectLogger;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.specificcommand.AreaCommand;
import is.shapes.specificcommand.DeleteCommand;
import is.shapes.specificcommand.GroupCommand;
import is.shapes.specificcommand.ListCommand;
import is.shapes.specificcommand.MoveCommand;
import is.shapes.specificcommand.MoveOffsetCommand;
import is.shapes.specificcommand.ZoomCommand;
import is.shapes.specificcommand.NewObjectCmdWithID;
import is.shapes.specificcommand.PerimeterCommand;
import is.shapes.specificcommand.UngroupCommand;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;
import java.util.Random;
//import java.security.SecureRandom;

public class MiniCADInterpreter {

    private final GraphicObjectPanel gpanel;
    private final CommandHandler handler;
    GraphicObjectLogger logger = new GraphicObjectLogger();

    public MiniCADInterpreter(GraphicObjectPanel gpanel, CommandHandler handler) {
        this.gpanel = gpanel;
        this.handler = handler;
    }

    public void executeCommand(String input) {
        try {
            Command command = parseCommand(input);
            handler.handle(command);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
        }
    }

    private Command parseCommand(String input) throws Exception {
        StreamTokenizer tokenizer = new StreamTokenizer(new java.io.StringReader(input));

        // Configura il tokenizer per trattare lettere, numeri e parole come token separati
        tokenizer.wordChars('0', '9'); // Considera i numeri come parte delle parole
        tokenizer.wordChars('a', 'z'); // Permetti lettere minuscole
        tokenizer.wordChars('A', 'Z'); // Permetti lettere maiuscole
        tokenizer.wordChars('_', '_'); // Permetti underscore negli ID
        tokenizer.whitespaceChars(' ', ' '); // Riconosci spazi come separatori
        tokenizer.whitespaceChars('\t', '\t'); // Riconosci tabulazioni come separatori
        tokenizer.eolIsSignificant(false); // Ignora fine riga come separatore

        tokenizer.nextToken(); // Leggi il primo token
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Comando non valido: " + input);
        }

        String comando = tokenizer.sval.toLowerCase(); // Comando principale
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
                return new Command() {
                    @Override
                    public boolean doIt() {
                        ((HistoryCommandHandler) handler).undo();
                        return true;
                    }

                    @Override
                    public boolean undoIt() {
                        return false;
                    }
                };
            case "redo":
                return new Command() {
                    @Override
                    public boolean doIt() {
                        ((HistoryCommandHandler) handler).redo();
                        return true;
                    }

                    @Override
                    public boolean undoIt() {
                        return false;
                    }
                };
            default:
                throw new Exception("Comando '" + comando + "' non riconosciuto");
        }
    }

    private Command createNewObjectCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken(); // Tipo di oggetto (es. "circle")
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Tipo di oggetto non valido per il comando 'new'");
        }
    
        String type = tokenizer.sval.toLowerCase();
        
        String id = generaId("obj");
    
        switch (type) {
            case "circle":
                tokenizer.nextToken(); // Raggio
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per il raggio");
                }
                double radius = tokenizer.nval;
    
                tokenizer.nextToken(); // Coordinata X
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                double x = tokenizer.nval;
    
                tokenizer.nextToken(); // Coordinata Y
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                double y = tokenizer.nval;
    
                GraphicObject circle = new CircleObject(new Point2D.Double(x, y), radius);
                circle.addGraphicObjectListener(logger);
                return new NewObjectCmdWithID(gpanel, circle, id);
    
            case "rectangle":
                tokenizer.nextToken(); // Larghezza
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per la larghezza");
                }
                double width = tokenizer.nval;
    
                tokenizer.nextToken(); // Altezza
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per l'altezza");
                }
                double height = tokenizer.nval;
    
                tokenizer.nextToken(); // Coordinata X
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                x = tokenizer.nval;
    
                tokenizer.nextToken(); // Coordinata Y
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                y = tokenizer.nval;
    
                GraphicObject rectangle = new RectangleObject(new Point2D.Double(x, y), width, height);
                rectangle.addGraphicObjectListener(logger);
                return new NewObjectCmdWithID(gpanel, rectangle, id);
    
            case "img":
                tokenizer.nextToken(); 
                if (tokenizer.ttype == '"') {
                    tokenizer.pushBack(); 
                    tokenizer.quoteChar('"'); 
                    tokenizer.nextToken(); 
                }
            
                if (tokenizer.ttype != StreamTokenizer.TT_WORD && tokenizer.ttype != '"') {
                    throw new Exception("Percorso immagine non valido o mancante. Usa virgolette se contiene spazi.");
                }
            
                String filePath = tokenizer.sval;
            
                tokenizer.nextToken(); // Coordinata X
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per X");
                }
                x = tokenizer.nval;
            
                tokenizer.nextToken(); // Coordinata Y
                if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
                    throw new Exception("Valore non valido per Y");
                }
                y = tokenizer.nval;
            
                GraphicObject image = new ImageObject(new javax.swing.ImageIcon(filePath), new Point2D.Double(x, y));
                image.addGraphicObjectListener(logger);
                return new NewObjectCmdWithID(gpanel, image, id);
    
            default:
                throw new Exception("Tipo di oggetto '" + type + "' non riconosciuto per il comando 'new'");
        }
    }

    private Command createMoveCommand(StreamTokenizer tokenizer) throws Exception {
        // Leggi il primo token: l'ID
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Errore nel comando 'mv': ID non valido o mancante.");
        }
        String id = tokenizer.sval; 
    
        // Leggi il secondo token: X
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Errore nel comando 'mv': valore X non valido.");
        }
        double newX = tokenizer.nval;
    
        // Leggi il terzo token: Y
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Errore nel comando 'mv': valore Y non valido.");
        }
        double newY = tokenizer.nval;
    
        // Verifica che esista un oggetto con l'ID specificato
        GraphicObject object = gpanel.getObjectById(id);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + id + "' non trovato.");
        }
    
        // Crea il comando per spostare l'oggetto
        return new MoveCommand(object, new Point2D.Double(newX, newY));
    }

    private Command createMoveOffsetCommand(StreamTokenizer tokenizer) throws Exception {

        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Errore nel comando 'mvoff': ID non valido o mancante");
        }
        String id = tokenizer.sval;
    
        // Leggi l'offset X
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Errore nel comando 'mvoff': valore offset X non valido");
        }
        double offsetX = tokenizer.nval;
    
        // Leggi l'offset Y
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Errore nel comando 'mvoff': valore offset Y non valido");
        }
        double offsetY = tokenizer.nval;
    
        GraphicObject object = gpanel.getObjectById(id);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + id + "' non trovato");
        }
    
        return new MoveOffsetCommand(object, offsetX, offsetY);
    }

    private Command createScaleCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Errore nel comando 'scale': ID non valido o mancante");
        }
        String id = tokenizer.sval;
    
       
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Errore nel comando 'scale': fattore di scala non valido");
        }
        double factor = tokenizer.nval;
    
       
        GraphicObject object = gpanel.getObjectById(id);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + id + "' non trovato");
        }
    
        return new ZoomCommand(object, factor);
    }

    private Command createDeleteCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken(); 
        String id = readWord(tokenizer, "ID dell'oggetto");
    
        
        GraphicObject object = gpanel.getObjectById(id);
        if (object == null) {
            throw new Exception("Oggetto con ID '" + id + "' non trovato");
        }
    
       
        return new DeleteCommand(gpanel, object, id);
    }

    private Command createListCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = readWord(tokenizer, "target per 'ls'");
        return new ListCommand(gpanel, target);
    }

    private Command createGroupCommand(StreamTokenizer tokenizer) throws Exception {
        List<String> ids = new ArrayList<>();
    
        // Leggi tutti i token fino alla fine
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                // dividi il token cpon virgole e aggiungi gli ID
                String[] idParts = tokenizer.sval.split(",");
                for (String id : idParts) {
                    String trimmedId = id.trim();
                    if (!trimmedId.isEmpty()) {
                        ids.add(trimmedId);
                    }
                }
            }
        }
    
        if (ids.size() < 2) {
            throw new Exception("Il comando 'grp' richiede almeno 2 ID");
        }
    
        List<GraphicObject> objects = new ArrayList<>();
        for (String id : ids) {
            GraphicObject object = gpanel.getObjectById(id);
            if (object == null) {
                throw new Exception("Oggetto con ID '" + id + "' non trovato");
            }
            objects.add(object);
        }
    
        String groupId = generaId("grp");
    
        return new GroupCommand(gpanel, objects, groupId);
    }

    private Command createUngroupCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        
        String groupId = readWord(tokenizer, "ID del gruppo");
    
    
        GraphicObject group = gpanel.getObjectById(groupId);
        if (group == null || !(group instanceof GroupObject)) {
            throw new Exception("Gruppo con ID '" + groupId + "' non trovato o non valido");
        }
    

        return new UngroupCommand(gpanel, group);
    }

    private Command createAreaCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = readWord(tokenizer, "target per 'area'");
    
        return new AreaCommand(gpanel, target);
    }

    private Command createPerimeterCommand(StreamTokenizer tokenizer) throws Exception {
        tokenizer.nextToken();
        String target = readWord(tokenizer, "target per 'perimeter'");
    
        return new PerimeterCommand(gpanel, target);
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

    /*private double readDouble(StreamTokenizer tokenizer, String fieldName) throws Exception {
        tokenizer.nextToken();
        if (tokenizer.ttype != StreamTokenizer.TT_NUMBER) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        return tokenizer.nval;
    } */
    

    private String readWord(StreamTokenizer tokenizer, String fieldName) throws Exception {
        if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
            throw new Exception("Valore non valido per " + fieldName);
        }
        String value = tokenizer.sval;
        tokenizer.nextToken();
        return value;
    }
    
}
