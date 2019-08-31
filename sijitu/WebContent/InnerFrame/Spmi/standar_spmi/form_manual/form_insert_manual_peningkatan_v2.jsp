<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.SearchManual"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>

<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//-------------------------------------------------
String ua=request.getHeader("User-Agent").toLowerCase();
boolean mobile=false;
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  //response.sendRedirect("http://detectmobilebrowser.com/mobile");
  //return;	
  mobile = true;
  
}
//--------------------------------------------

//System.out.println("sampe kesini dong");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
SearchStandarMutu ssm = new SearchStandarMutu();
SearchManual sm = new SearchManual();
ToolSpmi ts = new ToolSpmi();
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());


String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//Vector v= (Vector)request.getAttribute("v");
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_versi = request.getParameter("id_versi");
String id_tipe_std = request.getParameter("id_tipe_std");
String id_std = request.getParameter("id_std");
String id_master_std = request.getParameter("id_master_std");
System.out.println("id_versi1="+id_versi);
System.out.println("id_std="+id_std);
int norut_terakhir = 0;
norut_terakhir = ts.getNorutTerakhirManPelaksanaanStdUmum(Integer.parseInt(id_std));
//System.out.println("norut_terakhir1="+norut_terakhir);
String mode = request.getParameter("mode");
String at_menu_dash = request.getParameter("at_menu_dash");
String fwdto = request.getParameter("fwdto");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
/*
String pernyataan_std = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "PERNYATAAN_STD");
String target_param = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TKN_PARAMETER");
String target_indikator = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TKN_INDIKATOR");
String starting_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_PERIOD_START");
String target_val_1 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_1");
String target_val_2 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_2");
String target_val_3 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_3");
String target_val_4 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_4");
String target_val_5 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_5");
String target_val_6 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_6");
String target_unit = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_1_UNIT");
String unit_per_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "UNIT_PERIOD_USED");
String lama_per_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "LAMA_NOMINAL_PER_PERIOD");
*/
String thsms_now = Checker.getThsmsNow();

//String list_pihak_terkait_std_isi = ssm.getListPihakTerkaitPadaIsiStd(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
//System.out.println("pernyataan_std="+pernyataan_std);
//System.out.println("thsms_now="+thsms_now);
//System.out.println("starting_period="+starting_period);
//System.out.println("unit_per_period="+unit_per_period);
//System.out.println("lama_per_period="+lama_per_period);
/*
String target_val = null;
int norut_target_val = ToolSpmi.getNorutTargetParamIndikatorPeriod(starting_period, unit_per_period, lama_per_period);
if(norut_target_val==1) {
	target_val = new String(target_val_1);
}
else if(norut_target_val==2) {
	target_val = new String(target_val_2);
}
else if(norut_target_val==3) {
	target_val = new String(target_val_3);
}
else if(norut_target_val==4) {
	target_val = new String(target_val_4);
}
else if(norut_target_val==5) {
	target_val = new String(target_val_5);
}
else if(norut_target_val==6) {
	target_val = new String(target_val_6);
}
*/
//System.out.println("norut_target_val="+norut_target_val);
//System.out.println("id_std_isi="+id_std_isi);
//System.out.println("list_pihak_terkait_std_isi="+list_pihak_terkait_std_isi);
//Vector v_pihak = sdb.getListAvalablePihakTerkait_v1(false,list_pihak_terkait_std_isi);
//Vector v_group = sdb.getListAvalablePihakTerkait_v1(true,list_pihak_terkait_std_isi);
//param dari form di page ini

//String norut = request.getParameter("norut");
String norut_man = request.getParameter("norut_man");
//System.out.println("norut_man_form="+norut_man);
String ket_proses = request.getParameter("ket_proses");
if(!Checker.isStringNullOrEmpty(ket_proses)) {
	ket_proses = ket_proses.replace("<br>", "");
}
String capaian = request.getParameter("capaian");
if(!Checker.isStringNullOrEmpty(capaian)) {
	capaian = capaian.replace("<br>", "");
}

