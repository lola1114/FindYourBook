package it.ispwproject.findyourbook.model;

import it.ispwproject.findyourbook.enumerator.Role;
import java.time.LocalDate; // IMPORTANTE

public class Publisher extends User {

    private String descrizione;

    public Publisher() {
        super();
    }

    // AGGIUNTO LocalDate dataNascita nei parametri
    public Publisher(int id, String name, String surname, String username, String password, LocalDate dataNascita, String descrizione) {
        // AGGIUNTO dataNascita nel super() prima del Role
        super(id, name, surname, username, password, dataNascita, Role.CASA_EDITRICE);
        this.descrizione = descrizione;
    }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}