<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.spmi.SearchQandA" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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

/*
SETTING SEARCHIN LIMIT DISINI  
*/
String src_manual_limit="6";
session.setAttribute("src_manual_limit",src_manual_limit); 
boolean ada_manual_yg_bisa_diedit = false;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String[]visi_misi_tujuan_nilai = Getter.getVisiMisiTujuanNilaiPt(Constants.getKdpti());

Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
Boolean team_spmi = (Boolean) session.getAttribute("team_spmi");//asal jabatan ada mutu
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//Vector v= (Vector)request.getAttribute("v");
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String at_menu_dash = request.getParameter("at_menu_dash");
String std_kdpst = "null";
String scope_std = "null";
String versi_standar = null;
SearchQandA sqa = new SearchQandA();
boolean editor = spmi_editor.booleanValue();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
String unit_used=null;
ToolSpmi ts = new ToolSpmi();
String darimana = request.getParameter("darimana");
SearchStandarMutu ssm = new SearchStandarMutu();
//boolean sdh_aktif = ssm.isStandardActivated(Integer.parseInt(id_std_isi));

//SearchManualMutu smm = new SearchManualMutu();
//smm.autoInsertManual(Integer.parseInt(id_versi),Integer.parseInt(id_std_isi), "evaluasi");

//System.out.println("id_std0="+id_std);
//System.out.println("id_tipe_std0="+id_tipe_std);
//System.out.println("id_master_std0="+id_master_std);

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
/*.table:hover td { background:#82B0C3 }*/
</style>
<style>
.table0 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
table-layout: fixed;
}
.table0 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;
font-weight:bold;
word-wrap:break-word;
}
.table0 thead > tr > th, .table0 tbody > tr > t-->h, .table0 tfoot > tr > th, .table0 thead > tr > td, .table0 tbody > tr > td, .table0 tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }

