package is.shapes.model;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphicObject implements GraphicObject, Cloneable {

	private  List<GraphicObjectListener> listeners = new LinkedList<>();
	
	private Color color = Color.BLACK; // Colore di default

	@Override
	public void addGraphicObjectListener(GraphicObjectListener l) {
		if (listeners.contains(l))
			return;
		listeners.add(l);
	}

	@Override
	public void removeGraphicObjectListener(GraphicObjectListener l) {
		listeners.remove(l);

	}

	protected void notifyListeners(GraphicEvent e) {

		for (GraphicObjectListener gol : listeners)

			gol.graphicChanged(e);

	}

	@Override
	public GraphicObject clone() {
		try {
			AbstractGraphicObject go = (AbstractGraphicObject) super.clone();
			go.listeners = new LinkedList<>();
			go.color = color;
			return go;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        notifyListeners(new GraphicEvent(this));
    }

}
