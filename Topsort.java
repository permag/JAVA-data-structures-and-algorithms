import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.Queue;

public class Topsort {
	
	private Map<Integer, List<Integer>> nodeMap;

	public Topsort(Map<Integer, List<Integer>> nodeMap) {
		this.nodeMap = nodeMap;
	}

	@SuppressWarnings("unchecked")
	public boolean hasCycle() {
		Queue<Integer> noOutdegree = new LinkedList<Integer>();

		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			int key = entry.getKey();
			if (entry.getValue().size() == 0) {
				noOutdegree.add(key);
			}
		}

		while (noOutdegree.size() > 0) {
			int node = noOutdegree.poll();
			int keyNode;

			for (Iterator<Map.Entry<Integer, List<Integer>>> iter = this.nodeMap.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				keyNode = (Integer) entry.getKey();
				for (Iterator it = ((List<Integer>) entry.getValue()).iterator(); it.hasNext();) {
					int edge = (Integer) it.next();
					// if edge points back to node to check, or edge points to self node (self-loop).
					if (edge == node || edge == entry.getKey()) {
						it.remove();
					}
					if (((List<Integer>) entry.getValue()).size() == 0) {
						iter.remove();
						noOutdegree.add(keyNode);
					}
				}
			}
		}

		if (this.nodeMap.get(this.nodeMap.keySet().iterator().next()).size() > 0) {
			// cycles exists
			return true;
		}
		return false;
	}
}
