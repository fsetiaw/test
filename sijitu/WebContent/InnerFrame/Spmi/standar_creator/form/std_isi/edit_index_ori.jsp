
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
//System.out.println("okay2");
String atMenu = request.getParameter("atMenu");
String mode = request.getParameter("mode");
//System.out.println("okay3");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
//System.out.println("okay4");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//System.out.println("okay5");
String updated = request.getParameter("updated");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
//System.out.println("okay6");
Vector v_job = sdb.getListAvalablePihakTerkait_v1();
SearchRequest sr = new SearchRequest();
Vector v = sr.getListNuStdReq();
//System.out.println("okay7");
String id_master_title = request.getParameter("id_master");
String id_std_isi = request.getParameter("id_std_isi");
String id_versi = request.getParameter("id_versi");
//System.out.println("mode="+mode);
//System.out.println("id_master_title="+id_master_title);
//System.out.println("id_std_isi="+id_std_isi);
//System.out.println("id_versi="+id_versi);
Vector v_err = (Vector)session.getAttribute("v_err");
session.removeAttribute("v_err");
SearchSpmi ss = new SearchSpmi();
Vector v_root = ss.getListMasterStandar();
//id_tipe
//String id_master = request.getParameter("id_master");
//String id_tipe=request.getParameter("id_tipe");
String tkn_pengawas=request.getParameter("tkn_pengawas");
String cakupan_std=request.getParameter("cakupan_std");
String tipe_proses_pengawasan=request.getParameter("tipe_proses_pengawasan");
session.removeAttribute("v_target");
String id_tipe_title = request.getParameter("id_tipe");
//System.out.println("id_tipe_title="+id_tipe_title);
String at_page = request.getParameter("at_page");
String max_data_per_pg = request.getParameter("max_data_per_pg");
String first_index = request.getParameter("first_index");
//String backto = request.getParameter("backto");
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
<%
if(mode.equalsIgnoreCase("list")) {
%>
	tr:hover td { background:#82B0C3 }
<%	
}
%>

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
	
	<%
	if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("list")) {
	%>
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" />
	<%
	}
	else if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("std_non_aktif")) {
		%>
		
	<ul>	
		<li><a href="go.getListAllStd?mode=std_non_aktif&id_tipe_std=<%=id_tipe_title %>&id_master_std=<%=id_master_title %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">BACK<span><b style="color:#eee">---</b></span></a></li>
	</ul>
		<%
	}
	else {
		%>
	<ul>	
		<li><a href="<%=url %>?first_index=<%=first_index %>&id_tipe_std=<%=id_tipe_title %>&id=<%=id_master_title %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>">BACK<span><b style="color:#eee">---</b></span></a></li>
	</ul>
	<%
	}
	%>	
	
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
if(mode.equalsIgnoreCase("list")) {
	//System.out.println("list mode");
	if(v==null||v.size()<1) {
%>
		<div style="font-weight:bold;font-size:1.5em;text-align:center">
		Usulan Penetapan Standar: 0 
		</div>
<%	
	}
	else {
%>
		<center>
		
		<table class="table" style="width:90%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST USULAN STANDARISASI</th>
  				</tr>
  				<tr>
  					<th width="10%">NO</th>
  					<th width="90%">RASIONALE&nbsp/&nbspALASAN PENGAJUAN&nbsp/&nbsp MASALAH YG SERING TERJADI DI LAPANGAN</th>
  				</tr>
  			</thead>
  			<tbody>
<%	
		int norut = 1;
		ListIterator li = v.listIterator();
		while(li.hasNext()) {
		
			String brs = (String)li.next();
			//System.out.println("baris = "+brs);
			st = new StringTokenizer(brs,"`");
		//id+"`"+id_turun+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale
			String id = st.nextToken();
			String id_std = st.nextToken();
			String isi = st.nextToken();
			String butir = st.nextToken();
			String kdpst_std = st.nextToken();
			String rasionale = st.nextToken();
%>
				<tr onclick="location.href='edit_index.jsp?mode=edit_list_view&id_std_isi=<%=id%>&atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'">
					<td align="center" style="vertical-align: middle; padding: 5px 5px"><%=norut++ %></td>
					<td style="vertical-align: middle;text-align:left; padding: 5px 5px"><%=Checker.pnn(rasionale) %></td>
				</tr>	
<%		
		}
%>
			</tbody>	
		</table>
			
<%	
	}
}
else {
	//edit mode || edit_list_view
	//System.out.println("edit mode");
	
	atMenu = request.getParameter("atMenu");
	//String id_tipe = request.getParameter("id_tipe");
	String kdpst_menu = request.getParameter("kdpst_menu");
	String nmpst_menu = request.getParameter("nmpst_menu");
	String kdkmp_menu = request.getParameter("kdkmp_menu");
	//String id_std_isi = request.getParameter("id_std_isi");
	//String id_versi = request.getParameter("id_versi");
	//System.out.println(" id_std_isi = "+id_std_isi);
	if(!Checker.isStringNullOrEmpty(id_std_isi)) {
		//v = (Vector)session.getAttribute("v_target");
		//System.out.println("kok1="+v.size());
		
		//kalo asalnya dari home_standard - masuk ke sini v>4
		//if(v==null || v.size()<1) {
		//System.out.println("kok2="+v.size());
		if(Checker.isStringNullOrEmpty(id_versi)) {
			v = sr.getInfoStd(Integer.parseInt(id_std_isi));
			//System.out.println("id_versi0="+id_versi);
		}
		else {
			v = sr.getInfoStd(Integer.parseInt(id_std_isi),Integer.parseInt(id_versi));
			//System.out.println("id_std_isi0="+id_std_isi+" & id_versi0= "+id_versi);
		}
		//session.removeAttribute("v_target");
		ListIterator li = v.listIterator();
		String brs = (String)li.next();
		//System.out.println("baris == "+brs);
		st = new StringTokenizer(brs,"`");
	
		String id = st.nextToken();
		String id_std = st.nextToken();
		String id_master = request.getParameter("id_master");
		String id_tipe = request.getParameter("id_tipe");
		//System.out.println("id_master0="+id_master);
		//System.out.println("id_tipe0="+id_tipe);
		String isi = st.nextToken();
		if(Checker.isStringNullOrEmpty(isi)) {
			isi = "";
		}
		String butir = st.nextToken();
		String kdpst_std = st.nextToken();
		String rasionale = st.nextToken();
		String versi = st.nextToken();
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
			
			
		//if(Checker.isStringNullOrEmpty(id_master) && st.hasMoreTokens()) {
		if(st.hasMoreTokens()) {	
			String tmp =  st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)&&!tmp.equalsIgnoreCase("0")) {
				id_master = new String(tmp);	
			}
			//id_master = st.nextToken();	
		}
		//System.out.println("id_master-2="+id_master);
		//if(Checker.isStringNullOrEmpty(id_tipe) && st.hasMoreTokens()) {
		if(st.hasMoreTokens()) {
			String tmp =  st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)) {
				id_tipe = new String(tmp);	
			}
			//id_tipe = st.nextToken();
		}
		
		//if(Checker.isStringNullOrEmpty(tkn_pengawas) && st.hasMoreTokens()) {
		if(st.hasMoreTokens()) {
			String tmp =  st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)) {
				tkn_pengawas = new String(tmp);	
			}
			//tkn_pengawas = st.nextToken();	
		}
		//if(Checker.isStringNullOrEmpty(cakupan_std) && st.hasMoreTokens()) {
		if(st.hasMoreTokens()) {	
			String tmp =  st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)) {
				cakupan_std = new String(tmp);	
			}
			//cakupan_std = st.nextToken();	
		}
		//if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan) && st.hasMoreTokens()) {
		if(st.hasMoreTokens()) {	
			String tmp =  st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)) {
				tipe_proses_pengawasan = new String(tmp);	
			}
			//tipe_proses_pengawasan = st.nextToken();	
		}
		//System.out.println("cakupan_std="+cakupan_std);
	%>

	 
			<form action="ins.makeItStd" method="post">
			<input type="hidden" name="atMenu" value="<%=atMenu %>"/>
	<%
	if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("std_non_aktif")) {
		%>		
			<input type="hidden" name="mode" value="<%=mode%>"/>
		<%
	
	}
	else {
	%>		
			<input type="hidden" name="mode" value="view"/>
	<%
	}
	%>		
			<input type="hidden" name="id_tipe_std" value="<%=id_tipe%>"/>
			<input type="hidden" name="id_master_std" value="<%=id_master%>"/>
			<input type="hidden" name="id" value="<%=id_master%>"/>
			<input type="hidden" name="first_index" value="<%=first_index%>"/>
			<input type="hidden" name="versi" value="<%=versi%>"/>
			
	<%
			String backTo = request.getParameter("backTo");
			if(!Checker.isStringNullOrEmpty(backTo)) {
				//String at_page = request.getParameter("at_page");
				//String max_data_per_pg = request.getParameter("max_data_per_pg");
				
	%>
			<input type="hidden" name="backTo" value="<%=backTo %>"/>
			<input type="hidden" name="at_page" value="<%=at_page %>"/>
			<input type="hidden" name="max_data_per_pg" value="<%=max_data_per_pg %>"/>
			

	<%			
			}
	%>		
			
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
			<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>"/>
			
			<table class="table" width="90%">
				<thead>
					<tr>
	  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">FORM PERNYATAAN ISI STANDAR</th>
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
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >ALASAN DIBUTUHKAN STANDAR</td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
							<textarea name="rasionale" style="width:100%;height:100px;border:none;rows:5" required><%=rasionale %></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PERNYATAAN ISI STANDAR</td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
							<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5" required><%=isi%></textarea>
						</td>
					</tr>		
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;text-align:center" >LINGKUP CAKUPAN & KLASIFIKASI STANDAR MUTU</td>
					</tr>
					<tr>
					
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" title="Pilih Tingkat/Level Unit Satuan yg akan diukur" >
						LINGKUP CAKUPAN STANDARD
						</td>
						
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="cakupan_std"  style="width:100%;height:25px;border:none;text-align-last:center;">
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
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RUMPUN STANDAR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="id_master" id="id_master"  style="width:100%;height:25px;border:none;text-align-last:center;">
							<option value="null">-PILIH RUMPUN STANDAR-</option>
						<%
						ListIterator lir = v_root.listIterator();
						while(lir.hasNext()) {
							brs = (String)lir.next();
							st = new StringTokenizer(brs,"`");
							//System.out.println(brs+" vs "+id_master);
							String id_root = st.nextToken();
							String keter = st.nextToken();
							if(id_root.equalsIgnoreCase(id_master)) {
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
					if(!Checker.isStringNullOrEmpty(id_master)) {
					%>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GOLONGAN STANDAR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select  name="id_tipe"  style="width:100%;height:25px;border:none;text-align-last:center;">
							<option value="null">-PILIH TIPE STANDAR-</option>
						<%
						
						v_root = ss.getListTipeStandar(Integer.parseInt(id_master));
						lir = v_root.listIterator();
						while(lir.hasNext()) {
							brs = (String)lir.next();
							//id_std+"`"+id_tipe_std+"`"+ket
							st = new StringTokenizer(brs,"`");
							String id_std_tmp = st.nextToken();
							String id_tipe_std = st.nextToken();
							String keter = st.nextToken();
							if(id_tipe_std.equalsIgnoreCase(id_tipe_title)) {
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
					%>
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;text-align:center" >LAMA PERIODE PER PERIODE DAN SATUAN YG DIGUNAKAN SERTA PERIODE AWAL (I)</td>
					</tr>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SATUAN PERIODE
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="periode_unit_used" style="width:100%;height:25px;border:none;text-align-last:center">
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
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >LAMA/PERIODE
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(lama_per_period) && !lama_per_period.equalsIgnoreCase("0")) {
						%>
							<input  type="number" value="<%=lama_per_period %>" placeholder="1" name="qtt_unit_per_period" style="height:25px;text-align:center;width:100%;border:none"  required />
						<%	
						}
						else {
						%>
							<input  type="number" placeholder="1" name="qtt_unit_per_period" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%
						}
						%>
						</td>
					</tr>
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >AWAL PERIODE [PERIODE I] 
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(periode_awal)) {
							%>
							<input  type="text" placeholder="2016/20161" value="<%=periode_awal %>" name="periode_awal" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="2016/20161" name="periode_awal" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;text-align:center" >TARGET PER PERIODE DAN SATUAN SERTA INDIKATOR YG DIGUNAKAN</td>
					</tr>	
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PARAMETER YG DIUKUR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(tkn_param)) {
							%>
							<input  type="text" placeholder="isi nama parameter / variable" value="<%=tkn_param %>" name="tkn_variable" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="isi nama parameter / variable" name="tkn_variable" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
							
						</td>
					</tr>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE I</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE II</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE III</td>
					</tr>
					<tr>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms1)) {
							%>
							<input type="number" name="target1" value="<%=thsms1 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target1" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit1" style="height:25px;text-align:center;width:20%;border:none" >
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
							]
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms2)) {
							%>
							<input type="number" name="target2" value="<%=thsms2 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target2" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit2" style="height:25px;text-align:center;width:20%;border:none">
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
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms3)) {
							%>
							<input type="number" name="target3" value="<%=thsms3 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target3" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit3" style="height:25px;text-align:center;width:20%;border:none">
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
						</td>
					</tr>
					<tr>
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE IV</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE V</td>
						<td align="center" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TARGET BESARAN PARAMETER PERIODE VI</td>
					</tr>
					<tr>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms4)) {
							%>
							<input type="number" name="target4" value="<%=thsms4 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target4" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit4" style="height:25px;text-align:center;width:20%;border:none">
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
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms5)) {
							%>
							<input type="number" name="target5" value="<%=thsms5 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target5" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit5" style="height:25px;text-align:center;width:20%;border:none">
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
						</td>
						<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(thsms6)) {
							%>
							<input type="number" name="target6" value="<%=thsms6 %>" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%	
						}
						else {
						%>
							<input type="number" name="target6" style="height:25px;text-align:center;width:70%;border:none" required/>
						<%
						}
						%>
						[
							<select name="unit6" style="height:25px;text-align:center;width:20%;border:none">
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
						</td>
					</tr>
					<tr>	
						<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >INDIKATOR
						</td>
						<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<%
						if(!Checker.isStringNullOrEmpty(tkn_indikator)) {
							%>
							<input  type="text" placeholder="" value="<%=tkn_indikator %>" name="tkn_indikator" style="height:25px;text-align:center;width:100%;border:none" required />
						<%	
						}
						else {
						%>
							<input  type="text" placeholder="" name="tkn_indikator" style="height:25px;text-align:center;width:100%;border:none" required/>
						<%	
						}
						%>
							
						</td>
					</tr>
					<tr>
						<td colspan="4" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;text-align:center" >TIPE PROSES PENGAWASAN</td>
					</tr>	
					<tr>	
						<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="tipe_proses_pengawasan" style="width:100%;height:25px;border:none;text-align-last:center">
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
						%>
							
							
						</select>
						</td>
					</tr>
					<tr>
						<%
						String info="Dokumen Terkait:\n&nbsp &nbsp Seluruh dokumen yang dibutuhkan selama siklus mutu. Dimulai dari dokumen terkait dalam Penetapan, Pelaksanaa, Evaluasi dan Pengendalian serta Peningkatan Standar Mutu";
						%>
						<td colspan="3" style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;">
							<table width="100%" style="border:none">
								<tr>
									<td width="75%" style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;font-size:1.6em" title="<%=info%>">DOKUMEN TERKAIT</td>
									<td width="20%" style="padding:0 5px 0 0;text-align:right;border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >
									Tambah Dokumen Terkait
									</td>
									<td width="5%" style="border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >			
									<section class="gradient" style="text-align:right;vertical-align: top;">
		            					<button style="font-size: 50px;" value="dok_terkait" name="tombol">+</button>
	        						</section>
	        						</td>
								</tr>
							</table>
						</td>
							
					</tr>	
					<tr>
						<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/selec_doc.jsp">
						<jsp:param name="tkn_doc" value="<%=tkn_doc %>"/>
						</jsp:include>
					</tr>
					<tr>
						
						<%
						info="Pihak Terkait:\n&nbsp &nbsp Seluruh pihak yang terkait dalam seluruh siklus mutu. Dimulai dari pihak terkait dalam Penetapan, Pelaksanaa, Evaluasi dan Pengendalian serta Peningkatan Standar Mutu";
						%>
						<td colspan="3" style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;">
							<table width="100%" style="border:none">
								<tr>
									<td width="75%" style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold;font-size:1.6em" title="<%=info%>">PIHAK TERKAIT</td>
									<td width="20%" style="padding:0 5px 0 0;text-align:right;border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >
									Tambah Pihak Terkait
									</td>
									<td width="5%" style="border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >			
									<section class="gradient" style="text-align:right;vertical-align: top;">
		            					<button style="font-size: 50px;" value="pihak_terkait" name="tombol">+</button>
	        						</section>
	        						</td>
								</tr>
							</table>
						</td>	
					</tr>	
					<tr>
	<%
		//System.out.println(request.getRequestURI());
			boolean first = true;
			String prev_grp = "";
			int counter = 0;
			ListIterator li_job = v_job.listIterator();
			while(li_job.hasNext()) {
				boolean match = false;
				counter++;
				String job = (String)li_job.next();
				StringTokenizer stj = new StringTokenizer(job,"`");
				job = stj.nextToken();
				String grp = stj.nextToken();
				if(first) {
					first = false;
					prev_grp = new String(grp);
				}
				//System.out.println(job+" vs "+pihak);
				if(!Checker.isStringNullOrEmpty(pihak)) {
					StringTokenizer stp = new StringTokenizer(pihak,",");
					while(stp.hasMoreTokens() && !match) {
						String terkait = stp.nextToken();
						if(job.equalsIgnoreCase(terkait)) {
							match = true;
						}
					}
				}
				if(!prev_grp.equalsIgnoreCase(grp)) {
					prev_grp = new String(grp);
					counter = 1;
					%>
					<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >GROUP / KELOMPOK JABATAN TERKAIT</td>
					</tr>	
					<tr>
	<%
					
				}
			//String sin = st.nextToken();
				if(counter%3!=0) {
						if(match) {
	%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" checked="checked"> <%=job %></td>
	<%						
						}
						else {
	%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
	<%
						}
				}
				else {
					if(match) {
						%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" checked="checked"> <%=job %></td>
	<%
					}
					else {
				%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
	<%
					}	
	%>
								</tr>
								<tr>
	<%			
					
				}
				if(!li_job.hasNext()) {
				%>
								</tr>
				<%		
				}
				
			}	
	%>
					<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >PIHAK PENGAWAS</td>
					</tr>
					<tr>
	<%
			first = true;
			prev_grp = "";
			counter = 0;
	//System.out.println("tkn_pengawas="+tkn_pengawas);
			li_job = v_job.listIterator();
			while(li_job.hasNext()) {
				boolean match = false;
				counter++;
				String job = (String)li_job.next();
				StringTokenizer stj = new StringTokenizer(job,"`");
				job = stj.nextToken();
				String grp = stj.nextToken();
				if(first) {
					first = false;
					prev_grp = new String(grp);
				}
				//System.out.println(job+" vs "+pihak);
				if(!Checker.isStringNullOrEmpty(tkn_pengawas)) {
					StringTokenizer stp = new StringTokenizer(tkn_pengawas,",");
					while(stp.hasMoreTokens() && !match) {
						String terkait = stp.nextToken();
						if(job.equalsIgnoreCase(terkait)) {
							match = true;
						}
					}
				}
				if(!prev_grp.equalsIgnoreCase(grp)) {
					prev_grp = new String(grp);
					counter = 1;
					%>
					<tr>
						<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >GROUP / KELOMPOK JABATAN PENGAWAS</td>
					</tr>	
					<tr>
	<%
					
				}
			//String sin = st.nextToken();
				if(counter%3!=0) {
						if(match) {
	%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job_mon" value="<%=job %>" checked="checked"> <%=job %></td>
	<%						
						}
						else {
	%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job_mon" value="<%=job %>" > <%=job %></td>
	<%
						}
				}
				else {
					if(match) {
						%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job_mon" value="<%=job %>" checked="checked"> <%=job %></td>
	<%
					}
					else {
				%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job_mon" value="<%=job %>" > <%=job %></td>
	<%
					}	
	%>
								</tr>
								<tr>
	<%			
					
				}
				if(!li_job.hasNext()) {
				%>
								</tr>
				<%		
				}
				
			}	
	%>				
					<tr>
						<td colspan="4" style="padding:5px 0px">
							<section class="gradient" style="text-align:center">
		            			<button style="padding: 5px 50px;font-size: 20px;" value="std_usulan" name="tombol">STANDARISASI USULAN</button>
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