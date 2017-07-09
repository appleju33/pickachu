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
<%
	String loginOK=null;
	String jumpURL="./login/adm_login.jsp?jump=../d_03.jsp";
	
	loginOK=(String)session.getAttribute("login_OK");
	if(loginOK==null){
		response.sendRedirect(jumpURL);
		return;
	}else if(!(loginOK.equals("yes"))){
		response.sendRedirect(jumpURL);
		return;
	}
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
<h2>관리자 - 수정</h2>
<%
	
	String name=htmlchar_run(request.getParameter("name"));
	String date=request.getParameter("date");
	String oldDate=request.getParameter("oldDate");
	int room=Integer.parseInt(request.getParameter("room"));
	int oldRoom=Integer.parseInt(request.getParameter("oldRoom"));
	String addr=htmlchar_run(request.getParameter("addr"));
	String telnum=request.getParameter("tel");
	String in_name=htmlchar_run(request.getParameter("in_name"));
	String comment=htmlchar_run(request.getParameter("comment"));
	String write_date=request.getParameter("write_date");
	int processing= Integer.parseInt(request.getParameter("processing"));
	
	try{
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	
	String utfName=new String(name.getBytes("8859_1"),"utf-8");
	String utfAddr=new String(addr.getBytes("8859_1"),"utf-8");
	String utfIn_name=new String(in_name.getBytes("8859_1"),"utf-8");
	String utfcomment=new String(comment.getBytes("8859_1"),"utf-8");
	String sql="";
	if(!(oldDate.equals(date))||!(oldRoom==room)){
	sql="delete from redsv where resv_date='"+oldDate+"' and room="+oldRoom;
	stmt.execute(sql);
	sql="insert into redsv(name,resv_date,room,addr,telnum,in_name,comment,write_date,processing) "+
	"values('"+utfName+"','"+date+"',"+room+",'"+utfAddr+"','"+telnum+"','"+utfIn_name+"','"+utfcomment+"','"+write_date+"',"+processing+")"; 
	stmt.execute(sql);
	}
	else{
	sql="update redsv set name='"+utfName+"', addr='"+utfAddr+"', telnum='"+telnum+"', "+
	"in_name='"+utfIn_name+"', comment='"+utfcomment+"',processing="+processing+" where resv_date='"+date+"' and room="+room;
	stmt.execute(sql);
	}
	out.println("<h1>정상적으로 변경 되었습니다.</h1>");
	out.println("<input type=button value='예약상황' onclick=location.href='../d_03.jsp'>");
	
	stmt.close();
	conn.close();
	}catch(SQLException e){
		out.println(e.getMessage());
	if(e.getErrorCode()==1062){ 
		out.println("<script>alert(\"이미 예약되어 있습니다. 다른날짜 또는 방을 이용해 주세요.\");history.go(-1);");
		out.println("</script>");
		}
	}
%>
</center>
</body>
</html>