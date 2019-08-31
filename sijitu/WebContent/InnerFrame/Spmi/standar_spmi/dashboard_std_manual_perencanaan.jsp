<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
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
Vector v= (Vector)request.getAttribute("v");
request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String id_std = request.getParameter("id_std");
String at_menu_dash = request.getParameter("at_menu_dash");
String std_kdpst = "null";
String scope_std = "null";
request.removeAttribute("v");
String am_i_pengawas = request.getParameter("am_i_pengawas");
String am_i_terkait = request.getParameter("am_i_terkait");
boolean editor = spmi_editor.booleanValue();
boolean terkait = Boolean.valueOf(am_i_terkait);
boolean pengawas = Boolean.valueOf(am_i_pengawas);
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
String unit_used=null;
ToolSpmi ts = new ToolSpmi();
String darimana = request.getParameter("darimana");

SearchManualMutu smm = new SearchManualMutu();
smm.autoInsertManual(Integer.parseInt(id_versi),Integer.parseInt(id_std_isi), "perencanaan");
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
	function myFunction() {
	$("#wait").show();
		$(document).ready(function() {
			$("#wait").remove();
			scroll(0,0);
			resize_iframe();
			//$("#main").show();
		});
	}
</script>
<script>
	$(document).ready(function() {
		$("#form").hide();
		$("#upload").hide();
		$("#chart").show();
		
		document.getElementById('chart').style.display='block';
		document.links[6].className = 'active';
		document.links[7].className = '';
		document.links[8].className = '';
		document.getElementById('form').style.display='none';
		document.getElementById('upload').style.display='none';
	});	
</script>

