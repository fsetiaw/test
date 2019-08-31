<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="beans.dbase.trnlm.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.*" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="beans.dbase.dosen.*" %>
	<%@ page import="beans.dbase.trlsm.*" %>
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
  UpdateDbTrnlm udt = new UpdateDbTrnlm();
  String thsms = request.getParameter("thsms");
  udt.updateNilaiMalaikat(thsms);
  %>
</head>
<body>
UPDATE NILAI SELESAI
</body>
</html>