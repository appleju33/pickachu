<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<script>
function chageImage(id){
	var image = document.getElementById(id);
	if(cntChage(1,id)>=0){
		if (image.src.match("gray")) {
			image.src="./image/red.png";		
		}
	}
}
function cntChage(cnt,id){
	var checkCNT=parseInt(fm.resv_num.value);
	checkCNT-=cnt;
	if(checkCNT>=0){
		fm.resv_num.value=checkCNT;
		var num=fm.number.value;
		num+=id+",";
		fm.number.value=num;
	}
	else{
		alert("선택수가 초과 되었습니다.");
	}
	return checkCNT;
}
function chageImage_wang(id){
	var wang_id=id.replace("wang_","")
	var image = document.getElementById(id);
	if(cntChage_wang(1,wang_id)>=0){
		if (image.src.match("gray")) {
			image.src="./image/red.png";		
		}
	}
}
function cntChage_wang(cnt,id){
	var checkCNT=parseInt(fm.resv_num_wang.value);
	checkCNT-=cnt;
	if(checkCNT>=0){
		fm.resv_num_wang.value=checkCNT;
		var num=fm.number_wang.value;
		num+=id+",";
		fm.number_wang.value=num;
	}
	else{
		alert("선택수가 초과 되었습니다.");
	}
	return checkCNT;
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
	try{
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
	
	String utfStart_place=new String(start_place.getBytes("8859_1"),"utf-8");
	String utfArrive_place=new String(arrive_place.getBytes("8859_1"),"utf-8");
	ResultSet rset = stmt.executeQuery("select set_num from airReservation where resv_date='"+start_date+"' and start_place='"+utfStart_place+"' and start_time='"+start_time+"' and arrive_time='"+arrive_time+"' and arrive_place='"+utfArrive_place+"';");
	ArrayList<String> a=new ArrayList<String>();
	int cnt=0;
	while(rset.next()){
		 a.add(rset.getString(1));
		 cnt++;
	}
	%>
<center>
<form method=post name=fm action="b_write.jsp">
<h2><%=start_date%> <%=utfStart_place%>-><%=utfArrive_place%></h2>
<h3><%=start_time%> <%=arrive_time%></h3>
<table>
<%
String []setNumber={"A","B","C","D","E","F"};
for(int i=0; i<7; i++){%>
<tr>
<%	for(int j=0; j<10; j++){
	if(i==0){
		if(j==2||j==7){
			out.print("<center><td></td></center>");
		}
		else{
			out.print("<center><td>"+j+"</td></center>");
		}
	}
	else{
		String id=setNumber[i-1]+""+j;
		if(j==2||j==7){%>
			<td>
			<%=setNumber[i-1]%>
			</td>
		<%
		continue;}
		else{
			boolean trueX=true;
			for(int k=0; k<a.size(); k++){
			if(a.get(k).equals(id)){%>
			<td>
			<img id=<%=id%> width=35px height=35px src="./image/X.png">
			</td>
			<%
			trueX=false;
			}
			}if(trueX){
			%>
			<td>
			<img id=<%=id%> onclick="chageImage('<%=id%>');" width=35px height=35px src="./image/gray.png">
			</td>
		<%}}
		
			}
		}%>

<%}%>
</tr>
</table>

<%
String how=request.getParameter("how");
if(how.equals("wangbuk")){
	String arrive_date=request.getParameter("arrive_date");
	ResultSet rset2 = stmt.executeQuery("select set_num from airReservation where resv_date='"+arrive_date+"' and start_place='"+utfArrive_place+"' and start_time='"+start_time2+"' and arrive_time='"+arrive_time2+"' and arrive_place='"+utfStart_place+"';");
	ArrayList<String> b=new ArrayList<String>();
	while(rset2.next()){
		 b.add(rset2.getString(1));
	}
%>
<h2><%=arrive_date%> <%=utfArrive_place%>-><%=utfStart_place%></h2>
<h3><%=start_time2%> <%=arrive_time2%></h3>
<table>	
<%for(int i=0; i<7; i++){%>
<tr>
<%	for(int j=0; j<10; j++){
	if(i==0){
		if(j==2||j==7){
			out.print("<center><td></td></center>");
		}
		else{
			out.print("<center><td>"+j+"</td></center>");
		}
	}
	else{
		String wang_id=setNumber[i-1]+""+j;
		if(j==2||j==7){%>
			<td>
			<%=setNumber[i-1]%>
			</td>
		<%
		continue;}
		else{
			boolean trueX=true;
			for(int k=0; k<b.size(); k++){
			if(b.get(k).equals(wang_id)){%>
			<td>
			<img id=wang_<%=wang_id%> width=35px height=35px src="./image/X.png">
			</td>
			<%
			trueX=false;
			}
			}if(trueX){
			%>
			<td>
			<img id=wang_<%=wang_id%> onclick="chageImage_wang('wang_<%=wang_id%>');" width=35px height=35px src="./image/gray.png">
			</td>
		<%}}
		
			}
		}%>

<%}%>
</tr>
</table>
<input type=hidden name=resv_num value='<%=resv_num%>'>
<input type=hidden name=resv_num_wang value='<%=resv_num%>'>
<input type=hidden name=number value=''>
<input type=hidden name=number_wang value=''>
<input type=hidden name=start_place value='<%=utfStart_place%>'>
<input type=hidden name=arrive_place value='<%=utfArrive_place%>'>
<input type=hidden name=start_date value='<%=start_date%>'>
<input type=hidden name=arrive_date value='<%=arrive_date%>'>
<input type=hidden name=start_time value='<%=start_time%>'>
<input type=hidden name=arrive_time value='<%=arrive_time%>'>
<input type=hidden name=start_time2 value='<%=start_time2%>'>
<input type=hidden name=arrive_time2 value='<%=arrive_time2%>'>
<input type=hidden name=how value='<%=how%>'>
<table>
<tr>
<td>
<input type=button value="취소" onclick="location.href='b_01.jsp'">
<input type=submit value="예약">
</td>
</tr>
</table>
<%}
	}catch(SQLException e){
		out.println(e.getErrorCode());
		out.println(e.getMessage());
	}%>
</form>
</center>
</body>
</html>