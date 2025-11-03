package com.DAA.graph.dagsp;


import java.util.List;

  //Contains distances, parent pointers, and methods to reconstruct paths.

public class PathResult {
    private final double[] dist;
    private final int[] parent;
    private final int source;

    public PathResult(double[] dist, int[] parent, int source) {
        if (dist == null || parent == null) {
            throw new IllegalArgumentException("Arrays cannot be null");
        }
        if (dist.length != parent.length) {
            throw new IllegalArgumentException("Array sizes must match");
        }
        if (source < 0 || source >= dist.length) {
            throw new IllegalArgumentException("Source out of bounds");
        }
        this.dist = dist;
        this.parent = parent;
        this.source = source;
    }

    public double[] getDistances() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }


    public int getSource() {
        return source;
    }


     //Reconstructs the path from source to target vertex.
     //Trace backward from target using parent array, then reverse.
    public List<Integer> getPath(int target) {
        if (target < 0 || target >= dist.length) {
            throw new IllegalArgumentException("Target out of bounds");
        }
        if (dist[target] == Double.POSITIVE_INFINITY ||
                dist[target] == Double.NEGATIVE_INFINITY) {
            return null;
        }

        // Trace backward from target to source using parent pointers
        List<Integer> path = new java.util.ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) {
            path.add(v);
        }

        // Reverse to get source -> target order
        java.util.Collections.reverse(path);
        return path;
    }
}