package it.ispwproject.findyourbook.model;

import it.ispwproject.findyourbook.enumerator.Role;
import java.time.LocalDate; // MANCAVA QUESTA RIGA

// Per ora lasciamo la predisposizione per i Libri Preferiti, potrai scommentarla quando creeremo la classe Libro!
public class Reader extends User {

    // private List<Libro> libriPreferiti;

    public Reader() {
        super();
        // this.libriPreferiti = new ArrayList<>();
    }

    public Reader(int id, String name, String surname, String username, String password, LocalDate dataNascita) {
        super(id, name, surname, username, password, dataNascita, Role.LETTORE);
        // this.libriPreferiti = new ArrayList<>();
    }

    // Qui in futuro metteremo i metodi addPreferito(), removePreferito(), ecc. esattamente come il collega
}