package parser;

/**
 * Sets the rules for one specific stone. The Player and the respective
 * position.
 * 
 * @author Lionel Schroeder
 *
 */
public class Stone {
	/**
	 * Player is the human which plays the game. The position describes where the
	 * human placed his stone.
	 */
	private String player, position;
	private int x;
	private int y;

	/**
	 * Token is the complete string containing the player and the position.
	 * 
	 * @param token
	 */
	public Stone(String token) {
		this.player = getPayerFromToken(token);
		this.position = getPositionFromToken(token);
		setX(token);
		setY(token);
	}

	public Stone(String player, String position) {
		this.player = player;
		this.position = getPositionFromTokenForPrevious(position);
	}

	/**
	 * Function which allows to retrieve the Player from the String passed as
	 * parameter. (Token contains Player and Position i.e : B[XY]).
	 * 
	 * @param token String which contains the Player and the respective position of
	 *              the stone.
	 * @return the player either B for black or W for white.
	 */
	private String getPayerFromToken(String token) {
		return String.valueOf(token.charAt(0)).toUpperCase();
	}

	/**
	 * Get the position from token without a specific player. (Used for previous
	 * moves).
	 * 
	 * @param token String containing the stones moves without having the player in
	 *              the String.
	 * @return String containg the position of the stone. E.g (10,8). (Without the
	 *         player B or W).
	 */
	private String getPositionFromTokenForPrevious(String token) {
		int asciiX = (int) token.charAt(0) - 97;
		int asciiY = (int) (token.charAt(1) - 97);
		return "(" + asciiX + "," + asciiY + ")";
	}

	/**
	 * Function which allows to retrieve the position of the token.
	 * 
	 * @param token containing the player and position of the Stone. e.g: B[XY].
	 *              Where B is Black.
	 * @return
	 */
	private String getPositionFromToken(String token) {
		int asciiX = (int) token.charAt(2) - 97;
		int asciiY = (int) (token.charAt(3) - 97);

		return "(" + asciiX + "," + asciiY + ")";
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(String token) {
		this.x = (int) token.charAt(2) - 97;
	}

	public void setY(String token) {
		this.y = (int) (token.charAt(3) - 97);
	}

	public String getPlayer() {
		return player;
	}

	public String getPosition() {
		return position;
	}

	// place this in to string for converting
	public String customToString() {
		return "[\"" + getPlayer() + "\",\"" + getPosition() + "\"]";
	}

	@Override
	public String toString() {
		return "[\"" + getPlayer() + "\",\"" + getPosition() + "\"]";
	}

}
