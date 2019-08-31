<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" /><style>
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
Vector v_list_mhs = (Vector)request.getAttribute("v_list_mhs");
//request.removeAttribute("v_list_mhs");
String limit_per_page = request.getParameter("limit_per_page");
String init_hal = request.getParameter("init_hal");
String search_range = request.getParameter("search_range");
String starting_smawl = request.getParameter("starting_smawl");
%>


</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_pageVersion.jsp" flush="true" />
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
	
	ListIterator li = v_list_mhs.listIterator();
	String brs_info = (String)li.next();
	StringTokenizer st = new StringTokenizer(brs_info,"`");
	boolean ada_prev = Boolean.parseBoolean(st.nextToken());
	String at_hal = st.nextToken();
	boolean ada_next = Boolean.parseBoolean(st.nextToken());
	st = new StringTokenizer(at_hal,"/");
	at_hal = st.nextToken();
	String tot_hal = st.nextToken();
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
  			<th width="15%">THSMS</th>
  		</tr>
  		</thead>	
	<%	
	
	int no = Integer.parseInt(init_hal)*Integer.parseInt(limit_per_page)-(Integer.parseInt(limit_per_page)-1);
	while(li.hasNext()) {
		//no++;
		String baris = (String)li.next();
		st = new StringTokenizer(baris,"`");
		String kdpst = st.nextToken();
		String npmhs = st.nextToken();
		String nmmhs = st.nextToken();
		String smawl = st.nextToken();
%>
		<tbody>
  		<tr>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=no++ %></td>
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
</body>
</html>