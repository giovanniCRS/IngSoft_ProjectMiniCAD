package is;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.*;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

public class MoveScaleTest {
    private GraphicObjectPanel panel;
    private GraphicObject circle;
    
    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        circle = new CircleObject( new Point2D.Double(100, 100), 50);
        panel.add("C1", circle);
    }

    @Test
    @DisplayName("Spostamento di un oggetto")
    void testMove() {
        circle.moveTo(new Point2D.Double(200, 200));
        assertEquals(new Point2D.Double(200, 200), circle.getPosition());
    }

    @Test
    @DisplayName("Ridimensionamento di un oggetto")
    void testScale() {
        circle.scale(2);
        assertEquals(100, ((CircleObject) circle).getRadius());
    }
}

