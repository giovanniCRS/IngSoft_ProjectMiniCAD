package is.shapes.model;

//import java.awt.Color;import java.util.Random;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class GroupObject extends AbstractGraphicObject {

    private final List<GraphicObject> members;
    //private final Color groupColor; // Colore associato al gruppo

    public GroupObject(List<GraphicObject> objects) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Il gruppo deve contenere almeno un oggetto");
        }
        this.members = new ArrayList<>(objects);
        //this.groupColor = generateRandomColor();
    }

    /* 
    public Color getGroupColor() {
        return groupColor;
    }

    private Color generateRandomColor() {
        Random random = new Random();
        float hue = random.nextFloat(); // Tonalità: un valore tra 0 e 1
        float saturation = 0.7f + random.nextFloat() * 0.3f; // Saturazione alta: tra 0.7 e 1.0
        float brightness = 0.7f + random.nextFloat() * 0.3f; // Luminosità alta: tra 0.7 e 1.0
        return Color.getHSBColor(hue, saturation, brightness);
    }
    */

    @Override
    public void moveTo(Point2D p) {
        Point2D oldPos = getPosition();
        double dx = p.getX() - oldPos.getX();
        double dy = p.getY() - oldPos.getY();
        for (GraphicObject obj : members) {
            Point2D objPos = obj.getPosition();
            obj.moveTo(new Point2D.Double(objPos.getX() + dx, objPos.getY() + dy));
        }
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public Point2D getPosition() {
        double x = 0, y = 0;
        for (GraphicObject obj : members) {
            x += obj.getPosition().getX();
            y += obj.getPosition().getY();
        }
        return new Point2D.Double(x / members.size(), y / members.size());
    }

    @Override
    public Dimension2D getDimension() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for (GraphicObject obj : members) {
            Point2D pos = obj.getPosition();
            Dimension2D dim = obj.getDimension();
            minX = Math.min(minX, pos.getX() - dim.getWidth() / 2);
            minY = Math.min(minY, pos.getY() - dim.getHeight() / 2);
            maxX = Math.max(maxX, pos.getX() + dim.getWidth() / 2);
            maxY = Math.max(maxY, pos.getY() + dim.getHeight() / 2);
        }

        Dimension2D groupDim = new Dimension();
        groupDim.setSize(maxX - minX, maxY - minY);
        return groupDim;
    }

    @Override
    public boolean contains(Point2D p) {
        for (GraphicObject obj : members) {
            if (obj.contains(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void scale(double factor) {
        for (GraphicObject obj : members) {
            obj.scale(factor);
        }
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public String getType() {
        return "Group";
    }

    public List<GraphicObject> getMembers() {
        return new ArrayList<>(members);
    }
}
