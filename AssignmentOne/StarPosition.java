package AssignmentOne;

import java.util.ArrayList;

/**
 * Class that represent a Position in the Maze specific for the AStar Search.
 * 
 * @author Carmen Bentley
 *
 */
public class StarPosition extends Position {
	/* List of neighbors to this Position */
	private ArrayList<StarPosition> starNeighbors;
	/* A* Cost */
	int cost;

	/**
	 * Constructor method that initializes all necessary attributes of a Position
	 * 
	 * @param x
	 *            X coordinate of the Position
	 * @param y
	 *            Y coordinate of the Position
	 * @param value
	 *            value of the Position
	 * @param maze
	 *            Maze the Position belongs to
	 */
	public StarPosition(int x, int y, int value, Maze maze) {
		super(x, y, value, maze);
		this.starNeighbors = new ArrayList<StarPosition>();
		this.cost = 0;
	}

	/**
	 * Gathers the neighbors of this Position in the Maze and adds them to the
	 * Neighbor list attribute.
	 * 
	 * @param maze
	 *            Maze for which this Position belongs.
	 */
	public void exploreStarPosition() {
		/** Right **/
		if (this.width + this.value < maze.getWidth())
			starNeighbors.add(maze.getStarMaze()[this.width + this.value][this.height]);
		/** Left **/
		if (this.width - this.value >= 0)
			starNeighbors.add(maze.getStarMaze()[this.width - this.value][this.height]);
		/** Up **/
		if (getHeight() - this.value >= 0)
			starNeighbors.add(maze.getStarMaze()[this.width][this.height - this.value]);
		/** Down **/
		if (getHeight() + getValue() < maze.getHeight())
			starNeighbors.add(maze.getStarMaze()[this.width][this.height + this.value]);
	}

	/**
	 * Returns the list of neighbors associated with this Position
	 * 
	 * @return the list of neighbors associated with this Position
	 */
	public ArrayList<StarPosition> getStarNeighbors() {
		return this.starNeighbors;
	}

	/**
	 * Comparator function that overrides the current comparison definition. Uses a
	 * heuristic to determine worth(cost) of a Position based on its placement from
	 * the maze GOAL.
	 * 
	 * f(n) = g(n) + h(n)
	 * 
	 * g(n) = actual number of hops taken from start Position to current Position
	 * 
	 * h(n) = 1 if the current Position is in the same row or column as the goal
	 * Position h(n) = 2 if the current Position is in a different row or column
	 * from the goal Position NOTE: This is an admissible heuristic as it will never
	 * overestimate the number of moves(cost) from the current position to the goal
	 * position. - Same row or column --> best case we need one move to get to GOAL
	 * - Different row or column --> best case we need two moves to get to GOAL. The
	 * addition of g(n) will also never over estimate because the cost of the path
	 * will most definitely include g(n).
	 * 
	 * @param p2
	 *            Position being compared to this Position.
	 * @return positive value if p2 is greater than this Position, negative value if
	 *         p2 is less than this Position, and zero if they are equal.
	 */
	@Override
	public int compareTo(Position p2) {
		int thisHeuristic = 0;
		int thatHeuristic = 0;
		Position that = p2;

		/* Assign f(n) for THIS position */
		if (this.maze.getGoal().getHeight() == this.getHeight() || this.maze.getGoal().getWidth() == this.getWidth())
			thisHeuristic = maze.getHops() + 1; // Position in same row or col as goal
		else
			thisHeuristic = maze.getHops() + 2; // Position not in same row or col as goal
		if (this.maze.getGoal().getHeight() == this.getHeight() && this.maze.getGoal().getWidth() == this.getWidth())
			thisHeuristic = maze.getHops() + 0; // Position is goal

		/* Assign f(n) for P2 position */
		if (that.maze.getGoal().getHeight() == that.getHeight() || that.maze.getGoal().getWidth() == that.getWidth())
			thatHeuristic = maze.getHops() + 1; // Position in same row or col as goal
		else
			thatHeuristic = maze.getHops() + 2; // Position not in same row or col as goal
		if (that.maze.getGoal().getHeight() == that.getHeight() && that.maze.getGoal().getWidth() == that.getWidth())
			thatHeuristic = maze.getHops() + 0; // Position is goal

		return thisHeuristic - thatHeuristic;
	}
}
