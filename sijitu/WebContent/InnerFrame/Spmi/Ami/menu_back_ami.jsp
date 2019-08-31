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
boolean editor = (Boolean) session.getAttribute("spmi_editor");
boolean team_spmi = (Boolean) session.getAttribute("team_spmi");

//String src_limit = request.getParameter("limit");

int at_page = 1; //always starting at 1 bila aksed dari menu

int max_data_per_man_pg=Constant.getAt_page();
int max_data_per_pg=Constant.getMax_data_per_pg();
String tmp_max_data_per_pg = ""+max_data_per_pg;
if(Checker.isStringNullOrEmpty(tmp_max_data_per_pg)) {
	tmp_max_data_per_pg=""+Constant.getMax_data_per_pg();
}	

String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
if(Checker.isStringNullOrEmpty(kdpst_nmpst_kmp)) {
	kdpst_nmpst_kmp = (String)session.getAttribute("current_kdpst_nmpst_kmp");
}



String id_ami = request.getParameter("id_ami");
if(Checker.isStringNullOrEmpty(id_ami)) {
	id_ami = (String)session.getAttribute("id_ami");;
}	
String kode_activity = request.getParameter("kode_activity");
if(Checker.isStringNullOrEmpty(kode_activity)) {
	kode_activity = (String)session.getAttribute("kode_activity");;
}
String tgl_plan = request.getParameter("tgl_plan");
if(Checker.isStringNullOrEmpty(tgl_plan)) {
	tgl_plan = (String)session.getAttribute("tgl_plan");;
}
String ketua_tim = request.getParameter("ketua_tim");
if(Checker.isStringNullOrEmpty(ketua_tim)) {
	ketua_tim = (String)session.getAttribute("ketua_tim");;
}
String anggota_tim = request.getParameter("anggota_tim");
if(Checker.isStringNullOrEmpty(anggota_tim)) {
	anggota_tim = (String)session.getAttribute("anggota_tim");;
}
String id_cakupan_std = request.getParameter("id_cakupan_std");
if(Checker.isStringNullOrEmpty(id_cakupan_std)) {
	id_cakupan_std = (String)session.getAttribute("id_cakupan_std");;
}
String ket_cakupan_std = request.getParameter("ket_cakupan_std");
if(Checker.isStringNullOrEmpty(ket_cakupan_std)) {
	ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");;
}
String tgl_ril = request.getParameter("tgl_ril");
if(Checker.isStringNullOrEmpty(tgl_ril)) {
	tgl_ril = (String)session.getAttribute("tgl_ril");;
}
String tgl_ril_done = request.getParameter("tgl_ril_done");
if(Checker.isStringNullOrEmpty(tgl_ril_done)) {
	tgl_ril_done = (String)session.getAttribute("tgl_ril_done");;
}
String id_master_std = request.getParameter("id_master_std");
if(Checker.isStringNullOrEmpty(id_master_std)) {
	id_master_std = (String)session.getAttribute("id_master_std");;
}
String ket_master_std = request.getParameter("ket_master_std");
if(Checker.isStringNullOrEmpty(ket_master_std)) {
	ket_master_std = (String)session.getAttribute("ket_master_std");;
}





//System.out.println("kdpst_nmpst_kmp2=="+kdpst_nmpst_kmp);

kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
String at_menu = request.getParameter("at_menu");
String mode = request.getParameter("mode");
//boolean sdh_aktif = false;
//SearchStandarMutu ssm = new SearchStandarMutu();
//sdh_aktif = ssm.isStandardUmumActivated(Integer.parseInt(id_versi), Integer.parseInt(id_std));

%>
</head>
<body>
<div id="header">
	<ul>
		<!--  li>
			<a href="get.prepInfoMan_v1?status_manual=<=status_manual %>&id_versi=<=id_versi %>&id_tipe_std=<=id_tipe_std %>&id_master_std=<=id_master_std %>&id_std=<=id_std %>&kdpst_nmpst_kmp=<=kdpst_nmpst_kmp %>&at_menu_dash=<= at_menu_dash%>&fwdto=dashboard_std_manual_pelaksanaan_v1.jsp&darimana=riwayat">BACK <span><b style="color:#eee">&nbsp</b></span></a>
		</li -->
		<li>
			<li><a href="go.prepDashOverviewAmi?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
		</li>
<%
if(Checker.isStringNullOrEmpty(mode)) {
	if(Checker.isStringNullOrEmpty(at_menu)) {
		%>
		<li>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" target="inner_iframe" class="active">DASHBOARD<span>AMI</span></a>
		</li>
		<%	
	}
	else {
			%>
		<li>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" target="inner_iframe">DASHBOARD<span>AMI</span></a>
		</li>
		<%		
	}
	if(Checker.isStringNullOrEmpty(at_menu)) {
		%>
		<li>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/dashArsipAmi.jsp?at_menu=folder&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" target="inner_iframe">FOLDER<span>FILE</span></a>
		</li>
		<%	
	}
	else {
		%>
		<li>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/dashArsipAmi.jsp?at_menu=folder&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" class="active" target="inner_iframe">FOLDER<span>FILE</span></a>
		</li>
		<%	
	}	
}
%>
	</ul>
</div>
<%
//System.out.println("tamat");
%>	
</body>
</html>