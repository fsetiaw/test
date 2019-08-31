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
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	
//String src_limit = request.getParameter("limit");
//DARI FORM
String id_ami = (String)session.getAttribute("id_ami");;
String kode_activity = (String)session.getAttribute("kode_activity");
String tgl_plan = (String)session.getAttribute("tgl_plan");
String ketua_tim = (String)session.getAttribute("ketua_tim");
String anggota_tim = (String)session.getAttribute("anggota_tim");
String id_cakupan_std = (String)session.getAttribute("id_cakupan_std");
String ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");
String tgl_ril = (String)session.getAttribute("tgl_ril");
String tgl_ril_done = (String)session.getAttribute("tgl_ril_done");
String id_master_std = (String)session.getAttribute("id_master_std");
String ket_master_std = (String)session.getAttribute("ket_master_std");

/*
SESSION VALUE DI BUANG DI index_pelaksanaan

session.removeAttribute("id_ami");
session.removeAttribute("kode_activity");
session.removeAttribute("tgl_plan");
session.removeAttribute("ketua_tim");
session.removeAttribute("anggota_tim");
session.removeAttribute("id_cakupan_std");
session.removeAttribute("ket_cakupan_std");
session.removeAttribute("tgl_ril");
session.removeAttribute("tgl_ril_done");
session.removeAttribute("id_master_std");
session.removeAttribute("ket_master_std");
*/

int at_page = 1; //always starting at 1 bila aksed dari menu
String tmp_max_data_per_pg = request.getParameter("max_data_per_pg");
if(Checker.isStringNullOrEmpty(tmp_max_data_per_pg)) {
	tmp_max_data_per_pg=""+Constant.getMax_data_per_pg();
}	
int max_data_per_pg = Integer.parseInt(tmp_max_data_per_pg);
ToolSpmi ts = new ToolSpmi();
String status = ts.getStatusKegiatanAmi(Integer.parseInt(id_ami));
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
<%
if(!Checker.isStringNullOrEmpty(status)&&status.equalsIgnoreCase("sedang")) {
	%>		
			<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" onclick="if (confirm('Anda akan meninggalkan laman ini ?!?  \nPastikan and telah melakukan update data dengan meng-klik icon save-folder terlebih dahulu.')) return true;return false; " target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a-->
			<a href="#" onclick="(function(){
			     var x = document.getElementById('wait');
		     var y = document.getElementById('main');
		     x.style.display = 'block';
		     y.style.display = 'none';
		     document.getElementById('hiddenField').value='true';
		     document.getElementById('updateHasilAmi').submit();
		     })();"
			target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a>
<%
}
else {
%>		
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_ami=<%=id_ami%>&kode_activity=<%=kode_activity%>&tgl_plan=<%=tgl_plan%>&ketua_tim=<%=ketua_tim%>&anggota_tim=<%=anggota_tim%>&id_cakupan_std=<%=id_cakupan_std%>&ket_cakupan_std=<%=ket_cakupan_std%>&tgl_ril=<%=tgl_ril%>&tgl_ril_done=<%=tgl_ril_done%>" target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a>
<%
}
%>			
		</li>
	</ul>
</div>
	
</body>
</html>