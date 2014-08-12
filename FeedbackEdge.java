import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class FeedbackEdge {
	
	private Map<Integer, List<Integer>> nodeMap;
	private Map<Integer, Integer> stored;

	public FeedbackEdge(Map<Integer, List<Integer>> nodeMap) {
		this.nodeMap = nodeMap;
		this.stored = new HashMap<Integer, Integer>();
	}

	public Map<Integer, List<Integer>> getEdgeSet() {
		Map<Integer, List<Integer>> feedbackSet = new HashMap<Integer, List<Integer>>();

		List<Integer> sL = new ArrayList<Integer>();
		List<Integer> sR = new ArrayList<Integer>();
		List<Integer> s = new ArrayList<Integer>();
		int v, u, w;

		while (this.nodeMap.size() > 0) {
			while (this.getSink() > 0) {
				// choose a sink v
				v = this.getSink();
				// remove v from G
				this.removeNode(v);
				// prepend v to sR
				sR.add(v);
			}
			while (this.getSource() > 0) {
				// choose a source u
				u = this.getSource();
				// remove u from G
				this.removeNode(u);
				// append u to sL
				sL.add(u);
			}
			if (this.nodeMap.size() > 0) {
				// choose a node w such that d+(w) - d-(w) is maximum
				w = this.getMaxDegreeNode();
				// remove w from G
				this.removeNode(w);
				// append w to sL
				sL.add(w);
			}
		}
		// reverse order of sR list
		Collections.reverse(sR);
		s.addAll(sL);
		s.addAll(sR);

		for (Map.Entry<Integer, Integer> entry : this.stored.entrySet()) {
			// if stored VALUE points to sL key
			// get values
			List<Integer> list = new ArrayList<Integer>();
			for (int key : sL) {  // key in sL list
				if (key == entry.getValue()) {
					list.add(entry.getValue());
					feedbackSet.put(entry.getKey(), list);
				}
			}
		}
		return feedbackSet;
	}

	private void removeNode(int node) {
		this.nodeMap.remove(node);
		List<Integer> values;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			values = entry.getValue();
			for (Iterator<Integer> iter = values.iterator(); iter.hasNext();) {

				if (iter.next() == node) {
					iter.remove();
					this.stored.put(entry.getKey(), node);
				}
			}
		}
	}

	private int getSink() {
		int key;
		List<Integer> values;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			key = entry.getKey();
			values = entry.getValue();
			if (values.size() == 0 || values == null) {
				return key;
			}
		}
		return 0;
	}

	private int getSource() {
		Set<Integer> keys = new HashSet<Integer>();
		Set<Integer> edges = new HashSet<Integer>();

		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			for (int edge : entry.getValue()) {
				keys.add(entry.getKey());
				edges.add(edge);
			}
		}
		// use union to get value
		keys.removeAll(edges);
		if (keys.size() > 0) {
			return keys.iterator().next();
		}
		return 0;
	}

	private int getMaxDegreeNode() {
		int theNode = 0;
		int maxSum = 0;
		for (Map.Entry<Integer, List<Integer>> entry : this.nodeMap.entrySet()) {
			int keyNode = entry.getKey();
			int outSum = entry.getValue().size();
			for (int edge : entry.getValue()) {
				int inSum = 0;
				for (int node2 : this.nodeMap.keySet()) {
					if (node2 == edge) {
						inSum++;
					}
				}
				if ((outSum - inSum) >= maxSum) {
					maxSum = outSum - inSum;
					theNode = keyNode;
				}
			}
		}
		return theNode;
	}

}
