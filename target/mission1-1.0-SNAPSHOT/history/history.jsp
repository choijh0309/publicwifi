<%@ page import="com.example.mission1.history.HistoryRepository" %>
<%@ page import="com.example.mission1.history.History" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #historyInfos {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #historyInfos td, #historyInfos th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #historyInfos tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #historyInfos tr:hover {
            background-color: #ddd;
        }

        #historyInfos th {
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<h1><%= "위치 히스토리 목록" %>
</h1>
<a href="/">홈 </a>
<a> | </a>
<a href="history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="../bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="../bookmarkgroup/bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>
<table id="historyInfos">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HistoryRepository historyRepository = new HistoryRepository();
        List<History> historyList = historyRepository.getAllHistory();

        if (historyList.isEmpty()) {
    %>
    <tr>
        <td style="text-align: center" colspan="5">조회된 위치 정보가 존재하지 않습니다.</td>
    </tr>
    <% } else { %>
    <tr>
        <td style="text-align: center" colspan="5">
            <button onclick="js:allDelete(-1)">전체 삭제</button>
        </td>
    </tr>
    <%
        for (History history : historyList) { %>
    <tr>
        <td><%= history.getId() %>
        </td>
        <td><%= history.getLat() %>
        </td>
        <td><%= history.getLnt() %>
        </td>
        <td><%= outputFormat.format(history.getInquiryDate()) %>
        </td>
        <td style="text-align: center">
            <button onclick="js:confirmDelete(<%= history.getId() %>)">삭제</button>
        </td>
    </tr>
    <% }
    } %>
</table>
<script src="../javascript/delete-history.js" charset="UTF-8"></script>
</body>
</html>