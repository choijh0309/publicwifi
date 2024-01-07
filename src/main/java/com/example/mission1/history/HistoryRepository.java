package com.example.mission1.history;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private String dbUrl = "jdbc:sqlite:/Users/choijunha/Downloads/SearchSeoulWifiZone-main/src/main/java/db/seoulWifi.db";

    public void createHistoryTable() {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(dbUrl);
                 Statement statement = connection.createStatement()) {
                String createTableSql = "CREATE TABLE IF NOT EXISTS LOCATION_HISTORY (" +
                        "ID  INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "LAT DOUBLE," +
                        "LNT DOUBLE," +
                        "INQUIRY_DATE TIMESTAMP" +
                        ");";
                statement.execute(createTableSql);

                System.out.println("LOCATION_HISTORY 테이블이 성공적으로 생성되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver 찾지 못하였습니다.");
            e.printStackTrace();
        }
    }

    public void insertHistoryTable(History history) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String insertSql = "INSERT INTO LOCATION_HISTORY (LAT, LNT, INQUIRY_DATE) VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setDouble(1, history.getLat());
                preparedStatement.setDouble(2, history.getLnt());
                Timestamp inquiryDate = history.getInquiryDate();
                preparedStatement.setTimestamp(3, inquiryDate);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAlldata() {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String deleteAllSql = "DELETE FROM LOCATION_HISTORY";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAllSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHistoryById(int id) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String deleteSql = "DELETE FROM LOCATION_HISTORY WHERE ID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                    preparedStatement.setInt(1, id);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.printf("ID=%d 가 삭제되었습니다.\n", id);
                    } else {
                        System.out.printf("ID=%d 가 존재하지 않습니다.\n", id);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<History> getAllHistory() {
        List<History> historyList = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM LOCATION_HISTORY ORDER BY ID DESC")) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    double lat = rs.getDouble("lat");
                    double lnt = rs.getDouble("lnt");
                    Timestamp inquiryDate = rs.getTimestamp("inquiry_Date");
                    History history = new History(id, lat, lnt, inquiryDate);
                    historyList.add(history);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return historyList;
    }
}
