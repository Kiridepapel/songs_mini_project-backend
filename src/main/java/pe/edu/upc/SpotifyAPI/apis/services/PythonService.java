package pe.edu.upc.SpotifyAPI.apis.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jep.SharedInterpreter;
import jep.JepException;

@Service
public class PythonService {

    @Value("$genius.client.access.token")
    private String geniusClientAccessToken;

    private final ThreadLocal<SharedInterpreter> jep1 = new ThreadLocal<>();

    private void generateInterpreter() {
        if (jep1.get() == null) {
            jep1.set(new SharedInterpreter());
            String searchPath = "src/main/java/pe/edu/upc/SpotifyAPI/apis/python/songs.py";
            jep1.get().runScript(searchPath);
        }
    }

    public String getYoutubeLink(String songName, String artistName) throws JepException {
        this.generateInterpreter();
        String search = songName + " " + artistName;
        Object result = jep1.get().invoke("get_youtube_link", search);
        System.out.println(search + " (" + result.toString() + ")");
        return result.toString();
    }

    public String getLyrics(String songName, String artistName) throws JepException {
        this.generateInterpreter();
        Object result = jep1.get().invoke("search_lyrics", this.geniusClientAccessToken, songName, artistName);
        return result.toString();
    }
}