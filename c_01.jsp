<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<%!
private String htmlchar_run(String str)//db에 저장하기 전에 text형태로 넣는것 이기 때문에 에디팅을 해준다.
	{
	  String tmp=str.replace("\t","&nbsp;&nbsp;&nbsp;&nbsp;");
	  tmp=tmp.replace(" ","&nbsp;");
	  tmp=tmp.replace("<","&lt;");
	  tmp=tmp.replace(">","&gt;");
	  tmp=tmp.replace("\n","<br>");
	  return tmp;
	};
%>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
</head>
<body>
<center>
<%
		
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	String id=(String)session.getAttribute("login_id");
	String utfId=new String(id.getBytes("8859_1"),"utf-8");
	Calendar cale=Calendar.getInstance();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
	
	String query="select count(*) from airReservation where id='"+utfId+"'";
	ResultSet rset = stmt.executeQuery(query);
	boolean rsetTure=false;
	if(rset.next()){
		if(rset.getInt(1)>0){
			rsetTure=true;
		}
	}
	query="select * from airReservation where id='"+utfId+"' and resv_date>='"+sdf.format(cale.getTime())+"'";
	rset = stmt.executeQuery(query);
	
	
	if(rsetTure){
		out.println("<h2>"+id+"님의 예약 상황입니다.</h2>");
		out.println("<table border=1>");
		out.println("<tr>");
		out.println("<td>예약날짜</td>");
		out.println("<td>좌석번호</td>");
		out.println("<td>출발지</td>");
		out.println("<td>도착지</td>");
		out.println("<td>출발시간</td>");
		out.println("<td>도착시간</td>");
		out.println("</tr>");
		while(rset.next()){
			out.println("<tr>");
			out.println("<td>"+rset.getString(1)+"</td>");
			out.println("<td>"+rset.getString(2)+"</td>");
			out.println("<td>"+rset.getString(7)+"</td>");
			out.println("<td>"+rset.getString(4)+"</td>");
			out.println("<td>"+rset.getString(5)+"</td>");
			out.println("<td>"+rset.getString(6)+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
	}
	else{
		out.println("예약이 없습니다.");
	}	
	rset.close();
	stmt.close();
	conn.close();
%>
</center>
</body>
</html>