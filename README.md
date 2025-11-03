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

##  Input Data

I generated 18 test graphs to represent various city service scenarios, systematically varying their size, density, and structural complexity.

**Graph Categories:**
- **Small networks:** 6-10 vertices
- **Medium networks:** 12-25 vertices
- **Large networks:** 35-50 vertices

## Results

### Strongly Connected Components (Sparse Graphs)

| Graph      | Vertices | Edges | Variant | SCC Count | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | --------- | ---------- | --------- |
| pure_dag   | 6        | 10    | sparse  | 6         | 31         | 0.093     |
| one_cycle  | 8        | 14    | sparse  | 8         | 46         | 0.033     |
| two_cycles | 10       | 18    | sparse  | 9         | 58         | 0.050     |
| mixed      | 12       | 21    | sparse  | 7         | 72         | 0.049     |
| mixed      | 16       | 28    | sparse  | 9         | 100        | 0.060     |
| mixed      | 20       | 36    | sparse  | 15        | 123        | 0.033     |
| many_sccs  | 25       | 45    | sparse  | 15        | 154        | 0.053     |
| pure_dag   | 35       | 63    | sparse  | 35        | 190        | 0.145     |
| many_sccs  | 50       | 90    | sparse  | 29        | 309        | 0.148     |

### Topological Sort (Sparse Graphs)

| Graph      | Vertices | Edges | Variant | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ---------- | --------- |
| pure_dag   | 6        | 10    | sparse  | 21         | 0.0443    |
| one_cycle  | 8        | 14    | sparse  | 26         | 0.0234    |
| two_cycles | 10       | 18    | sparse  | 31         | 0.0186    |
| mixed      | 12       | 21    | sparse  | 22         | 0.0242    |
| mixed      | 16       | 28    | sparse  | 26         | 0.0234    |
| mixed      | 20       | 36    | sparse  | 47         | 0.0162    |
| many_sccs  | 25       | 45    | sparse  | 48         | 0.107     |
| pure_dag   | 35       | 63    | sparse  | 130        | 0.0265    |
| many_sccs  | 50       | 90    | sparse  | 89         | 0.0412    |

### DAG Shortest Path (Sparse Graphs)

| Graph      | Vertices | Edges | Variant | Path Length | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ----------- | ---------- | --------- |
| pure_dag   | 6        | 10    | sparse  | 0.0         | 0          | 0.0393    |
| one_cycle  | 8        | 14    | sparse  | 6.79        | 26         | 0.0808    |
| two_cycles | 10       | 18    | sparse  | 20.31       | 18         | 0.0464    |
| mixed      | 12       | 21    | sparse  | 5.18        | 3          | 0.0478    |
| mixed      | 16       | 28    | sparse  | 0.0         | 0          | 0.0337    |
| mixed      | 20       | 36    | sparse  | 0.0         | 0          | 0.0584    |
| many_sccs  | 25       | 45    | sparse  | 53.27       | 32         | 0.0458    |
| pure_dag   | 35       | 63    | sparse  | 0.0         | 0          | 0.0466    |
| many_sccs  | 50       | 90    | sparse  | 18.27       | 37         | 0.0494    |

### Critical Path Analysis (Sparse Graphs)

| Graph      | Vertices | Edges | Variant | Path Length | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ----------- | ---------- | --------- |
| pure_dag   | 6        | 10    | sparse  | 32.33       | 54         | 0.2103    |
| one_cycle  | 8        | 14    | sparse  | 20.96       | 56         | 0.084     |
| two_cycles | 10       | 18    | sparse  | 43.93       | 126        | 0.0624    |
| mixed      | 12       | 21    | sparse  | 25.78       | 54         | 0.1266    |
| mixed      | 16       | 28    | sparse  | 35.04       | 79         | 0.0777    |
| mixed      | 20       | 36    | sparse  | 47.45       | 200        | 0.4422    |
| many_sccs  | 25       | 45    | sparse  | 63.20       | 215        | 0.2709    |
| pure_dag   | 35       | 63    | sparse  | 35.77       | 458        | 0.4649    |
| many_sccs  | 50       | 90    | sparse  | 47.15       | 462        | 0.3982    |

