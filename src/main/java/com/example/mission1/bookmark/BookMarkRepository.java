package com.example.mission1.bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkRepository {
    private String dbUrl = "jdbc:sqlite:/Users/choijunha/Downloads/SearchSeoulWifiZone-main/src/main/java/db/seoulWifi.db";

    public void createBookmarkTable() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(dbUrl);
                 Statement statement = connection.createStatement()) {
                String createTableSql = " CREATE TABLE IF NOT EXISTS BOOKMARK (" +
                        "ID  INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "BOOKMARK_NAME TEXT," +
                        "WIFI_NAME TEXT," +
                        "REG_DATE TIMESTAMP" +
                        ");";

                statement.execute(createTableSql);

                System.out.println("BOOKMARK 테이블이 성공적으로 생성되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver 찾지 못하였습니다.");
            e.printStackTrace();
        }
    }

    public void insertBookmarkTable(BookMark bookmark) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String insertSql = " INSERT INTO BOOKMARK (BOOKMARK_NAME, WIFI_NAME, REG_DATE) VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, bookmark.getBookmarkName());
                preparedStatement.setString(2, bookmark.getWifiName());
                Timestamp regiDate = bookmark.getRegiDate();
                preparedStatement.setTimestamp(3, regiDate);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAlldata() {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String deleteAllSql = "DELETE FROM BOOKMARK_GROUP;";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAllSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookmarkById(int id) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String deleteSql = "DELETE FROM BOOKMARK WHERE ID = ?;";
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

    public List<BookMark> getAllBookmark() {
        List<BookMark> boomarkList = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKMARK ORDER BY ID;")) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bookmarkName = rs.getString("bookmark_Name");
                    String wifiName = rs.getString("wifi_Name");
                    Timestamp regiDate = rs.getTimestamp("reg_Date");
                    BookMark bookmark = new BookMark(id, bookmarkName, wifiName, regiDate);
                    boomarkList.add(bookmark);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return boomarkList;
    }

    public boolean hasBookmarksInGroup(String groupName) {
        boolean hasBookmarks = false;

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String query = "SELECT COUNT(*) FROM BOOKMARK WHERE BOOKMARK_NAME = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, groupName);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        hasBookmarks = count > 0;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return hasBookmarks;
    }

    public void deleteBookmarksByGroupName(String groupName) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String deleteSql = "DELETE FROM BOOKMARK WHERE BOOKMARK_NAME = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                    preparedStatement.setString(1, groupName);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBookmarksByGroupName(String origingroupName, String newgroupName) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String updateSql = "UPDATE BOOKMARK SET BOOKMARK_NAME = ? WHERE BOOKMARK_NAME = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                    preparedStatement.setString(1, newgroupName);
                    preparedStatement.setString(2, origingroupName);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
