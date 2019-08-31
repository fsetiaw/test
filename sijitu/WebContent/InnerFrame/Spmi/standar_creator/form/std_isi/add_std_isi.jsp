
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
String id_versi = request.getParameter("id_versi");
String isi_std = request.getParameter("isi_std");//umpan balik kalo ada error dari input form
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String updated = request.getParameter("updated");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
Vector v_err = (Vector)session.getAttribute("v_err");
session.removeAttribute("v_err");
%>




<style>
.table { 
	border: 1px solid #2980B9;
	background:<%=Constant.lightColorBlu()%>;
	 
}
.table thead > tr > th { 
	border-bottom: none;
	background: <%=Constant.darkColorBlu()%>;
	color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<ul>
		<li><a href="go.getListAllStd?mode=start&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br>
		
<%
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
%>	
	</div>
	
<%	
}
		%>	
		
		<form action="ins.addDraftStdIsi" method="post">
		<input type="hidden" name="id_versi" value="<%=id_versi %>"/>
		<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>"/>
		<input type="hidden" name="id_master_std" value="<%=id_master_std %>"/>
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:2.5em;background:white;color:#369">FORM TAMBAH PERNYATAAN ISI STANDAR</th>
  				</tr>
				<tr>
					<th colspan="2" align="left" style="vertical-align: middle; padding: 0px 5px;font-size:1.5em;font-weight:bold" >DRAFT PERNYATAAN ISI STANDAR</th>
				</tr>
			</thead>
			<tbody>	
				<tr>
					<td colspan="2" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5" placeholder="" required><%=Checker.pnn_v1(isi_std) %></textarea>
					</td>
				</tr>
				<tr>
					<th align="left" style="width:50%;vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:#fff;font-weight:bold;font-size:1.5em" title="Pilih Tingkat/Level Unit Satuan yg akan diukur" >
						LINGKUP CAKUPAN STANDARD
					</th>
					<td align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<select name="cakupan_std"  style="width:100%;height:40px;border:none;text-align-last:center;">
							<option value="null">-PILIH LINGKUP CAKUPAN STANDAR-</option>
							<option value="univ">UNIVERSITAS</option>
							<option value="biro">BIRO/LEMBAGA/UNIT KERJA</option>
							<option value="prodi">PRODI</option>
						</select>
					</</td>
				</tr>
						
				<tr>
					<td colspan="2" style="padding:5px 0px">
						<section class="gradient" style="text-align:center">
	            			<button style="padding: 5px 50px;font-size: 20px;">ADD STANDAR ISI</button>
        				</section>
					</td>		
				</tr>	
  			</tbody>
		</table>
		<br>
		</form>

	</div>
</div>		
</body>
</html>	