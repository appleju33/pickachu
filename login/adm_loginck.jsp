<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
<html>
<head>
<script>
function reload(jump){
	parent.top_frame.location.href="../top.jsp";
	parent.main.location.href=jump;
}
</script>
</head>
<body>
<center>
<%
	request.setCharacterEncoding("utf-8");
	String jump =request.getParameter("jump");
	String id =request.getParameter("id");
	String pass=request.getParameter("passwd");
	
	boolean bPassCheck=false;
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	ResultSet rset = stmt.executeQuery("select * from customer where id='"+id+"' and password='"+pass+"'");//admin의 pass를 확인
	if(rset.next()){//값이 있으면 bPassCheck가 ture로 바꾼다.
		bPassCheck=true;
	}
	else{//없으면 그대로 false
		bPassCheck=false;
	}
	
	
	if(bPassCheck){//bPassCheck의 값이 true면 세션을 설정한다.
		session.setAttribute("login_OK","yes");
		session.setAttribute("login_id",id);
		out.println("<script>reload('"+jump+"');</script>");
	}
	else{
		out.println("<h2>아이디 또는 패스워드 오류.</h2>");
		out.println("<input type=button value='로그인' onclick=\"location.href='adm_login.jsp?jump="+jump+"'\">");
	}
%>
</center>
</body>
</html>