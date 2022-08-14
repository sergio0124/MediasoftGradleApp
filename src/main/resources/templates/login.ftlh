<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Log in</title>
</head>
<body>

<#if RequestParameters.error??>
    Invalid username and password.
<#elseif RequestParameters.logout??>
    You have been logged out.
<#elseif status??>
    <p>
        ${status}
    </p>
</#if>

</div>
<p>Registration <a href="/registration"> here</a></p>

<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="sign In"/></div>
</form>
</body>
</html>