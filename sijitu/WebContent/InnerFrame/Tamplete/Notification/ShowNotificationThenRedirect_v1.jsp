<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
String msg = request.getParameter("msg");
String redirectTo = request.getParameter("redirectTo");
String paramNeeded = request.getParameter("paramNeeded");
if(paramNeeded!=null) {
	paramNeeded = paramNeeded.replace("``", "=");
	paramNeeded = paramNeeded.replace("`", "&");
}
redirectTo = redirectTo+"?"+paramNeeded;
%>
</head>
<body>
<h2 align="center"><%=msg %></h2>

<META HTTP-EQUIV="refresh" CONTENT="3; URL=<%=redirectTo%>">
</body>
</html>