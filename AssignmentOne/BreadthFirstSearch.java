package AssignmentOne;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class implements a Breadth First Search Algorithm variation to determine the
 * shortest solution for a give number maze.
 * 
 * @author Carmen Aiken Bentley
 */
public class BreadthFirstSearch {

	/* List containing the current frontier of Maze Positions to be explored */
	private ArrayList<Position> frontier;
	/* List of Maze Positions that have been explored */
	private ArrayList<Position> explored;
	/* The maze being searched/solved */
	private Maze maze;
	/* Number of Positions that get expanded for exploration */
	private int expansions;

	/**
	 * Constructor Method that initializes all necessary attributes of the search.
	 * 
	 * @param maze
	 *            Maze being searched.
	 */
	public BreadthFirstSearch(Maze maze) {
		/* Array of Positions to be explored */
		this.frontier = new ArrayList<Position>();
		/* Array of Positions that have been explored */
		this.explored = new ArrayList<Position>();
		/* Maze being solved */
		this.maze = maze;
		/* Number of expansions done during the search */
		this.expansions = 0;
	}

	/**
	 * Executes a BFS algorithm variation to find the shortest path from the initial
	 * state of the number maze to the goal state.
	 * 
	 * @return the list containing the solution positions if a solution exists.
	 *         Otherwise, null is returned.
	 */
	public ArrayList<Position> search() {

		/* Add initial state Position to frontier list to being searching the maze. */
		frontier.add(maze.getMaze()[0][0]);

		/** Search for solution **/
		while (frontier.size() != 0) {
			/* Remove first element of frontier for examination */
			Position front = frontier.remove(0);

			/* Check if front Position is goal Position */
			if (front.getValue() == -1) {
				// ---------------------------------------------------------------------------------------------------------
				maze.addSolutionPoint(front); // front is the goal Position

				/** Collect the shortest path in reverse **/
				for (int i = 0; i < maze.getSolution().size(); i++) {
					/*
					 * For each position in the solution list find it's earliest neighbor in the
					 * explored list
					 */
					for (int j = 0; j < explored.size(); j++) {
						if (maze.getSolution().get(i).check(explored.get(j))) {
							maze.addSolutionPoint(explored.get(j));
							break;
						}
					}
					if (maze.getSolution().get(i + 1).getWidth() == 0 && maze.getSolution().get(i + 1).getHeight() == 0)
						break; // Full solution found
				}

				/* Reverse the order of the solution since it was collected backward */
				Collections.reverse(maze.getSolution());

				/* Print Expansions to console */
				System.out.println("\n\t\tNumber of Expansions : " + expansions);

				/* Return correctly ordered solution to caller */
				return maze.getSolution();

				// ---------------------------------------------------------------------------------------------------------
			}

			/* Front is not the goal, explore front and find possible transitions states */
			front.explorePosition();
			explored.add(front);
			expansions++; // Track number of expansion made in this search

			for (int index = 0; index < front.getNeighbors().size(); index++) {
				/* Add all neighbors of front to frontier */
				Position neighbor = front.getNeighbors().get(index); // neighbor of Position

				/** If neighbor is not explored or not in frontier, add to frontier **/
				if (!neighbor.isExplored() && !frontier.contains(neighbor)) {
					frontier.add(neighbor); // neighbor is not the goal
				}
			}
		}

		System.out.println("\n\t\tNo solution found | Number of Expansions = " + expansions);
		return null; // No solution to Maze
	}
}
