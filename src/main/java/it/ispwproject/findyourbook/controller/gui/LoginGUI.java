package it.ispwproject.findyourbook.controller.gui;

import it.ispwproject.findyourbook.controller.applicativo.LoginController;
import it.ispwproject.findyourbook.controller.applicativo.LoginController.LoginResult;
import it.ispwproject.findyourbook.view.gui.LoginGUIView;
import it.ispwproject.findyourbook.exception.DAOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginGUI {

    private final Stage stage;
    private final LoginController loginController = new LoginController();
    private final LoginGUIView view  = new LoginGUIView();

    public LoginGUI(Stage stage) { this.stage = stage; }

    public void show() {
        // Creiamo il layout
        javafx.scene.layout.VBox root = view.buildRoot(this::handleLogin, () -> new RegistrationGUI(stage).show());

        // LA MAGIA È QUI: Invece di fare 'new Scene', usiamo il nostro GUIUtils che contiene il CSS!
        Scene scene = it.ispwproject.findyourbook.controller.gui.GUIUtils.createScene(root);

        stage.setScene(scene);
        stage.show();
    }

    private void handleLogin() {
        // CORREZIONE: Leggiamo l'username, non l'email!
        String username = view.usernameField.getText().trim();
        String password = view.passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            view.setError("Inserisci username e password.");
            return;
        }

        try {
            LoginResult result = loginController.login(username, password);
            switch (result) {
                case SUCCESSO_LETTORE -> MainGUI.showReaderDashboard();
                case SUCCESSO_CASA_EDITRICE -> MainGUI.showPublisherDashboard();
            }
        } catch (DAOException e) { // <-- Risolto il code smell dell'eccezione generica!
            view.setError("Login fallito: " + e.getMessage());
        }
    }
}