package it.ispwproject.findyourbook.dao;

import it.ispwproject.findyourbook.dao.db.LoginDAODB;
import it.ispwproject.findyourbook.dao.db.UserDAODB;
import it.ispwproject.findyourbook.dao.memory.LoginDAOMemory;
import it.ispwproject.findyourbook.dao.memory.UserDAOMemory;
import it.ispwproject.findyourbook.dao.memory.RegistrationDAOMemory;

public class DAOFactory {

    public static final String DATABASE = "database";
    public static final String MEMORY   = "memory";

    // Modifica questa riga per cambiare da DB vero a DB finto!
    private static String persistence = MEMORY;

    private DAOFactory() {}

    public static void setPersistence(String mode) {
        if (mode != null && !mode.isBlank()) {
            persistence = mode;
        }
    }

    public static String getPersistence() {
        return persistence;
    }

    public static LoginDAO getLoginDAO() {
        if (MEMORY.equalsIgnoreCase(persistence)) return new LoginDAOMemory();
        return new LoginDAODB();
    }

    public static UserDAO getUserDAO() {
        if (MEMORY.equalsIgnoreCase(persistence)) return new UserDAOMemory();
        return new UserDAODB();
    }

    public static RegistrationDAO getRegistrationDAO() {
        if (MEMORY.equalsIgnoreCase(persistence)) return new RegistrationDAOMemory();

        // Ora restituisce il VERO DAO per il database MySQL!
        return new it.ispwproject.findyourbook.dao.db.RegistrationDAODB();
    }

    public static FavoritesDAO getFavoritesDAO() {
        if (MEMORY.equalsIgnoreCase(persistence)) {
            // Ritorneremo la memoria (lo creiamo al prossimo step!)
            return new it.ispwproject.findyourbook.dao.memory.FavoritesDAOMemory();
        }
        return new it.ispwproject.findyourbook.dao.db.FavoritesDAODB();
    }
}