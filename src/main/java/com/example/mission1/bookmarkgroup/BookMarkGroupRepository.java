package com.example.mission1.bookmarkgroup;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkGroupRepository {
    private String dbUrl = "jdbc:sqlite:/Users/choijunha/Downloads/SearchSeoulWifiZone-main/src/main/java/db/seoulWifi.db";

    public void createBookmarkGroupTable() {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(dbUrl);
                 Statement statement = connection.createStatement()) {
                String createTableSql = " CREATE TABLE IF NOT EXISTS BOOKMARK_GROUP (" +
                        "ID  INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "BOOKMARKGROUP_NAME TEXT," +
                        "BOOKMARKGROUP_ORDER INTEGER," +
                        "REG_DATE TIMESTAMP," +
                        "EDIT_DATE TIMESTAMP" +
                        ");";
                statement.execute(createTableSql);

                System.out.println("BOOKMARK_GROUP 테이블이 성공적으로 생성되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver 찾지 못하였습니다.");
            e.printStackTrace();
        }
    }

    public void insertBookmarkgroupTable(BookMarkGroup bookmarkGroup) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String insertSql = " INSERT INTO BOOKMARK_GROUP (BOOKMARKGROUP_NAME, BOOKMARKGROUP_ORDER, REG_DATE) VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, bookmarkGroup.getBookmarkgroupName());
                preparedStatement.setInt(2, bookmarkGroup.getBookmarkgroupOrder());
                Timestamp regiDate = bookmarkGroup.getRegiDate();
                preparedStatement.setTimestamp(3, regiDate);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBookmarkgroupTable(String groupName, int groupOrder, Timestamp editDate, int id) {
        try {
            Class.forName("org.sqlite.JDBC");
            String insertSql = "UPDATE BOOKMARK_GROUP " +
                    " SET BOOKMARKGROUP_NAME = ?, BOOKMARKGROUP_ORDER = ?, EDIT_DATE = ?" +
                    " WHERE ID = ?;";
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                    preparedStatement.setString(1, groupName);
                    preparedStatement.setInt(2, groupOrder);
                    preparedStatement.setTimestamp(3, editDate);
                    preparedStatement.setInt(4, id);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
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

    public void deleteBookmarkgroupById(int id) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String deleteSql = "DELETE FROM BOOKMARK_GROUP WHERE ID = ?;";
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

    // 모든 북마크 그룹의 정보를 조회하는 메서드
    public List<BookMarkGroup> getAllBookmarkgroup() {
        List<BookMarkGroup> boomarkgroupList = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKMARK_GROUP ORDER BY BOOKMARKGROUP_ORDER;")) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bookmarkgroupName = rs.getString("bookmarkgroup_Name");
                    int bokkmarkgroupOrder = rs.getInt("bookmarkgroup_Order");
                    Timestamp regiDate = rs.getTimestamp("reg_Date");
                    Timestamp editDate = rs.getTimestamp("edit_Date");
                    BookMarkGroup bookmarkgroup = new BookMarkGroup(id, bookmarkgroupName, bokkmarkgroupOrder, regiDate, editDate);
                    boomarkgroupList.add(bookmarkgroup);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return boomarkgroupList;
    }
}
