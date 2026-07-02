package it.ispwproject.findyourbook.dao;

import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.Book;
import java.util.List;

public interface BookDAO {
    List<Book> findByGenere(String genere) throws DAOException;
    void save(Book book) throws DAOException;
}