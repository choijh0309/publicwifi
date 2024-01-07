function addBookmark() {
    const xhr = new XMLHttpRequest();
    const url = "/AddBookmark"
    const dropdown = document.getElementById("dropdown");
    const selectedOption = dropdown.options[dropdown.selectedIndex];
    const bookmarkName = selectedOption.getAttribute("data-bookmarkname");
    const wifiName = document.getElementById("wifiName").innerText;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("북마크가 추가되었습니다.");
            window.location.reload();
        }
    };
    const params = "bookmarkName=" + encodeURIComponent(bookmarkName) + "&wifiName=" + encodeURIComponent(wifiName);
    xhr.send(params);
}

function deleteBookmark(id) {
    const bookmarkName = document.getElementById("bookmarkName").innerText;
    const confirmDelete = confirm("'" + bookmarkName + "'을(를) 정말로 삭제하시겠습니까?");
    if (confirmDelete) {
        const xhr = new XMLHttpRequest();
        const url = "/DeleteBookmark?id=" + id;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert(bookmarkName + "가 삭제되었습니다.")
                    window.location.href = "bookmark-list-view.jsp";
                } else {
                    alert("삭제에 실패하였습니다.");
                }
            }
        };
        xhr.send();
    } else {

    }
}