package is;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import is.command.HistoryCommandHandler;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.view.GraphicObjectViewFactory;
import is.shapes.view.GroupObjectView;
import is.shapes.view.CircleObjectView;
import is.shapes.view.RectangleObjectView;
import is.shapes.view.ImageObjectView;
import is.shapes.controller.MiniCADInterpreter;

public class TestGraphicsMiniCAD {

    public static void main(String[] args) {
        JFrame f = new JFrame("MiniCAD Interpreter");

        // Toolbar con pulsanti Undo e Redo
        JToolBar toolbar = new JToolBar();
        JButton undoButt = new JButton("Undo");
        JButton redoButt = new JButton("Redo");  

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

        // Layout della finestra principale
        f.add(toolbar, BorderLayout.NORTH);
        f.add(new JScrollPane(gpanel), BorderLayout.CENTER);
        f.add(commandPanel, BorderLayout.SOUTH);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}