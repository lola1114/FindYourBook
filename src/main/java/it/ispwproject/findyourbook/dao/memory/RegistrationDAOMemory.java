package it.ispwproject.findyourbook.dao.memory;

import it.ispwproject.findyourbook.dao.RegistrationDAO;
import it.ispwproject.findyourbook.demo.DemoDataStore;
import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.User;

public class RegistrationDAOMemory implements RegistrationDAO {

    @Override
    public boolean usernameExists(String username) throws DAOException {
        return DemoDataStore.getInstance().getUsers().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public void save(User user) throws DAOException {
        // Deleghiamo la generazione dell'ID al database finto (come fa il collega)
        user.setId(DemoDataStore.getInstance().nextUserId());
        DemoDataStore.getInstance().getUsers().add(user);
    }
}