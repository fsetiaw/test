<!DOCTYPE html><html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.sistem.AskSystem"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
String versi_std = request.getParameter("versi_std");
String id_std = request.getParameter("id_std");
String tipe = request.getParameter("tipe");
String no_dok_spmi = request.getParameter("no_dok_spmi");

%>
</head>
<body>
<form name="input_form" action="go.downloadMan?no_dok_spmi=<%=no_dok_spmi %>&versi_std=<%=versi_std %>&id_std=<%=id_std %>&tipe=<%=tipe.toUpperCase() %>&output_file_name=manual" method="POST">
<button type="button" class="btn btn-default" onclick="document.input_form.submit()">
	<span class="glyphicon glyphicon-floppy-save"></span> Download
</button>
</form>
</body>
</html>