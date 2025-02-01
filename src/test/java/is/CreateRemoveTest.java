package is;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

import java.awt.geom.Point2D;

public class CreateRemoveTest {
    private GraphicObjectPanel panel;
    private GraphicObject circle;
    
    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        circle = new CircleObject(new Point2D.Double(100, 100), 50);
    }
    
    @Test
    @DisplayName("Aggiunta di un oggetto")
    void testAddObject() {
        panel.add("C1", circle);
        assertNotNull(panel.getObjectById("C1"));
    }

    @Test
    @DisplayName("Rimozione di un oggetto")
    void testRemoveObject() {
        panel.add("C1", circle);
        panel.remove("C1");
        assertNull(panel.getObjectById("C1"));
    }
}
