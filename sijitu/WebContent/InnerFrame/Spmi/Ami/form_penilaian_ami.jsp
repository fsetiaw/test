<!DOCTYPE html>
<head>

<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.ToolSpmi"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@page import="beans.dbase.spmi.*"%>
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
boolean editor = (Boolean) session.getAttribute("spmi_editor");
boolean team_spmi = (Boolean) session.getAttribute("team_spmi");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);

String status = (String)request.getParameter("status");

//String status = ts.getStatusKegiatanAmi(Integer.parseInt(id_ami));
//System.out.println("status penilaian="+status);
//dari foem
/*
String kode_activity = (String)request.getParameter("kode_activity");
String tgl_plan = (String)request.getParameter("tgl_plan");
String ketua_tim = (String)request.getParameter("ketua_tim");
String[]anggota_tim = (String[])request.getParameterValues("anggota_tim");
String[]cek = (String[])request.getParameterValues("cek");
*/
Vector v_QA = (Vector)session.getAttribute("v_QA");
session.removeAttribute("v_QA");
//id_master_std+"`"+ket_tipe_std



%>
<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

a.hover {
	text-decoration: none;
	background:none;
}

a.hover:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #fff;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: 1px solid #fff;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #fff; }

.table-noborder { border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: 1px solid #fff;padding: 2px }
/*.table:hover td { background:#82B0C3 }*/
</style>
<style>
.table1 {
border: 1px solid #fff;
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
	
	table.StateTable td.nopad{padding:0;;text-align:center;}
	table.StateTable thead tr:hover td { background:#82B0C3;;text-align:center; }
	
</style>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
<jsp:include page="menu_back_indek_pelaksanaan_ami.jsp" />
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
		
		<center>
		
		
<%
if(v_QA!=null && v_QA.size()>0) {
	//System.out.println("betul");
	try {
		//Collections.sort(v_QA);
	}
	catch(Exception e){}
	int norut=1;
	int norut_jawaban=0;
	ListIterator li = v_QA.listIterator();


		
	
	if(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println(brs);
		st = new StringTokenizer(brs,"~");
		String norut_question_v = st.nextToken();
		String kode_hist_v = st.nextToken();
		String id_master_std_v = st.nextToken();
		//System.out.println("id_master_std_v="+id_master_std_v);
		String id_std_v = st.nextToken();
		String id_tipe_std_v = st.nextToken();
		String id_std_isi_v = st.nextToken();
		String ket_master_std_v = st.nextToken();
		String ket_standar_v = st.nextToken();
		String tgl_sta_v = st.nextToken();
		String tgl_end_v = st.nextToken();
		String isi_std_v = st.nextToken();
		String butir_v = st.nextToken();
		String kdpst_v = st.nextToken();
		String rasionale_v = st.nextToken();
		String aktif_v = st.nextToken();
		String tgl_activated_v = st.nextToken();
		String tgl_deactivated_v = st.nextToken();
		String scope_v = st.nextToken();
		String tipe_awas_v = st.nextToken();
		String question_v = st.nextToken();
		String answer_v = st.nextToken();
		String bobot_v = st.nextToken();
%>
		<table style="border:none;background:#fff;width:100%">	
			<tr>
				<td style="text-align:left;padding:0 0 0 5px;width:80px">
					<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/form_urutan_qa_ami_v1.jsp?status=<%=status %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>">
						<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit_png.png" width="75" height="30">
					</a>
				</td>
				<td style="text-align:left;padding:0 0 0 5px;vertical-align:middle">
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/form_urutan_qa_ami_v1.jsp?status=<%=status %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>" class="hover">
					Ubah urutan pertanyaan 
					</a>
				</td>
				
				<td style="text-align:right;padding:0 0 0 5px;vertical-align:middle">
					<a class="img" href="#" onclick="if(confirm('Anda yakin mau me-reset susunan pertanyaan dari awal?'))(function(){
     var x = document.getElementById('wait');
     var y = document.getElementById('main');
     x.style.display = 'block';
     y.style.display = 'none';
     location.href='go.prepListQa?dari_update_susunan_question=bisDeleteSusunan&ket_master_std=<%=ket_master_std_v %>&status=<%=status%>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_master_std=<%=id_master_std_v %>&reset=true'
     })();return false;"
 					>
					Reset urutan pertanyaan&nbsp&nbsp
					</a>
				</td>
				<td style="text-align:right;padding:0 0 0 5px;width:80px">
					<a class="img" href="#" onclick="if(confirm('Anda yakin mau me-reset susunan pertanyaan dari awal?'))(function(){
     var x = document.getElementById('wait');
     var y = document.getElementById('main');
     x.style.display = 'block';
     y.style.display = 'none';
     location.href='go.prepListQa?dari_update_susunan_question=bisDeleteSusunan&ket_master_std=<%=ket_master_std_v %>&status=<%=status%>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&id_master_std=<%=id_master_std_v %>&reset=true'
     })();return false;"
 					>

						<img border="0" alt="RESET" src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/reset.png" width="75" height="30">
					</a>
				</td>
			</tr>
		</table>
					
		<table style="width:98%">
			<tr>
					<td style="padding:0 0 0 10px;text-align:left;width:10%;background:#369;color:#fff;font-weight:bold;font-size:1.3em">
					 No.
					</td>
					<td colspan="3" style="width:90%;text-align:center;background:#369;color:#fff;font-weight:bold;font-size:1.3em">
					 Pertanyaan & Jawaban & Bobot
					</td>
				
			</tr>
				<tr>
					<td colspan="1" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
						<%=norut++ %>
					</td>
<%
		if(Checker.isStringNullOrEmpty(question_v)) {
%>					
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>;color:red">
<%			
		}
		else {
%>					
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
<%
		}	
						out.print("[Pernyataan Standar] "+isi_std_v+"<br>[Q/A]  ");
						out.print(Checker.pnn_v1(question_v, "Pertanyaan belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
%>
					</td>
				</tr>	
<%
		if(!Checker.isStringNullOrEmpty(question_v)) {
		//jawaban
			norut_jawaban=1;//yg pertama kali
%>
				<tr>
					<td colspan="1" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
					&nbsp
					</td>
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 0;text-align:left;background:#fff">
					<table style="width:100%">
						<tr>
							<td colspan="2" style="border:1px solid #369;padding:0 0 0 10px;text-align:left;width:5%;background:<%=Constant.lightColorGrey() %>;color:#000;font-weight:bold;font-size:0.9em">
					 		Pilihan Jawaban
							</td>
							<td style="border:1px solid #369;width:25%;text-align:center;background:<%=Constant.lightColorGrey() %>;color:#000;font-weight:bold;font-size:0.9em">
							Bobot Penilaian
							</td>
						</tr>	
						<tr>
							<td style="border:1px solid #369;border-right:none;padding:0 0 0 10px;text-align:left;width:5%;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
					 			a.
							</td>
							<td style="border:1px solid #369;border-left:none;padding:0 0 0 0;width:70%;text-align:left;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
							<%
					 			out.print(Checker.pnn_v1(answer_v, "Pilihan Jawaban belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
							 %>
							</td>
							<td style="border:1px solid #369;width:25%;text-align:center;background:#fff;color:#000;font-weight:bold;font-size:0.9em;vertical-align:middle">
							<%
					 			out.print(Checker.pnn_v1(bobot_v, "N/A"));
							 %>
							</td>
						</tr>	
					<!--  /table>
					</td>
			</tr-->
				
<%			
		}

		while(li.hasNext()) {
			brs = (String)li.next();
			//System.out.println(brs);
			st = new StringTokenizer(brs,"~");
			String next_norut_question_v = st.nextToken();
			String next_kode_hist_v = st.nextToken();
			String next_id_master_std_v = st.nextToken();
			String next_id_std_v = st.nextToken();
			String next_id_tipe_std_v = st.nextToken();
			String next_id_std_isi_v = st.nextToken();
			String next_ket_master_std_v = st.nextToken();
			String next_ket_standar_v = st.nextToken();
			String next_tgl_sta_v = st.nextToken();
			String next_tgl_end_v = st.nextToken();
			String next_isi_std_v = st.nextToken();
			String next_butir_v = st.nextToken();
			String next_kdpst_v = st.nextToken();
			String next_rasionale_v = st.nextToken();
			String next_aktif_v = st.nextToken();
			String next_tgl_activated_v = st.nextToken();
			String next_tgl_deactivated_v = st.nextToken();
			String next_scope_v = st.nextToken();
			String next_tipe_awas_v = st.nextToken();
			String next_question_v = st.nextToken();
			String next_answer_v = st.nextToken();
			String next_bobot_v = st.nextToken();
			if(next_id_std_isi_v.equalsIgnoreCase(id_std_isi_v) && next_question_v.equalsIgnoreCase(question_v)) {
			//isi std sama	 & pertanyaan sama
				norut_jawaban++;//yg pertama kali
				String huruf = "";
				if(norut_jawaban==1) {
					huruf="a.";
				}
				else if(norut_jawaban==2) {
					huruf="b.";
				}
				else if(norut_jawaban==3) {
					huruf="c.";
				}
				else if(norut_jawaban==4) {
					huruf="d.";
				}
				else if(norut_jawaban==5) {
					huruf="e.";
				}
				%>
				
						<tr>
							<td style="border:1px solid #369;border-right:none;padding:0 0 0 10px;text-align:left;width:5%;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
					 			<%=huruf %>
							</td>
							<td style="border:1px solid #369;border-left:none;padding:0 0 0 0;width:70%;text-align:left;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
							<%
					 			out.print(Checker.pnn_v1(next_answer_v, "Pilihan Jawaban belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
							 %>
							</td>
							<td style="border:1px solid #369;width:25%;text-align:center;background:#fff;color:#000;font-weight:bold;font-size:0.9em;vertical-align:middle">
							<%
					 			out.print(Checker.pnn_v1(next_bobot_v, "N/A"));
							 %>
							</td>
						</tr>
				
<%	
			}
			else {
			//isi std baru
			//1. tutup table jawaban jika norut_jawaban > 0
				if(norut_jawaban>0) {
%>
					</table>
				</td>
			</tr>
<%	
			//2. reset norut -jawana
					norut_jawaban=0;
				}
				id_std_isi_v = new String(next_id_std_isi_v);
				question_v = new String(next_question_v);
	%>
				<tr>
					<td colspan="1" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
						<%=norut++ %>
					</td>
<%						
				if(Checker.isStringNullOrEmpty(next_question_v)) {
%>					
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>;color:red">
<%			
				}
				else {
%>					
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
<%
				}
				out.print("[Pernyataan Standar] "+next_isi_std_v+"<br>[Q/A]  ");
				out.print(Checker.pnn_v1(next_question_v, "Pertanyaan belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
							%>
					</td>
				</tr>	
	<%
				if(!Checker.isStringNullOrEmpty(next_question_v)) {
		//jawaban
					norut_jawaban=1;//yg pertama kali
%>
				<tr>
					<td colspan="1" style="border:1px solid #369;padding:0 0 0 15px;text-align:left;background:<%=Constant.lightColorBlu()%>">
					&nbsp
					</td>
					<td colspan="3" style="border:1px solid #369;padding:0 0 0 0;text-align:left;background:#fff">
					<table style="width:100%">
						<tr>
							<td colspan="2" style="border:1px solid #369;padding:0 0 0 10px;text-align:left;width:5%;background:<%=Constant.lightColorGrey() %>;color:#000;font-weight:bold;font-size:0.9em">
					 		Pilihan Jawaban
							</td>
							<td style="border:1px solid #369;width:25%;text-align:center;background:<%=Constant.lightColorGrey() %>;color:#000;font-weight:bold;font-size:0.9em">
							Bobot Penilaian
							</td>
						</tr>	
						<tr>
							<td style="border:1px solid #369;border-right:none;padding:0 0 0 10px;text-align:left;width:5%;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
					 			a.
							</td>
							<td style="border:1px solid #369;border-left:none;padding:0 0 0 0;width:70%;text-align:left;background:#fff;color:#000;font-weight:bold;font-size:0.9em">
							<%
					 			out.print(Checker.pnn_v1(next_answer_v, "Pilihan Jawaban belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
							 %>
							</td>
							<td style="border:1px solid #369;width:25%;text-align:center;background:#fff;color:#000;font-weight:bold;font-size:0.9em;vertical-align:middle">
							<%
					 			out.print(Checker.pnn_v1(next_bobot_v, "N/A"));
							 %>
							</td>
						</tr>	
					<!--  /table>
					</td>
			</tr-->
				
<%			
				}	
	
			}
			if(!li.hasNext()) {
				if(norut_jawaban>0) {
					%>
					</table>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<%
			}
		}//end while()
	}

}
else {
	//System.out.println("salah");
	out.print("<h1>BELUM ADA PERSIAPAN<br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']</h1>");
}
%>
	</center>
		<!-- Column 1 start -->
	</div>
</div>	
</div>	
</body>
</html>