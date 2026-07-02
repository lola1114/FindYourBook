package it.ispwproject.findyourbook.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.ispwproject.findyourbook.bean.BookBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GoogleBooksService {

    private static final String API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    // Assicurati che questo percorso corrisponda esattamente a dove hai messo il file!
    private static final String PROPERTIES_FILE = "src/main/resources/db.properties";
    private static final String API_KEY;

    // Blocco statico per caricare la chiave API all'avvio
    static {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            API_KEY = properties.getProperty("GOOGLE_BOOKS_API_KEY");
            if (API_KEY == null) {
                System.err.println("⚠️ Attenzione: GOOGLE_BOOKS_API_KEY non trovata nel file properties.");
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Impossibile caricare db.properties per Google Books: " + e.getMessage());
        }
    }

    public List<BookBean> searchBooks(String query) throws Exception {
        List<BookBean> bookList = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return bookList;
        }

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // 1. ABBASSATO maxResults a 20 per evitare il timeout di Google
        String urlString = API_BASE_URL + encodedQuery + "&maxResults=20";

        // 2. CONTROLLO CHIAVE: Aggiungiamo la chiave SOLO se esiste davvero!
        if (API_KEY != null && !API_KEY.trim().isEmpty()) {
            urlString += "&key=" + API_KEY;
            System.out.println("🔑 [Google API] Ricerca in corso CON chiave privata attivata.");
        } else {
            System.out.println("⚠️ [Google API] Nessuna chiave trovata, procedo con i limiti gratuiti anonimi.");
        }

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Errore API Google Books: HTTP " + connection.getResponseCode());
        }

        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();

        if (jsonResponse.has("items")) {
            JsonArray items = jsonResponse.getAsJsonArray("items");

            for (JsonElement itemElement : items) {
                JsonObject item = itemElement.getAsJsonObject();
                JsonObject volumeInfo = item.getAsJsonObject("volumeInfo");

                String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Titolo Sconosciuto";

                String author = "Autore Sconosciuto";
                if (volumeInfo.has("authors")) {
                    JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                    if (!authorsArray.isEmpty()) {
                        author = authorsArray.get(0).getAsString();
                    }
                }
                String genre = "Sconosciuto";
                if (volumeInfo.has("categories")) {
                    JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
                    if (!categoriesArray.isEmpty()) {
                        genre = categoriesArray.get(0).getAsString();
                    }
                }

                String description = "Trama non disponibile.";
                if (volumeInfo.has("description")) {
                    description = volumeInfo.get("description").getAsString();
                }

                String imageUrl = null;
                if (volumeInfo.has("imageLinks")) {
                    JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                    if (imageLinks.has("thumbnail")) {
                        imageUrl = imageLinks.get("thumbnail").getAsString()
                                .replace("http:", "https:")
                                .replace("zoom=1", "zoom=0")
                                .replace("&edge=curl", "");
                    }
                }

                bookList.add(new BookBean(title, author, genre, imageUrl, description));
            }
        }

        reader.close();
        connection.disconnect();

        return bookList;
    }
}