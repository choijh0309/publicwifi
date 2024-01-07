<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #bookmarkDelete {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #bookmarkDelete td, #bookmarkGroup th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #bookmarkDelete tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #bookmarkDelete tr:hover {
            background-color: #ddd;
        }

        #bookmarkDelete th {
            width: 19%;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #04AA6D;
            color: white;
            padding: 10px;
        }

        #bookmarkDelete td {
            text-align: left;
        }
    </style>
</head>
<body>
<h1><%= "북마크 그룹 삭제하기" %>
</h1>
<a href="../index.jsp">홈 </a>
<a> | </a>
<a href="../history/history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="../bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="../bookmarkgroup/bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>

<table id="bookmarkDelete">
    <tr>
        <th>북마크 이름</th>
        <td id="bookmarkName"><%= request.getParameter("bookmarkName") %>
        </td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td id="wifiName"><%= request.getParameter("wifiName") %>
        </td>
    </tr>
    <tr>
        <th>등록일자</th>
        <td id="regiDate"><%= request.getParameter("regiDate") %>
        </td>
    </tr>
    <tr>
        <td style="text-align: center" colspan="2">
            <a href="bookmark-list-view.jsp">돌아가기</a>
            <button onclick="js:deleteBookmark(<%= request.getParameter("id") %>)">삭제</button>
        </td>
    </tr>
</table>
<script src="../javascript/cd-bookmark.js" charset="UTF-8"></script>
</body>
</html>
