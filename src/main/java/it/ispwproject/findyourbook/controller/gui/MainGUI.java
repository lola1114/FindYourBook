package it.ispwproject.findyourbook.controller.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    private static Stage primaryStage;

    private static void setPrimaryStage(Stage stage) {
        MainGUI.primaryStage = stage;
    }

    @Override
    public void start(Stage stage) {
        setPrimaryStage(stage);
        stage.setTitle("Find Your Book");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setResizable(true);

        showLogin();
    }

    public static void showLogin() {
        new LoginGUI(primaryStage).show();
    }

    // Niente showRegistration per ora, la faremo dopo!

    // Ecco il cambio di nome esatto come il collega!
    public static void showReaderDashboard() {
        new ReaderDashboardGUI(primaryStage).show();
    }

    public static void showPublisherDashboard() {
        new PublisherDashboardGUI(primaryStage).show();
    }

    public static void launch(String[] args) {
        Application.launch(MainGUI.class, args);
    }
}