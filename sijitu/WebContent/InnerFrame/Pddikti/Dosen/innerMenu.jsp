<!DOCTYPE html>
<head>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.PathFinder"%>
<%@ page import="beans.tools.Checker"%>
<%@ page import="beans.login.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
</head>
<body>

	<%

	String backTo= request.getParameter("backTo");
	//System.out.println("bakto="+backTo);
//	if(backTo!=null) {
//		backTo = backTo.replace("Titik", ".");
//		backTo = backTo.replace("TandaTanya", "?");
//		backTo = backTo.replace("SamaDgn", "=");
//	}
	
	%>
<ul>		
 <li><a href="<%=backTo %>" target="_self">BACK <span><b>&nbsp</b></span></a></li>
</ul>

</body>
</html>
	