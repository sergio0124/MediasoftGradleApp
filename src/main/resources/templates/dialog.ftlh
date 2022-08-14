<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dialog page</title>
</head>
<body>

<div>
    <#if messages??>
        <fieldset>
            <#list messages as message>
                ${message.sender.username} to ${message.recipient.username}: ${message.text}
            </#list>
        </fieldset>
    <#elseif error??>
        <p>${error}</p>
    </#if>
</div>

<div>
    <input name="text" placeholder="Текст сообщения" type="search" , id="message_field">
    <button id="message_button">Поиск</button>
</div>

</body>
</html>