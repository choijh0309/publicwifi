<%@ page import="com.example.mission1.wifi.WifiRepository" %>
<%@ page import="com.example.mission1.wifi.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #wifiInfos {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            padding-top: 12px;
        }

        #wifiInfos td, #wifiInfos th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        #wifiInfos tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #wifiInfos tr:hover {
            background-color: #ddd;
        }

        #wifiInfos th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }

        #wifiInfos td {
            text-align: center;
            padding-top: 20px;
            padding-bottom: 20px;
        }

    </style>
</head>
<body>
<% WifiRepository wifiRepository = new WifiRepository();
    wifiRepository.setNullDistance();%>
<h1><%= "와이파이 정보 구하기" %>
</h1>
<a href=" ">홈 </a>
<a> | </a>
<a href="history/history.jsp">위치 히스토리 목록 </a>
<a> | </a>
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<a> | </a>
<a href="bookmark/bookmark-list-view.jsp">북마크 보기</a>
<a> | </a>
<a href="bookmarkgroup/bookmark-group.jsp">북마크 그룹 관리</a>
<p></p>
<a>LAT: </a>
<%--<--min max 제한도 둘건지? -->--%>
<input id="LAT" type="text" value=0.0
       oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">,
<a>LNT: </a>
<input id="LNT" type="text" value=0.0
       oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
<button id="btnLocation" onclick="loadGeo()">내 위치 가져오기</button>
<button id="btnNearwifiInfo" onclick="js:sendDatatoServer()">근처 WIFI 정보 보기</button>


<p></p>

<table id="wifiInfos">
    <thead>
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </thead>
    </tr>

    <tbody id="wifiDataBody">
    <tr>
        <td colspan="17"> 위치 정보를 입력한 후에 조회해 주세요</td>
    </tr>
    </tbody>

</table>
<script src="javascript/load-location.js" charset="UTF-8"></script>
</body>
</html>