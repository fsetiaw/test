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
<%@page import="beans.dbase.spmi.*"%>
<%@page import="beans.dbase.spmi.riwayat.ami.SearchAmi"%>
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

String id_ami = (String)session.getAttribute("id_ami");;
String kode_activity = (String)session.getAttribute("kode_activity");
String tgl_plan = (String)session.getAttribute("tgl_plan");
String ketua_tim = (String)session.getAttribute("ketua_tim");
String anggota_tim = (String)session.getAttribute("anggota_tim");
String id_cakupan_std = (String)session.getAttribute("id_cakupan_std");
String ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");
String tgl_ril = (String)session.getAttribute("tgl_ril");
String tgl_ril_done = (String)session.getAttribute("tgl_ril_done");
String id_master_std = (String)session.getAttribute("id_master_std");
String ket_master_std = (String)session.getAttribute("ket_master_std");
String status = (String)request.getParameter("status");
//System.out.println("statusnya="+status);
//dari foem
/*
String kode_activity = (String)request.getParameter("kode_activity");
String tgl_plan = (String)request.getParameter("tgl_plan");
String ketua_tim = (String)request.getParameter("ketua_tim");
String[]anggota_tim = (String[])request.getParameterValues("anggota_tim");
String[]cek = (String[])request.getParameterValues("cek");
*/
//System.out.println("id_master_std=a="+id_master_std);
//System.out.println("kdspt=a="+kdpst);
SearchAmi sa = new SearchAmi();
//Vector v_QA = sa.getListUrutanPertanyaanAmi(Integer.parseInt(id_master_std),1);
Vector v_QA = sa.previewAmiQandA(Integer.parseInt(id_master_std), false, kdpst);
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
	int tot_question = v_QA.size();
	//System.out.println("tot_question="+tot_question);
	int norut=1;
	int norut_jawaban=0;
	ListIterator li = v_QA.listIterator();


		
	
	if(li.hasNext()) {
		String brs = (String)li.next();
		//String tmp = 
		st = new StringTokenizer(brs,"~");
		//norut+"~"+
		String norut_question_v = st.nextToken();
		//kode+"~"+
		String kode_hist_v = st.nextToken();
		//id_master_std+"~"+
		String id_master_std_v = st.nextToken();
		//System.out.println("id_master_std_v="+id_master_std_v);
		//id_std+"~"+
		String id_std_v = st.nextToken();
		//id_tipe_std+"~"+
		String id_tipe_std_v = st.nextToken();
		//id_std_isi+"~"+
		String id_std_isi_v = st.nextToken();
		//ket_master_std+"~"+
		String ket_master_std_v = st.nextToken();
		//ket_standar+"~"+
		String ket_standar_v = st.nextToken();
		//tgl_sta+"~"+
		String tgl_sta_v = st.nextToken();
		//tgl_end+"~"+
		String tgl_end_v = st.nextToken();
		//isi_std+"~"+
		String isi_std_v = st.nextToken();
		//butir+"~"+
		String butir_v = st.nextToken();
		//kdpst+"~"+
		String kdpst_v = st.nextToken();
		//rasionale+"~"+
		String rasionale_v = st.nextToken();
		//aktif+"~"+
		String aktif_v = st.nextToken();
		//tgl_activated+"~"+
		String tgl_activated_v = st.nextToken();
		//tgl_deactivated+"~"+
		String tgl_deactivated_v = st.nextToken();
		//scope+"~"+
		String scope_v = st.nextToken();
		//tipe_awas+"~"+
		String tipe_awas_v = st.nextToken();
		//question+"~"+
		String question_v = st.nextToken();
		//answer+"~"+
		String answer_v = st.nextToken();
		//bobot+"~"+
		String bobot_v = st.nextToken();
		//id_question+"~"+
		String id_question_v = st.nextToken();
		//norut_question
		
		if(Checker.isStringNullOrEmpty(norut_question_v)) {
			norut_question_v="1";
		}
%>
		
		<br>
		<form action="go.prepListQa" method="post">			
		<input type="hidden" name="dari_update_susunan_question" value="true" />
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />	
			<input type="hidden" name="id_ami" value="<%=id_ami %>"/>
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
			<input type="hidden" name="kode_activity" value="<%=kode_activity %>"/>
			<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
			<input type="hidden" name="ketua_tim" value="<%=ketua_tim %>"/>
			<input type="hidden" name="anggota_tim" value="<%=anggota_tim %>"/>
			<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
			<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
			<input type="hidden" name="id_master_std" value="<%=id_master_std %>"/>
			<input type="hidden" name="ket_master_std" value="<%=ket_master_std %>"/>	
			<input type="hidden" name="status" value="<%=status%>"/>
		<table style="width:98%">
			<tr>
					<td style="text-align:center;width:15%;background:#369;color:#fff;font-weight:bold;font-size:1.3em">
					 No Urut.
					</td>
					<td colspan="3" style="width:85%;text-align:center;background:#369;color:#fff;font-weight:bold;font-size:1.3em">
					 Silahkan Ubah Urutan Pertanyaan dgn Merubah 'No Urut'
					</td>
				
			</tr>
				<tr>
					<td colspan="1" style="border:1px solid #369;text-align:left;background:<%=Constant.lightColorBlu()%>">
					<select name="norut_question" style="width:100%;border:none;height:30px;text-align-last:center">
<%
		for(int i=1;i<=tot_question;i++) {
			if(i==Integer.parseInt(norut_question_v)) {
%>
						<option value="<%=id_question_v %>`<%=id_std_isi_v %>`<%=i %>" selected="selected"><%=i %></option>
<%				
			}
			else {
				%>
						<option value="<%=id_question_v %>`<%=id_std_isi_v %>`<%=i %>"><%=i %></option>
<%					
			}
		}
%>					
					</select>	
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
						out.print(Checker.pnn_v1(question_v, "Pertanyaan belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
%>
					</td>
				</tr>	
<%
		
		while(li.hasNext()) {
			brs = (String)li.next();
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
			String next_id_question_v = st.nextToken();
			
			
			if(true) {
			//isi std baru
			//1. tutup table jawaban jika norut_jawaban > 0
				
				id_std_isi_v = new String(next_id_std_isi_v);
				question_v = new String(next_question_v);
				if(id_question_v.equalsIgnoreCase(next_id_question_v)) {
					//ignore
					//System.out.println("ignore2");
				}
				else {
					if(Checker.isStringNullOrEmpty(next_norut_question_v)) {
						norut_question_v = ""+(Integer.parseInt(norut_question_v)+1);
						next_norut_question_v=new String(norut_question_v);
					}
	%>
				<tr>
					<td colspan="1" style="border:1px solid #369;text-align:left;background:<%=Constant.lightColorBlu()%>">
					<select name="norut_question" style="width:100%;border:none;height:30px;text-align-last:center">
<%
					for(int i=1;i<=tot_question;i++) {
						if(i==Integer.parseInt(next_norut_question_v)) {
%>
						<option value="<%=next_id_question_v %>`<%=next_id_std_isi_v %>`<%=i %>" selected="selected"><%=i %></option>
<%				
						}
						else {
				%>
						<option value="<%=next_id_question_v %>`<%=next_id_std_isi_v %>`<%=i %>"><%=i %></option>
<%					
						}
					}
%>					
					</select>	
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
					out.print(Checker.pnn_v1(next_question_v, "Pertanyaan belum disiapkan <br>[harap disiapkan melalui menu pada 'Manual Evaluasi Pelaksanaan Standar']"));
							%>
					</td>
				</tr>	
	<%
					id_question_v = new String(next_id_question_v);
				}
			}
			if(!li.hasNext()) {
				
			%>
			<tr>
				<td colspan="4" style="border:1px solid #369;background:<%=Constant.lightColorBlu()%>;padding:5px 5px">
					<section class="gradient">
					<div style="text-align:center">
						<button name="btn" value="do" style="padding: 5px 50px;font-size: 15px;">UPDATE SUSUNAN PERTANYAAN</button>
					</div>
					</section>
				</td>	
			</tr>
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
</body>
</html>