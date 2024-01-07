package com.example.mission1.bookmark;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckBookmarks")
public class BookMarkCheckServlet extends HttpServlet {
    private final BookMarkRepository bookmarkRepository = new BookMarkRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String groupName = request.getParameter("groupName");

        boolean hasBookmarks = bookmarkRepository.hasBookmarksInGroup(groupName);
        String originBookmarkGroupName = groupName;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hasBookmarks", hasBookmarks);
        jsonObject.addProperty("originBookmarkGroupName", originBookmarkGroupName);
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }


}
