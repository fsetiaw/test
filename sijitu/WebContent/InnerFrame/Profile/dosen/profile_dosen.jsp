
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
//System.out.println("masuk");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String info = (String)request.getAttribute("info");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String cmd = request.getParameter("cmd");
String atMenu = request.getParameter("atMenu");

//info = kdpst+"`"+npmhs+"`"+local+"`"+gelar_depan+"`"+gelar_belakang+"`"+nidn+"`"+tipe_id+"`"+no_id+"`"+status+"`"+pt_s1+"`"+jur_s1+"`"+kdpst_s1+"`"+gelar_s1+"`"+bidil_s1+"`"+noija_s1+"`"+tglls_s1+"`"+file_ija_s1+"`"+judul_s1+"`"+pt_s2+"`"+jur_s2+"`"+kdpst_s2+"`"+gelar_s2+"`"+bidil_s2+"`"+noija_s2+"`"+tglls_s2+"`"+file_ija_s2+"`"+judul_s2+"`"+pt_s3+"`"+jur_s3+"`"+kdpst_s3+"`"+gelar_s3+"`"+bidil_s3+"`"+noija_s3+"`"+tglls_s3+"`"+file_ija_s3+"`"+judul_s3+"`"+pt_gb+"`"+jur_gb+"`"+kdpst_gb+"`"+gelar_gb+"`"+bidil_gb+"`"+noija_gb+"`"+tglls_gb+"`"+file_ija_gb+"`"+judul_gb+"`"+tot_kum+"`"+jja_dikti+"`"+jja_local+"`"+jab_struk+"`"+tipe_ika+"`"+tgl_in+"`"+tgl_out+"`"+serdos+"`"+kdpti_home+"`"+kdpst_home+"`"+email_org+"`"+pangkat_gol+"`"+catatan_riwayat+"`"+ktp_sim_paspo+"`"+no_ktp_sim_paspo+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nsdmi;

//String kdpst = null;
String npmhs = null;
String local = null;
String gelar_depan = null;
String gelar_belakang = null;
String nidn = null;
String tipe_id = null;
String no_id = null;
String status = null;
String pt_s1 = null;
String jur_s1 = null;
String kdpst_s1 = null;
String gelar_s1 = null;
String bidil_s1 = null;
String noija_s1 = null;
String tglls_s1 = null;
String file_ija_s1 = null;
String judul_s1 = null;
String pt_s2 = null;
String jur_s2 = null;
String kdpst_s2 = null;
String gelar_s2 = null;
String bidil_s2 = null;
String noija_s2 = null;
String tglls_s2 = null;
String file_ija_s2 = null;
String judul_s2 = null;
String pt_s3 = null;
String jur_s3 = null;
String kdpst_s3 = null;
String gelar_s3 = null;
String bidil_s3 = null;
String noija_s3 = null;
String tglls_s3 = null;
String file_ija_s3 = null;
String judul_s3 = null;
String pt_gb = null;
String jur_gb = null;
String kdpst_gb = null;
String gelar_gb = null;
String bidil_gb = null;
String noija_gb = null;
String tglls_gb = null;
String file_ija_gb = null;
String judul_gb = null;
String tot_kum = null;
String jja_dikti = null;
String jja_local = null;
String jab_struk = null;
String tipe_ika = null;
String tgl_in = null;
String tgl_out = null;
String serdos = null;
String kdpti_home = null;
String kdpst_home = null;
String email_org = null;
String pangkat_gol = null;
String catatan_riwayat = null;
String ktp_sim_paspo = null;
String no_ktp_sim_paspo = null;
String nik = null;
String nip = null;
String niy_nigk = null;
String nuptk = null;
String nsdmi = null;
String nidk = null;
String nup = null;
if(!Checker.isStringNullOrEmpty(info)) {
	StringTokenizer st = new StringTokenizer(info,"`");
	kdpst = st.nextToken();
	npmhs = st.nextToken();
	local = st.nextToken();
	gelar_depan = st.nextToken();
	gelar_belakang = st.nextToken();
	nidn = st.nextToken();
	tipe_id = st.nextToken();
	no_id = st.nextToken();
	status = st.nextToken();
	pt_s1 = st.nextToken();
	jur_s1 = st.nextToken();
	kdpst_s1 = st.nextToken();
	gelar_s1 = st.nextToken();
	bidil_s1 = st.nextToken();
	noija_s1 = st.nextToken();
	tglls_s1 = st.nextToken();
	file_ija_s1 = st.nextToken();
	judul_s1 = st.nextToken();
	pt_s2 = st.nextToken();
	jur_s2 = st.nextToken();
	kdpst_s2 = st.nextToken();
	gelar_s2 = st.nextToken();
	bidil_s2 = st.nextToken();
	noija_s2 = st.nextToken();
	tglls_s2 = st.nextToken();
	file_ija_s2 = st.nextToken();
	judul_s2 = st.nextToken();
	pt_s3 = st.nextToken();
	jur_s3 = st.nextToken();
	kdpst_s3 = st.nextToken();
	gelar_s3 = st.nextToken();
	bidil_s3 = st.nextToken();
	noija_s3 = st.nextToken();
	tglls_s3 = st.nextToken();
	file_ija_s3 = st.nextToken();
	judul_s3 = st.nextToken();
	pt_gb = st.nextToken();
	jur_gb = st.nextToken();
	kdpst_gb = st.nextToken();
	gelar_gb = st.nextToken();
	bidil_gb = st.nextToken();
	noija_gb = st.nextToken();
	tglls_gb = st.nextToken();
	file_ija_gb = st.nextToken();
	judul_gb = st.nextToken();
	tot_kum = st.nextToken();
	jja_dikti = st.nextToken();
	jja_local = st.nextToken();
	jab_struk = st.nextToken();
	tipe_ika = st.nextToken();
	tgl_in = st.nextToken();
	tgl_out = st.nextToken();
	serdos = st.nextToken();
	kdpti_home = st.nextToken();
	kdpst_home = st.nextToken();
	email_org = st.nextToken();
	pangkat_gol = st.nextToken();
	catatan_riwayat = st.nextToken();
	ktp_sim_paspo = st.nextToken();
	no_ktp_sim_paspo = st.nextToken();
	nik = st.nextToken();
	nip = st.nextToken();
	niy_nigk = st.nextToken();
	nuptk = st.nextToken();
	nsdmi = st.nextToken();
	nidk = st.nextToken();
	nup = st.nextToken();
}
%>




