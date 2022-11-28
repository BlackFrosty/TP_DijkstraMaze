package maze;

import java.util.Set;

import graph.Vertex;

public class DepartureBox extends MazeBox {

	public DepartureBox(Maze parentMaze, int yPos, int xPos) {
		super(parentMaze, yPos, xPos);
	}

	@Override
	public boolean isDeparture() { return true; }

	@Override
	public Set<Vertex> getSuccessors() {
		return super.getSuccessors();
	}

}
