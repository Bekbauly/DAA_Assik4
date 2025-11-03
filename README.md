# Assignment 4: Advanced Graph Algorithms for City Service Task Networks

## Project Overview

This project applies graph algorithms to analyze networks of city service tasks. The datasets include activities such as street cleaning, infrastructure repairs, and maintenance of cameras or sensors, each containing internal analytical subtasks. Some task dependencies form cycles that must be identified and condensed, while others are acyclic and can be scheduled efficiently.

I developed and tested four main algorithms: Tarjan’s algorithm for detecting strongly connected components, Kahn’s method for topological ordering, shortest path calculation in directed acyclic graphs, and critical path evaluation. The project emphasized comparing actual performance with theoretical expectations using 18 different directed graphs modeled on city service operations.

## Problem Statement

The goals are to:

1. **Identify cyclic dependencies** using SCC analysis to avoid endless scheduling loops  
2. **Determine feasible task execution sequences** for improved city service scheduling  
3. **Calculate optimal completion times** to ensure efficient resource utilization  
4. **Detect critical bottlenecks** that may cause delays in overall service operations


Given weighted directed graphs representing city service task networks where:

- Vertices correspond to individual service activities such as cleaning, repairs, or maintenance  
- Edges define task dependencies and scheduling limitations  
- Edge weights indicate task durations or associated resource costs

## Theory

### Tarjan’s Algorithm for Detecting Strongly Connected Components

Strongly connected components (SCCs) are the maximal groups of vertices in a directed graph where every vertex can reach all others in the group. Tarjan’s method finds all SCCs using a single depth-first search traversal.

The algorithm keeps track of:
- **Discovery time** when a vertex is first visited  
- **Low-link values** indicating the smallest vertex reachable through DFS  
- **Stack** to monitor the current path  

**Key idea:** A vertex whose low-link equals its discovery time is the root of an SCC, including all vertices above it on the stack.

**Time Complexity:** O(V + E) – DFS is performed once, with stack operations  

This is more efficient than Kosaraju’s algorithm, which requires two separate DFS passes.

---

### Kahn’s Algorithm for Topological Sorting

Topological sorting produces a linear sequence of vertices such that for every directed edge (u → v), u comes before v. This is useful for task scheduling where dependencies matter.

Steps of Kahn’s algorithm:
1. Compute the in-degree for all vertices  
2. Add all vertices with zero in-degree to a queue  
3. While the queue is not empty:  
   - Remove a vertex from the queue  
   - Reduce the in-degree of its neighbors  
   - Add neighbors with zero in-degree to the queue  

**Time Complexity:** O(V + E) – each vertex and edge is handled once  

It can also detect cycles: if fewer than V vertices are processed, the graph contains a cycle.

---

### Shortest Paths in DAGs

In directed acyclic graphs (DAGs), shortest paths can be computed efficiently using topological sorting. Negative edge weights are allowed since no negative cycles exist.

**Procedure:**
1. Perform a topological sort of the vertices  
2. Initialize distances: 0 for the source, ∞ for all other vertices  
3. Relax edges following the topological order: for each vertex u, update distances for outgoing edges  

**Time Complexity:** O(V + E) – one topological sort plus edge relaxation  

**Advantage:** Ensures optimal results by respecting vertex dependencies.

---

### Longest Path (Critical Path) in DAGs

The critical path identifies the longest route in a DAG, useful for detecting scheduling bottlenecks. This is essentially the reverse of shortest path calculations.

**Procedure:** Same as DAG shortest path, but maximize distances:
1. Topologically sort vertices  
2. Initialize distances: 0 for the source, -∞ for all others  
3. Relax edges in topological order to find maximum path lengths  

**Time Complexity:** O(V + E)  

**Workflow:** SCC detection → topological sorting → path analysis enables structured DAG processing.
