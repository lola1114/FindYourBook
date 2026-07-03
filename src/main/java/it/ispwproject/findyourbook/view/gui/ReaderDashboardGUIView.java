package it.ispwproject.findyourbook.view.gui;

import it.ispwproject.findyourbook.bean.BookBean;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.function.Consumer;

public class ReaderDashboardGUIView extends DashboardGUIView {

    public VBox buildRoot(Runnable onLogout, Runnable onMyBooksClick, Consumer<String> onSearch, Consumer<String> onGenreClick) {
        VBox root = new VBox(40);
        root.setPadding(new Insets(20, 50, 40, 50));
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        // 1. Usa la Navbar del padre
        HBox navbar = super.buildNavbar(onMyBooksClick, onLogout, onSearch);

        // Separatore
        Region sep = new Region();
        sep.setMinHeight(2);
        sep.setStyle("-fx-background-color: #FFFFFF;");

        // 2. Assembla la schermata usando i moduli
        root.getChildren().addAll(
                navbar,
                sep,
                buildRecommendationsSection(),
                buildGenresSection(onGenreClick)
        );

        try {
            root.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("CSS non trovato sulla Home");
        }

        root.setPadding(new Insets(20, 50, 40, 50));
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        return root;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Sezione Raccomandazioni (Equivalente al suo buildCalendarSection)
    // ────────────────────────────────────────────────────────────────────────
    // Sostituisci il metodo buildRecommendationsSection così:
    private HBox buildRecommendationsSection() {
        HBox section = new HBox(40);
        section.setAlignment(Pos.CENTER);

        // Creiamo BookBean "finti" per il momento
        BookBean book1 = new BookBean("Il cerchio dei giorni", "Ken Follett", null, null, null);
        BookBean book2 = new BookBean("La bugia dell'orchidea", "Donato Carrisi", null, null, null);

        // Ora passiamo l'oggetto book invece di tanti parametri
        VBox card1 = super.buildBookCard("Il nostro consiglio", book1, null, null, null, null);
        VBox card2 = super.buildBookCard("Le novità dalle Case Editrici", book2, null, null, null, null);

        section.getChildren().addAll(card1, card2);
        return section;
    }
    // ────────────────────────────────────────────────────────────────────────
    // Sezione Generi
    // ────────────────────────────────────────────────────────────────────────
    private VBox buildGenresSection(Consumer<String> onGenreClick) {
        VBox section = new VBox(30);

        // PADDING: (Top, Right, Bottom, Left). Questo la spinge in basso.
        section.setPadding(new javafx.geometry.Insets(40, 0, 0, 0));
        section.setAlignment(Pos.CENTER);

        Label title = new Label("Seleziona per Genere");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 22px; -fx-text-fill: " + TEXT_DARK + "; -fx-font-weight: bold;");

        HBox iconsBox = new HBox(35);
        iconsBox.setAlignment(Pos.CENTER);

        // Usa il widget del padre in un loop
        String[] generi = {"classici", "fantasy", "romance", "gialli", "avventura", "poesia", "storici", "filosofici"};
        for (String g : generi) {
            String nomeFormattato = g.substring(0, 1).toUpperCase() + g.substring(1);
            // Passa il nome del file (es: "fantasy.png") e l'azione
            iconsBox.getChildren().add(super.buildGenreTile(g + ".png", nomeFormattato, onGenreClick));
        }

        section.getChildren().addAll(title, iconsBox);
        return section;
    }
}