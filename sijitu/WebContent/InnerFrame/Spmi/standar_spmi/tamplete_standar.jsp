<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.SearchManual"%>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@page import="beans.sistem.AskSystem"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
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

//System.out.println("tamplete std");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
session.removeAttribute("folder_asal");
session.removeAttribute("folder_asal1");
session.removeAttribute("hak_akses_asal");

Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
Boolean team_spmi = (Boolean) session.getAttribute("team_spmi");//asal jabatan ada mutu
boolean editor = spmi_editor.booleanValue();
boolean tim_spmi = team_spmi.booleanValue();
SearchStandarMutu ssm = new SearchStandarMutu();
ToolSpmi ts = new ToolSpmi();
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
String[]visi_misi_tujuan_nilai = Getter.getVisiMisiTujuanNilaiPt(Constants.getKdpti());

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
//System.out.println("spmi_editor="+spmi_editor);
Vector vListJabatanGabungan = Tool.combine2VectorSameStructureAndRemoveDuplicate(vListJabatan, vListJabatanGroup);
//Vector v_kendal= (Vector)request.getAttribute("v_kendal");
//request.removeAttribute("v");
StringTokenizer st = null;
/*
bila edit maka variable ini akan di overide di bawah (dalam body table)
*/
String[]job_rumus=request.getParameterValues("job_rumus"); 
String[]job_cek=request.getParameterValues("job_cek"); 
String[]job_stuju=request.getParameterValues("job_stuju"); 
String[]job_tetap=request.getParameterValues("job_tetap"); 
String[]job_kendali=request.getParameterValues("job_kendali"); 
String[]job_survey=request.getParameterValues("job_survey"); 


String mode=request.getParameter("mode");
String id_tipe_std=request.getParameter("id_tipe_std"); 
String at_menu=request.getParameter("at_menu"); 
String id_versi_std=request.getParameter("id_versi_std");//dipake untuk lihat prev ato next version
String id_master_std=request.getParameter("id_master_std"); 
String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp"); 
st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//System.out.println("id_versi_std="+id_versi_std);
//System.out.println("id_master_std="+id_master_std);

String tujuan = request.getParameter("tujuan"); 
String lingkup = request.getParameter("lingkup"); 
String prosedur = request.getParameter("prosedur"); 
String kuali = request.getParameter("kuali"); 
String doc = request.getParameter("doc"); 
String ref = request.getParameter("ref"); 
String definisi = request.getParameter("definisi");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);


//dari servlet updated
String updated = request.getParameter("updated");

Vector v_info_tamplete_std = ssm.getLatestInfoTampleteStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std));
//System.out.println("masuk");
if(id_versi_std!=null) {
	//System.out.println("kesini");
	v_info_tamplete_std = ssm.getLatestInfoTampleteStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std), Integer.parseInt(id_versi_std));
}
//System.out.println("id_versi_std="+id_versi_std);
int id_std = ssm.getIdStd(id_master_std, id_tipe_std);
//System.out.println("sampe sini");
String no_dok_spmi = ts.getPenomoranStandarMutuUmum(""+id_std);
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
table-layout: fixed;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>;word-wrap:break-word; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold;word-wrap:break-word; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px;word-wrap:break-word; }

</style>
<style>
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
table-layout: fixed;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>;word-wrap:break-word; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold;word-wrap:break-word; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px;word-wrap:break-word; }
.table2 tr:hover td { background:#82B0C3;word-wrap:break-word; }
</style>
<style>
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>;word-wrap:break-word; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold;word-wrap:break-word; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px;word-wrap:break-word; }


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
<jsp:include page="menu_std.jsp" />
<div class="colmask fullpage">
	<div class="col1">
	<!-- Column 1 start -->
	<br>
	<div style="text-align:center;padding:0 0 0 2px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
		</div>
		<br>
	<br>
	<%

