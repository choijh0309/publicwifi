package com.example.mission1.history;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;

@WebServlet("/History")
public class HistoryAddServlet extends HttpServlet {
    private final HistoryRepository historyRepository = new HistoryRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = request.getReader();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(stringBuilder.toString()).getAsJsonObject();

            double lat = jsonObject.get("lat").getAsDouble();
            double lnt = jsonObject.get("lnt").getAsDouble();
            Timestamp inquiryDate = Timestamp.from(Instant.now());

            History history = new History(0, lat, lnt, inquiryDate);
            historyRepository.createHistoryTable();
            historyRepository.insertHistoryTable(history);

            PrintWriter out = response.getWriter();
            out.println("데이터가 성공적으로 추가되었습니다. in Servlet");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
