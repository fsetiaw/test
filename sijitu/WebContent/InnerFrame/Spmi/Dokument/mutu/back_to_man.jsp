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
String id_master_std=request.getParameter("id_master_std"); 
String at_menu_dash=request.getParameter("at_menu_dash");
String fwdto=request.getParameter("fwdto");
%>
<div id="header">
<%
if(fwdto.contains("perencanaan")) {
%>
	<ul>
		<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_perencanaan_v1.jsp?fwdto=<%=fwdto %>&mode=edit&id_versi=<%=id_versi %>&id_std=<%=id_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&at_menu_dash=<%=at_menu_dash %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
<%
}
else if(fwdto.contains("pelaksanaan")) {
%>
	<ul>
		<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pelaksanaan_v1.jsp?fwdto=<%=fwdto %>&mode=edit&id_versi=<%=id_versi %>&id_std=<%=id_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&at_menu_dash=<%=at_menu_dash %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
<%
} 
else if(fwdto.contains("eval")) {
%>
	<ul>
		<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp?fwdto=<%=fwdto %>&mode=edit&id_versi=<%=id_versi %>&id_std=<%=id_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&at_menu_dash=<%=at_menu_dash %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
<%
} 
else if(fwdto.contains("kendali")) {
%>
	<ul>
		<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian_v1.jsp?fwdto=<%=fwdto %>&mode=edit&id_versi=<%=id_versi %>&id_std=<%=id_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&at_menu_dash=<%=at_menu_dash %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
<%
} 
else if(fwdto.contains("peningkatan")) {
%>
	<ul>
		<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_peningkatan_v1.jsp?fwdto=<%=fwdto %>&mode=edit&id_versi=<%=id_versi %>&id_std=<%=id_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&at_menu_dash=<%=at_menu_dash %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
<%
} 
%>	
</div>

</head>
<body>
</body>
</html>