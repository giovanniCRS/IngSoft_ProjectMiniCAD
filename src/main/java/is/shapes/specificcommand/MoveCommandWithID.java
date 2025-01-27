package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;

import java.awt.geom.Point2D;

public class MoveCommandWithID implements Command {

	private  final Point2D oldPos;

	private  final Point2D newPos;

	private  final GraphicObject object;

	private  final String id;
	
	public MoveCommandWithID(GraphicObject go, Point2D pos, String id) {
		oldPos = go.getPosition();
		newPos = pos;
		this.object = go;
		this.id = id;
	}

	@Override
	public boolean doIt() {
		System.out.printf("[%s] posizionato in (%s, %s) con ID: %s.%n", object.getType(), newPos.getX(), newPos.getY(), id);
		object.moveTo(newPos);
		return true;
	}

	@Override
	public boolean undoIt() {
		System.out.printf("[%s] riposizionato in (%s, %s) con ID: %s.%n", object.getType(), oldPos.getX(), oldPos.getY(), id);
		object.moveTo(oldPos);
		return true;
	}

}
