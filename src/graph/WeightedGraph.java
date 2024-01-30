package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph will never store duplicate vertices.
 * </P>
 * 
 * <P>
 * The weights will always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The Weighted Graph will maintain a collection of "GraphAlgorithmObservers",
 * which will be notified during the performance of the graph algorithms to
 * update the observers on how the algorithms are progressing.
 * </P>
 * 
 * The Weighted Graph Class contain general methods useful in graphs such as adding edges and vertices, 
 * contains method, get weight method and traversal methods.
 */
public class WeightedGraph<V> {

	/*
	 * Used maps as main data structure for weighted graph implementation.
	 */
	HashMap<V, HashMap<V, Integer>> wGraph;

	/*
	 * A collection of observers.
	 * Graph traversal algorithms (DFS, BFS, and Dijkstra) will notify these observers to let
	 * the observers know how the algorithms are progressing and what steps are they in.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;

	/**
	 * Constructor for weightedGraph.
	 * Initializes the hashMap and the observerList.
	 */
	public WeightedGraph() {
		observerList = new HashSet<GraphAlgorithmObserver<V>>();
		wGraph = new HashMap<V, HashMap<V, Integer>>();
	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/**
	 * Method will add a vertex to the graph. If the vertex is already in the graph, throw an
	 * IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		//if graph doesn't contain the vertex, add to the graph. else, throw an exception.
		if (!wGraph.containsKey(vertex)) {
			wGraph.put(vertex, new HashMap<V, Integer>());
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Searches for a given vertex in the graph.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		//return true if graph has the contains, and false otherwise.
		return (wGraph.containsKey(vertex));
	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentExeption in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from   the vertex the edge leads from
	 * @param to     the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex is not in the graph, or
	 *                                  the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		//if the two vertices not present, or weight is less than 0, throw exception.
		if ((!(containsVertex(from)) || !(containsVertex(to)) || weight < 0)) {
			throw new IllegalArgumentException();
		} else {
			//  if not, add the edges and weight from the targeted vertices.
			wGraph.get(from).put(to, weight);
		}
	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null if
	 * the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified are
	 * not in the graph.
	 * </P>
	 * 
	 * @param from vertex where edge begins
	 * @param to   vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException if either of the vertices specified are not
	 *                                  in the graph.
	 */
	public Integer getWeight(V from, V to) {
		//if the edges doesn't exist, throw exception
		if (!(containsVertex(from)) ||!(containsVertex(to))) {
			throw new IllegalArgumentException();
		} else {
			//if the vertex from "from" to the "to" vertex is not in the graph, return null
			if (!(wGraph.get(from).containsKey(to))) {
				return null;
			}
			//returns the weight from the edges
			return wGraph.get(from).get(to);
		}
	}

	/**
	 * <P>
	 * This method will perform a Breadth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without processing further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoBFS(V start, V end) {
		//using both a set to keep track of the visited vertex and the queue
		HashSet<V> visitedSet = new HashSet<V>();
		Queue<V> queue = new LinkedList<V>();
		
		// add the first element to queue
		queue.add(start);
		
		// notify the observers inside the observerList to start BFS
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		
		//search runs as long as the queue is not empty
		while (!queue.isEmpty()) {
			V currentVertex = queue.element();
			queue.remove(currentVertex);
			
			// if it's not in the visited set
			if (!(visitedSet.contains(currentVertex))) {
				
				//notify the visit to the observers 
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(currentVertex);
				}
				
				// add current vertex to visitedSet
				visitedSet.add(currentVertex);
				
				// Get the adjacent vertices of the current vertex
				Map<V, Integer> adjacentVertices = wGraph.get(currentVertex);
								
				// If not in the visitedSet, add to the queue.
				for (V adjacentVertex : adjacentVertices.keySet()) {
					if (!(visitedSet.contains(adjacentVertex))) {
						queue.add(adjacentVertex);
					}
				}
			}
			
			//At the end vertex, notify the observers that the search is over
			if (currentVertex.equals(end)) {
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifySearchIsOver();
				}
				return;
			}
		}
		
	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without visiting further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		//using both a list to keep track of the visited vertex and the stack for DFS.
		//visited boolean to mark if the vertex has been visited.
		HashSet<V> visitedSet = new HashSet<V>();
		Stack<V> stack = new Stack<V>();
		boolean visited = false;

		//push the starting vertex into the stack, and notify the search has started to the observers.
		stack.push(start);
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		
		//search runs as long as the stack is not empty
		while (!(stack.isEmpty())) {
			V currentVertex = stack.pop();

			//  if it's not in the visited set
			if (!(visitedSet.contains(currentVertex))) {
				
				// notify the visit to the observers
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(currentVertex);
				}
				// add current vertex to visitedList
				visitedSet.add(currentVertex);
				
				// Get the adjacent vertices of the current vertex
				Map<V, Integer> adjacentVertices = wGraph.get(currentVertex);
				
				// If not in the visitedSet, add to the queue
				for (V adjacentVertex : adjacentVertices.keySet()) {
					if (!(visitedSet.contains(adjacentVertex))) {
						stack.push(adjacentVertex);
					}
				}
			}
			
			// when current vertex reaches the end, set the boolean to true and break the loop.
			if (currentVertex.equals(end)) {
				visited = true;
				break;
			}
		}
		
		//notify the observers that the search is over
		if (visited) {
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifySearchIsOver();
			}
		}

	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It will
	 * continue until EVERY vertex in the graph has been added to the finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes through
	 * the collection of Observers, calling notifyDijkstraVertexFinished on each one
	 * (passing the vertex that was just added to the finished set as the first
	 * argument, and the optimal "cost" of the path leading to that vertex as the
	 * second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the algorithm
	 * will calculate the "least cost" path of vertices leading from the starting
	 * vertex to the ending vertex. Next, it will go through the collection of
	 * observers, calling notifyDijkstraIsOver on each one, passing in as the
	 * argument the "lowest cost" sequence of vertices that leads from start to end
	 * (I.e. the first vertex in the list will be the "start" vertex, and the last
	 * vertex in the list will be the "end" vertex.)
	 * </P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end   special vertex used as the end of the path reported to observers
	 *              via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		//maps for weighted, predecessor vertices, and the visited set.
		Map<V, Integer> weightMap = new HashMap<V, Integer>();
		Map<V, V> predecessorMap = new HashMap<V, V>();
		HashSet<V> visitedSet = new HashSet<V>();
		
		// Notify all the elements that the Dijkstra is starting
		for (GraphAlgorithmObserver<V> element : observerList) {
			element.notifyDijkstraHasBegun();
		}
		
		// initialize weight map with starting vertex at 0 and other with max integer value.
		weightMap.put(start, 0);
		for (V current : wGraph.keySet()) {
			if (!current.equals(start)) {
				weightMap.put(current, Integer.MAX_VALUE);
			}
		}
		
		//initialize the predecessor vertices in the predecessor map to null.
		for (V currentVertex : wGraph.keySet()) {
			predecessorMap.put(currentVertex, null);
		}
		
		
		while (visitedSet.size() != wGraph.size()) {
			//local variable for cost and predecessor vertex
			int leastCost = Integer.MAX_VALUE;
			V predVertex = null;	
			
			// If the currentVertex is not yet visited
			for (V currentVertex : weightMap.keySet()) {
				if (!(visitedSet.contains(currentVertex))) {
					// Update leastCost & predecessor vertex if currentVertex cost is less than leastCost
					if (weightMap.get(currentVertex) < leastCost) {
						leastCost = weightMap.get(currentVertex);
						predVertex = currentVertex;
					}
				}
			}
			
			// add vertex to visitedSet & notify observer than the vertex is finished (update cost & predecessor)
			visitedSet.add(predVertex);
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(predVertex, weightMap.get(predVertex));
			}

			//  loop compares where which path has a lower cost and updates the predecessor vertex
			for (V current : wGraph.get(predVertex).keySet()) {
				if (!(visitedSet.contains(current))) {
					if (weightMap.get(predVertex) + getWeight(predVertex, current) < weightMap.get(current)) {
						weightMap.put(current, weightMap.get(predVertex) + getWeight(predVertex, current));
						// update the predecessor
						predecessorMap.put(current, predVertex);
					}
				}
			}
		}
		
		//list for shortest path
		ArrayList<V> shortestPath = new ArrayList<V>();
		
		//add the paths to the shortest path list.
		while (end != null) {
			shortestPath.add(0, end);
			end = predecessorMap.get(shortestPath.get(0));
		}
		// Notify observer that the algorithm is over and include the shortest path
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(shortestPath);
		}
	}

}
