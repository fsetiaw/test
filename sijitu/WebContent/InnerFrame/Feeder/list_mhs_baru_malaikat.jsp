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
	<%@ page import="beans.dbase.mhs.*" %>
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
  //UpdateDbTrnlm udt = new UpdateDbTrnlm();
String prodi = request.getParameter("tags6");
StringTokenizer st = new StringTokenizer(prodi,"`");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String thsms = request.getParameter("thsms");
SearchDbInfoMhs sdm = new SearchDbInfoMhs();
Vector v = sdm.listMhsMalaikat(thsms, "B", kdpst);
  %>
</head>
<body>
LIST MHS BARU MALAIKAT <%=thsms %> <%=prodi %> 
<%
if(v!=null) {
	int i=1;
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String npmhs = st.nextToken();
		String nmmhs = st.nextToken();
%>
	<%=i++ %>. [<%=npmhs %>]&nbsp&nbsp<%=nmmhs %><br>
<%		
	}
}
else {
	out.print("null");
}
%> 
</body>
</html>