package it.ispwproject.findyourbook.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.ispwproject.findyourbook.bean.BookBean;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryService {

    private static final String API_BASE_URL = "https://openlibrary.org/subjects/";
    // Risolto code smell: Costante per evitare la duplicazione della stringa
    private static final String COVER_ID_KEY = "cover_id";

    // Risolto code smell: Sostituito throws Exception con throws IOException
    public List<BookBean> getBooksBySubject(String subjectKey) throws IOException {
        List<BookBean> bookList = new ArrayList<>();

        // Costruiamo l'URL. Chiediamo 20 risultati per avere un buon bilanciamento e velocità
        String urlString = API_BASE_URL + subjectKey.toLowerCase() + ".json?limit=20";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            // Risolto code smell: Sostituito RuntimeException con IOException
            throw new IOException("Errore Open Library: HTTP " + connection.getResponseCode());
        }

        // Usiamo il try-with-resources per assicurarci che il reader venga chiuso
        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
            JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();

            // Open Library restituisce i libri dentro un array chiamato "works"
            if (jsonResponse.has("works")) {
                JsonArray worksArray = jsonResponse.getAsJsonArray("works");

                for (JsonElement element : worksArray) {
                    JsonObject work = element.getAsJsonObject();
                    // Risolto code smell di Complessità Cognitiva estraendo il metodo
                    bookList.add(parseWorkItem(work, subjectKey));
                }
            }
        } finally {
            connection.disconnect();
        }

        return bookList;
    }

    // --- METODO ESTRATTO PER RIDURRE LA COMPLESSITÀ ---
    private BookBean parseWorkItem(JsonObject work, String subjectKey) {
        // 1. Titolo
        String title = work.has("title") ? work.get("title").getAsString() : "Titolo Sconosciuto";

        // 2. Autore
        String author = "Autore Sconosciuto";
        if (work.has("authors")) {
            JsonArray authorsArray = work.getAsJsonArray("authors");
            if (!authorsArray.isEmpty()) {
                JsonObject authorObj = authorsArray.get(0).getAsJsonObject();
                author = authorObj.has("name") ? authorObj.get("name").getAsString() : "Autore Sconosciuto";
            }
        }

        // 3. Copertina
        String imageUrl = null;
        // Usiamo la costante appena creata
        if (work.has(COVER_ID_KEY) && !work.get(COVER_ID_KEY).isJsonNull()) {
            long coverId = work.get(COVER_ID_KEY).getAsLong();
            imageUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
        }

        return new BookBean(title, author, subjectKey, imageUrl);
    }
}