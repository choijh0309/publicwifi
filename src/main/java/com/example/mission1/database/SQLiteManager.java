package com.example.mission1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager {
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:seoulWifi.db";
    private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";

    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int OPT_VALID_TIMEOUT = 500;

    private Connection conn = null;
    private String driver = null;
    private String url = null;

    public SQLiteManager() {
        this(SQLITE_FILE_DB_URL);
    }

    public SQLiteManager(String url) {
        this.driver = SQLITE_JDBC_DRIVER;
        this.url = url;
    }

    public Connection createConnection() {
        try {
            Class.forName(this.driver);

            this.conn = DriverManager.getConnection(this.url);

            System.out.println("CONNECTED");

            this.conn.setAutoCommit(OPT_AUTO_COMMIT);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return this.conn;
    }

    public void closeConnection() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.conn = null;

            System.out.println("CLOSED");
        }
    }

    public Connection ensureConnection() {
        try {
            if (this.conn == null || this.conn.isValid(OPT_VALID_TIMEOUT)) {
                closeConnection();
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.conn;
    }

    public Connection getConnection() {
        return this.conn;
    }
}