String manual = request.getParameter("manual");
if(!Checker.isStringNullOrEmpty(manual)) {
	manual = manual.replace("<br>", "");
}
//String tot_repetisi = request.getParameter("tot_repetisi");
//String satuan = request.getParameter("satuan");
//String tipe_sarpras = request.getParameter("tipe_sarpras");
//String catat_civitas = request.getParameter("catat_civitas");
Vector vListJabatan = sdb.getListTitleJabatanIndividu();
Vector vListJabatanGroup = sdb.getListTitleJabatanKelompok();
if(vListJabatanGroup!=null) {
	ListIterator li = vListJabatanGroup.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		brs = brs.replace("`", " [tipe: group]`");
		li.set(brs);
		//System.out.println("group="+brs);
	}
}
Vector vListJabatanGabungan = Tool.combine2VectorSameStructureAndRemoveDuplicate(vListJabatan, vListJabatanGroup);
//Vector v_kendal= (Vector)request.getAttribute("v_kendal");
//request.removeAttribute("v");

/*
bila edit maka variable ini akan di overide di bawah (dalam body table)
*/
String[]job_rumus=request.getParameterValues("job_rumus"); 
String[]job_cek=request.getParameterValues("job_cek"); 
String[]job_stuju=request.getParameterValues("job_stuju"); 
String[]job_tetap=request.getParameterValues("job_tetap"); 
String[]job_kendali=request.getParameterValues("job_kendali"); 
String[]job_survey=request.getParameterValues("job_survey"); 

String tujuan = request.getParameter("tujuan"); 
String lingkup = request.getParameter("lingkup"); 
String prosedur = request.getParameter("prosedur"); 
String kuali = request.getParameter("kuali"); 
String doc = request.getParameter("doc"); 
String ref = request.getParameter("ref"); 
String definisi = request.getParameter("definisi");

/*
AMBIL JABATAN DARI PROSES PERENCANAAN
*/
String brs1=null;
int versi_man_perencanaan_terkini = sm.getVersiTerakhirManualPerencanaanTermasukDraft(Integer.parseInt(id_std));
if(versi_man_perencanaan_terkini==0) {
	brs1 = sm.searchListManualPerencanaanUmum(1, Integer.parseInt(id_std));	
}
else {
	brs1 = sm.searchListManualPerencanaanUmum(versi_man_perencanaan_terkini, Integer.parseInt(id_std));
}

StringTokenizer st1 = new StringTokenizer(brs1,"~");
st1.nextToken();//String versi_id=
st1.nextToken();//id_std=
st1.nextToken();//String tgl_sta=
st1.nextToken();//String tgl_end=
String tkn_jab_rumus_plan=st1.nextToken();
System.out.println("tkn_jab_rumus_plan="+tkn_jab_rumus_plan);
String tkn_jab_cek_plan=st1.nextToken();
String tkn_jab_stuju_plan=st1.nextToken();
String tkn_jab_tetap_plan=st1.nextToken();
String tkn_jab_kendal_plan=st1.nextToken();
String tkn_jab_survey_plan=st1.nextToken();
String[]job_rumus_at_perencanaan=null; 
String[]job_cek_at_perencanaan=null; 
String[]job_stuju_at_perencanaan=null; 
String[]job_tetap_at_perencanaan=null; 
String[]job_kendali_at_perencanaan=null; 
String[]job_survey_at_perencanaan=null;
if(!Checker.isStringNullOrEmpty(tkn_jab_rumus_plan) && job_rumus==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_rumus_plan,"`");
	int length = st1.countTokens();
	job_rumus_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_rumus_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_rumus = job_rumus_at_perencanaan;
}
if(!Checker.isStringNullOrEmpty(tkn_jab_cek_plan) && job_cek==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_cek_plan,"`");
	int length = st1.countTokens();
	job_cek_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_cek_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_cek = job_cek_at_perencanaan;
}
if(!Checker.isStringNullOrEmpty(tkn_jab_stuju_plan) && job_stuju==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_stuju_plan,"`");
	int length = st1.countTokens();
	job_stuju_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_stuju_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_stuju = job_stuju_at_perencanaan;
}
if(!Checker.isStringNullOrEmpty(tkn_jab_tetap_plan) && job_tetap==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_tetap_plan,"`");
	int length = st1.countTokens();
	job_tetap_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_tetap_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_tetap = job_tetap_at_perencanaan;
}
if(!Checker.isStringNullOrEmpty(tkn_jab_kendal_plan) && job_kendali==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_kendal_plan,"`");
	int length = st1.countTokens();
	job_kendali_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_kendali_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_kendali = job_kendali_at_perencanaan;
}
if(!Checker.isStringNullOrEmpty(tkn_jab_survey_plan) && job_survey==null) {
	System.out.println("masuk");
	st1 = new StringTokenizer(tkn_jab_survey_plan,"`");
	int length = st1.countTokens();
	job_survey_at_perencanaan = new String[length];
	for(int i=0;i<length;i++) {
		job_survey_at_perencanaan[i]=new String(st1.nextToken());
	}
	job_survey = job_survey_at_perencanaan;
}

