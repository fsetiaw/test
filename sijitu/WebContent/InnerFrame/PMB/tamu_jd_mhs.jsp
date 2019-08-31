<!DOCTYPE html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.wilayah.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/jquery-ui.css">
  <script src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/js/jquery-ui.js"></script>
<%
String v_id_obj=request.getParameter("id_obj");
String v_nmmhs=request.getParameter("nmm");
String v_npmhs=request.getParameter("npm");
String v_obj_lvl=request.getParameter("obj_lvl");
String v_kdpst=request.getParameter("kdpst");
String cmd=request.getParameter("cmd");



Vector v_info_civ = (Vector) session.getAttribute("v_info_civ");

String tkn_civ = null;
String tkn_eciv = null;
if(v_info_civ!=null && v_info_civ.size()>0) {
	ListIterator li = v_info_civ.listIterator();
	tkn_civ = (String)li.next();
	tkn_eciv = (String)li.next();
	//System.out.println(tkn_civ);
	//System.out.println(tkn_eciv);
}


String id_c=null,idobj_c=null,kdpti_c=null,kdjen_c=null,kdpst_c=null,npm_c=null,nimhs_c=null,nmmhs_c=null,shift_c=null,tplhr_c=null,tglhr_c=null,kdjek_c=null,tahun_c=null,smawl_c=null,btstu_c=null,assma_c=null,tgmsk_c=null,tglls_c=null,stmhs_c=null,stpid_c=null,sksdi_c=null,asnim_c=null,aspti_c=null,asjen_c=null,aspst_c=null,bistu_c=null,peksb_c=null,nmpek_c=null,ptpek_c=null,pspek_c=null,noprm_c=null,nokp1_c=null,nokp2_c=null,nokp3_c=null,nokp4_c=null,gelom_c=null,nama_ayah_c=null,tglhr_ayah_c=null,tplhr_ayah_c=null,lulus_ayah_c=null,hape_ayah_c=null,kerja_ayah_c=null,gaji_ayah_c=null,nik_ayah_c=null,kandung_ayah_c=null,nama_ibu_c=null,tglhr_ibu_c=null,tplhr_ibu_c=null,lulus_ibu_c=null,hape_ibu_c=null,kerja_ibu_c=null,gaji_ibu_c=null,nik_ibu_c=null,kandung_ibu_c=null,nama_wali_c=null,tglhr_wali_c=null,tplhr_wali_c=null,lulus_wali_c=null,hape_wali_c=null,kerja_wali_c=null,gaji_wali_c=null,nik_wali_c=null,hub_wali_c=null,nama_emg1_c=null,hape_emg1_c=null,hub_emg1_c=null,nama_emg2_c=null,hape_emg2_c=null,hub_emg2_c=null,nisn_c=null,warga_c=null,nonik_c=null,nosim_c=null,paspor_c=null;
	
