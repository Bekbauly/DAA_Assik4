package com.DAA.graph.dagsp;

import com.DAA.core.DirectedGraph;
import com.DAA.core.Edge;
import com.DAA.graph.topo.KahnTopologicalSort;
import com.DAA.graph.topo.TopologicalSortResult;
import com.DAA.utils.Metrics;

import java.util.Arrays;
import java.util.List;


  //Computes shortest paths from a source in a DAG using topological sort + relaxation.
  //Time complexity: O(V + E) after topological sort verification.

public class DAGShortestPath {
    private Metrics metrics;

    public DAGShortestPath() {
        this.metrics = new Metrics("DAG-ShortestPath");
    }


     //Computes shortest paths from source to all vertices.
     //Verifies graph is DAG via topological sort, then relaxes edges in topo order.

    public PathResult computeShortestPaths(DirectedGraph graph, int source) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (source < 0 || source >= graph.getN()) {
            throw new IllegalArgumentException("Source out of bounds");
        }
        int n = graph.getN();
        double[] dist = new double[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        metrics.reset();
        metrics.startTimer();

        // Verify graph is DAG and get topological order
        KahnTopologicalSort TS = new KahnTopologicalSort();
        TopologicalSortResult TSResult = TS.sort(graph);
        if (!TSResult.isDAG()) {
            metrics.stopTimer();
            return null;
        }

        // Relax edges in topological order
        List<Integer> order = TSResult.getOrder();
        for (int u : order) {
            // Only relax from vertices that are reachable from source
            if (dist[u] != Double.POSITIVE_INFINITY) {
                for (Edge edge : graph.getAdjacent(u)) {
                    int v = edge.getTo();
                    double newDist = dist[u] + edge.getWeight();
                    metrics.incrementRelaxation();
                    metrics.incrementComparison();

                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        parent[v] = u;
                        metrics.incrementDistanceUpdate();
                    }
                }
            }
        }

        metrics.stopTimer();
        return new PathResult(dist, parent, source);
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
