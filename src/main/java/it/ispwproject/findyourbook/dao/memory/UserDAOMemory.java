package it.ispwproject.findyourbook.dao.memory;

import it.ispwproject.findyourbook.demo.DemoDataStore;
import it.ispwproject.findyourbook.dao.UserDAO;
import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.User;

public class UserDAOMemory implements UserDAO {

    private final DemoDataStore store = DemoDataStore.getInstance();

    @Override
    public User findByUsername(String username) throws DAOException {
        return store.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new DAOException("Utente non trovato: " + username));
    }
}