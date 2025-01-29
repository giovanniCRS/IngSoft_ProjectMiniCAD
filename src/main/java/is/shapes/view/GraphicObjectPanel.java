package is.shapes.view;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicEvent;
import is.shapes.model.GraphicObject;
import is.shapes.model.GraphicObjectListener;
import is.shapes.model.GroupObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.specificdecorator.ColorDecorator;
import is.shapes.specificstrategy.LightTheme;
import is.strategy.ThemeStrategy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

public class GraphicObjectPanel extends JComponent implements GraphicObjectListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8993548105090978185L;

	/**
	 * @directed true
	 */

	private final List<GraphicObject> objects = new LinkedList<>();

	// griglia
	private boolean showGrid = false;

	// tema
    private ThemeStrategy theme = new LightTheme();

    public void setTheme(ThemeStrategy theme) {
        this.theme = theme;
        repaint(); // Aggiorna il pannello
    }
/* 
	public GraphicObjectPanel() {
		setBackground(Color.WHITE);
	}
*/
	@Override
	public void graphicChanged(GraphicEvent e) {
		repaint();
		revalidate();

	}

	
	public GraphicObject getGraphicObjectAt(Point2D p) {
		for (GraphicObject g : objects) {
			if (g.contains(p))
				return g;
		}
		return null;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension ps = super.getPreferredSize();
		double x = ps.getWidth();
		double y = ps.getHeight();
		for (GraphicObject go : objects) {
			double nx = go.getPosition().getX() + go.getDimension().getWidth() / 2;
			double ny = go.getPosition().getY() + go.getDimension().getHeight() / 2;
			if (nx > x)
				x = nx;
			if (ny > y)
				y = ny;
		}
		return new Dimension((int) x, (int) y);
	}