Vector v_err_std = (Vector)request.getAttribute("v_err_std");
request.removeAttribute("v_err_man");
if(v_err_std!=null && v_err_std.size()>0) {
	ListIterator litmp = v_err_std.listIterator();
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

	
if(updated!=null) {
	String status_updated = "Data berhasil diupdated";
	int data_updated = Integer.parseInt(updated);
	if(data_updated==0) {
		status_updated = "Data gagal diupdate. Harap hubungi admin";
			%>
	<div style="font-weight:bold;color:red;text-align:center">
			<%			
	}
	else if(data_updated<2) {
		status_updated = "Hanya sebagian data berhasil diupdate. harrap hubungi admin";
		%>
	<div style="font-weight:bold;color:red;text-align:center">
		<%			
	}
	else {
			%>
	<div style="font-weight:bold;color:#369;text-align:center">
		<%			
	}
	%>
	<%=status_updated %>
	</div>
	<%	
}
	
if(v_info_tamplete_std!=null&&v_info_tamplete_std.size()>0) {
	ListIterator li = v_info_tamplete_std.listIterator();
		//versi
					//tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend+"~"+dok_terkait;
	String brs = (String)li.next();
	st = new StringTokenizer(brs,"~");
	String versi_std = st.nextToken();
	String tkn_jab_rumus_std = st.nextToken();
	String tkn_jab_cek_std = st.nextToken();
	String tkn_jab_stuju_std = st.nextToken();
	String tkn_jab_tetap_std = st.nextToken();
	String tkn_jab_kendali_std = st.nextToken();
	String tkn_jab_lap_std = st.nextToken();
	String rasionale_std = st.nextToken();
	String pihak_responsible = st.nextToken();
	String definisi_std = st.nextToken();
	String referensi_std = st.nextToken();
	String tglsta_std = st.nextToken();
	String tglend_std = st.nextToken();
	String dok_terkait_std = st.nextToken();
	//System.out.println("versi_id="+versi_std);
	//System.out.println("id_std="+id_std);
	boolean std_expired = ssm.isStandardExpired(Integer.parseInt(versi_std), id_std);
	boolean std_active = ssm.isStandardActivated(Integer.parseInt(versi_std), id_std);
	//System.out.println("std_expired="+std_expired);
	//System.out.println("std_active="+std_active);
	int prev_versi = ssm.getVersiDibawahnya(""+id_std, versi_std);
	int next_versi = ssm.getVersiDiatasnya(""+id_std, versi_std);
	if(next_versi<0) {
		id_versi_std=null; //berarti versi terkini, direset agar bisa tambah edit buti isi std
	}
	//System.out.println("prev_versi="+prev_versi);
	//System.out.println("next_versi="+next_versi);
	%>
	<table style="border:none;background:#fff;width:100%">	
		<tr>
			<td style="text-align:right;width:50%;padding:0 10px 0 0">
	<%
	if(prev_versi>0) {
		%>
		<a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_versi_std=<%=prev_versi %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std"><< Versi Sebelumnya</a>	
		<%	
	}
	%>		
			</td>
			<td style="width:50%;padding:0 0 0 10px">
			<%
	if(next_versi>0) {
		%>
		<a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_versi_std=<%=next_versi %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std">Versi Selanjutnya >></a>	
		<%	
	}
	%>	
			</td>
		</tr>
	</table>		
	<%
	if(editor && !std_expired) { 
	%>	
		<table style="border:none;background:#fff;width:100%">	
			<tr>
				<td style="text-align:left;padding:0 5px;width:10%">
					<a class="img" href="go.getListAllStd?mode=edit_std&id_versi=<%=versi_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std">
						<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30">
					</a>
				</td>
	<%
		if(editor) {
	%>
				<td style="text-align:center;padding:0 5px;width:10%">
					<jsp:include page="download_std.jsp" flush="true" />
				</td>
	<%		
		}
		if(!std_active&&!std_expired) {
		//blum pernah diaktifkan
	%>			
				<td style="text-align:right;padding:0 5px;width:10%">
					<a class="img" href="go.aktivasiStd_v1?toogle=activate&id_std=<%=id_std %>&id_versi=<%=versi_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengaktifkan standar ini? \nSyarat Aktivasi:\n1. Telah memiliki Manual Perencanaan,Pelaksanaan,Evaluasi,Pengendalian, dan Peningkatan yg aktif .\n2. Telah melakukan kegiatan pelaksanaan yang menghasilkan penetapan standar')) return true;return false; ">
					<img border="0" alt="PLAY" src="<%=Constants.getRootWeb() %>/images/play_blue.png" width="75" height="30"> <%=versi_std %>
					</a>
				</td>
	<%
		}
		else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
				%>			
				<td style="text-align:right;padding:0 5px;width:10%">
					<a class="img2" href="go.aktivasiStd_v1?toogle=deactivate&id_std=<%=id_std %>&id_versi=<%=versi_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengaktifkan standar ini? \nSyarat Aktivasi:\n1. Telah memiliki Manual Perencanaan,Pelaksanaan,Evaluasi,Pengendalian, dan Peningkatan yg aktif .\n2. Telah melakukan kegiatan pelaksanaan yang menghasilkan penetapan standar')) return true;return false; ">
					<img border="0" alt="STOP" src="<%=Constants.getRootWeb() %>/images/stop_red.png" width="30" height="30">
					</a>
				</td>
	<%				
		}
	%>	
			</tr>	
		</table>		
		<%
	}
	
	
		//if(editor && !ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		%>	
							
		<table class="table1" id="table1" style="width:100%">
		<thead>	
			<tr>
				<td rowspan="4" width="20%" style="text-align:center;vertical-align:middle;padding:5px 5px;" >
					<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/logo.png" alt="USG"  style="width:150px;height:150px;">
				</td>
				<th style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:40%;border:none;font-size:1.5em;font-weight:bold">
				<%=Constant.getNama_pt().toUpperCase() %> 
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
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		out.print("DRAFT ");
	}
	out.print(ssm.getNamaRumpunStandar(Integer.parseInt(id_master_std)));
				%>
				
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
	String tgl_tmp = ""+ssm.getTanggalStandarActivated(""+id_std,versi_std);
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
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
								<%=versi_std %>
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
	<%
	if(id_versi_std==null) {
	%>
	<h1 style="text-align:center">
	<%
	}
	else {
	%>
	<h1 style="text-align:center;color:red">
	<%	
	}
		if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
			out.print("DRAFT ");
		}
		out.print(ssm.getNamaStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std)));
		if(id_versi_std!=null) {
			out.print("<br>[EXPIRED / KADALUARSA]");	
		}
				%>
	</h1>
	<br><br>
	<table class="table1" id="table1" style="width:100%">
		<thead>	
			<tr>
				<th rowspan="2" style="text-align:center;vertical-align:middle;width:15%;font-size:1em;font-weight:bold">
					PROSES
				</th>
				<th colspan="3" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;width:70%;border:none;font-size:1em;font-weight:bold">
				PENGANGGUNG JAWAB
				</th>	
				<th rowspan="2" style="text-align:center;vertical-align:middle;width:15%;font-size:1em;font-weight:bold">
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
	String tkn_jab = null;
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitDraft(""+id_std, "perumusan");
	}
	else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitFinal(""+id_std, versi_std, "perumusan");
	}
	if(tkn_jab!=null) {
		String tmp = new String(tkn_jab);
		while(tmp.contains("`")) {
			tmp = tmp.replace("`", "<br>");
		}
		out.print(tmp);	
	}
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
	tgl_tmp = ""+ssm.getTglPenetapan(""+id_std, versi_std, "perumusan");
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
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
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitDraft(""+id_std, "pemeriksaan");
	}
	else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitFinal(""+id_std, versi_std, "pemeriksaan");
	}
	if(tkn_jab!=null) {
		String tmp = new String(tkn_jab);
		while(tmp.contains("`")) {
			tmp = tmp.replace("`", "<br>");
		}
		out.print(tmp);	
	}
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
	tgl_tmp = ""+ssm.getTglPenetapan(""+id_std, versi_std, "pemeriksaan");
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
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
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitDraft(""+id_std, "persetujuan");
	}
	else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitFinal(""+id_std, versi_std, "persetujuan");
	}
	if(tkn_jab!=null) {
		String tmp = new String(tkn_jab);
		while(tmp.contains("`")) {
			tmp = tmp.replace("`", "<br>");
		}
		out.print(tmp);	
	}
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
	tgl_tmp = ""+ssm.getTglPenetapan(""+id_std, versi_std, "persetujuan");
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
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
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitDraft(""+id_std, "penetapan");
	}
	else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitFinal(""+id_std, versi_std, "penetapan");
	}
	if(tkn_jab!=null) {
		String tmp = new String(tkn_jab);
		while(tmp.contains("`")) {
			tmp = tmp.replace("`", "<br>");
		}
		out.print(tmp);	
	}
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
	tgl_tmp = ""+ssm.getTglPenetapan(""+id_std, versi_std, "penetapan");
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
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
	if(!std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitDraft(""+id_std, "pengendalian");
	}
	else if(std_active&&!ssm.isStandardExpired(Integer.parseInt(versi_std), id_std)) {
		tkn_jab = ssm.getPihakTerkaitFinal(""+id_std, versi_std, "pengendalian");
	}
	if(tkn_jab!=null) {
		String tmp = new String(tkn_jab);
		while(tmp.contains("`")) {
			tmp = tmp.replace("`", "<br>");
		}
		out.print(tmp);	
	}
				%>
				</td>
				<td colspan="1" style="background:#fff;text-align:left;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%="" %>
				</td>
				<td colspan="1" style="text-align:center;height:30px;vertical-align:middle;padding:5px 5px;border:thin solid #369;font-size:0.9em;font-weight:bold">
				<%
				/*
				tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "pengendalian","pelaksanaan");
				//System.out.println("tgl_tmp2="+tgl_tmp);
				out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
				*/
				%>
				<%
	tgl_tmp = ""+ssm.getTanggalStandarActivated(""+id_std,versi_std);
	out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));
				%>
				</td>
			</tr>
		</thead>
	</table>	
	<br><br>
	<table class="table1" id="table1" style="width:100%">
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
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
					<%
	if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						
		if(visi_misi_tujuan_nilai[0]!=null) {
			while(visi_misi_tujuan_nilai[0].contains("\n")) {
				visi_misi_tujuan_nilai[0] = visi_misi_tujuan_nilai[0].replace("\n", "<br>");
			}
							//out.print(visi_misi_tujuan_nilai[0]);
		}
						
	}
					%>
					<%=Checker.pnn_v1(visi_misi_tujuan_nilai[0], "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.2. Misi Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
					<%
	if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
		if(visi_misi_tujuan_nilai[1]!=null) {
			while(visi_misi_tujuan_nilai[1].contains("\n")) {
				visi_misi_tujuan_nilai[1] = visi_misi_tujuan_nilai[1].replace("\n", "<br>");
			}
							//out.print(visi_misi_tujuan_nilai[1]);	
		}
						
	}
					%>
					<%=Checker.pnn_v1(visi_misi_tujuan_nilai[1], "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.3. Tujuan Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
					<%
	if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
		if(visi_misi_tujuan_nilai[2]!=null) {
			while(visi_misi_tujuan_nilai[2].contains("\n")) {
				visi_misi_tujuan_nilai[2] = visi_misi_tujuan_nilai[2].replace("\n", "<br>");
			}
							//out.print(visi_misi_tujuan_nilai[2]);	
		}
						
	}
					%>
					<%=Checker.pnn_v1(visi_misi_tujuan_nilai[2], "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.4. Nilai-nilai Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
					<%
	if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
		if(visi_misi_tujuan_nilai[3]!=null) {
			while(visi_misi_tujuan_nilai[3].contains("\n")) {
				visi_misi_tujuan_nilai[3] = visi_misi_tujuan_nilai[3].replace("\n", "<br>");
			}
			out.print(visi_misi_tujuan_nilai[3]);	
		}
						
	}
					%>
					<%=Checker.pnn_v1(visi_misi_tujuan_nilai[3], "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					2. RASIONALE PENETAPAN <%=ssm.getNamaStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std)) %>
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
	if(rasionale_std!=null) {
		while(rasionale_std.contains("\n")) {
			rasionale_std = rasionale_std.replace("\n", "<br>");
		}	
	}
				%>
					<%=Checker.pnn_v1(rasionale_std, "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					3. PIHAK YANG BERTANGGUNGJAWAB UNTUK MENCAPAI ISI STANDAR
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
	if(pihak_responsible!=null) {
		while(pihak_responsible.contains("\n")) {
			pihak_responsible = pihak_responsible.replace("\n", "<br>");
		}
					//out.print(pihak_responsible);	
	}
				%>
				<%=Checker.pnn_v1(pihak_responsible, "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>	
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					4. DEFINISI
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
	if(definisi_std!=null) {
		while(definisi_std.contains("\n")) {
			definisi_std = Tool.cetakWebFormat(definisi_std, "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]");
		}
					//out.print(definisi_std);	
	}
				%>
				<%=definisi_std %>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					5. PERNYATAAN ISI STANDAR
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
				boolean active_only=true;
	Vector v_list_std_isi = ssm.getInfoTampletStandar(editor,kdpst, id_std,active_only);
	Vector v_list_strategi = new Vector();
	Vector v_list_indikator = new Vector();
				
	ListIterator lis=v_list_strategi.listIterator();
	ListIterator lin=v_list_indikator.listIterator();
				
	if(v_list_std_isi!=null) {
				%>
					<table class="table2" style="width:100%;border:none;background:#fff">
				<%	
		int norut=1;
		li = v_list_std_isi.listIterator();
					//tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
		while(li.hasNext()) {
			brs = (String)li.next();
			//System.out.println("brs="+brs);
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
			String strategi = st.nextToken();
			lin.add(indikator_rs+"~"+periode_start_rs+"~"+unit_used_by_periode_start_rs+"~"+besaran_interval_per_period_rs+"~"+unit_used_byTarget_rs);
			if(!Checker.isStringNullOrEmpty(strategi)) {
				lis.add(strategi);
			}
			else {
				lis.add("strategi pelaksanaan butir ("+norut+") harap diisi");
			}
			if(Checker.isStringNullOrEmpty(id_versi_std)) {
				%>
						<tr onclick="window.location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar_single.jsp?id_std_isi=<%=id_std_isi_rs %>&id_versi=<%=id_versi_rs %>&id_tipe_std=<%=id_tipe_rs %>&id_master_std=<%=id_master_rs %>&id_std=<%=id_std_rs %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=<%=mode%>'">
				<%
			}
			else {
			%>
						<tr>
			<%	
			}
				%>		
							<td style="border:none;width:2%;text-align:left;padding:5px 0 5px 5px">
								<%=norut++ %>.
							</td>
							<td style="border:none;width:98%;text-align:left;padding:5px 2px 5px 5px">
								<%
			if(isi_std_rs!=null) {
				while(isi_std_rs.contains("\n")) {
					isi_std_rs = isi_std_rs.replace("\n", "<br>");
				}
				while(isi_std_rs.contains(" ")) {
					isi_std_rs = isi_std_rs.replace(" ", "&nbsp");
				}
			
				out.print(isi_std_rs);
				if(cakupan_rs.equalsIgnoreCase("univ")) {
					out.print(" (Perguruan Tinggi)");
				}
				else if(cakupan_rs.equalsIgnoreCase("prodi")) {
					if(!Checker.isStringNullOrEmpty(kdpst_rs)) {
						out.print(" ("+Converter.getNamaKdpstDanJenjang(kdpst_rs) +")");
					}
					else {
						out.print(" (semua Prodi)");
					}	
				}
				else {
					//lembaga
					out.print(" (Badan/Biro/Unit Terkait)");
				}

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
		out.print("N/A");
	}
	if((editor||tim_spmi)&&Checker.isStringNullOrEmpty(id_versi_std)) {
					%>	
						<table style="border:none;width:100%">
						<tr>
								<td colspan="2" style="border:none;width:2%;text-align:center;padding:5px 0 5px 5px">
									<a class="img" href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/add_std_isi.jsp?id_versi=<%=versi_std %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" style="vertical-align:middle;valign:middle">
										<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Pernyataan Isi Standar 
									</a>	
								</td>
							</tr>
						</table>
					<%	
	}
				%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					6. INDIKATOR KETERCAPAIAN STANDAR
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 0">
				
				<%
	int norut=0;
	if(v_list_std_isi!=null && v_list_std_isi.size()>0) {
		li = v_list_std_isi.listIterator();
				//tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
		while(li.hasNext()) {
			norut++;
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
			String strategi = st.nextToken();
				%>	
				<table style="width:100%">
					<tr>
						<th style="width:5%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						NO
						</th>
						<th style="width:45%;text-align:left;font-weight:bold;background:<%=Constant.lightColorBlu() %>;padding:0 0 0 5px;border-top:1px solid <%=Constant.lightColorBlu() %>">
						INDIKATOR CAPAIAN
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						<%
			if(unit_used_by_periode_start_rs.contains("sms") && periode_start_rs.length()==4) {
				periode_start_rs=periode_start_rs+"1";
			}
			else if(unit_used_by_periode_start_rs.contains("thn") && periode_start_rs.length()==5) {
				periode_start_rs=periode_start_rs.substring(0,4);
			}
						//out.print(periode_start_rs);
						%>
						<%=Checker.pnn_v1(periode_start_rs, "PERIODE") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						<%
			String next_periode = new String(periode_start_rs);
			next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start_rs, Integer.parseInt(besaran_interval_per_period_rs));
						//out.print(next_periode);
						%>
						<%=Checker.pnn_v1(next_periode, "PERIODE") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						<%
			//next_periode = new String(periode_start_rs);
			next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start_rs, Integer.parseInt(besaran_interval_per_period_rs));
						//out.print(next_periode);
						 %>
						 <%=Checker.pnn_v1(next_periode, "PERIODE") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						<%
			//next_periode = new String(periode_start_rs);
			next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start_rs, Integer.parseInt(besaran_interval_per_period_rs));
						//out.print(next_periode);
						 %>
						 <%=Checker.pnn_v1(next_periode, "PERIODE") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;background:<%=Constant.lightColorBlu() %>;border-top:1px solid <%=Constant.lightColorBlu() %>">
						<%
			//next_periode = new String(periode_start_rs);
			next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start_rs, Integer.parseInt(besaran_interval_per_period_rs));
						//out.print(next_periode);
						 %>
						 <%=Checker.pnn_v1(next_periode, "PERIODE") %>
						</th>
					</tr>
					<tr>
						<th style="width:5%;text-align:center;font-weight:bold;">
						<%=norut %>
						</th>
						<th style="width:45%;text-align:left;font-weight:bold;;padding:0 0 0 5px">
						<%=Checker.pnn_web(indikator_rs, "Indikator capaian harap dilengkapi")  %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;">
						<%=Checker.pnn_v1(target1_rs, "N/A") %>&nbsp<%=Checker.pnn_v1(unit_used_byTarget_rs, "") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;">
						<%=Checker.pnn_v1(target2_rs, "N/A") %>&nbsp<%=Checker.pnn_v1(unit_used_byTarget_rs, "") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;">
						<%=Checker.pnn_v1(target3_rs, "N/A") %>&nbsp<%=Checker.pnn_v1(unit_used_byTarget_rs, "") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;">
						<%=Checker.pnn_v1(target4_rs, "N/A") %>&nbsp<%=Checker.pnn_v1(unit_used_byTarget_rs, "") %>
						</th>
						<th style="width:10%;text-align:center;font-weight:bold;">
						<%=Checker.pnn_v1(target5_rs, "N/A") %>&nbsp<%=Checker.pnn_v1(unit_used_byTarget_rs, "") %>
						</th>
					</tr>
				</table>
				<br>
				<%
					
		}
	}
	else {
				%>	
				<div style="padding:0 0 0 20px">
				<%	
		out.print("N/A");
				%>
				</div>
				<%
	}
				%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					7. STRATEGI PELAKSANAAN STANDAR
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<table style="width:100%;border:none;background:#fff">
				<%
	if(v_list_strategi!=null && v_list_strategi.size()>0) {
		lis=v_list_strategi.listIterator();
		norut=1;
		while(lis.hasNext()) {
			brs = (String)lis.next();
				%>
					<tr>
						<td style="border:none;width:2%;text-align:left;padding:5px 0 5px 5px">
								<%=norut++ %>.
						</td>
						<td style="border:none;width:98%;text-align:left;padding:5px 2px 5px 5px">
								<%=Checker.pnn_web(brs, "N/A")  %>
						</td>
					</tr>	
				<%
		}
	}
	else {
		out.print("N/A");
	}
				%>
				</table>
				</td>
			</tr>	
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					8. DOKUMEN TERKAIT
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
	boolean tmp_selalu_kosong=true;
	if(dok_terkait_std!=null) {
				%>
					<table class="table2" style="width:100%;border:none;background:#fff">
				<%	
					
		int no=1;
		st = new StringTokenizer(dok_terkait_std,",");
		while(st.hasMoreTokens()) {
			String tmp = st.nextToken();
			if(!Checker.isStringNullOrEmpty(tmp)) {
				tmp_selalu_kosong=false;
							%>
						<tr onclick="window.open('go.prepDokumenMutu?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&nm_doc=<%=tmp.toUpperCase()%>','popup','width=850,height=450')">
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
					9. REFERENSI
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 20px">
				<%
	if(referensi_std!=null) {
		referensi_std=Tool.cetakWebFormat(referensi_std, "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]");
					//out.print();	
	}
				%>
				 <%=Checker.pnn_v1(referensi_std, "N/A  [Untuk merubah, silahkan klik tombol EDIT pada bagian atas laman ini]") %>
				</td>
			</tr>	
		</thead>
	</table>
<%
}
		

%>			
		<br>

	
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
