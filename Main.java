import a3.A3Main;
import a3.A3Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;


public class Main implements A3Main {

	public static void main(String args[]) {

		String filename = (args.length > 0) ? args[0] : "input.txt";
		A3Main main = new Main();
		A3Graph graph = main.readCSVFile(filename);

		// test print all
		main.testPrintAll(graph);
	}

	public A3Graph readCSVFile(String filename) {

		A3Graph graph = new MyGraph();

		try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] arr = line.split(",");
				int val1 = Integer.parseInt(arr[0]);

				if (arr.length == 1 && !graph.hasNode(val1)) {  // Node
					graph.addNode(val1);
				} 
				else if (arr.length == 2) {  // Edge
					int val2 = Integer.parseInt(arr[1]);
					if (!graph.hasNode(val1)) {
						graph.addNode(val1);
					}
					if (!graph.hasNode(val2)) {
						graph.addNode(val2);
					}
					if (!graph.hasEdge(val1, val2)) {  // dont add if already exists
						graph.addEdge(val1, val2);
					}
				}
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

	public void testPrintAll(A3Graph graph) {

		// print all nodes
		System.out.println("ALL NODES:");
		graph.printAllNodes();
		System.out.println();

		// print all edges
		System.out.println("ALL EDGES:");
		graph.printAllEdges();
		System.out.println();

		// print DSF list
		System.out.println("DFS LIST:\n" + graph.visitDFS(1) + "\n");

		// print BFS list
		System.out.println("BFS LIST:\n" + graph.visitBFS(1) + "\n");

		// check if graph is connected
		System.out.println("IS CONNECTED?: " + graph.isConnected() + "\n");

		// check if graph has self loop
		System.out.println("HAS SELF LOOP?: " + graph.hasSelfLoops() + "\n");

		// check if graph has two cycles
		System.out.println("HAS TWO CYCLES?: " + graph.hasTwoCycles() + "\n");

		// check if graph has cycles. NOTE: method alters original graph.
		System.out.println("HAS CYCLES? " + graph.hasCycles() + "\n");

		// feedback edge set
		System.out.println("FEEDBACK EDGE SET: " + graph.feedbackEdges() + "\n");


		// simple unit test
		System.out.println("HAS NODE 999: " + graph.hasNode(999) + "\n");  // true
		System.out.println("HAS EDGE 12,999: " + graph.hasEdge(12,999) + "\n");  // true

	}
}
