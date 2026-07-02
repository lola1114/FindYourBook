package it.ispwproject.findyourbook.demo;

import it.ispwproject.findyourbook.model.User;
import java.util.ArrayList;
import java.util.List;

public class DemoDataStore {
    private static DemoDataStore instance = null;
    private List<User> users;

    // AGGIUNGI QUESTA VARIABILE
    private int nextUserId = 10;

    private DemoDataStore() {
        users = new ArrayList<>();
        // ... eventuale inizializzazione utenti ...
    }

    public static DemoDataStore getInstance() {
        if (instance == null) {
            instance = new DemoDataStore();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    // AGGIUNGI QUESTO METODO
    public int nextUserId() {
        return nextUserId++;
    }
}