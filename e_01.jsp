<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
<html>
<head>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
</head>
<body>
<center>
<h1>공지사항</h1>
<table cellspacing=1 width=600 border=1>
	<tr>
		<td width=50><p align=center>번호</p></td>
		<td width=500><p align=center>공지사항</p></td>
		<td width=100><p align=center>등록일</p></td>
	</tr>
<%
	String loginOK=(String)session.getAttribute("login_OK");
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	//Statement로 쿼리를 실행한 값을 ResultSet에다가 저장한다.
	int fromPT=0;
	int cntPT=20;
	ResultSet rset=stmt.executeQuery("select count(*) from gongji");
	int totalCount=0;
	
	while(rset.next()){
		totalCount=rset.getInt(1);
	}
	int minPage=1;
	int maxPage=0;
    if(totalCount%cntPT==0){maxPage=(totalCount/cntPT);}//총 레코드 수를 cntPT로 나눈 나머지가 0일 때 maxPage의 갯수
	else{maxPage=(totalCount/cntPT)+1;}//총 레코드 수를 cntPT로 나눈 나머지가 0이 아닐 때 maxPage의 개수
	if(request.getParameter("from")==null||Integer.parseInt(request.getParameter("from"))<0){
		fromPT=0;
		
	}
	else if(Integer.parseInt(request.getParameter("from"))>=totalCount){
		fromPT=(maxPage-1)*cntPT;
		
	}
	else{
		fromPT=Integer.parseInt(request.getParameter("from"))-1;
		
	}
	stmt.close();
	stmt = conn.createStatement();
	rset = stmt.executeQuery("select * from gongji order by id desc limit "+fromPT+","+cntPT);
	while(rset.next()){
%>
	<tr>
		<td width=50><p align=center><%=rset.getInt(1)%></p></td>
		<td width=500><p align=left><a href='./gongji/gongji_view.jsp?key=<%=rset.getInt(1)%>'><%=rset.getString(2)%></a></p></td>
		<td width=100><p align=center><%=rset.getDate(3)%></p></td>
	</tr>
	<%
	}
	%>
</table>
<%if(loginOK!=null){
	if(loginOK.equals("yes")){%>
<table width=650>
	<tr>
		<td width=550></td>
		<td><input type=button value="신규" onClick="window.location='./gongji/gongji_insert.jsp'"></td>
	<tr>
</table>
<%
	}
}
	int startPage=0;//시작페이지
	int endPage=0;//끝 페이지
	int myPage=0;//현재 페이지
	
	if(cntPT==1){//cntPT가 1일때는 현재 페이지를 맞추기 위한 부분
		myPage=(int)(fromPT/cntPT);
	}else{//cntPT가 1이 아닐 때 현재 페이지를 맞추기 위한 부분
		myPage=(int)(fromPT/cntPT)+1;
	}
	
    if((myPage-5)<minPage) startPage=minPage;//현재 페이지-5 보다 최소 페이지 보다 작으면 보여주는 시작 페이지는 1
    else   startPage=myPage-5;//현재 페이지-5 보다 최소 페이지 보다 크면 보여주는 시작페이지는 현재페이지 -5 
    if((myPage+5)>=maxPage) endPage=maxPage;//최대 페이지의 값이 현재페이지+5보다 작거나 같으면 보여주는 끝 페이지는 maxPage의 값
    else    endPage=startPage+10;//최대 페이지의 값이 현재페이지+5보다 크면 보여주는 끝 페이지는 시작페이지+10
    out.println("<a href=e_01.jsp?from="+(myPage-10)*cntPT+"&cnt="+cntPT+"> << </a>");//<<를 누루면 현재페이지의 10페이지 전으로 간다.
	  for(int i = 0; i < 10; i++){//페이지는 기본적으로 10개를 보여준다.
      if((startPage+i)>maxPage){//시작페이지+i의 값이 maxPage보다 크면
         endPage = maxPage;//끝페이지의 값은 최대 페이지가 되며 for문을 벗어난다.
         break;
      }
      if(myPage == (startPage+i)){//현재페이지의 번호를 출력하는 부분 진하게
         out.println("<a href=e_01.jsp?from="+(((startPage+(i-1))*cntPT)+1)+"&cnt="+(cntPT)+"><Strong>"+(startPage+i)+"</Strong></a>");
      }else{//현재페이지가 아닌 번호 부분
         out.println("<a href=e_01_list.jsp?from="+(((startPage+(i-1))*cntPT)+1)+"&cnt="+(cntPT)+">"+(startPage+i)+" </a>");
      }
   }
	
      out.println("<a href=e_01_list.jsp?from="+(myPage+10)*cntPT+"&cnt="+cntPT+"> >> </a>");
%>
</center>
</body>
</html>