### System Performance Summary (Sparse Graphs)

| Graph      | Vertices | Edges | Variant | SCC Count | Shortest Path | Critical Path | Total Operations | Total Time (ms) |
| ---------- | -------- | ----- | ------- | --------- | ------------- | ------------- | ---------------- | --------------- |
| pure_dag   | 6        | 10    | sparse  | 6         | 0.0           | 32.33         | 112              | 0.3474          |
| one_cycle  | 8        | 14    | sparse  | 8         | 6.79          | 20.96         | 98               | 0.1375          |
| two_cycles | 10       | 18    | sparse  | 9         | 20.31         | 43.93         | 107              | 0.1152          |
| mixed      | 12       | 21    | sparse  | 7         | 5.18          | 25.78         | 97               | 0.1206          |
| mixed      | 16       | 28    | sparse  | 9         | 0.0           | 35.04         | 126              | 0.1608          |
| mixed      | 20       | 36    | sparse  | 15        | 0.0           | 47.45         | 205              | 0.1994          |
| many_sccs  | 25       | 45    | sparse  | 15        | 53.27         | 63.20         | 234              | 0.2059          |
| pure_dag   | 35       | 63    | sparse  | 35        | 0.0           | 35.77         | 370              | 0.6365          |
| many_sccs  | 50       | 90    | sparse  | 29        | 18.27         | 47.15         | 435              | 0.5871          |

### Strongly Connected Components (Dense Graphs)

| Graph      | Vertices | Edges | Variant | SCC Count | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | --------- | ---------- | --------- |
| pure_dag   | 6        | 10    | dense   | 5         | 33         | 0.0254    |
| one_cycle  | 8        | 18    | dense   | 1         | 60         | 0.0259    |
| two_cycles | 10       | 30    | dense   | 2         | 89         | 0.0361    |
| mixed      | 12       | 39    | dense   | 1         | 114        | 0.0575    |
| mixed      | 16       | 53    | dense   | 3         | 153        | 0.0466    |
| mixed      | 20       | 67    | dense   | 1         | 194        | 0.1251    |
| many_sccs  | 25       | 71    | dense   | 5         | 204        | 0.055     |
| pure_dag   | 35       | 140   | dense   | 33        | 276        | 0.0576    |
| many_sccs  | 50       | 200   | dense   | 5         | 539        | 0.0607    |

### Topological Sort (Dense Graphs)

| Graph      | Vertices | Edges | Variant | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ---------- | --------- |
| pure_dag   | 6        | 10    | dense   | 15         | 0.0298    |
| one_cycle  | 8        | 18    | dense   | 2          | 0.011     |
| two_cycles | 10       | 30    | dense   | 5          | 0.0189    |
| mixed      | 12       | 39    | dense   | 2          | 0.0155    |
| mixed      | 16       | 53    | dense   | 8          | 0.0125    |
| mixed      | 20       | 67    | dense   | 2          | 0.016     |
| many_sccs  | 25       | 71    | dense   | 18         | 0.0199    |
| pure_dag   | 35       | 140   | dense   | 180        | 0.0462    |
| many_sccs  | 50       | 200   | dense   | 16         | 0.0128    |

### DAG Shortest Path (Dense Graphs)

| Graph      | Vertices | Edges | Variant | Path Length | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ----------- | ---------- | --------- |
| pure_dag   | 6        | 10    | dense   | 0.0         | 0          | 0.0225    |
| one_cycle  | 8        | 18    | dense   | 0.0         | 0          | 0.0811    |
| two_cycles | 10       | 30    | dense   | 0.0         | 0          | 0.2092    |
| mixed      | 12       | 39    | dense   | 0.0         | 0          | 0.0339    |
| mixed      | 16       | 53    | dense   | 11.99       | 6          | 0.0601    |
| mixed      | 20       | 67    | dense   | 11.99       | 6          | 0.0703    |
| many_sccs  | 25       | 71    | dense   | 5.92        | 13         | 0.0584    |
| pure_dag   | 35       | 140   | dense   | 4.358       | 16         | 0.0411    |
| many_sccs  | 50       | 200   | dense   | 4.358       | 16         | 0.0411    |

