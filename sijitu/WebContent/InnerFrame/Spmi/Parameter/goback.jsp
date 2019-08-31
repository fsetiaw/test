<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>
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
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String mode=request.getParameter("mode");
String id_tipe_std=request.getParameter("id_tipe_std"); 
String id_versi=request.getParameter("id_versi");
String id_std=request.getParameter("id_std");
String id_std_isi=request.getParameter("id_std_isi");
String id_master_std=request.getParameter("id_master_std"); 
String at_menu_dash=request.getParameter("at_menu_dash");
String fwdto=request.getParameter("fwdto");

//System.out.println("id_tipe_std goback="+id_tipe_std);
//System.out.println("id_versi goback="+id_versi);
//System.out.println("id_master_std goback="+id_master_std);
//System.out.println("id_std goback="+id_std);
//System.out.println("id_std_isi goback="+id_std_isi);
%>
<div id="header">
<ul>
	<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_single.jsp?first_index=1&mode=<%=mode%>&id_master_std=<%=id_master_std %>&id_tipe_std=<%=id_tipe_std %>&id_std_isi=<%=id_std_isi%>&id_std=<%=id_std%>&id_versi=<%=id_versi%>&atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_page=1" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
</ul>
	
</div>

</head>
<body>
</body>
</html>