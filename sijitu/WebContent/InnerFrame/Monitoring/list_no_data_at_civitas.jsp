<!DOCTYPE html>
<head>

<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.wilayah.*" %>
<%
AddHocMonitorFunction amf = new AddHocMonitorFunction(); 
Vector v = amf.cekDataYgAdaDiExtCivitasTpTidakAdaDiCivitas();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
if(v!=null && v.size()>0) {
	int i=1;
	out.print("LIST NO PARENT INFO<br><br>");
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		out.print(i+++"."+brs+"<br>");
	}
}
%>
<br>SELESAI
</body>
</html>