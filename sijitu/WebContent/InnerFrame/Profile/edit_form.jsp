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
  	
  	
  	function validate(id){
  		var  inputval = document.getElementById(id).value;
  		var length = inputval.length;
  		var error = document.getElementById("errorinput1");
  		if(length==0){
  			error.innerHTML="EMPTY";
  		}
  		else if(length<MIN){
  		//MIN is your minimum number of charecter
  			error.innerHTML="minimum " + MIN + " charecter are required ";
  		}
  		else if(length>MAX){
  		 //MAX is your maximum number of charecter
  		 	error.innerHTML="maximum " + MAX + " charecter are required ");
  		}

  	}
</script>
 
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String tkn_wajib = "`NAMA`TEMPAT KELAHIRAN`TANGGAL KELAHIRAN`ALAMAT RUMAH`KECAMATAN`NIK`PASPOR`KELURAHAN`NEGARA KELAHIRAN`KEWARGANEGARAAN`PROVINSI`KOTA`TELP`NO HP`EMAIL`";//SINGLE QUATE depan belakang
String info = (String)request.getAttribute("info");
//System.out.println("inpoh="+info);
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String atMenu = request.getParameter("atMenu");
String cmd = request.getParameter("cmd");
String nmmhs=null;
String tplhr=null;
String tglhr=null;
String kdjek=null;
String nisn=null;
String warganegara=null;
String niktp=null;
String nosim=null;
String paspo=null;
String angel=null;
String sttus=null;
String email=null;
String nohpe=null;
String almrm=null;
String no_rt=null;
String no_rw=null;
String prorm=null;
String proid=null;
String kotrm=null;
String kotid=null;
String kecrm=null;
String kecid=null;
String kelrm=null;
String dusun=null;
String posrm=null;
String telrm=null;
String nglhr=null;
String agama=null;

if(!Checker.isStringNullOrEmpty(info)) {
	while(info.contains("``")) {
		info = info.replace("``", "`null`");
	}
	StringTokenizer st = new StringTokenizer(info,"`");
	nmmhs=st.nextToken();
	tplhr=st.nextToken();
	tglhr=st.nextToken();
	kdjek=st.nextToken();
	nisn=st.nextToken();
	warganegara=st.nextToken();
	niktp=st.nextToken();
	nosim=st.nextToken();
	paspo=st.nextToken();
	angel=st.nextToken();
	sttus=st.nextToken();
	email=st.nextToken();
	nohpe=st.nextToken();
	almrm=st.nextToken();
	no_rt=st.nextToken();
	no_rw=st.nextToken();
	prorm=st.nextToken();
	proid=st.nextToken();
	kotrm=st.nextToken();
	kotid=st.nextToken();
	kecrm=st.nextToken();
	kecid=st.nextToken();
	kelrm=st.nextToken();
	dusun=st.nextToken();
	posrm=st.nextToken();
	telrm=st.nextToken();
	nglhr=st.nextToken();
	agama=st.nextToken();
	
}
String lbl = "";
Vector v_tmp = null;
ListIterator li = null;
StringTokenizer st = null;

boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));

//boolean mhs_yg_edit = false;
//if(validUsr.iAmStu() && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
//	mhs_yg_edit = true;
//}
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
	<form action="go.updDataProfile" method="post">
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
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA PRIBADI</th>
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
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(!wajib_update_profile) {
					%>
						<input type="text" name="nmmhs" value="<%=Checker.pnn(nmmhs) %>" required/>
					<%
					}
					else {
						out.print(nmmhs);
					%>
						<input type="hidden" name="nmmhs" value="<%=Checker.pnn(nmmhs) %>"/>
					<%
					}
					%>	
					</td>
					<td colspan="2" rowspan="6" align="center" style="vertical-align: middle; padding: 0px 0px;">
						<img src="show.passPhoto?picfile=<%=npm %>.jpg&npmhs=<%=npm %>" class="img-thumbnail" alt="Cinque Terre" width="200" height="100">
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "GENDER";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
						<select class="selectpicker show-tick form-control" data-live-search="true" name="kdjek">
						<option value="null">Pilih Gender</option>
