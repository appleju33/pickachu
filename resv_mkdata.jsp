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
	stmt.execute("create table redsv (name varchar(20), "+   //성명	
				"resv_date date not null, "+  //예약일
				"room int not null, "+ //예약방 1:VIP룸 2:일반룸 3:합리적인룸
				"addr varchar(100), "+  //주소
				"telnum varchar(20), "+ //연락처
				"in_name  varchar(20), "+ //입금자명
				"comment  text, "+ //남기실말
				"write_date date, "+// 예약한(이 글을 쓴) 날짜
				"processing int, "+//현재 진행 1:예약완료 2: 입금완료(예약확정) 3: 환불요청 4:...
				"primary key (resv_date,room) )"+  // 예약일과 룸을 합쳐서 DB의 키로 사용
				"DEFAULT CHARSET=utf8;");
	
	stmt.close();
	conn.close();
%>
</body>
</html>