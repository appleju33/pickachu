<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<script>
function getStyle(obj, jsprop, cssprop) {
        if (obj.currentStyle) {
                return obj.currentStyle[jsprop];

        } else if (window.getComputedStyle) {
                return document.defaultView.getComputedStyle(obj, null).getPropertyValue(cssprop);

        } else {
                return null;
        }
}
function chose_time(id,start_time,arrive_time){
	if(id.match("one")){
		fm.start_time.value=start_time;
		fm.arrive_time.value=arrive_time;
		var arr=new Array("one_td1","one_td2","one_td3","one_td4","one_td5");
		for(var i=0; i<arr.length; i++){
			var obj = document.getElementById(arr[i]);
			if(arr[i]==id){
				if(getStyle(obj,"backgroundColor", "background-color").match("81")){
					obj.style.backgroundColor="#FFFFFF";
				}else{
					obj.style.backgroundColor="#51E4D5";
				}
			}else{
				obj.style.backgroundColor = "#FFFFFF";
			}
		}
	}
	else{
		fm.start_time2.value=start_time;
		fm.arrive_time2.value=arrive_time;
		var arr=new Array("td1","td2","td3","td4","td5");
		for(var i=0; i<arr.length; i++){
			var obj = document.getElementById(arr[i]);
			if(arr[i]==id){
				if(getStyle(obj,"backgroundColor", "background-color").match("81")){
					obj.style.backgroundColor="#FFFFFF";
				}else{
					obj.style.backgroundColor="#51E4D5";
				}
			}else{
				obj.style.backgroundColor = "#FFFFFF";
			}
		}
	}
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
<%
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	String start_place=request.getParameter("start_place");
	String arrive_place=request.getParameter("arrive_place");
	int resv_num=Integer.parseInt(request.getParameter("resv_num"));
	String start_date=request.getParameter("start_date");
	String arrive_date=request.getParameter("arrive_date");
	String utfStart_place=new String(start_place.getBytes("8859_1"),"utf-8");
	String utfArrive_place=new String(arrive_place.getBytes("8859_1"),"utf-8");
	ResultSet rset = stmt.executeQuery("select start_time,arrive_time from airSchedule where start_place='"+utfStart_place+"' and arrive_place='"+utfArrive_place+"'");
	%>
<center>
<form method=post name=fm action="b_04.jsp">
<h2><%=utfStart_place%>-><%=utfArrive_place%></h2>
<table border=1>
<tr>
<%
int cnt=1;
while(rset.next()){
	out.print("<td onclick=chose_time('one_td"+cnt+"','"+rset.getString(1)+"','"+rset.getString(2)+"') id='one_td"+cnt+"'>");
	out.print("<table>");
	out.print("<tr>");
	out.print("<td>출발시간:"+rset.getString(1)+"</td>");
	out.print("</tr>");
	out.print("<tr>");
	out.print("<td>도착시간:"+rset.getString(2)+"</td>");
	out.print("</tr>");
	out.print("</table>");
	out.print("</td>");
	cnt++;
	}%>
</tr>
</table>
<br>
<%
String how=request.getParameter("how");
if(how.equals("wangbuk")){
	rset = stmt.executeQuery("select start_time,arrive_time from airSchedule where start_place='"+utfArrive_place+"' and arrive_place='"+utfStart_place+"'");%>
<h2><%=utfArrive_place%>-><%=utfStart_place%></h2>
<table border=1>
<tr>
<%
cnt=1;
while(rset.next()){
	out.print("<td onclick=chose_time('td"+cnt+"','"+rset.getString(1)+"','"+rset.getString(2)+"') id='td"+cnt+"'>");
	out.print("<table>");
	out.print("<tr>");
	out.print("<td>출발시간:"+rset.getString(1)+"</td>");
	out.print("</tr>");
	out.print("<tr>");
	out.print("<td>도착시간:"+rset.getString(2)+"</td>");
	out.print("</tr>");
	out.print("</table>");
	out.print("</td>");
	cnt++;
	}%>
</tr>
</table>

<%}
rset.close();
stmt.close();
conn.close();%>
<input type=hidden name=start_place value='<%=utfStart_place%>'>
<input type=hidden name=arrive_place value='<%=utfArrive_place%>'>
<input type=hidden name=start_date value='<%=start_date%>'>
<input type=hidden name=arrive_date value='<%=arrive_date%>'>
<input type=hidden name=resv_num value='<%=resv_num%>'>
<input type=hidden name=start_time value=''>
<input type=hidden name=arrive_time value=''>
<input type=hidden name=start_time2 value=''>
<input type=hidden name=arrive_time2 value=''>
<input type=hidden name=how value='<%=how%>'>
<table>
<tr>
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