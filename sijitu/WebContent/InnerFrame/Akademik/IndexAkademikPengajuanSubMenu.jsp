<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>

<%
	//var validUsr di initialize di main pagenya spt formPengajuanBukaKelas.jsp
	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//String cmd = request.getParameter("cmd");
	//caller page buat menentukan class-aktif
	String backTo= request.getParameter("backTo");
	if(backTo!=null) {
		backTo = backTo.replace("Titik", ".");
		backTo = backTo.replace("TandaTanya", "?");
		backTo = backTo.replace("SamaDgn", "=");
	}
	String nambah_kelas = request.getParameter("kelasTambahan");
	boolean penambahan_kelas = false;
	if(nambah_kelas!=null && nambah_kelas.equalsIgnoreCase("yes")) {
		penambahan_kelas=true;
	}
	//System.out.println("kelasTambahan="+nambah_kelas);
%>
<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>

</head>
<body>

<ul>
<%

String target = "";
String uri = request.getRequestURI();
if(uri.contains("formPengajuanBukaKelas")) {
	target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
}
else {
	target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
}
//System.out.println("uri="+uri);
String url = PathFinder.getPath(uri, target);
if(backTo==null || Checker.isStringNullOrEmpty(backTo)) {
%>
<li><a href="javascript:history.go(-1)" >BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
}
else {
	%>
<li><a href="<%=backTo %>" >BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%	
}
/*
if(atFormPengajuanBukaKelasTahap1) {
	String backward = "go.getListKurikulum?atMenu=bukakelas&callerPage="+callerPage+"&cmd=bukakelas&kdpst_nmpst="+kdpst_nmpst+"&scope=reqBukaKelas";

%>
	<li><a href="<%=backward %>">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
}
else {
	if(atFormPengajuanBukaKelasTahap2) {
//System.out.println("bakward2 = "+backward2);
	backward2 = backward2.replace("#&","||");
//System.out.println("bakward2 upd= "+backward2);
	%>
		<li><a href="<%=backward2%>" >BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%		
	}
	else {
%>
		<li><a href="<%=url %>">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
	}
}
*/

if(backTo!=null && !Checker.isStringNullOrEmpty(backTo)) {
	backTo = backTo.replace(".","Titik");
	backTo = backTo.replace("?","TandaTanya");
	backTo = backTo.replace("=","SamaDgn");
}
else {
	backTo = "null";
}


if(false) {
/*
* UPDATE : tidak dibutuhkan semenjak mo dipisahkan antara pengajuan, penggabungan dan perubahan dosen ajar
*/
//if(validUsr.isAllowTo("reqBukaKelas")>0 && !penambahan_kelas) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	
	if(atMenu!=null && atMenu.equalsIgnoreCase("bukaKelas")) {
		%>		
		<!--  li><a href="<%=url %>?scope=reqBukaKelas&callerPage=dashPengajuan.jsp&cmd=bukakelas&atMenu=bukakelas&backTo=<%=backTo %>" target="_self" class="active">PENGAJUAN BUKA<span>KELAS PERKULIAHAN</b></span></a></li -->
		<li><a href="<%=url %>?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=<%=backTo %>" target="_self" class="active">PENGAJUAN BUKA<span>KELAS PERKULIAHAN</b></span></a></li>
<%		
	}
	else {
%>		
		<!--  li><a href="<%=url %>?scope=reqBukaKelas&callerPage=dashPengajuan.jsp&cmd=bukakelas&atMenu=bukakelas&backTo=<%=backTo %>" target="_self">PENGAJUAN BUKA<span>KELAS PERKULIAHAN</b></span></a></li -->
		<li><a href="<%=url %>?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=<%=backTo %>" target="_self">PENGAJUAN BUKA<span>KELAS PERKULIAHAN</b></span></a></li>
<%
	}
}

if(false) {
//if(validUsr.isAllowTo("reqGabungKelasFak")>0 && !penambahan_kelas) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("gabungKelasFak")) {
		%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqGabungKelasFak&callerPage=dashPengajuan.jsp&cmd=gabungKelasFak&atMenu=gabungKelasFak&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self" class="active">PENGAJUAN PENGGABUNGAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqGabungKelasFak&callerPage=dashPengajuan.jsp&cmd=gabungKelasFak&atMenu=gabungKelasFak&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self">PENGAJUAN PENGGABUNGAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%
	}
}
/*
if(validUsr.isAllowTo("reqBatalKelasFak")>0 && !penambahan_kelas) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("batalKelasFak")) {
		%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqBatalKelasFak&callerPage=dashPengajuan.jsp&cmd=batalKelasFak&atMenu=batalKelasFak&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self" class="active">PENGAJUAN PEMBATALAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqBatalKelasFak&callerPage=dashPengajuan.jsp&cmd=batalKelasFak&atMenu=batalKelasFak&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self">PENGAJUAN PEMBATALAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%
	}
}
*/
if(false) {
//if(validUsr.isAllowTo("reqUbahDsnAjar")>0 && !penambahan_kelas) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("ubahDosenAjar")) {
		%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqUbahDsnAjar&callerPage=dashPengajuan.jsp&cmd=ubahDosenAjar&atMenu=ubahDosenAjar&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self" class="active">PENGAJUAN PERUBAHAN<span>DOSEN AJAR</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=url %>?nuFwdPage=get.listKelasClassPool&scope=reqUbahDsnAjar&callerPage=dashPengajuan.jsp&cmd=ubahDosenAjar&atMenu=ubahDosenAjar&scopeType=prodyOnly&backTo=<%=backTo %>" target="_self">PENGAJUAN PERUBAHAN<span>DOSEN AJAR</b></span></a></li>
<%
	}
}
%>
	
	
</ul>

</body>
</html>