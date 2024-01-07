function confirmDelete(id) {

    if (confirm("삭제하시겠습니까?")) {
        const xhr = new XMLHttpRequest();
        const url = "/history-delete?id=" + id;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    window.location.reload();
                } else {
                    alert("삭제에 실패하였습니다.");
                }
            }
        };
        xhr.send();
    } else {
    }
}

function allDelete(id) {
    if (confirm("위치 기록을 전체 삭제하시겠습니까?")) {
        const xhr = new XMLHttpRequest();
        const url = "/history-delete?id=" + id;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    window.location.reload();
                } else {
                    alert("전체 삭제에 실패하였습니다.");
                }
            }
        };
        xhr.send();
    } else {
    }
}