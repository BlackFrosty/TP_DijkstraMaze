package maze;

import fr.tp.maze.model.MazeModel;
import fr.tp.maze.ui.MazeEditor;

public class MyMazeLauncher {

	public static void main(String[] args) {
		
		int	defaultHeight = 10;
		int defaultWidth = 20;
		
		final MazeModel maze = new MyMazeFactory().createMazeModel(defaultHeight, defaultWidth); 
		
		final FileMazePersistenceManager mazePersistenceManager = new FileMazePersistenceManager();
		
		final MazeEditor mazeEditor = new MazeEditor(maze, mazePersistenceManager);
		
		mazePersistenceManager.setEditor(mazeEditor);
	}

}
