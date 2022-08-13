<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dialog page</title>
</head>
<body>


<#if messages??>
    <fieldset>
        <#list messages as message>
            ${message.sender.username} to ${message.recipient.username}: ${message.text}
        </#list>
    </fieldset>
<#elseif error??>
    <p>${error}</p>
</#if>



</body>
</html>