<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vo.NewsVO, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<style>
* {
	text-align: center;
}

html {
	background: url('resources/images/default.jpg') no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}

body {

margin-top: 100px;

}

#b1 {
	background-color: #58ACFA;
	color: #FFFFFF;
}

#b2 {
	height: 200px;
	border-collapse: collapse;
	opacity: 0.8;
	background-color: #E6E6E6;
}

#b3:hover {
	font-weight: bold;
}

#b4 {
	border-bottom: 1px solid #BDBDBD;
}

#b5 {
	margin-left: 640px;
}

td:nth-child(3) {
	width: 400px;
}
</style>


<title>NEWS</title>
</head>
<body>
	<%
		ArrayList<NewsVO> list = (ArrayList<NewsVO>) request.getAttribute("list");
    	NewsVO read = (NewsVO) request.getAttribute("read");
	
		if (list != null) {
	%>

	<h1 style="color : #ffffff; text-shadow: 1px 2px 2px #33334d; text-align: center;">뉴스 게시판</h1>
	<br><br><br>

	<DIV id="search" style="margin-left: 350px;">
		<FORM method='GET' action='http://localhost:8000/springnews/newsmain'>
			<SELECT name='searchtype'>
				<OPTION value='writer'>작성자</OPTION>
				<OPTION value='title'>제목</OPTION>
				<OPTION value='content'>내용</OPTION>
			</SELECT> <input type="search" style="width: 250px" name="key"
				placeholder="검색할 단어를 입력하세요">
			<button type='submit'>검색</button>
		</FORM>
	</DIV>
	<br>
	<table id="b1" style="margin: auto;">
		<tr>
			<td style="width: 50px; text-align: center;">번호</td>
			<td style="width: 290px; text-align: center;">제목</td>
			<td style="width: 100px; text-align: center;">작성자</td>
			<td style="width: 200px; text-align: center;">작성일</td>
			<td style="width: 50px; text-align: center;">조회수</td>
		</tr>
	</table>
	<table id="b2" style="margin: auto;">
		<%
			for (NewsVO vo : list) {
		%>
		<tr id="b3">
			<td id="b4" style="width: 50px; text-align: center;"class='<%=vo.getId()%>'>
			    <%=vo.getId()%></td>
			<td id="b4" style="width: 300px; text-align: center;" class='<%=vo.getId()%>'
				            onclick="location.href='http://localhost:8000/springnews/newsmain?action=read&id=<%=vo.getId()%>'">
				<%=vo.getTitle()%></td>
			<td id="b4" style="width: 100px; text-align: center;" class='<%=vo.getId()%>'
				            onclick="location.href='http://localhost:8000/springnews/newsmain?searchtype=writer&key=<%=vo.getWriter()%>'">
				<%=vo.getWriter()%></td>
			<td id="b4" style="width: 200px; text-align: center;" class='<%=vo.getId()%>'>
			    <%=vo.getWritedate()%></td>
			<td id="b4" style="width: 50px; text-align: center;" class='<%=vo.getId()%>'>
			    <%=vo.getCnt()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
		if (request.getAttribute("msg") != null) {
	%>
	<script>
		alert('${ msg }');
	</script>

	<%
		}
	%>
	<br>
	<button id="b5" onclick="displayDiv();">뉴스 작성</button>
	<script>
		function displayDiv() {
			document.getElementById("write").style.display = 'block';
		}
		function back() {
			document.getElementById("write").style.display = 'none';
		}

		function back2() {
			document.getElementById("update").style.display = 'none';
		}

		function del(writer) {
			location.href = "http://localhost:8000/springnews/newsdelete?action=delete&id="+id+"&writer="+writer;
		}

		function displayUpdateForm(cv) {
			var doms = document.getElementsByClassName(cv)
			document.getElementById("update").style.display = 'block';
		}
	</script>
	<div id="write" style="display: none">
		<hr style="width: 50%;">
		<h2 id="divT" style="color : #ffffff;">뉴스 작성</h2>
		<form method="post" action="http://localhost:8000/springnews/newsinsert">
			<input type="hidden" name="action" value="insert"> 
			<input id="n_writer" style="width: 500px; text-align:left;" type="text" name="writer"
			       placeholder="작성자명을 입력하세요"><br> 
			<input id="n_title" style="width: 500px; text-align:left;" type="text" name="title"
				placeholder="제목을 입력하세요"> <br>
			<textarea id="n_content" style="width: 500px; height: 200px; text-align:left;" name="content" 
			    placeholder="내용을 입력하세요"> </textarea>
			<br> 
			<input type="submit" value="저장"> 
			<input type="reset" value="재작성"> 
			<input onclick="back();" type="button" value="취소">
		</form>
	</div>

	<div id="update" style="display: none">
		<hr style="width: 50%;">
		<h2 id="divT" style="color : #ffffff;">뉴스 내용</h2>
		<form method="post" action="http://localhost:8000/springnews/newsupdate">
			<input type="hidden" name="action" value="update"> 
			   <input type="hidden" name="id" value="${read.id}"> 
			   <input id="writer" style="width: 500px; text-align:left;" type="text" name="writer" value="${read.writer}"> <br> 
			   <input id="title" style="width: 500px; text-align:left;" type="text" name="title" value="${read.title}"><br>
			<textarea id="content" style="width: 500px; height: 200px; text-align:left;" name="content">${read.content}</textarea>
			<br> 
			<input type="submit" value="수정"> 
			<input onclick="del('${read.writer}');" type="button" value="삭제"> 
			<input onclick="back2();" type="button" value="닫기">
		</form>
	</div>
</body>
<script>
var id = <%=request.getParameter("id")%>
	if (id != null) {
		document.getElementById("update").style.display = 'block';
	}
</script>
</html>