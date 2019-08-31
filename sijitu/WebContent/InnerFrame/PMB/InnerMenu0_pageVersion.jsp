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
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//caller page buat menentukan class-aktif
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<ul>
	<li><a href="get.notifications" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
if(atMenu!=null && atMenu.equalsIgnoreCase("insBt")) {
	%>
	<li><a href="buku_tamu.jsp?atMenu=insBt" target="_self" class="active">BUKU<span>TAMU</span></a></li>
	<%	
}
else {
	%>
	<li><a href="buku_tamu.jsp?atMenu=insBt" target="_self">BUKU<span>TAMU</span></a></li>
	<%	
}
/*
if(atMenu!=null && atMenu.equalsIgnoreCase("insDsn")) {
	//target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
	//uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	//url = PathFinder.getPath(uri, target);
	%>
	<li><a href="pmb_index.jsp?atMenu=insDsn&objid_kdpst=<%=Checker.getObjidKdpstDosen("DOSEN") %>" target="_self" class="active">INSERT<span>DOSEN BARU</span></a></li>
	<%	
}
else {
	%>
	<li><a href="pmb_index.jsp?atMenu=insDsn&objid_kdpst=<%=Checker.getObjidKdpstDosen("DOSEN") %>" target="_self">INSERT<span>DOSEN BARU</span></a></li>
	<%	
}
if(atMenu!=null && atMenu.equalsIgnoreCase("insCiv")) {
	//target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
	//uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	//url = PathFinder.getPath(uri, target);
	%>
	<li><a href="pmb_index.jsp?atMenu=insCiv" target="_self" class="active">INSERT<span>CIVITAS BARU</span></a></li>
	<%	
}
else {
	%>
	<li><a href="pmb_index.jsp?atMenu=insCiv" target="_self">INSERT<span>CIVITAS BARU</span></a></li>
	<%	
}
*/
%>
</ul>

</body>
</html>