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
//InnerFrame/Pesan/Riwayat/indexRiwayatPesan.jsp?
String cmd = request.getParameter("cmd");
String limit_per_page = request.getParameter("limit_per_page");
String offset = "0";
//String cmd = request.getParameter("cmd");

%>

</head>

<body>
<ul>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Pesan/Riwayat/indexRiwayatPesan.jsp?cmd=<%=cmd %>&limit_per_page=<%=limit_per_page %>&offset=<%=offset %>">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
</ul>

</body>
</html>