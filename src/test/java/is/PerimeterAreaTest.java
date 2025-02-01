package is;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.*;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

public class PerimeterAreaTest {
    private GraphicObjectPanel panel;
    private GraphicObject circle;
    
    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        circle = new CircleObject( new Point2D.Double(100, 100), 50);
        panel.add("C1", circle);
    }

    @Test
    @DisplayName("Calcolo area cerchio")
    void testArea() {
        assertEquals(Math.PI * 50 * 50, panel.calculateArea("C1"));
    }

    @Test
    @DisplayName("Calcolo perimetro cerchio")
    void testPerimeter() {
        assertEquals(2 * Math.PI * 50, panel.calculatePerimeter("C1"));
    }
}

