<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello from Mine</title>
</head>
<body>

<#if httpServletRequest??>
    <p>My crazy hello, ${httpServletRequest.remoteUser}!</p>
</#if>

    <p>Log in <a href="/login"> here</a></p>

    <p>Registration <a href="/registration"> here</a></p>

<fieldset>
    <#list users as user>
        ${user.username}: ${user.password}
    </#list>
</fieldset>

</body>
</html>