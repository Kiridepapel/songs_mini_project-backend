package pe.edu.upc.SpotifyAPI.apis.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RapidApiService {
    
    @Value("${rapid.api.key}")
    private String rapidApiKey;

    // Youtube MP3
    String rapidApiHost = "youtube-mp36.p.rapidapi.com";
    String youtubeMp3Url = "https://youtube-mp36.p.rapidapi.com/dl";
    
    public String getAudioUrl(String youtubeUrl) throws IOException, InterruptedException {
        String youtubeId = getYTIdFromYTUrl(youtubeUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(youtubeMp3Url + "?id=" + youtubeId))
            .header("x-rapidapi-key", this.rapidApiKey)
            .header("x-rapidapi-host", this.rapidApiHost)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        return jsonObject.getString("link");
    }
    
    private String getYTIdFromYTUrl(String youtubeUrl) {
        String[] parts = youtubeUrl.split("=");
        return parts[1];
    }
}