<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #369;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
</style>
</head>
<%
if(!Checker.isStringNullOrEmpty(darimana)&&darimana.equalsIgnoreCase("riwayat")) {
	%>
<body onload="window.resizeTo(screen.width, 4500);document.getElementById('chart').style.display='none';document.getElementById('form').style.display='block';document.links[7].className = 'active';document.links[6].className = '';document.links[8].className = '';document.getElementById('upload').style.display='none';">
<%
}
else {
%>
<body onload="window.resizeTo(screen.width, 4500);">
<%
}
%>
<jsp:include page="menu_dash.jsp" />
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
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
	if(li.hasNext()) {
		norut++;
		//id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+id_versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+dokumen+"`"+tkn_indikator+"`"+norut+"`"+target_period_start+"`"+unit_period+"`"+lama_per_period+"`"+target_unit1+"`"+target_unit2+"`"+target_unit3+"`"+target_unit4+"`"+target_unit5+"`"+target_unit6+"`"+tkn_param+"`"+id_master+"`"+id_tipe+"`"+tkn_pengawas+"`"+scope+"`"+tipe_survey;
		
		String brs = (String)li.next();
		//System.out.println("baris="+brs);
		st = new StringTokenizer(brs,"`");
		
		id_std_isi = st.nextToken();
		id_std = st.nextToken();
		String isi_std = st.nextToken();
		String butir = st.nextToken();
		std_kdpst = st.nextToken();
		String rasionale = st.nextToken();
		id_versi = st.nextToken();
		String id_perencanaan = st.nextToken();
		String id_pelaksanaan = st.nextToken();
		String id_evaluasi = st.nextToken();
		String id_pengendalian = st.nextToken();
		String id_peningkatan = st.nextToken();
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String target1 = st.nextToken();
		String target2 = st.nextToken();
		String target3 = st.nextToken();
		String target4 = st.nextToken();
		String target5 = st.nextToken();
		String target6 = st.nextToken();
		String tkn_pihak = st.nextToken();
		tkn_pihak = tkn_pihak.replace("null", "");
		tkn_pihak = tkn_pihak.replace(",,", "");
		String tkn_dokumen = st.nextToken();
		tkn_dokumen = tkn_dokumen.replace("null", "");
		tkn_dokumen = tkn_dokumen.replace(",,", "");
		String tkn_indikator = st.nextToken();
		String nomor_urut = st.nextToken();
		String periode_start = st.nextToken();
		//perhitungan cari sekarang periode ke berapa
		int periode_ke=0;
		String thsms_now = Checker.getThsmsNow();
		if(!Checker.isStringNullOrEmpty(periode_start)&&periode_start.length()==4) {
			//perhitungan berdasar tahun
			int tahun = Integer.parseInt(periode_start);
			boolean match = false;
			int max_iter = 6;
			while(!thsms_now.startsWith(""+tahun) && !match && periode_ke<max_iter) {
				periode_ke++;
				if(thsms_now.startsWith(""+tahun)) {
					match = true;
				}
				else {
					tahun++;
				}
			}
		}
		else if(!Checker.isStringNullOrEmpty(periode_start)&&periode_start.length()==5) {
			//perhitungan berdasar semester
			//int tahun = Integer.parseInt(periode_start);
			boolean match = false;
			int max_iter = 6;
			while(!thsms_now.startsWith(periode_start) && !match && periode_ke<max_iter) {
				periode_ke++;
				if(thsms_now.startsWith(periode_start)) {
					match = true;
				}
				else {
					periode_start = Tool.returnNextThsmsGivenTpAntara(periode_start);
				}
			}
		}
		
		String unit_used_by_periode_start = st.nextToken();
		String besaran_interval_per_period = st.nextToken();
		String unit_used_byTarget1 = st.nextToken();
		String unit_used_byTarget2 = st.nextToken();
		String unit_used_byTarget3 = st.nextToken();
		String unit_used_byTarget4 = st.nextToken();
		String unit_used_byTarget5 = st.nextToken();
		String unit_used_byTarget6 = st.nextToken();
		String tkn_param = st.nextToken();
		String id_master = st.nextToken();
		String id_tipe = st.nextToken();
		String tkn_pengawas = st.nextToken();
		scope_std = st.nextToken();
		String tipe_survey = st.nextToken();
		
		tkn_pengawas = tkn_pengawas.replace("null", "");
		tkn_pengawas = tkn_pengawas.replace(",,", "");
		unit_used = new String(unit_used_byTarget1);
		
		//if(validUsr.amI("ADMIN")||validUsr.amIcontain("MUTU")) {
		//	editor = true; //terkait & pengaeas
		//	terkait = true;
		//	pengawas = true;
		//}
		
		//if(!editor) {
		//	terkait = validUsr.amIcontain(tkn_pihak);
		//  pengawas = validUsr.amIcontain(tkn_pengawas);
	    //}
		
		//<tr onclick="location.href='edit_index.jsp?		

		/*
		out.print("<br>editor="+editor);
		out.print("<br>terkait="+terkait);
		out.print("<br>pengaw="+pengawas);
		*/
		//if(!mobile) {
		

%>		
	
		<table class="table1" id="tabel" style="width:100%" >
			<thead>
				<tr>
					<th colspan="10" style="border-left: none;text-align:center;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.5em;font-weight:bold">
					PERNYATAAN ISI STANDAR
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="10" style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=isi_std %>
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
					<th rowspan="2" colspan="2" style="vertical-align:middle;padding:0 5px;height:25px;width:15%;border:1px solid #8AA2A4">
					PARAMETER
					</th>
					<th rowspan="2" colspan="3" style="vertical-align:middle;padding:0 5px;height:25px;width:50%;border:1px solid #8AA2A4">
					INDIKATOR
					</th>
					<th colspan="1" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
					<%=periode_start %>
					</th>
					<th colspan="1" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
					<%
					String next_periode = new String(periode_start);
					next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start, Integer.parseInt(besaran_interval_per_period));
					out.print(next_periode);
					%>
					</th>
					<th colspan="1" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
					<%
					next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start, Integer.parseInt(besaran_interval_per_period));
					out.print(next_periode);
					%>
					</th>
					<th colspan="1" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
					<%
					next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start, Integer.parseInt(besaran_interval_per_period));
					out.print(next_periode);
					%>
					</th>
					<th colspan="1" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
					<%
					next_periode = Tool.returnNextPeriodeSpmi(next_periode, unit_used_by_periode_start, Integer.parseInt(besaran_interval_per_period));
					out.print(next_periode);
					%>
					</th>
				</tr>
				</tr>	
					<th colspan="6" style="vertical-align:middle;padding:0 5px;height:25px;width:7%;border:1px solid #8AA2A4">
						[<%=Checker.pnn_v1(unit_used_byTarget1) %>]
					</th>
				</tr>
			</thead>
				<tr>
					<td colspan="2" style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=tkn_param.replace(",", "<br>") %>
					</td>
					<td colspan="3" style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=tkn_indikator.replace(",", "<br>") %>
					</td>
					<td style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=target1 %>
					</td>
					<td style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=target2 %>
					</td>
					<td style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=target3 %>
					</td>
					<td style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=target4 %>
					</td>
					<td style="text-align:center;padding:10px 0;font-size:1.3em">
						<%=target5 %>
					</td>
				</tr>
			<thead>	
				<tr>
					<th  colspan="2" style="text-align:left;vertical-align:middle;padding:0 5px;height:25px;border:0px solid #8AA2A">
					PIHAK TERKAIT
					</th>
					<td colspan="8" style="vertical-align:top;text-align:left;padding:1px 5px;font-size:1.3em">
						
					<%
						if(!Checker.isStringNullOrEmpty(tkn_pihak)) {
							st = new StringTokenizer(tkn_pihak,",");
							if(st.countTokens()<2) {
					%>
					<section style="vertical-align:middle;text-align:left;padding:1px 2px;font-size:0.8em">
						1.&nbsp<%= tkn_pihak%>
					</section>
					<%			
								//out.print(tkn_dokumen);
							}
							else {
								int sisa_token = st.countTokens();
								//int sisa_token = tot_tkn;
								int mak_col_per_row = 4;
								int col_ke = 0;
								int col_sisa = mak_col_per_row;
								int counter=0;
					%>
						<center>
						<table style="border:none;width:100%">
					<%
								while(st.hasMoreTokens()) {
									counter++;
									String tkn = st.nextToken();
									sisa_token =  sisa_token - col_ke;
									if(col_ke==0) {
					%>
							<tr>
					<%					
										if(sisa_token==1) {
					%>
								<td valign="top" colspan="<%=mak_col_per_row %>" style="vertical-align:top;width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%						
										}
										else {
					%>
								<td valign="top" style="vertical-align:top;width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%				
										}
									}
									else if(col_sisa>0 && col_ke<mak_col_per_row) { //bukan kol pertama
										if(sisa_token<col_sisa) {
											//data habis
					%>
							
								<td valign="top" colspan="<%=col_sisa+1 %>" style="vertical-align:top;width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%					
										}
										else {
					%>
							
								<td valign="top" style="vertical-align:top;width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%	
											
										}
									}
									col_ke++;
									col_sisa--;
									if(col_sisa<0) {
										col_sisa=0;
										
									}
									if(col_ke==mak_col_per_row) {
										col_ke=0;
					%>
							</tr>
					<%					
									}
									
								}
						%>						
						</table>
						</center>	
						<%	
							}
						}
					%>		
						
					</td>
				</tr>	
			</thead>		
			<thead>	
				<tr>
					<th  colspan="2" style="text-align:left;vertical-align:middle;padding:0 5px;height:25px;border:0px solid #8AA2A">
					DOKUMEN TERKAIT
					</th>
					<td colspan="8" style="vertical-align:top;text-align:center;padding:1px 5px;font-size:1.3em">
						
					<%
						if(!Checker.isStringNullOrEmpty(tkn_dokumen)) {
							st = new StringTokenizer(tkn_dokumen,",");
							if(st.countTokens()<2) {
								%>
						<section style="vertical-align:middle;text-align:left;padding:1px 2px;font-size:0.8em">
							1.&nbsp<%= tkn_dokumen%>
						</section>
								<%	
							}
							else {
								int sisa_token = st.countTokens();
								//int sisa_token = tot_tkn;
								int mak_col_per_row = 4;
								int col_ke = 0;
								int col_sisa = mak_col_per_row;
								int counter=0;
					%>
						<center>
						<table style="border:none;width:100%">
					<%
								while(st.hasMoreTokens()) {
									counter++;
									String tkn = st.nextToken();
									sisa_token =  sisa_token - col_ke;
									if(col_ke==0) {
					%>
							<tr>
					<%					
										if(sisa_token==1) {
					%>
								<td valign="top" colspan="<%=mak_col_per_row %>" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%						
										}
										else {
					%>
								<td valign="top" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%				
										}
									}
									else if(col_sisa>0 && col_ke<mak_col_per_row) { //bukan kol pertama
										if(sisa_token<col_sisa) {
											//data habis
					%>
							
								<td valign="top" colspan="<%=col_sisa+1 %>" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%					
										}
										else {
					%>
							
								<td valign="top" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%	
											
										}
									}
									col_ke++;
									col_sisa--;
									if(col_sisa<0) {
										col_sisa=0;
										
									}
									if(col_ke==mak_col_per_row) {
										col_ke=0;
					%>
							</tr>
					<%					
									}
									
								}
						%>						
						</table>
						</center>	
						<%	
							}
						}
					%>		
						
					</td>
				</tr>	
			</thead>				
			<thead>	
				<tr>
					<th  colspan="2" style="text-align:left;vertical-align:middle;padding:0 5px;height:25px;border:0px solid #8AA2A">
					PENGAWAS
					</th>
					<td colspan="8" style="vertical-align:top;text-align:center;padding:1px 5px;font-size:1.3em">
						
					<%
						if(!Checker.isStringNullOrEmpty(tkn_pengawas)) {
							st = new StringTokenizer(tkn_pengawas,",");
							if(st.countTokens()<2) {
								%>
						<section style="valign:middle;text-align:left;padding:1px 2px;font-size:0.8em">
							1.&nbsp<%= tkn_pengawas%>
						</section>
								<%	
							}
							else {
								int sisa_token = st.countTokens();
								//int sisa_token = tot_tkn;
								int mak_col_per_row = 4;
								int col_ke = 0;
								int col_sisa = mak_col_per_row;
								int counter=0;
					%>
						<center>
						<table style="border:none;width:100%">
					<%
								while(st.hasMoreTokens()) {
									counter++;
									String tkn = st.nextToken();
									sisa_token =  sisa_token - col_ke;
									if(col_ke==0) {
					%>
							<tr>
					<%					
										if(sisa_token==1) {
					%>
								<td valign="top" colspan="<%=mak_col_per_row %>" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%						
										}
										else {
					%>
								<td valign="top" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
					<%				
										}
									}
									else if(col_sisa>0 && col_ke<mak_col_per_row) { //bukan kol pertama
										if(sisa_token<col_sisa) {
											//data habis
					%>
							
								<td valign="top" colspan="<%=col_sisa+1 %>" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%					
										}
										else {
					%>
							
								<td valign="top" style="width:25%;border:none;text-align:left;padding:1px 0;font-size:0.8em">
									<%=counter %>.&nbsp<%=tkn %>
								</td>
							
					<%	
											
										}
									}
									col_ke++;
									col_sisa--;
									if(col_sisa<0) {
										col_sisa=0;
										
									}
									if(col_ke==mak_col_per_row) {
										col_ke=0;
					%>
							</tr>
					<%					
									}
									
								}
						%>						
						</table>
						</center>	
						<%	
							}
						}
					%>		
						
					</td>
				</tr>	
			</thead>					
		</table>
		<br><br>
