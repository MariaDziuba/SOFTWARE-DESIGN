package ru.akirakozov.sd.refactoring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase {
    private static final String DATABASE_URL = "jdbc:sqlite:test.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void createTables() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query =
                    "CREATE TABLE IF NOT EXISTS PRODUCT" +
                            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " NAME           TEXT    NOT NULL, " +
                            " PRICE          INT     NOT NULL)";

            statement.executeUpdate(query);
        }
    }

    public static void deleteTables() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "DELETE FROM PRODUCT";
            statement.executeUpdate(query);
        }
    }

    public static void dropTables() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "DROP TABLE PRODUCT";

            statement.executeUpdate(sql);
        }
    }
}

