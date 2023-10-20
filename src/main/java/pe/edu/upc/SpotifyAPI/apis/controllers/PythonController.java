package pe.edu.upc.SpotifyAPI.apis.controllers;

import java.io.IOException;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.upc.SpotifyAPI.apis.dtos.StringResponse;
import pe.edu.upc.SpotifyAPI.apis.services.PythonService;
import pe.edu.upc.SpotifyAPI.apis.services.RapidApiService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/python")
@AllArgsConstructor
public class PythonController {

    private PythonService pythonService;
    private RapidApiService rapidApiService;
    
    @GetMapping("/get-audio-url")
    public StringResponse getAudioUrlFromYouTube(
    @RequestParam("songName") String songName,
    @RequestParam("artistName") String artistName) throws IOException, InterruptedException {
        String youtubeUrl = pythonService.getYoutubeLink(songName, artistName);
        String audioUrl = rapidApiService.getAudioUrl(youtubeUrl);
        StringResponse audioUrlResponse = new StringResponse();
        audioUrlResponse.setResponse(audioUrl);
        return audioUrlResponse;
    }

    @GetMapping("/get-lyrics")
    public StringResponse getLyrics(
    @RequestParam("songName") String songName,
    @RequestParam("artistName") String artistName
    ) throws IOException, InterruptedException {
        String lyrics = pythonService.getLyrics(songName, artistName);
        StringResponse lyricsResponse = new StringResponse();
        lyricsResponse.setResponse(lyrics);
        return lyricsResponse;
    }
}