package it.ispwproject.findyourbook.controller.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public final class GUIUtils {

    private GUIUtils() {
    }

    public static Scene createScene(Parent root) {

        // Creiamo la scena usando le dimensioni del tuo MainGUI
        Scene scene = new Scene(
                root,
                MainGUI.WINDOW_WIDTH,
                MainGUI.WINDOW_HEIGHT
        );

        // IL COLLEGAMENTO MAGICO: diciamo a JavaFX di usare il tuo nuovo CSS
        try {
            String cssPath = GUIUtils.class.getResource("/styles/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("ATTENZIONE: File style.css non trovato nella cartella /css/");
        }

        return scene;
    }

    // Ho commentato i font del collega per evitare crash.
    // Se in futuro vorrai aggiungere font speciali, li riattiveremo qui.
    /*
    private static void loadFonts() {
        Font.loadFont(GUIUtils.class.getResourceAsStream("/fonts/Poppins-Regular.ttf"), 14);
        Font.loadFont(GUIUtils.class.getResourceAsStream("/fonts/Poppins-Bold.ttf"), 14);
        Font.loadFont(GUIUtils.class.getResourceAsStream("/fonts/Poppins-Italic.ttf"), 14);
        Font.loadFont(GUIUtils.class.getResourceAsStream("/fonts/Poppins-BoldItalic.ttf"), 14);
    }
    */
}