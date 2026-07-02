package it.ispwproject.findyourbook.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
            props.load(input);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Errore nel caricamento del file db.properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                props.getProperty("CONNECTION_URL"),
                props.getProperty("LOGIN_USER"),
                props.getProperty("LOGIN_PASS")
        );
    }
}