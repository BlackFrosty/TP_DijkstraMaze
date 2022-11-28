package graph.maze.impl;

import java.util.*;
import graph.*;

public class MazeShortestPaths implements ShortestPaths {
	
	private final Map<Vertex, Vertex> previousEdges;
	
	public MazeShortestPaths() {
		this.previousEdges = new HashMap<>();
	}

	@Override
	public void setPrevious(Vertex vertexEnd, Vertex vertexPrevious) {
		this.previousEdges.put(vertexEnd, vertexPrevious );
	}

	@Override
	public List<Vertex> getShortestPath(Vertex startVertex, Vertex endVertex ) {
		final List<Vertex> shortestPath = new ArrayList<>();
		Vertex previous = endVertex;
		
		do {
			shortestPath.add( 0, previous );
			previous = previousEdges.get( previous );
		} while ( previous != null && !startVertex.equals( previous ) ); 
		
		shortestPath.add( 0, previous );
		
		return shortestPath;
	}
}
