package is.shapes.specificdecorator;

import java.awt.Color;
import java.awt.Graphics2D;

import is.decorator.GraphicObjectViewDecorator;
import is.shapes.model.GraphicObject;
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
            Color originalColor = g.getColor();
            
            // Usa il colore dell'oggetto se è stato impostato, altrimenti usa il colore del tema
            g.setColor(go.getColor() != null ? go.getColor() : theme.getShapeColor());
            
            // Disegna l'oggetto grafico utilizzando la vista decorata
            decoratedView.drawGraphicObject(go, g);

            // Ripristina il colore originale
            g.setColor(originalColor);
        }
}
