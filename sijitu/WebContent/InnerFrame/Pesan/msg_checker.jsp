<!DOCTYPE html>
<html>
<head lang="en">

	<%@ page import="java.util.concurrent.TimeUnit" %>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
  	
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String grp_conversation_nm = request.getParameter("grp_conversation_nm");
String grp_conversation_id = request.getParameter("grp_conversation_id");

String npm_indiv = request.getParameter("npm_indiv");
String str_range = request.getParameter("str_range");
String cur_sta_index = request.getParameter("cur_sta_index");
//System.out.println("--------------------------------------------");
//System.out.println("grp_conversation_nm="+grp_conversation_nm);
//System.out.println("grp_conversation_id="+grp_conversation_id);
//System.out.println("npm_indiv="+npm_indiv);
//System.out.println("cur_sta_index="+cur_sta_index);

//System.out.println("--------------------------------------------");
//cek pesan baru
JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/dashboard/"+validUsr.getNpm()+"/ada_pesan_baru/"+grp_conversation_id);

%>
<meta http-equiv="refresh" content="15; url=<%=Constants.getRootWeb() %>/InnerFrame/Pesan/msg_checker.jsp?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=0&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>">
<script>
function myFunction() {
	document.getElementById('binggo').click();
	//parent.location.href=parent.location.href;
//parent.location.reload();
}
</script>

</head>

	
<%
if(jsoa!=null && jsoa.length()>0) {
	JSONObject job = jsoa.getJSONObject(0);
	try {
		String ada_pesan = (String)job.get("NU_MSG");
		if(ada_pesan.equalsIgnoreCase("true")) {
%>

<body onload="myFunction()">
	<a id="binggo" href="prep.conversation?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=0&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>" target="_parent">
	</a>
<%
		}
		else {
			%>
			<body>
				<%				
		}
		out.print("ada_pesan="+ada_pesan);
	}
	catch(JSONException e) {}//ignore
}
else {
	%>
<body>
	<%	
}
%>
</body>
</html>