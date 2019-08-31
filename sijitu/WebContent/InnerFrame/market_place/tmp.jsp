<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="org.apache.commons.codec.digest.*" %>
<%@ page import="org.apache.http.client.*" %>

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
%>
</head>
<body>

<%

String feedback = (String)session.getAttribute("feedback");
String pesan_xml = (String)session.getAttribute("pesan_xml");
String signature = (String)session.getAttribute("signature");

session.removeAttribute("signature");
session.removeAttribute("feedback");
session.removeAttribute("pesan_xml");

%>

<form action="go.topupPulsa" method="POST">
	user_id : <input type="text" name="user_id" value="javra" style="width:150px"/><br>
	trx_id &nbsp&nbsp: <input type="text" name="trx_id" value="112233" style="width:150px"/><br>
	cmd &nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" name="cmd" value="TopupRequest" style="width:150px"/><br>
	pin &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" name="pin" value="6633" style="width:150px"/><br>
	cust_id : <input type="text" name="cust_id" value="0818111333" style="width:150px"/><br>
	product : <input type="text" name="product" value="TS10" style="width:150px"/><br>
	<input type="submit" value="submit"/>
</form>

<br>SIGNATURE:<br>
<%=signature %>
<br>XML REQUEST:<br>
<%
if(!Checker.isStringNullOrEmpty(pesan_xml)) {
	pesan_xml = pesan_xml.replace("<", "&#60;");
	out.print(pesan_xml);
}
else {
	out.print("null");
}
%><br>
<br>RESULT:<br>
<%
if(!Checker.isStringNullOrEmpty(feedback)) {
	feedback = feedback.replace("<", "&#60;");
	out.print(feedback);
}
%>
</body>
</html>