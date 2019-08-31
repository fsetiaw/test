<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
String backTo =  request.getParameter("backTo");
String tipe_pengajuan = request.getParameter("tipe_pengajuan");
String keter = "";
String target_thsms = request.getParameter("target_thsms");
Vector v= null;
if(tipe_pengajuan.equalsIgnoreCase("K")) {
	keter = "[KELUAR]";
}
else if(tipe_pengajuan.equalsIgnoreCase("D")) {
	keter = "[D.O.]";
} 
else if(tipe_pengajuan.equalsIgnoreCase("L")) {
	keter = "[LULUS]";
}
else if(tipe_pengajuan.equalsIgnoreCase("N")) {
	keter = "[NON AKTIF]";
}
else if(tipe_pengajuan.equalsIgnoreCase("P")) {
	keter = "[PINDAH PRODI]";
}
%>

</head>
<body onload="location.href='#'">
<div id="header">
<ul>
	<li><a href="" target="_self" >HOME <span><b style="color:#eee">---</b></span></a></li>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<br />
		<%
		if(validUsr.isUsrAllowTo("rstStm", npm, obj_lvl)){
		%>
		JADi RESET PENGAJUAN <%=keter+" ["+target_thsms+"]" %>? <%=npm %>-<%=nmm %>-<%=kdpst %>
		<form action="go.resetPengajuan">
			<input type="hidden" name="target_thsms" value="<%=target_thsms %>" />
			<input type="hidden" name="tipe_pengajuan" value="<%=tipe_pengajuan %>" />
			<input type="hidden" name="objId" value="<%=objId %>" />
			<input type="hidden" name="nmm" value="<%=nmm %>" />
			<input type="hidden" name="npm" value="<%=npm %>" />
			<input type="hidden" name="kdpst" value="<%=kdpst %>" />
			<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />	
			<input type="submit" value="RESET PENGAJUAN" style="color:red;font-weight:bold" />
		</form>
		<%
		}
		%>
	</div>
</div>	
</body>
</html>