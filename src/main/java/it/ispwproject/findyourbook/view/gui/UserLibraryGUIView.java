package it.ispwproject.findyourbook.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import java.util.function.Consumer;

public class UserLibraryGUIView extends DashboardGUIView {

    private FlowPane booksGrid; // La griglia dinamica dove metteremo le card

    public VBox buildRoot(Runnable onHomeClick, Runnable onLogout, Consumer<String> onSearch, Consumer<String> onFilterClick) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(20, 50, 40, 50));
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        // 1. NAVBAR (Passiamo onHomeClick invece di onMyBooksClick)
        HBox navbar = super.buildNavbar(null, onLogout, onSearch);

        // Modifichiamo l'aspetto della navbar per far capire che siamo in "I miei libri"
        // (Nota: Per farlo perfetto bisognerebbe invertire le classi CSS nella classe padre, ma per ora lo facciamo al volo qui)
        Label homeLabel = (Label) navbar.getChildren().get(1);
        homeLabel.getStyleClass().clear();
        homeLabel.getStyleClass().add("nav-link"); // Home diventa link normale
        homeLabel.setOnMouseClicked(e -> onHomeClick.run());

        Label myBooksLabel = (Label) navbar.getChildren().get(2);
        myBooksLabel.getStyleClass().clear();
        myBooksLabel.getStyleClass().add("nav-link-active"); // MyBooks diventa attivo

        // Separatore
        Region sep = new Region();
        sep.setMinHeight(2);
        sep.setStyle("-fx-background-color: #FFFFFF;");

        // 2. HEADER: PROFILO UTENTE (Stile Mockup Editoriale)
        VBox headerBox = new VBox(35); // Più spazio tra il profilo e i bottoni
        headerBox.setAlignment(Pos.CENTER);

        // --- Sezione superiore: Avatar e Statistiche ---
        HBox profileBox = new HBox(40);
        profileBox.setAlignment(Pos.CENTER); // Centriamo tutto orizzontalmente

        // Avatar (cerchio con omino)
        Label avatarIcon = new Label("👤");
        avatarIcon.setStyle("-fx-font-size: 60px; -fx-text-fill: " + TEXT_DARK + "; -fx-border-color: " + TEXT_DARK + "; -fx-border-width: 3; -fx-border-radius: 100; -fx-padding: 15 25;");

        // Info a destra dell'Avatar
        VBox profileInfo = new VBox(15);
        profileInfo.setAlignment(Pos.CENTER_LEFT);

        // Nome (Potresti prendere il nome reale qui, ma lasciamo il segnaposto per ora)
        Label nameLabel = new Label("Il tuo profilo");
        nameLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        // Statistiche con i separatori verticali
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        Label stat1 = new Label("14\nLibri letti");
        stat1.setStyle("-fx-font-family: 'Georgia'; -fx-text-alignment: center; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + TEXT_DARK + ";");

        Label sep1 = new Label("|");
        sep1.setStyle("-fx-font-size: 26px; -fx-text-fill: " + TEXT_DARK + "; -fx-font-weight: lighter;");

        Label stat2 = new Label("Preferiti");
        stat2.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: " + TEXT_DARK + ";");

        Label sep2 = new Label("|");
        sep2.setStyle("-fx-font-size: 26px; -fx-text-fill: " + TEXT_DARK + "; -fx-font-weight: lighter;");

        Label stat3 = new Label("Generi");
        stat3.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: " + TEXT_DARK + ";");

        statsBox.getChildren().addAll(stat1, sep1, stat2, sep2, stat3);
        profileInfo.getChildren().addAll(nameLabel, statsBox);

        profileBox.getChildren().addAll(avatarIcon, profileInfo);

        // --- Sezione inferiore: Bottoni di Filtro ---
        HBox filterBox = new HBox(20);
        filterBox.setAlignment(Pos.CENTER);

        Button btnToRead = createFilterButton("Da leggere", () -> onFilterClick.accept("DA_LEGGERE"));
        Button btnReading = createFilterButton("In lettura", () -> onFilterClick.accept("IN_LETTURA"));
        Button btnRead = createFilterButton("Letti", () -> onFilterClick.accept("LETTO"));

        filterBox.getChildren().addAll(btnToRead, btnReading, btnRead);

        // Assembliamo l'intero Header!
        headerBox.getChildren().addAll(profileBox, filterBox);

        // 3. GRIGLIA DEI LIBRI (FlowPane)
        booksGrid = new FlowPane();
        booksGrid.setHgap(30); // Spazio orizzontale tra i libri
        booksGrid.setVgap(30); // Spazio verticale
        booksGrid.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(booksGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + BG_COLOR + ";");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Togliamo il bordo del fastidioso ScrollPane di default
        scrollPane.getStyleClass().add("transparent-pane");

        root.getChildren().addAll(navbar, sep, headerBox, scrollPane);

        return root;
    }

    // Metodo helper per creare i bottoni dei filtri
    private Button createFilterButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: " + TEXT_DARK + "; -fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-padding: 10 30; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        btn.setOnAction(e -> action.run());

        // Effetto Hover
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + TEXT_DARK + "; -fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-padding: 10 30; -fx-background-radius: 20; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: " + TEXT_DARK + "; -fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-padding: 10 30; -fx-background-radius: 20; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"));
        return btn;
    }

    // Metodo per svuotare e riempire la griglia
    public void populateGrid(java.util.List<javafx.scene.layout.VBox> bookCards) {
        booksGrid.getChildren().clear();
        if (bookCards.isEmpty()) {
            Label emptyLabel = new Label("Nessun libro trovato in questa sezione.");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #7A7A7A; -fx-font-style: italic;");
            booksGrid.getChildren().add(emptyLabel);
        } else {
            booksGrid.getChildren().addAll(bookCards);
        }
    }
}