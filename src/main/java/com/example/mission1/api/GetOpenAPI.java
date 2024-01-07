package com.example.mission1.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getOpenAPI", value = "/load-wifi")
public class GetOpenAPI extends HttpServlet {
    private String message;
    private String baseUrl = "http://openapi.seoul.go.kr:8088/745069664e63686f3538766c6b565a/json/TbPublicWifiInfo";

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        String getTotalCount = getWifiTotalCount(baseUrl);
        JsonObject json_totalCount = new Gson().fromJson(getTotalCount, JsonObject.class);
        int totalCount = Integer.parseInt(json_totalCount.getAsJsonObject("TbPublicWifiInfo").get("list_total_count").toString());

        List<JsonObject> allRecords = getAllWifiRecords(totalCount);
        createTableDatabase();
        storeInDatabase(allRecords);
        PrintWriter out = response.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <title>와이파이 정보 구하기</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center\">");
        out.println("<h1>" + totalCount + "/" + "개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>");
        out.println("<a href=\"index.jsp\">홈 으로 가기 </a>");
        out.println("</div>\n" +
                "</body>\n" +
                "</html>\n");
    }

    String getWifiTotalCount(String url) {
        String result = null;

        try {
            OkHttpClient client = new OkHttpClient();
            url = url + "/1/1/";
            Request rq = new Request.Builder()
                    .url(url)
                    .build();
            Response rs = client.newCall(rq).execute();
            result = rs.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    List<JsonObject> getAllWifiRecords(int totalCount) {
        List<JsonObject> allRecords = new ArrayList<>();
        int start = 1;
        String result = null;
        String startPage = null;
        String endPage = null;
        StringBuilder pageUrl = new StringBuilder();
        OkHttpClient client = new OkHttpClient();
        while (start <= totalCount) {
            startPage = String.valueOf(start);
            endPage = String.valueOf(start + 999);
            pageUrl.setLength(0);
            try {
                pageUrl.append(baseUrl);
                pageUrl.append("/" + startPage);
                pageUrl.append("/" + endPage + "/");
                Request rq = new Request.Builder()
                        .url(pageUrl.toString())
                        .build();
                Response rs = client.newCall(rq).execute();
                String pageData = rs.body().string();
                JsonObject jsonPageData = new Gson().fromJson(pageData, JsonObject.class);
                JsonArray jsonArray = jsonPageData.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");

                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    allRecords.add(jsonObject);
                }

                start += 1000;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        return allRecords;
    }

    void createTableDatabase() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:seoulWifi.db");

            String sql = "CREATE TABLE IF NOT EXISTS WIFI_INFO (" +
                    "MANAGE_NO        VARCHAR(20) PRIMARY KEY NOT NULL,\n" +
                    "DISTANCE         INTEGER,\n" +
                    "BOROUGH          VARCHAR(20),\n" +
                    "WIFI_NAME        VARCHAR(20),\n" +
                    "ROAD_ADDR        VARCHAR(20),\n" +
                    "DETAIL_ADDR      VARCHAR(10),\n" +
                    "INSTALL_LOC      VARCHAR(20),\n" +
                    "INSTALL_TYPE     VARCHAR(15),\n" +
                    "INSTALL_AGENCY   VARCHAR(15),\n" +
                    "SERVICE_CLASSFIY VARCHAR(15),\n" +
                    "NET_TYPE         VARCHAR(15),\n" +
                    "INSTALL_YEAR     INTEGER,\n" +
                    "IN_OR_OUT        VARCHAR(6),\n" +
                    "WIFI_CON_ENV     VARCHAR(10),\n" +
                    "LAT              INTEGER,\n" +
                    "LNT              INTEGER,\n" +
                    "WORK_DATE        VARCHAR(40)\n" +
                    ")";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void storeInDatabase(List<JsonObject> records) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:seoulWifi.db");

            for (JsonObject record : records) {
                String manageNo = record.get("X_SWIFI_MGR_NO").getAsString();
                String borough = record.get("X_SWIFI_WRDOFC").getAsString();
                String wifiName = record.get("X_SWIFI_MAIN_NM").getAsString();
                String roadAddr = record.get("X_SWIFI_ADRES1").getAsString();
                String detailAddr = record.get("X_SWIFI_ADRES2").getAsString();
                String intallLoc = record.get("X_SWIFI_INSTL_FLOOR").getAsString();
                String installType = record.get("X_SWIFI_INSTL_TY").getAsString();
                String installAgency = record.get("X_SWIFI_INSTL_MBY").getAsString();
                String serviceClassify = record.get("X_SWIFI_SVC_SE").getAsString();
                String netType = record.get("X_SWIFI_CMCWR").getAsString();
                Integer installYear = record.get("X_SWIFI_CNSTC_YEAR").getAsInt();
                String inOrout = record.get("X_SWIFI_INOUT_DOOR").getAsString();
                String wifiConEnv = record.get("X_SWIFI_REMARS3").getAsString();
                double lat = record.get("LAT").getAsDouble();
                double lnt = record.get("LNT").getAsDouble();
                String workDate = record.get("WORK_DTTM").getAsString();

                String insertSQL = "INSERT INTO WIFI_INFO (MANAGE_NO, BOROUGH, WIFI_NAME, ROAD_ADDR, " +
                        "DETAIL_ADDR, INSTALL_LOC, INSTALL_TYPE, INSTALL_AGENCY, SERVICE_CLASSFIY, NET_TYPE, INSTALL_YEAR, " +
                        "IN_OR_OUT, WIFI_CON_ENV, LAT, LNT, WORK_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
                insertStatement.setString(1, manageNo);
                insertStatement.setString(2, borough);
                insertStatement.setString(3, wifiName);
                insertStatement.setString(4, roadAddr);
                insertStatement.setString(5, detailAddr);
                insertStatement.setString(6, intallLoc);
                insertStatement.setString(7, installType);
                insertStatement.setString(8, installAgency);
                insertStatement.setString(9, serviceClassify);
                insertStatement.setString(10, netType);
                insertStatement.setInt(11, installYear);
                insertStatement.setString(12, inOrout);
                insertStatement.setString(13, wifiConEnv);
                insertStatement.setDouble(14, lat);
                insertStatement.setDouble(15, lnt);
                insertStatement.setString(16, workDate);
                insertStatement.executeUpdate();
                insertStatement.close();
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}