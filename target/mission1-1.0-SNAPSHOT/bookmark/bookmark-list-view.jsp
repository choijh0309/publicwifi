<%@ page import="com.example.mission1.bookmark.BookMark" %>
<%@ page import="com.example.mission1.bookmark.BookMarkRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #bookmarkGroup {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #bookmarkGroup td, #bookmarkGroup th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #bookmarkGroup tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #bookmarkGroup tr:hover {
            background-color: #ddd;
        }

        #bookmarkGroup th {
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<h1><%= "북마크" %>
</h1>
<a href="/">홈 </a>
<a> | </a>
<a href="../history/history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="">북마크 보기</a>
<a> | </a>
<a href="../bookmarkgroup/bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>
<table id="bookmarkGroup">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    <%
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        BookMarkRepository bookmarkrepository = new BookMarkRepository();
        bookmarkrepository.createBookmarkTable();
        List<BookMark> bookmarkList = bookmarkrepository.getAllBookmark();

        if (bookmarkList.isEmpty()) {
    %>
    <tr>
        <td style="text-align: center;" colspan="5"> 정보가 존재하지 않습니다.</td>
    </tr>
    <% } else {
        for (BookMark bookmark : bookmarkList) { %>
    <tr>
        <td><%=bookmark.getId()%>
        </td>
        <td><%=bookmark.getBookmarkName()%>
        </td>
        <td><%=bookmark.getWifiName()%>
        </td>
        <td><%=outputFormat.format(bookmark.getRegiDate())%>
        </td>
        <td style="text-align: center"><a
                href="bookmark-delete.jsp?id=<%= bookmark.getId() %>&bookmarkName=<%=bookmark.getBookmarkName()%>&wifiName=<%= bookmark.getWifiName() %>&regiDate=<%= bookmark.getRegiDate() %>">삭제</a>
        </td>
    </tr>
    <% }
    } %>
</table>
</body>
</html>
