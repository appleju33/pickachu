<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,javax.sql.*,java.io.*,java.util.*"%>
<html>
<head>
<script type="text/javascript">
function submitForm(y,m,dNum){
	if(parseInt(m)<10){
		m="0"+m;
	}
	if(parseInt(dNum)<10){
		dNum="0"+dNum;
	}
	fm.resv_date.value=y+"-"+m+"-"+dNum;
	fm.action="b_02.jsp";
	fm.submit();
}
function printCalendar(y, m) { 
	var kCalendar = document.getElementById("test");
    
    //① 현재 날짜와 현재 달에 1일의 날짜 객체를 생성합니다.
    var date=new Date(); //날짜 객체 생성
    var nowY=date.getFullYear(); //현재 연도
    var nowM=date.getMonth(); //현재 월
    var nowD=date.getDate(); //현재 일
    
    y = (y != undefined)?y:nowY;
    m = (m != undefined)?parseInt(m)+1:parseInt(nowM)+1;
    var prevDate;
	if(m !=0 ){
	prevDate = parseInt(m)-2;
	}
	else{
	y=parseInt(y)-1;
	m =12;
	prevDate=11;
	}
	//만약 이번달이 1월이라면 1년 전 12월로 출력.
	var nextDate;
	if(m != 13) {
		nextDate = parseInt(m);
	}
	else {
		y=parseInt(y)+1;
		m=1;
		nextDate=1;
	}//만약 이번달이 12월이라면 1년 후 1월로 출력.
	
    /* 현재 월의 1일에 요일을 구합니다. 
     그럼 그달 달력에 첫 번째 줄 빈칸의 개수를 구할 수 있습니다.*/
    var theDate=new Date(y, m-1, 1); 
    var theDay=theDate.getDay();

    //현재 월에 마지막 일을 구해야 합니다.

    //1월부터 12월까지 마지막 일을 배열로 저장함.
    var last=[31,28,31,30,31,30,31,31,30,31,30,31];
    /*현재 연도가 윤년(4년 주기이고 100년 주기는 제외합니다. 
    또는 400년 주기)일경우 2월에 마지막 날짜는 29가 되어야 합니다.*/
    if(y%4 == 0 && y % 100 !=0 || y%400 == 0) lastDate=last[1]=29;

    var lastDate=last[m-1]; //현재 월에 마지막이 몇일인지 구합니다.

    /* 현재 월의 달력에 필요한 행의 개수를 구합니다.
    var row(행의 개수)= Math.ceil( (theDay(빈 칸)+lastDate(월의 전체 일수)) / 7)*/

    var row=Math.ceil((theDay+lastDate)/7); //필요한 행수
    
	
    var calendar="<form method=post name=fm><h2>";
	calendar += '<span><a href="#" onclick="printCalendar(\'' +  y + '\', \'' + prevDate + '\')">< </a></span>';
	calendar += '<span id="date">' + y + '년 ' + m+ '월</span>';
	calendar += '<span><a href="#" onclick="printCalendar(\'' + y + '\', \'' + nextDate + '\')"> ></a></span></span>'; 
	calendar+="<table width=700 border='1'>";
    calendar+="<tr>";
    calendar+="<th width=100>일</th>";
    calendar+="<th width=100>월</th>";
    calendar+="<th width=100>화</th>";
    calendar+="<th width=100>수</th>";
    calendar+="<th width=100>목</th>";
    calendar+="<th width=100>금</th>";
    calendar+="<th width=100>토</th>";
    calendar+="</tr>";

    var dNum=1;
    //이중 for문을 이용해 달력 테이블을 생성
    for(var i=1; i<=row; i++){//행 생성 (tr 태그 생성)
    calendar+="<tr>";

    for(var k=1; k<=7; k++){//열 생성 (td 태그 생성)        
        /*행이 첫 줄이고 현재 월의 1일의 요일 이전은 모두 빈열로
        표기하고 날짜가 마지막 일보다 크면 빈열로 표기됩니다.*/
        if(i==1 && k<=theDay || dNum>lastDate){
          calendar+="<td width=100> &nbsp; </td>";
		  
         }else{
			 var dDate=new Date(y+"-"+m+"-"+dNum);
			 if(date.getTime()<=dDate.getTime()){
				calendar+="<td width=100 bgcolor='#C3EEEE' onclick='submitForm("+y+","+m+","+dNum+");'><center>"+dNum+"</center>";
				dNum++;
			 }
			 else{
				calendar+="<td width=100 bgcolor='#9A9A9A'><center>"+dNum+"</center>";
				dNum++;
			 }
         }
    }
    calendar+="<tr>";
    }    
	calendar+="</table>";
	calendar+="<input type=hidden name=resv_date value=''></form>";
    //⑤ 문자로 결합된 달력 테이블을 문서에 출력
    /* document.write(calendar); */
	kCalendar.innerHTML=calendar;
}

//printCalendar(2012,2);
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
<center>
<div id="test"></div>
<script>printCalendar();</script>
<center>
</body>
</html>
			
			
			