.table0-noborder { border: none;background:<%=Constant.lightColorBlu()%>;word-wrap:break-word; }
.table0-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold;word-wrap:break-word; }
.table0-noborder thead > tr > th, .table0-noborder tbody > tr > th, .table0-noborder tfoot > tr > th, .table0-noborder thead > tr > td, .table0-noborder tbody > tr > td, .table0-noborder tfoot > tr > td { border: none;padding: 2px;word-wrap:break-word; }

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
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 tr:hover td { background:#82B0C3 }
</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
	
	
	
	
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
	
	$('table.StateTable tr.statetablerow td') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	
	
	
});
</script>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #fff;;text-align:center;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	table.CityTable thead th.blue{padding: 0px; background: #369;cursor:pointer; color:black;padding:5px 0 5px 5px;text-align:left}
	table.CityTable thead:hover th.blue { background:#82B0C3;;text-align:left;padding:5px 0 5px 5px; }
	
	table.StateTable td.nopad{background: #369;padding:0;;text-align:center;}
	table.StateTable th.yellow{background: #FE9A2E;padding:0;;text-align:center;}
	table.StateTable tr:hover td.nopad { background:#82B0C3 }
	table.StateTable thead tr:hover td { background:#82B0C3;;text-align:center; }
	table.StateTable tr:hover th.yellow { background:#FFB766;;text-align:center; }
	
</style>
<script>
	$(document).ready(function() {
		$("#form1").hide();
		$("#upload").hide();
		$("#chart").show();
		$("#ami").hide();
		$("#prep").hide();
		
		document.links[6].className = 'active';
		document.links[7].className = '';
		document.links[8].className = '';
		document.links[9].className = '';
		document.links[10].className = '';
		document.getElementById('chart').style.display='block';
		document.getElementById('form1').style.display='none';
		document.getElementById('upload').style.display='none';
		document.getElementById('ami').style.display='none';
		document.getElementById('prep').style.display='none';
	});	
</script>




<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
</head>
<%
if(!Checker.isStringNullOrEmpty(darimana)&&darimana.equalsIgnoreCase("riwayat")) {
	%>
<body onload="window.resizeTo(screen.width, 4500);document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='block';document.links[7].className = 'active';document.links[6].className = '';document.links[8].className = '';document.links[9].className = '';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='none';">
<%
}
else if(!Checker.isStringNullOrEmpty(darimana)&&darimana.equalsIgnoreCase("ami")) {
	%>
<body onload="window.resizeTo(screen.width, 4500);document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='block';document.links[9].className = 'active';document.links[7].className = '';document.links[6].className = '';document.links[8].className = '';">
<%	
}
else if(!Checker.isStringNullOrEmpty(darimana)&&darimana.equalsIgnoreCase("prep")) {
	%>
<body onload="window.resizeTo(screen.width, 4500);document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='none';document.getElementById('prep').style.display='block';document.links[10].className = '';document.links[9].className = 'active';document.links[7].className = '';document.links[6].className = '';document.links[8].className = '';">
<%	
}
else {
%>
<body onload="window.resizeTo(screen.width, 4500);document.getElementById('chart').style.display='block';document.getElementById('form1').style.display='none';document.links[7].className = '';document.links[6].className = 'active';document.links[8].className = '';document.links[9].className = '';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='none';">
<%
}
%>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="tunggu ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<jsp:include page="menu_dash_v1.jsp" />
<div class="colmask fullpage">
	<div class="col1">

		<!-- Column 1 start -->
		<div style="text-align:center;padding:0 0 0 2px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>
	<br>
		<%
		
			
int norut=0;

%>
<div id="header">
<ul>
	<li><a href="#form" onclick="document.getElementById('prep').style.display='none';document.getElementById('chart').style.display='block';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='none';document.links[6].className = 'active';document.links[7].className = '';document.links[8].className = '';document.links[9].className = '';document.links[10].className = '';" >MANUAL EVALUASI<br>PELAKSANAAN STANDAR</a></li>
	<li><a href="#form" onclick="document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='block';document.getElementById('upload').style.display='none';document.getElementById('ami').style.display='none';document.getElementById('prep').style.display='none';document.links[6].className = '';document.links[7].className = 'active';document.links[8].className = '';document.links[9].className = '';document.links[10].className = '';" >KEGIATAN EVALUASI<br>PELAKSANAAN STANDAR</a></li>
	<li><a href="#form" onclick="document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='block';document.getElementById('ami').style.display='none';document.getElementById('prep').style.display='none';document.links[6].className = '';document.links[7].className = '';document.links[8].className = 'active';document.links[9].className = '';document.links[10].className = '';" >ARSIP FILE<br>DOKUMEN</a></li>
	<li><a href="#form" onclick="document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('prep').style.display='block';document.getElementById('ami').style.display='none';document.links[6].className = '';document.links[7].className = '';document.links[8].className = '';document.links[9].className = 'active';document.links[10].className = '';" >PERSIAPAN KEGIATAN<br>AUDIT MUTU INTERNAL</a></li>
<%
/*
%>	
	<li><a href="#form" onclick="document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('prep').style.display='none';document.getElementById('ami').style.display='block';document.links[6].className = '';document.links[7].className = '';document.links[8].className = '';document.links[9].className = '';document.links[10].className = 'active';" >RESERVED FOR RIWAYAT<br>AUDIT MUTU INTERNAL</a></li>
<%
*/
%>	
</ul>

</div>
<div id="form1">
<br>&nbsp<br>
<%
/*
Vector v_err_man = (Vector)request.getAttribute("v_err_man");
request.removeAttribute("v_err_man");
if(v_err_man!=null && v_err_man.size()>0) {
	ListIterator litmp = v_err_man.listIterator();
%>

<div style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:red">
<%
	while(litmp.hasNext()) {
		String brs2 = (String)litmp.next();
		out.print("* "+brs2+"<br>");
	}
	
%>	
</div>

<%	
}
*/
SearchManual sm = new SearchManual();
Vector v_kendal= sm.searchListManualEvaluasiUmum(Integer.parseInt(id_std));
//Vector v_best_manual_kendal = sm.getManualEvaluasiAktifAtoLatestDraft(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
if(!editor) {	
%>
<table class="table2" id="tabel2" style="width:100%">
<%
}
else {
	%>
<table class="table2" id="tabel2" style="width:100%">
	<%	
}
%>
	<thead>	
		<tr>
			<th colspan="3" style="border-bottom:thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:0 5px;width:100%;border:none;font-size:1.5em;font-weight:bold">
			MANUAL STANDAR EVALUASI
			</th>	
		</tr>
		<tr>
			<th style="width:5%;border-style: solid;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			NO
			</th>
			<th style="width:80%;border-style: solid;border-left: thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			PROSEDUR DAN LANGKAH EVALUASI STANDAR
			</th>
			<th style="width:15%;border-style: solid;border-left: thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			STATUS MANUAL 
			</th>
		</tr>
	</thead>
	
<%

if(v_kendal==null || v_kendal.size()<1) {
	boolean ada_penetapan_man=true;
	if(!ada_penetapan_man) {
		%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Penetapan Manual Standar <br> Harap kembali dan selesaikan proses I: MANUAL PERENCANAAN STANDAR					
			</td>
		</tr>
<%		
	}
	else {
%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Manual Evaluasi Pelaksanaan Standar <br> Silahkan Input Manual Pelaksanaan Standar yang Sudah Dirumuskan Pada Proses Perencanaan Standar					
			</td>
		</tr>
<%
	}
}
else {
	ListIterator li = v_kendal.listIterator();
	int counter = v_kendal.size();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		String id_versi = st.nextToken();
		//System.out.println("id_versi_ini="+id_versi);
		st.nextToken();//ignore id_std = request parameter
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String tkn_jab_rumus = st.nextToken();
		String tkn_jab_cek = st.nextToken();
		String tkn_jab_stuju = st.nextToken();
		String tkn_jab_tetap = st.nextToken();
		String tkn_jab_kendal = st.nextToken();
		String tkn_jab_survey = st.nextToken();
		String tujuan = st.nextToken();
		String lingkup = st.nextToken();
		String definisi = st.nextToken();
		String prosedur = st.nextToken();
		String kualifikasi = st.nextToken();
		String dokumen = st.nextToken();
		String referensi = st.nextToken();
		
		String status_manual = "N/A";
		if(Checker.isStringNullOrEmpty(tgl_sta)) {
			status_manual = "BELUM DIAKTIFKAN";
			ada_manual_yg_bisa_diedit = true; 
		}
		else {
			status_manual = "AKTIF";
		}
		if(!Checker.isStringNullOrEmpty(tgl_end)) {
			status_manual = "SUDAH TIDAK BERLAKU";
		}
		boolean man_expired=ts.apaManualSudahExpiredUmum(Integer.parseInt(id_std), Integer.parseInt(id_std), "PERENCANAAN");
		/*
		String target_kondisi = st.nextToken();
		String target_proses_dan_obj = st.nextToken();
		String manual_kegiatan = st.nextToken();
		String interval_pengawasan = st.nextToken();
		String unit_interval_pengawasan = st.nextToken();
		String jabatan_pengawas = st.nextToken();
		String tkn_id_uu = st.nextToken();
		String tkn_id_permen = st.nextToken();
		String jabatan_inputer = st.nextToken();
		String tipe_sarpras = st.nextToken();
		String catat_civitas = st.nextToken();
		*/
		//boolean am_i_surveyor = validUsr.amI_v1(jabatan_inputer, kdpst);
		//boolean am_i_controller = validUsr.amI_v1(jabatan_pengawas, kdpst);
		//System.out.println("--"+jabatan_inputer+"--"+am_i_surveyor);
		//System.out.println("--"+jabatan_pengawas+"--"+am_i_controller);
		//if(status_manual!=null &&  status_manual.equalsIgnoreCase("AKTIF")) {
		if(true) {	
%>	
		<tr>
<%
		}
		else {
			%>	
		<tr onclick="javascript:alert('Manual harus diaktifkan terlebih dahulu untuk melihat / manambah riwayay kegiatan pelaksanaan');">
	<%			
		}	
%>	

			<td style="text-align:center;padding:0 2px;font-size:1.3em">
				<%=counter %>	
<%		
		//if(editor && status_manual!=null && status_manual.equalsIgnoreCase("BELUM DIAKTIFKAN")) {
		if(false) {
%>
			<form action="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp" method="post">
				<!--  at_menu_dash=< at_menu_dash-->
				<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
				<input type="hidden" name="id_std" value="<%=id_std%>"/>
				<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash%>"/>
				<input type="hidden" name="fwdto" value="dashboard_std_manual_pengendalian_eval_v1.jsp"/>
				<input type="hidden" name="id_versi" value="<%=id_versi%>"/>
				<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std%>"/>
				<input type="hidden" name="id_master_std" value="<%=id_master_std%>"/>
				<input type="image" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30" alt="Submit Form" />
			</form>
			
			<!--  form action="#" method="post">
				<input type="hidden" name="kdpst_nmpst_kmp" value="<=kdpst_nmpst_kmp%>"/>
				<input type="hidden" name="id_kendali" value=""/>
				<input type="hidden" name="id_versi" value="<=id_versi%>"/>
				<input type="hidden" name="id_std_isi" value="<=id_std_isi%>"/>
				<input type="hidden" name="norut" value="<=norut_man%>"/>
				<input type="hidden" name="capaian" value=""/>
				<input type="hidden" name="ket_proses" value=""/>
				<input type="hidden" name="manual" value=""/>
				<input type="hidden" name="tot_repetisi" value=""/>
				<input type="hidden" name="satuan" value=""/>
				<input type="hidden" name="job" value=""/>
			
				<input type="hidden" name=job_input value=""/>
				<input type="hidden" name="fwdto" value="dashboard_std_manual_pengendalian_eval.jsp"/>
				<input type="hidden" name="at_menu_dash" value="<=at_menu_dash%>"/>
				<input type="image" src="<=Constants.getRootWeb() %>/images/delete_v2.png" width="75" height="30" alt="Submit Form" onclick="if (confirm('Hapus Manual Standar Evaluasi No <=counter%>?!?')) form.action='go.updManualStd?hapus=true'; else return false;" />
																																				
			  </form-->
			
<%			
		}
%>									
			</td>
			<td onclick="window.location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/riwayat_evaluasi_v1.jsp?status_manual=<%=status_manual%>&starting_no=1&unit_used=<%=unit_used %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat&at_menu_dash=control'" style="text-align:left;padding:0 2px;font-size:0.9em;padding:0 0 0 5px">
				<%=prosedur %>						
			</td>
			<td style="text-align:center;padding:0 2px;font-size:0.9em">
				<%
				out.print(status_manual+"<br>");
				/*
				if(status_manual.equalsIgnoreCase("AKTIF")) {
					%>
				<a class="img2" href="set.aktivasiMan_v1?status=aktif&tipe_manual=evaluasi&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan memberhentikan penggunaan manual ini ?!? \nNote:\n1. Anda tidak dapat mengaktifkan kembali manual yg sudah diberhentikan.\n2. Pemberhentian manual dilakukan bila manual digantikan dengan revisi yg baru')) return true;return false; ">	
					<img border="0" alt="STOP" src="<%=Constants.getRootWeb() %>/images/stop_red.png" width="30" height="30">
				</a>	
					<%	
				}
				else if(status_manual.equalsIgnoreCase("BELUM DIAKTIFKAN")) {
					%>
				<a class="img2" href="set.aktivasiMan_v1?status=nonaktif&tipe_manual=evaluasi&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengaktifkan penggunaan manual ini ?!? \nSyarat Aktivasi:\n1. Draf manual telah melalui kegiatan perumusan, pemeriksaan, persetujuan dan penetapan.\n2. Pastikan draf manual telah diisi dengan benar, karena manual yg telah diaktifkan tidak dapat diedit')) return true;return false; ">	
					<img border="0" alt="PLAY" src="<%=Constants.getRootWeb() %>/images/play_blue.png" width="70" height="30">
				</a>	
					<%
				}
				*/
				%>							
			</td>
			
		</tr>
<%	
		counter--;
	}
}	
//if(editor && !ada_manual_yg_bisa_diedit) {
if(false) {
	%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
			<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp?mode=insert&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="vertical-align:middle;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Manual Evaluasi Pelaksanaan Baru 
			</a>		
			</td>
		</tr>
<%	
}	
%>		
	
</table>
</div>
<div id="chart">
	<br><br>
<%
Vector v_err_man = (Vector)request.getAttribute("v_err_man");
request.removeAttribute("v_err_man");
if(v_err_man!=null && v_err_man.size()>0) {
	ListIterator litmp = v_err_man.listIterator();
%>

<div style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:red">
<%
	while(litmp.hasNext()) {
		String brs2 = (String)litmp.next();
		out.print("* "+brs2+"<br>");
	}
	
%>	
</div>

<%	
}
//System.out.println("v_kendal="+v_kendal);
if(v_kendal==null || v_kendal.size()<1) {
	%>
	<h1 style="text-align:center;color:red">
		BELUM ADA MANUAL EVALUASI PELAKSANAAN STANDAR
	</h1>
	<table width="100%;border:none">
	<tr>
		<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
			<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp?mode=insert&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="vertical-align:middle;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Manual Evaluasi Pelaksanaan Baru 
			</a>		
		</td>
	</tr>
</table>	
	<%
}
else {
	
	String tmp="";		
	ListIterator li = v_kendal.listIterator();
	int counter = 1;
	if(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		String id_versi = st.nextToken();
		st.nextToken();//id_std ignore = request parameter
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String tkn_jab_rumus = st.nextToken();
		//System.out.println("rumus="+tkn_jab_rumus);
		String tkn_jab_cek = st.nextToken();
		String tkn_jab_stuju = st.nextToken();
		String tkn_jab_tetap = st.nextToken();
		String tkn_jab_kendal = st.nextToken();
		String tkn_jab_survey = st.nextToken();
		String tujuan = st.nextToken();
		String lingkup = st.nextToken();
		String definisi = st.nextToken();
		String prosedur = st.nextToken();
		String kualifikasi = st.nextToken();
		String dokumen = st.nextToken();
		String referensi = st.nextToken();
		
		String status_manual = "N/A";
		if(Checker.isStringNullOrEmpty(tgl_sta)) {
			status_manual = "BELUM DIAKTIFKAN";
		}
		else {
			status_manual = "AKTIF";
		}
		if(!Checker.isStringNullOrEmpty(tgl_end)) {
			status_manual = "SUDAH TIDAK BERLAKU";
		}
		boolean man_expired = ts.apaManualSudahExpiredUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "EVALUASI");
		String no_dok_spmi = ts.getPenomoranDokMutuUmum(id_std, "MANUAL", "EVALUASI");
%>	
	<br>&nbsp<br>
<%
		if(editor && !man_expired) {
	%>	
		<table style="border:none;background:#fff;width:100%">	
			<tr>
				<td style="text-align:left;padding:0 5px;width:10%">
					<form action="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp" method="post">
					<!--  at_menu_dash=< at_menu_dash-->
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
					<input type="hidden" name="id_std" value="<%=id_std%>"/>
					<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash%>"/>
					<input type="hidden" name="fwdto" value="dashboard_std_manual_pengendalian_eval_v1.jsp"/>
					<input type="hidden" name="id_versi" value="<%=id_versi%>"/>
					<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std%>"/>
					<input type="hidden" name="id_master_std" value="<%=id_master_std%>"/>
					<input type="image" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30" alt="Submit Form" />
					</form>
				</td>
				<%
			if(editor) {
	%>
				<td style="text-align:center;padding:0 0;width:10%">
					<jsp:include page="download_man_tetap.jsp" flush="true">
						<jsp:param name="versi_std" value="<%=id_versi %>"/>
						<jsp:param name="id_std" value="<%=id_std %>"/>
						<jsp:param name="tipe" value="perencanaan"/>
						<jsp:param name="no_dok_spmi" value="<%=no_dok_spmi %>"/>
					</jsp:include>
				</td>
	<%		
			}
	%>			
				<td style="text-align:right;padding:0 5px;width:10%">
					<%
				//out.print(status_manual+"<br>");
			if(spmi_editor) {
						%>
					<a href="#" onclick="if(confirm('Copy/Salin manual versi <%=id_versi%> ini untuk seluruh standar?\nCatatan:\nBila standar sudah memiliki manual versi <%=id_versi%> maka akan digantikan dengan salinan ini.'))(function(){
						var x = document.getElementById('wait');
						var y = document.getElementById('main');
						x.style.display = 'block';
						y.style.display = 'none';										
						location.href='set.copyManForAll?status=aktif&tipe_manual=evaluasi&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp'
						})();return false;"
					>
						<img border="0" alt="COPY" src="<%=Constants.getRootWeb() %>/images/copy.png" width="70" height="30">
					</a>
					<%
			}
			if(status_manual.equalsIgnoreCase("AKTIF")) {
					%>
					<div style="font-weight:bold;color:#369;font-size:1.3em">
						STATUS : AKTIF
					</div>
				<%
					/*
					%>
				<a class="img2" href="set.aktivasiMan_v1?status=aktif&tipe_manual=evaluasi&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan memberhentikan penggunaan manual ini ?!? \nNote:\n1. Anda tidak dapat mengaktifkan kembali manual yg sudah diberhentikan.\n2. Pemberhentian manual dilakukan bila manual digantikan dengan revisi yg baru')) return true;return false; ">	
					<img border="0" alt="STOP" src="<%=Constants.getRootWeb() %>/images/stop_red.png" width="30" height="30">
				</a>	
					<%
					*/
				}
				else if(status_manual.equalsIgnoreCase("BELUM DIAKTIFKAN")) {
					%>
				<a class="img2" href="set.aktivasiMan_v1?status=nonaktif&tipe_manual=evaluasi&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengaktifkan penggunaan manual ini ?!? \nSyarat Aktivasi:\n1. Draf manual telah melalui kegiatan perumusan, pemeriksaan, persetujuan dan penetapan.\n2. Pastikan draf manual telah diisi dengan benar, karena manual yg telah diaktifkan tidak dapat diedit')) return true;return false; ">	
					<img border="0" alt="PLAY" src="<%=Constants.getRootWeb() %>/images/play_blue.png" width="70" height="30">
				</a>		
					<%
				}
				else  if(status_manual.equalsIgnoreCase("SUDAH TIDAK BERLAKU")) {
					out.println(status_manual);	
				}
				%>	
				</a>
				</td>
			</tr>
		</table>		
		<%
		}
		if(man_expired) {
		%>
			<section style="text-align:center;font-size:1.5em;color:red">
			TIDAK ADA MANUAL YG AKTIF & DRAFT MANUAL BARU
			</section>
		<%
			if(editor) {
		%>
		<table width="100%;border:none">
		<tr>
		<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
			<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp?mode=insert&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" style="vertical-align:middle;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Manual Baru 
			</a>		
		</td>
		</tr>
		</table>	
		<%		
			}
		}
		else {
%>				
	<table class="table0" id="table0" style="width:100%">
		<thead>	
			<tr>
				<td rowspan="4" width="20%" style="text-align:center;vertical-align:middle;padding:5px 5px;">
					<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/logo.png" alt="USG"  style="width:150px;height:150px;">
				</td>
				<th style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:40%;border:none;font-size:1.5em;font-weight:bold">
				UNIVERSITAS SATYAGAMA
				</th>	
				<td width="40%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold">
					<table width="100%" style="border:none">
						<tr>
							<td width="30%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								NO STANDAR
							</td>
							<td width="1%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								:
							</td>
							<td width="69%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:right">
								<%=no_dok_spmi %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th rowspan="3" style="border-bottom:thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:35%;border:none;font-size:1.5em;font-weight:bold">
				<%
				if(Checker.isStringNullOrEmpty(id_versi) || Checker.isStringNullOrEmpty(ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "penetapan","perencanaan"))) {
					out.print("DRAFT ");
				}
				%>
				MANUAL SPMI
				</th>	
				<td style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold">
					<table width="100%" style="border:none">
						<tr>
							<td width="30%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								TANGGAL
							</td>
							<td width="1%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								:
							</td>
							<td width="69%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:right">
								<%
								if(Checker.isStringNullOrEmpty(tgl_sta)) {
									out.print("jalankan manual (klik tombol 'play')");
								}
								else {
									out.print(Converter.autoConvertDateFormat(tgl_sta, "/"));	
								}
								 
								%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold">
					<table width="100%" style="border:none">
						<tr>
							<td width="30%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								REVISI
							</td>
							<td width="1%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								:
							</td>
							<td width="69%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:right">
								<%=id_versi %>
								<%
								/*
								dipake untuk versi di laman arsip
								*/
								if(Checker.isStringNullOrEmpty(id_versi)) {
									versi_standar="1";
								}
								else {
									versi_standar=new String(id_versi);
								}
								%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold">
					<table width="100%" style="border:none">
						<tr>
							<td width="30%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								HALAMAN
							</td>
							<td width="1%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:left">
								:
							</td>
							<td width="69%" style="vertical-align:middle;padding:3px 3px 3px 10px;font-weight:bold;border:none;text-align:right">
								N/A
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</thead>
	</table>
	<br><br>
	<h1 style="text-align:center">
	<%
	if(Checker.isStringNullOrEmpty(id_versi) || Checker.isStringNullOrEmpty(ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "penetapan","perencanaan"))) {
		out.print("DRAFT ");
	}
				%>
	MANUAL EVALUASI PELAKSANAAN STANDARD</h1>
	<br><br>
	<table class="table0" id="table0" style="width:100%">
		<thead>	
			<tr>
				<th rowspan="2" style="vertical-align:middle;width:15%;font-size:1em;font-weight:bold">
					PROSES
				</th>
				<th colspan="3" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:70%;border:none;font-size:1em;font-weight:bold">
				PENGANGGUNG JAWAB
				</th>	
				<th rowspan="2" style="vertical-align:middle;width:15%;font-size:1em;font-weight:bold">
					TANGGAL
				</th>
			</tr>
			<tr>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:30%;border:thin solid #369;font-size:0.9em;font-weight:bold">
				NAMA
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:25%;border:thin solid #369;font-size:0.9em;font-weight:bold">
				JABATAN
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:15%;border:thin solid #369;font-size:0.9em;font-weight:bold">
				TANDA TANGAN
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				1. PERUMUSAN
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="background:#fff;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				tmp = new String(tkn_jab_rumus);
				while(tmp.contains("`")) {
					tmp = tmp.replace("`", "<br>");
				}
				out.print(tmp);
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				String tgl_tmp=null;
				if(!Checker.isStringNullOrEmpty(id_versi)) {
					tgl_tmp = ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "perumusan","perencanaan");
					out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));	
				}
				%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				2. PEMERIKSAAN
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="background:#fff;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				tmp = new String(tkn_jab_cek);
				while(tmp.contains("`")) {
					tmp = tmp.replace("`", "<br>");
				}
				out.print(tmp);
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				if(!Checker.isStringNullOrEmpty(id_versi)) {
					tgl_tmp = ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "pemeriksaan","perencanaan");
					out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));	
				}
				%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				3. PERSETUJUAN
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="background:#fff;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				tmp = new String(tkn_jab_stuju);
				while(tmp.contains("`")) {
					tmp = tmp.replace("`", "<br>");
				}
				out.print(tmp);
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				if(!Checker.isStringNullOrEmpty(id_versi)) {
					tgl_tmp = ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "persetujuan","perencanaan");
					out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));	
				}
				%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				4. PENETAPAN
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="background:#fff;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				tmp = new String(tkn_jab_tetap);
				while(tmp.contains("`")) {
					tmp = tmp.replace("`", "<br>");
				}
				out.print(tmp);
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				if(!Checker.isStringNullOrEmpty(id_versi)) {
					tgl_tmp = ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "penetapan","perencanaan");
					out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));	
				}
				%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				5. PENGENDALIAN
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="background:#fff;text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				tmp = new String(tkn_jab_kendal);
				while(tmp.contains("`")) {
					tmp = tmp.replace("`", "<br>");
				}
				out.print(tmp);
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%=""%>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta)) {
					out.print("jalankan manual (klik tombol 'play')");
				}
				else {
					out.print(Converter.autoConvertDateFormat(tgl_sta, "/"));	
				}			 
				%>
				</td>
			</tr>
		</thead>
	</table>	
	<br><br>
	<table class="table0" id="table0" style="width:100%">
		<thead>	
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					1. VISI, MISI, TUJUAN, DAN NILAI-NILAI
				</th>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.1. Visi Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 55px">
					<%
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[0]!=null) {
							while(visi_misi_tujuan_nilai[0].contains("\n")) {
								visi_misi_tujuan_nilai[0] = visi_misi_tujuan_nilai[0].replace("\n", "<br>");
							}
							while(visi_misi_tujuan_nilai[0].contains(" ")) {
								visi_misi_tujuan_nilai[0] = visi_misi_tujuan_nilai[0].replace(" ", "&nbsp");
							}
							out.print(visi_misi_tujuan_nilai[0]);	
						}
						
					}
					%>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.2. Misi Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 55px">
					<%
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[1]!=null) {
							while(visi_misi_tujuan_nilai[1].contains("\n")) {
								visi_misi_tujuan_nilai[1] = visi_misi_tujuan_nilai[1].replace("\n", "<br>");
							}
							while(visi_misi_tujuan_nilai[1].contains(" ")) {
								visi_misi_tujuan_nilai[1] = visi_misi_tujuan_nilai[1].replace(" ", "&nbsp");
							}
							out.print(visi_misi_tujuan_nilai[1]);	
						}
						
					}
					%>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.3. Tujuan Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 55px">
					<%
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[2]!=null) {
							while(visi_misi_tujuan_nilai[2].contains("\n")) {
								visi_misi_tujuan_nilai[2] = visi_misi_tujuan_nilai[2].replace("\n", "<br>");
							}
							while(visi_misi_tujuan_nilai[2].contains(" ")) {
								visi_misi_tujuan_nilai[2] = visi_misi_tujuan_nilai[2].replace(" ", "&nbsp");
							}
							out.print(visi_misi_tujuan_nilai[2]);	
						}
						
					}
					%>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.4. Nilai-nilai Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 55px">
					<%
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[3]!=null) {
							while(visi_misi_tujuan_nilai[3].contains("\n")) {
								visi_misi_tujuan_nilai[3] = visi_misi_tujuan_nilai[3].replace("\n", "<br>");
							}
							while(visi_misi_tujuan_nilai[3].contains(" ")) {
								visi_misi_tujuan_nilai[3] = visi_misi_tujuan_nilai[3].replace(" ", "&nbsp");
							}
							out.print(visi_misi_tujuan_nilai[3]);	
						}
						
					}
					%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					2. TUJUAN DAN MAKSUD DOKUMEN MANUAL EVALUASI PELAKSANAAN 
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(tujuan)) {
						while(tujuan.contains("\n")) {
							tujuan = tujuan.replace("\n","<br>");
						}
						while(tujuan.contains(" ")) {
							tujuan = tujuan.replace(" ","&nbsp");
						}
						out.print(tujuan);
					}
					else {
						out.print("N/A");
					}
					%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					3. LUAS LINGKUP MANUAL EVALUASI PELAKSANAAN
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(lingkup)) {
						while(lingkup.contains("\n")) {
							lingkup = lingkup.replace("\n","<br>");
						}
						while(lingkup.contains(" ")) {
							lingkup = lingkup.replace(" ","&nbsp");
						}
						out.print(lingkup);
					}
					else {
						out.print("N/A");
					}
					 %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					4. DEFINISI ISTILAH TERKAIT MANUAL EVALUASI PELAKSANAAN
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(definisi)) {
						while(definisi.contains("\n")) {
							definisi = definisi.replace("\n","<br>");
						}
						while(definisi.contains(" ")) {
							definisi = definisi.replace(" ","&nbsp");
						}
						out.print(definisi);
					}
					else {
						out.print("N/A");
					}
					%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					5. PROSEDUR DAN LANGKAH EVALUASI PELAKSANAAN
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(prosedur)) {
						while(prosedur.contains("\n")) {
							prosedur = prosedur.replace("\n","<br>");
						}
						while(prosedur.contains(" ")) {
							prosedur = prosedur.replace(" ", "&nbsp");
						}
						out.print(prosedur);
					}
					else {
						out.print("N/A");
					}
					 %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					6. KUALIFIKASI PEJABAT/PETUGAS YANG MENJALANKAN PROSEDUR
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(kualifikasi)) {
						while(kualifikasi.contains("\n")) {
							kualifikasi = kualifikasi.replace("\n","<br>");
						}
						while(kualifikasi.contains(" ")) {
							kualifikasi = kualifikasi.replace(" ","&nbsp");
						}
						out.print(kualifikasi);
					}
					else {
						out.print("N/A");
					}
					 %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					7. DOKUMEN TERKAIT
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
				boolean tmp_selalu_kosong=true;
				if(dokumen!=null) {
				%>
					<table style="width:100%;border:none;background:#fff">
				<%	
					
					int no=1;
					st = new StringTokenizer(dokumen,",");
					while(st.hasMoreTokens()) {
						tmp = st.nextToken();
						if(!Checker.isStringNullOrEmpty(tmp)) {
							tmp_selalu_kosong=false;
							%>
						<tr>
							<td style="border:none;width:2%;text-align:left;padding:5px 0 5px 5px">
									<%=no++ %>.
							</td>
							<td style="border:none;width:98%;text-align:left;padding:5px 2px 5px 5px">
									<%=tmp %>
							</td>
						</tr>	
						<%			
						}						
					}
				%>
					</table>
				<%	
				}
				if(tmp_selalu_kosong) {
				%>	
				<div style="padding:0 0 0 0">
				<%	
					out.print("N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]");
				%>
				</div>
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					8. REFERENSI
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(referensi)) {
						while(referensi.contains("\n")) {
							referensi = referensi.replace("\n","<br>");
						}
						while(referensi.contains(" ")) {
							referensi = referensi.replace(" ","&nbsp");
						}
						out.print(referensi);
					}
					else {
						out.print("N/A");
					}
					 %>
				</td>
			</tr>
		</thead>
	</table>
