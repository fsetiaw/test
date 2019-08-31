<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>

<%@ page import="beans.tools.filter.FilterKampus" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v = (Vector)session.getAttribute("vf");
String target_thsms = (String)request.getParameter("target_thsms");
String fullname_table_rules = (String)request.getParameter("fullname_table_rules");
String tipe_pengajuan = fullname_table_rules.replace("_RULES", "");
String title_pengajuan = tipe_pengajuan.replace("_", " ");
String atKmp = (String)request.getParameter("target_kampus");
String scope_kampus = validUsr.getScopeKampus("ov");
scope_kampus =FilterKampus.kampusAktifOnly(target_thsms, scope_kampus);

%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<ul>
	<li><a href="get.sebaranTrlsm?target_thsms=<%=target_thsms %>" target="_self" >BACK<span>&nbsp</span></a></li>
<%
StringTokenizer st = new StringTokenizer(scope_kampus,",");
while(st.hasMoreTokens()) {
	String kode_kmp = st.nextToken();
	String nm_kmp = Checker.getNickNameKampus(kode_kmp);
	nm_kmp = nm_kmp.replace(kode_kmp+"`KAMPUS ", "");
	
	if(kode_kmp.equalsIgnoreCase(atKmp)) {
		
		%>
	<li><a href="go.getWhoRequest?fullname_table_rules=<%=fullname_table_rules %>&target_thsms=<%=target_thsms %>&target_kampus=<%=kode_kmp %>" class="active">KAMPUS<span><%=nm_kmp %></span></a></li>
	<%	
	}
	else {
%>
	<li><a href="go.getWhoRequest?fullname_table_rules=<%=fullname_table_rules %>&target_thsms=<%=target_thsms %>&target_kampus=<%=kode_kmp %>">KAMPUS<span><%=nm_kmp %></span></a></li>
<%	
	}
}
%>
</ul>


</body>
</html>