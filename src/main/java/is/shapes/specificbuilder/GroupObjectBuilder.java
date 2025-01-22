package is.shapes.specificbuilder;

import java.util.ArrayList;
import java.util.List;

import java.awt.geom.Point2D;

import is.builder.GraphicObjectBuilder;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;

public class GroupObjectBuilder implements GraphicObjectBuilder {
    private final List<GraphicObject> members = new ArrayList<>();

    @Override
    public GraphicObjectBuilder addMember(GraphicObject member) {
        if (member == null) {
            throw new IllegalArgumentException("Il membro non può essere null");
        }
        members.add(member);
        return this;
    }

    @Override
    public GraphicObjectBuilder addMembers(Iterable<GraphicObject> members) {
        for (GraphicObject member : members) {
            addMember(member);
        }
        return this;
    }

    @Override
    public GraphicObject build() {
        if (members.isEmpty()) {
            throw new IllegalStateException("Il gruppo deve contenere almeno un oggetto");
        }
        return new GroupObject(members);
    }

    @Override
    public GraphicObjectBuilder setPosition(double x, double y) {
        throw new UnsupportedOperationException("setPosition non supportato per GroupObject");
    }

    @Override
    public GraphicObjectBuilder setPosition(Point2D position) {
        throw new UnsupportedOperationException("setPosition non supportato per GroupObject");
    }

    @Override
    public GraphicObjectBuilder setDimension(double... dimensions) {
        throw new UnsupportedOperationException("setDimension non supportato per GroupObject");
    }

    @Override
    public GraphicObjectBuilder setImage(javax.swing.ImageIcon image) {
        throw new UnsupportedOperationException("setImage non supportato per GroupObject");
    }
}
