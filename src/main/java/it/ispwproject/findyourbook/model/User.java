package it.ispwproject.findyourbook.model;

import it.ispwproject.findyourbook.enumerator.Role;
import java.time.LocalDate;

public abstract class User {

    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Role role;

    protected LocalDate dataNascita;

    protected User() {}

    protected User(int id, String name, String surname, String username, String password, LocalDate dataNascita, Role role) {
        this.id       = id;
        this.name     = name;
        this.surname  = surname;
        this.username = username;
        this.password = password;
        this.dataNascita = dataNascita;
        this.role     = role;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public boolean hasRole(Role role) {
        return this.role == role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}