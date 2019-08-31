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
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String id_std = request.getParameter("id_std");
String am_i_pengawas = request.getParameter("am_i_pengawas");
String am_i_terkait = request.getParameter("am_i_terkait");
String backto = request.getParameter("backto");
String src_limit = "3";
String starting_no = "1";
%>
</head>
<body>
<div id="header">
	<ul>
<%
/*
if(!Checker.isStringNullOrEmpty(backto) && backto.equalsIgnoreCase("overview")) {
	%>	
	<li><a href="go.prepDashOverviewSpmi?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&fwdto=overview" target="inner_iframe">BACK <span><b style="color:#eee">---</b></span></a></li>
<%	
}
else {
%>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
}
*/
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("plan")) {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_plan.jsp" class="active">MANUAL PERENCANAAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_plan.jsp">MANUAL PERENCANAAN<br>STANDAR</a></li>
<%	
}
%>
<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("do")) {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=do&fwdto=dashboard_std_manual_do.jsp" class="active">MANUAL PELAKSANAAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=do&fwdto=dashboard_std_manual_do.jsp">MANUAL PELAKSANAAN<br>STANDAR</a></li>
<%	
}
%>

<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("control")) {
	
%>	
	<li><a href="javascript:window.open('get.prepInfoStd?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=control&fwdto=dashboard_std_manual_pengendalian_eval.jsp','','height=screen.height,width=screen.width');" class="active">MANUAL EVALUASI <br>PELAKSANAAN STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="javascript:window.open('get.prepInfoStd?starting_no=1&limit=<%=src_limit %>&backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=control&fwdto=dashboard_std_manual_pengendalian_eval.jsp','','height=screen.height,width=screen.width');">MANUAL EVALUASI <br>PELAKSANAAN STANDAR</a></li>
<%	
}
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("kendali")) {
	
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=kendali&fwdto=dashboard_std_manual_pengendalian_kendali.jsp" class="active">MANUAL PENGENDALIAN<br>PELAKSANAAN STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=kendali&fwdto=dashboard_std_manual_pengendalian_kendali.jsp">MANUAL PENGENDALIAN<br>PELAKSANAAN STANDAR</a></li>
<%	
}
%>
<%
if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("upgrade")) {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=upgrade&fwdto=dashboard_std_manual_upgrade.jsp" class="active">MANUAL PENINGKATAN<br>STANDAR</a></li>
<%
}
else {
%>	
	<li><a href="get.prepInfoStd?backto=<%=backto %>&am_i_pengawas=<%=am_i_pengawas %>&am_i_terkait=<%=am_i_pengawas %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=upgrade&fwdto=dashboard_std_manual_upgrade.jsp">MANUAL PENINGKATAN<br>STANDAR</a></li>
<%	
}
%>	


	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

	</div>
</div>		
</body>
</html>