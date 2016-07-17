<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<html>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Members</title>
</head>
<body>
<form action="/Hitick/kick-members" method="get" onsubmit="return validateRadio()">
		
<table>

			<tr>
			    <c:if test="${sessionScope.id==adminId }">
			    <th>Select
			    </c:if>
			    
			    <th>Serial No
				<td>||
				<th>Member's Name
				<td>||
				<th>Member's Email
				<td>||
				<th>Member's Mobile Number
				<td>||
				<th>Member's Institution
			</tr>

			<c:forEach items="${listOfMembers}" var="member" varStatus="sNo">
				<tr>
				
				<c:if test="${sessionScope.id==adminId }">
				<td><input type="radio" id="memberId" name="memberId" value="${member.id}">
				</c:if>	
				    <td>${sNo.count}</td>
					<td>||
					<td>${member.firstName}</td>
					<td>||
					<td>${member.email}</td>
					<td>||
					<td>${ member.mobileNumber}
					<td>||
					<td>${ member.institution}
				
				</tr>

			</c:forEach>
			<c:if test="${sessionScope.id==adminId }">
		
		<tr><td><td><td><td><td><td><td><td><td><td><td>
		<input type="submit" value="Kick"><input type="hidden" value="${groupId }" name="groupId">
		</c:if>
		</table>
	</form>
</body>
</html>