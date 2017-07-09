<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
<script>
function submitForm(mode){
	if(mode=="delete"){
		fm.action="d_write.jsp?mode="+mode;
		fm.submit();
	}		
	
}
</script>
<%
	String loginOK=null;
	String jumpURL="./login/adm_login.jsp?jump=../d_02.jsp";
	
	loginOK=(String)session.getAttribute("login_OK");//로그인이 되어있는지 알기위한 세션값
	if(loginOK==null){//세션이 존재하지 않거나 값이 yes가 아니라면 밑에 부분을 보여주지 않는다.
		response.sendRedirect(jumpURL);
		return;
	}else if(!(loginOK.equals("yes"))){
		response.sendRedirect(jumpURL);
		return;
	}
%>
</head>
<body>
<center>
<h2>회원 탈퇴</h2>
<form method=post name=fm autocomplete="on">
<table border=1>
	<tr>
		<td>비밀번호</td>
		<td><input type=password maxlength=20 name=password></td>
	</tr>
</table>
<table width=550>
	<tr>
		<td width=500></td>
		<td><input type=button value='탈퇴' onclick="submitForm('delete')"></td>
	</tr>
</table>
</form>
</center>
</body>
</html>