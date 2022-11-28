package maze.throwables;

import java.io.IOException;

@SuppressWarnings("serial")
public class MazeReadingException extends IOException{
	
	public MazeReadingException (String fileName, Integer lineError, String errorMessage) {
		super(String.format("[%s] at line [%d] in file [%s]",errorMessage, lineError, fileName));
	}
}
