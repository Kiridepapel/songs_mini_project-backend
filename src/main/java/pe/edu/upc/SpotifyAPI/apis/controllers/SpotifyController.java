package pe.edu.upc.SpotifyAPI.apis.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pe.edu.upc.SpotifyAPI.apis.dtos.TrackDto;
import pe.edu.upc.SpotifyAPI.apis.services.SpotifyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/spotify")
@AllArgsConstructor
public class SpotifyController {

    private SpotifyService spotifyService;

    @GetMapping("/search")
    public List<TrackDto> searchTracks(@RequestParam String query) throws JsonMappingException, JsonProcessingException {
        return spotifyService.searchTracks(query);
    }
}
