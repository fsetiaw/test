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
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
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


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//System.out.println("masuk");
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
String id_versi = request.getParameter("id_versi");
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String id_std_isi = request.getParameter("id_std_isi");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String target_unit_used = request.getParameter("unit_used");
String src_manual_limit = (String) session.getAttribute("src_manual_limit");
String src_offset = request.getParameter("offset");
String starting_no = request.getParameter("starting_no");
if(Checker.isStringNullOrEmpty(starting_no)) {
	starting_no="1";
}

ToolSpmi ts = new ToolSpmi();
//String tgl_sta_man = ""+ts.getTglStartManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "pelaksanaan");
//String tgl_tetap_man = ""+ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "penetapan","perencanaan");
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
boolean sdh_aktif = false;
SearchStandarMutu ssm = new SearchStandarMutu();
//sdh_aktif = ssm.isStandardActivatedUmum(Integer.parseInt(id_std));
sdh_aktif = true;

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
String param_indikator = ssm.getParameterDanIndikator(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
st = new StringTokenizer(param_indikator,"`");
String param = st.nextToken();
String indikator = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	
//TIPE_SARPRAS & CATAT_CIVITAS didiapat dari vector, beda dengan form passing param dari menunya


String list_tipe_sarpras = Getter.getListTipeSarpras();
Vector v_info_sarpras = Getter.getListDetilInfoSarpras();

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
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();
	
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
	table.StateTable{margin:0px; border:1px solid #369;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
	table.StateTable thead tr:hover td { background:#82B0C3 }
	
</style>
</head>
<body>
<jsp:include page="menu_man_ami.jsp" />
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

Vector v_hist_eval=null;
		/*
ListIterator lit = v_scope_id.listIterator();
while(lit.hasNext()) {
	String brs = (String)lit.next();
	//System.out.println(brs);
}
*/
SrcHistEvaluasi shk = new SrcHistEvaluasi();
//System.out.println("offset="+offset);

//v_hist_eval=shk.getListRiwayatEvaluasiAmi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man),offset,Integer.parseInt(src_manual_limit),kdpst);
v_hist_eval=shk.getListRiwayatEvaluasiAmi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), offset, Integer.parseInt(src_manual_limit),kdpst);
/*
if(v_hist_eval!=null) {
	//System.out.println("v_hist_eval = "+v_hist_eval.size());
}
else {
	//System.out.println("v_hist_eval is null");
}
*/
//Vector v_kendal= sm.searchManualStandardPengendalian_v1(Integer.parseInt(id_kendali));
/*
if(!Checker.isStringNullOrEmpty(filter_by_kdpst) && v_hist_eval!=null) {
	//FILTER BERDASARKAN KDPST
	ListIterator li = v_hist_eval.listIterator();
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
if(v_hist_eval==null || v_hist_eval.size()<1) {
%>	
		<br><br>
		<table style="width:99%">
		<tr>
			<td colspan="7" style="text-align:center;padding:0 0;font-size:2.0em;font-weight:bold">
				Belum Ada Kegiatan AMI			
			</td>
		</tr>
		<%
	if(man_sdh_aktif && sdh_aktif && ((am_i_surveyor||team_spmi)||editor)) {	
	//if(((am_i_surveyor||team_spmi) && sdh_aktif)||editor) {
		%>
		<tr>
			<td style="text-align:center;font-size:1.3em;font-weight:bold" align="center">
			
				<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/form_survey_evaluasi_ami_v1.jsp">
					
					<input type="hidden" name="status_manual" value="<%=status_manual %>" />
					<input type="hidden" name="scope_std" value="<%=scope_std %>" />
					<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
					<input type="hidden" name="id_versi" value="<%=id_versi %>" />
					<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>" />
					<input type="hidden" name="id_std" value="<%=id_std %>" />
					<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
					<input type="hidden" name="id_master_std" value="<%=id_master_std %>" />
					<input type="hidden" name="at_menu_kendal" value="riwayat" />
					<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
					<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
					<input type="hidden" name="am_i_controller" value="<%=am_i_controller %>" />
					<input type="hidden" name="am_i_surveyor" value="<%=am_i_surveyor %>" />
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />TAMBAH KEGIATAN EVALUASI
				</form>
			</td>
		</tr>
		<%
	}
	else {
			%>
			<tr>
				<td style="text-align:center;font-size:1.3em;font-weight:bold" align="center">
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />
					TAMBAH KEGIATAN AMI 1
				</td>
			</tr>
			<tr>	
				<td style="text-align:center;font-size:1em;font-weight:bold;color:red" align="center">
					Saat ini kegiatan Audit Mutu Internal belum bisa ditambah, karena 
			<%
		if(!man_sdh_aktif) {
				 if(!sdh_aktif) {
				 	out.print("Standar belum dijalankan dan manual evaluasi standar belum diaktifkan");
					 	
				 }
				 else {
				 	out.print("manual evaluasi standar belum diaktifkan<br>Untuk mengaktifkan silahkan menekan tombol 'play' pada manual / standar terkait");
					 	
				 }
		}
		else  if(!sdh_aktif) {
				out.print("Standar belum dijalankan<br>Untuk mengaktifkan silahkan menekan tombol 'play' pada manual / standar terkait");
		}
		else {
				out.print("Anda tidak berwenang untuk melakukan evaluasi standar");
		}
			%>		
					
					
				</td>
			</tr>			
			<%
	}
		%>
		</table>	
<%
}
else {
%>

		<br>
		<%
	if((am_i_surveyor && sdh_aktif)||editor) {
		%>		
		<section style="align:center;text-align:center">	
			<p>
				<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/form_survey_evaluasi_ami_v1.jsp" style="font-weight:bold;color:#369">
					<input type="hidden" name="scope_std" value="<%=scope_std %>" />
					<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
					<input type="hidden" name="id_versi" value="<%=id_versi %>" />
					<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>" />
					<input type="hidden" name="id_std" value="<%=id_std %>" />
					<input type="hidden" name="id_master_std" value="<%=id_master_std %>" />
					<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>" />
					<input type="hidden" name="at_menu_kendal" value="riwayat" />
					<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
					<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
					<input type="hidden" name="am_i_controller" value="<%=am_i_controller %>" />
					<input type="hidden" name="am_i_surveyor" value="<%=am_i_surveyor %>" />
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />TAMBAH KEGIATAN AMI 2
				</form>
			</p>	
		</section>
			
<%	
	}
%>
	
<table align="center" style="width:99%">
	<tr>
		<td colspan="8" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
			RIWAYAT KEGIATAN AMI 3
		</td>
	</tr>

</table>			
<section style="text-align:center">	
		<jsp:include page="navigasi_ami_v1.jsp" flush="true" />
		</section>
<%	
	
	ListIterator li = v_hist_eval.listIterator();
	int size =  v_hist_eval.size();
	if(li.hasNext()) {
			
		int counter = Integer.parseInt(starting_no);
		
		//ListIterator lir = null;
		int no_yg_tampil=1;
		while(li.hasNext()&& (no_yg_tampil <= max_yg_tampil)) {
			no_yg_tampil++;
			String brs = (String)li.next();
			//id_eval=st.nextToken();
			//std_versi_id+"~"+std_isi_id+"~"+norut+"~"+npm_eval+"~"+tgl_eval+"~"+waktu_eval+"~"+eval_timestamp+"~"+sikon+"~"+analisa+"~"+rekomendasi+"~"+tgl_next_eval+"~"+waktu_next_eval+"~"+target_val+"~"+real_val+"~"+tgl_sta+"~"+tgl_end+"~"+target_thsms1+"~"+target_thsms2+"~"+target_thsms3+"~"+target_thsms4+"~"+target_thsms5+"~"+target_thsms6+"~"+target_unit+"~"+aktif;
			st = new StringTokenizer(brs,"~");
			String id_eval=st.nextToken();
			String versi_id=st.nextToken();
			String std_isi_id=st.nextToken();
			String norut=st.nextToken();
			String npm_eval=st.nextToken();
			String tgl_eval=st.nextToken();
			String waktu_eval=st.nextToken();
			String eval_timestamp=st.nextToken();
			String sikon=st.nextToken();
			String analisa=st.nextToken();
			String rekomendasi=st.nextToken();
			String tgl_next_eval=st.nextToken();
			String waktu_next_eval=st.nextToken();
			String target_val=st.nextToken();
			String ril_val=st.nextToken();
			String tgl_sta=st.nextToken();
			String tgl_end=st.nextToken();
			String target_thsms1=st.nextToken();
			String target_thsms2=st.nextToken();
			String target_thsms3=st.nextToken();
			String target_thsms4=st.nextToken();
			String target_thsms5=st.nextToken();
			String target_thsms6=st.nextToken();
			String target_unit=st.nextToken();
			String aktif=st.nextToken();
%>


	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:99%;background:<%=Constant.lightColorBlu()%>;">
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="background:#369;vertical-align:middle;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/up_down_white.png" alt="Smiley face" height="42" width="42" style="vertical-align:middle">&nbsp&nbsp&nbsp&nbsp<%=counter++%>.  KEGIATAN EVALUASI AMI <%=Converter.convertFormatTanggalKeFormatDeskriptif(tgl_eval)%> &nbsp&nbsp
					<%
				if(!Checker.isStringNullOrEmpty(waktu_next_eval)) {
					out.print("["+waktu_next_eval.substring(0,5)+"]");
				}
					%> 
					&nbsp&nbsp&nbsp&nbsp<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/up_down_white.png" alt="Smiley face" height="42" width="42" style="vertical-align:middle">
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table class="table" id="tabel" style="width:100%;border:1px solid <%=Constant.lightColorBlu()%>;">
						<tr>
							<td rowspan="2" colspan="1" style="border:1px solid #369;vertical-align:middle;width:40%;text-align:center;background:#92B9D2;color:#369;padding:0 0 0 0;font-size:2em;font-weight:bold">
								PARAMETER YG DIEVALUASI
							</td>
							<td rowspan="2" colspan="1" style="border:1px solid #369;vertical-align:middle;width:40%;text-align:center;background:#92B9D2;color:#369;padding:0 0 0 0;font-size:2em;font-weight:bold">
								INDIKATOR CAPAIAN
							</td>
							<td colspan="1" style="border:1px solid #369;width:10%;text-align:center;background:#92B9D2;color:#369;font-size:1.25em;font-weight:bold">
								BESARAN TARGET
							</td>
							<td colspan="1" style="border:1px solid #369;width:10%;text-align:center;background:#92B9D2;color:#369;font-size:1.25em;font-weight:bold">
								BESARAN RIL
							</td>
						</tr>
						<tr>
								<td colspan="2" style="border:1px solid #369;text-align:center;font-size:1.15em;background:#92B9D2;color:white;font-weight:bold">
								<%
				if(!Checker.isStringNullOrEmpty(target_unit_used)) {
					out.print(target_unit_used);
				}
								%>
								</td>
						</tr>
						<tr>
							<td style="border:1px solid #369;vertical-align:middle;padding:0 5px 0 10px;font-size:1.5em;font-weight:bold"><%=param %></td>
							<td style="border:1px solid #369;vertical-align:middle;padding:0 5px 0 10px;font-size:1.5em;font-weight:bold"><%=indikator %></td>
							<td style="border:1px solid #369;vertical-align:middle;text-align:center;font-size:1.5em;font-weight:bold"><%=target_val %></td>
							<td style="border:1px solid #369;vertical-align:middle;text-align:center;font-size:1.5em;font-weight:bold"><%=ril_val %></td>
						</tr>
					</table>
				</td>
			</tr>
		</thead>
		<tbody>	
			
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;font-weight:bold">
					A.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					AUDITOR / SURVEYOR
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#000;width:75%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					<%=Checker.getNmmhs(npm_eval) %>
				</td>
			</tr>
			<tr>	
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;font-weight:bold">
					B.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					SITUASI DAN KONDISI LAPANGAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#000;width:75%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					<%=sikon %>
				</td>
			</tr>	
			<tr>	
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;font-weight:bold">
					C.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					HASIL ANALISA LAPANGAN
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#000;width:75%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					<%=analisa %>
				</td>
			</tr>
			<tr>	
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;font-weight:bold">
					D.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					REKOMENDASI
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#000;width:75%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					<%=rekomendasi %>
				</td>
			</tr>
			<%
			/*
			%>	
			<tr class="statetablerow">
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;font-weight:bold">
					E.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					JADWAL SURVEY BERIKUTNYA
				</td>
				<td style="background:<%=Constant.lightColorBlu()%>;color:#000;width:75%;border:1px solid <%=Constant.lightColorBlu()%>;padding:0 0 0 10px;text-align:left;font-weight:bold">
					<%=Converter.convertFormatTanggalKeFormatDeskriptif(tgl_next_eval) %>&nbsp&nbsp
					<%
				if(!Checker.isStringNullOrEmpty(waktu_next_eval)) {
					out.print("["+waktu_next_eval+"]");
				}
					%>

				</td>
			</tr>
			<%
			*/
			%>
		</tbody>
	</table>
	<br><br>		
			
<%				
		}	
		%>
	
	<%
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