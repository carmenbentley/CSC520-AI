package AssignmentOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Class implements a Best First Search Algorithm variation to determine a
 * solution for a give number maze.
 * 
 * @author Carmen Aiken Bentley
 */
public class AStarSearch {
	/* Maze being searched */
	private Maze maze;
	/* Queue of Positions to be explored, sorted by heuristic value */
	private PriorityQueue<StarPosition> open;
	/* List of positions that have been explored */
	private ArrayList<StarPosition> closed;
	private int expansions = 0;

	/**
	 * Constructor Method that initializes all necessary attributes of the search.
	 * 
	 * @param maze
	 *            Maze being searched.
	 */
	public AStarSearch(Maze maze) {
		this.maze = maze;
		this.open = new PriorityQueue<StarPosition>();
		this.closed = new ArrayList<StarPosition>();
	}

	/**
	 * Executes an AStar algorithm variation to find a solution path from the
	 * initial state of the number maze to the goal state.
	 * 
	 * Function relies on a heuristic value to sort the Priority Queue.
	 * 
	 * h(n) = 1 if the current Position is in the same row or column as the goal
	 * Position h(n) = 2 if the current Position is in a different row or column
	 * from the goal Position
	 * 
	 * NOTE: This is an admissible heuristic as it will never overestimate the
	 * number of moves(cost) from the current position to the goal position. - Same
	 * row or column --> best case we need one move to get to GOAL - Different row
	 * or column --> best case we need two moves to get to GOAL
	 * 
	 * @return the list containing the solution positions if a solution exists.
	 *         Otherwise, null is returned.
	 */
	public ArrayList<Position> search() {

		open.add(maze.getStarMaze()[0][0]); // Add start position of maze to Priority Queue
		
		while (!open.isEmpty()) {
			StarPosition p = open.poll(); // Remove highest priority position from Priority Queue according to f(n)

			if (p.getHeight() == maze.getGoal().getHeight() && p.getWidth() == maze.getGoal().getWidth()) {
				// ---------------------------------------------------------------------------------------------------
				maze.addSolutionPoint(p); // p is the goal

				/** Collect the shortest path in reverse **/
				for (int i = 0; i < maze.getSolution().size(); i++) {
					/* For each solution Position find it's earliest neighbor in the closed list */
					for (int j = 0; j < closed.size(); j++) {
						if (maze.getSolution().get(i).check(closed.get(j))) {
							maze.addSolutionPoint(closed.get(j));
							break;
						}
					}
					/** Check if the full solution has been found - neighbor == start position **/
					if (maze.getSolution().get(i + 1).getWidth() == 0 && maze.getSolution().get(i + 1).getHeight() == 0)
						break; // Full solution found
				}

				/* Reverse the order of the solution since it was collected backward */
				Collections.reverse(maze.getSolution());

				/* Print Expansions to console */
				System.out.println("\n\t\tExpansions: " + expansions);
				/* Return correctly ordered solution to caller */
				return maze.getSolution();
				// ---------------------------------------------------------------------------------------------------
			}

			/* Position p is not the Goal */
			p.exploreStarPosition(); // get the neighbors of p
			expansions++; // increment expansions

			/* For each neighbor that hasn't been explored, add to the Priority Queue */
			for (int i = 0; i < p.getStarNeighbors().size(); i++) {
				if (!closed.contains(p.getStarNeighbors().get(i))) {
					open.add(p.getStarNeighbors().get(i));
					/**
					 * Priority Queue will sort the entries with a comparison to the heuristic value
					 * assigned by f(n)
					 **/
				}
			}

			closed.add(p); // Push current position onto closed list
			maze.hop(); // increment number of hops from starting position to current position
		}
		System.out.println("\t\tNo solution found | Number of Expansions = " + expansions);
		return null; // No solution to Maze
	}
}
