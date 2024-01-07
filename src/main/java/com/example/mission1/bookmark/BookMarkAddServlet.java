package com.example.mission1.bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@WebServlet("/AddBookmark")
public class BookMarkAddServlet extends HttpServlet {
    private final BookMarkRepository bookmarkRepository = new BookMarkRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String bookmarkName = request.getParameter("bookmarkName");
        String wifiName = request.getParameter("wifiName");
        Timestamp regiDate = Timestamp.from(Instant.now());

        BookMark newBookmark = new BookMark(0, bookmarkName, wifiName, regiDate);
        bookmarkRepository.createBookmarkTable();
        bookmarkRepository.insertBookmarkTable(newBookmark);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
