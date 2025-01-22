package is.shapes.specificbuilder;

import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import is.builder.GraphicObjectBuilder;
import is.shapes.model.GraphicObject;
import is.shapes.model.RectangleObject;

public class RectangleObjectBuilder implements GraphicObjectBuilder {
    private Point2D position;
    private double width;
    private double height;

    @Override
    public GraphicObjectBuilder setPosition(double x, double y) {
        this.position = new Point2D.Double(x, y);
        return this;
    }

    @Override
    public GraphicObjectBuilder setPosition(Point2D position) {
        this.position = position;
        return this;
    }

    @Override
    public GraphicObjectBuilder setDimension(double... dimensions) {
        if (dimensions.length != 2 || dimensions[0] <= 0 || dimensions[1] <= 0) {
            throw new IllegalArgumentException("Un rettangolo richiede due valori positivi (larghezza, altezza)");
        }
        this.width = dimensions[0];
        this.height = dimensions[1];
        return this;
    }

    @Override
    public GraphicObject build() {
        return new RectangleObject(position, width, height);
    }

    @Override
    public GraphicObjectBuilder setImage(ImageIcon image) {
        throw new UnsupportedOperationException("setImage non supportato per RectangleObject");
    }

    @Override
    public GraphicObjectBuilder addMember(GraphicObject member) {
        throw new UnsupportedOperationException("addMember non supportato per RectangleObject");
    }

    @Override
    public GraphicObjectBuilder addMembers(Iterable<GraphicObject> members) {
        throw new UnsupportedOperationException("addMembers non supportato per RectangleObject");
    }
}
