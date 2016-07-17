<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hitick</title>
</head>
<body>
<h2 >Welcome!!</h2>
<c:if test="${sessionScope.loginStatus=='loggedIn'}"><h4 align="right"><a href="/Hitick/logout-user">Logout</a></h4></c:if>
<br>
<c:if test="${sessionScope.loginStatus=='loggedOut'}">
<h4><a href="/Hitick/loginUserPage">Login</a></h4>
<h4><a href="/Hitick/signUpPage">Sign Up</a></h4>
</c:if>
<c:if test="${sessionScope.loginStatus=='loggedIn'}">
<h4>Hi ${sessionScope.name}</h4>

<br><br>
<h4><a href="/Hitick/register-group">Create a group</a></h4>
<h4><a href="/Hitick/join-group">Join a Group</a></h4>
<h4><a href="/Hitick/select-group-for-question-posting">Post a Question</a></h4>
<h4><a href="/Hitick/select-group-for-voting-on-question">Vote in a Group / Leave a Group</a></h4>
<h4><a href="/Hitick/voting-results">Voting Results</a></h4>
<h4><a href="/Hitick/return-json">Test Json</a></h4>
<h4><a href="/Hitick/members-in-groups">Members</a></h4>


</c:if>
</body>
</html>