<%
v_tmp = Constant.getListGender();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"-");
	String val = st.nextToken();
	String ket = st.nextToken();
	if(val.toUpperCase().contains(kdjek.toUpperCase())) {
%>
						<option value="<%=val%>" selected="selected"><%=ket.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=val%>"><%=ket.toUpperCase() %></option>
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
						<select class="selectpicker show-tick form-control" data-live-search="true" name="sttus">
						<option value="null">Pilih Status</option>
<%
v_tmp = Constant.getListStatusNikah();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String stat = (String)li.next();
	if(stat.toUpperCase().equalsIgnoreCase(sttus.toUpperCase())) {
%>
						<option value="<%=stat%>" selected="selected"><%=stat.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=stat%>"><%=stat.toUpperCase() %></option>
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
					lbl = "AGAMA";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
						<select class="selectpicker show-tick form-control" data-live-search="true" name="agama">
						<option value="null">Pilih Agama</option>
<%
v_tmp = Constant.getListAgama();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String nm_agama = (String)li.next();
	if(nm_agama.toUpperCase().contains(agama.toUpperCase())) {
%>
						<option value="<%=nm_agama%>" selected="selected"><%=nm_agama.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=nm_agama%>"><%=nm_agama.toUpperCase() %></option>
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
					lbl = "TEMPAT KELAHIRAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
						<input type="text" name="tplhr" value="<%=Checker.pnn(tplhr) %>" required/>
					</td>
				</tr>
				<tr>	
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TANGGAL KELAHIRAN";
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
					if(!Checker.isStringNullOrEmpty(tglhr)) {
						tglhr = Converter.formatDdSlashMmSlashYy(tglhr);
					}
					%>
					<input type="text" name="tglhr" value="<%=Checker.pnn(tglhr) %>" placholder="tgl/bln/thn" required/>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "NEGARA KELAHIRAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px" >
						<select class="selectpicker show-tick form-control" data-live-search="true" name="nglhr">
						<option value="null">Pilih Wilayah</option>
<%
v_tmp = Constant.getListNegara();
li = v_tmp.listIterator();


while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String id_wil=st.nextToken();
	String id_negara=st.nextToken();
	String nm_wil=st.nextToken();
	String id_induk_wilayah=st.nextToken();
	if(nm_wil.toUpperCase().contains(nglhr.toUpperCase())) {
%>
						<option value="<%=brs%>" selected="selected"><%=nm_wil.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=brs%>"><%=nm_wil.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "KEWARGANEGARAAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px" >
						<select class="selectpicker show-tick form-control" data-live-search="true" name="warganegara">
						<option value="null">Pilih Wilayah</option>
<%
v_tmp = Constant.getListNegara();
li = v_tmp.listIterator();
st = null;
while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String id_wil=st.nextToken();
	String id_negara=st.nextToken();
	String nm_wil=st.nextToken();
	String id_induk_wilayah=st.nextToken();
	if(nm_wil.toUpperCase().contains(warganegara.toUpperCase())) {
%>
						<option value="<%=brs%>" selected="selected"><%=nm_wil.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=brs%>"><%=nm_wil.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">KETERANGAN  DOMISILI</th>
				</tr>
				</thead>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "ALAMAT RUMAH";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
						<textarea rows="3" name="almrm" ><%=Checker.pnn(almrm).trim() %></textarea>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PROVINSI";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px" >
						<select onchange="this.form.submit()" class="selectpicker show-tick form-control" data-live-search="true" name="prorm">
						<option value="null">Pilih Wilayah</option>
<%
v_tmp = Constant.getListProv("indonesia");
li = v_tmp.listIterator();
st = null;
while(li.hasNext()) {
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String id_wil=st.nextToken();
	String id_negara=st.nextToken();
	String nm_wil=st.nextToken();
	String id_induk_wilayah=st.nextToken();
	if(nm_wil.toUpperCase().contains(prorm.toUpperCase())) {
%>
						<option value="<%=brs%>" selected="selected"><%=nm_wil.toUpperCase() %></option>
<%		
	}
	else {
%>
						<option value="<%=brs%>"><%=nm_wil.toUpperCase() %></option>
<%	
	}
}

%>          					
          				</select>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "KOTA";
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
boolean kota_match = false;
if(!Checker.isStringNullOrEmpty(prorm)) {
%>
						<select onchange="this.form.submit()" class="selectpicker show-tick form-control" data-live-search="true" name="kotrm">
						<option value="null">Pilih Wilayah</option>
<%
	v_tmp = Constant.getListKota(prorm);
	li = v_tmp.listIterator();
	st = null;
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String id_wil=st.nextToken();
		String id_negara=st.nextToken();
		String nm_wil=st.nextToken();
		String id_induk_wilayah=st.nextToken();
		if(nm_wil.toUpperCase().contains(kotrm.toUpperCase())) {
			kota_match = true;
%>
						<option value="<%=brs%>" selected="selected"><%=nm_wil.toUpperCase() %></option>
<%		
		}
		else {
%>
						<option value="<%=brs%>"><%=nm_wil.toUpperCase() %></option>
<%	
		}
	}

%>          					
          				</select>
<%	
}
else {
	out.print("Pilih Provinsi Domisili Dahulu");
}
%>					
					</td>

				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "KELURAHAN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="kelrm" value="<%=Checker.pnn(kelrm) %>" required/>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "KECAMATAN";
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
if(!Checker.isStringNullOrEmpty(kotrm) && kota_match) {
%>
						<select class="selectpicker show-tick form-control" data-live-search="true" name="kecrm">
						<option value="null">Pilih Wilayah</option>
<%
	v_tmp = Constant.getListKec(kotrm);
	li = v_tmp.listIterator();
	st = null;
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String id_wil=st.nextToken();
		String id_negara=st.nextToken();
		String nm_wil=st.nextToken();
		String id_induk_wilayah=st.nextToken();
		if(nm_wil.toUpperCase().contains(kecrm.toUpperCase())) {
%>
						<option value="<%=brs%>" selected="selected"><%=nm_wil.toUpperCase() %></option>
<%		
		}
		else {
%>
						<option value="<%=brs%>"><%=nm_wil.toUpperCase() %></option>
<%	
		}
	}

%>          					
          				</select>
<%	
}
else {
	out.print("Pilih Kota Domisili Dahulu");
}
%>		
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "RT";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%></td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" min="1" max="99" style="width:100%;border:none;text-align:center" name="no_rt" value="<%=Checker.pnn(no_rt) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "RW";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" min="1" max="99" style="width:100%;border:none;text-align:center" name="no_rw" value="<%=Checker.pnn(no_rw) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "DESA";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >Belum Perlu Diisi</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "DUSUN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="dusun" value="<%=Checker.pnn(dusun) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "KODE POS";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" max="99999" style="width:100%;;border:none" name="posrm" value="<%=Checker.pnn(posrm) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "TELP";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" max="99999999999" oninvalid="this.setCustomValidity('Harap diisi no telp rumah dan bukan no handphone, untuk No handphone diisi pada pada baris NO HP')" oninput="this.setCustomValidity('')" style="width:100%;border:none;text-align:center" name="telrm" value="<%=Checker.pnn(telrm) %>" /></td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">INFO KONTAK & IDENTIFIKASI</th>
				</tr>
				</thead>
				<tr>
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
					<input type="number" min="100000000" max="9999999999999999" style="width:100%;border:none;text-align:center" name="niktp" value="<%=Checker.pnn(niktp) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "NISN";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="nisn" value="<%=Checker.pnn(nisn) %>" /></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "SIM";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="nosim" value="<%=Checker.pnn(nosim) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "PASPOR (WNA Wajib Diisi)";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="paspo" value="<%=Checker.pnn(paspo) %>" /></td>
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
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="number" max="999999999999999" style="width:100%;border:none;text-align:center" name="nohpe" value="<%=Checker.pnn(nohpe) %>" /></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					
					<%
					lbl = "EMAIL";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="email" value="<%=Checker.pnn(email) %>" /></td>
				</tr>
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
