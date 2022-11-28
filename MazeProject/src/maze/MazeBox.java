package maze;

import java.util.Set;

import fr.tp.maze.model.MazeBoxModel;
import graph.Vertex;

public abstract class MazeBox implements Vertex, MazeBoxModel {

	private final Maze parentMaze;
	private final int xPos;
	private final int yPos;
	
	//constructor
	public MazeBox(Maze parentMaze, int yPos, int xPos) {
		this.parentMaze = parentMaze;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	//getter
	public Maze 	getParentMaze() 		{ return parentMaze; }
	
	public int 		getXPos() 				{ return xPos; }
	
	public int 		getYPos() 				{ return yPos; }
	
	public boolean 	belongsToShortestPath() {
		return this.parentMaze.belongsToShortestPath(this); }
	
	//
	public boolean isEmpty() 	{ return false; }
	
	public boolean isWall() 	{ return false; }
	
	public boolean isDeparture(){ return false; }

	public boolean isArrival() 	{ return false; }
	
	public void setEmpty() {
		this.parentMaze.changeToEmptyBox(this);
	}
	
	public void setWall() {
		this.parentMaze.changeToWallBox(this);
	}
	
	public void setDeparture() {
		this.parentMaze.changeToDepartureBox(this);
	}
	
	public void setArrival() {
		this.parentMaze.changeToArrivalBox(this);
	}
	
	//
	public Set<Vertex> getSuccessors() {
		return parentMaze.getSuccessors(this);
	}
	
	public String getLabel() {
		return ("" + yPos + " " + xPos + "");
	}
}
