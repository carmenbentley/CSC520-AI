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
public class BestFirstSearch {
	/* Maze being searched */
	private Maze maze;
	/* Queue of Positions to be explored, sorted by heuristic value */
	private PriorityQueue<Position> open;
	/* List of positions that have been explored */
	private ArrayList<Position> closed;
	/* Number of expansions made during the search */
	private int expansions;

	/**
	 * Constructor Method that initializes all necessary attributes of the search.
	 * 
	 * @param maze
	 *            Maze being searched.
	 */
	public BestFirstSearch(Maze maze) {
		this.maze = maze;
		this.open = new PriorityQueue<Position>();
		this.closed = new ArrayList<Position>();
		this.expansions = 0;
	}

	/**
	 * Executes a BestFS algorithm variation to find a solution path from the
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

		open.add(maze.getMaze()[0][0]); // Add start position of maze to Priority Queue

		while (!open.isEmpty()) {

			Position p = open.poll(); // Remove highest priority position from open queue

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
				System.out.println("\n\t\tNumber of Expansions = " + expansions);

				/* Return correctly ordered solution to caller */
				return maze.getSolution();
				// ---------------------------------------------------------------------------------------------------
			}

			/* Position not the Goal, add to the closed list and continue exploring */
			closed.add(p);
			p.explorePosition(); // get the neighbors of p
			expansions++;

			/* For each neighbor that hasn't been explored, add to the Priority Queue */
			for (int i = 0; i < p.getNeighbors().size(); i++) {
				if (!closed.contains(p.getNeighbors().get(i))) {
					open.add(p.getNeighbors().get(i));
					/**
					 * Priority Queue will sort the entries with a comparison to the heuristic value
					 * assigned by h(n)
					 **/
				}
			}
		}

		System.out.println("\t\tNo solution found | Number of Expansions = " + expansions);
		return null; // No solution to Maze
	}
}