<style>
.table { 
	border: 1px solid #2980B9;
	background:<%=Constant.lightColorBlu()%>;
	 
}
.table thead > tr > th { 
	border-bottom: none;
	background: <%=Constant.darkColorBlu()%>;
	color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<jsp:include page="../InnerMenu.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<%
if(validUsr.getObjNickNameGivenObjId().contains("OPERATOR")) {
			%>
	<jsp:include page="../litle_civitas_info.jsp" flush="true" />
			<%			
}	
		//
//if(validUsr.isUsrAllowTo("epri", npm, obj_lvl)) {
if(validUsr.isUsrAllowTo("allowViewDataDosen", npm, obj_lvl)) {
%>
		<table  width="90%">
			<tbody>
  				<tr>
  					<td align="left">
  						<a href="get.profile_v1?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=edit&atMenu=<%=atMenu%>">
						<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit.jpg" width="75" height="30">
						</a>
  					</td>
  				</tr>
  			</tbody>	
		</table>




<%
}
%>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">DATA DOSEN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >STATUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					String tmp = "";
					String[]statOpt = Constants.getStatusDosen();
					for(int i=0;i<statOpt.length;i++) {
						StringTokenizer st = new StringTokenizer(statOpt[i],"-");
						String kode = st.nextToken();
						String keter = st.nextToken();
						if(status!=null && status.equalsIgnoreCase(kode)) {
							tmp = new String(keter);
						}
					}	
					out.print(Checker.pnn_v1(tmp));
					%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SERDOS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(serdos) %></td>
				</tr>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR DEPAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_depan) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR BELAKANG</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_belakang) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PT HOMEBASE</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(kdpti_home).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PRODI HOMEBASE</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(kdpst_home)) {
						out.print(Converter.getDetailKdpst_v1(kdpst_home));
					}
					%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL MASUK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_in))%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL KELUAR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_out))%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NILAI KUM</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(tot_kum).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TIPE IKA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(tipe_ika).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >JJA-LOKAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					String[]listJja = Constants.getListJenjangJabatanAkademik();
					tmp = "";
					for(int i=0;i<listJja.length;i++) {
						StringTokenizer st = new StringTokenizer(listJja[i],"-");
						String kode = st.nextToken();
						String keter = st.nextToken();
						if(jja_local!=null && jja_local.equalsIgnoreCase(kode)) {
							tmp = new String(keter);
						}
					}	
					out.print(tmp.toUpperCase());
					%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >JJA-PDDIKTI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					listJja = Constants.getListJenjangJabatanAkademik();
					tmp = "";
					for(int i=0;i<listJja.length;i++) {
						StringTokenizer st = new StringTokenizer(listJja[i],"-");
						String kode = st.nextToken();
						String keter = st.nextToken();
						if(jja_dikti!=null && jja_dikti.equalsIgnoreCase(kode)) {
							tmp = new String(keter);
						}
					}	
					out.print(tmp.toUpperCase());
					%></td>
				</tr>
				
			</tbody>

			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">NO IDENTIFIKASI DOSEN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TIPE ID</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(ktp_sim_paspo).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO ID</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(no_ktp_sim_paspo) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NIDN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nidn).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nik).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NIDK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nidk).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NIP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nip).toUpperCase()%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NUP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nup).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NIY-NIGK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(niy_nigk).toUpperCase()%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NUPTK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nuptk).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO NSDMI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(nsdmi).toUpperCase()%></td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">CATATAN / RIWAYAT</th>
  				</tr>
  			</thead>
			<tbody>
  				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<br>
					
					<%
					
					out.print(Checker.pnn(catatan_riwayat));
					%>
					<p>&nbsp</p>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN S1</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PT ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(pt_s1).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PRODI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(kdpst_s1) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_s1).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >BIDANG ILMU</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(bidil_s1).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO IJAZAH</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(noija_s1).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL LULUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s1))%></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN S2</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PT ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(pt_s2).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PRODI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(kdpst_s2) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_s2).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >BIDANG ILMU</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(bidil_s2).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO IJAZAH</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(noija_s2).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL LULUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s2))%></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN S3</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PT ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(pt_s3).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PRODI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(kdpst_s3) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_s3).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >BIDANG ILMU</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(bidil_s3).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO IJAZAH</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(noija_s3).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL LULUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s3))%></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN GURU BESAR</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PT ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(pt_gb).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PRODI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(kdpst_gb) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GELAR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(gelar_gb).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >BIDANG ILMU</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(bidil_gb).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO IJAZAH</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(noija_gb).toUpperCase()%></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TGL LULUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_gb))%></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>		
</body>
</html>	