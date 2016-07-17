<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group List</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
</script>
<script>
function validateRadio(){
    var radioStatus = $('input[type=radio]:checked').size() > 0;
    
    if(!radioStatus)
        alert("Select some Radio first!");
    return radioStatus;
}	
</script>
<style>
table td tr {
	background:#fcc;
	color:#000;
	border:1px;
}
</style>
<body>
<c:if test="${listOfGroupsSize!=0 }">
<form action="/Hitick/post-in-group" method="get" onsubmit="return validateRadio()">
		<table>

			<tr>
				<th>Select</th>
				<td>||</td>
				<th>Group Name</th>
				<td>||</td>
				<th>No of Members</th>
			
			</tr>

			<c:forEach items="${listOfGroups}" var="group">
				<tr>
					<td>
						<input type="radio" id="groupId" name="groupId" value="${group.id}">
					</td>
					<td>||</td>
					<td>${group.groupName}</td>
					<td>||</td>
					<td>${group.memberCount}</td>

			</c:forEach>
			
			<tr>
				<td>
					<input type="submit" value="Post in the selected group">
				</td>
			</tr>
			

		</table>
	</form>
</c:if>
<c:if test="${listOfGroupsSize==0 }">
<h3>Sorry, you are not the admin of any group presently , so you cant post a question</h3>
<br><br>
<h4><a href="/Hitick">Home</a></h4>
</c:if>
</body>
</html>