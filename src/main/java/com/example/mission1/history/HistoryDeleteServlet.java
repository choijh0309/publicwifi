package com.example.mission1.history;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/history-delete")
public class HistoryDeleteServlet extends HttpServlet {
    private final HistoryRepository historyRepository = new HistoryRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request);
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID 값이 필요합니다.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            if (id == -1) {
                historyRepository.deleteAlldata();
            } else if (id >= 1) {
                historyRepository.deleteHistoryById(id);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 ID 값입니다.");
        }
    }
}
