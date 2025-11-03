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

\#\# Theory

\### Tarjan’s Algorithm for Strongly Connected Components \(SCCs\)

Strongly connected components \(SCCs\) are the largest groups of vertices in a directed graph where every vertex can reach every other vertex within the same group. Tarjan’s algorithm identifies all SCCs using a single depth\-first search \(DFS\).

The algorithm tracks:
\- \*\*Discovery time\*\* for each vertex when first visited  
\- \*\*Low\-link values\*\* representing the smallest reachable vertex via DFS  
\- \*\*Stack\*\* to keep track of the current DFS path  

\*\*Core idea:\*\* If a vertex’s low\-link value equals its discovery time, it serves as the root of an SCC, including all vertices currently on the stack above it.

\*\*Time Complexity:\*\* O\(V + E\) – a single DFS traversal with stack operations  

This approach is more efficient than Kosaraju’s algorithm, which requires two DFS passes.

---

\### Kahn’s Algorithm for Topological Sorting

Topological sorting generates a linear ordering of vertices so that for each directed edge \(u \→ v\), vertex u comes before vertex v. This is crucial for scheduling tasks with dependencies.

Kahn’s algorithm works as follows:
1. Compute the in\-degree for each vertex  
2. Initialize a queue with vertices that have zero in\-degree  
3. While the queue is not empty:  
   \- Remove a vertex  
   \- Decrease the in\-degree of its neighbors  
   \- Add any neighbors whose in\-degree becomes zero  

\*\*Time Complexity:\*\* O\(V + E\) – every vertex and edge is processed once  

The algorithm also detects cycles: if fewer than V vertices are processed, the graph contains a cycle.

---

\### Shortest Path in DAGs

For directed acyclic graphs \(DAGs\), shortest paths can be computed efficiently using topological ordering. Negative weights are allowed since DAGs cannot contain negative cycles.

\*\*Steps:\*\*
1. Perform a topological sort of the vertices  
2. Initialize distances: 0 for the source, \∞ for all others  
3. Relax edges in topological order: for each vertex u, update distances for all outgoing edges  

\*\*Time Complexity:\*\* O\(V + E\) – topological sort plus one pass of edge relaxation  

\*\*Advantage:\*\* Guarantees correct results by processing vertices according to dependencies.

---

\### Longest Path \(Critical Path\) in DAGs

The critical path identifies the longest path in a DAG, useful for detecting bottlenecks in project schedules. This problem mirrors the shortest path but maximizes distances.

\*\*Procedure:\*\* Same as DAG shortest path, but maximize distances:
1. Topologically sort the vertices  
2. Initialize distances: 0 for the source, \-\∞ for all others  
3. Relax edges in topological order to maximize path lengths  

\*\*Time Complexity:\*\* O\(V + E\)  

\*\*Integration:\*\* SCC detection \→ topological sort \→ path analysis enables full DAG workflow.
