package it.ispwproject.findyourbook.bean;

import it.ispwproject.findyourbook.enumerator.Role;

/**
 * Bean leggero che rappresenta la sessione autenticata.
 *
 * Contiene solo username e role — dati minimi per l'autorizzazione.
 * Per i dati completi dell'utente usa SessionManager.getLoggedUser().
 **/
public class SessionBean {

    private final String username;
    private final Role role;

    public SessionBean(String username, Role role) {
        this.username = username;
        this.role  = role;
    }

    public String getUsername() { return username; }
    public Role getRole()    { return role; }
}