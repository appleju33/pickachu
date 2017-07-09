<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<script>
function submitForm(){
	
	top.location.reload();
}
</script>
</head>
<body>
<center>
<h2>로그아웃</h2>
<%
	session.invalidate();
	out.println("<h2>로그아웃 되었습니다.</h2>");
	out.println("<input type=button value='메인페이지' onclick='submitForm()';>");
%>
</center>
</body>
</html>