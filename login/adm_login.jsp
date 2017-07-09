<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
</head>
<body>
<%
	String jump=request.getParameter("jump");
%>
<center>
<h2>로그인</h2>
<form method="post" action="adm_loginck.jsp">
<table border=1>
	<tr>
		<td>아이디</td>
		<td><input type=text maxlength=20 name=id></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type=password maxlength=20 name=passwd></td>
	</tr>
	<tr>
		<td colspan=2 align=center><input type=submit value=전송></td>
	</tr>
</table>
<input type=hidden name=jump value='<%=jump%>'>
</form>
</center>
</body>
</html>