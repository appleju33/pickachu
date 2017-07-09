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
	String start_place=request.getParameter("start_place");
	String arrive_place=request.getParameter("arrive_place");
	int resv_num=Integer.parseInt(request.getParameter("resv_num"));
	String start_date=request.getParameter("start_date");
	String start_time=request.getParameter("start_time");
	String arrive_time=request.getParameter("arrive_time");
	String start_time2=request.getParameter("start_time2");
	String arrive_time2=request.getParameter("arrive_time2");
	String how=request.getParameter("how");
	String[] number=request.getParameter("number").split(",");
	String id=(String)session.getAttribute("login_id");
	
	String utfStart_place=new String(start_place.getBytes("8859_1"),"utf-8");
	String utfArrive_place=new String(arrive_place.getBytes("8859_1"),"utf-8");
	try{
	String sql="";
	if(how.equals("wangbuk")){
		String[] number_wang=request.getParameter("number_wang").split(",");
		String arrive_date=request.getParameter("arrive_date");
		
		for(int i=0; i<number.length; i++){
		sql="insert into airReservation(resv_date,set_num,id,start_place,arrive_place,start_time,arrive_time) values('"+start_date+"','"+number[i]+"','"+id+"','"+utfStart_place+"','"+
		""+utfArrive_place+"','"+start_time+"','"+arrive_time+"')"; 
		stmt.execute(sql);
		}
		for(int j=0; j<number_wang.length; j++){
		sql="insert into airReservation(resv_date,set_num,id,start_place,arrive_place,start_time,arrive_time) values('"+arrive_date+"','"+number_wang[j]+"','"+id+"','"+utfArrive_place+"','"+
		""+utfStart_place+"','"+start_time2+"','"+arrive_time2+"')"; 
		stmt.execute(sql);
		}
	}
	else if(how.equals("pendo")){
		for(int i=0; i<number.length; i++){
		sql="insert into airReservation(resv_date,set_num,id,start_place,arrive_place,start_time,arrive_time) values('"+start_date+"','"+number[i]+"','"+id+"','"+utfStart_place+"','"+
		""+utfArrive_place+"','"+start_time+"','"+arrive_time+"')"; 
		stmt.execute(sql);
		}
	}
	
	out.println("<h2>정상적으로 예약되었습니다.</h2>");
	out.println("<input type=button value='예약확인' onclick=location.href='c_01.jsp'>");
	
		
	
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
		
		out.println("<script>alert(\"이미 예약되어 있습니다.\");location.href=\"d_01.jsp\";");
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