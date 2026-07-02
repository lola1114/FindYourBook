package it.ispwproject.findyourbook.dao;

import it.ispwproject.findyourbook.bean.BookBean;
import it.ispwproject.findyourbook.exception.DAOException;
import java.util.List;

public interface FavoritesDAO {
    // Passiamo lo username e direttamente l'oggetto libro!
    void addLibroPreferito(String username, BookBean book, String statoLettura) throws DAOException;

    void removeLibroPreferito(String username, String titoloLibro) throws DAOException;

    List<BookBean> getLibriByStato(String username, String statoLettura) throws DAOException;

    void updateValutazione(String username, String titoloLibro, int rating) throws DAOException;
}