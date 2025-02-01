package is;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.*;

import is.command.HistoryCommandHandler;
import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.CircleObject;
//import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.view.CreateObjectAction;

public class UndoRedoTest {
    private GraphicObjectPanel panel;
    private HistoryCommandHandler handler;
    private AbstractGraphicObject prototype;
    private CreateObjectAction createAction;

    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        handler = new HistoryCommandHandler();
        prototype = new CircleObject(new Point2D.Double(100, 100), 50);
        //prototype = new RectangleObject(new Point2D.Double(100, 100), 50, 100);
        createAction = new CreateObjectAction(prototype, panel, handler); 
    }

    @Test
    @DisplayName("Test Undo/Redo Creazione Oggetto")
    void testUndoRedoCreate() {
        // Esegui l'azione di creazione
        createAction.actionPerformed(null);
        assertEquals(1, panel.getAllObjects().size(), "Dovrebbe esserci un oggetto");

        // Undo della creazione
        handler.undo();
        assertEquals(0, panel.getAllObjects().size(), "Dovrebbe essere stato rimosso con undo");

        // Redo della creazione
        handler.redo();
        assertEquals(1, panel.getAllObjects().size(), "L'oggetto dovrebbe essere stato ripristinato con redo");
    }
}

