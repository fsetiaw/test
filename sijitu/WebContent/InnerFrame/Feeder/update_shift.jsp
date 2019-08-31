<!doctype html>
<html lang="en">
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.*" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="beans.dbase.feeder.*" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
  <meta charset="utf-8">
 
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Autocomplete - Default functionality</title>
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/jquery-ui.css">
  <script src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/js/jquery-ui.js"></script>
	<%
	String[]list_npm = request.getParameterValues("list_npm");
	String[]target_shift = request.getParameterValues("target_shift");
	String[]angkatan = request.getParameterValues("angkatan");
	String[]prodi = request.getParameterValues("prodi");
	String thsms = request.getParameter("thsms");
	%>	
</head>
<body>
	<%
	UpdateDbFeeder udf = new UpdateDbFeeder();
	udf.updateShift(list_npm[0], target_shift[0]);
	%>
	<%=angkatan[0] %><br>
	<%
	StringTokenizer st = new StringTokenizer(list_npm[0],",");
	out.print(st.countTokens()+"<br>");
	out.print(list_npm[0]+"<br>");
	out.print(prodi[0]+"<br>");
	%><br>
	<%=target_shift[0] %><br>
	<jsp:forward page="info_prodi_shift.jsp">
	<jsp:param value="<%=prodi[0] %>" name="tags2"/> 
	<jsp:param value="<%=thsms %>" name="thsms"/> 
	</jsp:forward>
	<%
	%>
</body>
</html>