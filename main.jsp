<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<title>리조트를 방문해 주셔서 감사합니다.</title>
<style>
body{
	background-image: url('/kopo/Resort/image/main.png'); 
	background-size: 100% 100%;
}
</style>
</head>
<body>
<center>
<%
	Cookie[] cookies=request.getCookies();
	int num=-1;
	if(cookies!=null){
		for(int i=0; i<cookies.length;i++){
			if("date".equals(cookies[i].getName())){
				num=i;
			}
		}
	}
	if(num==-1){
			out.println("저희 리조르틀 처음 방문해 주셔서 감사합니다.");
			Calendar cal= Calendar.getInstance();
			SimpleDateFormat dformat= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
			String myName=dformat.format(cal.getTime());
			Cookie cookieName=new Cookie("date",myName);
			cookieName.setMaxAge(24*60*60);
			response.addCookie(cookieName);
	}else{
		out.println("최근 방문일: "+cookies[num].getValue());
	}
	
%>
</center>
</body>
</html>