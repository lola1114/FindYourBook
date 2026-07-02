package it.ispwproject.findyourbook.dao.db;

// import it.ispwproject.findyourbook.dao.ConnectionFactory; <-- Lo useremo quando configureremo il DB vero!
import it.ispwproject.findyourbook.dao.UserDAO;
import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.User;

public class UserDAODB implements UserDAO {

    @Override
    public User findByUsername(String username) throws DAOException {
        // PER ORA: Come per il Login, in attesa di MySQL.
        throw new DAOException("Connessione al database reale non ancora configurata!");
    }
}