%>
<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

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
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }

</style>
<style>
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }


label{
  	width: 150px;
 	display: inline-block; 
  	vertical-align: top;
}
</style>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%;  text-align:center;}
	table.StateTable{margin:0px; border:none;}
	
	table td{padding:0px;}
	table.StateTable thead th{background:#DBDBDB; padding: 0px; cursor:pointer; color:white;color:#369;border:none}
	table.CityTable thead th{padding: 0px; background: white;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	
	
	$('table.CityTable th') .click(
		function() {
			$(this) .parents('table.CityTable') .children('tbody') .toggle();
		}
	)
	
	$('table.StateTable tr.statetablerow th') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	/*
	$('table.StateTable tr.statetablerow td') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	*/
});
</script>
</head>
<body>
<jsp:include page="menu_back.jsp" />
<div class="colmask fullpage">
	<div class="col1">
	
		<!-- Column 1 start -->
		<div style="text-align:center;padding:0 0 0 3px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>
		<br>
		
<%
Vector v_err = (Vector)request.getAttribute("v_err");
request.removeAttribute("v_err");
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:1.2em;color:red;font-weight:bold">
	HARAP PERBAIKI DATA BERIKUT:
	</div>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
%>	
	</div>
	<br><%="manual="+manual %><br>
<%	
}		



boolean editor = false;
boolean terkait = false;
boolean pengawas = false;		

if(validUsr.amI("ADMIN")||validUsr.amIcontain("MUTU")) {
	editor = true; //terkait & pengaeas
	terkait = true;
	pengawas = true;
}
%>		
	<center>
	<form action="go.updManualPelaksanaan_v1" method="post">
		<input type="hidden" name="fwdto" value="<%=fwdto %>" />
		<input type="hidden" name="id_kendali" value="buat apa?" />
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
		<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
		<input type="hidden" name="id_master_std" value="<%=id_master_std %>" />
		<input type="hidden" name="id_std" value="<%=id_std %>" />
		<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
		<input type="hidden" name="id_versi_man" value="<%=id_versi %>" />

		<%
