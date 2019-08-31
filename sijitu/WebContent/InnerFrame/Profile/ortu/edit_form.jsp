<!DOCTYPE html>
<html>
<head>
  	<title>Bootstrap-select test page</title>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="java.util.ListIterator" %>


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
String tkn_wajib = "NAME-IBU-DOANG JADI NGGA BISA DIPAKAI";
String info = (String)request.getAttribute("info");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String atMenu = request.getParameter("atMenu");
String cmd = request.getParameter("cmd");
String nmmay=null;
String tglay=null;
String tplay=null;
String llsay=null;
String hpeay=null;
String jobay=null;
String payay=null;
String nikay=null;
String rilay=null;
String nmmbu=null;
String tglbu=null;
String tplbu=null;
String llsbu=null;
String hpebu=null;
String jobbu=null;
String paybu=null;
String nikbu=null;
String rilbu=null;
String nmmwa=null;
String tglwa=null;
String tplwa=null;
String llswa=null;
String hpewa=null;
String jobwa=null;
String paywa=null;
String nikwa=null;
String hubwa=null;
String nmer1=null;
String hper1=null;
String hber1=null;
String nmer2=null;
String hper2=null;
String hber2=null;

if(!Checker.isStringNullOrEmpty(info)) {
	while(info.contains("``")) {
		info = info.replace("``", "`null`");
	}
	StringTokenizer st = new StringTokenizer(info,"`");
	nmmay=st.nextToken();
	tglay=st.nextToken();
	tplay=st.nextToken();
	llsay=st.nextToken();
	hpeay=st.nextToken();
	jobay=st.nextToken();
	payay=st.nextToken();
	nikay=st.nextToken();
	rilay=st.nextToken();
	nmmbu=st.nextToken();
	tglbu=st.nextToken();
	tplbu=st.nextToken();
	llsbu=st.nextToken();
	hpebu=st.nextToken();
	jobbu=st.nextToken();
	paybu=st.nextToken();
	nikbu=st.nextToken();
	rilbu=st.nextToken();
	nmmwa=st.nextToken();
	tglwa=st.nextToken();
	tplwa=st.nextToken();
	llswa=st.nextToken();
	hpewa=st.nextToken();
	jobwa=st.nextToken();
	paywa=st.nextToken();
	nikwa=st.nextToken();
	hubwa=st.nextToken();
	nmer1=st.nextToken();
	hper1=st.nextToken();
	hber1=st.nextToken();
	nmer2=st.nextToken();
	hper2=st.nextToken();
	hber2=st.nextToken();
}
String lbl = "";
Vector v_tmp = null;
ListIterator li = null;
StringTokenizer st = null;

boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));


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
	<form action="go.updDataProfileOrtu" method="post">
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
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA AYAH</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NAMA*";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="nmmay" value="<%=Checker.pnn(nmmay) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "NIK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" min="100000000" max="9999999999999999" style="width:100%;border:none;text-align:center" name="nikay" value="<%=Checker.pnn(nikay) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TEMPAT LAHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="tplay" value="<%=Checker.pnn(tplay) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TANGGAL LAHIR";
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
					if(!Checker.isStringNullOrEmpty(tglay)) {
						tglay = Converter.formatDdSlashMmSlashYy(tglay);
					}
					%>
					<input type="text" name="tglay" value="<%=Checker.pnn(tglay) %>" />
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
				
					<%
					lbl = "NO HP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" name="hpeay" value="<%=Checker.pnn(hpeay) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PENDIDIKAN TERAKHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<select class="selectpicker show-tick form-control" data-live-search="true" name="llsay">
						<option value="null">Pilih Jenjang</option>