if(tkn_civ!=null) {
	StringTokenizer st  = new StringTokenizer(tkn_civ,"`");
	//String info = id+"`"+idobj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+updtm+"`"+gelom+"`"+nama_ayah+"`"+tglhr_ayah+"`"+tplhr_ayah+"`"+lulus_ayah+"`"+hape_ayah+"`"+kerja_ayah+"`"+gaji_ayah+"`"+nik_ayah+"`"+kandung_ayah+"`"+nama_ibu+"`"+tglhr_ibu+"`"+tplhr_ibu+"`"+lulus_ibu+"`"+hape_ibu+"`"+kerja_ibu+"`"+gaji_ibu+"`"+nik_ibu+"`"+kandung_ibu+"`"+nama_wali+"`"+tglhr_wali+"`"+tplhr_wali+"`"+lulus_wali+"`"+hape_wali+"`"+kerja_wali+"`"+gaji_wali+"`"+nik_ibu+"`"+hub_wali+"`"+nama_emg1+"`"+hape_emg1+"`"+hub_emg1+"`"+nama_emg2+"`"+hape_emg2+"`"+hub_emg2+"`"+nisn+"`"+warga+"`"+nonik+"`"+nosim+"`"+paspor;
	id_c = st.nextToken();
	idobj_c = st.nextToken();
	kdpti_c = st.nextToken();
	kdjen_c = st.nextToken();
	kdpst_c = st.nextToken();
	npm_c = st.nextToken();//ignore
	nimhs_c = st.nextToken();
	nmmhs_c = st.nextToken();
	shift_c = st.nextToken();
	tplhr_c = st.nextToken();
 	tglhr_c = st.nextToken();
	kdjek_c = st.nextToken();
	tahun_c = st.nextToken();
	smawl_c = st.nextToken();
	btstu_c = st.nextToken();
	assma_c = st.nextToken();
	tgmsk_c = st.nextToken();
	tglls_c = st.nextToken();
	stmhs_c = st.nextToken();
	stpid_c = st.nextToken();
	sksdi_c = st.nextToken();
	asnim_c = st.nextToken();
	aspti_c = st.nextToken();
	asjen_c = st.nextToken();
	aspst_c = st.nextToken();
	bistu_c = st.nextToken();
	peksb_c = st.nextToken();
	nmpek_c = st.nextToken();
	ptpek_c = st.nextToken();
	pspek_c = st.nextToken();
	noprm_c = st.nextToken();
	nokp1_c = st.nextToken();
	nokp2_c = st.nextToken();
	nokp3_c = st.nextToken();
	nokp4_c = st.nextToken();
	gelom_c = st.nextToken();
	nama_ayah_c = st.nextToken();
	tglhr_ayah_c = st.nextToken();
	tplhr_ayah_c = st.nextToken();
	lulus_ayah_c = st.nextToken();
	hape_ayah_c = st.nextToken();
	kerja_ayah_c = st.nextToken();
	gaji_ayah_c = st.nextToken();
	nik_ayah_c = st.nextToken();
	kandung_ayah_c = st.nextToken();
	nama_ibu_c = st.nextToken();
	tglhr_ibu_c = st.nextToken();
	tplhr_ibu_c = st.nextToken();
	lulus_ibu_c = st.nextToken();
	hape_ibu_c = st.nextToken();
	kerja_ibu_c = st.nextToken();
	gaji_ibu_c = st.nextToken();
	nik_ibu_c = st.nextToken();
	kandung_ibu_c = st.nextToken();
	nama_wali_c = st.nextToken();
	tglhr_wali_c = st.nextToken();
	tplhr_wali_c = st.nextToken();
	lulus_wali_c = st.nextToken();
	hape_wali_c = st.nextToken();
	kerja_wali_c = st.nextToken();
	gaji_wali_c = st.nextToken();
	nik_wali_c = st.nextToken();
	hub_wali_c = st.nextToken();
	nama_emg1_c = st.nextToken();
	hape_emg1_c = st.nextToken();
	hub_emg1_c = st.nextToken();
	nama_emg2_c = st.nextToken();
	hape_emg2_c = st.nextToken();
	hub_emg2_c = st.nextToken();
	nisn_c = st.nextToken();
	warga_c = st.nextToken();
	nonik_c = st.nextToken();
	nosim_c = st.nextToken();
	paspor_c = st.nextToken();
}

String kdpst_e=null,npmhs_e=null,sttus_e=null,email_e=null,nohpe_e=null,almrm_e=null,kotrm_e=null,posrm_e=null,telrm_e=null,almkt_e=null,kotkt_e=null,poskt_e=null,telkt_e=null,jbtkt_e=null,bidkt_e=null,jenkt_e=null,nmmsp_e=null,almsp_e=null,possp_e=null,kotsp_e=null,negsp_e=null,telsp_e=null,neglh_e=null,agama_e=null,krklm_e=null,ttlog_e=null,tmlog_e=null,dtlog_e=null,updtm_e=null,idtipebea_e=null,npmpa_e=null,nmmpa_e=null,oper_e=null,sma_e=null,smakt_e=null,lulusma_e=null,nortrm_e=null,norwrm_e=null,provrm_e=null,provid_e=null,kotid_e=null,kecrm_e=null,kecid_e=null,kelrm_e=null,dusun_e=null;


if(tkn_eciv!=null && !Checker.isStringNullOrEmpty(tkn_eciv)) {
	StringTokenizer st  = new StringTokenizer(tkn_eciv,"`");
	kdpst_e = st.nextToken();
	npmhs_e = st.nextToken();
	sttus_e = st.nextToken();
	email_e = st.nextToken();
	nohpe_e = st.nextToken();
	almrm_e = st.nextToken();
	kotrm_e = st.nextToken();
	posrm_e = st.nextToken();
	telrm_e = st.nextToken();
	almkt_e = st.nextToken();
	kotkt_e = st.nextToken();
	poskt_e = st.nextToken();
	telkt_e = st.nextToken();
	jbtkt_e = st.nextToken();
	bidkt_e = st.nextToken();
	jenkt_e = st.nextToken();
	nmmsp_e = st.nextToken();
	almsp_e = st.nextToken();
	possp_e = st.nextToken();
	kotsp_e = st.nextToken();
	negsp_e = st.nextToken();
	telsp_e = st.nextToken();
	neglh_e = st.nextToken();
	agama_e = st.nextToken();
	krklm_e = st.nextToken();
	ttlog_e = st.nextToken();
	tmlog_e = st.nextToken();
	dtlog_e = st.nextToken();
	idtipebea_e = st.nextToken();
	npmpa_e = st.nextToken();
	nmmpa_e = st.nextToken();
	oper_e = st.nextToken();
	sma_e = st.nextToken();
	smakt_e = st.nextToken();
	lulusma_e = st.nextToken();
	nortrm_e = st.nextToken();
	norwrm_e = st.nextToken();
	provrm_e = st.nextToken();
	provid_e = st.nextToken();
	kotid_e = st.nextToken();
	kecrm_e = st.nextToken();
	kecid_e = st.nextToken();
	kelrm_e = st.nextToken();
	dusun_e = st.nextToken();
}




