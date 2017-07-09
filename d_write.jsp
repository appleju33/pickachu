<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
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
	
	try{
	String mode=request.getParameter("mode");
	String sql="";
	if(mode.equals("join")){
		String name=htmlchar_run(request.getParameter("name"));
		String id=htmlchar_run(request.getParameter("id"));
		String password=htmlchar_run(request.getParameter("password"));
		String birth=request.getParameter("birth");
		String addr=htmlchar_run(request.getParameter("addr"));
		String utfName=new String(name.getBytes("8859_1"),"utf-8");
		String utfAddr=new String(addr.getBytes("8859_1"),"utf-8");
		String utfId=new String(id.getBytes("8859_1"),"utf-8");
		String utfPassword=new String(password.getBytes("8859_1"),"utf-8");
		String today=request.getParameter("today");
		
		sql="insert into customer(id,password,name,birth,addr,join_date,update_date) values('"+utfId+"','"+utfPassword+"','"+utfName+"','"+birth+"','"+
		""+utfAddr+"','"+today+"','"+today+"')"; 
		stmt.execute(sql);
		out.println("<h1>정상적으로 가입 되었습니다.</h1>");
	}
	else if(mode.equals("delete")){
		String loginId=(String)session.getAttribute("login_id");
		String password=htmlchar_run(request.getParameter("password"));
		String utfPassword=new String(password.getBytes("8859_1"),"utf-8");
		ResultSet rset=stmt.executeQuery("select * from redsv where id='"+loginId+"' and password="+utfPassword);
		if(rset.next()){
		sql="delete from customer where id='"+loginId+"' and password='"+utfPassword+"'";
		stmt.execute(sql);
		out.println("<h1>정상적으로 탈퇴 되었습니다.</h1>");
		session.removeAttribute("login_id");
		session.removeAttribute("login_OK");
		}
	}
	
	out.println("<input type=button value='예약상황' onclick=location.href='main.jsp'>");
	
		
	
	stmt.close();
	conn.close();
	}catch(SQLException e){
	if(e.getErrorCode()==1062){ 
		/* session.setAttribute("name",utfName);
		session.setAttribute("date",date);
		session.setAttribute("room",room);
		session.setAttribute("addr",utfAddr);
		session.setAttribute("tel",telnum);
		session.setAttribute("in_name",utfIn_name);
		session.setAttribute("comment",utfcomment);
		session.setAttribute("write_date",write_date);
		session.setAttribute("processing",processing); */
		
		out.println("<script>alert(\"이미 가입되어 있습니다.\");location.href=\"d_01.jsp\";");
		out.println("</script>");
		}
		out.println("<script>alert(\"비밀번호를 다시 확인해 주십시오.\");history.back();");
		out.println("</script>");
	stmt.close();
	conn.close();
	}
%>
</center>
</body>
</html>