package graph;

import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	
	/**
	 * Constructor that turns the Maze into a graph.
	 * 
	 * 
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and Y
	 * coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to A.
	 * The weight to be used for these edges is provided by the Maze. (The Maze
	 * methods getMazeWidth and getMazeHeight can be used to determine the number of
	 * Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a wall
	 * between any two adjacent junctures. The Maze methods called "getWeightAbove",
	 * "getWeightToRight", etc. should be used to obtain the weights.)
	 * </P>
	 * 
	 * @param maze to be used as the source of information for adding vertices and
	 *             edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		//calls super constructor 
		super();
		
		//add vertex with junctures to the maze using the loop through the maze
		for (int x = 0; x < maze.getMazeWidth(); x++) {
			for (int y = 0; y < maze.getMazeHeight(); y++) {
				addVertex(new Juncture(x, y));
			}
		}
		
		//loops through the whole maze
		for (int x = 0; x < maze.getMazeWidth(); x++) {
			for (int y = 0; y < maze.getMazeHeight(); y++) {
				
				// Assign current junction and the junctions for the top, bottom, left, right direction.
				Juncture currJ = new Juncture(x, y);
				Juncture right = new Juncture(x + 1, y);
				Juncture left = new Juncture(x - 1, y);
				Juncture top = new Juncture(x, y - 1);
				Juncture bottom = new Juncture(x, y + 1);
				
				// Check each wall direction and adjacent vertices before adding the edge.
				
				// if no wall above and has top vertex, add the edge and weight.
				if (!(maze.isWallAbove(currJ)) && containsVertex(top)) {
					addEdge(currJ, top, maze.getWeightAbove(currJ));
				}
				
				// if no wall below and has bottom vertex, add the edge and weight.
				if (!(maze.isWallBelow(currJ)) && containsVertex(bottom)) {
					addEdge(currJ, bottom, maze.getWeightBelow(currJ));
				}
				// if no wall at the right and has right vertex, add the edge and weight.
				if (!(maze.isWallToRight(currJ)) && containsVertex(right)) {
					addEdge(currJ, right, maze.getWeightToRight(currJ));
				}
				// if no wall at the left and has left vertex, add the edge and weight.
				if (!(maze.isWallToLeft(currJ)) && containsVertex(left)) {
					addEdge(currJ, left, maze.getWeightToLeft(currJ));
				}
			}
		}
	}

}
