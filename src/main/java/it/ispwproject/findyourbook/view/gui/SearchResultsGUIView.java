package it.ispwproject.findyourbook.view.gui;

import it.ispwproject.findyourbook.bean.BookBean;
import it.ispwproject.findyourbook.controller.applicativo.UserLibraryController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.Label;

public class SearchResultsGUIView extends DashboardGUIView {

    // Abbiamo aggiunto il 5° parametro: Runnable onMyBooksClick
    // Aggiungi ", java.util.function.Consumer<BookBean> onBookClick" alla fine delle parentesi!
    public VBox buildRoot(List<BookBean> books, String lastQuery, Runnable onBack, java.util.function.Consumer<String> onSearch, Runnable onLogout, Runnable onMyBooksClick, java.util.function.Consumer<BookBean> onBookClick) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20, 50, 20, 50));
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        HBox navbar = super.buildNavbar(onMyBooksClick, onLogout, onSearch);

        // --- MAGIA: Scriviamo la ricerca precedente nella barra! ---
        if (lastQuery != null) {
            javafx.scene.control.TextField searchBar = (javafx.scene.control.TextField) navbar.getChildren().get(3);
            searchBar.setText(lastQuery);
        }
        // 2. Togliamo l'evidenziatura dalla "Home" e la rendiamo cliccabile per tornare indietro!
        Label homeLabel = (Label) navbar.getChildren().get(1);
        homeLabel.getStyleClass().clear();
        homeLabel.getStyleClass().add("nav-link");
        homeLabel.setStyle("-fx-cursor: hand;");
        homeLabel.setOnMouseClicked(e -> onBack.run());

        // 3. Facciamo sembrare "Torna alla Home" un vero bottone cliccabile
        // Pulsante Indietro con effetto Sottolineatura al passaggio del mouse
        Button backBtn = new Button("< Indietro");

        // Definiamo lo stile normale (Trasparente, solo testo scuro)
        String stileBase = "-fx-background-color: transparent; -fx-padding: 8 15; -fx-cursor: hand; -fx-text-fill: #4A3F35; -fx-font-weight: bold; -fx-font-size: 14px;";
        // Definiamo lo stile quando ci passi sopra (Aggiunge la sottolineatura)
        String stileHover = stileBase + " -fx-underline: true;";

        backBtn.setStyle(stileBase);

        // Creiamo l'animazione / effetto visivo
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(stileHover)); // Mouse entra -> Sottolineato
        backBtn.setOnMouseExited(e -> backBtn.setStyle(stileBase));   // Mouse esce -> Normale

        backBtn.setOnAction(e -> onBack.run());

        TilePane grid = new TilePane();
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPrefColumns(2);

        for (BookBean book : books) {
            VBox card = super.buildBookCard(
                    null,                                      // 1. Titolo sezione
                    book,                                      // 2. L'oggetto book intero!
                    book.getStatus(),                          // 3. Stato
                    newStatus -> {                             // 4. Azione Tendina
                        new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController().saveBookToLibrary(book, newStatus);
                        book.setStatus(newStatus);
                    },
                    rating -> {                                // 5. Azione Stelline
                        new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController().rateBook(book, rating);
                        book.setRating(rating);
                    },
                    () -> onBookClick.accept(book)             // 6. Click sulla card
            );
            grid.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(navbar, backBtn, scroll);
        return root;
    }

}