// На кодинг и отладку этого жопаскрипт кода ушло >6 часов

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    console.log(xmlHttp.responseText)
    return JSON.parse(xmlHttp.responseText);
}


function http_get(username) {
    return "/message/dialog?recipient_name=" + username;
}


function search_for_user() {
    let search = document.getElementById("search_field");
    let data = {"text" : search.value};
    let json = JSON.stringify(data);
    let user = http_post("/users/findByWord", json);
    insert_data_into_table(user);
}


function insert_data_into_table(table_data) {
    let Table = document.getElementById("table_search");
    Table.innerHTML = "";

    for (let i in table_data) {
        var row = Table.insertRow(0);
        var cell = row.insertCell(0);
        cell.innerHTML = table_data[i].username;
    }

    add_listener_to_each_row();
}


function add_listener_to_each_row() {
    let row = document.getElementById('table_search').rows;
    for (let i = 0; i < row.length; i++) {
        for (let j = 0; j < row[i].cells.length; j++ ) {
            row[i].cells[j].addEventListener('click', function(){
                window.location.replace(http_get(row[i].cells[j].innerHTML));
            })
        }
    }
}

let button = document.getElementById("search_button");
button.addEventListener("click", function () {
    search_for_user();
});
