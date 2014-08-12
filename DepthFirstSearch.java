import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class DepthFirstSearch {

	private boolean[] visited;
	private List<Integer> retList;
	private Map<Integer, List<Integer>> nodeMap;
	private Set<Integer> nodes;

	public DepthFirstSearch(Map<Integer, List<Integer>> nodeMap, int maxKeyValue) {
		this.visited = new boolean[maxKeyValue+1];
		this.retList = new ArrayList<Integer>();
		this.nodeMap = nodeMap;
		this.nodes = nodeMap.keySet();
	}

	public List<Integer> search(int startNode) {
		this.search(startNode, true);  // overload search method with optimal boolean param.
		return this.retList;
	}

	public List<Integer> search(int startNode, boolean checkEntire) {
		this.dfs(startNode);
		if (checkEntire) {
			while (this.retList.size() != this.nodeMap.size()) {
				this.dfsUnConnected();
			}
		}
		return this.retList;
	}

	public List<Integer> dfs(int u) {
		List<Integer> edgeList = this.nodeMap.get(u);
		// visit node, by adding it to list
		this.retList.add(u);
		// mark node as visited
		this.visited[u] = true;

		if (edgeList != null) {
			// each successor to node
			for (int w : edgeList) {
				if (!this.visited[w]) {
					this.dfs(w);  // recursion
				}
			}
		}
		return this.retList;
	}

	public void dfsUnConnected() {
		// check unvisited non-connected nodes
		for (int w : this.nodes) {
			if (!this.visited[w]) {
				this.dfs(w);
			}
		}
	}
}
