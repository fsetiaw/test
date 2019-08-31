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
</style>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= (Vector)request.getAttribute("v"); 
if(v==null || (v!=null && v.size()<1)) {
	v= (Vector)session.getAttribute("v");
}
else {
	session.setAttribute("v",v);	
}
//System.out.println(); 
String tipe_cmd = request.getParameter("tipe_cmd");
%>
</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_pageVersion.jsp" flush="true" />
	
</div>
<div class="colmask fullpage">
<div class="col1">

<br />

<!-- Column 1 start -->

<%
StringTokenizer st = null;
if(v!=null && v.size()>4) {//4 baris pertama == overhead
%>
<jsp:include page="hamburger_menu_v1.jsp" flush="true" />
<br/>
<%
	ListIterator li = v.listIterator();
	if(li.hasNext()) {
		int i = 1;
		String col_type = (String)li.next();
		String tkn_header = (String)li.next();
		String tkn_align = (String)li.next();
		String tkn_width = (String)li.next();
		StringTokenizer stw = new StringTokenizer(tkn_width,"`");
		StringTokenizer sta = new StringTokenizer(tkn_align,"`");
		StringTokenizer sth = new StringTokenizer(tkn_header,"`");
%>


<table class="table">
	<thead>
		<tr>
		<th style="padding:0 5px;width:5%">
			NO
		</th>
<%
		while(sth.hasMoreTokens()) {
			String lebar = stw.nextToken();
			String teks_align = sta.nextToken();
%>		
			<th style="padding:0 5px;width:<%=lebar%>%;text-align:<%=teks_align%>">
				<%=sth.nextToken() %>
			</th>
<%
		}

%>		
		</tr>
	</thead>
	<tbody>
<%
		boolean first = true;
		do {
			stw = new StringTokenizer(tkn_width,"`");
			sta = new StringTokenizer(tkn_align,"`");
			sth = new StringTokenizer(tkn_header,"`");
%>
		<tr>
<%			
			if(first) {
				first = false;
		%>		
			<th style="padding:0 5px;width:5%">
				<%=i++ %>
			</td>
	<%	
			}
			String brs = (String)li.next();
			st = new StringTokenizer(brs,"`");
			while(st.hasMoreTokens()) {
				String lebar = stw.nextToken();
				String teks_align = sta.nextToken();
			%>		
			<th style="padding:0 5px;width:<%=lebar%>%;text-align:<%=teks_align%>">
				<%=st.nextToken() %>
			</td>
			
<%
			}
			%>
		</tr>
			<%
			first = true;
		}
		while(li.hasNext());
%>	
	</tbody>
</table>
<%
	}
}
else {
%>
	<h2 align="center">0 Result Set</h2>
<%
}
%>
</br/>
</div>
</div>
</body>
</html>