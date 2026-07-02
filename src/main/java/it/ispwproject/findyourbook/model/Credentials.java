package it.ispwproject.findyourbook.model;

import it.ispwproject.findyourbook.enumerator.Role;

/**
 * Credentials — oggetto TEMPORANEO usato solo durante il login.
 * NON è una entity persistente — non ha id perché non viene salvata nel DB.
 */
public class Credentials {

    private String username;
    private String password; // Qui andrà la password già cifrata
    private Role role;

    public Credentials(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole()       { return role; }
}