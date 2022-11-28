package graph.impl;

import java.util.HashSet;
import java.util.Set;

import graph.ProcessedVertexesSet;
import graph.Vertex;

public class BasicVertexesSet implements ProcessedVertexesSet {
	
	final Set<Vertex> processedVertexes;
	
	public BasicVertexesSet() {
		processedVertexes = new HashSet<>();
	}

	@Override
	public void add(Vertex vertex) {
		processedVertexes.add( vertex );
	}

	@Override
	public boolean contains(Vertex vertex) {
		return processedVertexes.contains( vertex );
	}
}
