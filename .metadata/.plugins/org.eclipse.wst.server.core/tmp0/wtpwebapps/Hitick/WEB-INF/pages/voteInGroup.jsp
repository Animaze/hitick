<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hitick</title>
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
<body>
<h2>Welcome </h2>
<br>
<c:if test="${questionStatus!='isEmpty' }">
<form:form action="/Hitick/save-group-question-voting-mapping-details" method="post" modelAttribute="newQuestionsBean" onsubmit="return validateRadio()">
<table>
<c:forEach items="${questionsList}" var="questionsBean">
<tr>
<td><h2>Select </h2><input type="radio" id="id" name="id" value="${questionsBean.id}">
<td><h2>Question : </h2><td><h3>${questionsBean.question }</h3>
</c:forEach>
<tr>
<td><td><input type="submit" value="YES" name="choice">
<td><td><input type="submit" value="NO" name="choice">
<input type="hidden" name="refGroupId" value="${groupId}">
</table>
</form:form>
</c:if>
<c:if test="${questionStatus=='isEmpty' }">
<h3>Sorry,There is no question for voting in this group presently , so you cant vote!!</h3>
<br><br>
<h4><a href="/Hitick">Home</a></h4>
</c:if>
</body>
</html>