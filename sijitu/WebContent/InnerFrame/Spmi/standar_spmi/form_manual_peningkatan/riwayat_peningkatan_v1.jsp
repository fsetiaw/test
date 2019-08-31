<!DOCTYPE html>
<head>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.ToolSpmi"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISTIM PENJAMINAN MUTU by www.cg2net.id</title>
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
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
ToolSpmi ts = new ToolSpmi();


//System.out.println("masuk sasaran");
String thsms_now = Checker.getThsmsNow();
Vector v_scope_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj_v1("hasSpmiMenu", true, thsms_now);
//System.out.println("v_scope_id="+v_scope_id.size());
Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
v_scope_kdpst = Converter.convertVscopeKdpstToDistinctInfoKdpst(v_scope_kdpst, "KDPSTMSPST");
boolean editor = (Boolean) session.getAttribute("spmi_editor");
boolean team_spmi = (Boolean) session.getAttribute("team_spmi");
String status_manual = request.getParameter("status_manual");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_plan = request.getParameter("id_plan");
String id_versi = request.getParameter("id_versi");
String id_tipe_std = request.getParameter("id_tipe_std");
String id_std = request.getParameter("id_std");
String id_master_std = request.getParameter("id_master_std");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String target_unit_used = request.getParameter("unit_used");
String src_manual_limit = (String) session.getAttribute("src_manual_limit");
String src_offset = request.getParameter("offset");
String starting_no = request.getParameter("starting_no");
if(Checker.isStringNullOrEmpty(starting_no)) {
	starting_no="1";
}
String kegiatan = "";
String ppepp = "peningkatan";
//System.out.println("id_versi="+id_versi);
//System.out.println("id_std="+id_std);

String tgl_sta_man = ""+ts.getTglStartManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "peningkatan");
String tgl_tetap_man = ""+ts.getPenetapanTanggalManualUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "penetapan","perencanaan");
boolean man_expired = ts.apaManualSudahExpiredUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std), "PENINGKATAN");
//System.out.println("tgl_sta_man="+tgl_sta_man);
//System.out.println("tgl_tetap_man="+tgl_tetap_man);
int max_yg_tampil = Integer.parseInt(src_manual_limit)-1;
//int limit = 0; 
int offset = 0;
//if(!Checker.isStringNullOrEmpty(src_limit)) {
//	limit = Integer.parseInt(src_limit);
//}
if(!Checker.isStringNullOrEmpty(src_offset)) {
	offset = Integer.parseInt(src_offset);
}
//System.out.println("at_menu_dash="+at_menu_dash);
//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
//System.out.println("at_menu_kendal="+at_menu_kendal);
//System.out.println("id_versi="+id_versi);
//System.out.println("id_std_isi="+id_std_isi);
//System.out.println("id_std="+id_std);
//System.out.println("norut_man="+norut_man);
//System.out.println("scope_std="+scope_std);
//System.out.println("std_kdpst="+std_kdpst);
//String id_hist_err = request.getParameter("id_hist_err");
String dt = AskSystem.getLocalDate();
String tm = ""+AskSystem.getWaktuSekarang();
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String filter_by_kdpst = new String(kdpst);
boolean man_sdh_aktif = false;
if(status_manual!=null && status_manual.equalsIgnoreCase("AKTIF")) {
	man_sdh_aktif = true;
}
//boolean sdh_aktif = false;
//SearchStandarMutu ssm = new SearchStandarMutu();
//sdh_aktif = ssm.isStandardUmumActivated(Integer.parseInt(id_versi),Integer.parseInt(id_std));
boolean am_i_surveyor = true;
boolean am_i_controller = true;
boolean validasi_mode = false;
Vector v_err = (Vector)request.getAttribute("v_err");
request.removeAttribute("v_err");
Vector v_ok = (Vector)request.getAttribute("v_ok");
request.removeAttribute("v_ok");
/*
IAMSURVEYOR DAN PENGENDALI DIATAS , DIGUNAKAN JIKA BLUM ADA RIWAYAT
SOMEWHERE DI BAWAH, DITIBAN VALUENYA BERDASARKAN RIWAYAT
boolean iAmSurveyor = validUsr.amIcontain(jab_surveyor_man);
boolean iAmPengendali = validUsr.amIcontain(jab_pengendali_man);
*/


//variable ini di dapat dari /form_manula_pengendalian/form_survey_pengendalian.jsp
//SearchStandarMutu ssm = new SearchStandarMutu();

//String param = st.nextToken();
//String indikator = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	
//TIPE_SARPRAS & CATAT_CIVITAS didiapat dari vector, beda dengan form passing param dari menunya

SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
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
//String[]job_jawab=request.getParameterValues("job_jawab");
//String[]dok_mutu=request.getParameterValues("dok_mutu");