if(mode!=null&&mode.equalsIgnoreCase("insert")) {
	//berarti add baru
	//System.out.println("add baru");
%>
		<input type="hidden" name="mode" value="insert" />
		
<%
}
else {
	//edit yg ada
	//System.out.println("edit yg ada");
	//SearchManual sm = new SearchManual();

	
	String brs = sm.searchListManualPelaksanaanUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std));
	//System.out.println("baris="+brs);
	//versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
	st1 = new StringTokenizer(brs,"~");
	String versi_id=st1.nextToken();
	id_std=st1.nextToken();
	String tgl_sta=st1.nextToken();
	String tgl_end=st1.nextToken();
	String tkn_jab_rumus=st1.nextToken();
	//System.out.println("tkn_jab_rumus="+tkn_jab_rumus);
	if(!Checker.isStringNullOrEmpty(tkn_jab_rumus)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_rumus,"`");
		job_rumus = new String[st2.countTokens()];
		for(int j=0;j<job_rumus.length;j++) {
			job_rumus[j]=st2.nextToken();
		}
	}
	String tkn_jab_cek=st1.nextToken();
	if(!Checker.isStringNullOrEmpty(tkn_jab_cek)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_cek,"`");
		job_cek = new String[st2.countTokens()];
		for(int j=0;j<job_cek.length;j++) {
			job_cek[j]=st2.nextToken();
		}
	}
	String tkn_jab_stuju=st1.nextToken();
	if(!Checker.isStringNullOrEmpty(tkn_jab_stuju)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_stuju,"`");
		job_stuju = new String[st2.countTokens()];
		for(int j=0;j<job_stuju.length;j++) {
			job_stuju[j]=st2.nextToken();
		}
	}
	String tkn_jab_tetap=st1.nextToken();
	if(!Checker.isStringNullOrEmpty(tkn_jab_tetap)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_tetap,"`");
		job_tetap = new String[st2.countTokens()];
		for(int j=0;j<job_tetap.length;j++) {
			job_tetap[j]=st2.nextToken();
		}
	}
	String tkn_jab_kendal=st1.nextToken();
	if(!Checker.isStringNullOrEmpty(tkn_jab_kendal)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_kendal,"`");
		job_kendali = new String[st2.countTokens()];
		for(int j=0;j<job_kendali.length;j++) {
			job_kendali[j]=st2.nextToken();
		}
	}
	String tkn_jab_survey=st1.nextToken();
	if(!Checker.isStringNullOrEmpty(tkn_jab_survey)) {
		StringTokenizer st2 = new StringTokenizer(tkn_jab_survey,"`");
		job_survey = new String[st2.countTokens()];
		for(int j=0;j<job_survey.length;j++) {
			job_survey[j]=st2.nextToken();
		}
	}
	tujuan=st1.nextToken();
	while(tujuan.contains("<br>")) {
		tujuan = tujuan.replace("<br>", "\n");
	}
	lingkup=st1.nextToken();
	while(lingkup.contains("<br>")) {
		lingkup = lingkup.replace("<br>", "\n");
	}
	definisi=st1.nextToken();
	while(definisi.contains("<br>")) {
		definisi = definisi.replace("<br>", "\n");
	}
	prosedur=st1.nextToken();
	while(prosedur.contains("<br>")) {
		prosedur = prosedur.replace("<br>", "\n");
	}
	kuali=st1.nextToken();
	while(kuali.contains("<br>")) {
		kuali = kuali.replace("<br>", "\n");
	}
	doc=st1.nextToken();
	while(doc.contains("<br>")) {
		doc = doc.replace("<br>", "\n");
	}
	ref=st1.nextToken();
	while(ref.contains("<br>")) {
		ref = ref.replace("<br>", "\n");
	}
%>
		<input type="hidden" name="mode" value="edit" />
		<input type="hidden" name="id_versi" value="<%=id_versi %>" />
<%
}
%>	 		
		<table class="table1" id="tabel" style="width:100%" >
			<thead>
				<tr>
					<th colspan="2" style="font-size:2em;text-align:center;font-weight:bold;">
						FORM INPUT MANUAL PELAKSANAAN STANDAR
					</th>
				</tr>
				<tr>
					<td colspan="2" style=";background:<%=Constant.mildColorBlu()%>;padding:0 0 0 5px;font-size:1.5em;text-align:left;font-weight:bold;color:#369">
						I. TENTUKAN PIHAK TERKAIT
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						1) &nbspPROSES PERUMUSAN
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>		
						<%
