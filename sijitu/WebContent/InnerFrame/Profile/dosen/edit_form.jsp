<!DOCTYPE html>
<html>
<head>
<title>Bootstrap-select test page</title>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="java.util.ListIterator" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>

	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
  	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>

  	<script>
  	$(document).ready(function () {
    		var mySelect = $('#first-disabled2');

    		$('#special').on('click', function () {
      		mySelect.find('option:selected').prop('disabled', true);
      		mySelect.selectpicker('refresh');
    	});

    	$('#special2').on('click', function () {
      		mySelect.find('option:disabled').prop('disabled', false);
      		mySelect.selectpicker('refresh');
    	});

	    $('#basic2').selectpicker({
    	  	liveSearch: true,
      		maxOptions: 1
    	});
  	});
</script>
 
<%
//System.out.println("edit2");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String tkn_wajib = "NAME-IBU-DOANG JADI NGGA BISA DIPAKAI";
String info = (String)request.getAttribute("info");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String atMenu = request.getParameter("atMenu");
String cmd = request.getParameter("cmd");


Vector v_list_kdpst = new Vector();
ListIterator li_lispro = v_list_kdpst.listIterator();
JSONArray jsoa = null;
try {
	jsoa = Getter.readJsonArrayFromUrl("/v1/search_prodi/daftar_prodi");
	//KDPSTMSPST,NMPSTMSPST,KDFAKMSPST,KDJENMSPST,KODE_JENJANG,PRODI
}
catch(Exception e) {}
if(jsoa!=null && jsoa.length()>0) {
	for(int i=0;i<jsoa.length();i++) {
		JSONObject job = jsoa.getJSONObject(i);
		String kodpst = "null",nmpst = "null",kdfak = "null",kdjen = "null",kodejen = "null",prodi = "null";
		try {
			kodpst = (String)job.get("KDPSTMSPST");
			nmpst = (String)job.get("NMPSTMSPST");
			kdfak = (String)job.get("KDFAKMSPST");
			kdjen = (String)job.get("KDJENMSPST");
			kodejen = (String)job.get("KODE_JENJANG");
			prodi = (String)job.get("PRODI");
			String tmp = kodpst+"`"+nmpst+"`"+kdfak+"`"+kdjen+"`"+kodejen+"`"+prodi;
			while(tmp.contains("``")) {
				tmp = tmp.replace("``","`null`");
			}
			li_lispro.add(tmp);
			
		}
		catch(JSONException e) {}//ignore
	}
}
li_lispro = v_list_kdpst.listIterator();
//info = nimhs+"`"+shift+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+stmhs+"`"+stpid+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+krklm+"`"+npm_pa+"`"+nmm_pa;
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
	while(info.contains("``")) {
		info = info.replace("``", "`null`");
	}
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
boolean allow_edit = false;
allow_edit = validUsr.isUsrAllowTo_updated("allowViewDataDosen",npm);
String lbl = "";
Vector v_tmp = null;
ListIterator li = null;
StringTokenizer st = null;
//String tkn_tipe_id = Constant.getValue("TIPE_ID");
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

input[type=text] {
	height:100%;
	width:100%;
	border:none;
	border-color:transparent;
	padding-left: 15px;
}

textarea:focus, input:focus{
    outline: none;
}

textarea { 
	width:100%;
	border: none; 
	border-color:transparent;
	padding-left: 15px;
}


</style>  
</head>
<body>

