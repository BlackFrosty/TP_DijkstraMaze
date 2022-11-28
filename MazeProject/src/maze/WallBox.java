package maze;

import java.util.Set;

import graph.Vertex;

public class WallBox extends MazeBox {

	public WallBox(Maze parentMaze, int yPos, int xPos) {
		super(parentMaze, yPos, xPos);
	}

	@Override
	public boolean isWall() 	{ return true; }
	
	@Override
	public Set<Vertex> getSuccessors() {
		return super.getSuccessors();
	}

}
