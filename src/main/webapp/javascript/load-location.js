class UserLocation {
    static lat = -1.0;
    static lnt = -1.0;
}

function loadGeo() {
    navigator.geolocation.getCurrentPosition(onGeoOk, onGeoError);
}

function onGeoOk(position) {
    UserLocation.lat = position.coords.latitude;
    UserLocation.lnt = position.coords.longitude;
    document.getElementById("LAT").value = UserLocation.lat;
    document.getElementById("LNT").value = UserLocation.lnt;
}

function onGeoError() {
    alert("Can't find you. No weather for you.");
}

function sendDatatoServer() {
    if (document.getElementById("LAT").value != 0.0 && document.getElementById("LNT").value != 0.0) {
        // HistoryAddServlet에 보내는 fucntion 호출 : 사용자의 위치 history db 저장
        UserLocation.lat = document.getElementById("LAT").value;
        UserLocation.lnt = document.getElementById("LNT").value;
        sendHistoryAddServlet();
        // WifiServlet에 보내는 function 호출 : 사용자의 위치에서 가장 가까운 20개의 데이터를 거리값을 null에서 값으로 update해준다.
        sendWifiServlet();
    } else {
        alert("내 위치 가져오기를 한 다음 이용하세요");
    }


}

function sendHistoryAddServlet() {
    const xhr = new XMLHttpRequest();
    const url = "/History";
    const data = {
        lat: UserLocation.lat.toString(),
        lnt: UserLocation.lnt.toString(),
    };
    console.log(UserLocation.lat);
    console.log(UserLocation.lnt);
    console.log(data);
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log("데이터가 성공적으로 추가되었습니다. in sendHistoryAddServlet");
            } else {
                console.error("데이터 추가에 실패하였습니다. in sendHistoryAddServlet");
            }
        }
    };

    xhr.send(JSON.stringify(data)); // 데이터를 서버로 전송
}

function sendWifiServlet() {
    const xhr = new XMLHttpRequest();
    const url = "/Wifi";
    const data = {
        lat: UserLocation.lat.toString(),
        lnt: UserLocation.lnt.toString()
    };
    // 서버로부터 응답을 받았을 때의 동작 정의
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const wifiData = JSON.parse(xhr.responseText);
            const wifiTableBody = document.getElementById("wifiDataBody");
            wifiTableBody.innerHTML = "";

            if (wifiData.length === 0) {
                // Wifi가 주변에 없다면 -> 서울이 아니라면
                const noWifiRow = document.createElement("tr");
                const noWifiCell = document.createElement("td");
                noWifiCell.colSpan = 17;
                noWifiCell.innerText = "주변 와이파이 정보가 없습니다.";
                noWifiRow.appendChild(noWifiCell);
                wifiTableBody.appendChild(noWifiRow);
            } else {
                // If wifi data found, populate the table with the data
                for (const wifi of wifiData) {
                    const wifiRow = document.createElement("tr");
                    wifiRow.innerHTML = `
                                <td style="text-align: left;">${wifi.distance}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_MGR_NO}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_WRDOFC}</td>
                                <td style="text-align: left;">
                                    <a href="DetailServlet?manageNo=${encodeURIComponent(wifi.X_SWIFI_MGR_NO)}">
                                        ${wifi.X_SWIFI_MAIN_NM}</a></td>
                                <td style="text-align: left;">${wifi.X_SWIFI_ADRES1}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_ADRES2}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_INSTL_FLOOR}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_INSTL_TY}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_INSTL_MBY}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_SVC_SE}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_CMCWR}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_CNSTC_YEAR}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_INOUT_DOOR}</td>
                                <td style="text-align: left;">${wifi.X_SWIFI_REMARS3}</td>
                                <td style="text-align: left;">${wifi.LNT}</td>
                                <td style="text-align: left;">${wifi.LAT}</td>
                                <td style="text-align: left;">${wifi.WORK_DTTM}</td>
                            `;
                    wifiTableBody.appendChild(wifiRow);
                }
            }
        } else {
            console.error("Request failed with status:", xhr.status);
        }
    };

    xhr.onerror = function () {
        console.error("Network request failed.");
    };

    xhr.send(JSON.stringify(data));
}

