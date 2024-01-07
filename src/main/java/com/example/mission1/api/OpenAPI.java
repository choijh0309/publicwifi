package com.example.mission1.api;

import com.example.mission1.wifi.Wifi;
import com.example.mission1.wifi.WifiRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public class OpenAPI {
    private String baseUrl = "http://openapi.seoul.go.kr:8088/";
    private String apiKey = "745069664e63686f3538766c6b565a";
    private int totalCnt;

    public void fetchAndStoreData() throws IOException {
        int page = 1;
        int pageSize = 1000;

        WifiRepository wifidb = new WifiRepository();
        wifidb.createWifiTable();
        wifidb.deleteAlldata();
        while (true) {
            String apiUrl = baseUrl + apiKey + "/json/TbPublicWifiInfo/" + page + "/" + (page + pageSize - 1) + "/";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();
                conn.disconnect();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                JsonArray rows = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");

                Wifi[] wifiInfoArray = gson.fromJson(rows, Wifi[].class);
                totalCnt += wifiInfoArray.length;
                wifidb.insertWifiTable(wifiInfoArray);

                page += pageSize;

                if (wifiInfoArray.length < pageSize) {
                    break;
                }
            } else {
                conn.disconnect();
                throw new IOException("API 요청에 실패했습니다. HTTP 에러 코드: " + conn.getResponseCode());
            }
        }
    }
}
