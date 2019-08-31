<!DOCTYPE html>
<head>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
/*
DIGUNAKAN OLEH AJAX
VARIABBLE INI SAMA DENGAN HOME.JSP KRN AJAX TIDAK 
BISA REDIRECT HANYA BISA WINDOWS.HREF YG TIDAK BISA PASSING VARIABLE
*/
String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/ListMhsPindahanBelumAdaPenyetaraan.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
url = url+"?prev=false&next=false&nav=mhsPindahanNoPenyetaraan&ending_page_shown=0&starting_page_shown=1&starting_smawl=20161&limit_per_page=10&at_hal=0&search_range=90&atMenu=index&cmd=viewKrs";
%>
</head>

<body>
	<meta http-equiv="refresh" content="0;URL='<%=url %>'" />
</body>
</html>