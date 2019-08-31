<!DOCTYPE html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
Vector v_err = (Vector) session.getAttribute("v_err");
String negara = request.getParameter("country");
%>
</head>
<body>

<%
int i=1;
if(v_err!=null && v_err.size()>0) {
	Collections.sort(v_err);
	ListIterator li = v_err.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		if(!brs.startsWith("-")) {
			out.print("<br/>"+i+++".&nbsp"+brs+"<br/>");	
		}
		else {
			out.print(brs+"<br/>");
		}
	}
}
else {
	out.print("NO ERROR");
}
%>
<br>
<%="selesai"%>
</body>
</html>