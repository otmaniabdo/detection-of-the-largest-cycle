package gui;

import java.util.LinkedList;
import java.util.List;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


import models.Arc;
import models.Noeud;

public class CycleDetection {
	public static DefaultDirectedGraph<String, DefaultEdge> graphNoCycle;
	public static List<List<String>> run(LinkedList<Noeud> Noeuds, LinkedList<Arc> Arcs){
		graphNoCycle = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		for(Noeud n : Noeuds) {
			graphNoCycle.addVertex(Integer.toString(n.id));
		}
		for(Arc arc : Arcs) {
			graphNoCycle.addEdge(Integer.toString(arc.getSource()), Integer.toString(arc.getDestination()));
		}
		
		for(Noeud n : Noeuds) {
			graphNoCycle.addVertex(Integer.toString(n.id));
		}
		for(Arc arc : Arcs) {
			graphNoCycle.addEdge(Integer.toString(arc.getSource()), Integer.toString(arc.getDestination()));
		}
	  
		TarjanSimpleCycles<String, DefaultEdge> cycleDetector = new TarjanSimpleCycles<String, DefaultEdge>(graphNoCycle);
		List<List<String>> cycleVertices = cycleDetector.findSimpleCycles();
		return cycleVertices;
	}

}
