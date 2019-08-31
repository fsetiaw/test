<!DOCTYPE html>
<head>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.tools.PathFinder"%>
<%@ page import="beans.login.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
</head>
<body>

	<%
	
	String target_thsms = request.getParameter("target_thsms");
	//String info = request.getParameter("info");
	String scope_cmd = request.getParameter("scope_cmd");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String atMenu = request.getParameter("atMenu");
	//System.out.println("jiahh="+atMenu);
	String backOnly = request.getParameter("MenuBackOnly");
	String backTo= request.getParameter("backTo");
	if(backTo!=null) {
		backTo = backTo.replace("Titik", ".");
		backTo = backTo.replace("TandaTanya", "?");
		backTo = backTo.replace("SamaDgn", "=");
	}
	boolean no_edit = true;
	String non_editable = request.getParameter("no_edit");
	if(non_editable!=null && non_editable.equalsIgnoreCase("false")) {
		no_edit = false;
	}
	String info = request.getParameter("info");
	//System.out.println("backto="+backTo);
	%>
<ul>		
 <%	
 	if(backOnly==null || !backOnly.equalsIgnoreCase("true")) {
 		//System.out.println("back1");
 		%>
 		<li><a href="get.notifications" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
 		
 		<%
 		if(atMenu!=null && atMenu.equalsIgnoreCase("form")) {
 			//System.out.println("back2");
 			%>
 				
  		<li><a href="get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=<%=scope_cmd %>" target="_self" class="active">STATUS PENGAJUAN<span>KELAS PERKULIAHAN</span></a></li>
 <% 		
 		}
 		
 		else if(atMenu==null || !atMenu.equalsIgnoreCase("pengajuanKelasBaru")) {
 			//System.out.println("back3");
 %>		
 		<li><a href="get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=<%=scope_cmd %>" target="_self">STATUS PENGAJUAN<span>KELAS PERKULIAHAN</span></a></li>
<%	
 		}
 		if(validUsr.isAllowTo("hasAkademikPengajuanMenu")>0 ) {
 			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
 			String uri = request.getRequestURI();
 			String url = PathFinder.getPath(uri, target);
 			//System.out.println("back4");
 			%>		
 		<li><a href="<%=target %>?backTo=prepTitikinfoListProdiNoClassTandaTanyascope_cmdSamaDgnreqBukaKelas&atMenu=pengajuanKelasBaru" target="_self">PENGAJUAN RANCANGAN<span>KELAS PERKULIAHAN</b></span></a></li>
 			<%
 		}
 		
 	}
 	else {//back only
 		if(backTo.equalsIgnoreCase("history")) {
 			//System.out.println("back5");
 			%>
 	  	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
 	  		<%	
 		}
 		else if(backTo.equalsIgnoreCase("listPengajuanProdi")) {
 			//System.out.println("back5");
 			%>
 	  	<li><a href="get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=<%=scope_cmd %>&backTo=get.notifications" target="_self">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
 	  		<%	
 		}
 		else {
 			//System.out.println("back6");
 		 %>
  		<li><a href="<%=backTo %>" target="_self">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
  		<%
  		}		
 	}
 
 	StringTokenizer st = new StringTokenizer(info,"`");
	String id_obj_pst = st.nextToken();
	String kdpst = st.nextToken();
	String kdkmp = st.nextToken();
	String kdpst_nmpst = Converter.getNamaKdpstDanJenjang(kdpst);
	kdpst_nmpst = kdpst+"pemisah"+kdpst_nmpst+"pemisah"+kdkmp;
	if(atMenu!=null && atMenu.equalsIgnoreCase("form")) {
		 %>
 	<li><a href="get.listKelasPengajuan?no_edit=<%=no_edit %>&target_thsms=<%=target_thsms%>&info=<%=info%>&atMenu=form&MenuBackOnly=true&backTo=listPengajuanProdi" class="active" target="_self">FORM APPROVAL<span>PENGAJUAN KELAS</span></a></li>
 		<%		
	}
	else {
	 %>
	<li><a href="get.listKelasPengajuan?no_edit=<%=no_edit %>&target_thsms=<%=target_thsms%>&info=<%=info%>&atMenu=form&MenuBackOnly=true&backTo=listPengajuanProdi" target="_self">FORM APPROVAL<span>PENGAJUAN KELAS</span></a></li>
	<%
	}
 
 	if(!no_edit) {
 		st = new StringTokenizer(info,"`");
 		id_obj_pst = st.nextToken();
 		kdpst = st.nextToken();
 		kdkmp = st.nextToken();
 		kdpst_nmpst = Converter.getNamaKdpstDanJenjang(kdpst);
 		kdpst_nmpst = kdpst+"pemisah"+kdpst_nmpst+"pemisah"+kdkmp;
 		 %>
   		<li><a href="go.getListKurikulum?kdpst_nmpst=<%=kdpst_nmpst %>&kelasTambahan=yes&scope=reqBukaKelas&atMenu=bukakelas&cmd=bukakelas&backTo=listPengajuanProdi" target="_self">PENAMBAHAN KELAS <span>PERKULIAHAN</span></a></li>
   		<%
   		
 	}
 	/*
 	ganti if(true) dgn if(!no_edit) { bila perubahan dosen hanay bial blum di approved
 	*/
 	if(true) {
 		st = new StringTokenizer(info,"`");
 		id_obj_pst = st.nextToken();
 		kdpst = st.nextToken();
 		kdkmp = st.nextToken();
 		kdpst_nmpst = Converter.getNamaKdpstDanJenjang(kdpst);
 		kdpst_nmpst = kdpst+"pemisah"+kdpst_nmpst+"pemisah"+kdkmp;
 		if(atMenu!=null && atMenu.equalsIgnoreCase("ubahDosenAjar")) {
 			 %>
 	   	<li><a href="get.listKelasPengajuan?no_edit=<%=no_edit %>&target_thsms=<%=target_thsms%>&info=<%=info%>&atMenu=ubahDosenAjar&MenuBackOnly=true&backTo=listPengajuanProdi" class="active" target="_self">UBAH DOSEN PENGAJAR<span>KELAS PERKULIAHAN</span></a></li>
 	   		<%		
 		}
 		else {
 		 %>
   		<li><a href="get.listKelasPengajuan?no_edit=<%=no_edit %>&target_thsms=<%=target_thsms%>&info=<%=info%>&atMenu=ubahDosenAjar&MenuBackOnly=true&backTo=listPengajuanProdi" target="_self">UBAH DOSEN PENGAJAR<span>KELAS PERKULIAHAN</span></a></li>
   		<%
 		}
 	}
 	
%>
</ul>

</body>
</html>
	