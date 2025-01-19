package is.shapes.specificstrategy;

import java.awt.Color;

import is.strategy.ThemeStrategy;

public class DarkTheme implements ThemeStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(30, 30, 30); // Grigio scuro
    }

    @Override
    public Color getGridColor() {
        return new Color(150, 50, 50);
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }

    @Override
    public Color getShapeColor() {
        return Color.WHITE;
    }
}
