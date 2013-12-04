package other;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Sobercheg on 12/3/13.
 */
public class SCC {
    private final Graph graph;
    private int sccNumber;

    public SCC(Graph graph) {
        this.graph = graph;
        calculateSCC();
    }

    private void calculateSCC() {
        // Step 1. Do DFS in G saving visited vertices in post order fashion to a stack
        Stack<Integer> dfsPostorderVertices = new Stack<Integer>();
        boolean[] visited = new boolean[graph.getSize()];
        for (int vertex = 0; vertex < graph.getSize(); vertex++) {
            if (!visited[vertex]) {
                doDfs(graph, dfsPostorderVertices, vertex, visited);
            }
        }

        // Step 2. Reverse all edges in G to obtain G*
        Graph reversedGraph = new Graph(graph.getSize());
        for (int vertex = 0; vertex < graph.getSize(); vertex++) {
            for (Edge adj : graph.getAdjacent(vertex)) {
                reversedGraph.addEdge(new Edge(adj.to, adj.from));
            }
        }

        // Step 3. Do DFS in G* getting start vertices the from the stack.
        visited = new boolean[graph.getSize()];
        for (Integer vertex : dfsPostorderVertices) {
            if (!visited[vertex]) {
                doDfs(reversedGraph, new Stack<Integer>(), vertex, visited);
                sccNumber++;
            }
        }
    }

    private void doDfs(Graph graph, Stack<Integer> dfsPostorderVertices, int vertex, boolean[] marked) {
        marked[vertex] = true;

        for (Edge adj : graph.getAdjacent(vertex)) {
            if (!marked[adj.to]) {
                doDfs(graph, dfsPostorderVertices, adj.to, marked);
            }
        }

        dfsPostorderVertices.push(vertex);
    }

    public int getSCCNumber() {
        return sccNumber;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(new Edge(0, 1));
        graph.addEdge(new Edge(1, 2));
        graph.addEdge(new Edge(2, 0));
        SCC scc = new SCC(graph);
        System.out.println(scc.getSCCNumber());
    }
}

class Edge {
    int from;
    int to;
    int weight;

    Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Graph {
    private final int size;

    private List<Edge>[] adjacentEdges;

    public Graph(int size) {
        this.size = size;
        this.adjacentEdges = (List<Edge>[]) new List[size];
        for (int i = 0; i < size; i++) {
            adjacentEdges[i] = new ArrayList<Edge>();
        }
    }

    public List<Edge> getAdjacent(int v) {
        return adjacentEdges[v];
    }

    public void addEdge(Edge e) {
        adjacentEdges[e.from].add(e);
    }

    public int getSize() {
        return size;
    }
}
