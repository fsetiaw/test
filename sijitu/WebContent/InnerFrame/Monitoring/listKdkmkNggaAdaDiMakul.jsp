<!DOCTYPE html>

<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%
Vector v = (Vector)request.getAttribute("v_err");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int i = 0;
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		out.println(brs+"<br>");
	}
}
%>
</body>
</html>