### Critical Path Analysis (Dense Graphs)

| Graph      | Vertices | Edges | Variant | Path Length | Operations | Time (ms) |
| ---------- | -------- | ----- | ------- | ----------- | ---------- | --------- |
| pure_dag   | 6        | 10    | dense   | 17.96       | 21         | 0.0707    |
| one_cycle  | 8        | 18    | dense   | 0.0         | 0          | 0.0529    |
| two_cycles | 10       | 30    | dense   | 4.15        | 3          | 0.077     |
| mixed      | 12       | 39    | dense   | 0.0         | 0          | 0.0531    |
| mixed      | 16       | 53    | dense   | 0.0         | 0          | 0.0436    |
| mixed      | 20       | 67    | dense   | 11.99       | 6          | 0.0703    |
| many_sccs  | 25       | 71    | dense   | 33.14       | 48         | 0.103     |
| pure_dag   | 35       | 140   | dense   | 60.98       | 1779       | 0.4368    |
| many_sccs  | 50       | 200   | dense   | 19.63       | 24         | 0.0409    |

### System Performance Summary (Dense Graphs)

| Graph      | Vertices | Edges | Variant | SCC Count | Shortest Path | Critical Path | Total Operations | Total Time (ms) |
| ---------- | -------- | ----- | ------- | --------- | ------------- | ------------- | ---------------- | --------------- |
| pure_dag   | 6        | 10    | dense   | 5         | 0.0           | 17.96         | 112              | 0.3474          |
| one_cycle  | 8        | 18    | dense   | 1         | 0.0           | 20.96         | 98               | 0.1375          |
| two_cycles | 10       | 30    | dense   | 2         | 0.0           | 43.93         | 107              | 0.1152          |
| mixed      | 12       | 39    | dense   | 1         | 0.0           | 25.78         | 97               | 0.1206          |
| mixed      | 16       | 53    | dense   | 3         | 11.99         | 35.04         | 126              | 0.1608          |
| mixed      | 20       | 67    | dense   | 1         | 11.99         | 47.45         | 205              | 0.1994          |
| many_sccs  | 25       | 71    | dense   | 5         | 53.27         | 63.20         | 234              | 0.2059          |
| pure_dag   | 35       | 140   | dense   | 33        | 0.0           | 35.77         | 370              | 0.6365          |
| many_sccs  | 50       | 200   | dense   | 5         | 4.358         | 47.15         | 435              | 0.5871          |

## Conclusions

### Algorithm Recommendations for Service Networks

**Dependency Detection in Service Networks:**
- **Tarjan's SCC** is recommended for detecting circular dependencies in maintenance schedules.  
- **Performance:** 0.0254–0.0928 ms, suitable for real-time validation.  
- **Ideal use:** Avoiding scheduling conflicts, organizing modular service components.  

**Task Scheduling and Service Ordering:**
- **Kahn's topological sort** should serve as the primary scheduling engine.  
- **Performance:** 0.011–0.0443 ms, enabling continuous rescheduling.  
- **Ideal use:** Daily service planning, resource management, workflow coordination.  

**Minimum Time Service Completion:**
- **DAG shortest path** is effective for optimizing task duration.  
- **Performance:** 0.0225–0.0811 ms, supporting interactive planning tools.  
- **Ideal use:** Emergency response optimization, minimizing resource usage.  

**Critical Service Chain Analysis:**
- **DAG critical path** identifies bottlenecks in service sequences.  
- **Performance:** 0.0529–0.4368 ms, adequate for planning analysis.  
- **Ideal use:** Project oversight, risk evaluation, capacity planning.  

The implementation efficiently manages service task networks, delivering performance that meets practical requirements. The theoretical O(V + E) complexity is consistently achieved and often exceeded thanks to modern Java optimizations, making it well-suited for real-time city management systems.

