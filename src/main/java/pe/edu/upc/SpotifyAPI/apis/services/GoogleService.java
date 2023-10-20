// package pe.edu.upc.SpotifyAPI.apis.services;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
//
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @Service
// public class GoogleService {
//
//     @Value("${google.api.key}")
//     private String googleApiKey;
//
//     public String getYTUrlFromSongName(String songName, String artistName) {
//         String searchQuery = songName + " " + artistName + " official audio";
//
//         String apiUrl = "https://www.googleapis.com/youtube/v3/search";
//         String params = "q=" + searchQuery + "&type=video&part=snippet&maxResults=1&key=" + this.googleApiKey;
//
//         RestTemplate restTemplate = new RestTemplate();
//         String response = restTemplate.getForObject(apiUrl + "?" + params, String.class);
//
//         try {
//             ObjectMapper objectMapper = new ObjectMapper();
//             JsonNode root = objectMapper.readTree(response);
//             JsonNode items = root.path("items");
//
//             if (items.isArray() && !items.isEmpty()) {
//                 String videoId = items.get(0).path("id").path("videoId").asText();
//                 return "https://www.youtube.com/watch?v=" + videoId;
//             } else {
//                 throw new RuntimeException("No se pudo encontrar el audio relacionado en YouTube.");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new RuntimeException("Error al obtener la URL del audio del video de YouTube.");
//         }
//     }
// }
