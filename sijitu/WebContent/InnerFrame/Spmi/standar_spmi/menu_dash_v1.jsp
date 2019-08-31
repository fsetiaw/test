<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.Constants"%>
<%@ page import="beans.dbase.spmi.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
Vector v= (Vector)request.getAttribute("v");
request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String am_i_pengawas = request.getParameter("am_i_pengawas");
String am_i_terkait = request.getParameter("am_i_terkait");
String backto = request.getParameter("backto");
//System.out.println("backti="+backto);
String src_limit = "3";
String starting_no = "1";
%>
</head>
<body>
<div id="header">
	<ul>
	<li><a href="go.getListAllStd?mode=start&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std">BACK<br>&nbsp</a></li>
<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("plan")) {	
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_perencanaan_v1.jsp" class="active">MANUAL PENETAPAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_perencanaan_v1.jsp">MANUAL PENETAPAN<br>STANDAR</a></li>
<%	
}
%>
<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("do")) {
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=do&fwdto=dashboard_std_manual_pelaksanaan_v1.jsp" class="active">MANUAL PELAKSANAAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=do&fwdto=dashboard_std_manual_pelaksanaan_v1.jsp">MANUAL PELAKSANAAN<br>STANDAR</a></li>
<%	
}
%>

<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("control")) {
	
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=control&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp" class="active">MANUAL EVALUASI <br>PELAKSANAAN STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=control&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp">MANUAL EVALUASI <br>PELAKSANAAN STANDAR</a></li>
<%	
}
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("kendali")) {
	
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=kendali&fwdto=dashboard_std_manual_pengendalian_kendali_v1.jsp" class="active">MANUAL PENGENDALIAN<br>PELAKSANAAN STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoMan_v1?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=kendali&fwdto=dashboard_std_manual_pengendalian_kendali_v1.jsp">MANUAL PENGENDALIAN<br>PELAKSANAAN STANDAR</a></li>
<%	
}

if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("peningkatan")) {
%>	
	<li><a href="get.prepInfoMan_v1?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=peningkatan&fwdto=dashboard_std_manual_peningkatan_v1.jsp" class="active">MANUAL PENINGKATAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoMan_v1?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=peningkatan&fwdto=dashboard_std_manual_peningkatan_v1.jsp">MANUAL PENINGKATAN<br>STANDAR</a></li>
<%	
}
%>	


	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
	</div>
</div>		
</body>
</html>