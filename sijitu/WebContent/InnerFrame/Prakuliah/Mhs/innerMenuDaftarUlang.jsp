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
</head>
<body>
<ul>
<%
String backTo = request.getParameter("backTo");
if(backTo!=null && !Checker.isStringNullOrEmpty(backTo)) {
%>
	<li><a href="get.notifications"  target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
}
else {
%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
}
%>
</ul>
</body>
