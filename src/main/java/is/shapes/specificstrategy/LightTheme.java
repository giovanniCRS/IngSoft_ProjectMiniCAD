package is.shapes.specificstrategy;

import java.awt.Color;

import is.strategy.ThemeStrategy;

public class LightTheme implements ThemeStrategy {
    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getGridColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getTextColor() {
        return Color.BLACK;
    }

    @Override
    public Color getShapeColor() {
        return Color.BLACK;
    }
}
