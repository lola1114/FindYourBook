package it.ispwproject.findyourbook.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PublisherDashboardGUIView {

    public final Button logoutBtn = new Button("Logout");
    public final FlowPane catalogGrid = new FlowPane();

    // Palette Colori
    private static final String BG_COLOR = "#EBE2D4";
    private static final String CARD_BG = "#F8F5F0";
    private static final String TEXT_DARK = "#4A3F35";
    private static final String TEXT_MUTED = "#70675C";
    private static final String BTN_DARK = "#4A3F35";
    private static final String ACCENT_GREEN = "#85A38D";

    // --- COSTANTE CSS AGGIUNTA ---
    private static final String STYLE_BG_COLOR = "-fx-background-color: ";

    public BorderPane buildRoot(String companyName, String descrizione, Runnable onLogout, Runnable onAddBook, Runnable onStats) {
        BorderPane root = new BorderPane();
        root.setStyle(STYLE_BG_COLOR + BG_COLOR + ";");

        // ────────────────────────────────────────────────────────────────────────
        // 1. NAVBAR
        // ────────────────────────────────────────────────────────────────────────
        HBox navbar = new HBox(20);
        navbar.setPadding(new Insets(15, 30, 15, 30));
        navbar.setStyle(STYLE_BG_COLOR + BG_COLOR + ";");
        navbar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("FindYourBook");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        Label subtitle = new Label("— Publisher Panel");
        subtitle.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 18px; -fx-text-fill: " + TEXT_MUTED + ";");

        HBox brandBox = new HBox(10, title, subtitle);
        brandBox.setAlignment(Pos.BASELINE_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label welcome = new Label("Editore: " + companyName);
        welcome.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_DARK + "; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: " + TEXT_DARK + "; -fx-border-radius: 15; -fx-padding: 5 15;");
        logoutBtn.setOnAction(e -> onLogout.run());

        navbar.getChildren().addAll(brandBox, spacer, welcome, logoutBtn);
        root.setTop(navbar);

        // ────────────────────────────────────────────────────────────────────────
        // 2. CENTRO
        // ────────────────────────────────────────────────────────────────────────
        VBox centerArea = new VBox(15);
        centerArea.setPadding(new Insets(25));

        Label sectionTitle = new Label("Il Tuo Catalogo Pubblicazioni");
        sectionTitle.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        Label bioLbl = new Label("Info: " + descrizione);
        bioLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-style: italic; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 14px;");

        catalogGrid.setHgap(30);
        catalogGrid.setVgap(30);
        catalogGrid.setPadding(new Insets(15, 0, 0, 0));

        ScrollPane scroll = new ScrollPane(catalogGrid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: " + BG_COLOR + ";");

        centerArea.getChildren().addAll(sectionTitle, bioLbl, scroll);
        root.setCenter(centerArea);

        // ────────────────────────────────────────────────────────────────────────
        // 3. DESTRA
        // ────────────────────────────────────────────────────────────────────────
        VBox rightMenu = new VBox(20);
        rightMenu.setPadding(new Insets(30, 20, 30, 20));
        rightMenu.setPrefWidth(280);
        rightMenu.setStyle(STYLE_BG_COLOR + CARD_BG + "; -fx-border-color: #D3C5B1; -fx-border-width: 0 0 0 1;");

        Label menuTitle = new Label("Gestione Catalogo");
        menuTitle.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        Button addBookBtn = buildMenuButton("➕ Pubblica Nuovo Libro", onAddBook);
        Button statsBtn = buildMenuButton("📊 Report e Vendite", onStats);

        rightMenu.getChildren().addAll(menuTitle, addBookBtn, statsBtn);
        root.setRight(rightMenu);

        return root;
    }

    private Button buildMenuButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(45);
        btn.setStyle(STYLE_BG_COLOR + BTN_DARK + "; -fx-text-fill: white; -fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 0 0 0 15; -fx-cursor: hand; -fx-background-radius: 15;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    public void addCatalogCard(String title, String author, String isbn, int copieVendute) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefSize(200, 260);
        card.setStyle(STYLE_BG_COLOR + CARD_BG + "; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: " + TEXT_DARK + ";");
        titleLbl.setWrapText(true);
        titleLbl.setMaxHeight(45);

        Label authLbl = new Label("di " + author);
        authLbl.setStyle("-fx-font-family: 'Georgia'; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 14px;");

        Label isbnLbl = new Label("ISBN: " + isbn);
        isbnLbl.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        HBox statsRow = new HBox(5);
        statsRow.setAlignment(Pos.CENTER_LEFT);
        Label soldLbl = new Label("📈 Venduti: " + copieVendute + " pz");
        soldLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-background-color: #E2EFE5; -fx-text-fill: " + ACCENT_GREEN + "; -fx-padding: 5 10; -fx-background-radius: 10; -fx-font-weight: bold;");
        statsRow.getChildren().add(soldLbl);

        card.getChildren().addAll(titleLbl, authLbl, isbnLbl, spacer, statsRow);
        catalogGrid.getChildren().add(card);
    }
}