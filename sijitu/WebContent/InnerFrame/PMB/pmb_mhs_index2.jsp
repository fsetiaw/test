<!DOCTYPE html >

<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="java.util.Collections" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
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
tr:hover td { background:#82B0C3 }
</style>
<script type="text/javascript"></script>		
</head>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
%>
	
<body>
<div id="header">
<jsp:include page="InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
	<br />
	<center>
	<form action="go.updatePpRules">
		<table class="table" width="70%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA MAHASISWA BARU</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA LENGKAP
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<input type="text" style="width:99%;height:99%" name="nama" id="nama" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KEWARGANEGARAAN
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<input type="text" style="width:99%;height:99%" name="warga" id="warga" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<section class="gradient">
						<div style="text-align:center; padding: 5px 5px">
						<button formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">NEXT</button>
						</div>
					</section>	
					</td>				
				</tr>
  			</tbody>
		</table>
	</form>
	</center>
	</br/>
	</div>
</div>
<%

%>
<br/>
<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:700px;height:100%;visibility:hidden;margin:0px 0 0 105px;" >
</div>
</body>
</html>