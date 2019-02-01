package AssignmentOne;

import java.io.File;
import java.util.ArrayList;

/**
 * CSC 520 - Artificial Intelligence
 * 
 * This program was created to fulfill the specifications of Assignment One
 * Question 4 - Search.
 * 
 * Problem involves creating a heuristic and graph-space search algorithms to
 * find the following for a number maze 1) best path 2) number of states
 * expanded by each algorithm when finding the path 3) total number of unique
 * paths through the graph for mazes that are 6x6 and 8x8 4) number of states
 * expanded in finding the paths
 * 
 * @author Carmen Bentley
 *
 */
public class AssignmentOne {
	/**
	 * Entry point of Assignment One Question 4 "Search"
	 * 
	 * @param args
	 *            List of command line arguments.
	 */
	public static void main(String args[]) {

		/* Solution to Maze */
		ArrayList<Position> solution = null;
		/* Allow Unique Path Search if variable is 1 */
		int uniSearch = 0;

		/* Ensure correct number of arguments */
		if (args.length != 2) {
			System.out.println("Program use Example : \"java AssignmentOne <search type> <full file pathname>\"");
			System.exit(0);
		}

		/* Ensure the search type is recognized */
		String search = args[0];
		if (!search.equalsIgnoreCase("BFS") && !search.equalsIgnoreCase("DFS") && !search.equalsIgnoreCase("BestFirst")
				&& !search.equalsIgnoreCase("UNI") && !search.equalsIgnoreCase("AStar")) {
			System.out
					.println("Search type \"" + search + "\" not recognized. Please use BFS, DFS, BestFirst, or AStar");
			System.exit(0);
		}

		/* Ensure the given file is valid & Create Maze */
		File inputFile = new File(args[1]);
		Maze maze = new Maze(inputFile);

		/* Output Maze Details to Console */
		System.out.println("\n\t------------------------  Maze Details ------------------------\n");
		System.out.println("\t\tFile Name          : " + maze.getFileName());
		System.out.println("\t\tFile Full Pathname : " + maze.getFullPathname());
		System.out.println("\t\tHeight             : " + maze.getHeight());
		System.out.println("\t\tWidth              : " + maze.getWidth());
		System.out.println("\t\tSearch Type        : " + search);
		System.out.println();
		maze.printMaze();

		/* Run the requested algorithm */
		if (search.equalsIgnoreCase("BFS")) {
			BreadthFirstSearch bfs = new BreadthFirstSearch(maze);
			solution = bfs.search();
		} else if (search.equalsIgnoreCase("DFS")) {
			DepthFirstSearch dfs = new DepthFirstSearch(maze);
			solution = dfs.search();
		} else if (search.equalsIgnoreCase("BestFirst")) {
			BestFirstSearch bestfs = new BestFirstSearch(maze);
			solution = bestfs.search();
		} else if (search.equalsIgnoreCase("UNI") && uniSearch == 1) {
			DepthFirstSearch dfs = new DepthFirstSearch(maze);
			System.out.println("\t\tNumber of Unique Paths = " + dfs.numUniquePaths());
			System.exit(0); // Terminate program with no error.
		} else if (search.equalsIgnoreCase("AStar")) {
			AStarSearch astar = new AStarSearch(maze);
			solution = astar.search();
		} else {
			System.out.println("\n\t\t***Unique Path Search Not Permitted at this time.***");
			System.exit(1);
		}

		/* Print maze solution */
		System.out.println("\t\tPositions in Solution (including goal) : " + solution.size());
		System.out.print("\n\t\tSolution : ");
		for (int index = 0; index < solution.size(); index++) {
			System.out.print(solution.get(index).toString());
			if (index % 15 == 0 && index > 10) {
				System.out.print("\n\t\t           ");
			}
		}
	}
}
