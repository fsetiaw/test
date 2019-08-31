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
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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
String nm_pt=Constant.getNama_pt();
String nm_yys=Constant.getNama_yys();
//System.out.println("tamplete std");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
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
String id_versi=request.getParameter("id_versi"); 

String id_master_std=request.getParameter("id_master_std"); 
String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp"); 
st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//System.out.println("id_tipe_std="+id_tipe_std);
//System.out.println("id_master_std="+id_master_std);

String tujuan = request.getParameter("tujuan"); 
String lingkup = request.getParameter("lingkup"); 
String prosedur = request.getParameter("prosedur"); 
String kuali = request.getParameter("kuali"); 
String doc = request.getParameter("doc"); 
String ref = request.getParameter("ref"); 
String definisi = request.getParameter("definisi");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);

Vector v_info_tamplete_std = ssm.getLatestInfoTampleteStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std));
int id_std = ssm.getIdStd(id_master_std, id_tipe_std);

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
<div id="header">
	<ul>
		<li><a href="go.getListAllStd?mode=start&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
</div>
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
		
	<%
	
	if(v_info_tamplete_std!=null&&v_info_tamplete_std.size()>0) {
		ListIterator li = v_info_tamplete_std.listIterator();
		//versi+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend+"~"+dok_terkait;
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
		String pihak_terkait_capaian_std = st.nextToken();
		String definisi_std = st.nextToken();
		String referensi_std = st.nextToken();
		String tglsta_std = st.nextToken();
		String tglend_std = st.nextToken();
		String dok_terkait_std = st.nextToken();
%>
	<form action="go.updStdMutu" method="post">
		<input type="hidden" name="mode" value="start"/>
		<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std%>"/>
		<input type="hidden" name="id_master_std" value="<%=id_master_std%>"/>
		<input type="hidden" name="id_versi" value="<%=id_versi%>"/>
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
		<table style="border:none;background:#fff;width:100%">
			<tr>
				<td style="text-align:center;padding:0 5px;width:100%;font-size:2.5em;color:#369">
					EDIT FORM <%=ssm.getNamaStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std)) %>
				</td>
				
			</tr>
		</table>
