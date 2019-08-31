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
String target=null,uri=null,url=null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String backto = request.getParameter("backto");
%>
</head>
<body>

<ul>
<%
if(Checker.isStringNullOrEmpty(backto)) {
%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span>&nbsp</span></a></li>
<%
}
else if(backto.equalsIgnoreCase("list_awal")) {
	%>
	<li><a href="prep.pengajuanPymnt?backto=list_awal" target="_self">BACK<span>&nbsp</span></a></li>
<%
}
else if(backto.equalsIgnoreCase("menu_keu")) {
	target = Constants.getRootWeb()+"/InnerFrame/Keu/index_keuangan.jsp";
	uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	url = PathFinder.getPath(uri, target);
	%>
	<li><a href="<%=url %>" target="_self">BACK<span>&nbsp</span></a></li>
<%
}
%>
</ul>
</body>
</html>