String nuform = ""+request.getParameter("nuform");
String valid_pt1 = request.getParameter("valid_pt1");
String valid_pt2 = request.getParameter("valid_pt2");
boolean passed1 = true;
boolean passed2 = true;
if(valid_pt1!=null && valid_pt1.equalsIgnoreCase("false")) {
	passed1 = false;
}
if(valid_pt2!=null && valid_pt2.equalsIgnoreCase("false")) {
	passed2 = false;
}
//System.out.println("pased1="+passed1+" - "+valid_pt1);
//System.out.println("pased2="+passed2+" - "+valid_pt2);
String err_msg = request.getParameter("err_msg");
String id_wil_indo = "000000";
SearchDbWilayah sdw = new SearchDbWilayah();
Vector v = sdw.getListNegara();
ListIterator li = null;
Vector v1 = sdw.getListWilayah(1,id_wil_indo);
String propinsi = request.getParameter("propinsi");
if(tkn_civ!=null && (propinsi==null || Checker.isStringNullOrEmpty(propinsi))) {
	propinsi = provrm_e+"`"+provid_e;
}
//System.out.println("prop="+propinsi);
if(!Checker.isWilayahValid(1, propinsi, null, null)) {
	propinsi = null;
}

//System.out.println("prop="+Checker.isWilayahValid(1, propinsi, null, null));
String id_wil_prop = "null";
String nm_wil_prop = "null";
//String id_wil_prop = null;
Vector v2 = null;

if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi) && passed1) {
	StringTokenizer st = new StringTokenizer(propinsi,"`");
	nm_wil_prop = st.nextToken();
	id_wil_prop = st.nextToken().trim();
	v2 = sdw.getListWilayah(2,id_wil_prop);
}


String id_wil_kot = "null";
String nm_wil_kot = "null";
String kota = request.getParameter("kota");

if(tkn_civ!=null && (kota==null || Checker.isStringNullOrEmpty(kota))) {
	kota = kotrm_e +"`"+ kotid_e;
}
if(!Checker.isWilayahValid(2, propinsi, kota, null)) {
	kota = null;
}



Vector v3 = null;
if(kota!=null && !Checker.isStringNullOrEmpty(kota) && passed1) {
	StringTokenizer st = new StringTokenizer(kota,"`");
	nm_wil_kot = st.nextToken();
	id_wil_kot = st.nextToken();
	v3 = sdw.getListWilayah(3,id_wil_kot);
}


String id_wil_kec = "null";
String nm_wil_kec = "null";
String kecamatan = request.getParameter("kecamatan");
if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan) && passed1) {
	kecamatan = kecamatan.replace("[", "`");
	kecamatan = kecamatan.replace("]", "");
	StringTokenizer st = new StringTokenizer(kecamatan,"`");
	nm_wil_kec = st.nextToken();
	id_wil_kec = st.nextToken();
}
if(tkn_civ!=null && (kecamatan==null || Checker.isStringNullOrEmpty(kecamatan))) {
	kecamatan = kecrm_e+" ["+kecid_e+"]";
}
if(!Checker.isWilayahValid(3, propinsi, kota, kecamatan)) {
	kecamatan = null;
}
%>  
  <script>
  $( function() {
	  
  
    var listKecamatan = [
<%


if(v3!=null && v3.size()>0) {
	li = v3.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
%>
		"<%=brs%>"
<%
		if(li.hasNext()) {
			%>
			,
	<%			
		}
	}
}
%>
    ];
    $( "#kecamatan" ).autocomplete({
      source: listKecamatan
    });
    
    
    var listNegara = [
<%


if(v!=null && v.size()>0) {
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
%>
		"<%=brs.trim()%>"
<%
		if(li.hasNext()) {
			%>
			,
	<%			
		}
	}
}
%>
    ];
    $( "#negara" ).autocomplete({
      source: listNegara
    });
    $( "#nglhr" ).autocomplete({
        source: listNegara
      });
    
  });
  </script>

<%
	//System.out.println("pmb1");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
/*
tr:hover td { background:#82B0C3 }
*/
  
