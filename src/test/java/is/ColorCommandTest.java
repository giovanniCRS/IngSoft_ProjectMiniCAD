package is;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.junit.jupiter.api.*;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.specificcommand.ColorCommand;

public class ColorCommandTest {
    private GraphicObjectPanel panel;
    private GraphicObject circle;
    
    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        circle = new CircleObject(new Point2D.Double(100, 100), 50);
        panel.add("C1", circle);
    }

    @Test
    @DisplayName("Cambiare colore di un oggetto")
    void testChangeColor() {
        ColorCommand colorCmd = new ColorCommand(panel, "C1", Color.RED);
        colorCmd.doIt();
        assertEquals(Color.RED, circle.getColor());
    }
}

