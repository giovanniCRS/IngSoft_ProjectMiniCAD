package is.shapes.specificbuilder;

import java.awt.geom.Point2D;
import javax.swing.ImageIcon;

import is.builder.GraphicObjectBuilder;
import is.shapes.model.GraphicObject;
import is.shapes.model.ImageObject;

public class ImageObjectBuilder implements GraphicObjectBuilder {
    private ImageIcon image;
    private Point2D position;

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
    public GraphicObjectBuilder setImage(ImageIcon image) {
        this.image = image;
        return this;
    }

    @Override
    public GraphicObject build() {
        if (image == null) {
            throw new IllegalStateException("L'immagine deve essere specificata");
        }
        return new ImageObject(image, position);
    }

    @Override
    public GraphicObjectBuilder setDimension(double... dimensions) {
        throw new UnsupportedOperationException("setDimension non supportato per ImageObject");
    }

    @Override
    public GraphicObjectBuilder addMember(GraphicObject member) {
        throw new UnsupportedOperationException("addMember non supportato per ImageObject");
    }

    @Override
    public GraphicObjectBuilder addMembers(Iterable<GraphicObject> members) {
        throw new UnsupportedOperationException("addMembers non supportato per ImageObject");
    }
}
