<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<script>
function submitForm(mode){
	fm.action="d_write.jsp?mode="+mode;
	fm.submit();
}
</script>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
<%!
private String htmlchar_derun(String str)
	{
	  String tmp=str.replaceAll("<br>","\n").replaceAll("&nbsp;"," ");
	  return tmp;
	}
%>
</head>
<body>
<%
	Calendar cal= Calendar.getInstance();
	SimpleDateFormat dformat= new SimpleDateFormat("yyyy-MM-dd");
	String toDay=dformat.format((cal.getTime()));
%>
<center>
<h2>회원가입</h2>
<form method=post name=fm>
<table cellspacing=1 width=750 border=1 style="table-layout:fixed">
<table width=650 border=1 cellspacing=0 cellpadding=5>
	<tr>
		<td><b>아이디</b></td>
		<td><input type=text name=id maxlength=20 value='' required></td>
	</tr>
	<tr>
		<td><b>비밀번호</b></td>
		<td><input type=password name=password  maxlength=20 value='' required></td>
	</tr>
	<tr>
		<td><b>이름</b></td>
		<td><input type=text name=name  value='' required></td>
	</tr>
	<tr>
		<td><b>생년월일</b></td>
		<td><input type=date name=birth value='<%=toDay%>'></td>
	</tr>
	<tr>
		<td><b>주소</b></td>
		<td><input type=text name=addr maxlength=50 value=''></td>
	</tr>
</table>
<input type=hidden name=today value='<%=toDay%>'>
<table width=550>
	<tr>
		<td width=500></td>
		<td><input type=button value='취소' onclick="location.href='main.html'"></td>
		<td><input type=button value='가입' onclick="submitForm('join')"></td>
	</tr>
</table>
<form>
</center>
</body>
</html>