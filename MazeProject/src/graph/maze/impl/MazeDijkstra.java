package graph.maze.impl;

import graph.Distance;
import graph.Graph;
import graph.Vertex;

public class MazeDijkstra {
	
	public static MazeShortestPaths findShortestPaths( 	Graph graph,
														Vertex startVertex,
														Vertex endVertex,
														MazeProcessedVertexesSet processedVertexes,
														MazeMinDistance minDistance,
														Distance distance ) {
		processedVertexes.add( startVertex );
		Vertex pivotVertex = startVertex;
		minDistance.setMinDistance(startVertex , 0 );
		
		for ( Vertex vertex : graph.getVertexes() ) {
			if ( !startVertex.equals( vertex ) ) {
				minDistance.setMinDistance( vertex, Integer.MAX_VALUE );
			}
		}
		
		final MazeShortestPaths shortestPaths = new MazeShortestPaths();
	
		while ( !processedVertexes.contains( endVertex ) ) {
			for ( Vertex succVertex : pivotVertex.getSuccessors() )
				if ( !(processedVertexes.contains(succVertex))) {		//missing condition added as described in the algorithm
					int currentDistance = minDistance.getMinDistance( pivotVertex ) + distance.getDistance( pivotVertex, succVertex );
					if ( currentDistance < minDistance.getMinDistance( succVertex ) ) {
						minDistance.setMinDistance( succVertex, currentDistance );
						shortestPaths.setPrevious( succVertex, pivotVertex );	//incorrect placement in original correction
					}
				}
			pivotVertex = minDistance.getMinDistanceVertex( processedVertexes, graph.getVertexes() );
			processedVertexes.add( pivotVertex );
		}	
		return shortestPaths;
	}
}
