<!DOCTYPE html>
<head>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.*"%>
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


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");


/*
KHUSUS UNTUK FORM SURVEY DIBERI KEMUDAHAN DIMANA SCOPE KDPST BUKAN DARI PILIHAN PERTAMA DARI MENU PENJAMINAN MUTU
TAPI BERDASARKAN SKOPE COMAAND AGAR MEMPERMUDAH PROSES PENGAWASAN
*/
String thsms_now = Checker.getThsmsNow();
Vector v_scope_cmd = validUsr.returnTknDistinctInfoProdiOnlyGivenCommand("hasSpmiMenu", true, thsms_now);
Vector v_list_target_civitas = (Vector)request.getAttribute("v_list"); //hasil searching mode
request.removeAttribute("v_list");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
Vector v_pihak = sdb.getListAvalablePihakTerkait_v1(false);
Vector v_group = sdb.getListAvalablePihakTerkait_v1(true);


String scope_std = request.getParameter("scope_std");
String std_kdpst = request.getParameter("std_kdpst");
String id_versi = request.getParameter("id_versi");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String id_tipe_std = request.getParameter("id_tipe_std");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String status_manual = request.getParameter("status_manual");

boolean man_sdh_aktif = false;
if(status_manual!=null && status_manual.equalsIgnoreCase("AKTIF")) {
	man_sdh_aktif = true;
}




String target_unit_used = request.getParameter("target_unit_used");

//dari form
String form_tgl_sta = request.getParameter("tgl_sta");
String form_waktu_sta = request.getParameter("waktu_sta");
String form_jenis_kegiatan = request.getParameter("jenis_kegiatan");
String form_nmm_kegiatan = request.getParameter("nmm_kegiatan");
String form_tujuan_kegiatan = request.getParameter("tujuan_kegiatan");
String form_isi_kegiatan = request.getParameter("isi_kegiatan");
String[] form_job_jawab = request.getParameterValues("job_jawab");
String[] form_job_target = request.getParameterValues("job_target");
String[] form_dok_mutu = request.getParameterValues("dok_mutu");
String form_started = request.getParameter("started");
//System.out.println("form_jenis_kegiatan="+form_jenis_kegiatan);

kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//String id_kendali = request.getParameter("id_kendali");


//TIPE_SARPRAS & CATAT_CIVITAS didiapat dari vector, beda dengan form passing param dari menunya
//String tipe_sarpras = request.getParameter("tipe_sarpras");
//String catat_civitas = request.getParameter("catat_civitas");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	
//param dari form di page ini
SearchManual sm = new SearchManual();
//System.out.println("pit 1");
//System.out.println("versi_id="+id_versi);
//System.out.println("std_isi_id="+id_std_isi);
//System.out.println("norut_man="+norut_man);
//Vector v = sm.prepInfoFormEvaluasi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man));
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
String[]job_jawab=request.getParameterValues("job_jawab");
String[]dok_mutu=request.getParameterValues("dok_mutu");

Vector v_list_doc = Getter.getListDokumenMutu();
//System.out.println("pit 2");
//String list_tipe_sarpras = Getter.getListTipeSarpras();
//Vector v_info_sarpras = Getter.getListDetilInfoSarpras();
//prepInfoFormSureveyPengendalian

boolean am_i_controller = true;
boolean am_i_surveyor = true;
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
<jsp:include page="menu_back_v1.jsp" />
	
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
Vector v_err = null;
v_err = (Vector) request.getAttribute("v_err");
request.removeAttribute("v_err");	
//String tgl = null;
//String waktu = null;
//String next_tgl_survey = null;
//String next_waktu_survey = null;
//String ril_val = null;
//String sikon = null;
//String analisa = null;
//String rekomendasi = null;


if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
	//get VALUE dari hasil validasi servlet
	/*
	tgl = (String) request.getAttribute("tgl");
	waktu = (String) request.getAttribute("waktu");
	next_tgl_survey = (String) request.getAttribute("next_tgl_survey");
	next_waktu_survey = (String) request.getAttribute("next_waktu_survey");
	ril_val = (String) request.getAttribute("ril_val");
	sikon = (String) request.getAttribute("sikon");
	analisa = (String) request.getAttribute("analisa");
	rekomendasi = (String) request.getAttribute("rekomendasi");
	request.removeAttribute("next_tgl_survey");
	request.removeAttribute("next_waktu_survey");
	
	request.removeAttribute("tgl");
	request.removeAttribute("waktu");scope_std
	request.removeAttribute("ril_val");
	request.removeAttribute("sikon");
	request.removeAttribute("analisa");
	request.removeAttribute("rekomendasi");
	*/
	
%>	
	</div>
	
<%	
}
%>		
		<br>
