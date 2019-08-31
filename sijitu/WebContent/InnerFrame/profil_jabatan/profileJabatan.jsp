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
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

String atMenu = request.getParameter("atMenu");
String nim = request.getParameter("nim");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");

Vector vListJabatan = (Vector)request.getAttribute("vListJabatan");
request.removeAttribute("vListJabatan");
Vector vp = (Vector)request.getAttribute("vp");
request.removeAttribute("vp");
Vector vkmp = (Vector)request.getAttribute("vkmp");
request.removeAttribute("vkmp");
Vector v_curr_val = (Vector)request.getAttribute("v_curr_val");
request.removeAttribute("v_curr_val");
//request.setAttribute("v_curr_val", v_curr_val);
String all_prodi = null;
if(vp!=null) {
	all_prodi =new String();
	ListIterator litmp = vp.listIterator();
	int counter = 0;
	while(litmp.hasNext()) {
		counter++;
		String bar = (String)litmp.next();
	//System.out.println(bar);
		StringTokenizer stt = new StringTokenizer(bar,"`");
		String kdprod = stt.nextToken();
		String singkatan = stt.nextToken();
		String kdjen = stt.nextToken();
		String nmprod = stt.nextToken();
		all_prodi = all_prodi+kdprod;
		if(litmp.hasNext()) {
			all_prodi = all_prodi+"`";
		}
	}			
}
else {
	all_prodi = new String("null"); 
}
String all_kmp = null;
if(vkmp!=null) {
	all_kmp = new String();
	ListIterator litmp = vkmp.listIterator();
	int counter = 0;
	while(litmp.hasNext()) {
		counter++;
		String bar = (String)litmp.next();
	//System.out.println(bar);
		StringTokenizer stt = new StringTokenizer(bar,"`");
		String kdkmp = stt.nextToken();
		String nmkmp = stt.nextToken();
		String nickkmp = stt.nextToken();
		all_kmp = all_kmp+kdkmp;
		if(litmp.hasNext()) {
			all_kmp = all_kmp+"`";
		}
	}	
}
else {
	all_kmp = new String("null"); 
}
%>
</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<br/>
<!-- Column 1 start -->
<%
Vector v_jab = validUsr.getListJabatan_detail(Integer.parseInt(id_obj));
if(v_jab!=null && v_jab.size()>0) {
	int i = 1;
	out.print("JABATAN :<br/>");
	ListIterator lij = v_jab.listIterator();
	while(lij.hasNext()) {
		String brs = (String)lij.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String job = st.nextToken();
		String sink_job = st.nextToken();
		out.print(i+++".&nbsp"+job.toUpperCase()+"<br/>");
		while(st.hasMoreTokens()) {
			String tmp = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(tmp,",");
			String kmp = st1.nextToken();
			String prodi = st1.nextToken();
			String id = st1.nextToken();
			out.print("&nbsp&nbsp&nbsp&nbsp&nbsp-"+Converter.getDetailKdpst_v1(prodi)+"<br/>");
			
		}
	}
}
else {
%>
<div style="font-weight:bold;font-size:1.5em;padding:10px 50px">List Jabatan : 0 Result</div>
<%	
}
%>
		</br/>
	</div>
</div>
</body>
</html>