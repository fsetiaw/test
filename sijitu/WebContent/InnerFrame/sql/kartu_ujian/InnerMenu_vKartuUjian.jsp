<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String backto = request.getParameter("backto");
//System.out.println("backto="+backto);
%>
</head>
<body>
<ul>

	<ul>
	<%
	String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/index_perkuliahan.jsp";
	String uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	String url = PathFinder.getPath(uri, target);
	if(false) {
		
	%>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span>&nbsp</span></a></li>	
	<%
	}
	else {
		%>
		<li><a href="<%=url %>" target="_self">BACK<span>&nbsp</span></a></li>	
	<%
	}
	%>
	</ul>
</ul>
</body>
</html>