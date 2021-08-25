package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Class which allows to create/setup the JsonObject which is required to pass
 * it as a Query to KataGo.
 * 
 * @author Lionel Schroeder
 *
 */
public class CleanText {
	// Regex used to detect stones which are placed on the board.
	public static Pattern patternSymboleBefore = Pattern.compile(";(B|W)\\[[a-z][a-z]\\]?");

	// Detect the previous stones. Stones which are on the board before the game
	// actually started.
	public static Pattern patternForStartingMoves = Pattern.compile("[a-z][a-z]");

	/**
	 * Function which allows to process the current sgf file.
	 * 
	 * @param File current sgf file.
	 * @returns the Final JsonObject as string to for the KataGo Querry.
	 */
	public static String processLiveOneLiner(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		InputJson jsonObject = new InputJson(file.getName(), new BoardSize(19, 19));
		// List of previously placed stones. Before the game started.
		List<Stone> previousMoves = new ArrayList<Stone>();

		// List of Stones on the board. (After the previously placed stones).
		List<Stone> moves = new ArrayList<Stone>();
		String text = "";
		String line = "";

		while ((text = br.readLine()) != null) {
			line += text;
		}
		// Remove all white spaces and new lines
		line = line.strip();

		if (line != null) {
			// Checks if the line contains a Komi value if yes set the new Komi value.
			if (line.contains("KM")) {
				jsonObject.setKomiFromString(line);
				System.out.println("Komi is: " + jsonObject.getKomi());
			}

			// Checks when there were Stones placed before the game started. (Previously
			// placed stones).
			if (line.contains("AB")) {
				int positionStart = line.indexOf("AB") + 2;
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = positionStart; i < line.length(); i++) {

					if (line.charAt(i) == ';') {
						break;
					}
					stringBuilder.append(line.charAt(i));
				}

				previousMoves = new ArrayList<Stone>();
				Matcher matcher = patternForStartingMoves.matcher(stringBuilder);
				// Create the stones from previously placed stones and add them to the
				// previously placed stones.
				while (matcher.find()) {
					System.out.println(matcher.group());
					System.out.println();
					Stone stone = new Stone("B", matcher.group());
					previousMoves.add(stone);
				}

			}

			// Find all stones which where placed after the previous stones and add them to
			// a list.
			Matcher matcherActualMoves = patternSymboleBefore.matcher(line);
			while (matcherActualMoves.find()) {
				String moveWithoutSymbol = matcherActualMoves.group().replace(";", "");
				Stone stone = new Stone(moveWithoutSymbol);
				moves.add(stone);
			}

			jsonObject.setInitialStones(previousMoves);

			// Sets how many moves have to be analysed. In this case all the moves are
			// required to be analyzed.
			int[] movesToAnalyse = IntStream.range(0, moves.size()).toArray();
			jsonObject.setMoves(moves);

			jsonObject.setAnalyzeTurns(movesToAnalyse);
		}
		// Finally return jsonObject as a string format which is in Json. Else return
		// NoMoves.
		if (moves.size() > 0)
			return jsonObject.toString();
		else
			return "NoMoves";

	}

}
