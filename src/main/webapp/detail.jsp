<%@ page import="com.example.mission1.wifi.WifiRepository" %>
<%@ page import="com.example.mission1.wifi.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mission1.bookmarkgroup.BookMarkGroupRepository" %>
<%@ page import="com.example.mission1.bookmarkgroup.BookMarkGroup" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #wifidetailInfos {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #wifidetailInfos td, #wifidetailInfos th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #wifidetailInfos tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #wifidetailInfos tr:hover {
            background-color: #ddd;
        }

        #wifidetailInfos th {
            width: 19%;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #04AA6D;
            color: white;
            padding: 10px;
        }

        #wifidetailInfos td {
            text-align: left;
        }
    </style>
</head>
<body>
<h1><%= "와이파이 정보 구하기" %>
</h1>
<a href="http://localhost:8080">홈 </a>
<a> | </a>
<a href="history/history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="bookmarkgroup/bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>
<%--북마크 이름 선택--%>
<select id="dropdown">
    <option value="" selected disabled>북마크 이름 선택</option>
    <% BookMarkGroupRepository bookMarkGroupRepository = new BookMarkGroupRepository();
        List<BookMarkGroup> currentBookmarkGroup = bookMarkGroupRepository.getAllBookmarkgroup();
        if (currentBookmarkGroup.size() == 0) {
            out.print("<option value='' disabled>북마크 그룹이 존재하지 않습니다.</option>");
        } else {
            for (BookMarkGroup bookmarkGroup : currentBookmarkGroup) { %>
    <option value="<%= bookmarkGroup.getId() %>" data-bookmarkname="<%= bookmarkGroup.getBookmarkgroupName() %>">
        <%= bookmarkGroup.getBookmarkgroupName() %>
    </option>
    <% }
    } %>
</select>
<button onclick="js:addBookmark()">북마크 추가하기</button>
<% Wifi wifiInfo = (Wifi) request.getAttribute("wifiInfo"); %>
<% if (wifiInfo != null) { %>
<table id="wifidetailInfos">
    <tr>
        <th>거리(Km)</th>
        <td>${wifiInfo.distance}</td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td>${wifiInfo.manageNo}</td>
    </tr>
    <tr>
        <th>자치구</th>
        <td>${wifiInfo.borough}</td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td id="wifiName">${wifiInfo.wifiName}</td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td>${wifiInfo.roadAddr}</td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td>${wifiInfo.detailAddr}</td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td>${wifiInfo.intallLoc}</td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td>${wifiInfo.installType}</td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td>${wifiInfo.installAgency}</td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td>${wifiInfo.serviceClassify}</td>
    </tr>
    <tr>
        <th>망종류</th>
        <td>${wifiInfo.netType}</td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td>${wifiInfo.installYear}</td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td>${wifiInfo.inOrout}</td>
    </tr>
    <tr>
        <th>WIFI접속환경</th>
        <td>${wifiInfo.wifiConEnv}</td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td>${wifiInfo.lat}</td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td>${wifiInfo.lnt}</td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td>${wifiInfo.workDate}</td>
    </tr>
    <% } else { %>
    <p>와이파이 정보를 찾을 수 없습니다.</p>
    <% } %>
</table>
<script src="javascript/cd-bookmark.js" charset="UTF-8"></script>
</body>
</html>
