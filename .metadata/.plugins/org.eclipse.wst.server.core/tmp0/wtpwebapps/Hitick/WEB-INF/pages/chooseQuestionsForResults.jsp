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
<form:form action="/Hitick/view-result" method="post" modelAttribute="emptyQuestionsBean" onsubmit="return validateRadio()">
<table>
<c:forEach items="${questionsList}" var="questionsBean">

<c:if test="${questionsBean.result==0 }">
<tr>
<td><h2>Select </h2><input type="radio" id="id" name="id" value="${questionsBean.id}">
<td><h2>Question : </h2><td><h3>${questionsBean.question }</h3>
<input type="hidden" name="refGroupId" value="${groupId }">
<tr>
<td><td><input type="submit" value="View Result">
</c:if>
<c:if test="${questionsBean.result!=0 }">
<tr>
<td>
<td><h2>Question : </h2><td><h3>${questionsBean.question } :: </h3>
<td>
<h3>
<c:if test="${questionsBean.result==1 }">YES!!</c:if>
<c:if test="${questionsBean.result==2 }">NO!!</c:if>
<c:if test="${questionsBean.result==3 }">TIE!!</c:if>
</h3>
</c:if>
</c:forEach>

</table>
</form:form>
</c:if>
<c:if test="${questionStatus=='isEmpty' }">
<h3>Sorry,There hasn't been any question in this group yet!!</h3>
<br><br>
<h4><a href="/Hitick">Home</a></h4>
</c:if>
</body>
</html>