package it.ispwproject.findyourbook.controller.gui;

import it.ispwproject.findyourbook.model.Publisher;
import it.ispwproject.findyourbook.model.User;
import it.ispwproject.findyourbook.pattern.singleton.SessionManager;
import it.ispwproject.findyourbook.view.gui.PublisherDashboardGUIView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PublisherDashboardGUI {

    private final Stage stage;
    private final PublisherDashboardGUIView view = new PublisherDashboardGUIView();

    public PublisherDashboardGUI(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // Recuperiamo l'utente loggato dal SessionManager castandolo a CasaEditrice
        User loggedUser = SessionManager.getInstance().getLoggedUser();

        String companyName = loggedUser.getName();
        String descrizione = "Nessuna descrizione inserita.";

        if (loggedUser instanceof Publisher publisher) {
            descrizione = publisher.getDescrizione();
        }

        // Costruiamo la radice grafica passando i riferimenti alle funzioni (Runnable)
        Scene scene = new Scene(view.buildRoot(
                companyName,
                descrizione,
                this::handleLogout,
                this::handlePublishNewBook,
                this::handleViewStats
        ));

        // Carichiamo i libri pubblicati da questo editore specifico
        loadPublisherCatalog();

        stage.setScene(scene);
        stage.show();
    }

    private void handleLogout() {
        System.out.println("🚪 Logout Casa Editrice ed eliminazione sessione...");
        SessionManager.getInstance().clearSession();
        MainGUI.showLogin();
    }

    private void handlePublishNewBook() {
        System.out.println("Apertura form di pubblicazione nuovo libro...");
        // In futuro: new PubblicaLibroGUI(stage).show();
    }

    private void handleViewStats() {
        System.out.println("Apertura pannello statistiche vendite corporate...");
    }

    /**
     * Popola temporaneamente la griglia con i libri di proprietà di questo editore.
     */
    private void loadPublisherCatalog() {
        view.addCatalogCard("Il Nome della Rosa", "Umberto Eco", "978-8806221379", 1450);
        view.addCatalogCard("Il Pendolo di Foucault", "Umberto Eco", "978-8845292538", 620);
        view.addCatalogCard("Baudolino", "Umberto Eco", "978-8845277122", 340);
    }
}