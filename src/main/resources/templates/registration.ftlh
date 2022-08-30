<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Log in</title>
</head>
<body>

<#if error??>
    <p>
        ${error}
    </p>
</#if>

</div>
<form action="users/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="sign In"/></div>
</form>
</body>

</html>