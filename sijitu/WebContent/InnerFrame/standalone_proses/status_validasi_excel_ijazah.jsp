<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String stm = (String)session.getAttribute("status_akhir_mahasiswa");
Vector v= (Vector)request.getAttribute("v_mismatch");

%>

</head>
<body>
<div id="header">

</div>
<div class="colmask fullpage">
	<div class="col1">

	
<%
if(v!=null) {
	out.print("<h2 align=\"center\">HASIL VALIDASI:</h2><br>");
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		out.print(brs+"<br>");
	}

}
else {
	out.print("<h2 align=\"center\">HASIL VALIDASI:</h2><br><center>DATA BERHASIL DIUPDATE TANPA ERROR</center><br>");
}
%>	
	</div>
</div>	

</body>
</html>