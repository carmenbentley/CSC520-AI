package AssignmentOne;

import java.util.ArrayList;

/**
 * Class that represent a Position in the Maze.
 * 
 * @author Carmen Bentley
 *
 */
public class Position implements Comparable<Position> {
	/* X coordinate of the Position in the Maze */
	protected int width;
	/* Y coordinate of the Position in the Maze */
	protected int height;
	/* Value of the Position in the Maze */
	protected int value;
	/* List of neighbors to this Position */
	private ArrayList<Position> neighbors;
	/* Maze for which this Position belongs */
	protected Maze maze;
	/* Tracks if this Position has been explored during a search of the maze */
	private boolean explored;

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
	public Position(int x, int y, int value, Maze maze) {
		this.width = x;
		this.height = y;
		this.value = value;
		this.neighbors = new ArrayList<Position>();
		this.maze = maze;
		explored = false;
	}

	/**
	 * Returns the X coordinate of the Position
	 * 
	 * @return the X coordinate of the Position.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the value of the Position
	 * 
	 * @return the value of the Position.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Returns the Y coordinate of the Position
	 * 
	 * @return the Y coordinate of the Position.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns true if the Position has been explored during a search.
	 * 
	 * @return true if the Position has been explored during a search.
	 */
	public boolean isExplored() {
		return explored;
	}

	/**
	 * Sets the value of the explored attribute of this Position.
	 * 
	 * @param explored
	 *            value for explored to be set.
	 */
	public void setExplored(boolean explored) {
		this.explored = explored;
	}
	
	/**
	 * Returns the list of neighbors associated with this Position
	 * @return the list of neighbors associated with this Position
	 */
	public ArrayList<Position> getNeighbors() {
		return neighbors;
	}

	/**
	 * Returns a string value of this Position in form (x,y)
	 * 
	 * @return a string value of this Position in form (x,y)
	 */
	public String toString() {
		return "(" + width + "," + height + ")";
	}

	/**
	 * Gathers the neighbors of this Position in the Maze and adds them to the
	 * Neighbor list attribute.
	 * 
	 * @param maze
	 *            Maze for which this Position belongs.
	 */
	public void explorePosition() {
		this.explored = true;
		/** Right **/
		if (this.width + this.value < maze.getWidth())
			neighbors.add(maze.getMaze()[this.width + this.value][this.height]);
		/** Left **/
		if (this.width - this.value >= 0)
			neighbors.add(maze.getMaze()[this.width - this.value][this.height]);
		/** Up **/
		if (getHeight() - this.value >= 0)
			neighbors.add(maze.getMaze()[this.width][this.height - this.value]);
		/** Down **/
		if (getHeight() + getValue() < maze.getHeight())
			neighbors.add(maze.getMaze()[this.width][this.height + this.value]);
	}

	/**
	 * Returns true if Position p is a neighbor of this Position.
	 * 
	 * @param p
	 *            Neighbor Position to examine.
	 * @return true if Position p is a neighbor of this Position and false
	 *         otherwise.
	 */
	public boolean check(Position p) {

		/** Right **/
		if (p.getWidth() + p.getValue() == this.width && p.getHeight() == this.height)
			return true;
		/** Left **/
		else if (p.getWidth() - p.getValue() == this.width && p.getHeight() == this.height)
			return true;
		/** Up **/
		else if (p.getHeight() - p.getValue() == this.height && p.getWidth() == this.width)
			return true;
		/** Down **/
		else if (p.getHeight() + p.getValue() == this.height && p.getWidth() == this.width)
			return true;

		return false;
	}

	/**
	 * Comparator function that overrides the current comparison definition. Uses a
	 * heuristic to determine worth(cost) of a Position based on its placement from
	 * the maze GOAL.
	 * 
	 * h(n) = 1 if the current Position is in the same row or column as the goal
	 * Position h(n) = 2 if the current Position is in a different row or column
	 * from the goal Position NOTE: This is an admissible heuristic as it will never
	 * overestimate the number of moves(cost) from the current position to the goal
	 * position. - Same row or column --> best case we need one move to get to GOAL
	 * - Different row or column --> best case we need two moves to get to GOAL
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

		if (this.maze.getGoal().getHeight() == this.getHeight() || this.maze.getGoal().getWidth() == this.getWidth())
			thisHeuristic = 1; // Position in same row or col as goal
		else
			thisHeuristic = 2; // Position not in same row or col as goal
		if (that.maze.getGoal().getHeight() == that.getHeight() || that.maze.getGoal().getWidth() == that.getWidth())
			thatHeuristic = 1; // Position in same row or col as goal
		else
			thatHeuristic = 2; // Position not in same row or col as goal
		if (this.maze.getGoal().getHeight() == this.getHeight() && this.maze.getGoal().getWidth() == this.getWidth())
			thisHeuristic = 0; // Position is goal
		if (that.maze.getGoal().getHeight() == that.getHeight() && that.maze.getGoal().getWidth() == that.getWidth())
			thatHeuristic = 0; // Position is goal

		return thisHeuristic - thatHeuristic;
	}
}
