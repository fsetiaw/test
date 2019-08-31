
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

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String atMenu = request.getParameter("atMenu");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//String kdpst = request.getParameter("kdpst");
//String nmpst = request.getParameter("nmpst");
//String kdkmp = request.getParameter("kdkmp");
String updated = request.getParameter("updated");
session.removeAttribute("v_err");
session.removeAttribute("v_target");
//SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
//Vector v = sdb.getListTitleJabatan();
SearchRequest sr = new SearchRequest();
Vector v = sr.getListNuStdReq();

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
tr:hover td { background:#82B0C3 }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<jsp:include page="topMenu.jsp" >
		<jsp:param name="atMenu" value="<%=atMenu %>"/>
		<jsp:param name="target_kdpst" value="<%=kdpst %>"/>
		<jsp:param name="target_nmpst" value="<%=nmpst %>"/>
		<jsp:param name="target_kdkmp" value="<%=kdkmp %>"/>

	</jsp:include>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
	
		<br>
<%
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
		<form action="go.updatePpRules">
			<table class="table">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST USULAN STANDAR</th>
  				</tr>
  				<tr>
  					<th width="5%">NO</th>
  					<th width="95%">RASIONALE&nbsp/&nbspALASAN PENGAJUAN&nbsp/&nbsp MASALAH YG SERING TERJADI DI LAPANGAN</th>
  					<!--  th width="45%">ISI STANDAR</th -->
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
				<tr onclick="location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/form/std_isi/edit_usulan.jsp?id_std_isi=<%=id%>&atMenu=form_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'">
					<td align="center" style="vertical-align: middle; padding: 5px 5px"><%=norut++ %></td>
					<td style="vertical-align: middle;text-align:left; padding: 5px 5px"><%=Checker.pnn(rasionale) %></td>	
				</tr>	
<%		
	}
%>
			</tbody>	
			</table>
		</form>	
<%	
}
%>
	</div>
</div>		
</body>
</html>	