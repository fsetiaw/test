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
String atMenu = request.getParameter("atMenu");
String nim = request.getParameter("nim");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
%>
</head>
<body>
<ul>
	<li><a href="get.profile_v1?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=profile&atMenu=pribadi" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<!--  li><a href="get.profile?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=dashboard" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li -->
<%
if(atMenu!=null && atMenu.equalsIgnoreCase("js")) {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=js&cmd=js" target="_self" class="active">INFO<span>JABATAN</span></a></li>
<%	
}
else {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=js&cmd=js" target="_self">INFO<span>JABATAN</span></a></li>
<%
}
if(atMenu!=null && atMenu.equalsIgnoreCase("ejs")) {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=ejs&cmd=ejs" target="_self" class="active">EDIT<span>JABATAN</span></a></li>
<%	
}
else {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=ejs&cmd=ejs" target="_self">EDIT<span>JABATAN</span></a></li>
<%
}
%>	
</ul>
</body>
</html>