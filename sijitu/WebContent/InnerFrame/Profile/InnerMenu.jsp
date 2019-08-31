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
<title>Insert title here</title>



</head>
<!--  body onload="location.href='#'" -->
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String cmd = request.getParameter("cmd");
String atMenu = request.getParameter("atMenu");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String nim = Checker.getNimhs(npm);
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
//System.out.println("com="+cmd);
String target = "get.profile_v1";
boolean target_is_mhs = false;
if(Checker.getObjDesc(Integer.parseInt(id_obj)).contains("MHS")) {
	target_is_mhs = true;
}
boolean pindahan = Checker.mhsPindahan(npm);
boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));
//System.out.println("target_is_mhs="+target_is_mhs);
%>

<body>
	<ul>
<%
if(!wajib_update_profile) {
%>	
		<li><a href="get.profile?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=dashboard" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
}
String label = "MAHASISWA";
if(atMenu.equalsIgnoreCase("pribadi")) {
	
	
	if(!target_is_mhs) {
		label = "CIVITAS";
	}
	if(wajib_update_profile) { //AGAR DEFAULTNYA EDIT 
		cmd="edit";
		%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=pribadi" target="_self" class="active">DATA PROFILE<span><%=label %></span></a></li>
<%		
	}
	else {
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=pribadi" target="_self" class="active">DATA PROFILE<span><%=label %></span></a></li>
<%	
	}
}
else {
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=pribadi" target="_self">DATA PROFILE<span><%=label %></span></a></li>
<%	
}
if(target_is_mhs && !wajib_update_profile) {
	if(atMenu.equalsIgnoreCase("kemhsan") ) {
	
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=kemhsan" target="_self" class="active">DATA PROFILE<span>KEMAHASISWAAN</span></a></li>
<%	
	}
	else {
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=kemhsan" target="_self">DATA PROFILE<span>KEMAHASISWAAN</span></a></li>
<%	
	}
}
if(atMenu.equalsIgnoreCase("ortu")) {
	if(wajib_update_profile) {
		cmd="edit";
		%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=ortu" target="_self" class="active">DATA PROFIL<span>ORANG TUA & WALI</span></a></li>
<%		
	}
	else {
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=ortu" target="_self" class="active">DATA PROFIL<span>ORANG TUA & WALI</span></a></li>
<%
	}
}
else {
%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=ortu" target="_self">DATA PROFIL<span>ORANG TUA & WALI</span></a></li>
<%	
}
if(false) { //KALO MO IDPAKE IF nya PAKE YG DIBAWAH
//if(target_is_mhs || mhs_yg_edit) {
	if(atMenu.equalsIgnoreCase("work")) {
	
%>
		<li><a href="#" target="_self" class="active">DATA INFO<span>PEKERJAAN</span></a></li>
<%	
	}
	else {
%>
		<li><a href="#" target="_self">DATA INFO<span>PEKERJAAN</span></a></li>
<%	
	}
}

if(!target_is_mhs) {
	if(validUsr.isUsrAllowTo_updated("allowViewDataDosen",npm)) {
		if(atMenu.equalsIgnoreCase("dsn")) {
		
		%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=dsn" target="_self" class="active">DATA PROFIL<span> DOSEN </span></a></li>
			<%	
		}
		else {
		%>
		<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=dsn" target="_self">DATA PROFIL<span> DOSEN </span></a></li>
		<%	
		}
	}
	
	if(validUsr.isUsrAllowTo("js", npm, obj_lvl)) {
		if(cmd!=null && cmd.equalsIgnoreCase("js")) {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=js&cmd=js" target="_self" class="active">JABATAN<span>STRUKTURAL</span></a></li>
<%
		}
		else {
%>
	<li><a href="get.jabatanStruk?nim=<%=nim %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=js&cmd=js" target="_self" >JABATAN <span>STRUKTURAL</span></a></li>
<%
		}
	}
}

if(validUsr.isUsrAllowTo("insSksdi", npm, obj_lvl)||validUsr.isUsrAllowTo("penyetaraan", npm, obj_lvl)) {
	if(atMenu.equalsIgnoreCase("pindahan")) {
	%>
	<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=pindahan" target="_self" class="active">DATA MATAKULIAH<span>TRANSFER/PINDAHAN</span></a></li>
	<%		
	}
	else {
		%>
	<li><a href="<%=target %>?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&atMenu=pindahan" target="_self">DATA MATAKULIAH<span>TRANSFER/PINDAHAN</span></a></li>
		<%		
	}
}

%>		
	</ul>
		
</body>
</html>