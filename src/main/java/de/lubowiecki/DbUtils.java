package de.lubowiecki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    // user.home = Pfad zum Benutzerordner
    private final static String FOLDER = System.getProperty("user.home");

    // Datenbank wird im Benutzerordner gespeichert
    private final static String URL = "jdbc:sqlite:" + FOLDER + "/coffeehouse.db";

    private DbUtils() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