</style>
<script>
function resetForm($form) {
	$('#email').val('');
	$('#hape').val('');
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
         .removeAttr('checked').removeAttr('selected');
}
</script>
</head>
<%
//if(nuform!=null && nuform.equalsIgnoreCase("true")) {
if(false) {	
%>
<body onload="resetForm($('#myform'))">
<%	
}
else {
%>
<body>
<%
}
%>
<div id="header">

<jsp:include page="../innerMenu_standalone.jsp" flush="true" />
</div>
<div class="colmask fullpage">
<br />
		<br />
		<section align="center" style="font-size:.8em;color:red;line-height:12px">
			<center>
			<br>
			<br>
			<table>
				<tr>
					<td style="text-align:left"><%=Checker.pnn(err_msg) %></td>
				</tr>
			</table>
			</center>
		</section>
		<br/>
	<center>
	<form action="go.ubahJadiMhsPart2?atMenu=insBt" method="post" id="myform" name="myform">
		
		<input type="hidden" name="id_obj" value="<%=v_id_obj%>">
		<input type="hidden" name="nmm" value="<%=v_nmmhs%>">
		<input type="hidden" name="npm" value="<%=v_npmhs%>">
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl%>">
		<input type="hidden" name="kdpst" value="<%=v_kdpst%>">
		<input type="hidden" name="cmd" value="<%=cmd%>">
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">TUJUAN PROGRAM STUDI</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						PROGRAM STUDI
					</td>
					<td>
					<%
	String prodi=""+request.getParameter("prodi");
	Vector vObj = null;
	if(validUsr.isUsrAccessCommandContain("allowViewDataDosen")) {
		vObj = validUsr.getScopeUpd11Jan2016("iciv");
	}
	else {
		vObj = validUsr.getScopeUpd11Jan2016ProdiOnly("iciv");
	}
	//Vector vObj = validUsr.getScopeUpd11Jan2016ProdiOnly("iciv");
	if(vObj!=null && vObj.size()>0) {
		li = vObj.listIterator();
		if(li.hasNext()) {
					
					%>
					<select onchange="this.form.submit()" name="prodi" style="width:100%;height:35px;text-align-last:center;">
						<option value="null">-PILIH PRODI-</option>
					<%
			while(li.hasNext()) {
				String baris = (String)li.next();
				StringTokenizer st = new StringTokenizer(baris);
				String id_obj = st.nextToken();
				String kdpst = st.nextToken();
				String obj_dsc = st.nextToken();
				String obj_level = st.nextToken();
				if(prodi!=null && !Checker.isStringNullOrEmpty(prodi)) {
					st = new StringTokenizer(prodi,"-");
					String id_obj_1 = st.nextToken();
					String kdpst_1 = st.nextToken();
					if(id_obj.equalsIgnoreCase(id_obj_1)) {
						%>
						<option value="<%=id_obj %>-<%=kdpst %>" selected="selected"><%=obj_dsc.replaceAll("_", " ") %></option>
						<%	
					}
					else {
						%>
						<option value="<%=id_obj %>-<%=kdpst %>"><%=obj_dsc.replaceAll("_", " ") %></option>
						<%
					}	
				}
				else {
					%>
						<option value="<%=id_obj %>-<%=kdpst %>"><%=obj_dsc.replaceAll("_", " ") %></option>
					<%
				}	
			}
					%>
					</select>
					<%
		}
	}
	%>	
					</td>
				</tr>
	<%
	if(prodi!=null && !Checker.isStringNullOrEmpty(prodi)) {
		StringTokenizer st = new StringTokenizer(prodi,"-");
		String idobj_prodi = st.nextToken();
		String kdpst_prodi = st.nextToken();
	%>			
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						TIPE MAHASISWA
					</td>
					<td>
	<%
		String stpid = request.getParameter("stpid");
	%>				
						<select name="stpid" style="width:100%;height:35px;text-align-last:center;">
							<option value="null">-PILIH-</option>
		<%
	
		String []opt=Constants.getTipeCivitas();
		for(int i=0;i<opt.length;i++) {
			String tmp = opt[i];
			st = new StringTokenizer(tmp);
			String val = st.nextToken();
			String ket = st.nextToken();
			if(stpid!=null && stpid.equalsIgnoreCase(val)) {
			%>	
							<option value="<%=val %>" selected="selected"><%=ket %></option>
<%
			}
			else {
		%>	
							<option value="<%=val %>"><%=ket %></option>
		<%
			}
		}
		%>
						</select>	
					</td>
				</tr>	
				
					<%
					//if(prodi!=null && !Checker.isStringNullOrEmpty(prodi)) {
		String shift_kls = request.getParameter("shift_kls");
				%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						SHIFT PERKULIAHAN
					</td>
					<td>
				<%		
		String kdjen = Checker.getKdjen(kdpst_prodi);
		String list_aktif_shift = Checker.getListShift(kdjen);
						//tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
		st = new StringTokenizer(list_aktif_shift,"#");
		if(st.hasMoreTokens()) {
					%>
						<select name="shift_kls" style="width:100%;height:35px;text-align-last:center;">
							<option value="null">-PILIH-</option>
					<%		
			while(st.hasMoreTokens()) {
				String 	uniqKeter = st.nextToken();
				String 	shift = st.nextToken();
				String 	hari = st.nextToken();
				String 	tkn_kdjen = st.nextToken();
				String 	keterKonversi = st.nextToken();
				if(shift_kls!=null && shift_kls.equalsIgnoreCase(uniqKeter)) {
									%>
							<option value="<%=uniqKeter%>" selected="selected"><%=keterKonversi %></option>
							<%				
				}
				else {
					%>
							<option value="<%=uniqKeter%>"><%=keterKonversi %></option>
					<%			
				}
			}
		}
		else {
							%>
						<select name="shift_kls" style="width:100%;height:35px;text-align-last:center;">
							<option value="null">-TIDAK ADA PILIHAN AKTIF SHIFT -</option>
						</select>	
						<%	
		}
					%>
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						TIPE PEMBIAYAAN
					</td>
					<td>
						<select name="bistu" style="width:100%;height:35px;text-align-last:center;">
				<%
		String bistu = request.getParameter("bistu");		
		String list_biaya = Checker.getListPaketBeasiswa();
		if(list_biaya==null || Checker.isStringNullOrEmpty(list_biaya)) {
				%>
							<option value="null">-TABEL BEASISWA KOSONG</option>
				<%		
		}
		else {
			st = new StringTokenizer(list_biaya,"`");
			while(st.hasMoreTokens()) {
				String id_pkt = st.nextToken();
				String nm_pkt = st.nextToken();
				if(bistu!=null && bistu.equalsIgnoreCase(id_pkt+"-"+nm_pkt)) {
					%>
							<option value="<%=id_pkt%>-<%=nm_pkt %>" selected="selected"><%=nm_pkt %></option>
					<%
				}
				else {
							%>
							<option value="<%=id_pkt%>-<%=nm_pkt %>"><%=nm_pkt %></option>
							<%
				}			
			}
							
		}
	}	
					%>
						</select>
					</td>
				</tr>
 <%		
 	if(!validUsr.getObjNickNameGivenObjId().contains("PMB")) {
 %> 				
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						GELOMBANG
					</td>
					<td>
						<select name="gelombang" style="width:100%;height:35px;text-align-last:center;">
							<option value="5" selected="selected">5</option>
				<%
		String gelombang = request.getParameter("gelombang");		
		if(gelombang==null || Checker.isStringNullOrEmpty(gelombang)) {
			    	%>
							<option value="<%=gelombang%>"><%=gelombang %></option>
					<%
		}
		else {
							%>
							<option value="<%=gelombang%>" selected="selected"><%=gelombang %></option>
					<%
		}			

					%>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						ANGKATAN
					</td>
					<td>				
				<%
		String angkatan = request.getParameter("angkatan");		
		if(angkatan==null || Checker.isStringNullOrEmpty(angkatan)) {
			angkatan = Checker.getThsmsPmb();
			//angkatan = "20161";
		}
		else {
		}	
		if(validUsr.amI("ADMIN")) {
		%>
			<input type="text" style="width:100%;height:35px;text-align:left" name="angkatan" value="<%=angkatan%>"/>
		<%	
		}
		else {
		%>
			<input type="text" style="width:100%;height:35px;text-align:left" name="angkatan" value="<%=angkatan%>" readonly/>
		<%	
		}
		%>
						
					</td>
				</tr>
<%
	}
	%>						
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA CALON MAHASISWA</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA LENGKAP
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nama = request.getParameter("nama");
					if(tkn_civ!=null && (nama==null || Checker.isStringNullOrEmpty(nama))) {
						nama = nmmhs_c; 
					}
					//System.out.println("nama="+nama);
					if(nama!=null && !Checker.isStringNullOrEmpty(nama)) {
					%>	
						<input type="text" style="width:100%;height:35px" name="nama" id="nama" value="<%=nama %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="text" style="width:100%;height:35px" name="nama" id="nama" placeholder="*wajib diisi"/>
					<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						EMAIL
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String email = request.getParameter("email");
					if(tkn_civ!=null && (email==null || Checker.isStringNullOrEmpty(email))) {
						email = email_e; 
					}
					if(email!=null && !Checker.isStringNullOrEmpty(email)) {
					%>	
						<input type="email" style="width:45%;height:35px" name="email" id="email" value="<%=email %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="email" style="width:45%;height:35px" name="email" id="email" placeholder="*wajib diisi"/>
					<%
					}
					%>
					&nbsp&nbsp&nbsp
					NO HP :
					<%
					String hape = request.getParameter("hape");
					if(tkn_civ!=null && (hape==null || Checker.isStringNullOrEmpty(hape))) {
						hape = nohpe_e; 
					}
					if(hape!=null && !Checker.isStringNullOrEmpty(hape)) {
					%>	
						<input type="number" style="width:25%;height:35px" name="hape" id="hape" value="<%=hape %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="number" style="width:25%;height:35px" name="hape" id="hape" placeholder="*wajib diisi"/>
					<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						GENDER
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String gender = request.getParameter("gender");
					if(tkn_civ!=null && (gender==null || Checker.isStringNullOrEmpty(gender))) {
						gender = kdjek_c; 
					}
					%>
						<select name="gender" style="width:100%;height:35px;text-align-last:center;">
					<%
					String[]kdjek = Constants.getOptionGender();
					for(int i=0;i<kdjek.length;i++) {
						String baris = kdjek[i];
						StringTokenizer st = new StringTokenizer(baris);
						String val = st.nextToken();
						String ket = st.nextToken();
						if(val.equalsIgnoreCase(gender)) {
							%>
							<option value="<%=val%>" selected="selected"><%=ket%></option>
							<%	
						}
						else {
							%>
							<option value="<%=val%>"><%=ket%></option>
								<%
						}	
					}
					%>	 
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KEWARGANEGARAAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String negara = request.getParameter("negara");
					if(tkn_civ!=null && (negara==null || Checker.isStringNullOrEmpty(negara))) {
						negara = warga_c; 
					}
					//System.out.println("negara jsp = "+negara);
					if(negara!=null && !Checker.isStringNullOrEmpty(negara)) {
					%>
						 <input name="negara" id="negara" style="width:100%;height:35px" type="text" value="<%=negara %>" placeholder="*wajib diisi">
					<%
					}
					else {
					%>
						 <input name="negara" id="negara" style="width:100%;height:35px" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	 
					</td>
				</tr>
				<%
				if(negara!=null && !Checker.isStringNullOrEmpty(negara) && !negara.equalsIgnoreCase("indonesia")) {
				%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NO PASPOR
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String paspor = request.getParameter("paspor");
					if(tkn_civ!=null && (paspor==null || Checker.isStringNullOrEmpty(paspor))) {
						paspor = paspor_c; 
					}
					//System.out.println("negara jsp = "+negara);
					if(paspor!=null && !Checker.isStringNullOrEmpty(paspor)) {
					%>
						 <input name="paspor" id="paspor" style="width:100%;height:35px" type="text" value="<%=paspor %>" placeholder="*wajib diisi">
					<%
					}
					else {
					%>
						 <input name="paspor" id="paspor" style="width:100%;height:35px" type="text" placeholder="*wajib diisi bagi non-WNI">
					<%
					}
					%>	 
					</td>
				</tr>
				<%	
				}

				if(negara!=null && !Checker.isStringNullOrEmpty(negara) && negara.equalsIgnoreCase("indonesia")) {
				%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NIK
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nik = request.getParameter("nik");
					if(tkn_civ!=null && (nik==null || Checker.isStringNullOrEmpty(nik))) {
						nik = nonik_c; 
					}
					
					if(nik!=null && !Checker.isStringNullOrEmpty(nik)) {
					%>
						<input type="text" style="width:100%;height:35px" name="nik" value="<%=nik %>" id="nik" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:100%;height:35px" name="nik" id="nik" placeholder="wajib diisi"/>
					<%
					}
					%>	
					</td>
				</tr>
				<%
				}
				%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NISN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nisn = request.getParameter("nisn");
					if(tkn_civ!=null && (nisn==null || Checker.isStringNullOrEmpty(nisn))) {
						nisn = nisn_c; 
					}
					if(nisn!=null && !Checker.isStringNullOrEmpty(nisn)) {
					%>
						<input type="text" style="width:100%;height:35px" name="nisn" value="<%=nisn %>" id="nisn" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:100%;height:35px" name="nisn" id="nisn" />
					<%
					}
					%>	
					</td>
				</tr>				
				
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						AGAMA
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String agama = request.getParameter("agama");
					if(tkn_civ!=null && (agama==null || Checker.isStringNullOrEmpty(agama))) {
						agama = agama_e; 
					}
					%>
						<select name="agama" style="width:100%;height:35px;text-align-last:center;">
					<%
					Vector vagama = Getter.getListAgama();
					ListIterator lia = vagama.listIterator();
					while(lia.hasNext()) {
						String brs = (String)lia.next();
						StringTokenizer st1 = new StringTokenizer(brs,"`");
						String id = st1.nextToken();
						String nm = st1.nextToken();
						if(nm.equalsIgnoreCase(agama)) {
							%>
							<option value="<%=nm%>" selected="selected"><%=nm%></option>
							<%	
						}
						else {
							%>
							<option value="<%=nm%>"><%=nm%></option>
								<%
						}	
					}
					%>	 
						</select>
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NEGARA KELAHIRAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nglhr = request.getParameter("nglhr");
					if(tkn_civ!=null && (nglhr==null || Checker.isStringNullOrEmpty(nglhr))) {
						nglhr = neglh_e; 
					}
					if(nglhr!=null && !Checker.isStringNullOrEmpty(nglhr)) {
					%>
						 <input name="nglhr" id="nglhr" style="width:100%;height:35px" type="text" value="<%=nglhr %>" placeholder="*harap diisi bila berbeda dengan kewarganegaraan">
					<%
					}
					else {
					%>
						 <input name="nglhr" id="nglhr" style="width:100%;height:35px" type="text" placeholder="*harap diisi bila berbeda dengan kewarganegaraan">
					<%
					}
					%>	 
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						TEMPAT/TGL LAHIR
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String tplhr = request.getParameter("tplhr");
					if(tkn_civ!=null && (tplhr==null || Checker.isStringNullOrEmpty(tplhr))) {
						tplhr = tplhr_c; 
					}
					if(tplhr!=null && !Checker.isStringNullOrEmpty(tplhr)) {
					%>
						<input name="tplhr" id="tplhr" style="width:50%;height:99%" type="text" value="<%=tplhr %>" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="tplhr" id="tplhr" style="width:50%;height:99%" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
						&nbsp&nbspTANGGAL :
					<%
					String tglhr = request.getParameter("tglhr");
					if(tkn_civ!=null && (tglhr==null || Checker.isStringNullOrEmpty(tglhr))) {
						tglhr = tglhr_c; 
					}
					if(tglhr!=null && !Checker.isStringNullOrEmpty(tglhr)) {
					%>	
						<input name="tglhr" id="tglhr" style="width:30%;height:99%" type="text" value="<%=tglhr %>" placeholder="*dd-mm-yyyy">
					<%	
					}
					else {
					%>	
						<input name="tglhr" id="tglhr" style="width:30%;height:99%" type="text" placeholder="*dd-mm-yyyy">
					<%
					}
					%>			 
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center; background-color: #f2f2f2;vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						INFO ORANGA TUA / WALI
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA AYAH
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String ayah = request.getParameter("ayah");
					if(tkn_civ!=null && (ayah==null || Checker.isStringNullOrEmpty(ayah))) {
						ayah = nama_ayah_c; 
					}
					if(ayah!=null && !Checker.isStringNullOrEmpty(ayah)) {
					%>
						<input type="text" style="width:100%;height:35px" name="ayah" value="<%=ayah %>" id="ayah" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:100%;height:35px" name="ayah" id="ayah" />
					<%
					}
					%>	
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA IBU
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String ibu = request.getParameter("ibu");
					if(tkn_civ!=null && (ibu==null || Checker.isStringNullOrEmpty(ibu))) {
						ibu = nama_ibu_c; 
					}
					if(ibu!=null && !Checker.isStringNullOrEmpty(ibu)) {
					%>
						<input type="text" style="width:100%;height:35px" name="ibu" value="<%=ibu %>" id="ibu" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:100%;height:35px" name="ibu" id="ibu" placeholder="*wajib diisi"/>
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center; background-color: #f2f2f2;vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						INFO TEMPAT TINGGAL
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						PROVINSI
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<select onchange="this.form.submit()" name="propinsi" style="width:100%;height:35px;text-align-last:center;">
							<option value="null">Pilih Propinsi</option>
						<%
						li = v1.listIterator();
						while(li.hasNext()) {
							String brs=(String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							String nm_wil = st.nextToken();
							String id_wil = st.nextToken();
							
							if(id_wil_prop.equalsIgnoreCase(id_wil)) {
								%>
							<option value="<%=brs %>" selected="selected"><%=nm_wil.replace("Prop.","")%></option>
							<%	
							}
							else {
						%>
							<option value="<%=brs %>"><%=nm_wil.replace("Prop.","")%></option>
						<%	
							}
						}
						%>
						</select>
					</td>
				</tr>
<%
if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi) && passed1) {
	

%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KOTA / KABUPATEN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<select onchange="this.form.submit()" name="kota" style="width:100%;height:35px;text-align-last:center;">
							<option value="null">Pilih Kota / Kabupaten</option>
						<%
						li = v2.listIterator();
						while(li.hasNext()) {
							String brs=(String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							String nm_wil = st.nextToken();
							String id_wil = st.nextToken();
							
							
							if(id_wil_kot.equalsIgnoreCase(id_wil)) {
								%>
							<option value="<%=brs %>" selected="selected"><%=nm_wil.replace("Kab.","").replace("Kab", "").replace("Kota", "").replace("Kota.", "") %></option>
							<%	
							}
							else {
						%>
							<option value="<%=brs %>"><%=nm_wil.replace("Kab.","").replace("Kab", "").replace("Kota", "").replace("Kota.", "")%></option>
						<%	
							}
						}	
						%>
						</select>
					</td>
				</tr>
<%	
}

if(kota!=null && !Checker.isStringNullOrEmpty(kota) && passed1) {
%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KECAMATAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					//kecamatan = request.getParameter("kecamatan");
					//if(tkn_civ!=null && (kecamatan==null || Checker.isStringNullOrEmpty(kecamatan))) {
					//	kecamatan = kecrm_e; 
					//}
					if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan)) {
						%>
						<input name="kecamatan" id="kecamatan" value="<%=kecamatan %>" style="width:100%;height:35px" type="text" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="kecamatan" id="kecamatan" style="width:100%;height:35px" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td rowspan="2" style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						ALAMAT
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String alamat = request.getParameter("alamat");
					if(tkn_civ!=null && (alamat==null || Checker.isStringNullOrEmpty(alamat))) {
						alamat = almrm_e; 
					}
					if(alamat!=null && !Checker.isStringNullOrEmpty(alamat)) {
						%>
						 <textarea placeholder="*wajib diisi" name="alamat" rows="3"  style="width:99%;vertical-align: middle;"><%=alamat %></textarea>
					<%
					}
					else {
						%>
						 <textarea placeholder="*wajib diisi" name="alamat" rows="3" style="width:99%;vertical-align: middle;"></textarea>
					<%
					}
					%>	 
					</td>
				</tr>
				<tr>
					<td colspan="2" style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						RT/RW :
					<%
					String rt = request.getParameter("rt");
					if(tkn_civ!=null && (rt==null || Checker.isStringNullOrEmpty(rt))) {
						rt = nortrm_e; 
					}
					if(rt!=null && !Checker.isStringNullOrEmpty(rt)) {
						%> 
						<input type="text" value="<%=rt %>" style="width:10%;height:99%" name="rt" id="rt" />
					<%	
					}
					else {
						%> 
						<input type="text" style="width:10%;height:99%" name="rt" id="rt" /> /
					<%
					}
					
					String rw = request.getParameter("rw");
					if(tkn_civ!=null && (rw==null || Checker.isStringNullOrEmpty(rw))) {
						rw = norwrm_e; 
					}
					if(rw!=null && !Checker.isStringNullOrEmpty(rw)) {
						%> 	 
						<input type="text" value="<%=rw %>" style="width:10%;height:99%" name="rw" id="rw" />
					<%
					}
					else {
						%> 	 
						<input type="text" style="width:10%;height:99%" name="rw" id="rw" />
					<%
					}
					String pos = request.getParameter("pos");
					if(tkn_civ!=null && (pos==null || Checker.isStringNullOrEmpty(pos))) {
						pos = posrm_e; 
					}
					if(pos!=null && !Checker.isStringNullOrEmpty(pos)) {
						%> 	 
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  KODE POS : <input type="text" value="<%=pos %>" style="width:20%;height:99%" name="pos" id="pos" />
						<%	
					}
					else {
						%> 	 
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  KODE POS : <input type="text" style="width:20%;height:99%" name="pos" id="pos" />
					<%
					}
					%>
					</td>
					
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KELURAHAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String kelurahan = request.getParameter("kelurahan");
					if(tkn_civ!=null && (kelurahan==null || Checker.isStringNullOrEmpty(kelurahan))) {
						kelurahan = kelrm_e; 
					}
					if(kelurahan!=null && !Checker.isStringNullOrEmpty(kelurahan)) {
						%>
						<input name="kelurahan" value="<%=kelurahan %>" id="kelurahan" style="width:100%;height:35px" type="text" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="kelurahan" id="kelurahan" style="width:100%;height:35px" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						DUSUN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String dusun = request.getParameter("dusun");
					if(tkn_civ!=null && (dusun==null || Checker.isStringNullOrEmpty(dusun))) {
						dusun = dusun_e; 
					}
					if(dusun!=null && !Checker.isStringNullOrEmpty(dusun)) {
						%>
						<input name="dusun" id="dusun" value="<%=dusun %>" style="width:100%;height:35px" type="text">
					<%	
					}
					else {
						%>
						<input name="dusun" id="dusun" style="width:100%;height:35px" type="text">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<section class="gradient">
						<div style="text-align:right; padding: 5px 5px">
						<button formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">DAFTARKAN</button>
						</div>
					</section>	
					</td>				
				</tr>
<%	
}

%>				
				
  			</tbody>
		</table>
	</form>
	<div class="col1">
	</center>
	</br/>
	</div>
</div>	
</body>
</html>