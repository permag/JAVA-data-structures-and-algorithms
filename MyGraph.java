import a3.A3Graph;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.Collections;


public class MyGraph implements A3Graph {

	private Map<Integer, List<Integer>> nodeMap;
	private int maxKeyValue = 0;

	public MyGraph() {
		this.nodeMap = new HashMap<Integer, List<Integer>>();
	}

	public void addNode(int nodeItem) {
		this.nodeMap.put(nodeItem, new LinkedList<Integer>());
		this.maxKeyValue = (nodeItem > this.maxKeyValue) ? nodeItem : this.maxKeyValue;
	}
	public void addEdge(int srcNodeItem, int tgtNodeItem) {
		this.nodeMap.get(srcNodeItem).add(tgtNodeItem);
	}

	public void sortAdjacency() {
		for (int node : this.nodeMap.keySet()) {
			Collections.sort(this.nodeMap.get(node));
		}
	}
	public void sortAdjacency(int srcNodeItem) {
		Collections.sort(this.nodeMap.get(srcNodeItem));
	}
	
	public boolean hasNode(int nodeItem) {
		return this.nodeMap.containsKey(nodeItem);
	}

	public boolean hasEdge(int srcNodeItem, int tgtNodeItem) {
		if (this.hasNode(srcNodeItem)) {
			return this.nodeMap.get(srcNodeItem).contains(tgtNodeItem);
		}
		return false;
	}
	
	public void printAllNodes() {
		for (int key : this.nodeMap.keySet()) {
			System.out.println(key);
		}
	}

	public void printAllEdges() {
		int key;
		List<Integer> values;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			if (entry.getValue().size() > 0) {
				key = entry.getKey();
				values = entry.getValue();
				for (int edge : values) {
					System.out.println(key + "," + edge);
				}
			}
		}
	}
	
	public List<Integer> visitDFS(int startNodeItem) {
		this.sortAdjacency();
		return new DepthFirstSearch(this.nodeMap, this.maxKeyValue).search(startNodeItem);
	}

	public List<Integer> visitBFS(int startNodeItem) {
		int node = startNodeItem;
		boolean[] visited = new boolean[this.maxKeyValue+1];
		List<Integer> retList = new ArrayList<Integer>();
		Queue<Integer> q = new LinkedList<Integer>();
		this.sortAdjacency();
		
		// add start node to queue and mark as visited
		q.add(node);
		visited[node] = true;
		retList.add(node);

		// while queues not empty visit unvisited adjacent nodes
		while (!q.isEmpty()) {
			node = q.poll();

			if (this.nodeMap.get(node) != null) {
				for (int w : this.nodeMap.get(node)) {
					if (!visited[w]) {
						visited[w] = true;
						q.add(w);
						retList.add(w);
					}
				}
			}

			// finally, if non-connected nodes exists, visit them.
			if (q.isEmpty()) {
				for (int w : this.nodeMap.keySet()) {
					if (!visited[w]) {
						q.add(w);
						visited[w] = true;
						retList.add(w);
						break;
					}
				}
			}
		}
		return retList;
	}

	public boolean hasSelfLoops() {
		int key;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			key = entry.getKey();
			for (int edge : entry.getValue()) {
				if (key == edge) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isConnected() {
		DepthFirstSearch depthFirstSearch = new DepthFirstSearch(this.nodeMap, this.maxKeyValue);
		int aNode = this.nodeMap.keySet().iterator().next();
		List<Integer> list = depthFirstSearch.search(aNode, false);  // false - visit connected nodes only.

		return this.nodeMap.size() == list.size();
	}

	public boolean hasTwoCycles() {
		int key;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			key = entry.getKey();
			for (int edge : entry.getValue()) {
				// check if the edge node is connected back to the selected node to check.
				if (edge != key) {
					for (int edgeNode : this.nodeMap.get(edge)) {
						if (edgeNode == key) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasCycles() {
		Topsort topsort = new Topsort(this.nodeMap);
		return topsort.hasCycle();
	}

	
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
	public Map<Integer, List<Integer>> feedbackEdges() {
		if (this.hasSelfLoops() || this.hasTwoCycles() || !this.isConnected()) {
			return null;
		}
		FeedbackEdge feedback = new FeedbackEdge(this.nodeMap);
		
		return feedback.getEdgeSet();
	}

}
