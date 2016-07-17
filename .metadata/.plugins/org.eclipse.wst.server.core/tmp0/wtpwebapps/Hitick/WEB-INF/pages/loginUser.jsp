<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hitick</title>
</head>
<body>
<h2>Welcome </h2>
<br>

<form action="/Hitick/login-user" method="post">
<table>
<tr>
<td>Mobile Number : <td><input type="text" name = "mobileNumber" placeholder="Mobile Number">
<tr>
<td>Password : <td><input type="password" name = "password" placeholder="password">
<tr><td><td><input type="submit" value="Submit">
</table>
</form>

</body>
</html>