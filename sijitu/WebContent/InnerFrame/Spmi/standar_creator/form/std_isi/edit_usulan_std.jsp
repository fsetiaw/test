
<!DOCTYPE html>
<%@page import="org.codehaus.jackson.map.introspect.BasicClassIntrospector.GetterMethodFilter"%>
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
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@ page import="beans.dbase.spmi.request.*"%>
<%@ page import="beans.dbase.spmi.*"%>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<%
//System.out.println("okay1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
Boolean team_spmi = (Boolean) session.getAttribute("team_spmi");//asal jabatan ada mutu
boolean editor = spmi_editor.booleanValue();
boolean tim_spmi = team_spmi.booleanValue();
//String atMenu = request.getParameter("atMenu");

//System.out.println("okay3");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
//System.out.println("okay6");
Vector v_job = sdb.getListAvalablePihakTerkait_v1();
SearchRequest sr = new SearchRequest();

//System.out.println("okay7");
String mode = request.getParameter("mode");
String updated = request.getParameter("updated");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String id_master_std_form = request.getParameter("id_master_std");
String id_tipe_std_form = request.getParameter("id_tipe_std");
String isi_std_form = request.getParameter("isi_std");
String id_std_isi_form = request.getParameter("id_std_isi");
//String id_std_form = request.getParameter("id_std");
//String tkn_pengawas=request.getParameter("tkn_pengawas");
//String cakupan_std=request.getParameter("cakupan_std");
//String tipe_proses_pengawasan=request.getParameter("tipe_proses_pengawasan");
//System.out.println("id_tipe_std_form="+id_tipe_std_form);
//System.out.println("id_versi_form="+id_versi_form);
//System.out.println("id_std_isi_form="+id_std_isi_form);
//System.out.println("id_std_form="+id_std_form);
//System.out.println("id_master_std_form="+id_master_std_form);


Vector v = sr.getListNuStdReq(id_std_isi_form);
Vector v_err = (Vector)session.getAttribute("v_err");
session.removeAttribute("v_err");
SearchSpmi ss = new SearchSpmi();
Vector v_root = ss.getListMasterStandar();
StringTokenizer st = null;

session.removeAttribute("v_target");

Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST
%>




<style>


