<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.trakm.HitungKhs" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  

%>

</head>
<body>
<div id="header">
</div>
<jsp:include page="menu.jsp" />
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		TUTORIAL SOFTWARE PENJAMINAN MUTU:<br>
		<a href="#" onclick="window.open('files/tutorial_software_mutu_part2.mp4','popup','width=850,height=600')">(1) Penggunaan Software Untuk Kegiatan Monitoring </a>
		
		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>