<%
//if(v!=null && v.size()>0) {
if(false) {	
	
}
else {
%>


<br>
<form action="go.updRiwayatPelaksanaan_v1" method="post">
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
	<input type="hidden" name="status_manual" value="<%=status_manual %>" />

	
	<table class="table1" id="tabel" style="width:100%" >
		<thead>
			<tr>
				<td colspan="3" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
					FORM RENCANA KEGIATAN 
				</td>
			</tr>
			<tr>
				<td colspan="3" style="background:#369;font-size:2em;text-align:center;font-weight:bold;color:#fff">
					INFORMASI RENCANA KEGIATAN
				</td>
			</tr>
			<tr style=";vertical-align:middle;">
				<thead>	
				<th colspan="3" style="valign:top;font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
				<%
		String dt = AskSystem.getLocalDate();
		String tm = ""+AskSystem.getWaktuSekarang();
			%>
					<table style="width:100%;border:none">
						<tr>
							<td style="width:60%;border:none;padding:0 0 0 10px">
			<%
		if(Checker.isStringNullOrEmpty(form_tgl_sta)) {
			%>
					TGL DIMULAI : <input type="text" name="tgl_sta" value="<%=dt %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%	
		}
		else {
			%>
					TGL DIMULAI : <input type="text" name="tgl_sta" value="<%=form_tgl_sta %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%
		}
			%>				
							</td>
							<td style="width:40%;border:none;padding:0 0 0 10px">
			<%
		if(Checker.isStringNullOrEmpty(form_waktu_sta)) {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu_sta" placeholder="hh:mm" value="<%=tm %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%
		}
		else {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu_sta" placeholder="hh:mm" value="<%=form_waktu_sta %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%		
		}
			%>					
							</td>
						</tr>
					</table>
			
					 
				</th>
				</thead>
			</tr>
			<tr>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:5%;border:none;padding:0 0 0 10px">
					A.
				</td>
				<td style="background:<%=Constant.darkColorBlu()%>;color:#fff;width:25%;border:none;padding:0 0 0 10px;text-align:left">
					JENIS KEGIATAN
				</td>
				<td style="width:70%;background:#D7D7D7;color:#369;font-size:0.8em;text-align:center;padding:0 0 0 0">
					<select name="jenis_kegiatan" style="height:30px;width:100%;border:none;text-align-last:center;border-radius: 0px;">
						<option value="kendali_std">Pelaksanaan Standar</option>
						<!--  option value="">--pilih jenis kegiatan--</option-->
						<%
						/*
						if(!man_sdh_aktif) {
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("rumus")) {
							%>
							<option value="rumus" selected="selected">Perumusan Manual</option>
							<%
							}
							else {
						%>
						<option value="rumus">Perumusan Manual</option>
						<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("cek")) {
							%>
							<option value="cek" selected="selected">Pemeriksaan Manual</option>
							<%
							}
							else {
						%>
						<option value="cek">Pemeriksaan Manual</option>
						<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("stuju")) {
							%>
							<option value="stuju" selected="selected">Persetujuan Manual</option>
							<%	
							}
							else {
						%>
						<option value="stuju">Persetujuan Manual</option>
						<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("tetap")) {
							%>
							<option value="tetap" selected="selected">Penetapan Manual</option>
							<%	
							}
							else {
						%>
						<option value="tetap">Penetapan Manual</option>
						<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("kendali")) {
							%>
							<option value="kendali" selected="selected">Pengendalian Manual</option>
							<%	
							}
							else {
						%>
						<option value="kendali">Pengendalian Manual</option>
						<%
							}
						}
						else {
							//perancangan std
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("rumus_std")) {
								%>
							<option value="rumus_std" selected="selected">Perumusan Standar</option>
								<%
							}
							else {
							%>
							<option value="rumus_std">Perumusan Standar</option>
							<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("cek_std")) {
								%>
							<option value="cek_std" selected="selected">Pemeriksaan Standar</option>
								<%
							}
							else {
							%>
							<option value="cek_std">Pemeriksaan Standar</option>
							<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("stuju_std")) {
								%>
							<option value="stuju_std" selected="selected">Persetujuan Standar</option>
								<%	
							}
							else {
							%>
							<option value="stuju_std">Persetujuan Standar</option>
							<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("tetap_std")) {
								%>
							<option value="tetap" selected="selected">Penetapan Standar</option>
								<%	
							}
							else {
							%>
							<option value="tetap_std">Penetapan Standar</option>
							<%
							}
							if(form_jenis_kegiatan!=null && form_jenis_kegiatan.equalsIgnoreCase("kendali_std")) {
								%>
							<option value="kendali_std" selected="selected">Pengendalian Standar</option>
								<%	
							}
							else {
							%>
							<option value="kendali_std">Pengendalian Standar</option>
							<%
							}
						}
						*/
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
				if(!Checker.isStringNullOrEmpty(form_nmm_kegiatan)) {
					%>
					<input type="text" name="nmm_kegiatan" minlength="5" style="text-align:center;width:100%;height:30px;padding:5px 0px" placeholder="isikan nama kegiatan" value="<%=form_nmm_kegiatan%>" required="required"/>
				<%	
				}
				else {
				%>
					<input type="text" name="nmm_kegiatan" minlength="5" style="text-align:center;width:100%;height:30px;padding:5px 0px" placeholder="isikan nama kegiatan" required="required"/>
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
				if(!Checker.isStringNullOrEmpty(form_nmm_kegiatan)) {
					%>
					<textarea name="tujuan_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required><%=form_tujuan_kegiatan %></textarea>
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
				if(!Checker.isStringNullOrEmpty(form_nmm_kegiatan)) {
					%>
					<textarea name="isi_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:3" placeholder="isikan keterangan singkat capaian/tujuan kegiatan" required><%=form_isi_kegiatan %></textarea>
				<%
				}
				else {
				%>
					<textarea name="isi_kegiatan" minlength="15" style="vertical-align:middle;width:100%;height:100px;border:none;rows:5" placeholder="isikan daftar acara kegiatan" required></textarea>
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
					<table class="table1" id="tabel" style="width:100%;border:none;height:30px;" >
						<tr>
							<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
								<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
									<thead>
										<tr class="statetablerow">
											<th colspan="3" style="height:30px;font-size:1.2em">Harap klik disini & kemudian centang pihak terkait</th>
										</tr>
									</thead>
									<tbody>	
										<tr class="statetablerow">	
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
			if(form_job_jawab!=null && form_job_jawab.length>0) {
				for(int i=0;i<form_job_jawab.length&&!match;i++) {
					if(jabatan.trim().toUpperCase().equalsIgnoreCase(form_job_jawab[i].trim().toUpperCase())) {
						match = true;
					}
				}
			}
	
			if(counter%3!=0) {
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
					<table class="table1" id="tabel" style="width:100%;border:none;height:30px;" >
						<tr>
							<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
								<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
									<thead>
										<tr class="statetablerow">
											<th colspan="3" style="height:30px;font-size:1.2em">Harap klik disini & kemudian centang pihak terkait</th>
										</tr>
									</thead>
									<tbody>	
										<tr class="statetablerow">	
						<%
		li = vListJabatanGabungan.listIterator();						
		counter = 0;
		while(li.hasNext()) {
			boolean match = false;
			counter++;
			String brs = (String)li.next();
			st = new StringTokenizer(brs,"`");
			String jabatan = st.nextToken();
			String sin = st.nextToken();
			if(form_job_target!=null && form_job_target.length>0) {
				for(int i=0;i<form_job_target.length&&!match;i++) {
					if(jabatan.trim().toUpperCase().equalsIgnoreCase(form_job_target[i].trim().toUpperCase())) {
						match = true;
					}
				}
			}
	
			if(counter%3!=0) {
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
					<table class="table1" id="tabel" style="width:100%;border:none;height:30px;" >
						<tr>
							<td style="paddig:0 5px 0 25px;width:80%;font-size:1.em;text-align:left;font-weight:bold;color:#369;background:white">
								<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:100%;background:white;">
									<thead>
										<tr class="statetablerow">
											<th colspan="3" style="height:30px;font-size:1.2em">Harap klik disini & kemudian centang pihak terkait</th>
										</tr>
									</thead>
									<tbody>	
										<tr class="statetablerow">	
						<%
		li = v_list_doc.listIterator();						
		counter = 0;
		while(li.hasNext()) {
			boolean match = false;
			counter++;
			String nmm_doc = (String)li.next();
			if(form_dok_mutu!=null && form_dok_mutu.length>0) {
				for(int i=0;i<form_dok_mutu.length&&!match;i++) {
					if(nmm_doc.trim().toUpperCase().equalsIgnoreCase(form_dok_mutu[i].trim().toUpperCase())) {
						match = true;
					}
				}
			}
	
			if(counter%3!=0) {
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
					if(form_started!=null && form_started.equalsIgnoreCase("false")) {
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
					if(form_started!=null && form_started.equalsIgnoreCase("true")) {
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
						<button style="padding: 5px 50px;font-size: 20px;">TAMBAH / UPDATE RENCANA KEGIATAN</button>
					</div>
					</section>
				</td>
			</tr>
		</thead>

		<%
	
		%>	
	</table>	
</form>	
<%
	
}

%>
<br>
	
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>