<%
v_tmp = Constant.getListJenjangLulusan();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String kdjen = st.nextToken();
	String kode = st.nextToken();
	if(llsay.toUpperCase().contains(kode.toUpperCase())) {
%>
						<option value="<%=kode%>" selected="selected"><%=kode.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=kode%>"><%=kode.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PEKERJAAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="jobay" value="<%=Checker.pnn(jobay) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "GAJI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="payay" value="<%=Checker.pnn(payay) %>" /></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA IBU</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NAMA*";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="nmmbu" value="<%=Checker.pnn(nmmbu) %>" required/></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NIK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" min="100000000" max="9999999999999999" style="width:100%;border:none;text-align:center" name="nikbu" value="<%=Checker.pnn(nikbu) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					TEMPAT LAHIR
					<%
					lbl = "TEMPAT LAHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="tplbu" value="<%=Checker.pnn(tplbu) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					TANGGAL LAHIR
					<%
					lbl = "TANGGAL LAHIR";
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
					if(!Checker.isStringNullOrEmpty(tglbu)) {
						tglbu = Converter.formatDdSlashMmSlashYy(tglbu);
					}
					 
					%>
					<input type="text" name="tglbu" value="<%=Checker.pnn(tglbu) %>" />
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >

					<%
					lbl = "NO HP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" style="width:100%" name="hpebu" value="<%=Checker.pnn(hpebu) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PENDIDIKAN TERAKHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<select class="selectpicker show-tick form-control" data-live-search="true" name="llsbu">
						<option value="null">Pilih Jenjang</option>
<%
v_tmp = Constant.getListJenjangLulusan();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String kdjen = st.nextToken();
	String kode = st.nextToken();
	if(llsbu.toUpperCase().contains(kode.toUpperCase())) {
%>
						<option value="<%=kode%>" selected="selected"><%=kode.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=kode%>"><%=kode.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PEKERJAAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="jobbu" value="<%=Checker.pnn(jobbu) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "GAJI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="paybu" value="<%=Checker.pnn(paybu) %>" /></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA WALI</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
<%
					lbl = "NAMA";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="nmmwa" value="<%=Checker.pnn(nmmwa) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "NIK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" min="100000000" max="9999999999999999" style="width:100%;border:none;text-align:center" name="nikwa" value="<%=Checker.pnn(nikwa) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TEMPAT LAHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="tplwa" value="<%=Checker.pnn(tplwa) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TANGGAL LAHIR";
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
					if(!Checker.isStringNullOrEmpty(tglwa)) {
						tglwa = Converter.formatDdSlashMmSlashYy(tglwa);
					}
					
					%>
					<input type="text" name="tglwa" value="<%=Checker.pnn(tglwa) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "NO HP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" name="hpewa" value="<%=Checker.pnn(hpewa) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PENDIDIKAN TERAKHIR";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<select class="selectpicker show-tick form-control" data-live-search="true" name="llswa">
						<option value="null">Pilih Jenjang</option>
<%
v_tmp = Constant.getListJenjangLulusan();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String kdjen = st.nextToken();
	String kode = st.nextToken();
	if(llswa.toUpperCase().contains(kode.toUpperCase())) {
%>
						<option value="<%=kode%>" selected="selected"><%=kode.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=kode%>"><%=kode.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PEKERJAAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="jobwa" value="<%=Checker.pnn(jobwa) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GAJI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="paywa" value="<%=Checker.pnn(paywa) %>" /></td>
				</tr>
			</tbody>
		
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA KONTAK EMERGENCY</th>
  				</tr>
  				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">KONTAK 1</th>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">KONTAK 2</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="nmer1" value="<%=Checker.pnn(nmer1) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="nmer2" value="<%=Checker.pnn(nmer2) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" name="hper1" value="<%=Checker.pnn(hper1) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" name="hper2" value="<%=Checker.pnn(hper2) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >HUBUNGAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="hber1" value="<%=Checker.pnn(hber1) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >HUBUNGAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" ><input type="text" name="hber2" value="<%=Checker.pnn(hber2) %>" /></td>
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
		</table>
		
		

	</form>	
	</div>
	</div>
</div>
</body>
</html>
