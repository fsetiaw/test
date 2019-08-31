<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
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

//String fwdto = request.getParameter("fwdto");
String status_manual = request.getParameter("status_manual");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String id_std = request.getParameter("std_isi");
String norut_man = request.getParameter("norut_man");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String target_unit_used = request.getParameter("target_unit_used");
boolean man_sdh_aktif = false;
if(status_manual!=null && status_manual.equalsIgnoreCase("AKTIF")) {
	man_sdh_aktif = true;
}
//String src_limit = request.getParameter("limit");

//System.out.println("id_std_isi="+id_std_isi);
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_kendali = request.getParameter("id_kendali");



boolean sdh_aktif = false;
SearchStandarMutu ssm = new SearchStandarMutu();
sdh_aktif = ssm.isStandardActivated(Integer.parseInt(id_std_isi));

%>
</head>
<body>
<div id="header">

	<ul>
		<li>
			<a href="get.prepInfoStd?status_manual=<%=status_manual %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pelaksanaan.jsp&darimana=riwayat">BACK <span><b style="color:#eee">&nbsp</b></span></a>
		</li>
	</ul>
</div>
	
</body>
</html>