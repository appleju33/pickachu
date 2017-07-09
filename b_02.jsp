<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<script>
function arriveHide(){
	var x= document.getElementById("arrive_date");
	x.style.display='none';
}
function arriveShow(){
	var x= document.getElementById("arrive_date");
	x.style.display='';
}
</script>
<%
	String loginOK=null;
	String jumpURL="./login/adm_login.jsp?jump=../b_01.jsp";
	
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
<form method=post name=fm action='b_03.jsp'>
<table border=1>
<tr>
<td colspan=2>
<input type=radio name=how onclick='arriveShow();' value='wangbuk'checked>왕복
<input type=radio name=how onclick='arriveHide();' value='pendo'>편도 
</td>
</tr>
<tr>
<td colspan=2>
출발지
<select name=start_place>
<option value="김포" selected="selected">김포</option>
<option value="제주">제주</option>
</td>
</tr>
<tr>
<td colspan=2>
도착지
<select  name=arrive_place>
<option value="제주" selected="selected">제주</option>
<option value="김포">김포</option>
</td>
</tr>
<tr>
<td>
<%String resv_date=request.getParameter("resv_date");
DateFormat dft=new SimpleDateFormat("yyyy-MM-dd");
java.util.Date date=dft.parse(resv_date);
Calendar cal=Calendar.getInstance();
cal.setTime(date);
cal.add(Calendar.DATE,1);
String next_date=dft.format(cal.getTime());
%>
출발날<input type=date name=start_date value='<%=resv_date%>' readonly>
</td>
<td id=arrive_date>
오는날<input type=date name=arrive_date min=<%=resv_date%> value='<%=next_date%>'>
</td>
</tr>
<tr>
<td colspan=2>
인원<input type=number min=1 max=60 name=resv_num value='1'>
</td>
</tr>
</table>
<table>
<tr>
<td>
</td>
<td>
<input type=button value="취소" onclick="location.href='b_01.jsp'">
</td>
<td>
<input type=submit value="다음">
</td>
</tr>
</table>
</form>
</center>
</body>
</html>