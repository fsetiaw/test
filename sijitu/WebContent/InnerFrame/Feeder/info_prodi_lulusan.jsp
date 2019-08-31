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
  String prodi = request.getParameter("tags5");
  String thsms = request.getParameter("thsms");
  StringTokenizer st = new StringTokenizer(prodi,"`");
  String kdpst = st.nextToken();
  String nmpst = st.nextToken();
  SearchDbFeeder sdf = new SearchDbFeeder();
  Vector v = null;
  if(kdpst.equalsIgnoreCase("88888")) {
	  v = sdf.getInfoMalaikatAndReal(thsms, "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_86208_20152.txt");
  }
  else {
	  v = sdf.getInfoMalaikatAndReal(thsms, "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_"+kdpst+"_20152.txt");  
  }
  Vector vmhs = null;
  Vector vang = null;
  SearchDbTrlsm sdt = new SearchDbTrlsm();
  v=sdt.listLulusan(kdpst,thsms);
  %>
</head>
<body>
	kelulusan
<%
if(v==null || v.size()<1) {
	out.print("TIDAK ADA LULUSAN "+thsms);
	
}
else {
	int no=1;
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		out.print(no+++"."+brs+"<br>");
	}
}
%>
</body>
</html>