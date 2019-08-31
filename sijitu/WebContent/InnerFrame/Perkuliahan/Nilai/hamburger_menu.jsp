<!DOCTYPE html><html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.sistem.AskSystem"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

String thsms= request.getParameter("thsms");
String idkmk=request.getParameter("idkmk");
String uniqueId=request.getParameter("uniqueId");
String kdkmk=request.getParameter("kdkmk");
String nakmk=request.getParameter("nakmk");
String shiftKelas=request.getParameter("shift");
String bolehEdit=request.getParameter("bolehEdit");
//System.out.println("bolehEdit="+bolehEdit);
String nmmdos= request.getParameter("nmmdos");
String npmdos= request.getParameter("npmdos");
boolean editable = Boolean.parseBoolean(bolehEdit);
boolean syDsnNya = false;
if(npmdos.equalsIgnoreCase(validUsr.getNpm())) {
	syDsnNya = true;
}
String group_proses = request.getParameter("group_proses");

String noKlsPll = request.getParameter("noKlsPll");
String kode_kelas = request.getParameter("kode_kelas");
String kode_gedung = request.getParameter("kode_gedung");
String kode_kampus = request.getParameter("kode_kampus");
String kode_gabung_kls = request.getParameter("kode_gabung_kls");
String kode_gabung_kmp = request.getParameter("kode_gabung_kmp");
String skstm = request.getParameter("skstm");
String skspr = request.getParameter("skspr");
String skslp = request.getParameter("skslp");
Vector vListNpmhsAndInfoNilai = (Vector) request.getAttribute("vListNpmhsAndInfoNilai");
session.setAttribute("v_tmp", vListNpmhsAndInfoNilai);
//20201`2020112100007`12710250004`HEVMYI RANDO KURNIAWAN`REGULER MALAM`L`20121`2020112100007`66.9`B-`false`2.75
//go.downloadVec2Excl
%>
</head>
<body>
<form name="input_form" action="go.updNilaiViaExcl" method="POST">
<input type="hidden" name="thsms" value="<%=thsms %>" />
<input type="hidden" name="uniqueId" value="<%=uniqueId %>" />
<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
<input type="hidden" name="nakmk" value="<%=nakmk %>" />
<input type="hidden" name="shiftKelas" value="<%=shiftKelas %>" />
<input type="hidden" name="nmmdos" value="<%=nmmdos %>" />
<input type="hidden" name="npmdos" value="<%=npmdos %>" />
<input type="hidden" name="noKlsPll" value="<%=noKlsPll %>" />
<button type="button" class="btn btn-default" onclick="document.input_form.submit()">
	<span class="glyphicon glyphicon-floppy-save"></span> Download
</button>
</form>
</body>
</html>