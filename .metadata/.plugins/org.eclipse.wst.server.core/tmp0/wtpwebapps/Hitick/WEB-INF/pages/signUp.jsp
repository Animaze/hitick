<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hitick</title>
</head>
<body>
<h2>Welcome </h2>
<br>

<form:form action="/Hitick/register-user" method="post" modelAttribute="userBean">
<table>
<tr>
<td>First Name : <td><input type="text" name = "firstName" placeholder="First Name">
<tr>
<td>Last Name : <td><input type="text" name = "LastName" placeholder="Last Name">
<tr>
<td>Mobile No : <td><input type="text" name = "mobileNumber" placeholder="Mobile Number">
<tr>
<td>Password : <td><input type="password" name = "password" placeholder="password">
<tr>
<td>Email Id : <td><input type="text" name = "email" placeholder="Email Id">
<tr>
<td>Institution : <td><input type="text" name = "institution" placeholder="Institution">
<tr><td><td><input type="submit" value="Submit">
</table>
</form:form>

</body>
</html>