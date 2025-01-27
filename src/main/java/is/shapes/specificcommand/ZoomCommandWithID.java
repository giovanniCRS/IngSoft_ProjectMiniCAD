package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;

public class ZoomCommandWithID implements Command {
	
	private final GraphicObject go;
	private final double factor;
	private final String id;

	public ZoomCommandWithID(GraphicObject go, double factor, String id) {
		this.go = go;
		this.factor = factor;
		this.id=id;
	}

	@Override
	public boolean doIt() {
		System.out.println(String.format("[%s] con ID %s ridimensionato con fattore: %s.", go.getType(), id, factor));
		go.scale(factor);
		return true;
	}

	@Override
	public boolean undoIt() {
		System.out.println(String.format("[%s] con ID %s tornato alle dimensioni precedenti.", go.getType(), id));
		go.scale(1.0 / factor);
		return true;
	}

}
