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
String at_menu_dash = request.getParameter("at_menu_dash");
//String fwdto = request.getParameter("fwdto");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//Vector v= (Vector)request.getAttribute("v");
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_kendali = request.getParameter("id_kendali");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String id_std = request.getParameter("std_isi");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String tipe_sarpras = request.getParameter("tipe_sarpras");
String catat_civitas = request.getParameter("catat_civitas");
String jabatan_pengendali = request.getParameter("jabatan_pengendali");
String jabatan_inputer = request.getParameter("jabatan_inputer");

boolean am_i_surveyor = Boolean.parseBoolean(request.getParameter("am_i_surveyor"));
boolean am_i_controller = Boolean.parseBoolean(request.getParameter("am_i_controller"));
String am_i_pengawas = request.getParameter("am_i_pengawas");
String am_i_terkait = request.getParameter("am_i_terkait");

boolean sdh_aktif = false;
SearchStandarMutu ssm = new SearchStandarMutu();
sdh_aktif = ssm.isStandardActivated(Integer.parseInt(id_std_isi));
%>
</head>
<body>
<div id="header">

	<ul>
	<li><a href="get.prepInfoStd?id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian.jsp">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
if(at_menu_kendal.equalsIgnoreCase("riwayat")) {
%>	
                                            
	<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/riwayat_kendal.jsp?am_i_controller=<%=am_i_controller %>&am_i_surveyor=<%=am_i_surveyor %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&jabatan_pengendali=<%=jabatan_pengendali %>&jabatan_inputer=<%=jabatan_inputer%>&catat_civitas=<%=catat_civitas %>&tipe_sarpras=<%=tipe_sarpras %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&id_kendali=<%=id_kendali %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat" class="active">RIWAYAT<span>PENGENDALIAN</span></a></li>
<%
}
else {
%>	
	<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/riwayat_kendal.jsp?am_i_controller=<%=am_i_controller %>&am_i_surveyor=<%=am_i_surveyor %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&jabatan_pengendali=<%=jabatan_pengendali %>&jabatan_inputer=<%=jabatan_inputer%>&catat_civitas=<%=catat_civitas %>&tipe_sarpras=<%=tipe_sarpras %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&id_kendali=<%=id_kendali %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat">RIWAYAT<span>PENGENDALIAN</span></a></li>
<%	
}
//if(sdh_aktif) {
if(false) {	
	if(at_menu_kendal.equalsIgnoreCase("form")) {

%>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/form_survey_pengendalian.jsp?jabatan_pengendali=<%=jabatan_pengendali %>&jabatan_inputer=<%=jabatan_inputer%>&catat_civitas=<%=catat_civitas %>&tipe_sarpras=<%=tipe_sarpras %>&scope_std=<%=scope_std %>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&id_kendali=<%=id_kendali %>&at_menu_kendal=form&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active">FORM SURVEY<span>PENGAWASAN</span></a></li>
<%
	}
	else {
%>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/form_survey_pengendalian.jsp?jabatan_pengendali=<%=jabatan_pengendali %>&jabatan_inputer=<%=jabatan_inputer%>&catat_civitas=<%=catat_civitas %>&tipe_sarpras=<%=tipe_sarpras %>&scope_std=<%=scope_std %>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&id_kendali=<%=id_kendali %>&at_menu_kendal=form&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>">FORM SURVEY<span>PENGAWASAN</span></a></li>
<%	
	}
}
%>
	</ul>
</div>
	
</body>
</html>