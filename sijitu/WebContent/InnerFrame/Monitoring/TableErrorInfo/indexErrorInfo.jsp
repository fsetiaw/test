<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//System.out.println("sudah kesini");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v_err_info = (Vector)session.getAttribute("v_err_info");
session.removeAttribute("v_err_info");	
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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>
</head>
<body>
<div id="header">
	<ul>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br>

		<%
		if(v_err_info!=null) {
			int i=1;
%>
		<table class="table" style="width:90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">WARNING / ERROR DATA</th>
  				</tr>
  				<tr>
  					<th width="5%">NO</th>
  					<th width="20%">PRODI</th>
  					<th width="20%">TABEL</th>
  					<th width="55%">KETERANGAN MASALAH</th>
  				</tr>
  			</thead>
  			<tbody>
<%			
			ListIterator li = v_err_info.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//li.add(kdpst+"~"+nmm_table+"~"+updtm+"~"+err_info);
				StringTokenizer st = new StringTokenizer(brs,"~");
				String kdpst = st.nextToken();
				String nmpst = Converter.getNamaKdpst(kdpst);
				String nmm_table = st.nextToken();
				String updtm = st.nextToken();
				String error_desc = st.nextToken();
%>
				<tr>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %></td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nmpst %></td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nmm_table %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=error_desc %></td>
				</tr>
<%				
			}
		}
		%>
			</tbody>
		</table>	
	</div>
</div>		
</body>
</html>