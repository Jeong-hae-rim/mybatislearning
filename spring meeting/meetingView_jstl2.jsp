<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vo.MeetingVO, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meeting Schedule</title>
<style>
td {
	border-bottom: 1px dotted green;
}

tr:hover {
	background-color: pink;
	font-weight: bold;
}

td:nth-child(3) {
	width: 400px;
}
</style>
</head>
<Script>
function f1(id){
	if (confirm("정말 삭제하시겠습니까??") == true){ 

		location.href="http://localhost:8000/springedu/meeting?id="+id;

	}else{ 
	    return;
    }
}
</Script>
<body>
	<c:if test ="${!empty list}">
	<h2>미팅스케줄(JSTL)</h2>
	<hr>
		<table>
		<c:forEach var="vo" items="${list}">
			<tr>
				<td>${vo.name}</td>
				<td>${vo.title}</td>
				<td>${vo.meetingDate}</td>
				<td><img src="/mvc/images/trash.jpg" width=30
					onclick="f1(${vo.id})"></td>
			</tr>
		</c:forEach>
		</table>
		</c:if>

	<c:if test="${!empty msg}">
		<h2>${msg}</h2>
	</c:if>
	<hr>
	<a href="/springedu/resources/meetingForm2.html">미팅 홈 화면으로 가기</a>
</body>
</html>

