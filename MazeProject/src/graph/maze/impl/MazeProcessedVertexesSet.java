package graph.maze.impl;

import java.util.*;
import graph.*;

public class MazeProcessedVertexesSet implements ProcessedVertexesSet {
	
	final Set<Vertex> processedVertexes;
	
	public MazeProcessedVertexesSet() {
		processedVertexes = new HashSet<>();
	}

	@Override
	public void add(Vertex vertex) {
		this.processedVertexes.add( vertex );
	}

	@Override
	public boolean contains(Vertex vertex) {
		return this.processedVertexes.contains( vertex );
	}
}
