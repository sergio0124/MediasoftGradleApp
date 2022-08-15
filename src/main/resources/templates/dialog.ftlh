<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dialog page</title>
    <link rel="stylesheet" href="dialog_css.css">
</head>
<body>


<h1>
    Dialog with ${recipient_name}
</h1>


<h1>
    ${recipient_name}...................${username}
</h1>


<table id="table_messages">
    <#if messages??>
        <#list messages as message>

            <tr>
                <#if message.sender.username = username>
                    <td style="visibility: hidden">${message.id}</td>
                    <td style="visibility: hidden">${message.sender.username}</td>
                    <td>......................................${message.text}</td>
                <#else>
                    <td style="visibility: hidden">${message.id}</td>
                    <td style="visibility: hidden">${message.sender.username}</td>
                    <td>${message.text}</td>
                </#if>
                <td>
                    <img
                            <#if message.emoji??>
                                src="images/${message.emoji}.jpg" alt=""
                            <#else>
                                src="images/white.jpg" alt=""
                            </#if>
                    >
                </td>
            </tr>

        </#list>

    </#if>

</table>

<div>
    <span>
        <h3 style="visibility: hidden" id="refactor_marker">Редактирование сообщения</h3>
        <a style="visibility: hidden" id="refactor_end"> Отменить редактирование</a>
    </span>
</div>


<div>
    <input name="text" placeholder="Текст сообщения" type="search" , id="message_field">
    <button id="message_button">Отправить</button>
</div>


<div id="context_menu" class="context_menu">
    <div class="item">Удалить</div>
    <div class="item">Изменить</div>
    <div class="emoji-container">
        <img class="item" src="images/sad.jpg" alt="">
        <img class="item" src="images/smile.jpg" alt="">
        <img class="item" src="images/poop.jpg" alt="">
    </div>
</div>


<div id="context_menu_only_emoji" class="context_menu">
    <div class="emoji-container">
        <img class="item" src="images/sad.jpg" alt="">
        <img class="item" src="images/smile.jpg" alt="">
        <img class="item" src="images/poop.jpg" alt="">
    </div>
</div>


</body>


<script type="text/javascript" src="dialog_script.js"></script>

</html>