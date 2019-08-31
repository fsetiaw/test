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
Vector v_list_kls = (Vector) session.getAttribute("vListKelasDgnNilaiTunda");
session.removeAttribute("vListKelasDgnNilaiTunda");
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
<div style="font-weight:bold">
<%
int no = 1;
if(v_list_kls!=null) {
	ListIterator li = v_list_kls.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String idkur = st.nextToken();
		String idkmk = st.nextToken();
		String kdpst = st.nextToken();
		String shift = st.nextToken();
		String noKlsPll = st.nextToken();
		String npm_pertama_input = st.nextToken();
		String npm_terakhir_updat = st.nextToken();
		String status_akhir = st.nextToken();
		String curr_avail_status = st.nextToken();
		String locked_or_editable = st.nextToken();
		String npmdos = st.nextToken();
		String nodos = st.nextToken();
		String npmasdos = st.nextToken();
		String noasdos = st.nextToken();
		String canceled = st.nextToken();
		String kode_kelas = st.nextToken();
		String kode_ruang = st.nextToken();
		String kode_gedung = st.nextToken();
		String kode_kampus = st.nextToken();
		String tkn_day_time = st.nextToken();
		String nmmdos = st.nextToken();
		String nmmasdos = st.nextToken();
		String enrolled = st.nextToken();
		String max_enrolled = st.nextToken();
		String min_enrolled = st.nextToken();
		String sub_keter_kdkmk = st.nextToken();
		String init_req_time = st.nextToken();
		String tkn_npm_approval = st.nextToken();
		String tkn_approval_time = st.nextToken();
		String target_ttmhs = st.nextToken();
		String passed = st.nextToken();
		String rejected = st.nextToken();
		String kode_gabung_kls = st.nextToken();
		String kode_gabung_kmp = st.nextToken();
		String unique_id = st.nextToken();
		String kdkmk = st.nextToken();
		String nakmk = st.nextToken();
		String skstm = st.nextToken();
		String skspr = st.nextToken();
		String skslp = st.nextToken();
%>
	<%=no++ %>. <%="["+kdkmk+"] "+nakmk+" ["+nmmdos+"/"+shift+"]<br>"%>
<%		
	}
}
%>
</div>
</br/>
	</div>
</div>
</body>
</html>