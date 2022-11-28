package maze;

import java.util.Set;

import graph.Vertex;

public class ArrivalBox extends MazeBox {

	public ArrivalBox(Maze parentMaze, int yPos, int xPos) {
		super(parentMaze, yPos, xPos);
	}

	@Override
	public boolean isArrival() 	{ return true; }
	
	@Override
	public Set<Vertex> getSuccessors() {
		return super.getSuccessors();
	}

}
