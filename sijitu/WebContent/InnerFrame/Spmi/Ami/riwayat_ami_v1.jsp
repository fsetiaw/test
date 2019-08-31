<!DOCTYPE html>
<head>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.ToolSpmi"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>
<%@page import="beans.dbase.spmi.riwayat.ami.*"%>
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
//----kegiatan  param audit mutu internal----------------
session.removeAttribute("id_ami");;
session.removeAttribute("kode_activity");
session.removeAttribute("tgl_plan");
session.removeAttribute("ketua_tim");
session.removeAttribute("anggota_tim");
session.removeAttribute("id_cakupan_std");
session.removeAttribute("ket_cakupan_std");
session.removeAttribute("tgl_ril");
session.removeAttribute("tgl_ril_done");
session.removeAttribute("id_master_std");
session.removeAttribute("ket_master_std");
//----END kegiatan  param audit mutu internal----------------

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
boolean editor = (Boolean) session.getAttribute("spmi_editor");
boolean team_spmi = (Boolean) session.getAttribute("team_spmi");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
session.setAttribute("current_kdpst_nmpst_kmp", kdpst_nmpst_kmp);

kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	

SearchAmi sa = new SearchAmi();
Vector v_hist_ami = sa.getRiwayatAmi(kdpst);
//id+"~"+nm_act+"~"+tgl_plan+"~"+ketua+"~"+anggota+"~"+cakupan_std+"~"+tgl_ril