Vector v_list_doc = Getter.getListDokumenMutu();
int no_yg_tampil=0;
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
/*.table2 tr:hover td { background:#82B0C3 }*/
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
	table.CityTable thead th.blue{padding: 0px; background: #369;cursor:pointer; color:black;}
	table.CityTable thead:hover th.blue { background:#82B0C3;;text-align:center; }
	
	table.StateTable td.nopad{background: #369;padding:0;;text-align:center;}
	table.StateTable th.yellow{background: #FE9A2E;padding:0;;text-align:center;}
	table.StateTable tr:hover td.nopad { background:#82B0C3 }
	table.StateTable thead tr:hover td { background:#82B0C3;;text-align:center; }
	table.StateTable tr:hover th.yellow { background:#FFB766;;text-align:center; }
	
</style>
</head>
<body>
<jsp:include page="menu_man_peningkatan_v1.jsp" />
<div class="colmask fullpage">
	<div class="col1">
		<br>
		<!-- Column 1 start -->
		<div style="text-align:center;padding:0 0 0 3px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
		</div>	
		<%

Vector v_hist_plan=null;
		/*
ListIterator lit = v_scope_id.listIterator();
while(lit.hasNext()) {
	String brs = (String)lit.next();
	//System.out.println(brs);
}
*/
SrcHistPeningkatan shk = new SrcHistPeningkatan();
//System.out.println("offset="+offset);

v_hist_plan=shk.getListRiwayatPeningkatanUmum(Integer.parseInt(id_versi), Integer.parseInt(id_std),offset,Integer.parseInt(src_manual_limit)-1);


//if(v_hist_plan!=null) {
	//System.out.println("v_hist_plan = "+v_hist_plan.size());
//}
//else {
	//System.out.println("v_hist_plan is null");
//}

//Vector v_kendal= sm.searchManualStandardPengendalian_v1(Integer.parseInt(id_kendali));
/*
if(!Checker.isStringNullOrEmpty(filter_by_kdpst) && v_hist_plan!=null) {
	//FILTER BERDASARKAN KDPST
	ListIterator li = v_hist_plan.listIterator();
	int counter = 1;
	ListIterator lir = null;
	while(li.hasNext()) {
		Vector vrow = (Vector)li.next();
		lir = vrow.listIterator();
		if(lir.hasNext()) {
			
			String id_hist = (String)lir.next();
			String tgl_sidak = (String)lir.next();
			String time_sidak = (String)lir.next();
			String timestamp = (String)lir.next();
			String periode_ke = (String)lir.next();
			String nilai_ril_capaian = (String)lir.next();
			String sikon = (String)lir.next();
			String analisa = (String)lir.next();
			String rekomendasi = (String)lir.next();
			String npm_surveyer = (String)lir.next();
			String target_capaian = (String)lir.next();
			String unit_used = (String)lir.next();
			String npm_civitas_underwatch = (String)lir.next();
			String tipe_sarpras_underwatch = (String)lir.next();
			String sarpras_underwatch = (String)lir.next();
			String kdpst_underwatch = (String)lir.next();
			//iAmSurveyor = validUsr.amI_v1(jabatan_inputer, kdpst_underwatch);
			//iAmPengendali = validUsr.amI_v1(jabatan_pengendali, kdpst_underwatch);
			if(!kdpst_underwatch.equalsIgnoreCase(filter_by_kdpst)) {
				li.remove();
			}
		}
	}
}	
*/
if(v_hist_plan==null || v_hist_plan.size()<1) {
		//System.out.println("v_hist_plan is null");

%>	
		<br><br>
		<table style="width:100%">
		<tr>
			<td colspan="7" style="text-align:center;padding:0 0;font-size:2.0em;font-weight:bold">
				Belum Ada Riwayat Kegiatan Peningkatan						
			</td>
		</tr>
		<%
	//pertama kali, hanya boleh kegiatan manual
	if((team_spmi||editor)&&!man_expired) {	
		
		%>
		<tr>
			<td style="text-align:center;font-size:1.3em;font-weight:bold" align="center">
		<%
		if(man_sdh_aktif) {
		%>	
				<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_peningkatan/form_survey_peningkatan_v1.jsp">
		<%
		}
		%>			
					<input type="hidden" name="status_manual" value="<%=status_manual %>" />
					<input type="hidden" name="scope_std" value="<%=scope_std %>" />
					<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
					<input type="hidden" name="id_versi" value="<%=id_versi %>" />
					<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
					<input type="hidden" name="id_std" value="<%=id_std %>" />
					<input type="hidden" name="id_master_std" value="<%=id_master_std %>" />
					<input type="hidden" name="at_menu_kendal" value="riwayat" />
					<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
					<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
					<input type="hidden" name="am_i_controller" value="<%=am_i_controller %>" />
					<input type="hidden" name="am_i_surveyor" value="<%=am_i_surveyor %>" />
					
		<%
		if(man_sdh_aktif) {
		%>	
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />
					TAMBAH KEGIATAN PENINGKATAN STANDAR
		<%
		}
		else {
			%>
					UNTUK MENAMBAH KEGIATAN HARAP AKTIFKAN MANUAL TERLEBIH DAHULU
		<%	
		}
		%>			
				</form>
			</td>
		</tr>
		<%
	}
	else {
		if(!man_expired) {	
		%>
		<tr>
			<td style="text-align:center;font-size:1.3em;font-weight:bold" align="center">
				<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />
				TAMBAH KEGIATAN PERANCANGAN MANUAL<br>[Anda tidak memiliki akses untuk menambah kegiatan]
			</td>
		</tr>			
		<%
		}
	}
		%>
		</table>	
<%
}
else {
	
	//System.out.println("v_hist_plan size="+v_hist_plan.size());
%>

		<br>
		<%
	//if(man_sdh_aktif && ((am_i_surveyor||team_spmi)||editor)) {	
	if(!Checker.isStringNullOrEmpty(tgl_tetap_man) && Checker.isStringNullOrEmpty(tgl_sta_man)&&!man_expired) {
		//sudah ada penetapan tapi manual blum di play
		//jadi tidak ada kegiatan yang bisa ditambah sampe di play
		%>		
		<section style="align:center;text-align:center;font-weight:bold;color:red">	
			<p style="align:center;text-align:center;font-weight:bold;color:red">	
			<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" />TAMBAH KEGIATAN PENINGKATAN STANDAR
			<br>
			[Manual sudah ditetapkan namun belum dijalankan/diaktifkan (play), harap aktifkan manual untuk dapat menambah kegiatan PERUMUSAN STANDAR]
			</p>	
		</section>
			
		<%
		
	}
	else {
		if(team_spmi||editor) {	
		%>		
		<section style="align:center;text-align:center">	
			<p>
				<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_peningkatan/form_survey_peningkatan_v1.jsp" style="font-weight:bold;color:#369">
					<input type="hidden" name="status_manual" value="<%=status_manual %>" />
					<input type="hidden" name="scope_std" value="<%=scope_std %>" />
					<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
					<input type="hidden" name="id_versi" value="<%=id_versi %>" />
					<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
					<input type="hidden" name="id_std" value="<%=id_std %>" />
					<input type="hidden" name="id_master_std" value="<%=id_master_std %>" />
					<input type="hidden" name="at_menu_kendal" value="riwayat" />
					<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
					<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
					<input type="hidden" name="am_i_controller" value="<%=am_i_controller %>" />
					<input type="hidden" name="am_i_surveyor" value="<%=am_i_surveyor %>" />
					
					<%
			if(!man_sdh_aktif) {
		//tidak ada manual yg aktif
			}
			else {
			%>
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />
					TAMBAH KEGIATAN PENINGKATAN STANDAR
		<%	
			}
		%>
				</form>
			</p>	
		</section>
			
<%	
	}
}
	
%>
	
<table align="center" style="width:100%">
	<tr>
		<td colspan="8" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
			RIWAYAT PENINGKATAN MANUAL & STANDAR
		</td>
	</tr>

</table>			
<section style="text-align:center">	
		<jsp:include page="navigasi_v1.jsp" flush="true" />
		</section>
<%	
	
	ListIterator li = v_hist_plan.listIterator();
	int size =  v_hist_plan.size();
	if(li.hasNext()) {

		int counter = Integer.parseInt(starting_no);
		
		//ListIterator lir = null;
		
		while(li.hasNext()&& (no_yg_tampil <= max_yg_tampil)) {
			
		
			no_yg_tampil++;
			//System.out.println("max_yg_tampil="+max_yg_tampil);
			//System.out.println("no_yg_tampil="+no_yg_tampil);
			String brs = (String)li.next();
			//String tmp = id_plan+"~"+versi_id+"~"+std_isi_id+"~"+norut_man+"~"+tgl_sta+"~"+waktu_sta+"~"+tgl_end+"~"+waktu_end+"~"+stamp_sta+"~"+stamp_end+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+tujuan_kegiatan+"~"+isi_kegiatan+"~"+tkn_job_tanggung+"~"+tkn_job_target+"~"+tkn_dok_kegiatan+"~"+hasil_kegiatan+"~"+kegiatan_started+"~"+kegiatan_ended+"~"+note_kegiatan+"~"+tgl_rumus_set+"~"+tgl_cek_set+"~"+tgl_stuju_set+"~"+tgl_tetap_set+"~"+tgl_kendali_set+"~"+tgl_next_kegiatan+"~"+waktu_next_kegiatan+"~"+tgl_sta_std+"~"+tgl_end_std+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kuali+"~"+doc+"~"+ref;
			st = new StringTokenizer(brs,"~");
			String id_plan_hist=st.nextToken();
			String versi_id_hist=st.nextToken();
			String id_std_hist=st.nextToken();
			String tgl_sta_hist=st.nextToken();
			if(!Checker.isStringNullOrEmpty(tgl_sta_hist)) {
				tgl_sta_hist=Converter.autoConvertDateFormat(tgl_sta_hist, "/");
			}
			String waktu_sta_hist=st.nextToken();
			if(!Checker.isStringNullOrEmpty(waktu_sta_hist) && waktu_sta_hist.length()==8) {
				waktu_sta_hist=waktu_sta_hist.substring(0,5);
			}
			String tgl_end_hist=st.nextToken();
			String waktu_end_hist=st.nextToken();
			String stamp_sta_hist=st.nextToken();
			String stamp_end_hist=st.nextToken();
			String nama_kegiatan_hist=st.nextToken();
			String jenis_kegiatan_hist=st.nextToken();
			String tujuan_kegiatan_hist=st.nextToken();
			String isi_kegiatan_hist=st.nextToken();
			String tkn_job_tanggung_hist=st.nextToken();
			String[]job_jawab=null;
			if(!Checker.isStringNullOrEmpty(tkn_job_tanggung_hist)) {
				StringTokenizer stt = new StringTokenizer(tkn_job_tanggung_hist,"`");
				job_jawab = new String[stt.countTokens()];
				for(int j=0;stt.hasMoreTokens();j++) {
					job_jawab[j]=stt.nextToken();
				}
			}
			String[]job_target=null;
			String tkn_job_target_hist=st.nextToken();
			//System.out.println("tkn_job_target_hist="+tkn_job_target_hist);
			if(!Checker.isStringNullOrEmpty(tkn_job_target_hist)) {
				StringTokenizer stt = new StringTokenizer(tkn_job_target_hist,"`");
				job_target = new String[stt.countTokens()];
				for(int j=0;stt.hasMoreTokens();j++) {
					String tmp_job = stt.nextToken();
					//tmp_job = tmp_job.replace("[tipe: group]", "");
					//tmp_job = tmp_job.trim();
					//System.out.println("tmp_job="+tmp_job);
					job_target[j]=tmp_job;
				}
			}
			String[]dok_mutu=null;
			String tkn_dok_kegiatan_hist=st.nextToken();
			//System.out.println("tkn_dok_kegiatan_hist="+tkn_dok_kegiatan_hist);
			if(!Checker.isStringNullOrEmpty(tkn_dok_kegiatan_hist)) {
				StringTokenizer stt = new StringTokenizer(tkn_dok_kegiatan_hist,"`");
				dok_mutu = new String[stt.countTokens()];
				for(int j=0;stt.hasMoreTokens();j++) {
					dok_mutu[j]=stt.nextToken();
				}
			}
			String hasil_kegiatan_hist=st.nextToken();
			String kegiatan_started_hist=st.nextToken();
			String kegiatan_ended_hist=st.nextToken();
			String note_kegiatan_hist=st.nextToken();
			String tgl_rumus_set_hist=st.nextToken();
			String tgl_cek_set_hist=st.nextToken();
			String tgl_stuju_set_hist=st.nextToken();
			String tgl_tetap_set_hist=st.nextToken();
			String tgl_kendali_set_hist=st.nextToken();
			String tgl_next_kegiatan_hist=st.nextToken();
			String waktu_next_kegiatan_hist=st.nextToken();
			String tgl_sta_std_hist=st.nextToken();
			String tgl_end_std_hist=st.nextToken();      
			String tkn_jab_rumus_hist=st.nextToken();
			String tkn_jab_cek_hist=st.nextToken();
			String tkn_jab_stuju_hist=st.nextToken();
			String tkn_jab_tetap_hist=st.nextToken();
			String tkn_jab_kendali_hist=st.nextToken();
			String tujuan_hist=st.nextToken();
			String lingkup_hist=st.nextToken();
			String definisi_hist=st.nextToken();
			String prosedur_hist=st.nextToken();
			String kuali_hist=st.nextToken();
			String doc_hist=st.nextToken();
			String ref_hist=st.nextToken();
			//System.out.println("kegiatan_started_hist="+kegiatan_started_hist);
			if(kegiatan_started_hist==null || (kegiatan_started_hist!=null && kegiatan_started_hist.equalsIgnoreCase("0"))) {
				//kegitaan belum dimulai
			
%>
<form action="go.updRiwayatPeningkatan_v1" method="post">
	<input type="hidden" name="status_manual" value="<%=status_manual %>" />
	<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
	<input type="hidden" name="id_versi" value="<%=id_versi %>" />
	<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
	<input type="hidden" name="id_master_std" value="<%=id_master_std%>" />
	<input type="hidden" name="id_std" value="<%=id_std %>" />
	<input type="hidden" name="at_menu_kendal" value="<%=at_menu_kendal %>" />
	<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
	<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
	<input type="hidden" name="scope_std" value="<%=scope_std %>" />
	<input type="hidden" name="id_plan" value="<%=id_plan_hist %>" />
	
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:1px solid #369;width:90%;background:white;">
		<thead>
			<%
				if(v_err!=null && v_err.size()>0  && id_plan!=null && id_plan.equalsIgnoreCase(id_plan_hist)) {
					ListIterator litmp = v_err.listIterator();
				%>
			<tr class="statetablerow">
				<td colspan="3" style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:red">
				<%
					while(litmp.hasNext()) {
						String brs2 = (String)litmp.next();
						out.print("* "+brs2+"<br>");
					}
					out.print("[ Harap diisi kembali data yang akan dirubah ]<br>");
				%>	
				</td>
			</tr>
				<%	
				}
				if(v_ok!=null && v_ok.size()>0 && id_plan!=null && id_plan.equalsIgnoreCase(id_plan_hist)) {
					ListIterator litmp = v_ok.listIterator();
			%>
		<tr class="statetablerow">
			<td colspan="3" style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:#369">
			<%
					while(litmp.hasNext()) {
						String brs2 = (String)litmp.next();
						out.print("* "+brs2+"<br>");
					}
			%>	
			</td>
		</tr>
			<%	
				}
			%>
			<tr class="statetablerow">
				<td class="nopad" colspan="3" style="vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					<%=counter++%>.  INFORMASI KEGIATAN / RENCANA KEGIATAN <%=ToolSpmi.cetakJenisKegiatanManualDanStandar(jenis_kegiatan_hist)%>
				</td>
			</tr>
			<tr>
				<td class="nopad" colspan="3" style="vertical-align:middle;font-size:1.2em;text-align:center;font-weight:bold;color:#fff">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_hist)) {
			%>
					TGL : <input type="text" name="tgl_sta" value="<%=dt %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%	
				}
				else {
			%>
					TGL : <input type="text" name="tgl_sta" value="<%=tgl_sta_hist%>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%
				}
			
				if(Checker.isStringNullOrEmpty(waktu_sta_hist)) {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu_sta" placeholder="hh:mm" value="<%=tm %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%
				}
				else {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu_sta" placeholder="hh:mm" value="<%=waktu_sta_hist %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%		
				}
			%>					
							 
				</td>
			</tr>
		</thead>
		<tbody>	
			
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:none;padding:0 0 0 10px">
					A.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:none;padding:0 0 0 10px;text-align:left">
					JENIS KEGIATAN
				</td>
				<td style="width:70%;background:#D7D7D7;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<select name="jenis_kegiatan" style="height:30px;width:100%;border:none;text-align-last:center;border-radius: 0px;">
				<%
				/*
				%>		
						<option value="">--pilih jenis kegiatan--</option>
						<%
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus")) {
							%>
							<option value="rumus" selected="selected">Perumusan Manual</option>
							<%
				}
				else {
						%>
						<option value="rumus">Perumusan Manual</option>
						<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek")) {
							%>
							<option value="cek" selected="selected">Pemeriksaan Manual</option>
							<%
				}
				else {
						%>
						<option value="cek">Pemeriksaan Manual</option>
						<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju")) {
							%>
							<option value="stuju" selected="selected">Persetujuan Manual</option>
							<%	
				}
				else {
						%>
						<option value="stuju">Persetujuan Manual</option>
						<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap")) {
							%>
							<option value="tetap" selected="selected">Penetapan Manual</option>
							<%	
				}
				else {
						%>
						<option value="tetap">Penetapan Manual</option>
						<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali")) {
							%>
							<option value="kendali" selected="selected">Pengendalian Manual</option>
							<%	
				}
				else {
						%>
						<option value="kendali">Pengendalian Manual</option>
						<%
				}
				//tahapan standar
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus_std")) {
					%>
						<option value="rumus_std" selected="selected">Perumusan Standar</option>
					<%
				}
				else {
				%>
						<option value="rumus_std">Perumusan Standar</option>
				<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek_std")) {
					%>
						<option value="cek_std" selected="selected">Pemeriksaan Standar</option>
					<%
				}
				else {
				%>
						<option value="cek_std">Pemeriksaan Standar</option>
				<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju_std")) {
					%>
						<option value="stuju_std" selected="selected">Persetujuan Standar</option>
					<%	
				}
				else {
				%>
						<option value="stuju_std">Persetujuan Standar</option>
				<%
				}
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap_std")) {
					%>
						<option value="tetap_std" selected="selected">Penetapan Standar</option>
					<%	
				}
				else {
				%>
						<option value="tetap_std">Penetapan Standar</option>
				<%
				}
				*/
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali_std")) {
					%>
						<option value="kendali_std" selected="selected">Peningkatan Standar</option>
					<%	
				}
				else {
				%>
						<option value="kendali_std">Peningkatan Standar</option>
				<%
				}
						%>
					</select>	
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px">
					B.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;text-align:left">
					NAMA KEGIATAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
				<%
				if(!Checker.isStringNullOrEmpty(nama_kegiatan_hist)) {
					%>
					<input type="text" name="nmm_kegiatan" minlength="5" style="text-align:center;width:100%;height:30px;padding:5px 0px" placeholder="isikan nama kegiatan" value="<%=nama_kegiatan_hist%>"/>
				<%	
				}
				else {
				%>
					<input type="text" name="nmm_kegiatan" minlength="5" style="text-align:center;width:100%;height:30px;padding:5px 0px" placeholder="isikan nama kegiatan" />
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					C.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TUJUAN KEGIATAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
				<%
				if(!Checker.isStringNullOrEmpty(nama_kegiatan_hist)) {
					%>
					<textarea name="tujuan_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required><%=Tool.cetakWebFormatTextarea(tujuan_kegiatan_hist, "") %></textarea>
				<%
				}
				else {
				%>
					<textarea name="tujuan_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required></textarea>
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					D.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					ISI KEGIATAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
				<%
				if(!Checker.isStringNullOrEmpty(nama_kegiatan_hist)) {
					%>
					<textarea name="isi_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required><%=Tool.cetakWebFormatTextarea(isi_kegiatan_hist, "") %></textarea>
				<%
				}
				else {
				%>
					<textarea name="isi_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required></textarea>
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					E.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					PIHAK PENANGGUNG JAWAB
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr>
								<th colspan="3" style="background:<%=Constant.lightColorBlu()%>;color:#369;height:30px;font-size:1.2em">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>	
							<tr>	
						<%
				ListIterator li1 = vListJabatan.listIterator();						
				int counter1 = 0;
				while(li1.hasNext()) {
					boolean match = false;
					counter1++;
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_jawab!=null && job_jawab.length>0) {
						for(int i=0;i<job_jawab.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_jawab[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
				%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_jawab" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_jawab" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
						}
					}
					else {
						if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_jawab" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_jawab" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
						}
%>		
							</tr>
							<tr>
<%			
					}
					if(!li1.hasNext()) {
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
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					F.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TARGET AUDIENCE / ANGGOTA KEGIATAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr>
								<th colspan="3" style="background:<%=Constant.lightColorBlu()%>;color:#369;height:30px;font-size:1.2em">Harap klik disini & kemudian centang pihak terkait</th>
							</tr>
						</thead>
						<tbody>	
							<tr>	
						<%
				li1 = vListJabatanGabungan.listIterator();						
				counter1 = 0;
				while(li1.hasNext()) {
					boolean match = false;
					counter1++;
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_target!=null && job_target.length>0) {
						for(int i=0;i<job_target.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_target[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_target" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_target" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
						}
					}
					else {
						if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_target" value="<%=jabatan %>" checked="checked"> <label><%=jabatan %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="job_target" value="<%=jabatan %>" > <label><%=jabatan %></label></td>
<%	
						}
%>		
							</tr>
							<tr>
<%			
					}
					if(!li1.hasNext()) {
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
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					G.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					BUKTI DOKUMEN KEGIATAN UTK DIUPLOAD
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
						<thead>
							<tr>
								<th colspan="3" style="background:<%=Constant.lightColorBlu()%>;color:#369;height:30px;font-size:1.2em">Harap klik disini & kemudian centang dokumen terkait</th>
							</tr>
						</thead>
						<tbody>	
							<tr>	
						<%
				li1 = v_list_doc.listIterator();						
				counter1 = 0;
				while(li1.hasNext()) {
					boolean match = false;
					counter1++;
					String nmm_doc = (String)li1.next();
					if(dok_mutu!=null && dok_mutu.length>0) {
						for(int i=0;i<dok_mutu.length&&!match;i++) {
							if(nmm_doc.trim().toUpperCase().equalsIgnoreCase(dok_mutu[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
				%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="dok_mutu" value="<%=nmm_doc %>" checked="checked"> <label><%=nmm_doc %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="dok_mutu" value="<%=nmm_doc %>" > <label><%=nmm_doc %></label></td>
<%	
						}
					}
					else {
						if(match) {
			%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="dok_mutu" value="<%=nmm_doc %>" checked="checked"> <label><%=nmm_doc %></label></td>
<%	
						}
						else {
%>
								<td align="left" style="border-bottom: 1px solid #ddd;vertical-align: top;"><input type="checkbox" name="dok_mutu" value="<%=nmm_doc %>" > <label><%=nmm_doc %></label></td>
<%	
						}
%>		
							</tr>
							<tr>
<%			
					}
					if(!li1.hasNext()) {
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
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top">
					H.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border:none;padding:0 0 0 10px;vertical-align:top;text-align:left">
					KEGIATAN SUDAH DIMULAI ?
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<table style="width:100%;background:white">
						<tr>
							<td style="border:none;padding:0 0 0 3px;text-align:center;font-size:1.2em">
							<%
				if(kegiatan_started_hist!=null && (kegiatan_started_hist.equalsIgnoreCase("false")||kegiatan_started_hist.equalsIgnoreCase("0"))) {
						%>	
						<input type="radio" name="started" value="false" checked="checked"> Belum Dijalankan
			<%
				}
				else {
					%>	
								<input type="radio" name="started" value="false"> Belum Dijalankan
					<%
				}
					%>			
							</td>
							<td style="border:none;padding:0 0 0 3px;text-align:center;font-size:1.2em">
					<%
				if(kegiatan_started_hist!=null && (kegiatan_started_hist.equalsIgnoreCase("true")||kegiatan_started_hist.equalsIgnoreCase("1"))) {
						%>	
								<input type="radio" name="started" value="true" checked="checked"> Sedang / Sudah Dijalankan
			<%
				}
				else {
					%>			
								<input type="radio" name="started" value="true"> Sedang / Sudah Dijalankan
					<%
				}
					%>			
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="3" style="padding:10px 10px;background:<%=Constant.lightColorBlu()%>;">
					<section class="gradient">
					<div style="text-align:center">
						<button style="padding: 5px 50px;font-size: 20px;">UPDATE DATA KEGIATAN</button>
					</div>
					</section>
				</td>
			</tr>
		</tbody>
	</table>	
</form>
<%	
			}
			else if(kegiatan_started_hist!=null && kegiatan_started_hist.equalsIgnoreCase("1") && kegiatan_ended_hist!=null && kegiatan_ended_hist.equalsIgnoreCase("0")) {
				//kegiatan sdgh dimulai 
%>				
<form action="go.updRiwayatHasilPeningkatan_v1" method="post">
	<input type="hidden" name="status_manual" value="<%=status_manual %>" />
	<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
	<input type="hidden" name="id_versi" value="<%=id_versi %>" />
	<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
	<input type="hidden" name="id_master_std" value="<%=id_master_std%>" />
	<input type="hidden" name="id_std" value="<%=id_std %>" />
	<input type="hidden" name="at_menu_kendal" value="<%=at_menu_kendal %>" />
	<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
	<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
	<input type="hidden" name="scope_std" value="<%=scope_std %>" />
	<input type="hidden" name="id_plan" value="<%=id_plan_hist %>" />
	
				
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:99%;background:white;">
		<thead>
			<tr class="statetablerow">
				<td class="nopad" rowspan="2" style="vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
				<%
				String man_or_std = "manual";
				String target_folder=Converter.autoConvertDateFormat(tgl_sta_hist, "-")+"_"+nama_kegiatan_hist;
				//System.out.println("target_folderA="+target_folder);
				//System.out.println("jenis_kegiatan_histA="+jenis_kegiatan_hist);
				if(jenis_kegiatan_hist.endsWith("_std")) {
					man_or_std = "standar";
				}
				if(jenis_kegiatan_hist.contains("tetap")) {
					kegiatan = "penetapan";
				}
				else if(jenis_kegiatan_hist.contains("cek")) {
					kegiatan = "pemeriksaan";
				}
				else if(jenis_kegiatan_hist.contains("stuju")) {
					kegiatan = "persetujuan";
				}
				else if(jenis_kegiatan_hist.contains("rumus")) {
					kegiatan = "perumusan";
				}
				else if(jenis_kegiatan_hist.contains("kendal")) {
					kegiatan = "pengendalian";
				}
				%>	
					<a href="#" onclick="window.open('<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/linkToArsipFolder.jsp?ppepp=<%=ppepp %>&target_folder=<%=target_folder %>&man_or_std=<%=man_or_std %>&kegiatan=<%=kegiatan %>&kdpst=<%=kdpst %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&id_versi=<%=id_versi %>','popup','width=850,height=450')"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/folder-doc-save.png" alt="Smiley face" height="50" width="50"></a>
				</td>
				<td class="nopad" colspan="2" style="vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					<%=counter++%>.  INFORMASI KEGIATAN <%=ToolSpmi.cetakJenisKegiatanManualDanStandar(jenis_kegiatan_hist)%>
				</td>
			</tr>
			<tr class="statetablerow">
				<td class="nopad" colspan="2" style="vertical-align:middle;font-size:1.2em;text-align:center;font-weight:bold;color:#fff">
				Tanggal Kegiatan Dimulai: <%=tgl_sta_hist%>&nbsp&nbsp  [<%=Checker.pnn(waktu_sta_hist)%>]
				</td>
				
			</tr>
		</thead>
		<tbody>	
			<tr>
				<td style="border: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;padding:0 0 0 10px">
					A.
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;padding:0 0 0 10px;text-align:left">
					JENIS KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;width:70%;background:#D7D7D7;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
					<select name="jenis_kegiatan" style="color:#369;height:30px;width:100%;border:none;text-align-last:center;border-radius: 0px;">
						<%
				boolean match = false;		
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus")) {
							match = true;
							%>
							<option value="rumus" selected="selected">Perumusan Manual</option>
							<%
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek")) {
					match = true;
					%>
							<option value="cek" selected="selected">Pemeriksaan Manual</option>
							<%
				}
				
				if(!match&& jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju")) {
					match = true;
							%>
							<option value="stuju" selected="selected">Persetujuan Manual</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap")) {
					match = true;
							%>
							<option value="tetap" selected="selected">Penetapan Manual</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali")) {
					match = true;
							%>
							<option value="kendali" selected="selected">Pengendalian Manual</option>
							<%	
				}
				//tahapan standar
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus_std")) {
							match = true;
							%>
							<option value="rumus_std" selected="selected">Perumusan Standar</option>
							<%
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek_std")) {
					match = true;
					%>
							<option value="cek_std" selected="selected">Pemeriksaan Standar</option>
							<%
				}
				
				if(!match&& jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju_std")) {
					match = true;
							%>
							<option value="stuju_std" selected="selected">Persetujuan Standar</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap_std")) {
					match = true;
							%>
							<option value="tetap_std" selected="selected">Penetapan Standar</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali_std")) {
					match = true;
							%>
							<option value="kendali_std" selected="selected">Peningkatan Standar</option>
							<%	
				}
				
						%>
					</select>	
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px">
					B.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;text-align:left">
					NAMA KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=nama_kegiatan_hist%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					C.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TUJUAN KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%
				while(tujuan_kegiatan_hist.contains("\n")) {
					tujuan_kegiatan_hist = tujuan_kegiatan_hist.replace("\n","<br>");	
				}
				out.print(Tool.cetakWebFormat(tujuan_kegiatan_hist, "")); 
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					D.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					ISI KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%
				while(isi_kegiatan_hist.contains("\n")) {
					isi_kegiatan_hist = isi_kegiatan_hist.replace("\n","<br>");	
				}
				out.print(Tool.cetakWebFormat(isi_kegiatan_hist, "")); 
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					E.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					PIHAK PENANGGUNG JAWAB
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border: none;height:30px;" >
						<tr>	
						<%
				ListIterator li1 = vListJabatan.listIterator();						
				int counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_jawab!=null && job_jawab.length>0) {
						for(int i=0;i<job_jawab.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_jawab[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
								counter1++;
				%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							
<%	
						}
						
					}
					else {
						if(match) {
							counter1++;
			%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							</tr>
							<tr>
					
<%	
						}
%>		
							
<%			
					}
					if(!li1.hasNext()) {
		%>
							</tr>
		<%		
					}
				}	
%>
					
					</table>				
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					F.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TARGET AUDIENCE / ANGGOTA KEGIATAN
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border:none;;height:30px;" >
						<tr>	
						<%
				li1 = vListJabatanGabungan.listIterator();						
				counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_target!=null && job_target.length>0) {
						for(int i=0;i<job_target.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_target[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
								counter1++;
				%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
								
<%	
						}
						
					}
					else {
						if(match) {
							counter1++;
			%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							</tr>
							<tr>	
<%	
						}
%>		
							
<%			
					}
					if(!li1.hasNext()) {
		%>
							</tr>
		<%		
					}
				}	
%>
					
					</table>				
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					G.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					BUKTI DOKUMEN KEGIATAN UTK DIUPLOAD
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border:none;height:30px;" >
						<tr>	
						<%
				li1 = v_list_doc.listIterator();						
				counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String nmm_doc = (String)li1.next();
					if(dok_mutu!=null && dok_mutu.length>0) {
						for(int i=0;i<dok_mutu.length&&!match;i++) {
							if(nmm_doc.trim().toUpperCase().equalsIgnoreCase(dok_mutu[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
							counter1++;
				%>
							<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=nmm_doc %></td>
							
<%	
						}
					}
					else {
						if(match) {
							counter1++;
			%>
							<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=nmm_doc %></td>
						</tr>
						<tr>	
<%	
						}
%>		
						
<%			
					}
					if(!li1.hasNext()) {
		%>
						</tr>
		<%		
					}
				}	
%>
						
					</table>		
				</td>
			</tr>
			<tr>
				<td colspan="3" style="border: 1px solid #ffcccc;vertical-align:middle;text-align:center;background:#ffcccc;color:#369;font-weight:bold">
					<table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="border: 1px solid #ffcccc;vertical-align:middle;width:100%;background:#ffcccc;color:#369;">
						<thead>
						<%
				if(v_err!=null && v_err.size()>0 && id_plan!=null && id_plan.equalsIgnoreCase(id_plan_hist)) {
					ListIterator litmp = v_err.listIterator();
				%>
							<tr class="statetablerow">
								<td colspan="3" style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:red">
				<%
					while(litmp.hasNext()) {
						String brs2 = (String)litmp.next();
						out.print("* "+brs2+"<br>");
					}
					out.print("[ Harap diisi kembali data yang akan dirubah ]<br>");
				%>	
								</td>
							</tr>
				<%	
				}
				if(v_ok!=null && v_ok.size()>0 && id_plan!=null && id_plan.equalsIgnoreCase(id_plan_hist)) {
					ListIterator litmp = v_ok.listIterator();
					%>
							<tr class="statetablerow">
								<td colspan="3" style="background:#fff;font-size:0.9em;text-align:center;font-weight:bold;color:#369">
					<%
					while(litmp.hasNext()) {
						String brs2 = (String)litmp.next();
						out.print("* "+brs2+"<br>");
					}
					%>	
								</td>
							</tr>
					<%	
				}		
			%>
							<tr>
								<th class="yellow" colspan="3" style="border: 1px solid #FE9A2E;vertical-align:middle;text-align:center;color:#369;padding:0 0 0 0;font-size:2em;font-weight:bold">
									FORM HASIL KEGIATAN
									<input type="hidden" name="penetapan_tgl" value="no">
								</th>
							</tr>
							
						</thead>
						<tbody>	
							<tr>
								<td colspan="3">
									<table style="border:none;width:100%">
										<tr>
											<td style="width:5%;border: 1px solid #FE9A2E;vertical-align:top;text-align:center;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												A.
											</td>
											<td style="width:25%;border: 1px solid #FE9A2E;vertical-align:top;text-align:left;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												RINCIAN HASIL KEGIATAN
											</td>
											<td style="border: 1px solid #FE9A2E;width:75%;background:#fff;color:#369;font-size:1em;text-align:center;padding:0 0 0 0">
				<%
				if(Checker.isStringNullOrEmpty(hasil_kegiatan_hist)) {
				%>	
												<textarea name="hasil" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:5" placeholder="isikan hasil dari kegiatan" required></textarea>
				<%
				}
				else {
				%>	
												<textarea name="hasil" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:5" placeholder="isikan hasil dari kegiatan" required><%=Tool.cetakWebFormatTextarea(hasil_kegiatan_hist,"") %></textarea>
				<%
				}
				%>							
											</td>
										</tr>
										<tr>
											<td style="width:5%;border: 1px solid #FE9A2E;vertical-align:top;text-align:center;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												B.
											</td>
											<td style="width:25%;border: 1px solid #FE9A2E;vertical-align:top;text-align:left;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												CATATAN TAMBAHAN
											</td>
											<td style="border: 1px solid #FE9A2E;width:75%;background:#fff;color:#369;font-size:1em;text-align:center;padding:0 0 0 0">
				<%
				if(Checker.isStringNullOrEmpty(note_kegiatan_hist)) {
				%>
												<textarea name="note"  style="vertical-align:middle;width:100%;height:100px;border:none;rows:5" placeholder="catatan tambahan kegiatan (optional)"></textarea>
				<%
				}
				else {
				%>	
												<textarea name="note"  style="vertical-align:middle;width:100%;height:100px;border:none;rows:5" placeholder="catatan tambahan kegiatan (optional)"><%=Tool.cetakWebFormatTextarea(note_kegiatan_hist,"") %></textarea>
				<%
				}
				%>
											</td>
										</tr>
										<tr>
											<td style="width:5%;border: 1px solid #FE9A2E;vertical-align:top;text-align:center;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												C.
											</td>
											<td style="width:25%;border: 1px solid #FE9A2E;vertical-align:top;text-align:left;background:#F5D0A9;color:#369;padding:0 5px 0 10px;font-size:1em;font-weight:bold">
												TANGGAL & WAKTU<br>KEGIATAN BERAKHIR
											</td>
											<td style="border: 1px solid #FE9A2E;width:75%;background:#FBF5EF;color:#369;font-size:1em;text-align:center;padding:0 0 0 0">
												
			<%
				if(Checker.isStringNullOrEmpty(tgl_end_hist)) {
			%>
												TGL SELESAI : <input type="text" name="tgl_end" value="" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em"/>
			<%	
				}
				else {
					tgl_end_hist = Converter.autoConvertDateFormat(tgl_end_hist, "/");	
				
			%>
												TGL SELESAI : <input type="text" name="tgl_end" value="<%=tgl_end_hist %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em"/>
			<%
				}
			%>				
							
			<%
				if(Checker.isStringNullOrEmpty(waktu_end_hist)) {
			%>
												&nbsp&nbsp  JAM : <input type="text" name="waktu_end" placeholder="hh:mm" value="" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em"/>
			<%
				}
				else {
					if(waktu_end_hist.length()==8) {
						waktu_end_hist=waktu_end_hist.substring(0, 5);
					}
				
			%>
												&nbsp&nbsp  JAM : <input type="text" name="waktu_end" placeholder="hh:mm" value="<%=waktu_end_hist %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em"/>
			<%		
				}
			%>					
												<br>
												 <label>* biarkan kosong bila kegiatan masih berlangsung</label>
											</td>
										</tr>
										<%
				if(!Checker.isStringNullOrEmpty(jenis_kegiatan_hist)) {
					String jenis_tmp = "";
					if(jenis_kegiatan_hist.equalsIgnoreCase("rumus")) {
						jenis_tmp = "PERUMUSAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("cek")) {
						jenis_tmp = "PEMERIKSAAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("stuju")) {
						jenis_tmp = "PERSETUJUAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("tetap")) {
						jenis_tmp = "PENETAPAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("kendali")) {
						jenis_tmp = "PENGENDALIAN";
					}
				%>
										
										<%	
				}
										%>
										
										<tr>
											<td colspan="3" style="padding:10px 10px;background:#FE9A2E;">
												<section class="gradient">
													<div style="text-align:center">
														<button style="padding: 5px 50px;font-size: 20px;">Update Info Hasil Kegiatan</button>
													</div>
												</section>
											</td>
										</tr>
									</table>
								</td>
							</tr>			
						</tbody>	
					</table>
				</td>	
			</tr>	
		</tbody>	
	</table>
</form>			
<%					
			}
			else {
%>


<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:99%;background:white;">
		<thead>
			<tr class="statetablerow">
				<td class="nopad" rowspan="2" style="vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
				<%
				String man_or_std = "manual";
				String target_folder=Converter.autoConvertDateFormat(tgl_sta_hist, "-")+"_"+nama_kegiatan_hist;
				//System.out.println("target_folderA="+target_folder);
				//System.out.println("jenis_kegiatan_histA="+jenis_kegiatan_hist);
				if(jenis_kegiatan_hist.endsWith("_std")) {
					man_or_std = "standar";
				}
				if(jenis_kegiatan_hist.contains("tetap")) {
					kegiatan = "penetapan";
				}
				else if(jenis_kegiatan_hist.contains("cek")) {
					kegiatan = "pemeriksaan";
				}
				else if(jenis_kegiatan_hist.contains("stuju")) {
					kegiatan = "persetujuan";
				}
				else if(jenis_kegiatan_hist.contains("rumus")) {
					kegiatan = "perumusan";
				}
				else if(jenis_kegiatan_hist.contains("kendal")) {
					kegiatan = "pengendalian";
				}
				%>	
					<a href="#" onclick="window.open('<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/linkToArsipFolder.jsp?ppepp=<%=ppepp %>&target_folder=<%=target_folder %>&man_or_std=<%=man_or_std %>&kegiatan=<%=kegiatan %>&kdpst=<%=kdpst %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&id_versi=<%=id_versi %>','popup','width=850,height=450')"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/folder-doc-save.png" alt="Smiley face" height="50" width="50"></a>
				</td>
				<td class="nopad" colspan="2" style="vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					<%=counter++%>.  INFORMASI KEGIATAN <%=ToolSpmi.cetakJenisKegiatanManualDanStandar(jenis_kegiatan_hist)%>
				</td>
			</tr>
			<tr class="statetablerow">
				<td class="nopad" colspan="2" style="background:#369;color:#fff;valign:middle;font-size:1.2em;text-align:center;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
				Tanggal Kegiatan Dimulai: <%=tgl_sta_hist%>&nbsp&nbsp  [<%=Checker.pnn(waktu_sta_hist)%>]
				</td>
			</tr>
		</thead>
		<tbody>	
			<tr>
				<td style="border: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;padding:0 0 0 10px">
					A.
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;padding:0 0 0 10px;text-align:left">
					JENIS KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;width:70%;background:#D7D7D7;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
					<select name="jenis_kegiatan" style="color:#369;height:30px;width:100%;border:none;text-align-last:center;border-radius: 0px;">
						<%
				boolean match = false;		
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus")) {
							match = true;
							%>
							<option value="rumus" selected="selected">Perumusan Manual</option>
							<%
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek")) {
					match = true;
					%>
							<option value="cek" selected="selected">Pemeriksaan Manual</option>
							<%
				}
				
				if(!match&& jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju")) {
					match = true;
							%>
							<option value="stuju" selected="selected">Persetujuan Manual</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap")) {
					match = true;
							%>
							<option value="tetap" selected="selected">Penetapan Manual</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali")) {
					match = true;
							%>
							<option value="kendali" selected="selected">Pengendalian Manual</option>
							<%	
				}
				//tahapan_std
				if(jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("rumus_std")) {
							match = true;
							%>
							<option value="rumus_std" selected="selected">Perumusan Standar</option>
							<%
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("cek_std")) {
					match = true;
					%>
							<option value="cek_std" selected="selected">Pemeriksaan Standar</option>
							<%
				}
				
				if(!match&& jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("stuju_std")) {
					match = true;
							%>
							<option value="stuju_std" selected="selected">Persetujuan Standar</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("tetap_std")) {
					match = true;
							%>
							<option value="tetap_std" selected="selected">Penetapan Standar</option>
							<%	
				}
				
				if(!match && jenis_kegiatan_hist!=null && jenis_kegiatan_hist.equalsIgnoreCase("kendali_std")) {
					match = true;
							%>
							<option value="kendali_std" selected="selected">Peningkatan Standar</option>
							<%	
				}
				
						%>
					</select>	
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px">
					B.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;text-align:left">
					NAMA KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=nama_kegiatan_hist%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					C.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TUJUAN KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=Tool.cetakWebFormat(tujuan_kegiatan_hist, "") %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					D.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					ISI KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=Tool.cetakWebFormat(isi_kegiatan_hist, "") %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					E.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					PIHAK PENANGGUNG JAWAB
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border: none;height:30px;" >
						<tr>	
						<%
				ListIterator li1 = vListJabatan.listIterator();						
				int counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_jawab!=null && job_jawab.length>0) {
						for(int i=0;i<job_jawab.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_jawab[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
								counter1++;
				%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							
<%	
						}
						
					}
					else {
						if(match) {
							counter1++;
			%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							</tr>
							<tr>
					
<%	
						}
%>		
							
<%			
					}
					if(!li1.hasNext()) {
		%>
							</tr>
		<%		
					}
				}	
%>
					
					</table>				
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					F.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TARGET AUDIENCE / ANGGOTA KEGIATAN
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border:none;;height:30px;" >
						<tr>	
						<%
				li1 = vListJabatanGabungan.listIterator();						
				counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String brs1 = (String)li1.next();
					st = new StringTokenizer(brs1,"`");
					String jabatan = st.nextToken();
					String sin = st.nextToken();
					if(job_target!=null && job_target.length>0) {
						for(int i=0;i<job_target.length&&!match;i++) {
							if(jabatan.trim().toUpperCase().equalsIgnoreCase(job_target[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
								counter1++;
				%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
								
<%	
						}
						
					}
					else {
						if(match) {
							counter1++;
			%>
								<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=jabatan %></td>
							</tr>
							<tr>	
<%	
						}
%>		
							
<%			
					}
					if(!li1.hasNext()) {
		%>
							</tr>
		<%		
					}
				}	
%>
					
					</table>				
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					G.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					BUKTI DOKUMEN KEGIATAN UTK DIUPLOAD
				</td>
				<td style="border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 0">
					<table class="table1" id="tabel" style="width:100%;border:none;height:30px;" >
						<tr>	
						<%
				li1 = v_list_doc.listIterator();						
				counter1 = 1;
				while(li1.hasNext()) {
					match = false;
					
					String nmm_doc = (String)li1.next();
					if(dok_mutu!=null && dok_mutu.length>0) {
						for(int i=0;i<dok_mutu.length&&!match;i++) {
							if(nmm_doc.trim().toUpperCase().equalsIgnoreCase(dok_mutu[i].trim().toUpperCase())) {
								match = true;
							}
						}
					}
	
					if(counter1%3!=0) {
						if(match) {
							counter1++;
				%>
							<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=nmm_doc %></td>
							
<%	
						}
					}
					else {
						if(match) {
							counter1++;
			%>
							<td align="left" style="border:none;font-weight:bold;vertical-align: top;padding:0 0 0 10px"><%=counter1-1%>.&nbsp&nbsp<%=nmm_doc %></td>
						</tr>
						<tr>	
<%	
						}
%>		
						
<%			
					}
					if(!li1.hasNext()) {
		%>
						</tr>
		<%		
					}
				}	
%>
						
					</table>		
				</td>
			</tr>
			<tr>
				<td colspan="3" style="background:#369;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					INFORMASI HASIL KEGIATAN
				</td>
			</tr>
			<tr>
				<td colspan="3" style="background:#369;color:#fff;valign:middle;font-size:1.2em;text-align:center;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
				Tanggal Kegiatan Selesai: <%=Converter.autoConvertDateFormat(tgl_end_hist, "/") %>&nbsp&nbsp  
				<%
				if(!Checker.isStringNullOrEmpty(waktu_end_hist)) {
					out.print("["+waktu_end_hist+"]");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					A.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					RINCIAN HASIL KEGIATAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=Checker.pnn(hasil_kegiatan_hist.replace("\n", "<br>")) %>
				</td>
			</tr>	
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					B.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					CATATAN TAMBAHAN
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%=Checker.pnn(note_kegiatan_hist.replace("\n", "<br>")) %>
				</td>
			</tr>	
			<%
			
				if(!Checker.isStringNullOrEmpty(jenis_kegiatan_hist)&&(tgl_rumus_set_hist.equalsIgnoreCase("1")||tgl_cek_set_hist.equalsIgnoreCase("1")||tgl_stuju_set_hist.equalsIgnoreCase("1")||tgl_tetap_set_hist.equalsIgnoreCase("1")||tgl_kendali_set_hist.equalsIgnoreCase("1"))) {
					String jenis_tmp = "";
					if(jenis_kegiatan_hist.equalsIgnoreCase("rumus")) {
						jenis_tmp = "PERUMUSAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("cek")) {
						jenis_tmp = "PEMERIKSAAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("stuju")) {
						jenis_tmp = "PERSETUJUAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("tetap")) {
						jenis_tmp = "PENETAPAN";
					}
					else if(jenis_kegiatan_hist.equalsIgnoreCase("kendali")) {
						jenis_tmp = "PENGENDALIAN";
					}
			%>
			
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top">
					C.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;border: 1px solid #fff;padding:0 0 0 10px;vertical-align:top;text-align:left">
					TETAPKAN SEBAGAI TANGGAL
				</td>
				<td style="font-weight:bold;border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1em;text-align:left;padding:0 0 0 10px">
				<%="PENETAPAN PROSES "+jenis_tmp.toUpperCase() %>
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
	<br><br>
<%			
		}	

	}
	else {
		out.print("BELUM ADA RIWAYAT");
	}
			
	

}	
%>		

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>