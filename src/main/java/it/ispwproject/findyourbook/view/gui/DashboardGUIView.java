package it.ispwproject.findyourbook.view.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.geometry.Insets;
import java.util.function.Consumer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public abstract class DashboardGUIView {

    // ── Costanti di stile comuni ──────────────────────────────────────────────
    public static final String BG_COLOR = "#EBE2D4";
    public static final String TEXT_DARK = "#4A3F35";
    public static final String BTN_GREEN = "#85A38D";

    // ────────────────────────────────────────────────────────────────────────
    // Navbar aggiornata (No Home, My Books cliccabile, Search Bar attiva)
    // ────────────────────────────────────────────────────────────────────────
    // IN DashboardGUIView.java
    public HBox buildNavbar(Runnable onMyBooksClick, Runnable onLogout, Consumer<String> onSearch) {
        HBox navbar = new HBox(40);
        navbar.setAlignment(Pos.CENTER_LEFT);

        Label brand = new Label("FindYourBook");
        brand.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");
        brand.setMinWidth(Region.USE_PREF_SIZE);

        // --- RE-IMPLEMENTAZIONE STILE PRIME VIDEO ---

        // HOME: È la pagina attiva, quindi le diamo la classe con lo sfondo pieno ("pillola")
        Label homeLink = new Label("Home");
        homeLink.getStyleClass().add("nav-link-active");

        // I MIEI LIBRI: Pagina non attiva, le diamo la classe che fa apparire il contorno all'hover
        Label myBooksLink = new Label("I miei libri"); // IN ITALIANO A SCHERMO
        myBooksLink.getStyleClass().add("nav-link");
        if (onMyBooksClick != null) {
            myBooksLink.setOnMouseClicked(e -> onMyBooksClick.run());
        }

        TextField searchBar = new TextField();
        searchBar.setPromptText("Cerca libri..."); // IN ITALIANO A SCHERMO
        searchBar.setPrefWidth(350);
        searchBar.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-padding: 10 20;");
        if (onSearch != null) {
            searchBar.setOnAction(e -> {
                String query = searchBar.getText().trim();
                if (!query.isEmpty()) {
                    onSearch.accept(query);
                }
            });
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button profileBtn = new Button("👤");
        profileBtn.setMinSize(45, 45);
        profileBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 22px; -fx-cursor: hand; -fx-border-color: " + TEXT_DARK + "; -fx-border-radius: 50;");
        profileBtn.setOnAction(e -> onLogout.run());

        // Ricordati di aggiungere homeLink qui!
        navbar.getChildren().addAll(brand, homeLink, myBooksLink, searchBar, spacer, profileBtn);
        return navbar;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Widget Libro (Stile Goodreads - Ottimizzato per Griglie e Liste)
    // ────────────────────────────────────────────────────────────────────────
    public VBox buildBookCard(String sectionTitle, String bookTitle, String author, String imageUrl, String currentStatus, int initialRating, Consumer<String> onStatusChange, Consumer<Integer> onRate, Runnable onClick) {
       VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 3);");
        card.setPrefWidth(320);
        card.setMaxWidth(320);

        if (sectionTitle != null && !sectionTitle.trim().isEmpty()) {
            Label header = new Label(sectionTitle);
            header.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");
            card.getChildren().add(header);
        }

        card.setOnMouseClicked(e -> {
            if (onClick != null) onClick.run();
        });

        HBox content = new HBox(15);
        content.setAlignment(Pos.CENTER_LEFT);

        ImageView coverView = new ImageView();
        coverView.setFitWidth(90);
        coverView.setFitHeight(140);
        coverView.setPreserveRatio(true);

        if (imageUrl != null && imageUrl.startsWith("http")) {
            Image image = new Image(imageUrl, 90, 140, true, true, true);
            coverView.setImage(image);
        } else {
            coverView.setStyle("-fx-background-color: #D3C5B1;");
        }

        VBox infoBox = new VBox(8);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Label titleL = new Label(bookTitle);
        titleL.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");
        titleL.setWrapText(true);
        titleL.setMaxWidth(180);

        Label authorL = new Label("di " + author);
        authorL.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 13px; -fx-text-fill: #7A7A7A; -fx-font-style: italic;");

        // --- MENU A TENDINA (Con opzione Rimuovi) ---
        // --- CALCOLO TESTO INIZIALE DEL BOTTONE ---
        String btnText = "Da leggere"; // Valore di default
        if ("LETTO".equals(currentStatus)) {
            btnText = "Letto";
        } else if ("IN_LETTURA".equals(currentStatus)) {
            btnText = "In lettura";
        }

        MenuButton readBtn = new MenuButton(btnText);
        readBtn.setOnMouseClicked(e -> e.consume());
        readBtn.setStyle("-fx-background-color: " + BTN_GREEN + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;");

        MenuItem optWantToRead = new MenuItem("Da leggere");
        MenuItem optReading = new MenuItem("In lettura");
        MenuItem optRead = new MenuItem("Letto");

        // Aggiungiamo un separatore e il tasto rimuovi in rosso
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem optRemove = new MenuItem("Rimuovi libro");
        optRemove.setStyle("-fx-text-fill: #C0392B;"); // Rosso scuro

        readBtn.getItems().addAll(optWantToRead, optReading, optRead, separator, optRemove);

        // --- LOGICA CLICK TENDINA ---
        optWantToRead.setOnAction(e -> { readBtn.setText("Da leggere"); if(onStatusChange != null) onStatusChange.accept("DA_LEGGERE"); });
        optReading.setOnAction(e -> { readBtn.setText("In lettura"); if(onStatusChange != null) onStatusChange.accept("IN_LETTURA"); });
        optRead.setOnAction(e -> { readBtn.setText("Letto"); if(onStatusChange != null) onStatusChange.accept("LETTO"); });

        // Logica per rimuovere con Popup stile Goodreads
        optRemove.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Rimuovi Libro");
            alert.setHeaderText("Vuoi rimuovere '" + bookTitle + "' dalla tua libreria?");
            alert.setContentText("Questa azione rimuoverà permanentemente il libro, incluse le tue valutazioni.");

            // Personalizziamo i bottoni dell'alert
            ButtonType btnAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(btnAnnulla, btnOk);

            // Aspettiamo la risposta dell'utente
            alert.showAndWait().ifPresent(type -> {
                if (type == btnOk) {
                    if(onStatusChange != null) onStatusChange.accept("RIMUOVI");
                }
            });
        });


        // --- RATING INTERATTIVO A STELLINE (CON MEMORIA) ---
        HBox ratingBox = new HBox(2);
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(ratingBox, new Insets(10, 0, 0, 0));

        Label valutaText = new Label("Valuta: ");
        valutaText.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 13px; -fx-text-fill: " + TEXT_DARK + ";");
        ratingBox.getChildren().add(valutaText);

        Label[] stars = new Label[5];
        final int[] clickedRating = {initialRating}; // <--- LA MEMORIA DELLE STELLINE

        for (int i = 0; i < 5; i++) {
            final int starValue = i + 1;
            // Se il valore della stella è minore o uguale al voto salvato, nasce già PIENA!
            stars[i] = new Label(starValue <= clickedRating[0] ? "★" : "☆");
            stars[i].setStyle("-fx-font-size: 18px; -fx-text-fill: #E6B800; -fx-cursor: hand;");

            // Quando entri col mouse
            stars[i].setOnMouseEntered(e -> {
                for (int j = 0; j < 5; j++) stars[j].setText(j < starValue ? "★" : "☆");
            });

            // Quando esci col mouse (Torna al valore salvato in memoria!)
            stars[i].setOnMouseExited(e -> {
                for (int j = 0; j < 5; j++) stars[j].setText(j < clickedRating[0] ? "★" : "☆");
            });

            // Quando clicchi (Salva in memoria e fissa le stelline)
            // Quando clicchi (Salva in memoria e fissa le stelline)
            stars[i].setOnMouseClicked(e -> {
                e.consume(); // <--- LA MAGIA! Ferma il click qui, non apre il dettaglio!

                clickedRating[0] = starValue;
                for (int j = 0; j < 5; j++) stars[j].setText(j < clickedRating[0] ? "★" : "☆");

                System.out.println("Votato " + starValue + " stelle!");
                if (onRate != null) onRate.accept(starValue);
            });

            ratingBox.getChildren().add(stars[i]);
        }

        infoBox.getChildren().addAll(titleL, authorL, readBtn, ratingBox);
        content.getChildren().addAll(coverView, infoBox);
        card.getChildren().add(content);

        return card;
    }

    // Questo metodo si trova nella classe padre (es. AbstractDashboardView)
    protected VBox buildGenreTile(String filename, String title, Consumer<String> onClick) {
        VBox tile = new VBox(10);
        tile.setAlignment(Pos.CENTER);
        tile.setStyle("-fx-cursor: hand;"); // Cambia il cursore al passaggio del mouse

        // 1. CARICAMENTO IMMAGINE SICURO DA RESOURCES
        ImageView icon = new ImageView();
        try {
            // Cerca l'immagine nella cartella src/main/resources/images/genres/
            String imagePath = "/icons/" + filename;
            InputStream is = getClass().getResourceAsStream(imagePath);

            if (is != null) {
                Image img = new Image(is);
                icon.setImage(img);
            } else {
                System.err.println("Immagine non trovata: " + imagePath);
                // Opzionale: carica un'immagine di default se non trova quella specifica
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento immagine: " + filename);
        }

        // Dimensioni fisse per le icone
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        // 2. ETICHETTA
        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 14px; -fx-text-fill: #3e3831; -fx-font-weight: bold;");

        // 3. AZIONE DEL CLICK
        tile.setOnMouseClicked(e -> {
            if (onClick != null) {
                // Passiamo il nome del genere (es. "fantasy") al controller
                onClick.accept(title.toLowerCase());
            }
        });

        // Effetti hover (opzionali per renderlo carino)
        tile.setOnMouseEntered(e -> icon.setOpacity(0.7));
        tile.setOnMouseExited(e -> icon.setOpacity(1.0));

        tile.getChildren().addAll(icon, lbl);
        return tile;
    }
}