<div class="colmask fullpage">
	<div class="col1">
	<div style="min-height:500px;overflow:auto;">
	<form action="go.updDataProfileDosen" method="post">
		<input type="hidden" name="info" value="<%=info%>"/>
		<input type="hidden" name="id_obj" value="<%=id_obj%>"/>
		<input type="hidden" name="nmm" value="<%=nmm%>"/>
		<input type="hidden" name="npm" value="<%=npm%>"/>
		<input type="hidden" name="obj_lvl" value="<%=obj_lvl%>"/>
		<input type="hidden" name="kdpst" value="<%=kdpst%>"/>
		<input type="hidden" name="atMenu" value="<%=atMenu%>"/>
		<input type="hidden" name="cmd" value="<%=cmd%>"/>
		
		
		
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">DATA DOSEN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "STATUS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					
						String[]statOpt = Constants.getStatusDosen();
				%>
					<select class="show-tick form-control" data-live-search="true" name="statusDsn">
					
						<option value="null">----Wajib diisi----</option>
				<%
						for(int i=0;i<statOpt.length;i++) {
							st = new StringTokenizer(statOpt[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(status!=null && status.equalsIgnoreCase(kode)) {
				%>
						<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
						<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
					</select>
				<%	
					}
					else {
						out.print(status);
					}
				%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "SERDOS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<select class="show-tick form-control" data-live-search="true" name="serdos">
						<option value="null">Pilih Status</option>
<%
						String brs = "`SUDAH SERDOS`SEDANG DIAJUKAN`BELUM DIAJUKAN";
						//System.out.println("baris = "+brs);
						//System.out.println("tipe_id = "+tipe_id);
						st = new StringTokenizer(brs,"`");
						while(st.hasMoreTokens()) {
							String tipe = st.nextToken();
							if(serdos!=null && (tipe.toUpperCase().equalsIgnoreCase(serdos.toUpperCase()))) {
%>
						<option value="<%=tipe%>" selected="selected"><%=tipe.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=tipe%>"><%=tipe.toUpperCase() %></option>
<%	
							}
						}
%>          					
          			</select>
					<%	
					}
					else {
						%>
					<input type="text" name="serdos" value="<%=Checker.pnn_v1(serdos) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GELAR DEPAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_depan" value="<%=Checker.pnn_v1(gelar_depan) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_depan" value="<%=Checker.pnn_v1(gelar_depan) %>" readonly />
						<%
					}
					%>
					
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GELAR BELAKANG";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_belakang" value="<%=Checker.pnn_v1(gelar_belakang) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_belakang" value="<%=Checker.pnn_v1(gelar_belakang) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PT HOMEBASE";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="kdpti_home" value="<%=Checker.pnn_v1(kdpti_home) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="kdpti_home" value="<%=Checker.pnn_v1(kdpti_home) %>" readonly />
						<%
					}
					%>
					
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PRODI HOMEBASE";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<select class="show-tick form-control" data-live-search="true" name="kdpst_home">
						<option value="null">Pilih Prodi Homebase</option>
<%
						li_lispro = v_list_kdpst.listIterator();		
						while(li_lispro.hasNext()) {
							String brs = (String)li_lispro.next();
							StringTokenizer stt = new StringTokenizer(brs,"`");
							String kdpst_list = stt.nextToken();
							String nmpst_list = stt.nextToken();
							String kdfak_list = stt.nextToken();
							String kdjen_list = stt.nextToken();
							String kodejen_list = stt.nextToken();
							
							if(kdpst_home!=null && (kdpst_list.toUpperCase().equalsIgnoreCase(kdpst_home.toUpperCase()))) {
%>
						<option value="<%=kdpst_list%>" selected="selected"><%=nmpst_list.toUpperCase() %> [<%=kodejen_list%>]</option>
<%		
							}
							else {
%>
						<option value="<%=kdpst_list%>"><%=nmpst_list.toUpperCase() %> [<%=kodejen_list%>]</option>
<%	
							}
						}
%>          					
          			</select>
					<%	
					}
					else {
						String tmp = "";
						if(!Checker.isStringNullOrEmpty(kdpst_home)) {
							tmp = Converter.getDetailKdpst_v1(kdpst_home);
						}
						%>
					<input type="text" name="kdpst_home" value="<%=Checker.pnn_v1(tmp) %>" readonly />
						<%
					}
					%>
					
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL MASUK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tgl_in" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_in))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tgl_in" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_in))%>" readonly />
						<%
					}
					%>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL KELUAR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tgl_out" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_out))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tgl_out" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tgl_out))%>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NILAI KUM";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
						//System.out.println("pit1");
					%>
					<input type="text" name="tot_kum" value="<%=Checker.pnn_v1(tot_kum) %>" />
					<%	
					}
					else {
						//System.out.println("pit2");
						%>
					<input type="text" name="tot_kum" value="<%=Checker.pnn_v1(tot_kum) %>" readonly />
						<%
					}
					//System.out.println("pit3");
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TIPE IKA";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<select class="show-tick form-control" data-live-search="true" name="tipe_ika">
						<option value="null">Pilih Ikatan Kerja</option>
<%
						String brs = Constant.getValue("IKA_DOSEN");
						//System.out.println("baris = "+brs);
						//System.out.println("tipe_id = "+tipe_id);
						st = new StringTokenizer(brs,"`");
						while(st.hasMoreTokens()) {
							String tipe = st.nextToken();
							if(tipe_ika!=null && (tipe.toUpperCase().equalsIgnoreCase(tipe_ika.toUpperCase()))) {
%>
						<option value="<%=tipe%>" selected="selected"><%=tipe.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=tipe%>"><%=tipe.toUpperCase() %></option>
<%	
							}
						}
