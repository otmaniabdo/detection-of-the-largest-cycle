package gui;

import java.util.LinkedList;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import models.Arc;
import models.Noeud;


public class DrawingGraph {
	
	Graph<Integer, String> g;
	

	public DrawingGraph(LinkedList<Arc> arcs,LinkedList<Noeud> Noeuds,boolean orientation) {
		 g = new SparseGraph<Integer, String>();
		 
		 for(Noeud N : Noeuds) {
			 g.addVertex(N.id);
		 }
				 
		 if(orientation) {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId(),arc.getSource() ,arc.getDestination(),EdgeType.DIRECTED);

			 }			 
		 }else {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- "  ,arc.getSource(),arc.getDestination());

			 } 
		 }

	}
	public Graph getGraph() {
		return g;
	}
	public void setGraph(Graph g) {
		this.g = g;
	}

}