<%
		}
	}
	
}
%>						
</div>
<div id="upload">
	<%
	session.setAttribute("ppepp_asal", "evaluasi");
	session.setAttribute("kdpst_asal", kdpst);
	session.setAttribute("id_tipe_std_asal", id_tipe_std);
	session.setAttribute("id_master_std_asal", id_master_std);
	session.setAttribute("id_std_asal", id_std);
	session.setAttribute("id_versi_asal", versi_standar);
	%>
	<iframe id="glue"  onload="myFunction()" src="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/dashArsipEvaluasi.jsp?kdpst=<%=kdpst %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&id_versi=<%=versi_standar %>" seamless="seamless" width="100%" height="1000"  name="inner_iframe"></iframe>
</div>
<div id="ami">
<br>&nbsp<br>
<%
/*
Vector v_err_man = (Vector)request.getAttribute("v_err_man");
request.removeAttribute("v_err_man");
if(v_err_man!=null && v_err_man.size()>0) {
	ListIterator litmp = v_err_man.listIterator();
%>

<div style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:red">
<%
	while(litmp.hasNext()) {
		String brs2 = (String)litmp.next();
		out.print("* "+brs2+"<br>");
	}
	
%>	
</div>

<%

}
*/
//document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('prep').style.display='none';document.getElementById('ami').style.display='block';document.links[6].className = '';document.links[7].className = '';document.links[8].className = '';document.links[9].className = '';document.links[10].className = 'active';
//sm = new SearchManual();
//v_kendal= sm.searchListManualEvaluasi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
//v_best_manual_kendal = sm.getManualEvaluasiAktifAtoLatestDraft(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
if(!editor) {	
%>
<table class="table1" id="tabel1" style="width:100%">
<%
}
else {
	%>
<table class="table1" id="tabel1" style="width:100%">
	<%	
}

