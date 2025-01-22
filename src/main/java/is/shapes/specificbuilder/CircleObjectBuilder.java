package is.shapes.specificbuilder;

import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import is.builder.GraphicObjectBuilder;
import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;

public class CircleObjectBuilder implements GraphicObjectBuilder {
    private Point2D position;
    private double radius;

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
        if (dimensions.length != 1 || dimensions[0] <= 0) {
            throw new IllegalArgumentException("Un cerchio richiede un solo valore positivo (raggio)");
        }
        this.radius = dimensions[0];
        return this;
    }

    @Override
    public GraphicObject build() {
        return new CircleObject(position, radius);
    }

    @Override
    public GraphicObjectBuilder setImage(ImageIcon image) {
        throw new UnsupportedOperationException("setImage non supportato per CircleObject");
    }

    @Override
    public GraphicObjectBuilder addMember(GraphicObject member) {
        throw new UnsupportedOperationException("addMember non supportato per CircleObject");
    }

    @Override
    public GraphicObjectBuilder addMembers(Iterable<GraphicObject> members) {
        throw new UnsupportedOperationException("addMembers non supportato per CircleObject");
    }
}
