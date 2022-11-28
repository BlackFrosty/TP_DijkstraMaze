package maze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.tp.maze.model.*;
import graph.*;
import graph.maze.impl.*;

public class Maze implements Graph, MazeModel, Distance {

	private final int mazeWidth;
	private final int mazeHeight;
	private Set<MazeBox> boxes;
	private final Set<ModelObserver> observers;
	private MazeShortestPaths shortestPaths;
	private	String	mazeId;
	
	public Maze( int mazeHeight, int mazeWidth ) {
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		this.boxes = new HashSet<MazeBox>();
		for (int yPos = 0 ; yPos < mazeHeight ; yPos++ ) {
			for (int xPos = 0 ; xPos < mazeWidth ; xPos++ ) {
				this.boxes.add(new EmptyBox(this, yPos, xPos));
			}
		}
		this.observers = new HashSet<ModelObserver>();
		this.shortestPaths = new MazeShortestPaths();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<Vertex> getVertexes() {
		return (Set) boxes;
	}

	@Override
	public Vertex getVertex(String label) {
		String[]	str = label.split(" ");
		int 		yPos = Integer.parseInt(str[0]);
		int			xPos = Integer.parseInt(str[1]);
		
		return (Vertex) this.getMazeBox(yPos, xPos);
	}

	@Override
	public Set<Vertex> getSuccessors(Vertex vertex) {
		Set<Vertex> result = new HashSet<Vertex>();
		MazeBox mazebox = (MazeBox) vertex;
		int diffCoord[][] = { {-1, 0} , {1, 0} , {0, -1} , {0, 1} };
		int xPosition = 0;
		int yPosition = 0;
		
		for (int i = 0 ; i < 4 ; i++ ) {
			xPosition = mazebox.getXPos() + diffCoord[i][0];
			yPosition = mazebox.getYPos() + diffCoord[i][1];
			
			if (this.isValid(yPosition, xPosition))
				if ( ! this.getMazeBox(yPosition, xPosition).isWall())
					result.add((Vertex) this.getMazeBox(yPosition, xPosition));
		}
		return result;
	}

	@Override
	public MazeFactory getMazeFactory() {
		return new MyMazeFactory();
	}

	@Override
	public void addObserver(ModelObserver observer) {
		observers.add( observer );
	}

	@Override
	public boolean removeObserver(ModelObserver observer) {
		return observers.remove( observer );
	}

	protected void notifyObservers() {
		for ( final ModelObserver observer : observers ) {
			observer.modelStateChanged();
		}
	}
	
	@Override
	public int getWidth() 			{ return this.mazeWidth; }

	@Override
	public int getHeigth() 			{ return this.mazeHeight; }

	@Override
	public MazeBoxModel getMazeBox(int rowIndex, int colIndex) {
		for (MazeBox mazebox : this.boxes)
			if ( (mazebox.getYPos() == rowIndex) && (mazebox.getXPos() == colIndex) )
				return mazebox;
		return null;
	}
	
	public MazeBoxModel getDepartureBox() {
		for (MazeBox mazebox : this.boxes)
			if (mazebox instanceof DepartureBox)
				return mazebox;
		return null;
	}
	
	public MazeBoxModel getArrivalBox() {
		for (MazeBox mazebox : this.boxes)
			if (mazebox instanceof ArrivalBox)
				return mazebox;
		return null;
	}

	@Override
	public int getDistance(Vertex vertex1, Vertex vertex2) {
		return 1;
//		if ( vertex1.getSuccessors().contains(vertex2) )
//			return 1;
//		return 0;
	}
	
	@Override
	public int getNumberOfBoxes() 	{ return mazeWidth*mazeHeight; }

	@Override
	public void clearMaze() {
		this.boxes.clear();
		for (int yPos = 0 ; yPos < this.getHeigth() ; yPos++ ) {
			for (int xPos = 0 ; xPos < this.getWidth() ; xPos++ ) {
				this.boxes.add(new EmptyBox(this, yPos, xPos));
			}
		}
		notifyObservers();
	}

	@Override
	public void clearShortestPath() {
		this.shortestPaths = new MazeShortestPaths();
		notifyObservers();
	}

	@Override
	public boolean solve() {
		this.shortestPaths = MazeDijkstra.findShortestPaths( 	this,
																(Vertex) this.getDepartureBox(), 
																(Vertex) this.getArrivalBox(), 
																new MazeProcessedVertexesSet(), 
																new MazeMinDistance(),
																this );
//		for debugging : uncomment following block to display computed path in console
//		for ( final Vertex vertex : this.shortestPaths.getShortestPath(startVertex, endVertex) ) {
//			System.out.print(vertex.getLabel() + " -> " ); 
//		}
		if (this.shortestPaths.getShortestPath((Vertex) this.getDepartureBox(), (Vertex) this.getArrivalBox()).contains((Vertex) this.getDepartureBox()) &&
			this.shortestPaths.getShortestPath((Vertex) this.getDepartureBox(), (Vertex) this.getArrivalBox()).contains((Vertex) this.getArrivalBox())	) {
			notifyObservers();
			return true;
		}
		return false;
	}
	
	public boolean belongsToShortestPath(MazeBox mazebox) {
		return this.shortestPaths.getShortestPath((Vertex) this.getDepartureBox(), (Vertex) this.getArrivalBox()).contains(mazebox);
	}
	
	@Override
	public List<String> validate() {
		return null;
	}

	public boolean isValid(int yPos, int xPos) {
		if(	xPos >= 0 && xPos < this.getWidth() &&
			yPos >= 0 && yPos < this.getHeigth())
			return true;
		return false;
	}

	void	changeToEmptyBox(MazeBox mazebox) {
		this.boxes.add(new EmptyBox(mazebox.getParentMaze(), mazebox.getYPos(), mazebox.getXPos()));
		this.boxes.remove(mazebox);
		notifyObservers();
	}
	
	void	changeToWallBox(MazeBox mazebox) {
		this.boxes.add(new WallBox(mazebox.getParentMaze(), mazebox.getYPos(), mazebox.getXPos())); 
		this.boxes.remove(mazebox);
		notifyObservers();
	}

	void	changeToDepartureBox(MazeBox mazebox) {
		//check if another Departure box exist
		for (MazeBox ibox : this.boxes)
			if (ibox instanceof DepartureBox) {
				this.boxes.add(new EmptyBox(ibox.getParentMaze(), ibox.getYPos(), ibox.getXPos())); 
				this.boxes.remove(ibox);				
				break;
			}
		this.boxes.add(new DepartureBox(mazebox.getParentMaze(), mazebox.getYPos(), mazebox.getXPos())); 
		this.boxes.remove(mazebox);
		notifyObservers();
	}
	
	void	changeToArrivalBox(MazeBox mazebox) {
		//check if another Departure box exist
		for (MazeBox ibox : this.boxes)
			if (ibox instanceof ArrivalBox) {
				this.boxes.add(new EmptyBox(ibox.getParentMaze(), ibox.getYPos(), ibox.getXPos())); 
				this.boxes.remove(ibox);				
				break;
			}			
		this.boxes.add(new ArrivalBox(mazebox.getParentMaze(), mazebox.getYPos(), mazebox.getXPos())); 
		this.boxes.remove(mazebox);
		notifyObservers();
	}

	@Override
	public String getId() {
		return this.mazeId;
	}

	@Override
	public void setId(String mazeId) {
		this.mazeId = mazeId;
		
	}
 }