package is;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.*;

import is.shapes.specificstrategy.DarkTheme;
import is.shapes.specificstrategy.LightTheme;
import is.shapes.view.GraphicObjectPanel;

public class ThemeTest {
    private GraphicObjectPanel panel;

    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
    }

    @Test
    @DisplayName("Cambio a modalità scura")
    void testDarkTheme() {
        panel.setTheme(new DarkTheme());
        assertEquals(Color.WHITE, panel.getBorderColor());
    }

    @Test
    @DisplayName("Cambio a modalità chiara")
    void testLightTheme() {
        panel.setTheme(new LightTheme());
        assertEquals(Color.BLACK, panel.getBorderColor());
    }
}

