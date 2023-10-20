// package pe.edu.upc.SpotifyAPI.apis.controllers;
//
// import java.io.IOException;
//
// import lombok.AllArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import pe.edu.upc.SpotifyAPI.apis.dtos.StringResponse;
// import pe.edu.upc.SpotifyAPI.apis.services.GoogleService;
// import pe.edu.upc.SpotifyAPI.apis.services.RapidApiService;
//
// @CrossOrigin(origins = "**")
// @RestController
// @RequestMapping("/api/google")
//@AllArgsConstructor
// public class GoogleController {
//
//     private GoogleService googleService;
//     private RapidApiService rapidApiService;
//
//     @GetMapping("/get-audio-url")
//     public StringResponse getAudioUrlFromYouTube(
//     @RequestParam("songName") String songName,
//     @RequestParam("artistName") String artistName) throws IOException, InterruptedException {
//         String youtubeUrl = googleService.getYTUrlFromSongName(songName, artistName);
//         String audioUrl = rapidApiService.getAudioUrl(youtubeUrl);
//         StringResponse audioResponse = new StringResponse();
//         audioResponse.setResponse(audioUrl);
//         return audioResponse;
//     }
// }