ListIterator li = vListJabatan.listIterator();						
int counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_rumus!=null && job_rumus.length>0) {
		for(int i=0;i<job_rumus.length&&!match;i++) {
			if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_rumus[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_rumus" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_rumus" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_rumus" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_rumus" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						2) &nbspPROSES PEMERIKSAAN
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>		
						<%
li = vListJabatan.listIterator();						
counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_cek!=null && job_cek.length>0) {
		for(int i=0;i<job_cek.length&&!match;i++) {
			if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_cek[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_cek" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_cek" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_cek" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_cek" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						3) &nbspPROSES PERSETUJUAN
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>		
						<%
li = vListJabatan.listIterator();						
counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_stuju!=null && job_stuju.length>0) {
		for(int i=0;i<job_stuju.length&&!match;i++) {
			if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_stuju[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_stuju" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_stuju" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_stuju" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_stuju" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						4) &nbspPROSES PENETAPAN
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>		
						<%
li = vListJabatan.listIterator();						
counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_tetap!=null && job_tetap.length>0) {
		for(int i=0;i<job_tetap.length&&!match;i++) {
			if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_tetap[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_tetap" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_tetap" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_tetap" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_tetap" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>	
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						5) &nbspPROSES PENGENDALIAN
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>								
						<%
li = vListJabatan.listIterator();						
counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_kendali!=null && job_kendali.length>0) {
		for(int i=0;i<job_kendali.length&&!match;i++) {
			if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_kendali[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_kendali" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_kendali" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_kendali" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_kendali" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>	
					</td>
				</tr>
				<tr>
					<td style="padding:0 5px 0 25px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						6) &nbspPROSES AUDIT MUTU INTERNAL
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr class="statetablerow">
								<th colspan="3">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>								
						<%
li = vListJabatanGabungan.listIterator();						
counter = 0;
while(li.hasNext()) {
	boolean match = false;
	counter++;
	String brs = (String)li.next();
	//System.out.println("job="+brs);
	st = new StringTokenizer(brs,"`");
	String jabatan = st.nextToken();
	String sin = st.nextToken();
	if(job_survey!=null && job_survey.length>0) {
		for(int i=0;i<job_survey.length&&!match;i++) {
			if(jabatan.replace("[tipe: group]", "").trim().toUpperCase().equalsIgnoreCase(job_survey[i].trim().toUpperCase())) {
				match = true;
			}
		}
	}
	
	if(counter%3!=0) {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_survey" value="<%=jabatan.replace("[tipe: group]", "").trim() %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_survey" value="<%=jabatan.replace("[tipe: group]", "").trim() %>" > <label><%=jabatan %></label></td>
<%	
		}
	}
	else {
		if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_survey" value="<%=jabatan.replace("[tipe: group]", "").trim() %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
		}
		else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_survey" value="<%=jabatan.replace("[tipe: group]", "").trim() %>" > <label><%=jabatan %></label></td>
<%	
		}
%>		
							</tr>
							<tr class="statetablerow">
<%			
	}
	if(!li.hasNext()) {
		%>
							</tr>
		<%		
	}
}	
%>
						</tbody>	
						</table>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						TUJUAN DAN MAKSUD DOKUMEN MANUAL
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(tujuan)) {
%>					
						<textarea name="tujuan" style="width:100%;height:100px;row:5;border:none">Tujuan dan maksud manual penetapan standar pengelolaan penelitian disusun untuk memberikan pedomman kepada pihak yag akan terlibat dalam proses pelaksanaan dan pemantauan pemenuhan ketercapaian isi standar [sebutkan nama standar]</textarea>
<%
}
else {
	%>					
						<textarea name="tujuan" style="width:100%;height:100px;row:5;border:none"><%=tujuan%></textarea>
<%	
}
%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						LUAS LINGKUP MANUAL
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(lingkup)) {
%>					
						<textarea name="lingkup" style="width:100%;height:100px;row:5;border:none">Manual pelaksanaa standar [sebutkan nama standar] deiberlakukan untuk memastikan pihak yang bertanggungjawab unuk melaksanakan dan memenuhi  isi standar mulai dari kegiatan sosialisasi</textarea>
<%
}
else {
	%>					
						<textarea name="lingkup" style="width:100%;height:100px;row:5;border:none"><%=lingkup %></textarea>
<%	
}
%>						
					</td>
				</tr>	
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						DEFINISI & ISTILAH
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(definisi)) {
%>						
						<textarea name="definisi" placeholder="hanya diisi definisi kata yang bersifat teknis, bila tidak ada isikan 'Tidak ada istilah teknis yang digunakan dalam penetapan standar'" style="width:100%;height:100px;row:5;border:none"><%="Tidak ada istilah teknis yang digunakan dalam penetapan standar" %></textarea>
<%
}
else {
	%>						
						<textarea name="definisi" style="width:100%;height:100px;row:5;border:none"><%=definisi %></textarea>
<%	
}
%>						
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						PROSEDUR & LANGKAH MERANCANG, MERUMUSKAN & MENETAPKAN STANDAR
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(prosedur)) {
%>					
						<textarea name="prosedur" placeholder="jelaskan dan isikan tahapan secara berurutan dan jelas" style="width:100%;height:100px;row:5;border:none"></textarea>
<%
}
else {
	%>						
						<textarea name="prosedur" style="width:100%;height:100px;row:5;border:none"><%=prosedur %></textarea>
<%	
}
%>							
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						KUALIFIKASI PEJABAT/PETUGAS YANG MENJALANKAN PROSEDUR
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(kuali)) {
%>					
						<textarea name="kuali" placeholder="isikan kualifikasi masing-masing pihak terkait" style="width:100%;height:100px;row:5;border:none"></textarea>
<%
}
else {
	%>						
						<textarea name="kuali" style="width:100%;height:100px;row:5;border:none"><%=kuali %></textarea>
<%	
}
%>						
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						DOKUMEN TERKAIT
					</td>
					<td style="paddig:5px 5px 5px 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
						<table width="100%" style="border:none">
							<tr>
								<td width="50%" style="padding:5px 5px 5px 0;text-align:right;border-color:<%=Constant.lightColorBlu()%>;vertical-align: middle;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold" >
								Tambah Jenis/Nama Dokumen Baru
								</td>
								<td width="50%" style="border-color:<%=Constant.lightColorBlu()%>;text-align:left;vertical-align: middle;background: <%=Constant.lightColorBlu()%>;color:#369;font-weight:bold" >			
									<section class="gradient" style="text-align:left;vertical-align: top;">
		            					<button style="font-size: 20px;" value="dok_terkait" name="tombol">+</button>
	        						</section>
	        					</td>
							</tr>
						</table>
						<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/selec_doc_man.jsp">
						<jsp:param name="tkn_doc" value="<%=doc %>"/>
						</jsp:include>
						
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle;padding:0 5px 0 5px;width:20%;font-size:1.em;text-align:left;font-weight:bold;color:#369">
						REFERENSI
					</td>
					<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
<%
if(Checker.isStringNullOrEmpty(ref)) {
%>					
						<textarea name="ref" placeholder="isikan referensi external dan internal" style="width:100%;height:100px;row:5;border:none"></textarea>
<%
}
else {
	%>						
						<textarea name="ref" style="width:100%;height:100px;row:5;border:none"><%=ref %></textarea>
<%	
}
%>							
					</td>
				</tr>
				<tr>
					<td colspan="2" style="padding:10px 10px">
					<section class="gradient">
						<div style="text-align:center">
							<button style="padding: 5px 50px;font-size: 20px;">Add / Update Manual</button>
						</div>
					</section>
					</td>
				</tr>
			</thead>
		</table>	
		<br>

	</form>
	</center>
		<br><br>

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>