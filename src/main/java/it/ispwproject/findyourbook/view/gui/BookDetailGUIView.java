package it.ispwproject.findyourbook.view.gui;

import it.ispwproject.findyourbook.bean.BookBean;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class BookDetailGUIView extends DashboardGUIView {

    // Aggiunto String currentStatus
    // Aggiunti i parametri onHomeClick e onSearch
    public VBox buildRoot(BookBean book, String currentStatus, Runnable onBack, Runnable onHomeClick, Runnable onMyBooksClick, Runnable onLogout, java.util.function.Consumer<String> onSearch) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20, 50, 20, 50));
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        // 1. Navbar superiore (Ora passiamo onSearch invece di null!)
        HBox navbar = super.buildNavbar(onMyBooksClick, onLogout, onSearch);

        // Disattiviamo l'evidenziatura sulla Home e le diamo l'azione corretta
        Label homeLabel = (Label) navbar.getChildren().get(1);
        homeLabel.getStyleClass().clear();
        homeLabel.getStyleClass().add("nav-link");
        homeLabel.setStyle("-fx-cursor: hand;");
        homeLabel.setOnMouseClicked(e -> onHomeClick.run()); // <-- PRIMA ERA onBack.run(), per questo sbagliava!

        // (Il resto del codice sotto con il backBtn rimane uguale...)

        // Pulsante Indietro
        // NUOVO PULSANTE INDIETRO CONFIGURATO CORRETTAMENTE
        // Pulsante Indietro (Grafica a pillola sicura e testo corretto)
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
        // 2. CONTENITORE PRINCIPALE DIVISO IN DUE COLONNE
        HBox mainContent = new HBox(40); // 40px di spazio tra colonna sinistra e destra
        mainContent.setAlignment(Pos.TOP_LEFT);
        mainContent.setPadding(new Insets(20, 0, 0, 0));

        // --- COLONNA SINISTRA: Copertina, Bottone Stato e Stelline ---
        VBox leftColumn = new VBox(15);
        leftColumn.setAlignment(Pos.TOP_CENTER);
        leftColumn.setPrefWidth(200);

        ImageView coverView = new ImageView();
        coverView.setFitWidth(180);
        coverView.setFitHeight(270);
        coverView.setPreserveRatio(true);
        if (book.getImageUrl() != null && book.getImageUrl().startsWith("http")) {
            coverView.setImage(new Image(book.getImageUrl(), 180, 270, true, true, true));
        }

        // --- 1. BOTTONE STATO (Menu a tendina interattivo) ---
        // Determiniamo il testo iniziale in base allo stato (se lo conosciamo, altrimenti "Aggiungi a...")
        // Determiniamo il testo iniziale
        String statusText = "Aggiungi a...";
        if ("DA_LEGGERE".equals(currentStatus)) statusText = "Da leggere";
        else if ("IN_LETTURA".equals(currentStatus)) statusText = "In lettura";
        else if ("LETTO".equals(currentStatus)) statusText = "Letto";

        MenuButton statusBtn = new MenuButton(statusText); // Usa il testo corretto!
        statusBtn.setStyle("-fx-background-color: #85A38D; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand; -fx-padding: 5 15;");

        MenuItem optWantToRead = new MenuItem("Da leggere");
        MenuItem optReading = new MenuItem("In lettura");
        MenuItem optRead = new MenuItem("Letto");

        // Logica click sulla tendina (Salva nel Database!)
        optWantToRead.setOnAction(e -> {
            statusBtn.setText("Da leggere");
            new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController().saveBookToLibrary(book, "DA_LEGGERE");
        });
        optReading.setOnAction(e -> {
            statusBtn.setText("In lettura");
            new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController().saveBookToLibrary(book, "IN_LETTURA");
        });
        optRead.setOnAction(e -> {
            statusBtn.setText("Letto");
            new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController().saveBookToLibrary(book, "LETTO");
        });

        statusBtn.getItems().addAll(optWantToRead, optReading, optRead);

        // --- 2. SISTEMA DI VALUTAZIONE (Stelline con memoria) ---
        HBox ratingBox = new HBox(5);
        ratingBox.setAlignment(Pos.CENTER);

        Label valutaTesto = new Label("Valuta: ");
        valutaTesto.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14px; -fx-text-fill: " + TEXT_DARK + ";");
        ratingBox.getChildren().add(valutaTesto);

        Label[] stars = new Label[5];
        final int[] clickedRating = {book.getRating()}; // Legge il voto del libro dal Bean!

        for (int i = 0; i < 5; i++) {
            final int starValue = i + 1;
            stars[i] = new Label(starValue <= clickedRating[0] ? "★" : "☆");
            stars[i].setStyle("-fx-font-size: 22px; -fx-text-fill: #E6B800; -fx-cursor: hand;");

            // Animazione hover
            stars[i].setOnMouseEntered(e -> {
                for (int j = 0; j < 5; j++) stars[j].setText(j < starValue ? "★" : "☆");
            });
            stars[i].setOnMouseExited(e -> {
                for (int j = 0; j < 5; j++) stars[j].setText(j < clickedRating[0] ? "★" : "☆");
            });

            // Click: Salva nel database!
            stars[i].setOnMouseClicked(e -> {
                clickedRating[0] = starValue;
                for (int j = 0; j < 5; j++) stars[j].setText(j < clickedRating[0] ? "★" : "☆");

                it.ispwproject.findyourbook.controller.applicativo.UserLibraryController appController =
                        new it.ispwproject.findyourbook.controller.applicativo.UserLibraryController();
                appController.rateBook(book, starValue);
                book.setRating(starValue); // Aggiorna in memoria
            });

            ratingBox.getChildren().add(stars[i]);
        }

        // --- 3. CONTENITORE AZIONI ---
        VBox actionBox = new VBox(20, statusBtn, ratingBox);
        actionBox.setAlignment(Pos.CENTER);
        VBox.setMargin(actionBox, new Insets(20, 0, 0, 0));

        leftColumn.getChildren().addAll(coverView, actionBox);


        // --- COLONNA DESTRA: Titolo, Autore, Descrizione ---
        // --- COLONNA DESTRA: Titolo, Autore, Descrizione ---
        VBox rightColumn = new VBox(15);
        rightColumn.setAlignment(Pos.TOP_LEFT);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");
        titleLabel.setWrapText(true);

        Label authorLabel = new Label("di " + book.getAuthor());
        authorLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 18px; -fx-text-fill: #7A7A7A; -fx-font-style: italic;");

        // 1. Definiamo il testo della trama
        // 1. Definiamo il testo della trama
        Label descriptionLabel = new Label(book.getDescription());
        descriptionLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14px; -fx-text-fill: " + TEXT_DARK + "; -fx-line-spacing: 5px;");
        descriptionLabel.setWrapText(true);

        // --- NOVITÀ: Aggiungiamo uno ScrollPane per non tagliare il testo! ---
        javafx.scene.control.ScrollPane scrollDesc = new javafx.scene.control.ScrollPane(descriptionLabel);
        scrollDesc.setFitToWidth(true);
        scrollDesc.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollDesc.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollDesc.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        // Impostiamo un'altezza massima per la cartella, così se il testo è più lungo, appare la barra!
        scrollDesc.setPrefHeight(350);

        // (Opzionale) Rimuove i bordi brutti di default dello scrollpane
        scrollDesc.getStyleClass().add("transparent-pane");

        // 2. CREIAMO LA "CARTELLA" PANNA PER LA TRAMA (ora contiene lo ScrollPane!)
        VBox descriptionCard = new VBox(scrollDesc);
        descriptionCard.setStyle("-fx-background-color: #FDFBF7; -fx-background-radius: 20; -fx-padding: 25; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 4);");
        descriptionCard.setMaxWidth(600);
        VBox.setMargin(descriptionCard, new Insets(15, 0, 0, 0));
        // 3. Assembliamo la colonna destra (INSERIAMO descriptionCard, non descriptionLabel!)
        rightColumn.getChildren().addAll(titleLabel, authorLabel, descriptionCard);
        // Assembliamo le due colonne
        mainContent.getChildren().addAll(leftColumn, rightColumn);

        // Aggiungiamo tutto alla radice
        root.getChildren().addAll(navbar, backBtn, mainContent);
        return root;
    }
}