<%	
		
	}
		%>
		
<%
}
%>
<div id="header">
<ul>
	<li><a href="#form" onclick="document.getElementById('chart').style.display='block';document.links[6].className = 'active';document.getElementById('form').style.display='none';document.links[7].className = '';document.getElementById('upload').style.display='none';document.links[8].className = '';" >MANUAL PELAKSANAAN<br>STANDAR</a></li>
	<li><a href="#form" onclick="document.getElementById('chart').style.display='none';document.links[6].className = '';document.getElementById('form').style.display='block';document.links[7].className = 'active';document.getElementById('upload').style.display='none';document.links[8].className = '';" >AKTIFASI / EDIT / TAMBAH KEGIATAN<br>MANUAL PELAKSANAAN STANDAR</a></li>
	<li><a href="#form" onclick="document.getElementById('glue').contentWindow.location.reload();document.getElementById('chart').style.display='none';document.links[6].className = '';document.getElementById('form').style.display='none';document.links[7].className = '';document.getElementById('upload').style.display='block';document.links[8].className = 'active';" >ARSIP FILE<br>DOKUMEN</a></li>
</ul>

</div>
<div id="form">
<br>&nbsp<br>
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

SearchManual sm = new SearchManual();
Vector v_kendal= sm.searchListManualPerencanaan(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
Vector v_best_manual_kendal = sm.getManualPerencanaanAktifAtoLatestDraft(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
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
			MANUAL PERENCANAAN STANDAR
			</th>	
		</tr>
		<tr>
			<th style="width:5%;border-style: solid;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			REVISI
			</th>
			<th style="width:80%;border-style: solid;border-left: thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			PROSEDUR DAN LANGKAH PERENCANAAN STANDAR
			</th>
			<th style="width:15%;border-style: solid;border-left: thin solid #2980B9;text-align:center;height:30px;vertical-align:middle;padding:0 5px;font-size:1.0em;font-weight:bold">
			STATUS MANUAL 
			</th>
		</tr>
	</thead>
	
<%

if(v_kendal==null || v_kendal.size()<1) {
	
%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0;font-size:0.9em">
				Belum Ada Manual Perencanaan					
			</td>
		</tr>
<%
}
else {
	ListIterator li = v_kendal.listIterator();
	int counter = v_kendal.size();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		id_versi = st.nextToken();
		id_std_isi = st.nextToken();
		String norut_man = st.nextToken();
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
		//if(editor) {	
		if(true) {	
%>	
		<tr>
<%
		}
		else {
			%>	
		<tr onclick="javascript:alert('Manual harus diaktifkan terlebih dahulu untuk melihat / manambah riwayay kegiatan perencanaan');">
	<%			
		}
%>
			<td style="text-align:center;padding:0 2px;font-size:1.3em">
				<%=counter %>	
<%		
		if(editor && status_manual!=null && status_manual.equalsIgnoreCase("BELUM DIAKTIFKAN")) {
%>
			<form action="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_perencanaan.jsp" method="post">
				<!--  at_menu_dash=< at_menu_dash-->
				<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
				<input type="hidden" name="id_std" value="<%=id_std%>"/>
				<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash%>"/>
				<input type="hidden" name="fwdto" value="dashboard_std_manual_perencanaan.jsp"/>
				<input type="hidden" name="id_versi" value="<%=id_versi%>"/>
				<input type="hidden" name="id_std_isi" value="<%=id_std_isi%>"/>
				<input type="hidden" name="norut_man" value="<%=norut_man%>"/>
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
			<td onclick="window.open('<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_perencanaan/riwayat_perencanaan.jsp?status_manual=<%=status_manual%>&starting_no=1&unit_used=<%=unit_used %>&norut_man=<%=norut_man %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&norut_man=<%=norut_man %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat&at_menu_dash=plan','popup','width=850,height=600')" style="text-align:left;padding:0 2px;font-size:0.9em">
				<%=prosedur %>						
			</td>
			<td style="text-align:center;padding:0 2px;font-size:0.9em">
				<%
				out.print(status_manual+"<br>");
				if(status_manual.equalsIgnoreCase("AKTIF")) {
					%>
				<a class="img2" href="set.aktivasiMan?status=aktif&tipe_manual=perencanaan&norut_man=<%=norut_man %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_perencanaan.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan memberhentikan penggunaan manual no.<%=norut %> ?!? \nNote:\n1. Anda tidak dapat mengaktifkan kembali manual yg sudah diberhentikan.\n2. Pemberhentian manual dilakukan bila manual digantikan dengan revisi yg baru')) return true; ">
					<img border="0" alt="STOP" src="<%=Constants.getRootWeb() %>/images/stop_red.png" width="30" height="30">
				</a>	
					<%	
				}
				else if(status_manual.equalsIgnoreCase("BELUM DIAKTIFKAN")) {
					%>
				<a class="img2" href="set.aktivasiMan?status=nonaktif&tipe_manual=perencanaan&norut_man=<%=norut_man %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_perencanaan.jsp" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengaktifkan penggunaan manual no.<%=norut %> ?!? \nSyarat Aktivasi:\n1. Tidak ada manual lain yang aktif.\n2. Telah melakukan kegiatan perencanaan yang menghasilkan penetapan manual')) return true;return false; ">
					<img border="0" alt="PLAY" src="<%=Constants.getRootWeb() %>/images/play_blue.png" width="30" height="30">
				</a>	
					<%
				}
				%>							
			</td>
			
		</tr>
<%	
		counter--;
	}
}	
if(editor && !ada_manual_yg_bisa_diedit) {
	%>	
		<tr>
			<td colspan="3" style="text-align:center;padding:0 0 10px 10px;font-size:0.9em;valign:middle">
			<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_perencanaan.jsp?id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_perencanaan.jsp" style="vertical-align:middle;valign:middle">
				<img border="0" alt="Add Manual" src="<%=Constants.getRootWeb() %>/images/add_ico.png" width="50px" height="50px" style="vertical-align:bottom;font-size:1.5em">Tambah Manual Baru 
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
if(v_best_manual_kendal==null || v_best_manual_kendal.size()<1) {
%>
<h1 style="text-align:center;color:red">
	BELUM ADA MANUAL PERENCANAAN STANDAR
</h1>
<%	
}
else {
	
	
	String tmp="";		
	ListIterator li = v_best_manual_kendal.listIterator();
	int counter = 1;
	if(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs="+brs);
		st = new StringTokenizer(brs,"~");
		//versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
		id_versi = st.nextToken();
		id_std_isi = st.nextToken();
		String norut_man = st.nextToken();
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
			ada_manual_yg_bisa_diedit = true;
		}
		else {
			status_manual = "AKTIF";
		}
		if(!Checker.isStringNullOrEmpty(tgl_end)) {
			status_manual = "SUDAH TIDAK BERLAKU";
		}
		
		String no_dok_spmi = ts.getPenomoranDokMutu(id_std_isi, id_versi, "MANUAL", "PERENCANAAN");
%>	
	<br>&nbsp<br>
	<table class="table1" id="table1" style="width:100%">
		<thead>	
			<tr>
				<td rowspan="4" width="20%" style="text-align:center;vertical-align:middle;padding:5px 5px;" >
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
		if(Checker.isStringNullOrEmpty(ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "penetapan","perencanaan"))) {
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
								<%=norut %>
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
		if(Checker.isStringNullOrEmpty(ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "penetapan","perencanaan"))) {
			out.print("DRAFT ");
		}
				%>
	MANUAL PERENCANAAN STANDARD</h1>
	<br><br>
	<table class="table1" id="table1" style="width:100%">
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
				String tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "perumusan","perencanaan");
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
				tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "pemeriksaan","perencanaan");
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
				tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "persetujuan","perencanaan");
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
				tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "penetapan","perencanaan");
				//System.out.println("tgl_tmp="+tgl_tmp);
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
				tmp = new String(tkn_jab_kendal);
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
				tgl_tmp = ts.getPenetapanTanggalManual(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man), "pengendalian","perencanaan");
				//System.out.println("tgl_tmp2="+tgl_tmp);
				if(Checker.isStringNullOrEmpty(tgl_tmp)) {
					out.print("[Tgl Awal Standar Dijalankan]");
				}
				else {
					out.print(Checker.pnn(Converter.autoConvertDateFormat(tgl_tmp, "/")));	
				}
				
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
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 55px">
					<%
					if(visi_misi_tujuan_nilai!=null && visi_misi_tujuan_nilai.length==4) {
						
						if(visi_misi_tujuan_nilai[0]!=null) {
							while(visi_misi_tujuan_nilai[0].contains("\n")) {
								visi_misi_tujuan_nilai[0] = visi_misi_tujuan_nilai[0].replace("\n", "<br>");
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
							out.print(visi_misi_tujuan_nilai[3]);	
						}
						
					}
					%>
				</td>
			</tr>
			<tr>
				<th style="text-align:left;vertical-align:middle;width:100%;font-size:1.5em;font-weight:bold;padding:0 0 0 5px">
					2. TUJUAN DAN MAKSUD DOKUMEN MANUAL PERENCANAAN STANDAR
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(tujuan)) {
						while(tujuan.contains("\n")) {
							tujuan = tujuan.replace("\n","<br>");
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
					3. LUAS LINGKUP MANUAL PERENCANAAN STANDAR
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(lingkup)) {
						while(lingkup.contains("\n")) {
							lingkup = lingkup.replace("\n","<br>");
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
					4. DEFINISI ISTILAH TERKAIT MANUAL PERENCANAAN STANDAR
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(definisi)) {
						while(definisi.contains("\n")) {
							definisi = definisi.replace("\n","<br>");
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
					5. PROSEDUR DAN LANGKAH PERENCANAAN STANDAR 
				</th>
			</tr>
			<tr>	
				<td style="background:white;text-align:left;vertical-align:middle;width:100%;font-size:0.9em;font-weight:bold;padding:5px 5px 5px 20px">
					<%
					if(!Checker.isStringNullOrEmpty(prosedur)) {
						while(prosedur.contains("\n")) {
							prosedur = prosedur.replace("\n","<br>");
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
					if(!Checker.isStringNullOrEmpty(dokumen)) {
						while(dokumen.contains("\n")) {
							dokumen = dokumen.replace("\n","<br>");
						}
						out.print(dokumen);
					}
					else {
						out.print("N/A");
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
%>						
</div>
	<div id="upload">
		<iframe id="glue"  onload="myFunction()" src="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/dashArsipPerencanaan.jsp?kdpst=<%=kdpst %>&id_std_isi=<%=id_std_isi %>&id_versi=<%=id_versi %>" seamless="seamless" width="100%" height="1000"  name="inner_iframe"></iframe>
	</div>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>