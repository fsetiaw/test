<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>



</head>
<!--  body onload="location.href='#'" -->
<%
Vector v_list_unapproved = (Vector) request.getAttribute("v_noapr");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//System.out.println("v_list_unapproved1="+v_list_unapproved.size());
String scope_kmp = validUsr.getScopeKampus("hasHeregitrasiMenu");
//System.out.println("scope_kmp="+scope_kmp);	
String at_kmp = request.getParameter("at_kmp");
%>
<body>
<ul>

		<li><a href="get.sebaranTrlsm">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
if(scope_kmp!=null && !Checker.isStringNullOrEmpty(scope_kmp)) {
	StringTokenizer st= new StringTokenizer(scope_kmp,",");
	while(st.hasMoreTokens()) {
		String kd_kmp = st.nextToken();
		String baris = Converter.getNamaKampus(kd_kmp);
		StringTokenizer st1 = new StringTokenizer(baris);
		String first= st1.nextToken();
		String second = "";
		if(st1.hasMoreTokens()) {
			second = st1.nextToken();
		}
		if(at_kmp!=null && at_kmp.equalsIgnoreCase(kd_kmp)) {
				
	%>
		<li><a href="get.whoRegisterUnapproved?at_kmp=<%=kd_kmp %>" class="active"><%=first %> <span><%=second %></span></a></li>
	<%			
		}
		else {
%>
		<li><a href="get.whoRegisterUnapproved?at_kmp=<%=kd_kmp %>"><%=first %> <span><%=second %></span></a></li>
<%		
		}
	}
}
%>
</ul>

</body>
</html>