package it.ispwproject.findyourbook.dao.memory;

import it.ispwproject.findyourbook.bean.BookBean;
import it.ispwproject.findyourbook.dao.FavoritesDAO;
import it.ispwproject.findyourbook.exception.DAOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesDAOMemory implements FavoritesDAO {

    // 1. LA VERA CASSAFORTE: Salviamo stringhe e numeri, NON l'oggetto BookBean intero!
    private static class MemoryRecord {
        String username;
        String title;
        String author;
        String genre;
        String imageUrl;
        String status;
        int rating; // Salviamo le stelline qui dentro al sicuro

        MemoryRecord(String u, BookBean b, String s) {
            this.username = u;
            this.title = b.getTitle();
            this.author = b.getAuthor();
            this.genre = b.getGenre();
            this.imageUrl = b.getImageUrl();
            this.status = s;
            this.rating = b.getRating();
        }
    }

    private static final List<MemoryRecord> DB_FINTO = new ArrayList<>();

    @Override
    public void addLibroPreferito(String username, BookBean book, String statoLettura) throws DAOException {
        System.out.println("💾 [MEMORY DAO] Richiesta salvataggio per: '" + book.getTitle() + "' in stato: " + statoLettura);

        for (MemoryRecord record : DB_FINTO) {
            // Usiamo equalsIgnoreCase e trim per ignorare spazi e maiuscole/minuscole
            if (record.username.equals(username) && record.title.trim().equalsIgnoreCase(book.getTitle().trim())) {
                record.status = statoLettura;
                System.out.println("   -> Libro già presente. Aggiornato stato a: " + statoLettura);
                return;
            }
        }
        DB_FINTO.add(new MemoryRecord(username, book, statoLettura));
        System.out.println("   -> Libro NUOVO aggiunto alla cassaforte!");
    }

    @Override
    public void removeLibroPreferito(String username, String titoloLibro) throws DAOException {
        DB_FINTO.removeIf(record -> record.username.equals(username) && record.title.trim().equalsIgnoreCase(titoloLibro.trim()));
    }

    @Override
    public void updateValutazione(String username, String titoloLibro, int rating) throws DAOException {
        System.out.println("💾 [MEMORY DAO] Aggiorno stelline per '" + titoloLibro + "' a " + rating);

        for (MemoryRecord record : DB_FINTO) {
            if (record.username.equals(username) && record.title.trim().equalsIgnoreCase(titoloLibro.trim())) {
                record.rating = rating;
                System.out.println("   -> ✅ Voto bloccato in cassaforte con successo!");
                return;
            }
        }
        System.out.println("   -> ⚠️ ATTENZIONE: Libro non trovato in cassaforte! (Non dovrebbe succedere)");
    }

    @Override
    public List<BookBean> getLibriByStato(String username, String statoLettura) throws DAOException {
        List<BookBean> risultati = new ArrayList<>();

        for (MemoryRecord record : DB_FINTO) {
            if (record.username.equals(username) && record.status.equals(statoLettura)) {
                // Ricreiamo un BookBean NUOVO E PULITO attingendo dai dati blindati
                BookBean b = new BookBean(record.title, record.author, record.genre, record.imageUrl);
                b.setRating(record.rating);
                b.setStatus(record.status);
                risultati.add(b);
            }
        }
        return risultati;
    }
}