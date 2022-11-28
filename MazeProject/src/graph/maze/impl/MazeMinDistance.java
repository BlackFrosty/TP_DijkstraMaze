package graph.maze.impl;

import java.util.*;
import graph.*;

public class MazeMinDistance implements MinDistance {
	
	private final Map<Vertex, Integer> minDistances;
	
	public MazeMinDistance() {
		minDistances = new HashMap<>();
	}

	public void setMinDistance(	Vertex vertex, int distance ) {
		this.minDistances.put( vertex, distance );
	}

	public int getMinDistance(Vertex vertex) {
		return this.minDistances.get( vertex );
	}

	@Override
	public Vertex getMinDistanceVertex(	ProcessedVertexesSet processedVertexes,
										Set<Vertex> vertexes) {
		int minDistance = Integer.MAX_VALUE;
		Vertex minDistVertex = null;
		
		for ( final Vertex vertex : vertexes ) {
			if ( !processedVertexes.contains( vertex ) ) {
				final int minDistanceVirtex = this.getMinDistance( vertex );
				
				if ( minDistanceVirtex < minDistance ) {
					minDistance = minDistanceVirtex;
					minDistVertex = vertex;
				}
			}
		}
		
		return minDistVertex;
	}
}
