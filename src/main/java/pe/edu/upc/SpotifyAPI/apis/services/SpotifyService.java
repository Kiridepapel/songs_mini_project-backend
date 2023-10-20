package pe.edu.upc.SpotifyAPI.apis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.upc.SpotifyAPI.apis.dtos.ArtistDto;
import pe.edu.upc.SpotifyAPI.apis.dtos.TrackDto;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String spotifyClientId;

    @Value("${spotify.client.secret}")
    private String spotifyClientSecret;

    private final WebClient webClient;

    public SpotifyService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.spotify.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<TrackDto> searchTracks(String query) throws JsonMappingException, JsonProcessingException {
        String accessToken = getAccessToken();
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search")
                        .queryParam("q", query)
                        .queryParam("type", "track")
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        JsonNode tracks = root.path("tracks").path("items");
    
        List<TrackDto> trackDTOs = new ArrayList<>();

        for (JsonNode track : tracks) {

            TrackDto trackDTO = new TrackDto();
            trackDTO.setId(track.path("id").asText());
            trackDTO.setName(track.path("name").asText());
            trackDTO.setDurationMs(track.path("duration_ms").asInt());
            trackDTO.setExplicit(track.path("explicit").asBoolean());
    
            List<ArtistDto> artistDTOs = new ArrayList<>();
            for (JsonNode artist : track.path("artists")) {
                ArtistDto artistDTO = new ArtistDto();
                artistDTO.setName(artist.path("name").asText());
                artistDTOs.add(artistDTO);
            }
            trackDTO.setArtists(artistDTOs);
    
            JsonNode image = track.path("album").path("images").get(0);
            if (image != null) {
                trackDTO.setImageUrl(image.path("url").asText());
            }
    
            trackDTOs.add(trackDTO);
        }
    
        return trackDTOs;
    }

    private String getAccessToken() {
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(spotifyClientId, spotifyClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, Object>>() {});

        if (response.getBody() != null) {
            return response.getBody().get("access_token").toString();
        } else {
            // Manejar el caso en el que el cuerpo de la respuesta es nulo
            return null;
        }
    }
}