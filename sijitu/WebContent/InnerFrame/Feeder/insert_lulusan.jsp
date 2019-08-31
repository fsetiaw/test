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
 Vector v1 = Tool.bacaFileExcel("wisudawan", "0`1`2`3`4`5`6`7`8", 2, 1000, 1);
 SearchDbInfoMhs sdm = new SearchDbInfoMhs();
 ListIterator li1 = v1.listIterator();
 Vector v_nimhs = new Vector();
 ListIterator lin = v_nimhs.listIterator();
 while(li1.hasNext()) {
	 String brs = (String)li1.next();
	 String nimhs = Tool.getTokenKe(brs, 5, "`");
	 if(nimhs.equalsIgnoreCase("11340357030")) {
		 nimhs = "11340357037";
	 }
	 lin.add(nimhs);
//	 out.println(nimhs+"<br>");
 }
 Vector v_no_match = sdm.cekApaVectorNimhsTdakAdaDiSistem(v_nimhs);
 if(v_no_match==null) {
	 out.println("no_problemo<br>");
	 UpdateDbTrlsm udt = new UpdateDbTrlsm();
	 udt.forcedInputLulusanViaExcel(v1,"28/11/2016");
 }
 else {
	 lin = v_no_match.listIterator();
	 while(lin.hasNext()) {
		 String brs = (String)lin.next();
		 out.print("NIMHS DIBAWAH INI TIDAK ADA DI SISTEM HARAP PERBAIKI:<br>");
		 out.print(brs+"<br>");
	 }
 }
 
 
 %>
</head>
<body>
UPDATE NILAI SELESAI
</body>
</html>