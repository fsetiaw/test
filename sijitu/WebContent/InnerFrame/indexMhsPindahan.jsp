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
<%@ page import="beans.dbase.*" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");


%>

</head>
<body>
<div id="header">
<%@ include file="dataMhsPindahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br>
		<center>
		<%
		SearchDb sdb = new SearchDb();
		String idkur = sdb.getIndividualKurikulum(kdpst, v_npmhs);
		if(Checker.isStringNullOrEmpty(idkur)) {
			out.print("<h2>Harap Tentukan Kurikulum Untuk SETIYA PURWANTO Terlebih Dahulu</h2>");
		}
		%>
		</center>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>