package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Main {

	// Input data File.
	private static String inputData = "C:\\Users\\schro\\OneDrive\\Desktop\\Data_Forschung\\dataToAnalyse\\Cheater";
	// Where to output the data received from KataGo.
	private static String outputData = "C:\\Users\\schro\\OneDrive\\Desktop\\Data_Forschung\\outPutAfterAnalysis\\Cheater";

	public static void main(String[] args) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		// Input SGF files which are required to be analyzed.
		File[] files = new File(inputData).listFiles();
		// Output files. Used to not compute the same file again.
		File[] filesCreated = new File(outputData).listFiles();

		for (File file : files) {

			if (file.isFile()) {
				// Checks if the file was already send to KataGo.
				// Improvement's can be done here. (To not check every time).
				int flag = 0;
				for (File fil : filesCreated) {
					if (fil.getName().contains(file.getName())) {
						flag = 1;
						System.out.println("File already exists.");
						break;
					}
				}

				// If file not already send to KataGo. Thus flag =0.
				if (flag == 0) {
					// Create the jsonQuery String.
					String jsonQuery = CleanText.processLiveOneLiner(file);
					// If string contains noMoves do not send to KataGo.
					if (jsonQuery.contains("NoMoves")) {

					} else {
						// Setting up the HttpRequest for the KataGoServer.
						// Post Request.
						HttpPost request = new HttpPost("http://127.0.0.1:2718/analyse/katago_gtp_bot");
						StringEntity params = new StringEntity(jsonQuery);
						request.addHeader("content-type", "application/json");
						request.setEntity(params);
						
						// Response.
						HttpResponse response = httpClient.execute(request);
						String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
						whenWriteStringUsingBufferedWritter_thenCorrect(responseBody.trim(), file.getName());
					}

				}

			}

		}
	}

	/**
	 * Function which allos to write a string to a file.
	 * 
	 * @param jsonString KataGo response string which is a JSON. (However can be any
	 *                   string).
	 * @param fileName   The name of the file.
	 * @throws IOException
	 */
	public static void whenWriteStringUsingBufferedWritter_thenCorrect(String jsonString, String fileName)
			throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(outputData + "\\" + fileName + ".txt"));
		writer.write(jsonString);

		writer.close();
	}

}
