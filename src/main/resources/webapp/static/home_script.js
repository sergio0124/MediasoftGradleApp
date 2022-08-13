function http_post(theUrl, inputData) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, true); // false for synchronous request
    xmlHttp.send(inputData);
    return xmlHttp.responseText;
}


function http_get(username){
    var xmlHttp = new XMLHttpRequest();
    let url = "/users/dialog?recipient_name=" + username
    xmlHttp.open("GET", url, false); // false for synchronous request
    xmlHttp.send(inputData);
    return xmlHttp.responseText;
}


function search_for_user() {
    var data = new FormData();
    let search = document.getElementById("search_field");
    data.append("text", search.value);
    user = http_post("/users/findByWord", data);
    insert_data_into_table(user);
}


function insert_data_into_table(table_data) {
    var Table = document.getElementById("table_search");
    Table.innerHTML = "";
    tbody = Table.getElementsByTagName("tbody")[0];

    for (var i in table_data) {
        var row = document.createElement("tr");
        var cell = document.createElement("td");
        cell.innerHTML = i.username;
        row.appendChild(cell);
        tbody.appendChild(row);
    }

    add_listener_to_each_row();
}


function add_listener_to_each_row() {
    document.querySelectorAll('#myTable td')
        .forEach(e => e.addEventListener("click", function () {
                http_get(e.value)
            })
        );
}

let button = document.getElementById("search_button");
button.addEventListener("click", search_for_user());
