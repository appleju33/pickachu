<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*,java.text.*"%>
<html>
<head>
<style>
body{
	background-image: url('/kopo/Resort/image/background.jpg'); 
	background-size: 100% 100%;
}
</style>
<script>
function submitForm(mode){
	var str=fm.name.value;
	var str1=fm.tel.value;
	var str2=fm.in_name.value;
	var date=fm.date.value;
	
	if(str.trim()==""||str1.trim()==""||str2.trim()==""){//필수로 입력해야하는 곳
		alert("필수 입력란(이름,전화번호,입금자명)이 비었습니다. 확인해 주세요.");
	}
	else{
		var num_check=/[0-9]{10}[0-9]?/;
		if(num_check.test(str1)){
			if(isValidDate(date)){
				if(mode=="update"){
				fm.action="adm_update.jsp";
				fm.submit();
				}
				if(mode=="delete"){
				fm.action="adm_delete.jsp";
				fm.submit();
				}
			}else{
				alert ( "날짜를 확인해 주세요" );
				frm.date.value="";
				frm.date.focus();
			}
		}
		else{
			alert ( "전화번호를 확인해 주세요." );
			frm.tel.value="";
			frm.tel.focus();
		}
	}
}
function isDateFormat(d) {
    var df = /[0-9]{4}-[0-9]{2}-[0-9]{2}/;
    return d.match(df);
}

/*
 * 윤년여부 검사
 */
function isLeaf(year) {
    var leaf = false;

    if(year % 4 == 0) {
        leaf = true;

        if(year % 100 == 0) {
            leaf = false;
        }

        if(year % 400 == 0) {
            leaf = true;
        }
    }

    return leaf;
}

/*
 * 날짜가 유효한지 검사
 */
function isValidDate(d) {
    // 포맷에 안맞으면 false리턴
    if(!isDateFormat(d)) {
        return false;
    }

    var month_day = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    var dateToken = d.split('-');
    var year = Number(dateToken[0]);
    var month = Number(dateToken[1]);
    var day = Number(dateToken[2]);
    
    // 날짜가 0이면 false
    if(day == 0) {
        return false;
    }

    var isValid = false;

    // 윤년일때
    if(isLeaf(year)) {
        if(month == 2) {
            if(day <= month_day[month-1] + 1) {
                isValid = true;
            }
        } else {
            if(day <= month_day[month-1]) {
                isValid = true;
            }
        }
    } else {
        if(day <= month_day[month-1]) {
            isValid = true;
        }
    }

    return isValid;
}
</script>
<%
	String loginOK=null;
	String jumpURL="adm_login.jsp?jump=../d_03.jsp";
	
	loginOK=(String)session.getAttribute("login_OK");
	if(loginOK==null){
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
	
	String key=request.getParameter("key");
	String date=request.getParameter("date"+key);
	String room="";
	if(request.getParameter("room")==null){
		room="0";
	}else{
		room=request.getParameter("room");
	}
	Class.forName("com.mysql.jdbc.Driver");//클래스가 동적으로 생성될때 DriverManager 에 해당 클래스를 등록시키는 목적을 가진다.
	//mysql에 접속하기 위한 부분 jdbc는 mysql이며 //ip주소:port번호/database이름,아이디,비번
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kopoctc", "root", "hard");
	//Statement 인터페이스는 Connection 객체에 의해 프로그램에 리턴되는 객체에 의해 구현되는 일종의 메소드 집합을 정의
	Statement stmt = conn.createStatement();
	ResultSet rset=stmt.executeQuery("select * from redsv where resv_date='"+date+"' and room="+room);
%>
<center>
<h2>관리자 - 예약하기</h2>
<form method=post name=fm >
<table width=550 border=1 cellspacing=0 cellpadding=5>
	<%while(rset.next()){%>
	<tr>
		<td width=100><b>성명</b></td>
		<td><input type=text name=name  maxlength=20 value='<%=rset.getString(1)%>'></td>
	</tr>
	<tr>
		<td width=100><b>예약일자</b></td>
		<td><input type=date name=date value='<%=rset.getString(2)%>'></td>
	</tr>
	<tr>
		<td width=100><b>예약방</b></td>
		<td><select name=room>
				<%
				if(room.equals("1")){%>
				<option value="1" selected="selected">VIP Room</option>
				<%}else{%>
				<option value="1">VIP Room</option>
				<%}
				if(room.equals("2")){%>
				<option value="2" selected="selected">Standard Room</option>
				<%}else{%>
				<option value="2">Standard Room</option>
				<%}
				if(room.equals("3")){%>
				<option value="3" selected="selected">Reasonable Room</option>
				<%}else{%>
				<option value="3">Reasonable Room</option>
				<%}%>
		</td>
	</tr>
	<tr>
		<td width=100><b>주소</b></td>
		<td><input type=text name=addr size=55 value='<%=rset.getString(4)%>'></td>
	</tr>
	<tr>
		<td width=100><b>전화번호</b></td>
		<td><input type=tel name=tel maxlength="11" pattern="" value='<%=rset.getString(5)%>'></td>
	</tr>
	<tr>
		<td width=100><b>입금자명</b></td>
		<td><input type=text name=in_name value='<%=rset.getString(6)%>'></td>
	</tr>
	<tr>
		<td width=100><b>남기실말</b></td>
		<td><input type=text name=comment size=55 maxlength=55 value='<%=rset.getString(7)%>'></td>
	</tr>
	<tr>
		<td width=100><b>접수일자</b></td>
		<td><input type=text name=write_date value='<%=rset.getString(8)%>' readonly></td>
	</tr>
	<tr>
		<td width=100><b>진행상황</b></td>
		<td><input type=text name=processing size=55 maxlength=55 value='<%=rset.getInt(9)%>'></td>
	</tr>
	<%}
	rset.close();
	stmt.close();
	conn.close();
	%>
</table>
<input type=hidden name=oldDate value='<%=date%>'>
<input type=hidden name=oldRoom value='<%=room%>'>
<table width=550>
	<tr>
		<td width=500></td>
		<td><input type=button value='취소' onclick="location.href='../d_03.jsp'"></td>
		<td><input type=button value='수정' onclick="submitForm('update')"></td>
		<td><input type=button value='삭제' onclick="submitForm('delete')"></td>
	</tr>
</table>
</form>
</center>
</body>
</html>