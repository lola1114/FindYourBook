package it.ispwproject.findyourbook.controller.applicativo;

import it.ispwproject.findyourbook.bean.SessionBean;
import it.ispwproject.findyourbook.dao.DAOFactory;
import it.ispwproject.findyourbook.model.Credentials;
import it.ispwproject.findyourbook.model.User;
import it.ispwproject.findyourbook.pattern.singleton.SessionManager;

public class LoginController {

    public enum LoginResult {
        SUCCESSO_LETTORE,
        SUCCESSO_CASA_EDITRICE
    }

    public LoginResult login(String username, String password) throws Exception {

        // 1. Il LoginDAO controlla se username e password corrispondono
        Credentials credentials = DAOFactory.getLoginDAO().execute(username, password);

        // 2. Lo UserDAO recupera tutti i dati completi dell'utente appena loggato
        User user = DAOFactory.getUserDAO().findByUsername(username);

        // 3. Salviamo l'utente e il bean alleggerito nel SessionManager
        SessionManager.getInstance().setLoggedUser(user);
        SessionManager.getInstance().setSessionBean(
                new SessionBean(user.getUsername(), credentials.getRole())
        );

        // 4. Gestiamo l'instradamento in base al ruolo
        return switch (credentials.getRole()) {
            case LETTORE -> LoginResult.SUCCESSO_LETTORE;
            case CASA_EDITRICE -> LoginResult.SUCCESSO_CASA_EDITRICE;
            default -> throw new Exception("Ruolo utente non riconosciuto.");
        };
    }
}