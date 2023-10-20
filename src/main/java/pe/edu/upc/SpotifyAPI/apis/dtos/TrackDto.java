package pe.edu.upc.SpotifyAPI.apis.dtos;

import java.util.List;

import lombok.Data;

@Data
public class TrackDto {
    private String id;
    private String name;
    private int durationMs;
    private boolean explicit;
    private List<ArtistDto> artists;
    private String imageUrl;
}
