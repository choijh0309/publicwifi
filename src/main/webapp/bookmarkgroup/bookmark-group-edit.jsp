<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #bookmarkGroupDelete {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #bookmarkGroupDelete td, #bookmarkGroup th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #bookmarkGroupDelete tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #bookmarkGroupDelete tr:hover {
            background-color: #ddd;
        }

        #bookmarkGroupDelete th {
            width: 19%;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #04AA6D;
            color: white;
            padding: 10px;
        }

        #bookmarkGroupDelete td {
            text-align: left;
        }
    </style>
</head>
<body>
<h1><%= "북마크 그룹 추가하기" %>
</h1>
<a href="../index.jsp">홈 </a>
<a> | </a>
<a href="http://localhost:8080">위치 히스토리 목록 </a>
<a> | </a>
<a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="../bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>

<table id="bookmarkGroupDelete">
    <tr>
        <th>북마크 그룹 이름</th>
        <td><input type="text" id="bookmarkGroupName" value="<%= request.getParameter("name") %>"/></td>
    </tr>
    <tr>
        <th>순서</th>
        <td><input type="text" id="bookmarkGroupOrder" value="<%= request.getParameter("order") %>"/></td>
    </tr>
    <tr>
        <td style="text-align: center" colspan="2">
            <a href="bookmark-group.jsp">돌아가기</a>
            <button onclick="js:editBookmarkgroup(<%= request.getParameter("id") %>, '<%= request.getParameter("name") %>')">
                수정
            </button>
        </td>
    </tr>
</table>
<script src="../javascript/crud-bookmarkgroup.js" charset="UTF-8"></script>
</body>
</html>
