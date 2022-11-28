package maze;

import fr.tp.maze.model.MazeFactory;
import fr.tp.maze.model.MazeModel;

public class MyMazeFactory implements MazeFactory {

	@Override
	public MazeModel createMazeModel(int height, int width) {
		return new Maze(height, width);
	}

}
