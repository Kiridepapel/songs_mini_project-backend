from youtubesearchpython import VideosSearch
import lyricsgenius

def get_youtube_link(search):
    # Crea una instancia de la clase VideosSearch para buscar vídeos en YouTube
    videosSearch = VideosSearch(search, limit=1)
    # Realiza la búsqueda y obtiene los resultados
    search_results = videosSearch.result()
    # Verifica si la búsqueda ha encontrado algún resultado
    if search_results["result"]:
        # Accede al primer resultado de la búsqueda
        video = search_results["result"][0]
        # Retorna el enlace del vídeo
        return video["link"]
    else:
        return "No se encontraron resultados."
    
def search_lyrics(token: str, song: str, artist: str):
    try:
        # Crea una instancia de la clase Genius con el token de acceso
        genius = lyricsgenius.Genius(token)
        # Busca la canción en Genius utilizando el título y el artista
        found_song = genius.search_song(song, artist)
        # --- MODIFICAR EL TEXTO
        # Eliminar la primera linea (10 Contributors)
        found_song.lyrics = found_song.lyrics.split('\n', 1)[1]
        # Elimina el primer "\n" al comienzo del string si es que existe
        found_song.lyrics = found_song.lyrics.lstrip('\n')
        # Elimina el texto raro del final del string (Embed)
        found_song.lyrics = found_song.lyrics.rstrip('0123456789Embed') + '.'
        # Retorna la letra de la canción encontrada
        return found_song.lyrics
    except Exception as e:
        return ("No se encontró letra para esta canción.")