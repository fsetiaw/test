<!DOCTYPE html>
<html>
<head>
  	<title>Bootstrap-select test page</title>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.apache.commons.lang3.StringUtils" %>


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
//info = nimhs+"`"+shift+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+stmhs+"`"+stpid+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+krklm+"`"+npm_pa+"`"+nmm_pa;
String nimhs=null;
String shift=null;
String tahun=null;
String smawl=null;
String btstu=null;
String assma=null;
String stpid=null;
String noprm=null;
String nokp1=null;
String nokp2=null;
String nokp3=null;
String nokp4=null;
String krklm=null;
String npm_pa=null;
String nmm_pa=null;

String sksdi=null;
String asnim=null;
String aspti=null;
String aspti_unlisted=null;
String asjen=null;
String aspst=null;
if(!Checker.isStringNullOrEmpty(info)) {
	while(info.contains("``")) {
		info = info.replace("``", "`null`");
	}
	StringTokenizer st = new StringTokenizer(info,"`");
	nimhs=st.nextToken();
	shift=st.nextToken();
	tahun=st.nextToken();
	smawl=st.nextToken();
	btstu=st.nextToken();
	assma=st.nextToken();
	stpid=st.nextToken();
	noprm=st.nextToken();
	nokp1=st.nextToken();
	nokp2=st.nextToken();
	nokp3=st.nextToken();
	nokp4=st.nextToken();
	krklm=st.nextToken();
	npm_pa=st.nextToken();
	nmm_pa=st.nextToken();
	
	sksdi=st.nextToken();
	asnim=st.nextToken();
	aspti=st.nextToken();
	asjen=st.nextToken();
	aspst=st.nextToken();
	aspti_unlisted=st.nextToken();
}

