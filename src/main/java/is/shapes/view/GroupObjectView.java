package is.shapes.view;

import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;

//import java.awt.Color;
import java.awt.Graphics2D;

public class GroupObjectView implements GraphicObjectView {

    @Override
    public void drawGraphicObject(GraphicObject go, Graphics2D g2D) {
        if (!(go instanceof GroupObject)) {
            throw new IllegalArgumentException("L'oggetto non è un GroupObject");
        }
        
        /*
        GroupObject group = (GroupObject) go;
        Color groupColor = group.getGroupColor();
        g2D.setColor(groupColor);

        // Colora
        for (GraphicObject member : group.getMembers()) {
            GraphicObjectView memberView = GraphicObjectViewFactory.FACTORY.createView(member);
            if (memberView != null) {
                memberView.drawGraphicObject(member, g2D);

                // contorno immagine
                if (member.getType().equalsIgnoreCase("Image")) {
                    g2D.drawRect(
                        (int) (member.getPosition().getX() - member.getDimension().getWidth() / 2),
                        (int) (member.getPosition().getY() - member.getDimension().getHeight() / 2),
                        (int) member.getDimension().getWidth(),
                        (int) member.getDimension().getHeight()
                    );
                }
            }
        }
        g2D.setColor(Color.black);
        */
    }
}
