<!DOCTYPE html>
<%
//try {
%>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

//String scopeHakAkses= request.getParameter("scopeHakAkses");
//String scopeKmp=request.getParameter("scopeKmp");
//Vector vListKelas = (Vector) request.getAttribute("vListKelas");


%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<%@ include file="../innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
	<br/>penuggasan<br/>


		<!-- Column 1 end -->
	</div>
</div>

</body>
<%
//}
//catch(Exception e) {
//	out.println("ADA ERROR @nilai.dashPenilaian.jsp");
//}
%>
</html>