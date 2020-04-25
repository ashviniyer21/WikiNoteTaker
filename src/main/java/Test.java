import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import org.json.JSONObject;

public class Test {

    private static final String API_KEY = "your_api_key";

    public static void main(String... args) throws Exception {
        var jsonObject = new JSONObject();
        jsonObject.put("source", "https://en.wikipedia.org/wiki/Timeline_of_the_near_future");
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.pdfshift.io/v2/convert"))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .header("Authentication", "Basic ")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();

        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

        var statusCode = response.statusCode();
        if (statusCode == 200 || statusCode == 201) {
            // Save the file locally
            var targetFile = new File("notes.pdf");
            Files.copy(response.body(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            // error occurred
        }
    }
}