<%
Vector v_err = (Vector)session.getAttribute("v_err");
	session.removeAttribute("v_err");
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
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
%>
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
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0px 0px 0px 5px">
					<%
					String tmp="Visi perguruan tinggi harap diisi";
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						
						if(visi_misi_tujuan_nilai[0]!=null) {
							//while(visi_misi_tujuan_nilai[0].contains("\n")) {
							//	visi_misi_tujuan_nilai[0] = visi_misi_tujuan_nilai[0].replace("\n", "<br>");
							//}
							tmp = new String(visi_misi_tujuan_nilai[0]);
						}
						
					}
					%>
					<textarea name="visi_pt" style="width:100%;height:70px;row:5;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.2. Misi Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
					<%
					tmp = "Misi  perguruan tinggi harap diisi";
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[1]!=null) {
							//while(visi_misi_tujuan_nilai[1].contains("\n")) {
							//	visi_misi_tujuan_nilai[1] = visi_misi_tujuan_nilai[1].replace("\n", "<br>");
							//}
							tmp = visi_misi_tujuan_nilai[1];
							//out.print(visi_misi_tujuan_nilai[1]);	
						}
						
					}
					%>
					<textarea name="misi_pt" style="width:100%;height:200px;row:5;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.3. Tujuan Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
					<%
					tmp = "Tujuan  perguruan tinggi harap diisi";
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[2]!=null) {
							//while(visi_misi_tujuan_nilai[2].contains("\n")) {
							//	visi_misi_tujuan_nilai[2] = visi_misi_tujuan_nilai[2].replace("\n", "<br>");
							//}
							tmp = new String(visi_misi_tujuan_nilai[2]);	
						}
						
					}
					%>
					<textarea name="tujuan_pt" style="width:100%;height:200px;row:5;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;vertical-align:middle;width:100%;font-size:1.2em;font-weight:bold;padding:5px 5px 5px 20px">
					1.4. Nilai-nilai Perguruan Tinggi
				</td>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
					<%
					tmp = "Nilai-nilai  perguruan tinggi harap diisi";
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						if(visi_misi_tujuan_nilai[3]!=null) {
							//while(visi_misi_tujuan_nilai[3].contains("\n")) {
							//	visi_misi_tujuan_nilai[3] = visi_misi_tujuan_nilai[3].replace("\n", "<br>");
							//}
							tmp = new String(visi_misi_tujuan_nilai[3]);
							//out.print(visi_misi_tujuan_nilai[3]);	
						}
						
					}
					%>
					<textarea name="nilai_pt" style="width:100%;height:200px;row:5;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					2. RASIONALE PENETAPAN <%=ssm.getNamaStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std)) %>
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
				<%
				tmp = "2.1. Rasionale Eksternal.\n &nbsp&nbsp&nbsp Sesuai dengan amanah UU no.12 tahun 2012 tentang pendidikan tinggi bagian III yang menyatakan setiap perguruan tinggi wajib merencanakan, melaksanakan, mengevaluasi dan meningkatkan standar serta dalam rangka mewujudakan amanah Permenristekdikti no 44 tahun 2015 pasal (...) sampai (...) khususnya pasal (...) ayat 1  maka "+nm_pt+" wajib merancang, merumuskan, menyusun, menetapkan, melaksanakan, mengevaluasi dan mengendalikan Standar pengelolaan penelitian yang merupakan kriteria minimal tentang perencanaan, pelaksanaan, pengendalian, pemantauan dan evaluasi, serta pelaporan kegiatan penelitian untuk menyelenggarakan pendidikan dalam rangka pemenuhan capaian pembelajaran lulusan.\n\n2.2. Rasionale Internal.\n &nbsp&nbsp&nbsp Dalam rangka mewujudkan visi misi dan tujuan dan sasaran "+nm_pt+" sebagaimana diamanahkan Statuta "+nm_pt+" di Bab (...) pasal (...) ayat (...) serta memastikan ketercapaian sasaran strategi di bidang penelitian maka "+nm_pt+" menetapkan dan melaksanakan standar pengeloalaan penelitian. (tambahkan cakupan bila perlu) ";
				if(!Checker.isStringNullOrEmpty(rasionale_std)) {
					tmp = new String(rasionale_std);
					//while(tmp.contains("\n")) {
					//	tmp = tmp.replace("\n", "<br>");
					//}
				}
				%>
					<textarea name="rasionale_std" style="width:100%;height:200px;row:10;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					3. PIHAK YANG BERTANGGUNGJAWAB UNTUK MENCAPAI ISI STANDAR
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
				<%
				tmp = "3.1. Perumusan\n &nbsp&nbsp&nbsp Perumusan Standar Pengelolaan Penelitian dilakukan oleh Tim Ad Hoc, dibawah tanggung jawab kepala LPM setelah memperoleh penetapan Rektor. \n3.2. Penetapan\n &nbsp&nbsp&nbsp Penetapan Standar Pengelolaan Penelitian dilakukan oleh "+nm_yys+" setelah memeperoleh pertimbangan, rekomendasi dan persetujuan senat dan ditetapkan oleh surat keputusan rektor \n3.3. Pelaksanaan\n &nbsp&nbsp&nbsp Pelaksanaan standar pengelolaan penelitian dilakukan oleh  ketua program studi dan warek I \n3.4. Evaluasi Pelaksanaan \n &nbsp&nbsp&nbsp Evaluasi Pelaksaan Standar Pengelolaan Penelitian dilakukan melalui kegiatan audit mutu internal yg dilakukan oleh tim auditor dibawah koordinasi kepala  LPM \n3.5. Pengendalian Pelaksanaan \n &nbsp&nbsp&nbsp Pengendalian Pelaksanaan Standar Pengelolaan Penelitian dilakukan oleh ketua program studi dan warek 1 dibawah koordinasi ketua tim auditor dan LPM.\n3.6. Peningkatan Standar\n &nbsp&nbsp&nbsp Peningkatan Standar Pengelolaan Penelitian dilakukan oleh pimpinan "+nm_pt+" dibawah koordinasi kepala LPM ";
				if(!Checker.isStringNullOrEmpty(pihak_terkait_capaian_std)) {
					tmp = new String(pihak_terkait_capaian_std);
					//while(tmp.contains("\n")) {
					//	tmp = tmp.replace("\n", "<br>");
					//}
				}
				%>
					<textarea name="pihak_terkait_std"  style="width:100%;height:300px;row:10;border:none" required><%=tmp %></textarea>
				</td>
			</tr>	
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					4. DEFINISI
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
					<%
					tmp ="Tidak ada definisi teknis yang terkait dengan isi standar";
					if(!Checker.isStringNullOrEmpty(definisi_std)) {
						tmp = new String(definisi_std);
						//while(tmp.contains("\n")) {
						//	tmp = tmp.replace("\n", "<br>");
						//}
					}	
					%>
					<textarea name="definisi_std" style="width:100%;height:250px;row:15;border:none"><%=tmp %></textarea>
				</td>
			</tr>
			<tr>

				<%
					String info="Dokumen Terkait:\n&nbsp &nbsp Seluruh dokumen yang dibutuhkan selama siklus mutu. Dimulai dari dokumen terkait dalam Penetapan, Pelaksanaa, Evaluasi dan Pengendalian serta Peningkatan Standar Mutu";
				%>
					<td style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#369;font-weight:bold;">
						<table width="100%" style="border:none">
							<tr>
								<td width="75%" style="border-color:#369;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;font-size:1.6em" title="<%=info%>">5. DOKUMEN TERKAIT</td>
								<td width="20%" style="padding:0 5px 0 0;text-align:right;border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold" >
								Tambah Dokumen Terkait
								</td>
								<td width="5%" style="border-color:#369;vertical-align: middle;background: <%=Constant.darkColorBlu()%>;color:#369;font-weight:bold" >			
									<section class="gradient" style="text-align:right;vertical-align: top;">
		            					<button style="font-size: 50px;" value="dok_terkait" name="tombol">+</button>
	        						</section>
	        					</td>
							</tr>
						</table>
					</td>
							
				</tr>	
				<tr>
					<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/selec_doc_std.jsp">
						<jsp:param name="tkn_doc" value="<%=dok_terkait_std %>"/>
						</jsp:include>
					</td>	
				</tr>
			</tr>	
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					6. REFERENSI
				</th>
			</tr>
			<tr>
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:0 0 0 5px">
					<%
					tmp ="Referensi Internal \n1. Statuta "+nm_pt+"\n2. Renstra "+nm_pt+"\n\nReferensi Eksternal \n1. Permenristekdikti no 44 tahun 2015";
					if(!Checker.isStringNullOrEmpty(definisi_std)) {
						tmp = new String(definisi_std);
						//while(tmp.contains("\n")) {
						//	tmp = tmp.replace("\n", "<br>");
						//}
					}	
					%>
					<textarea name="ref_std" style="width:100%;height:150px;row:10;border:none" required><%=tmp %></textarea>
				</td>
			</tr>
			<tr>
				<td style="padding:5px 0px">
						<section class="gradient" style="text-align:center">
	            			<button style="padding: 5px 50px;font-size: 20px;">UPDATE STANDAR</button>
        				</section>
				</td>		
			</tr>	
		</thead>
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
