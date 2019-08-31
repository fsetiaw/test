<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
AddHocFunction adh = new AddHocFunction();
Vector vf = adh.findIdobjKdpstError();
if(vf!=null) {
	ListIterator li = vf.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		out.print(brs+"<br/>");
	}
}
%>
</body>
</html>