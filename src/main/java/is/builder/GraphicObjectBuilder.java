package is.builder;

import java.awt.geom.Point2D;
import javax.swing.ImageIcon;

import is.shapes.model.GraphicObject;

public interface GraphicObjectBuilder {
    GraphicObjectBuilder setPosition(double x, double y);

    GraphicObjectBuilder setPosition(Point2D position);

    GraphicObjectBuilder setDimension(double... dimensions);

    GraphicObjectBuilder setImage(ImageIcon image);

    GraphicObjectBuilder addMember(GraphicObject member);

    GraphicObjectBuilder addMembers(Iterable<GraphicObject> members);

    GraphicObject build();
}
