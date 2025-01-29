package is.decorator;

import java.awt.Graphics2D;

import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectView;

public abstract class GraphicObjectViewDecorator implements GraphicObjectView {
    protected final GraphicObjectView decoratedView;

    public GraphicObjectViewDecorator(GraphicObjectView decoratedView) {
        this.decoratedView = decoratedView;
    }

    @Override
    public void drawGraphicObject(GraphicObject go, Graphics2D g) {
        decoratedView.drawGraphicObject(go, g);
    }
}
