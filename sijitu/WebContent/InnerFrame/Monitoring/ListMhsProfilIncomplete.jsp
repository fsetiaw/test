<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>

<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>
<%
//System.out.println("at ListMhsPro");
//Vector v_list_mhs = (Vector)request.getAttribute("v_list_mhs");
Vector v_list_mhs = (Vector)session.getAttribute("v_list_mhs");
//System.out.println("v_list_mhs2="+v_list_mhs.size());
//request.removeAttribute("v_list_mhs");

int limit_per_page = Integer.parseInt(request.getParameter("limit_per_page")) ;
int at_hal = Integer.parseInt(request.getParameter("at_hal"));
if(at_hal<1) {
	at_hal=1;
}
int search_range = Integer.parseInt(request.getParameter("search_range"));
//String limit_per_page = request.getParameter("limit_per_page");
//String init_hal = request.getParameter("init_hal");
//String search_range = request.getParameter("search_range");
String starting_smawl = request.getParameter("starting_smawl");

//System.out.println("starting_smawl="+starting_smawl);
%>
<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>

</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
<ul>
	<li><a href="#" onclick="(function(){
			//scroll(0,0);
			parent.scrollTo(0,0);
 			var x = document.getElementById('wait');
 			var y = document.getElementById('main');
 			x.style.display = 'block';
 			y.style.display = 'none';
 			location.href='get.notifications'})()"
	 		target="_self" >BACK<span>&nbsp</span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<section style="text-align:center">
		<jsp:include page="navigasi.jsp" flush="true" />
		</section>
<br />
<!-- Column 1 start -->
<div style="font-weight:bold">

<%

if(v_list_mhs!=null) {
	StringTokenizer st = null;
	ListIterator li = v_list_mhs.listIterator();
	//String brs_info = (String)li.next();
	//StringTokenizer st = new StringTokenizer(brs_info,"`");
	//boolean ada_prev = Boolean.parseBoolean(st.nextToken());
	//String at_hal = st.nextToken();
	//boolean ada_next = Boolean.parseBoolean(st.nextToken());
	//st = new StringTokenizer(at_hal,"/");
	//at_hal = st.nextToken();
	//String tot_hal = st.nextToken();
	%>
<center>
	
	<table class="table">
		<thead>
		<tr>
	  		<th colspan="5" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST MAHASISWA BELUM MELENGKAPI DATA PROFIL </th>
	  	</tr>
	  	<tr>
  			<th width="5%">NO</th>
  			<th width="25%">PRODI</th>
  			<th width="15%">NPMHS</th>
  			<th width="40%">NAMA</th>
  			<th width="15%">ANGKATAN</th>
  		</tr>
  		</thead>
	<%	
	int counter=(at_hal-1)*limit_per_page;
	//int no = Integer.parseInt(init_hal)*Integer.parseInt(limit_per_page)-(Integer.parseInt(limit_per_page)-1);
	while(li.hasNext() && counter< (at_hal*limit_per_page)) {
		//no++;
		counter++;
		String baris = (String)li.next();
		st = new StringTokenizer(baris,"`");
		String kdpst = st.nextToken();
		String npmhs = st.nextToken();
		String nmmhs = st.nextToken();
		String smawl = st.nextToken();
%>
		<tbody>
  		<tr>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=counter %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(kdpst) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=npmhs %></td>
			<td align="left" style="vertical-align: middle; padding: 0px 10px"><%=nmmhs %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=smawl %></td>
		</tr>
		
		
<%	
	}
%>
		</tbody>
	</table>	
	
</center>
<%
}
%>
</div>
</br/>
	</div>
</div>
</div>
</body>
</html>