if(v_kendal==null || v_kendal.size()<1) {
	

//boolean ada_penetapan_man= ts.apaSudahAdaPenetapanManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std));
	boolean ada_penetapan_man= true;
	if(!ada_penetapan_man) {
		%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Penetapan Manual Standar <br> Harap kembali dan selesaikan proses I: MANUAL PERENCANAAN STANDAR					
			</td>
		</tr>
<%		
	}
	else {
%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Manual Pelaksanaan Standar <br> Silahkan Input Manual Pelaksanaan Standar yang Sudah Dirumuskan Pada Proses Perencanaan Standar					
			</td>
		</tr>
<%
	}
	
}
else {
	ListIterator li = v_kendal.listIterator();
	int counter = 1;
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		String id_versi = st.nextToken();
		st.nextToken();//id_std ignore = request parameter
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String tkn_jab_rumus = st.nextToken();
		String tkn_jab_cek = st.nextToken();
		String tkn_jab_stuju = st.nextToken();
		String tkn_jab_tetap = st.nextToken();
		String tkn_jab_kendal = st.nextToken();
		String tkn_jab_survey = st.nextToken();
		String tujuan = st.nextToken();
		String lingkup = st.nextToken();
		String definisi = st.nextToken();
		String prosedur = st.nextToken();
		String kualifikasi = st.nextToken();
		String dokumen = st.nextToken();
		String referensi = st.nextToken();
		
		String status_manual = "N/A";
		if(Checker.isStringNullOrEmpty(tgl_sta)) {
			status_manual = "BELUM DIAKTIFKAN";
			ada_manual_yg_bisa_diedit = true; 
		}
		else {
			status_manual = "AKTIF";
		}
		if(!Checker.isStringNullOrEmpty(tgl_end)) {
			status_manual = "SUDAH TIDAK BERLAKU";
		}
		/*
		String target_kondisi = st.nextToken();
		String target_proses_dan_obj = st.nextToken();
		String manual_kegiatan = st.nextToken();
		String interval_pengawasan = st.nextToken();
		String unit_interval_pengawasan = st.nextToken();
		String jabatan_pengawas = st.nextToken();
		String tkn_id_uu = st.nextToken();
		String tkn_id_permen = st.nextToken();
		String jabatan_inputer = st.nextToken();
		String tipe_sarpras = st.nextToken();
		String catat_civitas = st.nextToken();
		*/
		//boolean am_i_surveyor = validUsr.amI_v1(jabatan_inputer, kdpst);
		//boolean am_i_controller = validUsr.amI_v1(jabatan_pengawas, kdpst);
		//System.out.println("--"+jabatan_inputer+"--"+am_i_surveyor);
		//System.out.println("--"+jabatan_pengawas+"--"+am_i_controller);
		//if(status_manual!=null &&  status_manual.equalsIgnoreCase("AKTIF")) {
%>	

<%		
		
		boolean ada_penetapan_man= ts.apaSudahAdaPenetapanManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std));
		boolean man_sdh_aktif= ts.apaManualSudahAktifUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "evaluasi");
		if(!ada_penetapan_man||!man_sdh_aktif) {
			String tmp = "Belum dapat melakukan kegiatan Audit Mutu Internal, karena ";	
		
			if(!ada_penetapan_man) {
				tmp = tmp+"proses Perencanaan belum menghasilkan penetapan manual";
			}
			else if(!man_sdh_aktif) {
				tmp = tmp+"Manual belum di-Aktifkan";
			}
			
%>
			<tr>
			<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Riwayat Kegiatan Audit Mutu Internal 
				<p style="text-align:center;font-size:1.3em;color:red">
					Belum dapat melakukan kegiatan Audit Mutu Internal, karena Standard / Manual Standar belum aktif
				</p> 
			</td>
			</tr>
<%			
		}
		else {
			Vector v_list_std_isi = ssm.getListStandarIsi(spmi_editor,kdpst, Integer.parseInt(id_std));
%>		
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0 0 0;font-size:0.9em;valign:middle">
<% 
			if(v_list_std_isi!=null) {
	%>
		<table class="table2" id="tabel2" style="width:100%">
			<thead>
			<tr>
				<th colspan="2" style="font-size:1.5em">
					LIST PERNYATAAN ISI STANDAR
				</th>
			</tr>
	<%	
				int no=1;
				li = v_list_std_isi.listIterator();
	//tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
				while(li.hasNext()) {
					brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					String id_versi_rs = st.nextToken();
					String id_std_isi_rs = st.nextToken();
					String id_std_rs = st.nextToken();
					String isi_std_rs = st.nextToken();
					String butir_rs = st.nextToken();
					String kdpst_rs = st.nextToken();
					String rasionale_rs = st.nextToken();
					String tgl_sta_rs = st.nextToken();
					String tgl_end_rs = st.nextToken();
					String target1_rs = st.nextToken();
					String target2_rs = st.nextToken();
					String target3_rs = st.nextToken();
					String target4_rs = st.nextToken();
					String target5_rs = st.nextToken();
					String target6_rs = st.nextToken();
					String pihak_rs = st.nextToken();
					String dokumen_rs = st.nextToken();
					String indikator_rs = st.nextToken();
					String periode_start_rs = st.nextToken();
					String unit_used_by_periode_start_rs = st.nextToken();
					String besaran_interval_per_period_rs = st.nextToken();
					String unit_used_byTarget_rs = st.nextToken();
					String pengawas_rs = st.nextToken();
					String param_rs = st.nextToken();
					String aktif_rs = st.nextToken();
					String cakupan_rs = st.nextToken();
					String tgl_mulai_aktif_rs = st.nextToken();
					String tgl_stop_aktif_rs = st.nextToken();
					String tipe_survey_rs = st.nextToken();
					String id_master_rs = st.nextToken();
					String id_tipe_rs = st.nextToken();
		%>
			<tr onclick="window.location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/riwayat_evaluasi_ami_v1.jsp?status_manual=<%=status_manual%>&starting_no=1&unit_used=<%=unit_used %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi_rs %>&id_tipe_std=<%=id_tipe_rs %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std_rs %>&id_std_isi=<%=id_std_isi_rs %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat&at_menu_dash=control'" style="text-align:left;padding:0 2px;font-size:0.9em;padding:0 0 0 5px">
				<td style="border:none;width:2%;text-align:left;padding:5px 0 5px 5px">
					<%=no++ %>.
				</td>
				<td style="border:none;width:98%;text-align:left;padding:5px 2px 5px 5px;font-size:1.3em">
					<%
					if(isi_std_rs!=null) {
						isi_std_rs=Tool.cetakWebFormat(isi_std_rs);
						out.print(isi_std_rs);
					}
								%>
				</td>
			</tr>
	<%		
				}
		%>
			</thead>
		</table>
	<%	
			}
%>		
		</td>
		</tr>							
		
<%	
		}
		
	}
}	

	%>	
		
