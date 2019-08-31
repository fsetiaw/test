
<!DOCTYPE html>
<%@page import="org.codehaus.jackson.map.introspect.BasicClassIntrospector.GetterMethodFilter"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@ page import="beans.dbase.spmi.request.*"%>
<%@ page import="beans.dbase.spmi.*"%>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />



<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
Boolean team_spmi = (Boolean) session.getAttribute("team_spmi");//asal jabatan ada mutu
boolean editor = spmi_editor.booleanValue();
boolean tim_spmi = team_spmi.booleanValue();
String mode=request.getParameter("mode");
String id_tipe_std=request.getParameter("id_tipe_std");
String id_master_std=request.getParameter("id_master_std");
String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
String at_menu=request.getParameter("at_menu");

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
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<ul>
		<li>
			<a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
	
		<br>
		

		<form action="go.editUsulanStdIsi" method="post">

		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="1" style="text-align: center; padding: 0px 10px;font-size:1.5em">FORM PERUMUSAN PERNYATAAN ISI STANDAR</th>
  				</tr>

  			</thead>
  			<tbody>	
  				<tr>
					<td colspan="1" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#000;font-weight:bold" >PERNYATAAN ISI STANDAR</td>
				</tr>
				<tr>
					<td colspan="1" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
					
<%
				if(Checker.isStringNullOrEmpty("")) {
					%>					
						<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5" placeholder="Harap ceritakan permasalahan yang terjadi di lapangan, serta centang/sebutkan pihak dan dokumen terkait.&#13;&#10;[contoh: Mahasiswa selalu terlambat melakukan heregistrasi] &#13;&#10;Bila pihak atau dokumen terkait belum ada di list, harap tuliskan juga pihak/dokumen yang terkait agar bisa didata."></textarea>
<%					
				}
				else {
%>					
						<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5"><%="" %></textarea>
<%
				}
%>						
					</td>
				</tr>
				<tr>
					<td colspan="1" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#000;font-weight:bold" >PARAMETER & INDIKATOR CAPAIAN</td>
				</tr>
				<tr>
					<td colspan="1" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<table class="table2" style="width:100%;border:none;background:#fff">
							<tr>
								<thead>
								<th style="width:5%">
									NO.
								</th>
								<th style="width:15%">
									PARAMETER
								</th>
								<th style="width:40%">
									INDIKATOR CAPAIAN
								</th>
								<th style="width:15%">
									PERIODE AWAL
								</th>
								<th style="width:25%">
									DURASI PER PERIODE
								</th>
								</thead>
							</tr>
							<tr>
								<td style="border:none;text-align:left;padding:5px 0 5px 5px">
								1.
								</td>
								<td style="border:none;text-align:left;padding:5px 0 5px 5px">
								Parameter
								</td>
								<td style="border:none;text-align:left;padding:5px 2px 5px 5px">
								List indikator disini
								</td>
								<td style="border:none;text-align:left;padding:5px 2px 5px 5px">
									THSMS
								</td>
								<td style="border:none;text-align:left;padding:5px 2px 5px 5px">
									semesteran
								</td>
							</tr>
						</table>
						
				<%
				if(editor) {
				%>
						<table style="border:none;width:100%">
							<tr>
								<td colspan="1" style="border:none;width:2%;text-align:center;padding:5px 0 5px 5px">
									<a class="img" href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/form/std_isi/form_std_isi.jsp?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" style="vertical-align:middle;valign:middle">
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
					<td colspan="1" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#000;font-weight:bold" >STRATEGI PELAKSANAAN STANDAR</td>
				</tr>	
				<tr>
					<td>
						<table style="border:none;width:100%;background:#fff">
							<tr>
								<td colspan="1" style="border:none;width:5%;text-align:left;padding:5px 0 5px 5px">
									1.	
								</td>
								<td colspan="1" style="border:none;width:95%;text-align:left;padding:5px 0 5px 5px">
									strategi disini
								</td>
							</tr>
						</table>
					</td>
				</tr>
					
				<tr>
					<td colspan="4" style="padding:5px 0px">
						<section class="gradient" style="text-align:center">
	            			<button style="padding: 5px 50px;font-size: 20px;">UPDATE USULAN STANDAR</button>
        				</section>
					</td>		
				</tr>	
  			</tbody>
		</table>
		<br>
		</form>
<%

%>
	</div>
</div>		
</body>
</html>	