%>          					
          			</select>
					<%	
					}
					else {
						%>
					<input type="text" name="tipe_ika" value="<%=Checker.pnn_v1(tipe_ika) %>" readonly />
						<%
					}
					%>
					</td>
					
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit4");
					lbl = "JJA-LOKAL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
						String[]listJja = Constants.getListJenjangJabatanAkademik();
				%>
						<select class="show-tick form-control" data-live-search="true" name="jja_local">
						
							<option value="null">Pilih JJA</option>
				<%
						for(int i=0;i<listJja.length;i++) {
							st = new StringTokenizer(listJja[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(jja_local!=null && jja_local.equalsIgnoreCase(kode)) {
				%>
							<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
							<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
						</select>
					
					<%	
					}
					else {
						%>
					<input type="text" name="jja_local" value="<%=Checker.pnn_v1(jja_local) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "JJA-DIKTI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					//System.out.println("pit5");
					if(allow_edit) {
						String[]listJja = Constants.getListJenjangJabatanAkademik();
				%>
						<select class="show-tick form-control" data-live-search="true" name="jja_dikti">
						
							<option value="null">Pilih JJA</option>
				<%
						for(int i=0;i<listJja.length;i++) {
							st = new StringTokenizer(listJja[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(jja_dikti!=null && jja_dikti.equalsIgnoreCase(kode)) {
				%>
							<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
							<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
						</select>
					<%
					}
					else {
						%>
							<input type="text" name="jja_dikti" value="<%=Checker.pnn_v1(jja_dikti) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				
			</tbody>	
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">NO IDENTIFIKASI DOSEN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TIPE ID";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<select class="show-tick form-control" data-live-search="true" name="tipe_id">
						<option value="null">Pilih Tipe Id</option>
<%
						String brs = Constant.getValue("TIPE_ID");;
						//System.out.println("baris = "+brs);
						//System.out.println("tipe_id = "+tipe_id);
						st = new StringTokenizer(brs,"`");
						while(st.hasMoreTokens()) {
							String tipe = st.nextToken();
							if(ktp_sim_paspo!=null && (tipe.toUpperCase().equalsIgnoreCase(ktp_sim_paspo.toUpperCase()))) {
%>
						<option value="<%=tipe%>" selected="selected"><%=tipe.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=tipe%>"><%=tipe.toUpperCase() %></option>
<%	
							}
						}
%>          					
          			</select>
          			<%	
					}
					else {
						%>
					<input type="text" name="tipe_id" value="<%=Checker.pnn_v1(ktp_sim_paspo) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO ID";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="no_id" value="<%=Checker.pnn_v1(no_ktp_sim_paspo) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="no_id" value="<%=Checker.pnn_v1(no_ktp_sim_paspo) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit6");
					lbl = "NO NIDN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nidn" value="<%=Checker.pnn_v1(nidn) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nidn" value="<%=Checker.pnn_v1(nidn) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NIK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nik" value="<%=Checker.pnn_v1(nik) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nik" value="<%=Checker.pnn_v1(nik) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NIDK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nidk" value="<%=Checker.pnn_v1(nidk) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nidk" value="<%=Checker.pnn_v1(nidk) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NIP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					//System.out.println("pit7");
					if(allow_edit) {
					%>
					<input type="text" name="nip" value="<%=Checker.pnn_v1(nip) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nip" value="<%=Checker.pnn_v1(nip) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NUP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nup" value="<%=Checker.pnn_v1(nup) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nup" value="<%=Checker.pnn_v1(nup) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NIY-NIGK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="niy_nigk" value="<%=Checker.pnn_v1(niy_nigk) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="niy_nigk" value="<%=Checker.pnn_v1(niy_nigk) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit8");
					lbl = "NO NUPTK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nuptk" value="<%=Checker.pnn_v1(nuptk) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nuptk" value="<%=Checker.pnn_v1(nuptk) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO NSDMI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="nsdmi" value="<%=Checker.pnn_v1(nsdmi) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nsdmi" value="<%=Checker.pnn_v1(nsdmi) %>" readonly />
						<%
					}
					//System.out.println("pit9");
					%>
					</td>
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
					<textarea name="catatan_riwayat" style="width:100%;text-align:left" rows="5">
					<%
					//catatan_riwayat=catatan_riwayat.trim();
					if(catatan_riwayat!=null) {
						catatan_riwayat =catatan_riwayat.replace("<br>", System.getProperty("line.separator"));	
					}
					 
					//out.print(catatan_riwayat.contains("\t"));
					out.print(Checker.pnn(catatan_riwayat));
					%>
					
					</textarea>
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
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit9a");
					lbl = "NAMA PT ASAL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="pt_s1" value="<%=Checker.pnn_v1(pt_s1) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="pt_s1" value="<%=Checker.pnn_v1(pt_s1) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PRODI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					//System.out.println("pit10");
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					//System.out.println("pit9b");
					if(allow_edit) {
					%>
					<input type="text" name="kdpst_s1" value="<%=Checker.pnn_v1(kdpst_s1) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="kdpst_s1" value="<%=Checker.pnn_v1(kdpst_s1) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GELAR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_s1" value="<%=Checker.pnn_v1(gelar_s1) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_s1" value="<%=Checker.pnn_v1(gelar_s1) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit9c");
					lbl = "BIDANG ILMU";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="bidil_s1" value="<%=Checker.pnn_v1(bidil_s1) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="bidil_s1" value="<%=Checker.pnn_v1(bidil_s1) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit11");
					lbl = "NO IJAZAH";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="noija_s1" value="<%=Checker.pnn_v1(noija_s1) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="noija_s1" value="<%=Checker.pnn_v1(noija_s1) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL LULUS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tglls_s1" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s1))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tglls_s1" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s1))%>" readonly />
						<%
					}
					%>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN S2</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NAMA PT ASAL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="pt_s2" value="<%=Checker.pnn_v1(pt_s2) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="pt_s2" value="<%=Checker.pnn_v1(pt_s2) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PRODI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="kdpst_s2" value="<%=Checker.pnn_v1(kdpst_s2) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="kdpst_s2" value="<%=Checker.pnn_v1(kdpst_s2) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					//System.out.println("pit12");
					lbl = "GELAR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_s2" value="<%=Checker.pnn_v1(gelar_s2) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_s2" value="<%=Checker.pnn_v1(gelar_s2) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "BIDANG ILMU";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="bidil_s2" value="<%=Checker.pnn_v1(bidil_s2) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="bidil_s2" value="<%=Checker.pnn_v1(bidil_s2) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO IJAZAH";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="noija_s2" value="<%=Checker.pnn_v1(noija_s2) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="noija_s2" value="<%=Checker.pnn_v1(noija_s2) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL LULUS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tglls_s2" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s2))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tglls_s2" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s2))%>" readonly />
						<%
					}
					//System.out.println("pit13");
					%>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN S3</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NAMA PT ASAL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="pt_s3" value="<%=Checker.pnn_v1(pt_s3) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="pt_s3" value="<%=Checker.pnn_v1(pt_s3) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PRODI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="kdpst_s3" value="<%=Checker.pnn_v1(kdpst_s3) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="kdpst_s3" value="<%=Checker.pnn_v1(kdpst_s3) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GELAR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					//System.out.println("pit14");
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_s3" value="<%=Checker.pnn_v1(gelar_s3) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_s3" value="<%=Checker.pnn_v1(gelar_s3) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "BIDANG ILMU";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="bidil_s3" value="<%=Checker.pnn_v1(bidil_s3) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="bidil_s3" value="<%=Checker.pnn_v1(bidil_s3) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO IJAZAH";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="noija_s3" value="<%=Checker.pnn_v1(noija_s3) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="noija_s3" value="<%=Checker.pnn_v1(noija_s3) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL LULUS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tglls_s3" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s3))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tglls_s3" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_s3))%>" readonly />
						<%
					}
					//System.out.println("pit15");
					%>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:1.5em">RIWAYAT PENDIDIKAN GURU BESAR</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NAMA PT ASAL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="pt_gb" value="<%=Checker.pnn_v1(pt_gb) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="pt_gb" value="<%=Checker.pnn_v1(pt_gb) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PRODI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="kdpst_gb" value="<%=Checker.pnn_v1(kdpst_gb) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="kdpst_gb" value="<%=Checker.pnn_v1(kdpst_gb) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GELAR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="gelar_gb" value="<%=Checker.pnn_v1(gelar_gb) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="gelar_gb" value="<%=Checker.pnn_v1(gelar_gb) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "BIDANG ILMU";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="bidil_gb" value="<%=Checker.pnn_v1(bidil_gb) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="bidil_gb" value="<%=Checker.pnn_v1(bidil_gb) %>" readonly />
						<%
					}
					//System.out.println("pit16");
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NO IJAZAH";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="noija_gb" value="<%=Checker.pnn_v1(noija_gb) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="noija_gb" value="<%=Checker.pnn_v1(noija_gb) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TGL LULUS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(allow_edit) {
					%>
					<input type="text" name="tglls_gb" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_gb))%>" />
					<%	
					}
					else {
						%>
					<input type="text" name="tglls_gb" value="<%=Checker.pnn_v1(Converter.formatDdSlashMmSlashYy(tglls_gb))%>" readonly />
						<%
					}
					%>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: left; padding: 0px 10px;font-size:0.8em;height:55px">* Wajib Diisi
  					<center>
					<section class="gradient">
						<div style="text-align:right;padding:5px 0">						
						<button class="button1" type="submit" value="tombol_submit" name="tombol_asal" style="padding: 5px 50px;font-size: 20px;">UPDATE DATA</button>
						</div>
					</section>
					</center>
					</th>
				</tr>
				</thead>
			</tbody>
		</table>
	</form>
	</div>	
	</div>
</div>
</body>
</html>
