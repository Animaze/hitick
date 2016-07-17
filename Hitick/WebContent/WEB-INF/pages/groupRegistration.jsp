<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hitick</title>
</head>
<body>
<h2>Welcome </h2>
<br>

<form:form action="/Hitick/save-group-details" method="post" modelAttribute="groupBean">
<table>
<tr>
<td>Group Name: <td><input type="text" name = "groupName" placeholder="Group Name">
<tr>
<td>Group password : <td><input type="password" name = "groupPassword" placeholder="Group Password">
<tr>
<td><td><input type="hidden" name = "refAdminId" value="${sessionScope.id}">

<tr><td><td><input type="submit" value="Submit">
</table>
</form:form>

</body>
</html>