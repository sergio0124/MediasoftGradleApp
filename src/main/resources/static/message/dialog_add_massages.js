let button = document.getElementById("load_more_button");
button.addEventListener("click", function () {

    let count = table.children.item(0).children.length;
    let recipient_name = new URL(window.location.href).searchParams.get("recipient_name");

    let jdata = {
        "recipientName": recipient_name,
        "rowNumber": count
    };
    let data = JSON.stringify(jdata);

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "/message/dialog/loadMessages", false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(data);

    let JsonResult = JSON.parse(xmlHttp.responseText);
    if (xmlHttp.status == 200) {
        for (let i = JsonResult.length - 1; i >= 0; i--) {
            let item = JsonResult[i];

            let row = table.insertRow(0);

            let cell = row.insertCell();
            cell.innerHTML = item.id;
            cell.style.visibility = "hidden";

            cell = row.insertCell();
            cell.innerHTML = item.sender.username;
            cell.style.visibility = "hidden";

            cell = row.insertCell();
            if (item.recipient.username != recipient_name) {
                cell.innerHTML = item.text;
            } else {
                cell.innerHTML = "......................................" + item.text;
            }

            cell.addEventListener("contextmenu", function (event) {
                event_listener_function(event, cell)
            });

            cell = row.insertCell();
            if (item.emoji == null) {
                cell.innerHTML = "<img src=\"images/white.jpg\" alt=\"\">";
            } else {
                cell.innerHTML = "<img src=\"images/" + item.emoji.toLowerCase() + ".jpg\" alt=\"\">";
            }

        }
    }
});