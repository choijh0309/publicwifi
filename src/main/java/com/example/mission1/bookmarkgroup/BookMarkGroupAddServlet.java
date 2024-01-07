package com.example.mission1.bookmarkgroup;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@WebServlet("/AddBookmarkGroup")
public class BookMarkGroupAddServlet extends HttpServlet {
    private final BookMarkGroupRepository bookmarkgroupRepository = new BookMarkGroupRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String bookmarkGroupName = request.getParameter("bookmarkGroupName");
        int bookmarkGroupOrder = Integer.parseInt(request.getParameter("bookmarkGroupOrder"));
        Timestamp regiDate = Timestamp.from(Instant.now());
        Timestamp editDate = null;

        BookMarkGroup newBookmarkGroup = new BookMarkGroup(0, bookmarkGroupName, bookmarkGroupOrder, regiDate, editDate);
        bookmarkgroupRepository.createBookmarkGroupTable();
        bookmarkgroupRepository.insertBookmarkgroupTable(newBookmarkGroup);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
