// server/src/main/java/com/yourgame/server/persistence/DatabaseManager.java
package com.yourgame.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static HikariDataSource dataSource;

    public static void initialize() throws SQLException {
        HikariConfig config = new HikariConfig();
        // This creates a database file named 'yourgame_server.db' in the directory where you run the server
        config.setJdbcUrl("jdbc:sqlite:yourgame_server.db"); 

        dataSource = new HikariDataSource(config);

        // Optional but recommended: Create the table if it doesn't exist on startup
        createUsersTable();
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DatabaseManager has not been initialized.");
        }
        return dataSource.getConnection();
    }
    
    private static void createUsersTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                nickname TEXT NOT NULL,
                gender TEXT NOT NULL,
                securityQuestion TEXT NOT NULL,
                answer TEXT NOT NULL,
                avatar TEXT NOT NULL
            )
        """;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}