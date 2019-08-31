<!DOCTYPE html>
<head>
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
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
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
	/*tr:hover td { background:#82B0C3 }*/
	</style>
<%
Vector v_mgs_hist = (Vector) session.getAttribute("v_mgs_hist");
Vector v_mgs_hist_clone = (Vector)v_mgs_hist.clone();
String limit_per_page = request.getParameter("limit_per_page");
String offset = request.getParameter("offset");
//session.removeAttribute("v_mgs_hist"); 
//li.add(nmmhs+"`"+topik+"`"+reed+"`"+updtm+"`"+target_nicknamev
%>	
</head>
<!-- body onload="location.href='#'" -->
<body>
<%
try {
%>
<div id="header">
	<jsp:include page="InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
<div class="col1">
<br />
<!--  center><jsp:include page="navigasi.jsp" flush="true" /></center -->
<!-- Column 1 start -->
<center>
<%
if(v_mgs_hist_clone!=null && v_mgs_hist_clone.size()>0) {
	if(v_mgs_hist_clone.size()>Integer.parseInt(limit_per_page)) {
		v_mgs_hist_clone.removeElementAt(v_mgs_hist_clone.size()-1);
	}
	ListIterator li = v_mgs_hist_clone.listIterator();
	int counter=Integer.parseInt(offset);
%>
<br>
<table class="table">
	<thead>
	<tr>
  		<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">RIWAYAT PESAN CIVITAS</th>
  	</tr>
  	<tr>
  		<th width="5%">NO</th>
  		<th width="25%">NAMA TAMU</th>
  		<th width="55%">ISI PESAN</th>
  		<th width="15%">WAKTU</th>
  	</tr>
  	</thead>
  	<tbody>
 <%

 	while(li.hasNext()) {
 		counter++;
 		String brs = (String)li.next();
 		StringTokenizer st = new StringTokenizer(brs,"`");
 		String npmhs = st.nextToken();
 		String nmmhs = st.nextToken();
 		String isi_topik = st.nextToken();
 		String reed = st.nextToken();
 		String updtm = st.nextToken();
 		String target_nick = st.nextToken();
 		String creator_nick = st.nextToken();
 		//String target = "PROGRAM NON PASCA";
 		//if(target_nick.contains("pasca")||target_nick.contains("PASCA")) {
 		//	target = "PROGRAM PASCA";
 		//}
 %> 	
  	<tr>
		<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=counter %></td>
		<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nmmhs %><br>[<%=npmhs %>]</td>
		<td align="left" style="vertical-align: middle; padding: 0px 5px">[<%=creator_nick %>]<br><%=isi_topik %></td>
		<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=updtm %></td>
	</tr>
<%
}
%>	
	</tbody>
</table>

<%
}
else {
%>
<h3 style="font-weight:bold">0 Riwayat Pesan Tamu</h3>
<%	
}
%>
<br />
<center><jsp:include page="navigasi.jsp" flush="true" /></center>
<%
session.removeAttribute("v_mgs_hist");
%>
</center>
</br/>

</div>
</div>

<%
}
catch(Exception e) {
%>
<center>
<img src="<%=Constants.getRootWeb() %>/images/oups.jpg" alt="Smiley face" height="100%" width="100%">	
<meta http-equiv="refresh" content="15; url=http:<%=Constants.getRootWeb() %>/InnerFrame/home.jsp">
</center>
<%	
}
%>
</body>
</html>