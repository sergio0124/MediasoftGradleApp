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


<div>
    <#if messages??>
        <table id="table_messages">
            <#list messages as message>

                <#if message.sender.username = username>
                    <tr>
                        <td style="visibility: hidden">${message.id}</td>
                        <td style="visibility: hidden">${message.sender.username}</td>
                        <td>......................................${message.text}</td>
                    </tr>
                <#else>
                    <tr>
                        <td style="visibility: hidden">${message.id}</td>
                        <td style="visibility: hidden">${message.sender.username}</td>
                        <td>${message.text}</td>
                    </tr>
                </#if>

            </#list>
        </table>
    <#elseif error??>
        <p>${error}</p>
    </#if>
</div>


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


<div id = "context_menu">
    <div class="item">Удалить</div>
    <div class="item">Изменить</div>
    <div>
</div>

</body>


<script type="text/javascript" src="dialog_script.js"></script>

</html>