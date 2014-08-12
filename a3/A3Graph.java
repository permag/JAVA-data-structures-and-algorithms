package a3;

import java.util.List;
import java.util.Map;

public interface A3Graph {
	public void addNode(int nodeItem);
	public void addEdge(int srcNodeItem, int tgtNodeItem);
	
	public boolean hasNode(int nodeItem);
	public boolean hasEdge(int srcNodeItem, int tgtNodeItem);
	
	public void printAllNodes();
	public void printAllEdges();
	
	public List<Integer> visitDFS(int startNodeItem);
	public List<Integer> visitBFS(int startNodeItem);
	
	public boolean hasSelfLoops();
	public boolean isConnected();
	public boolean hasTwoCycles();
	public boolean hasCycles();
	
	/**
	 * Looks for a feedback edges set using Greedy Cycle Removal
	 * algorithm.
	 * Resulting collection should contain feedback edges
	 * arranged by source node item.
	 * 
	 * Example: key 1 and list of values [2, 3] in resulting map
	 * would mean that edges 1->2 and 1->3 belong to computed
	 * feedback edges set. 
	 * 
	 * @return resulting map of edges, or null if unfeasible to compute
	 */
	public Map<Integer, List<Integer>> feedbackEdges();


	public void sortAdjacency();
	
}
