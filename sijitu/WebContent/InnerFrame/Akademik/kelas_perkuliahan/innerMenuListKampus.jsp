<!DOCTYPE html>
<head>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.PathFinder"%>
<%@ page import="beans.tools.Checker"%>
<%@ page import="beans.login.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
</head>
<body>

	<%
	//System.out.println("jiahh");
	String list_kampus = request.getParameter("list_kampus");
	String atKmp = request.getParameter("atKmp");
	String scope_cmd = request.getParameter("scope_cmd");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//	String atMenu = request.getParameter("atMenu");
//	String backOnly = request.getParameter("MenuBackOnly");
	String backTo= request.getParameter("backTo");
	//System.out.println("bakto="+backTo);
//	if(backTo!=null) {
//		backTo = backTo.replace("Titik", ".");
//		backTo = backTo.replace("TandaTanya", "?");
//		backTo = backTo.replace("SamaDgn", "=");
//	}
	
	%>
<ul>		
 <%	
 	if(backTo!=null && !Checker.isStringNullOrEmpty(backTo)) {
 		%>
 	 <li><a href="<%=backTo %>" target="_self">BACK <span><b>&nbsp</b></span></a></li>
 	 		<%
 	}
 	else {
 		
 		%>
 	<li><a href="prep.infoListProdiNoClass?scope_cmd=<%=scope_cmd %>" target="_self">BACK <span><b>&nbsp</b></span></a></li>
 		<%
 	}	
if(list_kampus!=null && !Checker.isStringNullOrEmpty(list_kampus)) {
	StringTokenizer st = new StringTokenizer(list_kampus,"`");
	while(st.hasMoreTokens()) {
		String tkn = st.nextToken();
		tkn = tkn.substring(1,tkn.length()-1); //removing bracket jason object format
		StringTokenizer st1 = new StringTokenizer(tkn,",");
		String kode = st1.nextToken();
		String kmp = st1.nextToken();
		if(kode.equalsIgnoreCase(atKmp)) {
			%>
		<li><a href="get.statusPengajuanKelasKuliah?atMenu=form&atKmp=<%=kode %>&backTo=<%=backTo %>" target="_self" class="active" >KAMPUS<span><%=kmp.replace("KAMPUS ", "") %></span></a></li>
	 		<% 			
		}
		else {
		%>
		<li><a href="get.statusPengajuanKelasKuliah?atMenu=form&atKmp=<%=kode %>&backTo=<%=backTo %>" target="_self" >KAMPUS<span><%=kmp.replace("KAMPUS ", "") %></span></a></li>
 		<% 
		}
	}	
		

 }
%>
</ul>

</body>
</html>
	