package AssignmentOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Class implements a Depth First Search Algorithm variation to determine a
 * solution for a give number maze.
 * 
 * Also runs a search for number of unique paths within a maze.
 * 
 * @author Carmen Aiken Bentley
 */
public class DepthFirstSearch {
	/* Stack to house Positions to be explored */
	private Stack<Position> stack;
	/* List of Positions that have been explored */
	private ArrayList<Position> explored;
	/* The maze being searched/solved */
	private Maze maze;
	/* Number of Positions that get expanded for exploration */
	private int expansions;
	/* Number of Unique Paths from Start to Goal */
	private int numUniquePaths = 0;

	/**
	 * Constructor Method that initializes all necessary attributes of the search.
	 * 
	 * @param maze
	 *            Maze being searched.
	 */
	public DepthFirstSearch(Maze maze) {
		this.stack = new Stack<Position>();
		this.explored = new ArrayList<Position>();
		this.maze = maze;
		this.expansions = 0;
	}

	/**
	 * Executes an iterative DFS algorithm variation to find a solution path from
	 * the initial state of the number maze to the goal state.
	 * 
	 * @return the list containing the solution positions if a solution exists.
	 *         Otherwise, null is returned.
	 */
	public ArrayList<Position> search() {

		/* Place initial state on stack to ensure loop starts below */
		stack.push(maze.getMaze()[0][0]);
		stack.peek().setExplored(true);
		explored.add(stack.peek()); // Add Position to the explored list

		/** Find a solution if possible **/
		while (!stack.isEmpty()) {

			Position p = stack.pop(); // Position to examine

			/* Check if p is the goal position */
			if (p.getValue() == -1) {
				// ----------------------------------------------------------------------------------------------------
				/* Neighbor is the Goal state, Get solution path. */
				stack.clear(); // clear the stack to stop while loop
				maze.addSolutionPoint(p); // add Goal Position to solution path

				/** Collect the shortest path in reverse **/
				int prev = explored.size(); // second iterator for explored list : barrier for solution searching
				for (int i = 0; i < maze.getSolution().size(); i++) {
					/* For each solution position find it's last neighbor in the explored list. */
					for (int j = explored.size() - 1; j >= 0; j--) {
						if (maze.getSolution().get(i).check(explored.get(j)) && j < prev) {
							maze.addSolutionPoint(explored.get(j));
							prev = j; // update barrier
							break;
						}
					}
					/* Check if we have reverse to initial search Position */
					if (maze.getSolution().get(i + 1).getWidth() == 0 && maze.getSolution().get(i + 1).getHeight() == 0)
						break; // Full solution found
				}

				Collections.reverse(maze.getSolution());

				/* Print Expansions to console and return solution */
				System.out.println("\n\t\tNumber of Expansions = " + expansions);
				return maze.getSolution();
				// ----------------------------------------------------------------------------------------------------
			}

			/* p is not the goals Position */
			p.explorePosition(); // get the neighbors of this Position
			expansions++; // Track number of expansions made during this search

			/* P has been explored : update attribute and add to explored list. */
			p.setExplored(true);
			explored.add(p);

			/** For each neighbor search and push ones that are not visited to stack **/
			for (int index = 0; index < p.getNeighbors().size(); index++) {
				Position neighbor = p.getNeighbors().get(index);
				if (!neighbor.isExplored()) {
					/* Neighbor has not been visited and is not the goal state */
					stack.push(neighbor);
				}
			}
		}

		System.out.println("No solution found | Number of Expansions = " + expansions);
		return null; // No solution to Maze
	}

	/**
	 * Function is the entry point to finding the number of unique paths in a give
	 * maze from start to goal.
	 * 
	 * @return number of unique paths from start to goal position.
	 */
	public int numUniquePaths() {
		/* Track visitation of positions in the maze - to be passed for recursion */
		boolean[][] visited = new boolean[maze.getWidth()][maze.getHeight()];

		System.out.println("\t\tNumber of Expansions = " + expansions);

		return searchUniquePaths(maze.getMaze()[0][0], numUniquePaths, visited);

	}

	/**
	 * Recursive DFS function to search for unique paths in a maze.
	 * 
	 * @param current
	 *            The current Position being examined.
	 * @param count
	 *            The number of unique paths found.
	 * @param visited
	 *            boolean[][] to track visitation of Positions during recursion.
	 * @return number of unique paths found in the maze from start to goal position.
	 */
	private int searchUniquePaths(Position current, int count, boolean[][] visited) {
		if (current.getValue() == -1) {
			/* Goal found */
			count++;
			return count; // End search in this unique path.
		}

		visited[current.getWidth()][current.getHeight()] = true; // update visitation matrix
		System.out.println("Expand + 1 -> " + expansions++); // used for testing purposes

		/* Check if neighbor has been visited yet, if not, recursively search */

		/** Right **/
		if (current.getWidth() + current.getValue() < maze.getWidth()
				&& !visited[current.getWidth() + current.getValue()][current.getHeight()])
			count = searchUniquePaths(maze.getMaze()[current.getWidth() + current.getValue()][current.getHeight()],
					count, visited);
		/** Left **/
		if (current.getWidth() - current.getValue() >= 0
				&& !visited[current.getWidth() - current.getValue()][current.getHeight()])
			count = searchUniquePaths(maze.getMaze()[current.getWidth() - current.getValue()][current.getHeight()],
					count, visited);
		/** Up **/
		if (current.getHeight() - current.getValue() >= 0
				&& !visited[current.getWidth()][current.getHeight() - current.getValue()])
			count = searchUniquePaths(maze.getMaze()[current.getWidth()][current.getHeight() - current.getValue()],
					count, visited);
		/** Down **/
		if (current.getHeight() + current.getValue() < maze.getHeight()
				&& !visited[current.getWidth()][current.getHeight() + current.getValue()])
			count = searchUniquePaths(maze.getMaze()[current.getWidth()][current.getHeight() + current.getValue()],
					count, visited);

		visited[current.getWidth()][current.getHeight()] = false; // update visitation matrix for return call

		return count; // No path found return to caller
	}
}
