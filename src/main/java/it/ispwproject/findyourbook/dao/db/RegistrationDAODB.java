package it.ispwproject.findyourbook.dao.db;

import it.ispwproject.findyourbook.dao.ConnectionFactory;
import it.ispwproject.findyourbook.dao.RegistrationDAO;
import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.Publisher;
import it.ispwproject.findyourbook.model.Reader;
import it.ispwproject.findyourbook.model.User;



import java.sql.*;

public class RegistrationDAODB implements RegistrationDAO {

    // ⚠️ ATTENZIONE: Assicurati che nel tuo database MySQL la tabella si chiami "utenti".
    // Se si chiama "user" o "users", cambialo in queste due stringhe!
    private static final String CHECK_USERNAME =
            "SELECT COUNT(*) FROM utenti WHERE username = ?";

    private static final String INSERT_USER =
            "INSERT INTO utenti (nome, cognome, username, password, ruolo, data_nascita, descrizione) VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public boolean usernameExists(String username) throws DAOException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(CHECK_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            // CAMBIATO QUI SOTTO DA SQLException a Exception
        } catch (Exception e) {
            throw new DAOException("Errore verifica username: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void save(User user) throws DAOException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            if (user instanceof Publisher) {
                Publisher pub = (Publisher) user;
                ps.setString(5, "CASA_EDITRICE");
                ps.setDate(6, java.sql.Date.valueOf(pub.getDataNascita()));
                ps.setString(7, pub.getDescrizione());

            } else if (user instanceof Reader) {
                Reader reader = (Reader) user;
                ps.setString(5, "LETTORE");
                ps.setDate(6, java.sql.Date.valueOf(reader.getDataNascita()));
                ps.setNull(7, Types.VARCHAR);
            }

            ps.executeUpdate();

            // CAMBIATO ANCHE QUI DA SQLException a Exception
        } catch (Exception e) {
            throw new DAOException("Errore durante la registrazione nel DB: " + e.getMessage());
        }
    }
}