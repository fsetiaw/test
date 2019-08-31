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

String target_thsms = (String)request.getParameter("target_thsms");
//String target_kampus = (String)request.getParameter("target_kampus");
String fullname_table_rules = (String)request.getParameter("fullname_table_rules");
String tipe_pengajuan = fullname_table_rules.replace("_RULES", "");
String title_pengajuan = tipe_pengajuan.replace("_", " ");
String atKmp = (String)request.getParameter("target_kampus");

//String no_mhs = ""+request.getParameter("no_mhs");
%>
</head>
<!-- body onload="location.href='#'" -->
<body>
<div id="header">
	<jsp:include page="listMhsReqInnerMenu.jsp" flush="true" />
</div>
<div class="colmask fullpage">
<div class="col1">
<br/>
<!-- Column 1 start -->
<center>
<%
Vector v = (Vector)session.getAttribute("vf");
if(v==null || v.size()<1) {
	
	out.print("<h2>MISISNG PARAMETER, HUBUNGI ADMIN</h2>");
		
}
else {

	//if(no_mhs.equalsIgnoreCase("false")) {
	if(true) {	
		int i = 1;
		
%>

<form action="#">
	[WARNA BIRU = VALID]&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp[WARNA PUTIH = PROSES VALIDASI ADMINISTRATIF]
	<table class="table">
	<thead>
		<tr>
  			<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST PENGAJUAN <%=title_pengajuan.toUpperCase() %> <%=target_thsms %></th>
  		</tr>
  		<tr>
  			<th width="5%">NO</th>
  			<th width="25%">PRODI</th>
  			<th width="35%"><%=Converter.npmAlias() %> / NAMA</th>
 			<th width="35%">STATUS PERSETUJUAN</th>
  		</tr>
  	</thead>
  	<tbody>
<%	
		
		if(v!=null && v.size()>0) {
			ListIterator li = v.listIterator();
			while(li.hasNext()) {
				String kmp = (String)li.next();
				String nm_kmp = Converter.getNamaKampus(kmp);
				Vector vtmp = (Vector)li.next();
				ListIterator lit = vtmp.listIterator();
				while(lit.hasNext()) {
					String brs = (String)lit.next();
					
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kmp1 = st.nextToken();
					String id1 = st.nextToken();
					String prod1 = st.nextToken();
					while(st.hasMoreTokens()) {
						String npm = st.nextToken();
						String nmm = st.nextToken();
						String approved = st.nextToken();
						String locked = st.nextToken();
						String rejected = st.nextToken();
						String updtm = st.nextToken();
						String malaikat = st.nextToken();
					
		if(malaikat.equalsIgnoreCase("true")) {
			%>	
		<tr style="background:white">
			<%
		}
		else {
			%>	
		<tr>
		<%
		}
		%>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(prod1) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px">[<%=npm %>] <%=nmm %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px">
			<%
						if(locked.equalsIgnoreCase("true") && !Checker.isStringNullOrEmpty(approved)) {
							out.println("Telah Disetujui");
						}
						else if(locked.equalsIgnoreCase("true") && !Checker.isStringNullOrEmpty(rejected)) {
							out.println("Ditolak");
						}
						else if(locked.equalsIgnoreCase("false") && Checker.isStringNullOrEmpty(rejected)) {
							out.println("Tgl Pengajuan: "+updtm+"<br/>");
							out.println("Status : Proses Validasi");
						}
			%>
			<br/></td>
		</tr>
					<%		
					}
				}
			}	
		}

	%>
  	</tbody>
	</table>
</form>
</center>
<%
	}
}	



%>
</br/>
</div>
</div>
</body>
</html>