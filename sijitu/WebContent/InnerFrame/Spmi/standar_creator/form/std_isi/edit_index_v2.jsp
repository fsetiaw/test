
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
//System.out.println("okay1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//System.out.println("okay2");
String atMenu = request.getParameter("atMenu");
String mode = request.getParameter("mode");
//System.out.println("okay3");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
//System.out.println("okay4");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//System.out.println("okay5");
String updated = request.getParameter("updated");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
//System.out.println("okay6");
Vector v_job = sdb.getListAvalablePihakTerkait_v1();
SearchRequest sr = new SearchRequest();
Vector v = sr.getListNuStdReq();
//System.out.println("okay7");
String id_master_title = request.getParameter("id_master");
String id_std_isi = request.getParameter("id_std_isi");
String id_versi = request.getParameter("id_versi");
//System.out.println("mode="+mode);
//System.out.println("id_master_title="+id_master_title);
//System.out.println("id_std_isi="+id_std_isi);
//System.out.println("id_versi="+id_versi);
Vector v_err = (Vector)session.getAttribute("v_err");
session.removeAttribute("v_err");
SearchSpmi ss = new SearchSpmi();
Vector v_root = ss.getListMasterStandar();
//id_tipe
//String id_master = request.getParameter("id_master");
//String id_tipe=request.getParameter("id_tipe");
String tkn_pengawas=request.getParameter("tkn_pengawas");
String cakupan_std=request.getParameter("cakupan_std");
String tipe_proses_pengawasan=request.getParameter("tipe_proses_pengawasan");
session.removeAttribute("v_target");
String id_tipe_title = request.getParameter("id_tipe");
//System.out.println("id_tipe_title="+id_tipe_title);
String at_page = request.getParameter("at_page");
String max_data_per_pg = request.getParameter("max_data_per_pg");
String first_index = request.getParameter("first_index");
//String backto = request.getParameter("backto");
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
<%
if(mode.equalsIgnoreCase("list")) {
%>
	tr:hover td { background:#82B0C3 }
<%	
}
%>

</style>


 <script type="text/javascript">

jQuery(function() {
    jQuery('#id_master').change(function() {
        this.form.submit();
    });
});


$(document).ready(function () {
	var mySelect = $('#first-disabled2');

	$('#special').on('click', function () {
		mySelect.find('option:selected').prop('disabled', true);
		mySelect.selectpicker('refresh');
	});

	$('#special2').on('click', function () {
		mySelect.find('option:disabled').prop('disabled', false);
		mySelect.selectpicker('refresh');
	});

	$('#basic2').selectpicker({
  		liveSearch: true,
		maxOptions: 1
	});
});
</script>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<!--  jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" / -->
	<%
	String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
	//System.out.println(target);
	String uri = request.getRequestURI();
	//System.out.println(uri);
	String url = PathFinder.getPath_v2(uri, target);
	//System.out.println(url);
	//<%=url >?id=<id_master_title >&id_tipe_std=id_tipe_std >&kdpst_nmpst_kmp=<kdpst_nmpst_kmp >&backto=<backto >&first_index=<first_index >&at_page=1&max_data_per_pg=<max_data_per_pg >
	
	%>
	
	<%
	if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("list")) {
	%>
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" />
	<%
	}
	else if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("std_non_aktif")) {
		%>
		
	<ul>	
		<li><a href="go.getListAllStd?mode=std_non_aktif&id_tipe_std=<%=id_tipe_title %>&id_master_std=<%=id_master_title %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">BACK<span><b style="color:#eee">---</b></span></a></li>
	</ul>
		<%
	}
	else {
		%>
	<ul>	
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_v2.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
	<%
	}
	%>	
	
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
if(mode.equalsIgnoreCase("list")) {
	//System.out.println("list mode");
	if(v==null||v.size()<1) {
%>
		<div style="font-weight:bold;font-size:1.5em;text-align:center">
		Usulan Penetapan Standar: 0 
		</div>
<%	
	}
	else {
%>
		<center>
		
		<table class="table" style="width:90%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST USULAN STANDARISASI</th>
  				</tr>
  				<tr>
  					<th width="10%">NO</th>
  					<th width="90%">RASIONALE&nbsp/&nbspALASAN PENGAJUAN&nbsp/&nbsp MASALAH YG SERING TERJADI DI LAPANGAN</th>
  				</tr>
  			</thead>
  			<tbody>
<%	
		int norut = 1;
		ListIterator li = v.listIterator();
		while(li.hasNext()) {
		
			String brs = (String)li.next();
			//System.out.println("baris = "+brs);
			st = new StringTokenizer(brs,"`");
		//id+"`"+id_turun+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale
			String id = st.nextToken();
			String id_std = st.nextToken();
			String isi = st.nextToken();
			String butir = st.nextToken();
			String kdpst_std = st.nextToken();
			String rasionale = st.nextToken();
			
%>
				<tr onclick="location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/form/std_isi/edit_usulan_std.jsp?id_std_isi=<%=id%>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'">
					<td align="center" style="vertical-align: middle; padding: 5px 5px"><%=norut++ %></td>
					<td style="vertical-align: middle;text-align:left; padding: 5px 5px"><%=Checker.pnn(rasionale) %></td>
				</tr>	
<%		
		}
%>
			</tbody>	
		</table>
			
<%	
	}
}

%>
	</div>
</div>		
</body>
</html>	