<%	

%>		

</table>
</div>

<div id="prep">
<br>&nbsp<br>
<%

//document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='none';document.getElementById('upload').style.display='none';document.getElementById('prep').style.display='none';document.getElementById('ami').style.display='block';document.links[6].className = '';document.links[7].className = '';document.links[8].className = '';document.links[9].className = '';document.links[10].className = 'active';
//sm = new SearchManual();
//v_kendal= sm.searchListManualEvaluasi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
//v_best_manual_kendal = sm.getManualEvaluasiAktifAtoLatestDraft(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
if(!editor) {	
%>
	<table class="statetablerow" style="width:100%">
<%
}
else {
	%>
	<table class="statetablerow" style="width:100%">
	<%	
}

if(v_kendal==null || v_kendal.size()<1) {
	

//boolean ada_penetapan_man= ts.apaSudahAdaPenetapanManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std));
	boolean ada_penetapan_man= true;
	if(!ada_penetapan_man) {
		%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Penetapan Manual Standar <br> Harap kembali dan selesaikan proses I: MANUAL PERENCANAAN STANDAR					
			</td>
		</tr>
<%		
	}
	else {
%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Manual Pelaksanaan Standar <br> Silahkan Input Manual Pelaksanaan Standar yang Sudah Dirumuskan Pada Proses Perencanaan Standar					
			</td>
		</tr>
<%
	}
	
}
else {
	ListIterator li = v_kendal.listIterator();
	int counter = 1;
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		String id_versi = st.nextToken();
		st.nextToken();//id_std ignore = request parameter
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String tkn_jab_rumus = st.nextToken();
		String tkn_jab_cek = st.nextToken();
		String tkn_jab_stuju = st.nextToken();
		String tkn_jab_tetap = st.nextToken();
		String tkn_jab_kendal = st.nextToken();
		String tkn_jab_survey = st.nextToken();
		String tujuan = st.nextToken();
		String lingkup = st.nextToken();
		String definisi = st.nextToken();
		String prosedur = st.nextToken();
		String kualifikasi = st.nextToken();
		String dokumen = st.nextToken();
		String referensi = st.nextToken();
		
		String status_manual = "N/A";
		if(Checker.isStringNullOrEmpty(tgl_sta)) {
			status_manual = "BELUM DIAKTIFKAN";
			ada_manual_yg_bisa_diedit = true; 
		}
		else {
			status_manual = "AKTIF";
		}
		if(!Checker.isStringNullOrEmpty(tgl_end)) {
			status_manual = "SUDAH TIDAK BERLAKU";
		}
		/*
		String target_kondisi = st.nextToken();
		String target_proses_dan_obj = st.nextToken();
		String manual_kegiatan = st.nextToken();
		String interval_pengawasan = st.nextToken();
		String unit_interval_pengawasan = st.nextToken();
		String jabatan_pengawas = st.nextToken();
		String tkn_id_uu = st.nextToken();
		String tkn_id_permen = st.nextToken();
		String jabatan_inputer = st.nextToken();
		String tipe_sarpras = st.nextToken();
		String catat_civitas = st.nextToken();
		*/
		//boolean am_i_surveyor = validUsr.amI_v1(jabatan_inputer, kdpst);
		//boolean am_i_controller = validUsr.amI_v1(jabatan_pengawas, kdpst);
		//System.out.println("--"+jabatan_inputer+"--"+am_i_surveyor);
		//System.out.println("--"+jabatan_pengawas+"--"+am_i_controller);
		//if(status_manual!=null &&  status_manual.equalsIgnoreCase("AKTIF")) {
%>	

<%		
		
		boolean ada_penetapan_man= ts.apaSudahAdaPenetapanManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std));
		boolean man_sdh_aktif= ts.apaManualSudahAktifUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "evaluasi");
		if(!ada_penetapan_man||!man_sdh_aktif) {
			String tmp = "Belum dapat melakukan kegiatan Audit Mutu Internal, karena ";	
		
			if(!ada_penetapan_man) {
				tmp = tmp+"proses Perencanaan belum menghasilkan penetapan manual";
			}
			else if(!man_sdh_aktif) {
				tmp = tmp+"Manual belum di-Aktifkan";
			}
			
%>
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Riwayat Kegiatan Audit Mutu Internal 
				<p style="text-align:center;font-size:1.3em;color:red">
					Belum dapat melakukan kegiatan Audit Mutu Internal, karena Standard / Manual Standar belum aktif
				</p> 
			</td>
		</tr>
<%			
		}
		else {
			Vector v_list_std_isi = ssm.getListStandarIsi(spmi_editor,kdpst, Integer.parseInt(id_std));
%>		
		<tr>
			<td colspan="3" style="background:<%=Constant.darkColorBlu() %>;text-align:center;padding:0 0 0 0;background:#369;vertical-align:middle;font-size:1.5em;text-align:center;font-weight:bold;color:#fff">
<% 
			if(v_list_std_isi!=null) {
	%>
				PILIH PERNYATAAN STANDAR YANG AKAN DISIAPKAN<br>	
	<%	
				int no=1;
				li = v_list_std_isi.listIterator();
	//tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
				while(li.hasNext()) {
					brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					String id_versi_rs = st.nextToken();
					String id_std_isi_rs = st.nextToken();
					String id_std_rs = st.nextToken();
					String isi_std_rs = st.nextToken();
					String butir_rs = st.nextToken();
					String kdpst_rs = st.nextToken();
					String rasionale_rs = st.nextToken();
					String tgl_sta_rs = st.nextToken();
					String tgl_end_rs = st.nextToken();
					String target1_rs = st.nextToken();
					String target2_rs = st.nextToken();
					String target3_rs = st.nextToken();
					String target4_rs = st.nextToken();
					String target5_rs = st.nextToken();
					String target6_rs = st.nextToken();
					String pihak_rs = st.nextToken();
					String dokumen_rs = st.nextToken();
					String indikator_rs = st.nextToken();
					String periode_start_rs = st.nextToken();
					String unit_used_by_periode_start_rs = st.nextToken();
					String besaran_interval_per_period_rs = st.nextToken();
					String unit_used_byTarget_rs = st.nextToken();
					String pengawas_rs = st.nextToken();
					String param_rs = st.nextToken();
					String aktif_rs = st.nextToken();
					String cakupan_rs = st.nextToken();
					String tgl_mulai_aktif_rs = st.nextToken();
					String tgl_stop_aktif_rs = st.nextToken();
					String tipe_survey_rs = st.nextToken();
					String id_master_rs = st.nextToken();
					String id_tipe_rs = st.nextToken();
					Vector v_QA = sqa.getQandA(Integer.parseInt(id_std_isi_rs));
					
		%>
			<tbody>
				<table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="width:100%">
					<thead>
					<tr>
						<th class="blue" style="background:<%=Constant.lightColorBlu() %>;border:1px solid #369;border-right:none;text-align:left;color:#000;font-size:0.8em">
					<%=no++ %>.
						</th>
						<th class="blue" colspan="3" style="background:<%=Constant.lightColorBlu() %>;border:1px solid #369;border-left:none;text-align:left;color:#000;font-size:0.8em">
					<%
					if(isi_std_rs!=null) {
						isi_std_rs=Tool.cetakWebFormat(isi_std_rs);
						out.print(isi_std_rs);
						if(Checker.isStringNullOrEmpty(kdpst_rs)) {
							out.print(" <br>[SEMUA PRODI]");
						}
						else {
							out.print(" <br>["+Converter.getNamaKdpstDanJenjang(kdpst_rs)+"]");
						}
						
					}
								%>
						</th>
					</tr>
					</thead>
					<tbody>
		<%
					if(v_QA!=null && v_QA.size()>0) {
						/*
						li.add(idque+"~"+question+"~"+tkn_doc+"~"+note+"~"+answer+"~"+bobot);
						*/
					
						ListIterator lit = v_QA.listIterator();
						//setiap paket terdiri dari 5 brs - 1 pertanyaan dan 4 pilihan jawaban
		%>			
						
						<tr>
							<td style="width:2%;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:0.9em;padding:0 0 0 10px;border:1px solid <%=Constant.lightColorGrey() %>">
								No.
							</td>
							<td colspan="2" style="width:83%;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								Pertanyaan & Penilaian Jawaban (Q&A)
							</td>
					<%
						if(true) {
						%>		
							<td style="width:15%;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
							Bobot Nilai
							</td>
				<%	
						}
					/*
						else {
					%>		
							<td rowspan="2" style="width:15%;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								Bobot Nilai
							</td>
					<%
						}
					*/
					%>
						</tr>
					<%
						int nomor = 1; 
						
						if(lit.hasNext()) {
							//idque+"~"+question+"~"+tkn_doc+"~"+note+"~"+answer+"~"+bobot
							String prev_id_question = null;
							String pertanyaan = null;
							String tkn_doc_terkait=null;
							String note=null;
							String jawaban = null;
							String nilai = null;
							brs = (String)lit.next();
							//System.out.println("brs="+brs);
							if(!Checker.isStringNullOrEmpty(brs)) {
								st = new StringTokenizer(brs,"~");
								prev_id_question = st.nextToken();
								pertanyaan = st.nextToken();
								tkn_doc_terkait = st.nextToken();
								note = st.nextToken();
								jawaban = st.nextToken();
								nilai = st.nextToken();
					%>
						<tr>
							<td style="width:2%;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-right:none">
							<%=nomor++ %>.
							</td>
					<%
								if(spmi_editor) {
						%>		
							<td colspan="2" style="width:83%;border-right:0px;color:#fff;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-left:none;border-right:none">
							<%=pertanyaan %>
							</td>
				<%	
								}
								else {
					%>		
							<td colspan="3" style="width:98%;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
							<%=pertanyaan %>
							</td>
					<%
								}
								if(spmi_editor) {
					%>
							<td style="width:15%;border-left:0px;color:#fff;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-left:none">
								<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/formQandA/form_qa.jsp?id_question=<%=prev_id_question %>&id_std_isi=<%=id_std_isi_rs %>&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp&darimana=prep">
									<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30">
								</a>
							</td>
					<%	
								}
					%>			
						</tr>
						
					<%
								String alphabet="a";
					%>
						<tr>
							<td colspan="2" style="width:2%;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-right:none">
								<%=alphabet %>.
							</td>
							<td style="width:83%;border-left:0px;color:#fff;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-left:none;border-right:none">
								<%=jawaban %>
							</td>
							<td style="width:15%;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>;border-left:none">
								<%=nilai %>
							</td>
						</tr>
					<%			
								while(lit.hasNext()) {
								//idque+"~"+question+"~"+tkn_doc+"~"+note+"~"+answer+"~"+bobot
									String id_question = null;
									pertanyaan = null;
									tkn_doc_terkait=null;
									note=null;
									jawaban = null;
									nilai = null;
									brs = (String)lit.next();
									//System.out.println("brs="+brs);
									if(!Checker.isStringNullOrEmpty(brs)) {
										st = new StringTokenizer(brs,"~");
										id_question = st.nextToken();
										pertanyaan = st.nextToken();
										tkn_doc_terkait = st.nextToken();
										note = st.nextToken();
										jawaban = st.nextToken();
										nilai = st.nextToken();
										if(prev_id_question.equalsIgnoreCase(id_question)) {
											//masih pada 1 pertanyaan yg sama
											if(alphabet.equalsIgnoreCase("a")) {
												alphabet="b";
											}
											else if(alphabet.equalsIgnoreCase("b")) {
												alphabet="c";
											}
											else if(alphabet.equalsIgnoreCase("c")) {
												alphabet="d";
											}
											else if(alphabet.equalsIgnoreCase("d")) {
												alphabet="e";
											}
						%>
						<tr>
							<td colspan="2" style="width:2%;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=alphabet %>.
							</td>
							<td style="width:83%;border-left:0px;color:#fff;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=jawaban %>
							</td>
							<td style="width:15%;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=nilai %>
							</td>
						</tr>
										<%				
										}
										else {
											//pertanyaan berikutnya
						%>
						<tr>
							<td rowspan="1" style="width:2%;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=nomor++ %>.
							</td>
						<%
											if(spmi_editor) {
						%>		
							<td colspan="2" style="width:83%;border-right:0px;color:#fff;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=pertanyaan %>
							</td>
						<%	
											}
											else {
						%>		
							<td colspan="3" style="width:98%;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=pertanyaan %>
							</td>
						<%
											}
											if(spmi_editor) {
						%>
							<td style="width:15%;border-left:0px;color:#fff;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/formQandA/form_qa.jsp?id_question=<%=id_question %>&id_std_isi=<%=id_std_isi_rs %>&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp&darimana=prep">
									<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30">
								</a>
							</td>
						<%	
											}
						%>			
						</tr>
						<%
						
											alphabet="a";
						%>
						<tr>
							<td colspan="2" style="width:2%;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=alphabet %>.
							</td>
							<td style="width:83%;border-left:0px;color:#fff;padding:0 0 0 0;background:#fff;color:#369;text-align:left;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=jawaban %>
							</td>
							<td style="width:15%;background:#fff;color:#369;text-align:center;font-size:0.9em;border:1px solid <%=Constant.lightColorGrey() %>">
								<%=nilai %>
							</td>
						</tr>
										<%	
											prev_id_question = new String(id_question);
										}
									}	
								}	
							}
							if(!lit.hasNext()) {
								%>
							<tr>
								<td colspan="4" style="background:#fff;color:#369;text-align:center;border:1px solid <%=Constant.lightColorGrey() %>">
									<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/formQandA/form_qa.jsp?id_std_isi=<%=id_std_isi_rs %>&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp&darimana=prep" style="vertical-align:middle;valign:middle">
										<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1em">Tambah Q&A 
									</a>
								</td>
							</tr>
							<%				
							}
						}
					}
					else {
						//input form
					%>
						<tr>
							<td colspan="4" style="background:#fff;color:#369;text-align:center;border:1px solid <%=Constant.lightColorGrey() %>">
								<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/formQandA/form_qa.jsp?id_std_isi=<%=id_std_isi_rs %>&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp&darimana=prep" style="vertical-align:middle;valign:middle">
									<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Q&A  
								</a>
							</td>
						</tr>
					<%	
					}
					%>	
					</tbody>
				</table>
	<%			
				}
		%>
				</tbody>	
				
	<%	
			}
%>		
			</td>
		</tr>							
		
<%	
		}
		
	}
}	

	%>	
		
<%	

%>		

	</table>
</div>	
		<!-- Column 1 start -->
	</div>
</div>	
</div>	
</body>
</html>