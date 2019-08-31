<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>
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
String mode=request.getParameter("mode");
//System.out.println("mode="+mode);
String id_tipe_std=request.getParameter("id_tipe_std");
//System.out.println("id_tipe_std="+id_tipe_std);

String id_master_std=request.getParameter("id_master_std");
//System.out.println("id_master_std="+id_master_std);
String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();



System.out.println("mode="+mode);
%>

<div id="header">
	<ul>
		<li><a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=nama_std" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>
</div>
</head>
<body>
</body>
</html>