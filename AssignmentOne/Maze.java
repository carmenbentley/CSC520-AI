package AssignmentOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class defines a Number Maze kept in a text file.
 * 
 * @author Carmen Bentley
 */
public class Maze {
	/* Name of the file containing the Maze. */
	private String fileName;
	/* Actual pathname of file containing the Maze */
	private String fullPathname;
	/* Height of the Maze */
	private int height;
	/* Width of the Maze */
	private int width;
	/* File containing the Maze */
	private File inputFile;
	/* Matrix representation of the Maze */
	private Position[][] Maze;
	/* Matrix representation of the Maze using StarPosition Objects */
	private StarPosition[][] StarMaze;
	/* List containing solution path for this Maze */
	private ArrayList<Position> solution;
	/* Position Object representing the Goal of the Maze */
	private Position goal;
	/* Number of hops taken so far to reach goal */
	private int hops;

	/**
	 * Constructor method for Maze Object. Initialize all object states.
	 * 
	 * @param inputFile
	 *            File containing maze.
	 */
	public Maze(File inputFile) {
		this.inputFile = inputFile;
		this.fileName = inputFile.getName();
		this.fullPathname = inputFile.getAbsolutePath();
		this.solution = new ArrayList<Position>();
		this.hops = 0;
		processFile();
		Maze = new Position[height][width];
		StarMaze = new StarPosition[height][width];
		fillMaze();
	}

	/**
	 * Returns the goal Position of the Maze.
	 * 
	 * @return the goal Position of the Maze.
	 */
	public Position getGoal() {
		return goal;
	}

	/**
	 * Returns the number of hops that have been taken so far in the Maze.
	 * 
	 * @return the goal Position of the Maze.
	 */
	public int getHops() {
		return hops;
	}

	/**
	 * Increments the number of hops taken in the search.
	 */
	public void hop() {
		this.hops++;
	}

	/**
	 * Adds a solution Position to the solution path of this Maze.
	 */
	public void addSolutionPoint(Position point) {
		this.solution.add(point);
	}

	/**
	 * Returns the solution path for this Maze.
	 * 
	 * @return the solution path for this Maze.
	 */
	public ArrayList<Position> getSolution() {
		return this.solution;
	}

	/**
	 * Returns the FileName of the Maze
	 * 
	 * @return the FileName of the Maze
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Returns the fullPathname of this Maze.
	 * 
	 * @return the fullPathname of this Maze.
	 */
	public String getFullPathname() {
		return fullPathname;
	}

	/**
	 * Returns the height of the maze.
	 * 
	 * @return the height of the maze.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the width of the maze.
	 * 
	 * @return the width of the maze.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the matrix representation of this Maze.
	 * 
	 * @return the matrix representation of this Maze.
	 */
	public Position[][] getMaze() {
		return this.Maze;
	}
	
	/**
	 * Returns the matrix representation of this StarMaze.
	 * 
	 * @return the matrix representation of this StarMaze.
	 */
	public StarPosition[][] getStarMaze() {
		return this.StarMaze;
	}

	/**
	 * Scans the maze file to populates maze attributes such as height and width.
	 */
	private void processFile() {
		try {
			Scanner input = new Scanner(this.inputFile);
			while (input.hasNextLine()) {
				if (height == 0) {
					String[] line = input.nextLine().split(",");
					width = line.length;
					height++;
				}
				height++;
				input.nextLine();
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to process Maze File while processing Maze.");
			e.printStackTrace();
		}
	}

	/**
	 * Populates the matrix representation of the maze with Position objects.
	 */
	private void fillMaze() {
		try {
			Scanner input = new Scanner(this.inputFile);
			int row = 0;
			while (input.hasNextLine()) {
				String[] line = input.nextLine().split(",");
				for (int col = 0; col < width; col++) {
					if (line[col].equals("G")) {
						StarMaze[row][col] = new StarPosition(row, col, -1, this); // -1 represents the Goal cell
						Maze[row][col] = new Position(row, col, -1, this); // -1 represents the Goal cell
						this.goal = new Position(row, col, -1, this);
					} else {
						Maze[row][col] = new Position(row, col, Integer.parseInt(line[col]), this);
						StarMaze[row][col] = new StarPosition(row, col, Integer.parseInt(line[col]), this);
					}
				}
				row++;
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to process Maze File while filling Maze values.");
			e.printStackTrace();
		}
	}

	/**
	 * Prints the Maze formatted as seen in the file.
	 */
	public void printMaze() {
		for (int row = 0; row < height; row++) {
			if (Maze[row][0].getValue() < 10)
				System.out.print(" ");
			System.out.print("                                 " + Maze[row][0].getValue());
			for (int col = 1; col < width; col++) {
				if(Maze[row][col].getValue() == -1)
					System.out.print(", G");
				else {
					if (Maze[row][col].getValue() < 10)
						System.out.print(", " + Maze[row][col].getValue());
					else
						System.out.print("," + Maze[row][col].getValue());
				}
			}
			System.out.println();
		}
	}
}
