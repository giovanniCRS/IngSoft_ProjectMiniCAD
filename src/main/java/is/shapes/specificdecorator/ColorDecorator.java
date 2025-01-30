package is.shapes.specificdecorator;

import java.awt.Color;
import java.awt.Graphics2D;

import is.decorator.GraphicObjectViewDecorator;
import is.shapes.model.GraphicObject;
import is.shapes.specificstrategy.DarkTheme;
import is.shapes.view.GraphicObjectView;
import is.strategy.ThemeStrategy;

public class ColorDecorator extends GraphicObjectViewDecorator{
    private final ThemeStrategy theme;
    
        public ColorDecorator(GraphicObjectView decoratedView, ThemeStrategy theme) {
            super(decoratedView);
            this.theme = theme;
        }
    
        @Override
        public void drawGraphicObject(GraphicObject go, Graphics2D g) {
            Color objectColor = go.getColor();
            if (objectColor == null) {
                objectColor = Color.BLACK; // Colore di default se non è stato impostato
            }

            // Se siamo in modalità scura, trasformiamo il nero in bianco
            if (theme instanceof DarkTheme && objectColor.equals(Color.BLACK)) {
                objectColor = Color.WHITE;
            }

            Color originalColor = g.getColor();
            g.setColor(objectColor);

            // Disegna l'oggetto con il colore corretto
            decoratedView.drawGraphicObject(go, g);

            g.setColor(originalColor); // Ripristina il colore originale
        }
}
