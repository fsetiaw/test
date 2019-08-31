<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String atMenu = request.getParameter("atMenu");
String backTo = request.getParameter("backTo");
//System.out.println("atMenu="+atMenu);
//int at_page = Integer.parseInt(request.getParameter("at_page"));

%>
<div id="header">
	<ul>
<%
if(backTo!=null && backTo.equalsIgnoreCase("mutu")) {
	//String atMenu = request.getParameter("atMenu");
	String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
	String id_std_isi = request.getParameter("id_std_isi");
	String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index.jsp";
	
	
	
	//System.out.println("?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas="+pihak_mon);;
	//request.getRequestDispatcher(url+"?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu=edit_isi&tkn_pengawas="+pihak_mon).forward(request,response);
	%>
	
		<li><a href="<%=target %>?mode=edit_list_view&id_std_isi=<%=id_std_isi%>&atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
}
else {
	if(!Checker.isStringNullOrEmpty(atMenu) && atMenu.equalsIgnoreCase("edit")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/jabatan/list_jabatan.jsp?atMenu=list" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
	}
	else {

%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/home_sdm.jsp" target="inner_iframe">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
		if(!Checker.isStringNullOrEmpty(atMenu) && atMenu.equalsIgnoreCase("list")) {
	%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/jabatan/list_jabatan.jsp?atMenu=list" target="inner_iframe" class="active">LIST JABATAN <span>STRUKTURAL</span></a></li>
<%		
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/jabatan/list_jabatan.jsp?atMenu=list" target="inner_iframe">LIST JABATAN <span>STRUKTURAL</span></a></li>
<%	
		}
		if(validUsr.isHakAksesAllawInsert("sdm")) {
			if(!Checker.isStringNullOrEmpty(atMenu) && atMenu.equalsIgnoreCase("form")) {
%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/jabatan/form_tambah_jabatan.jsp?atMenu=form" target="inner_iframe" class="active">TAMBAH JABATAN <span>STRUKTURAL BARU</span></a></li>
<%	
			}
			else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/jabatan/form_tambah_jabatan.jsp?atMenu=form" target="inner_iframe">TAMBAH JABATAN <span>STRUKTURAL BARU</span></a></li>
<%		
			}
		}
	}
}
%>	
	</ul>
</div>

</head>
<body>
</body>
</html>