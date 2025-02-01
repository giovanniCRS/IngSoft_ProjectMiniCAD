package is;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectPanel;

import java.awt.geom.Point2D;

public class GroupTest {
    private GraphicObjectPanel panel;
    private GraphicObject circle, rectangle;
    
    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        circle = new CircleObject(new Point2D.Double(100, 100), 50);
        rectangle = new RectangleObject( new Point2D.Double(200, 200), 60, 40);
        panel.add("C1", circle);
        panel.add("R1", rectangle);
    }

    @Test
    @DisplayName("Creazione di un gruppo")
    void testCreateGroup() {
        GroupObject group = new GroupObject(List.of(circle, rectangle));
        panel.add("G1", group);
        assertNotNull(panel.getObjectById("G1"));
    }

    @Test
    @DisplayName("Scioglimento di un gruppo")
    void testUngroup() {
        GroupObject group = new GroupObject(List.of(circle, rectangle));
        panel.add("G1", group);
        panel.remove("G1");
        assertNull(panel.getObjectById("G1"));
    }
}