.table { 
	border: 1px solid #2980B9;
	background:<%=Constant.lightColorBlu()%>;
	 
}
.table thead > tr > th { 
	border-bottom: none;
	background: <%=Constant.darkColorBlu()%>;
	color:#369;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:#369;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }

</style>


 <script type="text/javascript">

jQuery(function() {
    jQuery('#id_master').change(function() {
        this.form.submit();
    });
});


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

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<!--  jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" / -->
	<%
	String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
	//System.out.println(target);
	String uri = request.getRequestURI();
	//System.out.println(uri);
	String url = PathFinder.getPath_v2(uri, target);
	//System.out.println(url);
	//<%=url >?id=<id_master_title >&id_tipe_std=id_tipe_std >&kdpst_nmpst_kmp=<kdpst_nmpst_kmp >&backto=<backto >&first_index=<first_index >&at_page=1&max_data_per_pg=<max_data_per_pg >
	
	%>
	
	
	<ul>	
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_v2.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
		
	</ul>
	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->

		<br>
		
<%
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
%>	
	</div>
	
<%	
}
if(true) {

	//String id_tipe = request.getParameter("id_tipe");
	//String kdpst_menu = request.getParameter("kdpst_menu");
	//String nmpst_menu = request.getParameter("nmpst_menu");
	//String kdkmp_menu = request.getParameter("kdkmp_menu");
	//String id_std_isi = request.getParameter("id_std_isi");
	//String id_versi = request.getParameter("id_versi");
	//System.out.println(" id_std_isi = "+id_std_isi);
	if(!Checker.isStringNullOrEmpty(id_std_isi_form)) {
		//v = (Vector)session.getAttribute("v_target");
		//System.out.println("kok1="+v.size());
		
		//kalo asalnya dari home_standard - masuk ke sini v>4
		//if(v==null || v.size()<1) {
		//System.out.println("kok2="+v.size());
		if(true) {
			v = sr.getInfoStd(Integer.parseInt(id_std_isi_form));
			//System.out.println("id_versi0="+id_versi);
		}
		
		//session.removeAttribute("v_target");
		ListIterator li = v.listIterator();
		String brs = (String)li.next();
		//System.out.println("baris == "+brs);
		st = new StringTokenizer(brs,"`");
	
		String id_std_isi = st.nextToken();
		String id_std = st.nextToken();
		//String id_master = request.getParameter("id_master");
		//String id_tipe = request.getParameter("id_tipe");
		//System.out.println("id_master0="+id_master);
		//System.out.println("id_tipe0="+id_tipe);
		String isi = st.nextToken();
		if(Checker.isStringNullOrEmpty(isi)) {
			isi = "";
		}
		String butir = st.nextToken();
		String kdpst_std = st.nextToken();
		String rasionale = st.nextToken();
		String id_versi = st.nextToken();
		String id_declare = st.nextToken();
		String id_do = st.nextToken();
		String id_eval = st.nextToken();
		String id_control = st.nextToken();
		String id_upgrade = st.nextToken();
		String tglsta = st.nextToken();
		String tglend = st.nextToken();
		String thsms1 = st.nextToken();
		String thsms2 = st.nextToken();
		String thsms3 = st.nextToken();
		String thsms4 = st.nextToken();
		String thsms5 = st.nextToken();
		String thsms6 = st.nextToken();
		String pihak = st.nextToken();
		String tkn_doc = st.nextToken();
		//System.out.println("tkn_doc1 = "+tkn_doc);
		String tkn_indikator = st.nextToken();
		String norut = st.nextToken();
		
		String periode_awal = st.nextToken();
		String unit_period = st.nextToken();
		String lama_per_period = st.nextToken();
		String target_unit1 = st.nextToken();
		String target_unit2 = st.nextToken();
		String target_unit3 = st.nextToken();
		String target_unit4 = st.nextToken();
		String target_unit5 = st.nextToken();
		String target_unit6 = st.nextToken();
		String tkn_param = "null";
		if(st.hasMoreTokens()) {
			tkn_param = st.nextToken();
		}	
			
		//+target_unit6+"`"+tkn_param+"`"+id_master+"`"+id_tipe+"`"+tkn_pengawas+"`"+scope+"`"+tipe_survey+"`"+aktif+"`"+kdpst+"`"+strategi;	
		//if(Checker.isStringNullOrEmpty(id_master) && st.hasMoreTokens()) {
		String id_master_std=null;
		if(st.hasMoreTokens()) {	
			id_master_std = st.nextToken();	
		}
		//System.out.println("id_master-2="+id_master);
		String id_tipe_std=null;
		if(st.hasMoreTokens()) {	
			id_tipe_std = st.nextToken();
		}	
		

	%>

	 
		<form action="go.ubahUsulanToStd" method="post">
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
			<input type="hidden" name="id_std_isi" value="<%=id_std_isi_form %>"/>

			<table class="table" width="90%">
				<thead>
					<tr>
	  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:2.5em;color:#fff">FORM STANDARISASI USULAN</th>
	  				</tr>
	  				<!--  tr>
	  					<th width="5%">NO</th>
	  					<th width="20%">PRODI</th>
	  					<th width="20%">LOKASI</th>
	  					<th width="55%">STATUS PERSETUJUAN</th>
	  				</tr-->
	  			</thead>
	  			<tbody>
	  				<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >RASIONALE USULAN </td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="vertical-align: middle; padding: 10px 10px;background:white;font-weight:bold;font-size:2em;text-align:center" >
							<%=rasionale %>
						</td>
					</tr>	
					<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >DRAFT RUMUSAN PERNYATAAN ISI STANDAR</td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
							<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5" required><%=Checker.pnn(isi_std_form)%></textarea>
						</td>
					</tr>		
					<!--  tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;text-align:center;font-size:2em" >LINGKUP CAKUPAN & KLASIFIKASI STANDAR MUTU</td>
					</tr-->
					<%
					/*
					%>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" title="Pilih Tingkat/Level Unit Satuan yg akan diukur" >
						LINGKUP CAKUPAN STANDARD
						</td>
						
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
					<%
			if(editor) {
				%>	
					<select name="cakupan_std"  style="width:100%;height:35px;border:none;text-align-last:center;">
	<%
			
			}
			else {		
					%>	
					<select name="cakupan_std"  style="width:100%;height:35px;border:none;text-align-last:center;" disabled>
			<%
			}
			%>			
							<option value="null">-PILIH LINGKUP CAKUPAN STANDAR-</option>
							<%
							if(!Checker.isStringNullOrEmpty(cakupan_std) && cakupan_std.equalsIgnoreCase("univ")) {
							%>
							<option value="univ" selected="selected">UNIVERSITAS</option>
							<%	
							}	
							else {
							%>
							<option value="univ">UNIVERSITAS</option>
							<%	
							}
							if(!Checker.isStringNullOrEmpty(cakupan_std) && cakupan_std.equalsIgnoreCase("biro")) {
								%>
								<option value="biro" selected="selected">BIRO/LEMBAGA/UNIT KERJA</option>
								<%	
							}	
							else {
								%>
								<option value="biro">BIRO/LEMBAGA/UNIT KERJA</option>
								<%	
							}
							
					
							if(!Checker.isStringNullOrEmpty(cakupan_std) && cakupan_std.equalsIgnoreCase("fak")) {
								%>
							<option value="fak" selected="selected">FAKULTAS</option>
								<%	
							}	
							else {
								%>
							<option value="fak">FAKULTAS</option>
								<%	
							}
						
							if(!Checker.isStringNullOrEmpty(cakupan_std) && cakupan_std.equalsIgnoreCase("prodi")) {
								%>
							<option value="prodi" selected="selected">PRODI</option>
								<%	
							}	
							else {
								%>
							<option value="prodi">PRODI</option>
								<%	
							}
							%>
						</td>
						
					</tr>
					<%
					*/
					%>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >RUMPUN STANDAR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="id_master_std" id="id_master"  style="width:100%;height:40px;border:none;text-align-last:center;">
							<option value="null">-PILIH RUMPUN STANDAR-</option>
						<%
						ListIterator lir = v_root.listIterator();
						while(lir.hasNext()) {
							brs = (String)lir.next();
							st = new StringTokenizer(brs,"`");
							//System.out.println(brs+" vs "+id_master);
							String id_root = st.nextToken();
							String keter = st.nextToken();
							if(id_root.equalsIgnoreCase(id_master_std_form)) {
								%>
							<option value="<%=id_root %>" selected="selected"><%=keter %></option>
							<%		
							}
							else {
							%>
							<option value="<%=id_root %>"><%=keter %></option>
						<%		
							}
						}
						%>
							
						</select>
						</td>
					</tr>	
					<%
					if(!Checker.isStringNullOrEmpty(id_master_std_form)) {
					%>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TIPE STANDAR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select  name="id_tipe_std"  style="width:100%;height:40px;border:none;text-align-last:center;">
							<option value="null">-PILIH TIPE STANDAR-</option>
						<%
						
						v_root = ss.getListTipeStandar(Integer.parseInt(id_master_std_form));
						lir = v_root.listIterator();
						while(lir.hasNext()) {
							brs = (String)lir.next();
							//id_std+"`"+id_tipe_std+"`"+ket
							st = new StringTokenizer(brs,"`");
							String id_std_tmp = st.nextToken();
							id_tipe_std = st.nextToken();
							String keter = st.nextToken();
							if(id_tipe_std.equalsIgnoreCase(id_tipe_std_form)) {
								%>
							<option value="<%=id_tipe_std %>" selected="selected"><%=keter %></option>
							<%		
							}
							else {
							%>
							<option value="<%=id_tipe_std %>"><%=keter %></option>
						<%		
							}
						}
						%>
							
						</select>
						</td>
					</tr>	
					<%	
					}
					/*
					%>
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;text-align:center;font-size:2em" >LAMA PERIODE PER PERIODE DAN SATUAN YG DIGUNAKAN SERTA PERIODE AWAL (I)</td>
					</tr>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >SATUAN PERIODE
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="periode_unit_used" style="width:100%;height:35px;border:none;text-align-last:center">
						<%
						if(!Checker.isStringNullOrEmpty(unit_period)) {
							if(unit_period.equalsIgnoreCase("thn")) {
						
						%>
							<option value="thn" selected="selected">TAHUNAN</option>
						<%	
							}
							else {
						%>
							<option value="thn">TAHUNAN</option>
						<%	
							}
							if(unit_period.equalsIgnoreCase("sms")) {
								
						%>
							<option value="sms" selected="selected">SEMESTERAN</option>
						<%	
							}
							else {
						%>
							<option value="sms">SEMESTERAN</option>
						<%	
							}
						}
						else {
						%>
							<option value="thn">TAHUNAN</option>
							<option value="sms">SEMESTERAN</option>
						<%	
						}
						%>
							
							
						</select>
						</td>
					</tr>
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >LAMA/PERIODE
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(lama_per_period) && !lama_per_period.equalsIgnoreCase("0")) {
						%>
							<input  type="number" value="<%=lama_per_period %>" placeholder="1" name="qtt_unit_per_period" style="height:35px;text-align:center;width:100%;border:none"  required />
						<%	
						}
						else {
						%>
							<input  type="number" placeholder="1" name="qtt_unit_per_period" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%
						}
						%>
						</td>
					</tr>
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >AWAL PERIODE [PERIODE I] 
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(periode_awal)) {
							%>
							<input  type="text" placeholder="2016/20161" value="<%=periode_awal %>" name="periode_awal" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="2016/20161" name="periode_awal" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;text-align:center;font-size:2em" >PARAMETER, INDIKATOR & STRATEGI CAPAIAN & BESARAN DAN SATUAN TARGET CAPAIAN </td>
					</tr>	
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >PARAMETER
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(tkn_param)) {
							%>
							<input  type="text" placeholder="isi nama parameter / variable" value="<%=tkn_param %>" name="tkn_variable" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="isi nama parameter / variable" name="tkn_variable" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
							
						</td>
					</tr>
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >INDIKATOR CAPAIAN 
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(tkn_indikator)) {
							%>
							<input  type="text" placeholder="" value="<%=tkn_indikator %>" name="tkn_indikator" style="height:35px;text-align:center;width:100%;border:none" required />
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="" name="tkn_indikator" style="height:35px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
							
						</td>
					</tr>
					<!--  tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;text-align:center;font-size:2em" >STRATEGI PENCAPAIAN ISI STANDAR</td>
					</tr-->	
					<tr>	
						<td align="left" style="vertical-align: TOP; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >STRATEGI PELAKSANAAN STANDAR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						String tmp = "Strategi untuk pencapaian standar harap diisi";
						if(Checker.isStringNullOrEmpty(strategi)) {
						%>
							<textarea name="strategi" placeholder="<%=tmp %>" style="width:100%;height:100px;border:none;rows:5" required></textarea>
						<%
						}
						else {
						%>
							<textarea name="strategi" placeholder="<%=tmp %>" style="width:100%;height:100px;border:none;rows:5" required><%=strategi %></textarea>
						<%
						}
						%>	
						</td>
					</td>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE I</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE II</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE III</td>
					</tr>
					<tr>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms1)) {
							%>
							<input type="number" step="0.01" name="target1" value="<%=thsms1 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target1" style="height:35px;text-align:center;width:50%;border:none" required/>
						<%
						}
						%>
						<br>[<input type="text" name="unit1" style="height:35px;text-align:center;width:50%;border:none" value="<%=Checker.pnn(target_unit1) %>" placeholder="isikan unit-satuan"/>
						<!--  	
							<select name="unit1" style="height:35px;text-align:center;width:20%;border:none" >
						<%
						if(!Checker.isStringNullOrEmpty(target_unit1)) {
							if(target_unit1.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit1.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							-->
							]
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms2)) {
							%>
							<input type="number" step="0.01" name="target2" value="<%=thsms2 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target2" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%
						}
						if(!Checker.isStringNullOrEmpty(target_unit2)) {
						%>
							[<input type="text" name="unit2" style="height:35px;text-align:center;width:20%;border:none" value="<%=Checker.pnn(target_unit2) %>" readonly="readonly" />]
						<%
						}
						else {
							%>
							<input type="hidden" name="unit2" value="" />
						<%
						}
						%>
						<!-- 
						[
							<select name="unit2" style="height:35px;text-align:center;width:20%;border:none">
						<%
						if(!Checker.isStringNullOrEmpty(target_unit2)) {
							if(target_unit2.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit2.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							]
							 -->
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms3)) {
							%>
							<input type="number" step="0.01" name="target3" value="<%=thsms3 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target3" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%
						}
						if(!Checker.isStringNullOrEmpty(target_unit3)) {
						%>
							[<input type="text" name="unit3" style="height:35px;text-align:center;width:20%;border:none" value="<%=Checker.pnn(target_unit3) %>" readonly="readonly" />]
						<%
						}
						else {
							%>
							<input type="hidden" name="unit3" value="" />
						<%
						}
						%>
						<!-- 
						[
							<select name="unit3" style="height:35px;text-align:center;width:20%;border:none">
						<%
						if(!Checker.isStringNullOrEmpty(target_unit3)) {
							if(target_unit3.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit3.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							]
						-->	
						</td>
					</tr>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE IV</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE V</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold;font-size:1.5em" >TARGET BESARAN PARAMETER PERIODE VI</td>
					</tr>
					<tr>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms4)) {
							%>
							<input type="number" step="0.01" name="target4" value="<%=thsms4 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target4" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%
						}
						if(!Checker.isStringNullOrEmpty(target_unit4)) {
						%>
							[<input type="text" name="unit4" style="height:35px;text-align:center;width:20%;border:none" value="<%=Checker.pnn(target_unit4) %>" readonly="readonly" />]
						<%
						}
						else {
							%>
							<input type="hidden" name="unit4" value="" />
						<%
						}
						%>
						<!-- 
						[
							<select name="unit4" style="height:35px;text-align:center;width:20%;border:none">
						<%
						if(!Checker.isStringNullOrEmpty(target_unit4)) {
							if(target_unit4.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit4.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							]
						-->	
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms5)) {
							%>
							<input type="number" step="0.01" name="target5" value="<%=thsms5 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target5" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%
						}
						if(!Checker.isStringNullOrEmpty(target_unit5)) {
						%>
							[<input type="text" name="unit5" style="height:35px;text-align:center;width:20%;border:none" value="<%=Checker.pnn(target_unit5) %>" readonly="readonly" />]
						<%
						}
						else {
							%>
							<input type="hidden" name="unit5" value="" />
						<%
						}
						%>
						<!-- 
						[
							<select name="unit5" style="height:35px;text-align:center;width:20%;border:none">
						<%
						if(!Checker.isStringNullOrEmpty(target_unit5)) {
							if(target_unit5.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit5.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							]
						-->	
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms6)) {
							%>
							<input type="number" step="0.01" name="target6" value="<%=thsms6 %>" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" step="0.01" name="target6" style="height:35px;text-align:center;width:70%;border:none" required/>
						<%
						}
						if(!Checker.isStringNullOrEmpty(target_unit6)) {
						%>
							[<input type="text" name="unit6" style="height:35px;text-align:center;width:20%;border:none" value="<%=Checker.pnn(target_unit6) %>" readonly="readonly" />]
						<%
						}
						else {
							%>
							<input type="hidden" name="unit6" value="" />
						<%
						}
						%>
						<!-- 
						[
							<select name="unit6" style="height:35px;text-align:center;width:20%;border:none">
						<%
						if(!Checker.isStringNullOrEmpty(target_unit6)) {
							if(target_unit6.equalsIgnoreCase("percent")) {
						%>
								<option value="percent" selected="selected">%</option>
						<%		
							}
							else {
								%>
								<option value="percent">%</option>
						<%	
							}
							if(target_unit6.equalsIgnoreCase("unit")) {
								%>
								<option value="unit" selected="selected">unit</option>
								<%		
							}
							else {
								%>
								<option value="unit">unit</option>
								<%	
							}
						}
						else {
						%>	
								<option value="percent">%</option>
								<option value="unit">unit</option>
						<%
						}
						%>		
							</select>
							]
						-->	
						</td>
					</tr>
					
					
					<!--  tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#369;font-weight:bold;text-align:center" >TIPE PROSES PENGAWASAN</td>
					</tr>	
					<tr>	
						<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="tipe_proses_pengawasan" style="width:100%;height:35px;border:none;text-align-last:center">
						<%
						if(!Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {
							//System.out.println("1");
							if(tipe_proses_pengawasan.equalsIgnoreCase("std")) {
								//System.out.println("1a");
						
						%>
							<option value="std" selected="selected">NORMAL [BERKESINAMBUNGAN]</option>
						<%	
							}
							else {
						%>
							<option value="std">NORMAL [BERKESINAMBUNGAN]</option>
						<%	
							}
							
							if(tipe_proses_pengawasan.equalsIgnoreCase("harian")) {
								//System.out.println("1b");
								
						%>
							<option value="harian" selected="selected">BERULANG HARIAN </option>
						<%	
							}
							else {
						%>
							<option value="harian">BERULANG HARIAN </option>
						<%	
							}
							if(tipe_proses_pengawasan.equalsIgnoreCase("mingguan")) {
								//System.out.println("1c");
						%>
							<option value="mingguan" selected="selected">BERULANG MINGGUAN </option>
						<%	
							}
							else {
						%>
							<option value="mingguan">BERULANG MINGGUAN </option>
						<%	
							}
							if(tipe_proses_pengawasan.equalsIgnoreCase("bulanan")) {
								//System.out.println("1d");
						%>
							<option value="bulanan" selected="selected">BERULANG BULANAN </option>
						<%	
							}
							else {
						%>
							<option value="bulanan">BERULANG BULANAN </option>
						<%	
							}
							if(tipe_proses_pengawasan.equalsIgnoreCase("semesteran")) {
								//System.out.println("1e");
								%>
							<option value="semesteran" selected="selected">BERULANG SEMESTERAN </option>
								<%	
							}
							else {
								%>
							<option value="semesteran">BERULANG SEMESTERAN </option>
								<%	
							}
							if(tipe_proses_pengawasan.equalsIgnoreCase("tahunan")) {
								//System.out.println("1f");
								%>
							<option value="tahunan" selected="selected">BERULANG TAHUNAN </option>
								<%	
							}
							else {
							%>
							<option value="tahunan">BERULANG TAHUNAN </option>
							<%	
							}
						}
						else {
						%>
							<option value="null">-PILIH TIPE PENGAWASAN-</option>
							<option value="std">NORMAL [BERKESINAMBUNGAN]</option>
							<option value="harian">BERULANG HARIAN</option>
							<option value="mingguan">BERULANG MINGGUAN</option>
							<option value="bulanan">BERULANG BULANAN</option>
							<option value="semesteran">BERULANG SEMESTERAN</option>
							<option value="tahunan">BERULANG TAHUNAN</option>
						<%	
						}
						*/
						%>
							
							
						</select>
						</td>
					</tr-->
					
					<tr>
						<td colspan="4" style="padding:5px 0px">
							<section class="gradient" style="text-align:center">
		            			<button style="padding: 5px 50px;font-size: 20px;" value="std_usulan" name="tombol">UPDATE STANDAR ISI</button>
	        				</section>
						</td>		
					</tr>	
	  			</tbody>
			</table>
			<br>
			</form>
	<%
		//}
		//id+"`"+id_std+"`"+id_master+"`"+id_tipe+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+norut+"`"+target_period_start+"`"+unit_period+"`"+lama_per_period+"`"+target_unit1+"`"+target_unit2+"`"+target_unit3+"`"+target_unit4+"`"+target_unit5+"`"+target_unit6
		
	}
}
%>
	</div>
</div>		
</body>
</html>	