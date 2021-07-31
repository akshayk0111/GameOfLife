import java.util.Scanner;

public final class GameOfLife {

	// representing a dead cell in Hexadecimal format
	public final static int DEAD = 0x00;

	// representing a live cell in Hexadecimal format
	public final static int LIVE = 0x01;

	public final static void main(String[] args) {

		GameOfLife gof = new GameOfLife();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter grid of cells");
		int cells = sc.nextInt();
		gof.test(cells);  //testing the methods
	}
	// test class 
	private void test(int golIterations) {

		//life and dead cells are followed:

		int[][] board = { 	{ DEAD, DEAD, DEAD, DEAD, DEAD }, 
							{ DEAD, DEAD, DEAD, LIVE, DEAD },
							{ DEAD, DEAD, LIVE, LIVE, DEAD },
							{ DEAD, DEAD, DEAD, LIVE, DEAD }, 
							{ DEAD, DEAD, DEAD, DEAD, DEAD } };

		System.out.println("Generation GameOfLife");
		printBoard(board);

		for (int i = 0; i < golIterations; i++) {
			System.out.println();
			board = getNextBoard(board);
			printBoard(board);
		}
	}
	// Print board
	private void printBoard(int[][] board) {

		for (int i = 0, e = board.length; i < e; i++) {

			for (int j = 0, f = board[i].length; j < f; j++) {
				System.out.print(Integer.toString(board[i][j]) + " ");
			}
			System.out.println();
			
		}
	
	}

	// calculate if cells live on or die or new

	public int[][] getNextBoard(int[][] board) {

		// board does not have any values

		if (board.length == 0 || board[0].length == 0) {
			throw new IllegalArgumentException("must have a positive rows,columns");
		}

		int golRows = board.length;
		int golCols = board[0].length;

		// temporary board to store new values
		int[][] arr = new int[golRows][golCols];

		for (int row = 0; row < golRows; row++) {

			for (int col = 0; col < golCols; col++) {
				arr[row][col] = getNewCellState(board[row][col], getLiveNeighbours(row, col, board));
			}
			
		}
		return arr;
	}

	// live neighbors given the cell position

	private int getLiveNeighbours(int cellRow, int cellCol, int[][] board) {

		int liveNeighbours = 0;
		int rowEnd = Math.min(board.length, cellRow + 2);
		int colEnd = Math.min(board[0].length, cellCol + 2);

		for (int row = Math.max(0, cellRow - 1); row < rowEnd; row++) {

			for (int col = Math.max(0, cellCol - 1); col < colEnd; col++) {

				if ((row != cellRow || col != cellCol) && board[row][col] == LIVE) {
					liveNeighbours++;
				}
			}
		}
		return liveNeighbours;
		
	}

	// Get the new cell and the number of live neighbors of the cell

	private int getNewCellState(int currentState, int liveNeighbours) {

		int newState = currentState;
		//using switch case
		
		switch (currentState) {
		case LIVE:

			// Any live cell with fewer than two live neighbors dies, if loneliness

			if (liveNeighbours < 2) {
				newState = DEAD;
			}

			// Any live cell with more than three live neighbors dies, as if by overcrowding.
			
			if (liveNeighbours > 3) {
				newState = DEAD;
			}

			// Any live cell with two or three live neighbors lives on to the next generation.
			
			if (liveNeighbours == 2 || liveNeighbours == 3) {
				newState = LIVE;
			}
			break;

		case DEAD:

			// Any dead cell with exactly three live neighbors comes to life

			if (liveNeighbours == 3) {
				newState = LIVE;
			}
			break;

		default:
			throw new IllegalArgumentException("State of cell must be LIVE or DEAD");
		}
		return newState;
		
	}
}