%>
<style>
a.img:hover {
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
.table:hover td { background:#82B0C3 }
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
	
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();

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
	table.StateTable tr.statetablerow { background:<%=Constant.darkColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorBlu() %> }
	
</style>
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
		<br><br>
		<center>
		<%



if((team_spmi||editor)) {	
		%>
	<table style="width:100%">
		<tr>
			<td style="text-align:center;font-size:1.3em;font-weight:bold" align="center">
				<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Ami/form_add_ami.jsp">
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
					<input type="image" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="35" height="35" alt="Submit Form" onclick="this.form.submit()" />
					TAMBAH KEGIATAN AUDIT MUTU INTERNAL
				</form>
			</td>
		</tr>
	</table>
	<br><br>	
<%
	
}		
if(v_hist_ami!=null && v_hist_ami.size()>0) {
	Vector v_hist_ami_done = (Vector)v_hist_ami.clone();
	ListIterator li1 = v_hist_ami_done.listIterator();
	//id+"~"+nm_act+"~"+tgl_plan+"~"+ketua+"~"+anggota+"~"+cakupan_std+"~"+tgl_ril
	ListIterator li = v_hist_ami.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"~");
		String id = st.nextToken();
		String target_kdpst = st.nextToken();
		String nm_act = st.nextToken();
		String tgl_plan = st.nextToken();
		String ketua = st.nextToken();
		String anggota = st.nextToken();
		String id_cakupan_std = st.nextToken();
		String tgl_ril = st.nextToken();
		String ket_cakupan_std = st.nextToken();
		String tgl_ril_done = st.nextToken();
		boolean done = false;
		if(!Checker.isStringNullOrEmpty(tgl_ril_done)) {
			done = true;
		}
		if(done) {
			li1.next();
		}	
		else {
			li1.next();
			li1.remove();
			
%>
	<form action="go.prepAmi" method="post">
	<input type="hidden" name="id_ami" value="<%=id %>"/>
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
	<input type="hidden" name="kode_activity" value="<%=nm_act %>"/>
	<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
	<input type="hidden" name="ketua_tim" value="<%=ketua %>"/>
	<input type="hidden" name="anggota_tim" value="<%=anggota %>"/>
	<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
	<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
	<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
	<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:90%;background:white;">
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="font-size:0.9em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369">
				<%
		if(Checker.isStringNullOrEmpty(tgl_ril)) {
			out.print("RENCANA ");
		}
				%>
					KEGIATAN <%=nm_act.toUpperCase() %>
					<br>TANGGAL <%=Converter.autoConvertDateFormat(tgl_plan, "/") %>
				</td>
			</tr>
				
		</thead>
		<tbody>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					1.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Ketua Tim Auditor
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=ketua.toUpperCase() %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:center;border:1px solid #369">
					2.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Anggota Tim Auditor
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%
		StringTokenizer stt = new StringTokenizer(anggota,",");
		int norut=1;		
		while(stt.hasMoreTokens()) {
		%>
			<%=norut++ %>.&nbsp&nbsp<%=stt.nextToken().toUpperCase() %>
		<%
			if(stt.hasMoreTokens()) {
				out.print("<br>");
			}
		}
%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:center;border:1px solid #369">
					3.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Cakupan Standar AMI
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%
		stt = new StringTokenizer(ket_cakupan_std,",");
		norut=1;		
		while(stt.hasMoreTokens()) {
		%>
			<%=norut++ %>.&nbsp&nbsp<%=stt.nextToken().toUpperCase() %>
		<%
			if(stt.hasMoreTokens()) {
				out.print("<br>");
			}
		}
%>
				</td>
			</tr>
						<tr>
				<td rowspan="2" style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					4.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Tanggal Pelaksanaan Dimulai
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=Checker.pnn_v1(tgl_ril,"belum dilaksanakan") %>
				</td>
			</tr>	
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Tanggal Pelaksanaan Selesai
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%
					if(!Checker.isStringNullOrEmpty(tgl_ril_done)) {
						out.print(Checker.pnn_v1(tgl_ril_done));
					}
					else {
						if(Checker.isStringNullOrEmpty(tgl_ril)) {
							out.print(Checker.pnn_v1(tgl_ril,"belum dilaksanakan"));
						}
						else {
							out.print(Checker.pnn_v1(tgl_ril,"belum selesai (in progress)"));
						}
					} 
					%>
				</td>
			</tr>		
<%
		if(editor) {
%>			
			<tr>
				<td colspan="3" style="padding:10px 10px;background:<%=Constant.lightColorBlu()%>;border:1px solid #369">
					<section class="gradient">
					<div style="text-align:center">
		<%
		if(Checker.isStringNullOrEmpty(tgl_ril)) {
		%>			
						<button name="btn" value="edit" style="padding: 5px 50px;font-size: 20px;">EDIT RENCANA AMI</button>
						<button name="btn" value="preview" style="padding: 5px 50px;font-size: 20px;">REVIEW AMI</button>
		<%
		}
		else if(Checker.isStringNullOrEmpty(tgl_ril_done)) {
			%>				
						<button name="btn" value="resume" style="padding: 5px 50px;font-size: 20px;">LANJUTKAN KEGIATAN AMI</button>
<%	
		}
		else {
			%>				
						<button name="btn" value="result" style="padding: 5px 50px;font-size: 20px;">HASIL AMI</button>
<%	
		}

		%>				
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
	<br>		
<%
		}
	}
	if(v_hist_ami_done!=null && v_hist_ami_done.size()>0) {
		Vector v_list_riwayat_ami_sudah_done=(Vector) session.getAttribute("v_list_riwayat_ami_sudah_done");
		li = v_list_riwayat_ami_sudah_done.listIterator();
		int counter = 0;
		boolean new_table=true;
		while(li.hasNext()) {
			counter++;
			String brs = (String)li.next();
			StringTokenizer st0 = new StringTokenizer(brs,"~");
			String id_ami = st0.nextToken();
			String id_master = st0.nextToken();
			String nm_master = st0.nextToken();
			String hasil_penilaian = st0.nextToken();
			String max_penilaian = st0.nextToken();
			//double hasil_penilaian_decimal = Double.parseDouble(hasil_penilaian)/Double.parseDouble(max_penilaian);
			String tot_id_master = st0.nextToken();
			if(counter>Integer.parseInt(tot_id_master)) {
				counter=1;
			}
			//out.print(brs+"<br>");
			
			
			String id = null;
			String target_kdpst =null;
			String nm_act =null;
			String tgl_plan =null;
			String ketua =null;
			String anggota =null;
			String id_cakupan_std =null;
			String tgl_ril =null;
			String ket_cakupan_std =null;
			String tgl_ril_done =null;
			boolean match = false;
			//out.print("v_hist_ami_done.size= "+v_hist_ami_done.size());
			li1 = v_hist_ami_done.listIterator();
			while(li1.hasNext()&&!match) {
				brs = (String)li1.next();
				//out.print("++brs="+brs+"<br>");
				st = new StringTokenizer(brs,"~");
				id = st.nextToken();
				target_kdpst = st.nextToken();
				nm_act = st.nextToken();
				tgl_plan = st.nextToken();
				ketua = st.nextToken();
				anggota = st.nextToken();
				id_cakupan_std = st.nextToken();
				tgl_ril = st.nextToken();
				ket_cakupan_std = st.nextToken();
				tgl_ril_done = st.nextToken();	
				if(id_ami.trim().equalsIgnoreCase(id.trim())) {
					match = true;
				}
			}
			//out.print(id_ami+" vs "+id+"<br>");
			//bikin tabel disini
			if(counter==1) {
%>

	<form action="go.prepAmi" method="post">
	<input type="hidden" name="id_ami" value="<%=id %>"/>
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
	<input type="hidden" name="kode_activity" value="<%=nm_act %>"/>
	<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
	<input type="hidden" name="ketua_tim" value="<%=ketua %>"/>
	<input type="hidden" name="anggota_tim" value="<%=anggota %>"/>
	<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
	<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
	<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
	<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:90%;background:white;">
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="font-size:0.9em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369">
				<%
				if(Checker.isStringNullOrEmpty(tgl_ril)) {
					out.print("RENCANA ");
				}
				else {
					out.print("HASIL ");
				}
				%>
					KEGIATAN <%=nm_act.toUpperCase() %>
					<br>TANGGAL <%=Converter.autoConvertDateFormat(tgl_ril, "/") %> s/d <%=Converter.autoConvertDateFormat(tgl_ril_done, "/") %>
				</td>
			</tr>
<%
			}
			double width=100/Integer.parseInt(tot_id_master);
			if(counter==1) {
				
%>			
			<tr class="statetablerow">
				<td colspan="3" style="font-size:0.9em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369">
					<table style="width:100%">
						<tr>
							<td style="width:<%=width%>%">
								<table style="width:100%;border:1px solid #369">
									<tr>
										<td style="width:100%;background:<%=Constant.lightColorBlu() %>;color:#369">
											<%= nm_master%>
										</td>
									</tr>
									<tr>
										<td style="width:100%;background:#fff;color:#369">
											<% 
												double value = (Double.parseDouble(hasil_penilaian)/Double.parseDouble(max_penilaian))*100;
												StringTokenizer stt = new StringTokenizer(""+value,".");
												String int_val = stt.nextToken();
												String dec_val = stt.nextToken();
												if(Double.parseDouble(dec_val)>0) {
													out.print((Math.round(value*100.0)/100.0)+" %");
												}
												else {
													out.print(int_val+" %");
												}
											
											%> 
										</td>
									</tr>
								</table>		
							</td>
						
<%
			}
			else if(counter<=Integer.parseInt(tot_id_master)) {
%>				
						
							<td style="width:<%=width%>%">
								<table style="width:100%;border:1px solid #369">
									<tr>
										<td style="width:100%;background:<%=Constant.lightColorBlu() %>;color:#369">
											<%= nm_master%>
										</td>
									</tr>
									<tr>
										<td style="width:100%;background:#fff;color:#369">
											<% 
												double value = (Double.parseDouble(hasil_penilaian)/Double.parseDouble(max_penilaian))*100;
												StringTokenizer stt = new StringTokenizer(""+value,".");
												String int_val = stt.nextToken();
												String dec_val = stt.nextToken();
												if(Double.parseDouble(dec_val)>0) {
													out.print((Math.round(value*100.0)/100.0)+" %");
												}
												else {
													out.print(int_val+" %");
												}
											%> 
										</td>
									</tr>
								</table>		
							</td>
						
		<%
			}
			if(counter==Integer.parseInt(tot_id_master)) {
		%>				
						</tr>
					</table>
				</td>
			</tr>
<%
			}
			if(counter==Integer.parseInt(tot_id_master)) {
%>		
		</thead>
		<tbody>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					1.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Ketua Tim Auditor
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=ketua.toUpperCase() %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:center;border:1px solid #369">
					2.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Anggota Tim Auditor
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%
				StringTokenizer stt = new StringTokenizer(anggota,",");
				int norut=1;		
				while(stt.hasMoreTokens()) {
		%>
			<%=norut++ %>.&nbsp&nbsp<%=stt.nextToken().toUpperCase() %>
		<%
					if(stt.hasMoreTokens()) {
						out.print("<br>");
					}
				}
%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:center;border:1px solid #369">
					3.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Cakupan Standar AMI
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%
				stt = new StringTokenizer(ket_cakupan_std,",");
				norut=1;		
				while(stt.hasMoreTokens()) {
		%>
			<%=norut++ %>.&nbsp&nbsp<%=stt.nextToken().toUpperCase() %>
		<%
					if(stt.hasMoreTokens()) {
						out.print("<br>");
					}
				}
%>
				</td>
			</tr>
						<tr>
				<td rowspan="2" style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					4.
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Tanggal Pelaksanaan Dimulai
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=Checker.pnn_v1(tgl_ril,"belum dilaksanakan") %>
				</td>
			</tr>	
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Tanggal Pelaksanaan Selesai
				</td>
				<td style="background:<%=Constant.lightColorBlu() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%
				if(!Checker.isStringNullOrEmpty(tgl_ril_done)) {
					out.print(Checker.pnn_v1(tgl_ril_done));
				}
				else {
					if(Checker.isStringNullOrEmpty(tgl_ril)) {
						out.print(Checker.pnn_v1(tgl_ril,"belum dilaksanakan"));
					}
					else {
						out.print(Checker.pnn_v1(tgl_ril,"belum selesai (in progress)"));
					}
				} 
					%>
				</td>
			</tr>		
		
			<tr>
				<td colspan="3" style="padding:10px 10px;background:<%=Constant.lightColorBlu()%>;border:1px solid #369">
					<section class="gradient">
					<div style="text-align:center">
						<button name="btn" value="result" style="padding: 5px 50px;font-size: 20px;">HASIL AMI</button>
					</div>
					</section>
				</td>
			</tr>

	
		</tbody>
	</table>
	</form>
	<br>
<%			
			 
			}
		}
		
	}
}
else {
	%>	
	
	<table style="width:100%">
	<tr>
		<td colspan="7" style="text-align:center;padding:0 0;font-size:2.0em;font-weight:bold">
			Belum Ada Riwayat Kegiatan Audit Mutu Internal						
		</td>
	</tr>
	</table>
	<%
//pertama kali, hanya boleh kegiatan manual

		
}
%>
	</center>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>