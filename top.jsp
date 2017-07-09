<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<title>메인홈페이지</title>
	<style>
		td{font-size:12; text-aglign:center;}
		a:link {text-decoration:none;}
		a:visited {text-decoration:none;}
		a:hover {text-decoration:none;}
		a:acvite {text-decoration:none;}
	</style>
	<script>
	var mucnt=5;
	function fucShow(pos){
		var i=0;
		for(i;i<mucnt; i++){
			var obj=document.getElementById("menu"+i);
			var obj2=document.getElementById("m"+i);
			if(i==pos){
				obj.style.display="";
				obj2.style.color="#FF0000";
			}
			else{
				obj.style.display="none";
				obj2.style.color="#000000";
			}
		}
	}
	function fucHide(pos){
		var obj=document.getElementById("menu"+i);
		obj.style.display="none";
	}
	</script>
</head>
<body bgcolor="#FFFFFF">
<center>
<table cellpadding=0 cellspacing=1 border=0 width=900>
	<td width=150><a href='main.jsp' target=main><img src='/kopo/Resort/image/logo.png' width=140 height=50 border=0></a></td>
	<td>
	<table cellpadding=0 cellspacing=1 border=0 width=800 height=60>
		<tr height=30>
			<td width=100 onmouseover='fucShow(0)' id=m0><b>항공사 안내</b></td>
			<td width=100 onmouseover='fucShow(1)' id=m1><b>좌석예약</b></td>
			<td width=100 onmouseover='fucShow(2)' id=m2><b>나의 페이지</b></td>
			<td width=100 onmouseover='fucShow(3)' id=m3><b>회원가입</b></td>
			<td width=100 onmouseover='fucShow(4)' id=m4><b>공지사항</b></td>	
			<td width=100></td>
		</tr>
	<tr height=30>
		<td colspan=6>
		<table id=menu0 style='display:none;' cellpadding=0 cellspacing=0 border=0 width=700 height=30>
			<tr>
				<td width=0></td>
				<td width=700 style='text-align:left'>
				<a href='a_01.html' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 회사 안내 </span></a>
				<a href='a_02.html' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 항공기 안내 </span></a>
				<a href='a_03.html' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 운행지역안내 </span></a>
				<a href='a_04.html' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 운행요금안내 |</span></a>
				</td>
			</tr>
		</table>
		<table id=menu1 style='display:none;' cellpadding=0 cellspacing=0 border=0 width=700 height=30>
			<tr>
				<td width=100></td>
				<td width=600 style='text-align:left'>
				<a href='b_01.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 김포<->제주 |</span></a>
				</td>
			</tr>
		</table>
		<table id=menu2 style='display:none;' cellpadding=0 cellspacing=0 border=0 width=700 height=30>
			<tr>
				<td width=250></td>
				<td width=450 style='text-align:left'>
				<a href='c_01.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 나의 예약현황 </span></a>
				<a href='c_02.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 회원 정보 수정 |</span></a>
				</td>
			</tr>
		</table>
		<table id=menu3 style='display:none;' cellpadding=0 cellspacing=0 border=0 width=700 height=30>
			<tr>
				<td width=300></td>
				<td width=300 style='text-align:left'>
				<%String loginOK=(String)session.getAttribute("login_OK");
				if(loginOK==null || !(loginOK.equals("yes"))){//세션이 존재하지 않거나 값이 yes가 아니라면 밑에 부분을 보여주지 않는다.
				%>
				<a href='./login/adm_login.jsp?jump=../main.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 로그인 </span></a>
				<a href='d_01.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 회원가입 </span></a>
				<%}
				else{%>
				<a href='d_03.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 로그아웃 </span></a
				<%}%>
				<a href='d_02.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 회원탈퇴 |</span></a>
				</td>
			</tr>
		</table>
		<table id=menu4 style='display:none;' cellpadding=0 cellspacing=0 border=0 width=700 height=30>
			<tr>
				<td width=450></td>
				<td width=250 style='text-align:left'>
				<a href='e_01.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 알리는 글 </span></a>
				<a href='e_02.jsp' target=main>
				<span onmouseover=this.style.color='#ff0000' onmouseout=this.style.color='#000000'>| 고객의 소리| </span></a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</table>
</center>
</body>
</html>
