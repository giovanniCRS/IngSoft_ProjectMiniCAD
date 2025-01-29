package is;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.Box;
//import javax.swing.ButtonGroup;

import is.command.HistoryCommandHandler;
import is.interpreter.MiniCADInterpreter;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.view.GraphicObjectViewFactory;
import is.shapes.view.GroupObjectView;
import is.shapes.view.CircleObjectView;
import is.shapes.view.RectangleObjectView;
import is.shapes.view.ImageObjectView;
import is.shapes.specificstrategy.DarkTheme;
import is.shapes.specificstrategy.LightTheme;

public class TestGraphicsMiniCAD {

    public static void main(String[] args) {
        JFrame f = new JFrame("MiniCAD Interpreter");

        // Toolbar con pulsanti Undo e Redo
        JToolBar toolbar = new JToolBar();
        JButton undoButt = new JButton("↩  Undo");
        JButton redoButt = new JButton("Redo  ↪");  

        final HistoryCommandHandler handler = new HistoryCommandHandler();

        undoButt.addActionListener(evt -> handler.undo());
        redoButt.addActionListener(evt -> handler.redo());
        toolbar.add(undoButt);
        toolbar.add(redoButt);

        // Pannello per gli oggetti grafici
        GraphicObjectPanel gpanel = new GraphicObjectPanel();
        gpanel.setPreferredSize(new Dimension(600, 600));

        // Installazione delle viste per i tipi di oggetti
        GraphicObjectViewFactory.FACTORY.installView(is.shapes.model.RectangleObject.class, new RectangleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(is.shapes.model.CircleObject.class, new CircleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(is.shapes.model.ImageObject.class, new ImageObjectView());
        GraphicObjectViewFactory.FACTORY.installView(is.shapes.model.GroupObject.class, new GroupObjectView());

        // Creazione dell'interprete MiniCAD
        MiniCADInterpreter interpreter = new MiniCADInterpreter(gpanel, handler);

        // Pannello per inserire comandi testuali
        JTextField commandInput = new JTextField(30);
        JButton executeButton = new JButton("Esegui comando");

        executeButton.addActionListener(evt -> {
            String command = commandInput.getText();
            if (!command.isEmpty()) {
                interpreter.executeCommand(command);
                commandInput.setText(""); // Pulisce il campo dopo l'esecuzione del comando
            }
        });

        commandInput.addActionListener(evt -> executeButton.doClick());// KeyListener per tasto Invio

        JPanel commandPanel = new JPanel(new FlowLayout());
        commandPanel.add(commandInput);
        commandPanel.add(executeButton);

        // Griglia
        JRadioButton grid = new JRadioButton("Griglia");
        grid.addActionListener(evt -> gpanel.toggleGrid());
        toolbar.add(grid);  

        // tema
        JRadioButton darkModeToggle = new JRadioButton("Modalità Scura");

        // Tema di default: chiaro
        darkModeToggle.setSelected(false);
        
        // Aggiungi l'azione per il toggle del tema
        darkModeToggle.addActionListener(evt -> {
            if (darkModeToggle.isSelected()) {
                gpanel.setTheme(new DarkTheme()); // Applica tema scuro
            } else {
                gpanel.setTheme(new LightTheme()); // Applica tema chiaro
            }
        });
        
        // Aggiungi il pulsante alla toolbar
        toolbar.add(darkModeToggle);

        // Pulsante "Comandi" con menu popup
        JButton commandsButton = new JButton("Comandi");
        JPopupMenu commandsMenu = new JPopupMenu();

        // Menu per "new"
        JMenu newMenu = new JMenu("CREA");
        JMenuItem newCircle = new JMenuItem("CERCHIO:           new circle <radius> <X> <Y>");
        JMenuItem newRectangle = new JMenuItem("RETTANGOLO:  new rectangle <width> <height> <X> <Y>");
        JMenuItem newImage = new JMenuItem("IMMAGINE:          new img \"src/is/shapes/model/NyaNya.gif\" <X> <Y>");

        newCircle.addActionListener(evt -> commandInput.setText("new circle <radius> <X> <Y>"));
        newRectangle.addActionListener(evt -> commandInput.setText("new rectangle <width> <height> <X> <Y>"));
        newImage.addActionListener(evt -> commandInput.setText("new img \"src/is/shapes/model/NyaNya.gif\" <X> <Y>"));

        newMenu.add(newCircle);
        newMenu.add(newRectangle);
        newMenu.add(newImage);

        // Menu per "mv"
        JMenu moveMenu = new JMenu("POSIZIONA");
        JMenuItem moveItem = new JMenuItem("OGGETTO:  mv <id> <X> <Y>");
        
        moveItem.addActionListener(evt -> commandInput.setText("mv <id> <X> <Y>"));
        
        moveMenu.add(moveItem);

        // Menu per "scale"
        JMenu scaleMenu = new JMenu("RIDIMENSIONA");
        JMenuItem scaleItem = new JMenuItem("OGGETTO:  scale <id> <factor>");

        scaleItem.addActionListener(evt -> commandInput.setText("scale <id> <factor>"));

        scaleMenu.add(scaleItem);

        // Menu per "del"
        JMenu deleteMenu = new JMenu("ELIMINA");
        JMenuItem deleteItem = new JMenuItem("OGGETTO:  del <id>");

        deleteItem.addActionListener(evt -> commandInput.setText("del <id>"));

        deleteMenu.add(deleteItem);

        // Menu per "mvoff"
        JMenu moveOffsetMenu = new JMenu("SPOSTA");
        JMenuItem moveOffsetItem = new JMenuItem("OGGETTO:  mvoff <id> <offsetX> <offsetY>");


        moveOffsetItem.addActionListener(evt -> commandInput.setText("mvoff <id> <offsetX> <offsetY>"));

        moveOffsetMenu.add(moveOffsetItem);

        // Menu per "ls"
        JMenu listMenu = new JMenu("VISUALIZZA");
        JMenuItem listItem = new JMenuItem("OGGETTO:       ls <id>");
        JMenuItem listAll = new JMenuItem("TUTTO:             ls all");
        JMenuItem listCircle = new JMenuItem("CERCHI:            ls circle");
        JMenuItem listRectangle = new JMenuItem("RETTANGOLI:  ls rectangle");
        JMenuItem listImage = new JMenuItem("IMMAGINI:         ls image");
        JMenuItem listGroups = new JMenuItem("GRUPPI:            ls groups");

        listAll.addActionListener(evt -> commandInput.setText("ls all"));
        listItem.addActionListener(evt -> commandInput.setText("ls <id>"));
        listCircle.addActionListener(evt -> commandInput.setText("ls circle"));
        listRectangle.addActionListener(evt -> commandInput.setText("ls rectangle"));
        listImage.addActionListener(evt -> commandInput.setText("ls image"));
        listGroups.addActionListener(evt -> commandInput.setText("ls groups"));

        listMenu.add(listItem);
        listMenu.add(listAll);
        listMenu.add(listCircle);
        listMenu.add(listRectangle);
        listMenu.add(listImage);
        listMenu.add(listGroups);

        // Menu per "grp"
        JMenu groupMenu = new JMenu("CREA GRUPPO");
        JMenuItem groupItem = new JMenuItem("RAGGRUPPA:  grp <id1>, <id2>, ...");

        groupItem.addActionListener(evt -> commandInput.setText("grp <id1>, <id2>, ..."));

        groupMenu.add(groupItem);

        // Menu per "ungrp"
        JMenu ungroupMenu = new JMenu("SCIOGLI GRUPPO");
        JMenuItem ungroupItem = new JMenuItem("SCOMPATTA:  ungrp <groupId>");

        ungroupItem.addActionListener(evt -> commandInput.setText("ungrp <groupId>"));

        ungroupMenu.add(ungroupItem);

        // Menu per "area"
        JMenu areaMenu = new JMenu("CALCOLA AREA");
        JMenuItem areaItem = new JMenuItem("OGGETTO:       area <id>"); 
        JMenuItem areaAll = new JMenuItem("TUTTI:               area all");
        JMenuItem areaCircle = new JMenuItem("CERCHI:            area circle");
        JMenuItem areaRectangle = new JMenuItem("RETTANGOLI:  area rectangle");
        JMenuItem areaImage = new JMenuItem("IMMAGINI:         area image");

        areaItem.addActionListener(evt -> commandInput.setText("area <id>")); 
        areaAll.addActionListener(evt -> commandInput.setText("area all"));
        areaCircle.addActionListener(evt -> commandInput.setText("area circle"));
        areaRectangle.addActionListener(evt -> commandInput.setText("area rectangle"));
        areaImage.addActionListener(evt -> commandInput.setText("area image"));

        areaMenu.add(areaItem);
        areaMenu.add(areaAll);
        areaMenu.add(areaCircle);
        areaMenu.add(areaRectangle);
        areaMenu.add(areaImage);

        // Menu per "perimeter"
        JMenu perimeterMenu = new JMenu("CALCOLA PERIMETRO");
        JMenuItem perimeterItem = new JMenuItem("OGGETTO:       perimeter <id>");
        JMenuItem perimeterAll = new JMenuItem("TUTTI:               perimeter all");
        JMenuItem perimeterCircle = new JMenuItem("CERCHI:            perimeter circle");
        JMenuItem perimeterRectangle = new JMenuItem("RETTANGOLI:  perimeter rectangle");
        JMenuItem perimeterImage = new JMenuItem("IMMAGINI:         perimeter image");

        perimeterItem.addActionListener(evt -> commandInput.setText("perimeter <id>"));
        perimeterAll.addActionListener(evt -> commandInput.setText("perimeter all"));
        perimeterCircle.addActionListener(evt -> commandInput.setText("perimeter circle"));
        perimeterRectangle.addActionListener(evt -> commandInput.setText("perimeter rectangle"));
        perimeterImage.addActionListener(evt -> commandInput.setText("perimeter image"));

        perimeterMenu.add(perimeterItem);
        perimeterMenu.add(perimeterAll);
        perimeterMenu.add(perimeterCircle);
        perimeterMenu.add(perimeterRectangle);
        perimeterMenu.add(perimeterImage);

        // Menu per "color"
        JMenu colorMenu = new JMenu("COLORE");
        JMenuItem colorItem = new JMenuItem("Seleziona colore...");

        colorItem.addActionListener(evt -> {
            JPanel colorPanel = new JPanel(new FlowLayout());
            colorPanel.add(new JLabel("Scegli colore:"));
            
            JComboBox<String> colorDropdown = new JComboBox<>(COLOR_PALETTE.keySet().toArray(new String[0]));
            colorPanel.add(colorDropdown);
            
            JButton applyColorButton = new JButton("Applica");
            applyColorButton.addActionListener(e -> {
                String selectedColorName = (String) colorDropdown.getSelectedItem();
                if (selectedColorName != null) {
                    // Imposta il comando nel campo di testo
                    commandInput.setText("color <id|type|all> " + selectedColorName.toLowerCase());
                }
                // Chiude il dialogo
                Window window = SwingUtilities.getWindowAncestor(colorPanel);
                if (window != null) {
                    window.dispose();
                }
            });

            colorPanel.add(applyColorButton);

            JOptionPane.showOptionDialog(null, colorPanel, "Seleziona colore", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new Object[]{"Annulla"}, 
                "Annulla"
            );
        });

        colorMenu.add(colorItem);

        // Aggiunta al menu
        commandsMenu.add(newMenu);
        commandsMenu.add(moveMenu);
        commandsMenu.add(moveOffsetMenu);
        commandsMenu.add(scaleMenu);
        commandsMenu.add(deleteMenu);
        commandsMenu.add(listMenu);
        commandsMenu.add(groupMenu);
        commandsMenu.add(ungroupMenu);
        commandsMenu.add(areaMenu);
        commandsMenu.add(perimeterMenu);
        commandsMenu.add(colorMenu);

        commandsButton.addActionListener(evt -> commandsMenu.show(commandsButton, commandsButton.getWidth() - commandsMenu.getPreferredSize().width, commandsButton.getHeight()));

        // Comandi sulla destra
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(commandsButton); 

        // Pulsante Informazioni (con icona "i")
        JButton infoButton = new JButton("ℹ Info");
        infoButton.addActionListener(evt -> showInfoDialog());
        toolbar.add(infoButton);

        // Layout della finestra principale
        f.add(toolbar, BorderLayout.NORTH);
        f.add(new JScrollPane(gpanel), BorderLayout.CENTER);
        f.add(commandPanel, BorderLayout.SOUTH);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    // info
    private static void showInfoDialog() {
            String infoMessage = """
                Benvenuto in MiniCAD!
                Ecco alcune regole per utilizzare l'app:

                - Clicca su "Comandi" per avere una visione completa di tutti i comandi da inserire nella barra di testo in basso, che sono supportati dall'app!
                - Usa la griglia spuntando la casella apposita nella barra degli strumenti, l'utilizzo sarà più intuitivo.
                - Se il bianco ti dà fastidio alla vista, spunta la casella apposita nella barra degli strumenti per passare alla modalità scura.

                [ATTENZIONE: In ogni comando consigliato che presenta qualcosa tra "<" e ">",
                                           è necessario sostituire tutto il valore all'interno, compresi i due 
                                           simboli appena citati (servono solo da guida all'inserimento).
                                           Es. <X> va sostituito con 350;
                                                   <width> va sostituito con 75;
                                                   <factor> va sostituito con 3 ,oppure con 0,5 ecc.. . 
                ]

                - Usa il comando "new" per creare oggetti.
                
                - Posiziona oggetti in un punto (X,Y) preciso con "mv";
                - Sposta oggetti lungo gli assi con "mvoff".

                - Ingrandisci o rimpicciolisci oggetti con "scale" di un certo factor (fattore moltiplicativo).

                - Altri comandi:
                - "ls" (visualizza oggetti)
                - "grp" (crea un gruppo di oggetti)
                - "ungrp" (sciogli un gruppo di oggetti)
                - "area" (calcola l'area di uno o più oggetti)
                - "perimeter" (calcola perimetro di uno o più oggetti)
                - "del" (elimina un oggetto)

                - Puoi annullare e ripetere azioni con i pulsanti Undo/Redo.


                - OGNI COMANDO CHE SUPPORTA GLI ID, VALE SIA PER OGGETTI CHE PER GRUPPI.

                Suggerimenti:
                - X orizzontale e Y verticale. Per spostare oggetti/gruppi verso sopra oppure verso sinistra, usa valori negativi
                - Usa "ls all" per visualizzare tutti gli oggetti presenti nel pannello.
                - Specifica un percorso immagine valido per il comando "new img".

            """;
            JOptionPane.showMessageDialog(null, infoMessage, "Informazioni sull'App", JOptionPane.INFORMATION_MESSAGE);
    }

    // Palette dei colori disponibili
    private static final Map<String, Color> COLOR_PALETTE = Map.of(
        "red", Color.RED,
        "orange", new Color(255, 165, 0),
        "yellow", Color.YELLOW,
        "green", Color.GREEN,
        "blue", Color.BLUE,
        "indigo", new Color(75, 0, 130),
        "violet", new Color(138, 43, 226)
    );

}