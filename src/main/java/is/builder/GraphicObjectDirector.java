package is.builder;

import javax.swing.ImageIcon;

import is.shapes.model.GraphicObject;
import is.shapes.specificbuilder.*;

import java.util.List;

public class GraphicObjectDirector {

    public GraphicObject createCircle(double x, double y, double radius) {
        return new CircleObjectBuilder()
                .setPosition(x, y)
                .setDimension(radius)
                .build();
    }

    public GraphicObject createRectangle(double x, double y, double width, double height) {
        return new RectangleObjectBuilder()
                .setPosition(x, y)
                .setDimension(width, height)
                .build();
    }

    public GraphicObject createImage(double x, double y, ImageIcon image) {
        return new ImageObjectBuilder()
                .setPosition(x, y)
                .setImage(image)
                .build();
    }

    public GraphicObject createGroup(List<GraphicObject> members) {
        return new GroupObjectBuilder()
                .addMembers(members)
                .build();
    }
}
