let recipient_name = new URL(window.location.href).searchParams.get("recipient_name");
let marker = document.getElementById("refactor_marker");
let marker_end = document.getElementById("refactor_end");
marker_end.addEventListener("click", end_refactor);
let id = 0;
let input = document.getElementById("message_field");
const prefix = "......................................";
window.addEventListener("click", function () {
    document
        .getElementById("context_menu")
        .classList.remove("active");
});


let context_menu = document.getElementById("context_menu");



let table = document.getElementById("table_messages");
for (let i = 0; i < table.rows.length; i++) {
    let row = table.rows[i];
    row.cells[2].addEventListener("click", function () {
        event_listener_function(row.cells[2]);
    });
}


let send_button = document.getElementById("message_button");
send_button.addEventListener("click", function () {
    if (marker.style.visibility === "hidden") {
        send_message();
    } else {
        complete_refactor();
    }
});


function send_message() {
    let text = document.getElementById("message_field").value;

    let jdata = {
        "recipientName": recipient_name,
        "text": text
    };
    let data = JSON.stringify(jdata);

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", window.location.href, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(data);

    let result = xmlHttp.status;
    let JsonResult = JSON.parse(xmlHttp.responseText);
    if (result < 400) {
        input.value = "";

        let row = table.insertRow();

        let cell = row.insertCell();
        cell.innerHTML = JsonResult[0].id;
        cell.style.visibility = "hidden";

        cell = row.insertCell();
        cell.innerHTML = JsonResult[0].sender.username;
        cell.style.visibility = "hidden";

        cell = row.insertCell();
        cell.innerHTML = "......................................" + JsonResult[0].text;
        cell.addEventListener("click", function () {
            event_listener_function(cell)
        });

        document.getElementById("message_field").innerHTML = "";
    }
}


function complete_refactor() {
    marker.style.visibility = "hidden";

    let text = input.value;
    let jdata = {
        "id": id,
        "text": text
    };
    let data = JSON.stringify(jdata);
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("PUT", window.location.href, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(data);

    for (let i = 0; i < table.rows.length; i++) {
        let row = table.rows[i];
        if (row.cells[0].innerHTML == id) {
            row.cells[2].innerHTML = "......................................" + text;
            break;
        }
    }

    end_refactor();
}


function event_listener_function(myself) {
    let row = myself.parentNode;

    if (row.cells[1].innerHTML == recipient_name) {
        console.log("return")
        return;
    }

    marker.style.visibility = "visible";
    marker_end.style.visibility = "visible";
    id = row.cells[0].innerHTML;
    let message = row.cells[2].innerHTML;
    input.value = message.substring(prefix.length, message.length);
}


function end_refactor() {
    marker.style.visibility = "hidden";
    marker_end.style.visibility = "hidden";
    id = 0;
    input.value = "";
}



