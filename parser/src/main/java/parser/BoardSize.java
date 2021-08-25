package parser;

/**
 * Set the boardsize X and y respectively.
 * 
 * @author Lionel Schroeder
 *
 */
public class BoardSize {
	private int boardXSize, boardYSize;

	public BoardSize(int boardXSize, int boardYSize) {
		this.boardXSize = boardXSize;
		this.boardYSize = boardYSize;
	}

	public int getBoardXSize() {
		return boardXSize;
	}

	public int getBoardYSize() {
		return boardYSize;
	}

	@Override
	public String toString() {
		return "\"boardXSize\":" + getBoardXSize() + ",\"boardYSize\":" + getBoardYSize();
	}

}