String lbl = "";
Vector v_tmp = null;
ListIterator li = null;
StringTokenizer st = null;
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
	<form action="go.updDataProfileKemahasiswaan" method="post">
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
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA KEMAHASISWAAN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "NIM";
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
					if(validUsr.isUsrAllowTo_updated("nim",npm)) {
					%>
					<input type="text" name="nimhs" value="<%=Checker.pnn(nimhs) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="nimhs" value="<%=Checker.pnn(nimhs) %>" readonly />
						<%
					}
					%>
					
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "SHIFT KULIAH";
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
					if(validUsr.isUsrAllowTo_updated("updShift",npm)) {
					%>
						<select class="show-tick form-control" data-live-search="true" name="shift">
						<option value="null">Pilih Shift</option>
<%
						v_tmp = Constant.getListShiftAvail(kdpst);
						li = v_tmp.listIterator();


						while(li.hasNext()) {
							String brs = (String)li.next();
							st = new StringTokenizer(brs,"`");
							String keter = st.nextToken();
							String nm_shift = st.nextToken();
							String kenversi = st.nextToken();
							if(keter.toUpperCase().equalsIgnoreCase(shift.toUpperCase())) {
%>
						<option value="<%=keter%>" selected="selected"><%=keter.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=keter%>"><%=keter.toUpperCase() %></option>
<%	
							}
						}

%>          					
          				</select>
					<%	
					}
					else {
						%>
						<input type="hidden" name="shift" value="<%=Checker.pnn(shift) %>" readonly />
						<%
						out.print(Converter.printShift(shift,kdpst));
					}
					%>
						
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TAHUN MASUK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<input type="text" name="tahun" value="<%=Checker.pnn(tahun) %>" readonly />
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "TIPE PENDAFTARAN";
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
					//if(validUsr.amI("ADMIN") || validUsr.amI("KEPALA TATA USAHA", "54201")) {
					if(validUsr.isUsrAllowTo_updated("utm", npm)) {	
					%>
						<select class="show-tick form-control" data-live-search="true" name="stpid">
						<option value="null">Pilih Status</option>
<%
						v_tmp = Constant.getListTipePendaftaran();
						li = v_tmp.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							st = new StringTokenizer(brs,"`");
							String token = st.nextToken();
							st = new StringTokenizer(token,"-");
							String val = st.nextToken();
							String ket = st.nextToken();
							if(stpid.toUpperCase().equalsIgnoreCase(val)) {
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
					<%	
					}
					else {
						%>
						<input type="hidden" name="stpid" value="<%=Checker.pnn(stpid) %>" />
						<%=Converter.printKetStatusPindahanOrBaru(stpid) %>
					<%
					}
					%>
					
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "SEMESTER AWAL";
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
					if(validUsr.amI("ADMIN")) {
					%>
						<input type="text" name="smawl" value="<%=Checker.pnn(smawl) %>" />
					<%	
					}
					else {
						%>
						<input type="text" name="smawl" value="<%=Checker.pnn(smawl) %>" readonly />
					<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "BATAS STUDI";
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
					if(validUsr.amI("ADMIN")) {
					%>
						<input type="text" name="btstu" value="<%=Checker.pnn(btstu) %>" />
					<%	
					}
					else {
						%>
						<input type="text" name="btstu" value="<%=Checker.pnn(btstu) %>" readonly />
					<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "PEMBIMBING AKADEMIK";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<select class="selectpicker show-tick form-control" data-live-search="true" name="bimbing">
						<option value="null">Pilih Pembimbing</option>
<%
						v_tmp = Getter.getListDosen_v1(false);
						//nmdos+"`"+nodos+"`"+nomor+"`"+npmdos);
						li = v_tmp.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							st = new StringTokenizer(brs,"`");
							String nmdos = st.nextToken();
							String nidnn = st.nextToken();
							String nomor_ngga_guna = st.nextToken();
							String npmdos = st.nextToken();
							if(npm_pa.equalsIgnoreCase(npmdos)) {
%>
						<option value="<%=npmdos%>`<%=nmdos%>" selected="selected"><%=nmdos.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=npmdos%>`<%=nmdos%>"><%=nmdos.toUpperCase() %></option>
<%	
							}
						}

%>          					
          				</select>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >
					<%
					lbl = "KURIKULUM";
					if(tkn_wajib.contains("`"+lbl+"`")) {
						out.print(lbl+" *");
					}
					else {
						out.print(lbl);
					}
					%>
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
						<select class="selectpicker show-tick form-control" data-live-search="true" name="krklm">
						<option value="null">Pilih Kurikulum</option>
<%
						v_tmp = Getter.getListKurikulum(kdpst, false);
						//li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt);
						li = v_tmp.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							st = new StringTokenizer(brs,"`");
							String idkur = st.nextToken();
							String nmkur = st.nextToken();
							String stkur = st.nextToken();
							String start = st.nextToken();
							String end = st.nextToken();
							String skstt = st.nextToken();
							String smstt = st.nextToken();
							String ket = "AKTIF";
							if(!stkur.equalsIgnoreCase("A")) {
								ket = "EXPIRED";
							}
							if(krklm.equalsIgnoreCase(idkur)) {
%>
						<option value="<%=brs%>" selected="selected"><%=nmkur.toUpperCase() %> [<%=skstt %> sks / <%=smstt %> sms] [<%=ket %>]</option>
<%		
							}
							else {
%>
						<option value="<%=brs%>"><%=nmkur.toUpperCase() %> [<%=skstt %> sks / <%=smstt %> sms] [<%=ket %>]</option>
<%	
							}
						}

%>          					
          				</select>
					</td>
				</tr>
<%
if(!Checker.isStringNullOrEmpty(stpid) && stpid.equalsIgnoreCase("P") && !validUsr.iAmStu()) {
%>
			<thead>
				<tr>
  					<th colspan="4" style="text-alignalign="center" style="vertical-align: middle; padding: 0px 0px;background:white;": center; padding: 0px 10px;font-size:1.5em">RIWAYAT STUDI</th>
  				</tr>
  			</thead>
  			<tbody>
  				<%
  				/*
  				%>
  				
				<%
				*/
				%>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PERGURUAN TINGGI</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 0px;background:white;">
					
					<select class="selectpicker show-tick form-control" data-live-search="true" name="aspti">
					<option value="null">Pilih Perguruan Tinggi (klik & harap menunggu sampai tampil daftar pt)</option>
<%
v_tmp = Constant.getListPT();
//System.out.println("size = "+v_tmp.size());
li = v_tmp.listIterator();
st = null;
while(li.hasNext()) {
	String brs = (String)li.next();
	//System.out.println(brs);
	st = new StringTokenizer(brs,"`");
	
	String id_negara=st.nextToken();
	String nm_wil=st.nextToken();
	String nm_lemb=st.nextToken();
	String kdpti=st.nextToken();
	//if(aspti!=null && (nm_lemb.toUpperCase().contains(aspti.toUpperCase()))) {
	if(StringUtils.containsIgnoreCase(nm_lemb, aspti)) {	
	//if(aspti!=null) && (nm_lemb.containsIgnoreCase(aspti))) {	
%>
					<option value="<%=kdpti%>-<%=nm_lemb%>" selected="selected">[<%=kdpti %>]&nbsp-&nbsp<%=nm_lemb.toUpperCase() %>,&nbsp<%=nm_wil %>&nbsp(<%=id_negara %>)</option>
<%		
	}
	else {
%>
					<option value="<%=kdpti%>-<%=nm_lemb%>">[<%=kdpti %>]&nbsp-&nbsp<%=nm_lemb.toUpperCase() %>,&nbsp<%=nm_wil %>&nbsp(<%=id_negara %>)</option>
<%	
	}
}

%>          					
      				</select>
					
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA P.T. bila tdk dlm daftar</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 0px;background:white;">
					<%
					
					if(validUsr.isUsrAllowTo_updated("epri",npm)) {
					%>
					<input type="text" name="aspti_unlisted" value="<%=Checker.pnn(aspti_unlisted).toUpperCase() %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="aspti_unlisted" value="<%=Checker.pnn(aspti_unlisted).toUpperCase() %>" readonly />
						<%
					}
					
					%>
					</td>
				</tr>
				<tr>	
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PROGRAM STUDI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;">
					<%
					if(validUsr.isUsrAllowTo_updated("epri",npm)) {
					%>
					<input type="text" name="aspst" value="<%=Checker.pnn(aspst).toUpperCase() %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="aspst" value="<%=Checker.pnn(aspst).toUpperCase() %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >JENJANG STUDI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;">
					<%
					if(validUsr.isUsrAllowTo_updated("epri",npm)) {
					%>
					<select class="selectpicker show-tick form-control" data-live-search="true" name="asjen">
						<option value="null">Pilih Jenjang</option>
<%
						v_tmp = Constant.getListJenjangLulusan();
						li = v_tmp.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							st = new StringTokenizer(brs,"`");
							String kdjen = st.nextToken();
							String kode = st.nextToken();
							if(asjen.toUpperCase().contains(kdjen.toUpperCase())) {
%>
						<option value="<%=kdjen%>" selected="selected"><%=kode.toUpperCase() %></option>
<%		
							}
							else {
%>
						<option value="<%=kdjen%>"><%=kode.toUpperCase() %></option>
<%	
							}
						}
%>          					
          			</select>
					<%	
					}
					else {
						%>
					<input type="text" name="asjen" value="<%=Checker.pnn(asjen).toUpperCase() %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
				<tr>	
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NPM ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(validUsr.isUsrAllowTo_updated("epri",npm)) {
					%>
					<input type="text" name="asnim" value="<%=Checker.pnn(asnim) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="asnim" value="<%=Checker.pnn(asnim) %>" readonly />
						<%
					}
					%>
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SKS TRANSFER</td>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white;" >
					<%
					if(validUsr.isUsrAllowTo_updated("epri",npm)) {
					%>
					<input type="text" name="sksdi" value="<%=Checker.pnn(sksdi) %>" />
					<%	
					}
					else {
						%>
					<input type="text" name="sksdi" value="<%=Checker.pnn(sksdi) %>" readonly />
						<%
					}
					%>
					</td>
				</tr>
			</tbody>	
<%	
}
%>				
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
