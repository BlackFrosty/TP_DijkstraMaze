package maze;

import java.util.Set;

import graph.Vertex;

public class EmptyBox extends MazeBox {

	public EmptyBox(Maze parentMaze, int yPos, int xPos) {
		super(parentMaze, yPos, xPos);
	}

	@Override
	public boolean isEmpty() 	{ return true; }
	
	@Override
	public Set<Vertex> getSuccessors() {
		return super.getSuccessors();
	}

}
