package parser;

import java.util.List;

/**
 * Sets the rules for creating the Json Objects. The JsonObject is required in a
 * specific format since Katago only allows this specific format. JsonObject
 * represents the query for KataGo.
 * 
 * @author Lionel Schreoder
 *
 */
public class InputJson {

	// The query requires an ID.
	private String id;
	// The stones on the board
	private List<Stone> moves;
	// Which stones where placed before the game started.
	private List<Stone> initialStones;
	// On which rule was the game started.
	private String rules = "tromp-taylor";
	// Each game requires a komi for a specific value. Default value is 7.5 ( if not
	// changed).
	private double komi = 7.5;

	// How many turns should be analysed
	private int[] analyzeTurns;
	// The Board size is required. For example 19 stones on x an 19 on y can be
	// places.
	private BoardSize boardSize;

	/**
	 * 
	 * @param moves          list of stones on the board.
	 * @param turnsToAnalyse turns which are required to be analysed.
	 */
	public InputJson(String id, BoardSize boardSize) {
		this.id = id;
		this.boardSize = boardSize;
	}

	/**
	 * Function allows to set the InitialStones also known as previous Stones.
	 * 
	 * @param initialStones
	 */
	public void setInitialStones(List<Stone> initialStones) {
		this.initialStones = initialStones;
	}

	public String getId() {
		return id;
	}

	public void setKomi(double komi) {
		this.komi = komi;
	}

	public double getKomi() {
		return komi;
	}

	public List<Stone> getMoves() {
		return moves;
	}

	public void setMoves(List<Stone> moves) {
		this.moves = moves;
	}

	public int[] getAnalyzeTurns() {
		return analyzeTurns;
	}

	public void setAnalyzeTurns(int[] analyzeTurns) {
		this.analyzeTurns = analyzeTurns;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getKomiToString() {
		return "\"komi\":" + getKomi();
	}

	@Override
	public String toString() {
		if (this.initialStones != null) {
			if (this.initialStones.size() > 0) {

				return "{" + getIdJsonFormat() + "," + getInitialStonesJsonFormat() + "," + getMovesJsonFormat() + ","
						+ getRulesJsonFormat() + "," + getKomiToString() + "," + boardSize.toString() + ","
						+ getAnalyseTurnsJsonFormat() + "}";
			}
		}
		return "{" + getIdJsonFormat() + "," + getMovesJsonFormat() + "," + getRulesJsonFormat() + ","
				+ getKomiToString() + "," + boardSize.toString() + "," + getAnalyseTurnsJsonFormat() + "}";
	}

	/**
	 * Function which allows to set a new Komi value.
	 * 
	 * @param str String line which contains the Komi.
	 */
	public void setKomiFromString(String str) {
		int indexStart = str.indexOf("KM") + 3;
		String subString = str.substring(indexStart, indexStart + 4);
		String value = "";
		for (char c : subString.toCharArray()) {
			if (c == ']')
				break;
			value += c;
		}

		double komi = Double.valueOf(value);
		System.out.println("Komi: " + komi);
		if (komi > 20)
			setKomi(6);
		else
			setKomi(komi);
	}

	/**
	 * 
	 * Functions which allows to create a respective JsonObject in such a way that
	 * KataGo wants it. The normal creation of JsonObjects.
	 */

	private String getInitialStonesJsonFormat() {
		String movesInString = "\"initialStones\":[";

		for (Stone stone : initialStones) {

			if (stone == initialStones.get(initialStones.size() - 1)) {
				System.out.println("kek");
				movesInString = movesInString + stone.toString();
			} else {
				System.out.println("help");
				movesInString = movesInString + stone.toString() + ",";
			}
		}
		movesInString = movesInString + "]";
		return movesInString;
	}

	private String getAnalyseTurnsJsonFormat() {
		String turns = "";
		int length = analyzeTurns.length;
		for (int i = 0; i < length; i++) {
			if (i == length - 1) {
				turns = turns + String.valueOf(analyzeTurns[i]);
			} else
				turns = turns + String.valueOf(analyzeTurns[i]) + ",";
		}
		return "\"analyzeTurns\":[" + turns + "]";
	}

	private String getMovesJsonFormat() {
		String movesInString = "\"moves\":[";

		for (Stone stone : moves) {

			if (stone == moves.get(moves.size() - 1)) {
				movesInString = movesInString + stone.toString();
			} else
				movesInString = movesInString + stone.toString() + ",";
		}
		movesInString = movesInString + "]";
		return movesInString;
	}

	private String getIdJsonFormat() {
		return "\"id\":\"" + getId() + "\"";
	}

	private String getRulesJsonFormat() {
		return "\"rules\":\"" + getRules() + "\"";
	}

}
