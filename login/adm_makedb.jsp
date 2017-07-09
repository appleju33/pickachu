<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
<html>
<head>
</head>
<body>
<h1>Make table</h1>
<%
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	
	/*try{
		stmt.execute("drop table gongji2");
		out.println("drop table gongji2 OK<br>");
	}catch(Exception e){
		out.println("drop table gongji2 NOT OK<br>");
		out.println(e.toString());
	} */
	stmt.execute("create table adminfo (id varchar(20) primary key not null, "+
				"pass varchar(20)) DEFAULT CHARSET=utf8;");
	stmt.execute("insert into adminfo(id,pass) values('admin','1111')");
	stmt.close();
	conn.close();
%>
</body>
</html>