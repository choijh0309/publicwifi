<%@ page import="com.example.mission1.bookmarkgroup.BookMarkGroupRepository" %>
<%@ page import="com.example.mission1.bookmarkgroup.BookMarkGroup" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
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
<h1><%= "북마크 그룹" %>
</h1>
<a href="/">홈 </a>
<a> | </a>
<a href="../history/history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="../bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>
<button id="addBookmarkgroup" onclick="location.href='bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
<br/>
<table id="bookmarkGroup">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    <%
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        BookMarkGroupRepository bookMarkGroupRepository = new BookMarkGroupRepository();
        bookMarkGroupRepository.createBookmarkGroupTable();
        List<BookMarkGroup> bookmarkgroupList = bookMarkGroupRepository.getAllBookmarkgroup();

        if (bookmarkgroupList.isEmpty()) {
    %>
    <tr>
        <td style="text-align: center;" colspan="6"> 북마크 그룹을 추가해 주세요.</td>
    </tr>
    <% } else {
        for (BookMarkGroup bookmarkgroup : bookmarkgroupList) { %>
    <tr>
        <td><%= bookmarkgroup.getId()%>
        </td>
        <td><%= bookmarkgroup.getBookmarkgroupName()%>
        </td>
        <td><%= bookmarkgroup.getBookmarkgroupOrder()%>
        </td>
        <td><%= outputFormat.format(bookmarkgroup.getRegiDate())%>
        </td>
        <td>
            <%
                Timestamp editDate = bookmarkgroup.getEditDate();
                if (editDate == null) {
                    out.print("");
                } else {
                    out.print(outputFormat.format(editDate));
                }
            %>
        </td>
        <td style="text-align: center">
            <a href="bookmark-group-edit.jsp?id=<%= bookmarkgroup.getId() %>&name=<%= bookmarkgroup.getBookmarkgroupName() %>&order=<%= bookmarkgroup.getBookmarkgroupOrder() %>">수정</a>
            <a href="bookmark-group-delete.jsp?id=<%= bookmarkgroup.getId() %>&name=<%= bookmarkgroup.getBookmarkgroupName() %>&order=<%= bookmarkgroup.getBookmarkgroupOrder() %>">삭제</a>
        </td>
    </tr>
    <% }
    } %>
</table>
</body>
</html>