/* 
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Color originalColor = g2.getColor();

		// griglia
		if (showGrid) {
            drawGrid(g2);
        }

		g2.setColor(originalColor);

		for (GraphicObject go : objects) {
			GraphicObjectView view = GraphicObjectViewFactory.FACTORY.createView(go);
			view.drawGraphicObject(go, g2);
		}

	}
*/	
	public void add(GraphicObject go) {
		objects.add(go);
		go.addGraphicObjectListener(this);
		repaint();
	}//add del corso

	public void remove(GraphicObject go) {
		if (objects.remove(go)) {
			repaint();
			go.removeGraphicObjectListener(this);
		}

	}//remove del corso
	
	/* -------------------------------------------Modifiche mie------------------------------------------------- */ 

	// Mappa ID-oggetto
	private final Map<String, GraphicObject> objectMap = new HashMap<>(); 

	public Map<String, GraphicObject> getObjectMap() {
		return objectMap;
	}

	// add
	public void add(String id, GraphicObject go) {
		if (objectMap.containsKey(id)) {
			throw new IllegalArgumentException("ID già esistente: " + id);
		}
		objectMap.put(id, go); // Aggiunge l'oggetto alla mappa
		objects.add(go);
		go.addGraphicObjectListener(this);
		repaint();
	}

	//remove
	public void remove(String id) {
		GraphicObject go = objectMap.remove(id);
		if (go != null) {
			objects.remove(go);
			repaint();
			go.removeGraphicObjectListener(this);
		}
	}

	// Restituisce l'oggetto tramite ID
	public GraphicObject getObjectById(String id) {
		return objectMap.get(id); 
	}

	// list
	public void listObjects(String target) {
		boolean trovato = false; 
		Set<GraphicObject> giaVisti = new HashSet<>(); // Per evitare duplicati
	
		if (target.equalsIgnoreCase("all")) {
			System.out.println("LISTA DI TUTTI GLI OGGETTI PRESENTI:");
		} else {
			System.out.println("LISTA DI OGGETTO/I PRESENTE/I RICHIESTI:");
		}
	
		switch (target.toLowerCase()) {
			case "all": 
				for (GraphicObject go : objects) {
					if (!giaVisti.contains(go)) { // Evita duplicati
						printObjectDetails(go);
						giaVisti.add(go);
						trovato = true;
					}
				}
				break;
	
			case "circle": 
			case "rectangle":
			case "image":
				for (GraphicObject go : objects) {
					if (go.getType().equalsIgnoreCase(target) && !giaVisti.contains(go)) {
						printObjectDetails(go);
						giaVisti.add(go);
						trovato = true;
					}
				}
				break;
	
			case "groups": 
				for (GraphicObject go : objects) {
					if (go instanceof GroupObject && !giaVisti.contains(go)) {
						printObjectDetails(go);
						giaVisti.add(go);
						trovato = true;
					}
				}
				break;
	
			default: // Ricerca di un oggetto specifico tramite ID
				GraphicObject go = getObjectById(target);
				if (go != null) {
					printObjectDetails(go);
					trovato = true;
				} else {
					System.out.println("Oggetto con ID '" + target + "' non trovato.");
					return; 
				}
		}
	
		if (!trovato) {
			System.out.println("- Nessun oggetto " + target + " trovato. ");
		}
	}
	
	private void printObjectDetails(GraphicObject go) {
		String id = getObjectId(go);
		if (go instanceof CircleObject) {
			CircleObject circle = (CircleObject) go;
			System.out.printf("- Circle: ID=%s, radius=%.2f, position=(%.2f, %.2f)%n",
					id, circle.getRadius(), circle.getPosition().getX(), circle.getPosition().getY());
		} else if (go instanceof RectangleObject) {
			RectangleObject rectangle = (RectangleObject) go;
			System.out.printf("- Rectangle: ID=%s, width=%.2f, height=%.2f, position=(%.2f, %.2f)%n",
					id, rectangle.getDimension().getWidth(), rectangle.getDimension().getHeight(),
					rectangle.getPosition().getX(), rectangle.getPosition().getY());
		} else if (go instanceof ImageObject) {
			ImageObject image = (ImageObject) go;
			System.out.printf("- Image: ID=%s, dimensions=(%.2f x %.2f), position=(%.2f, %.2f)%n",
					id, image.getDimension().getWidth(), image.getDimension().getHeight(),
					image.getPosition().getX(), image.getPosition().getY());
		} else if (go instanceof GroupObject) {
			GroupObject group = (GroupObject) go;
			int totalMembers = countGroupMembers(group); // Conta tutti i membri, ricorsivamente
			System.out.printf("- Group: ID=%s, members=%d->[%s]%n", 
				id, 
				totalMembers, 
				getMembersDetails(group));
		} else {
			System.out.printf("- Oggetto sconosciuto: ID=%s, type=%s%n", id, go.getType());
		}
	}

	// Metodo ricorsivo per contare i membri
	private int countGroupMembers(GroupObject group) {
		int count = 0;
		for (GraphicObject member : group.getMembers()) {
			if (member instanceof GroupObject) {
				count += countGroupMembers((GroupObject) member); // Conta ricorsivamente
			} else {
				count++;
			}
		}
		return count;
	}

	private String getObjectId(GraphicObject go) {
		// Cerca l'ID dell'oggetto nella mappa objectMap
		for (Map.Entry<String, GraphicObject> entry : objectMap.entrySet()) {
			if (entry.getValue() == go) {
				return entry.getKey();
			}
		}
		return "Sconosciuto";
	}

	/* 
	private String getMembersDetails(GroupObject group) {
		StringBuilder details = new StringBuilder();
		for (GraphicObject member : group.getMembers()) {
			String memberId = getObjectId(member);
			details.append(memberId).append(", ");
		}
		if (details.length() > 0) {
			details.setLength(details.length() - 2); // Rimuove l'ultima virgola e spazio
		}
		return details.toString();
	}
	*/
	private String getMembersDetails(GroupObject group) {
		Set<String> memberIds = new HashSet<>(); // Per evitare duplicati
		collectMemberIds(group, memberIds);
	
		// Converti gli ID in una stringa separata da virgole
		return String.join(", ", memberIds);
	}
	
	// Metodo ricorsivo per raccogliere gli ID di tutti i membri, incluso gruppi nidificati
	private void collectMemberIds(GroupObject group, Set<String> memberIds) {
		for (GraphicObject member : group.getMembers()) {
			if (member instanceof GroupObject) {
				// Chiamata ricorsiva per gruppi nidificati
				collectMemberIds((GroupObject) member, memberIds);
			} else {
				// Aggiungi l'ID del membro
				String id = getObjectId(member);
				if (id != null) {
					memberIds.add(id);
				}
			}
		}
	}

	// area
	public double calculateArea(String target) {
		double totalArea = 0;

		switch (target.toLowerCase()) {
			case "all": 
				for (GraphicObject go : objects) {
					totalArea += calculateObjectArea(go);
				}
				break;

			case "circle": 
			case "rectangle":
			case "image":
				for (GraphicObject go : objects) {
					if (go.getType().equalsIgnoreCase(target)) {
						totalArea += calculateObjectArea(go);
					}
				}
				break;

			default: // Area per un oggetto specifico con ID
				GraphicObject obj = getObjectById(target);
				if (obj != null) {
					totalArea = calculateObjectArea(obj);
				} else {
					System.err.println("Oggetto o tipo non trovato per il target: " + target);
				}
		}

		return totalArea;
	}

	private double calculateObjectArea(GraphicObject go) {
		if (go instanceof CircleObject) {
			CircleObject circle = (CircleObject) go;
			double radius = circle.getRadius();
			return Math.PI * radius * radius;
		} else if (go instanceof RectangleObject) {
			Dimension dim = (Dimension) go.getDimension();
			return dim.getWidth() * dim.getHeight();
		} else if (go instanceof ImageObject) {
			Dimension dim = (Dimension) go.getDimension();
			return dim.getWidth() * dim.getHeight();
		} else if (go instanceof GroupObject) {
			double groupArea = 0;
			for (GraphicObject member : ((GroupObject) go).getMembers()) {
				groupArea += calculateObjectArea(member);
			}
			return groupArea;
		} else {
			System.err.println("Tipo di oggetto non supportato per il calcolo dell'area: " + go.getType());
			return 0;
		}
	}

	// perimeter
	public double calculatePerimeter(String target) {
		double totalPerimeter = 0;
	
		switch (target.toLowerCase()) {
			case "all": 
				for (GraphicObject go : objects) {
					totalPerimeter += calculateObjectPerimeter(go);
				}
				break;
	
			case "circle": 
			case "rectangle":
			case "image":
				for (GraphicObject go : objects) {
					if (go.getType().equalsIgnoreCase(target)) {
						totalPerimeter += calculateObjectPerimeter(go);
					}
				}
				break;
	
			default: // Perimetro per un oggetto specifico con ID
				GraphicObject obj = getObjectById(target);
				if (obj != null) {
					totalPerimeter = calculateObjectPerimeter(obj);
				} else {
					System.err.println("Oggetto o tipo non trovato per il target: " + target);
				}
		}
	
		return totalPerimeter;
	}
	
	private double calculateObjectPerimeter(GraphicObject go) {
		if (go instanceof CircleObject) {
			CircleObject circle = (CircleObject) go;
			double radius = circle.getRadius();
			return 2 * Math.PI * radius;
		} else if (go instanceof RectangleObject) {
			Dimension dim = (Dimension) go.getDimension();
			return 2 * (dim.getWidth() + dim.getHeight());
		} else if (go instanceof ImageObject) {
			Dimension dim = (Dimension) go.getDimension();
			return 2 * (dim.getWidth() + dim.getHeight());
		} else if (go instanceof GroupObject) {
			double groupPerimeter = 0;
			for (GraphicObject member : ((GroupObject) go).getMembers()) {
				groupPerimeter += calculateObjectPerimeter(member);
			}
			return groupPerimeter;
		} else {
			System.err.println("Tipo di oggetto non supportato per il calcolo del perimetro: " + go.getType());
			return 0;
		}
	}

	// griglia (altri pezzi a inizio codice)
	private void drawGrid(Graphics2D g2) {
		int width = getWidth();
		int height = getHeight();
		int gridSize = 50; 
	
		Color gridColor = theme.getGridColor();  // Colore della griglia dal tema
		Color textColor = theme.getTextColor(); // Colore dei numeri dal tema
	
		// Griglia
		g2.setColor(gridColor); 
		for (int x = 0; x <= width; x += gridSize) {
			g2.drawLine(x, 0, x, height); // Linee verticali
			g2.setColor(textColor); 
			g2.drawString(String.valueOf(x), x + 2, 12); // Numeri sopra la griglia
			g2.setColor(gridColor); 
		}
	
		for (int y = 0; y <= height; y += gridSize) {
			g2.drawLine(0, y, width, y); // Linee orizzontali
			g2.setColor(textColor); 
			g2.drawString(String.valueOf(y), 2, y - 2); // Numeri accanto alla griglia
			g2.setColor(gridColor); 
		}
	}

    public void toggleGrid() {
        showGrid = !showGrid;
        repaint();
    }

	// tema (altri pezzi a inizio codice)
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Sfondo
        setBackground(theme.getBackgroundColor());
        g2.setColor(theme.getBackgroundColor());
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Griglia
        if (showGrid) {
            drawGrid(g2);
        }

        // Disegna oggetti
        for (GraphicObject go : objects) {
			GraphicObjectView view = GraphicObjectViewFactory.FACTORY.createView(go);
			if (view != null) {
				// Avvolgi la vista con il decorator per gestire il colore del tema
				ColorDecorator themedView = new ColorDecorator(view, theme);
				themedView.drawGraphicObject(go, g2);
			}
		}
    }

	public List<GraphicObject> getAllObjects() {
		return new ArrayList<>(objects);
	}

	public Color getBorderColor() {
		return theme.getShapeColor(); // Usa il colore della modalità attuale
	}

} 