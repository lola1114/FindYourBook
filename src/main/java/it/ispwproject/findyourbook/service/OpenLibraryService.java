package it.ispwproject.findyourbook.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.ispwproject.findyourbook.bean.BookBean;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryService {

    // Endpoint specifico di Open Library per cercare per "Soggetto" (Genere)
    private static final String API_BASE_URL = "https://openlibrary.org/subjects/";

    public List<BookBean> getBooksBySubject(String subjectKey) throws Exception {
        List<BookBean> bookList = new ArrayList<>();

        // Costruiamo l'URL. Chiediamo 20 risultati per avere un buon bilanciamento e velocità
        String urlString = API_BASE_URL + subjectKey.toLowerCase() + ".json?limit=20";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Errore Open Library: HTTP " + connection.getResponseCode());
        }

        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();

        // Open Library restituisce i libri dentro un array chiamato "works"
        if (jsonResponse.has("works")) {
            JsonArray worksArray = jsonResponse.getAsJsonArray("works");

            for (JsonElement element : worksArray) {
                JsonObject work = element.getAsJsonObject();

                // 1. Titolo
                String title = work.has("title") ? work.get("title").getAsString() : "Titolo Sconosciuto";

                // 2. Autore (Open Library ha un array di autori per ogni libro)
                String author = "Autore Sconosciuto";
                if (work.has("authors")) {
                    JsonArray authorsArray = work.getAsJsonArray("authors");
                    if (!authorsArray.isEmpty()) {
                        JsonObject authorObj = authorsArray.get(0).getAsJsonObject();
                        author = authorObj.has("name") ? authorObj.get("name").getAsString() : "Autore Sconosciuto";
                    }
                }

                // 3. Copertina (Open Library usa un ID numerico per l'endpoint delle copertine)
                String imageUrl = null;
                if (work.has("cover_id") && !work.get("cover_id").isJsonNull()) {
                    long coverId = work.get("cover_id").getAsLong();
                    // Generiamo l'URL diretto per la copertina in formato Medium (M) o Large (L)
                    imageUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
                }

                bookList.add(new BookBean(title, author, subjectKey, imageUrl));
            }
        }

        reader.close();
        connection.disconnect();

        return bookList;
    }
}