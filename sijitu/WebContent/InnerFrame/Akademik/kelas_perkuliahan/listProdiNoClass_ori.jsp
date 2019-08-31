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
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String target_thsms = request.getParameter("target_thsms"); 
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	String errMsg = request.getParameter("errMsg");
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String kdpst_nmpst = null;
	String backward2 = null;
	String scope_cmd = request.getParameter("scope_cmd");
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	String msg = request.getParameter("msg");
	String pb = request.getParameter("pb");
	String alihkan =request.getParameter("alihkan");
	Vector v_list_prodi_no_class = (Vector)request.getAttribute("v_list_prodi_no_class"); //PST`57301`MANAJEMEN INFORMATIKA`D`117
	request.removeAttribute("v_list_prodi_no_class");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">

<jsp:include page="innerMenu.jsp" />
 
 
 	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%

		
		//msg
		if(v_list_prodi_no_class!=null && v_list_prodi_no_class.size()>0) {
			ListIterator li = v_list_prodi_no_class.listIterator();
			String thsms_kelas = Checker.getThsmsBukaKelas();
			%>
			<div style="font-size:1.5em;font-style:bold;text-align:left;padding:0 0 0 50px">
			Harap melakukan pengajuan kelas perkuliahan untuk thn/sms <%=Converter.convertThsmsKeterOnlyFromatThnAkademik(thsms_kelas) %>, bila ada<br/> 
		 	untuk program studi berikut: 	
		 	</div>
		 	<form action="proses.noPerkuliahan" methode="POST">
		 	<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
			<%
			int i = 1;
			do {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				String kdpst = st.nextToken();
				String nmpst = st.nextToken();
				String kdjen = st.nextToken();
				String idobj = st.nextToken();
				
			%>
				<div style="font-size:1.2em;font-style:italic;text-align:left;padding:0 0 0 60px">
				<%=i++ %>.<input style="padding:5px 5px" type="checkbox" name="noclass" value="<%=idobj+"`"+kmp+"`"+kdpst %>"/> <%=Converter.getNamaKdpst(kdpst) %> untuk jenjang <%=Converter.getDetailKdjen(kdjen) %> @ Kampus <%=kmp %><br/>
				</div>
			<%	
			}
			while(li.hasNext());
			%>
			<div style="font-size:1.2em;font-style:italic;text-align:left;padding:0 0 0 50px">&#42;&nbsp;Pilih kelas yang tidak ada perkuliahan</div>
			<center>
			
				<section class="gradient">
					<div style="text-align:center">
						<button formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">UPDATE PRODI TIDAK ADA PERKULIAHAN</button>
					</div>
				</section>
			</center>
			</form>
			<%
		}
		else {
			//kalo kosong redirect ke status pengajuan kelas perkuliahan
		%>	
			<!--  META http-equiv="refresh" content="0;URL=get.statusPengajuanKelasKuliah?atMenu=form" -->
		<%	

		}
		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>