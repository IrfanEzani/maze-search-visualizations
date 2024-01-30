# Maze Solver

## Overview
 Designed to solve mazes by using graphs conversion and graph traversal algorithms: Depth-First Search, Breadth-First Search and Dijkstra's Algorithm.

## Part I: WeightedGraph

### General Description
Generic class representing a directed, weighted graph.The class includes implementations of Depth-First Search (DFS), Breadth-First Search (BFS), and Dijkstra's algorithm for pathfinding and graph traversal.

### Key Features
- **Non-Duplicate Vertices**: Guarantees the uniqueness of vertices in the graph.
- **Non-Negative Edge Weights**: Ensures edge weights are always non-negative integers.
- **Graph Algorithms**:
  - **Depth-First Search (DFS)**: Traverses the graph deeply before moving to adjacent vertices, using a stack to manage the traversal process.
  - **Breadth-First Search (BFS)**: Explores the graph level by level, using a queue to visit vertices in an order that starts with the nearest vertices and extends outward.
  - **Dijkstra's Algorithm**: Finds the shortest path between two vertices in a weighted graph. It uses a priority queue to select the next vertex with the lowest cumulative weight.

### Algorithm Observers
- Observers can be added to track the progress of graph algorithms.

## Part II: MazeGraph
`MazeGraph` extends `WeightedGraph`, where `Juncture` represents the vertices of the graph, corresponding to each intersection in a maze. It converts a maze into a graph by adding vertices for each juncture and edges based on the maze's layout.

### Maze to Graph Conversion
- Each juncture in the maze is added as a vertex in the graph.
- Edges are added between adjacent junctures if they are not separated by a wall.
- The weight of each edge corresponds to the "cost" of traveling from one juncture to the adjacent one, as defined in the maze.

### Solving Mazes
The `MazeGraph` class uses the following algorithms to solve mazes:
- **Depth-First Search**: Suitable for exploring all possible paths, especially in sparse mazes. It may not find the shortest path but will explore all reachable areas of the maze.
- **Breadth-First Search**: Effective in finding the shortest path in unweighted mazes, as it explores the nearest junctures first.
- **Dijkstra's Algorithm**: Ideal for weighted mazes, as it finds the path with the minimal total cost. It is particularly interesting in sparse mazes where multiple paths exist.

### Maze Density and Complexity
- The package can handle mazes of varying densities, from very sparse to 100% dense.
- In sparse mazes, multiple paths exist, making Dijkstra's algorithm more applicable.
- In 100% dense mazes, there is exactly one path from the start to any juncture, offering a unique challenge for the algorithms.

### Usage with MazeGUI
The package is designed to work with a `MazeGUI` class, allowing users to visualize and interact with the maze-solving process.
- Compile the Java classes and run the main application by `java -jar Driver.jar`
- Interact with the GUI to play the game and test different scenarios.
