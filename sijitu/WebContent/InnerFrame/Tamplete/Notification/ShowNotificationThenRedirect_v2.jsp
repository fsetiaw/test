<!DOCTYPE html>
<html>
<head>
<%@ page import="beans.tools.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
String timeout = request.getParameter("timeout");
if(timeout==null || Checker.isStringNullOrEmpty(timeout)) {
	timeout = "3";
}
String msg = request.getParameter("msg");
String redirectTo = request.getParameter("redirectTo");
String paramNeeded = request.getParameter("paramNeeded");
if(paramNeeded!=null) {
	paramNeeded = paramNeeded.replace("``", "=");
	paramNeeded = paramNeeded.replace("`", "&");
}
redirectTo = redirectTo+"?"+paramNeeded;
//System.out.println("redirectTo="+redirectTo);
%>
</head>
<body>
<h2 align="center"><%=msg %></h2>

<META HTTP-EQUIV="refresh" CONTENT="<%=timeout %>; URL=<%=redirectTo%>">
</body>
</html>