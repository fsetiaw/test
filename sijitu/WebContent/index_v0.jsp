<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>

	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/2colsLayout/screen.css" media="screen" />
	<link rel="icon" href="/favicon.ico">
  	<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb()%>/expSearchfield/demo/styles.css">
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion_left.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-left.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	<script type="text/javascript">
    
	
	
	if (top.location!= self.location) {
        top.location = self.location.href
                   //or you can use your logout page as
                   //top.location='logout.jsp' here...
    }
	</script>
	<%
	
	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String[]visi_misi_tujuan = Getter.getVisiMisiTujuan(validUsr.getKdpst());
	Vector v_job = (Vector) session.getAttribute("v_jabatan");
	
	/*
	 * variable dari hasil pencarian
	*/		
	String my_stat = (String) session.getAttribute("curr_stmhsmsmhs");
	String id_obj = request.getParameter("id_obj");
	String nmm = request.getParameter("nmm");
	String npm = request.getParameter("npm");
	String obj_lvl = request.getParameter("obj_lvl");
	String kdpst = request.getParameter("kdpst");
	String id_obj_as_stu = null;
	String nmm_as_stu = null;
	String npm_as_stu = null;
	String obj_lvl_as_stu = null;
	String kdpst_as_stu = null;
	boolean am_i_stu = false; 
	if(validUsr.getObjNickNameGivenObjId().contains("MHS")) {
		am_i_stu = true;
		id_obj_as_stu = new String(""+validUsr.getIdObj());
		nmm_as_stu = new String(""+validUsr.getFullname());
		npm_as_stu = new String(""+validUsr.getNpm());
		obj_lvl_as_stu = new String(""+validUsr.getObjLevel());
		kdpst_as_stu = new String(""+validUsr.getKdpst());
	}
	/*
	RESERVED BILA HARUS UPDATE PADA SAAT LOGIN
	*/
	boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));

	//boolean mhs_hrs_upd_profile = false;
	//if(am_i_stu && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
	//	mhs_hrs_upd_profile = true;
	//}
	
	//closing session from summary tab

	/*
	LIST SESSION YG JGN Di REMOVE
	1.ObjGenderAndNickname
	*/
	session.removeAttribute("vHisBea");
	session.removeAttribute("vListNamaPaketBea");
	session.removeAttribute("vMhsUnHeregContainment");
	session.removeAttribute("kodeKampus");
	session.removeAttribute("targetAkun");
	session.removeAttribute("sumberDana");
	session.removeAttribute("dataForRouterAfterUpload");
	session.removeAttribute("target_kelas_info");
	session.removeAttribute("vMhsContainer");
	session.removeAttribute("nmpw");
	session.removeAttribute("forceBackTo");
	session.removeAttribute("tknKrsNotificationsForSender");
	session.removeAttribute("tknKrsNotifications");
	session.removeAttribute("v_totMhs");
	session.removeAttribute("vSum");
	session.removeAttribute("tknYyMm");
	session.removeAttribute("yTot");
	session.removeAttribute("vSumPsc");
	session.removeAttribute("yTotPsc");
	session.removeAttribute("vHistKrsKhsForEdit");
	session.removeAttribute("vTrnlpForEdit");
	session.removeAttribute("vf");
	session.removeAttribute("vListDsn");
	session.removeAttribute("vJsoa");
	session.removeAttribute("kelasTambahan");
	session.removeAttribute("v_nm_alm_access");
	session.removeAttribute("hakAksesUsrUtkFolderIni");
	session.removeAttribute("saveToFolder");
	request.removeAttribute("v_profile");
	request.removeAttribute("v_bak");
	request.removeAttribute("v_NuBak");
	session.removeAttribute("vListKelasPerProdi");
	request.removeAttribute("v_trnlm");
	request.removeAttribute("v_trnlp");
	session.removeAttribute("vHistTmp");
	request.removeAttribute("vHistoKrs");
	session.removeAttribute("vSummaryPMB");
	session.removeAttribute("vListMakul");
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	session.removeAttribute("vBukaKelas");
	session.removeAttribute("brs");
	session.removeAttribute("tmp");
	session.removeAttribute("lockedMsg");
	session.removeAttribute("vScopeBukaKelas");
	session.removeAttribute("targetObjNickName");
	session.removeAttribute("vSubTopik");
	session.removeAttribute("fieldAndValue");
	session.removeAttribute("validatedTransDate");
	session.removeAttribute("job");
	%>
	<script language="JavaScript">
	<!--
	function resize_iframe()
	{

		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		//resize the iframe according to the size of the
		//window (all these should be on the same line)
		document.getElementById("glu").style.height=parseInt(height-
		document.getElementById("glu").offsetTop-8)+"px";
	}
	// this will resize the iframe every
	// time you change the size of the window.
	window.onresize=resize_iframe; 
	//Instead of using this you can use: 
	//	<BODY onresize="resize_iframe()">
	//-->

	
</script>

<style>
.scroll-wrapper {
 // position: fixed; 
 // right: 0; 
 // bottom: 0; 
 // left: 0;
 // top: 0;
  -webkit-overflow-scrolling: touch;
  overflow-y: scroll;
}

.scroll-wrapper iframe {
  //height: 100%;
 // width: 100%;
}
</style>
</head>
<body>
<div id="header">

	<ul>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		if(am_i_stu && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
			//tidak ditampilkan bila harus update data profile	
		}
		else {
		%>
		<li>
		<a href="get.notifications" target="inner_iframe">HOME <span><b style="color:#eee">&nbsp</b></span></a>
		<!--  a href="<%=url %>" target="inner_iframe">HOME <span><b style="color:#eee">---</b></span></a-->
		</li>
		<%	
		}
		//kayaknya menu untuk mhs ajah nih
		//if(validUsr.isAllowTo("hasDataMhsMenu")>0) {
		if(am_i_stu) {			
			String v_npmhs = validUsr.getNpm();
			String v_id_obj = ""+validUsr.getIdObj();
			String v_nmmhs = validUsr.getNmmhs(v_npmhs);
			String v_obj_lvl = ""+validUsr.getObjLevel();
			String v_kdpst = validUsr.getKdpst();
			String v_nimhs = validUsr.getNimhs(v_npmhs);
			if(!validUsr.sudahUpdateDataNikDanIbuKandung()) {
				%>
				<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
				<li><a href="get.profile_v1?atMenu=pribadi&cmd=edit&id_obj=<%=v_id_obj%>&nim=<%=v_nimhs%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>" target="inner_iframe">DATA <span>PROFIL</span></a></li>
				<%	
			}
			else {
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="get.profile_v1?atMenu=pribadi&cmd=profile&id_obj=<%=v_id_obj%>&nim=<%=v_nimhs%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>" target="inner_iframe">DATA <span>PROFIL</span></a></li>
			<%
			}
			//hidden menu yang laen bagi mhs yg harus update data profile
			if(!wajib_update_profile) {
			//==========bidang akademik untuk mahasiswa===================
				target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademikMhs.jsp";
				uri = request.getRequestURI();
				url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">DATA<span>AKADEMIK</span></a></li>
			
			<li><a href="get.histPymnt?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&atMenu=riwayatBayaran" target="inner_iframe">DATA<span>KEUANGAN</span></a></li>
			<%
			}
		}
		
		/*
		pinah ke CIVITAS BARU
		target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
		uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("iciv")>0) {
		%>
		<li><a href="<%=url %>" target="inner_iframe">INSERT <span>CIVITAS BARU</span></a></li>
		<%
		}
		*/
		//CIVITAS BARU
		if(validUsr.isAllowTo("iciv")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/PMB/dash_pmb.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
		%>
		<li><a href="<%=url %>" target="inner_iframe">BUKU TAMU &<span>PENDAFTARAN</span></a></li>
		<%
		}
		
		/*
		if(validUsr.isAllowTo("hasHeregitrasiMenu")>0) {
			%>
			<li><a href="get.whoRegister" target="inner_iframe">HEREGISTRASI <span>DAFTAR ULANG</span></a></li>
			<%
		}
		*/
		
		String mdl = Checker.adaDiMoodle(validUsr.getNpm());
		//System.out.println("mdl="+mdl);
		Vector vTmp = validUsr.getScopeUpd7des2012("hasAkademikMenu");
		if((vTmp!=null && vTmp.size()>0) || mdl!=null || validUsr.getNpm().equalsIgnoreCase("0000812100004")) {
			target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?mdl=<%=mdl %>&callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BIDANG<span>AKADEMIK</span></a></li>
			<%
		}	
		/*
		==========bidang akademik untuk mahasiswa===================
		PINDAH KE BAGIAN ATAS 
		String usrNickNm = validUsr.getObjNickNameGivenObjId();
		if(usrNickNm.startsWith("MHS")) {
			target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademikMhs.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<=url %>?callerPage=<=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">DATA1<span>AKADEMIK</span></a></li>
			<%
		}
		*/
		vTmp = validUsr.getScopeUpd7des2012("hasMenuBagKeu");
		if(vTmp!=null && vTmp.size()>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Keu/indexKeuangan.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BIDANG<span>KEUANGAN</span></a></li>
			<%
		}	
		
  		/*
  		DEPRECATED - sdg coba dihilangkan
		if(validUsr.isAllowTo("HPrM")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Prakuliah/DashPrakuliah.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">KEGIATAN<span>PRA-KULIAH</span></a></li>
			<%
		}
		*/	
		if(validUsr.isAllowTo("hasPerkuliahanMenu")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">KEGIATAN<span>PERKULIAHAN</span></a></li>
			<%
		}		
		//target = Constants.getRootWeb()+"/InnerFrame/Summary/view.summary";
		//uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		//url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("hasSummaryMenu")>0) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/dashSummary.jsp" target="inner_iframe">SUMMARY<span><b style="color:#eee">-</b> </span></a></li>
		<!--  li><a href="<%=url %>" target="inner_iframe">SUMMARY<span><b style="color:#eee">---</b> </span></a></li -->
		<%
		}
		
		Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
		if(vDwn!=null && vDwn.size()>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Download/dashDownload.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">DOWNLOAD<span>UPLOAD FILE</span></a></li>
			<%
		}	
		
		Vector vSpm = validUsr.getScopeUpd7des2012("hasSpmiMenu");
		if(vSpm!=null && vSpm.size()>0) {
			%>
			<!--  li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp?atMenu=spmi" target="inner_iframe">PENJAMINAN<span>MUTU</span></a></li -->
			<li><a href="goto.tampleteRouteBasedOnScopeKdpst?atMenu=spmi&fwdTo=InnerFrame/Spmi/indexSpmi.jsp" target="inner_iframe">PENJAMINAN<span>MUTU</span></a></li>
			<%
		}
		Vector vAna = validUsr.getScopeUpd7des2012("hasStatMenu");
		if(vAna!=null && vAna.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Analisa/dashStat.jsp" target="inner_iframe">DATA<span>STATISTIK</span></a></li>
			<%
		}
		Vector vTool = validUsr.getScopeUpd7des2012("hasStoredProcedureMenu");
		if(vTool!=null && vTool.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/StoredProcedure/dashStoredProces.jsp" target="inner_iframe">ALAT KALKULASI<span>DATA</span></a></li>
			<%
		}
		//penempatan sementara dan hanya untuk operator kalo mhs ada dibidang akademik mhs
		if(validUsr.isAllowTo("hasEtestMenu")>0 && validUsr.getObjNickNameGivenObjId().contains("OPERATOR")) {
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/MhsSection/dashboardMhs.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li>
			<%
		}
		
		//kayaknya menu untuk mhs ajah nih
		//sudah dipecah diatas
		//if(validUsr.isAllowTo("hasDataMhsMenu")>0) {
		if(false) {	
			String myNpm = validUsr.getNpm();
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			
			<li><a href="get.profile?npm=<%=validUsr.getNpm() %>&cmd=dashboard&nim=<%=validUsr.getNimhs(myNpm)%>&stmhs=<%=validUsr.getStmhs(myNpm)%>&kdpst=<%=validUsr.getKdpst()%>&id_obj=<%=validUsr.getIdObj() %>&obj_lvl=<%=validUsr.getObjLevel() %>&nmm=<%=validUsr.getNmmhs(myNpm) %>" target="inner_iframe">DATA<span>MAHASISWA</span></a></li>
			<%
		}
		
		if(am_i_stu && !wajib_update_profile) {
			String myNpm = validUsr.getNpm();
			//target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
			target = Constants.getRootWeb()+"/InnerFrame/indexPengajuanMhs.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			//System.out.println("---iam student-----");
			//System.out.println(id_obj_as_stu);
			//System.out.println(nmm_as_stu);
			//System.out.println(npm_as_stu);
			//System.out.println(kdpst_as_stu);
			//System.out.println(obj_lvl_as_stu);
			//System.out.println("---end iam student-----");
			
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="<%=url %>?am_i_stu=true&id_obj=<%=id_obj_as_stu%>&nmm=<%=nmm_as_stu%>&npm=<%=npm_as_stu %>&obj_lvl=<%=obj_lvl_as_stu %>&kdpst=<%=kdpst_as_stu %>&cmd=reqMhs" target="inner_iframe">PENGAJUAN<span>&nbsp</span></a></li>
			<%
			//SURVEY FORM
			%>
			<li><a href="cek.availSurvey?am_i_stu=true&id_obj=<%=id_obj_as_stu%>&nmm=<%=nmm_as_stu%>&npm=<%=npm_as_stu %>&obj_lvl=<%=obj_lvl_as_stu %>&kdpst=<%=kdpst_as_stu %>&cmd=reqMhs" target="inner_iframe">SURVEY<span>&nbsp</span></a></li>
			<%
		}
		
		//if(validUsr.isAllowTo("hasContactUsMenu")>0) {
		if(false) {	
			//String myNpm = validUsr.getNpm();
			%>
			
			<li>
				<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/ContactUs/dashContactUs.jsp" target="inner_iframe">KOTAK<span>PESAN</span></a -->
				<a href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox() %>&show=unread" target="inner_iframe">INBOX &<span>KIRIM PESAN</span></a>
			</li>
			<%
		}
		
		if(validUsr.isAllowTo("settingParam")>0) {
			//String myNpm = validUsr.getNpm();
			%>
			
			<li>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Parameter/dashParameter.jsp" target="inner_iframe">PENGATURAN<span>PARAMETER</span></a>
			</li>
			<%
		}	

		if(validUsr.isAllowTo("hasArchiveMenu")>0) {
		%>
			<li>
				<a href="get.fileAndFolder" target="inner_iframe">ARSIP<span>USG</span></a>
			</li>
		<%
		}
		if(!validUsr.iAmStu()) {
		%>	
			<li>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/input_file/index_uploader.jsp" target="inner_iframe">FILE<span>UPLOADER</span></a>
			</li>
		<%
		}
		%>	
		<%
		//if(validUsr.isAllowTo("s")>0 && validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {
			
		if(!validUsr.iAmStu()) {
		%>
		<li>
			<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/standalone_proses/index_stp.jsp" target="inner_iframe">STANDALONE<span>PROSES</span></a>
		</li>
		<%
		}
		if(validUsr.amI("ADMIN")) {
		%>
		<li>
			<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Feeder/index_importer.jsp" target="inner_iframe">FEEDER<span>IMPORTER</span></a>
		</li>
		<%
		}
		
		if(Checker.amI("KEPALA TATA USAHA`ADMIN", validUsr.getIdObj())) {
			%>
			<li>
				<a href="get.listUsrPwd?cmd_scope=viewWhoRegister" target="inner_iframe">LIST USER<span>PWD</span></a>
			</li>
			<%
		}
		
		//if(validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {
		if(validUsr.iAmStu()) {	
			//String myNpm = validUsr.getNpm();
			%>
			
			<li>
				<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/ContactUs/dashContactUs.jsp" target="inner_iframe">KOTAK<span>PESAN</span></a -->
				<a href="go.prepContactDashboard" target="inner_iframe">INBOX &<span>KIRIM PESAN</span></a>
			</li>
			<%
		}
		%>
<!-- 
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm">2 Column <span>Left Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-right-menu.htm">2 Column <span>Right Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-double-page.htm">2 Column <span>Double Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-full-page.htm">1 Column <span>Full Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-stacked-columns.htm">Stacked <span>columns</span></a></li>
 -->

	</ul>
	<p id="layoutdims">Selamat Datang, <%=validUsr.getFullname().toLowerCase() %> | <a href="<%=Constants.getRootWeb() %>/InnerFrame/Edit/editUsrPwd.jsp" target="inner_iframe" title="edit usr/pwd">ubah usr/password</a> | <a href="<%=Constants.getRootWeb() %>/Logout/go.logout" title="logout">logout</a></li>
</div>
<div class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<!-- Main Column 1 start -->
			<div class="scroll-wrapper">
			

			<%
			if(id_obj!=null && nmm!=null && npm!=null&&obj_lvl!=null && kdpst!=null) {
			%>
				<iframe id="glu" src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe>
			<%
			}
			else if(am_i_stu && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
				target = Constants.getRootWeb()+"/InnerFrame/Tamplete/home_versi_pengumuman_upd_profile.jsp";
				uri = request.getRequestURI();
				//System.out.println(target+" / "+uri);
				url = PathFinder.getPath(uri, target);
			
			%>
				<iframe id="glu" src="<%=url %>" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe>
			<%
			}	
			else {
			%>
				<!--  iframe id="glu" src="InnerFrame/home.jsp" seamless="seamless" width="100%" onload="resize_iframe()" name="inner_iframe"></iframe -->
				<iframe id="glu" src="get.notifications" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe>
				
			<%
			}
			%>
			</div>			
			<!-- Main Column 1 end -->
		</div>
		<div class="col2">
			<!-- Column 2 start -->
				<%
				if(validUsr.isAllowTo("s")>0 && !(validUsr.getObjNickNameGivenObjId().contains("TAMU"))) {
				%>
				<p>
				<form id="searchform" name="searchform" method="post" action="people.search" target="inner_iframe">
      				<div class="fieldcontainer">
        			<input type="text" name="kword" id="s" class="searchfield" placeholder="nama / npm " tabindex="1">
        			<input type="submit" name="searchbtn" id="searchbtn" value=""> 
      				</div><!-- @end .fieldcontainer -->
    			</form>
				</p>
				<br/>
				
				<%
				}
				//get visi
				if(visi_misi_tujuan[0]!=null && !Checker.isStringNullOrEmpty(visi_misi_tujuan[0])) {
				%>
				
				<div class="accordion">
    				<div class="accordion-section">
        				<a class="accordion-section-title-center" href="#accordion-1">Visi</a>
         				<div id="accordion-1" class="accordion-section-content">
         					
         					<%=visi_misi_tujuan[0] %>
         					
         				</div>
         			</div>
         		</div>			
         				
				<%
				}
				/*
					}
					catch(JSONException je) {}
					
				}
				
				
				String misi = "";
				JSONArray jsoaMisi = null;
				try {
					jsoaMisi = Getter.readJsonArrayFromUrl("/v1/search_prodi/misi/"+validUsr.getKdpst());
				}
				catch(Exception e) {
					//ignore url not found
				}
				if(jsoaMisi!=null && jsoaMisi.length()>0) {
					JSONObject jobMisi = jsoaMisi.getJSONObject(0);
					try {
						misi = jobMisi.getString("MISI");	
				*/
				//get misi
				if(visi_misi_tujuan[1]!=null && !Checker.isStringNullOrEmpty(visi_misi_tujuan[1])) {
				%>
				<div class="accordion">
    				<div class="accordion-section">
        				<a class="accordion-section-title-center" href="#accordion-2">Misi</a>
         				<div id="accordion-2" class="accordion-section-content">
         					<%=visi_misi_tujuan[1] %>
         				</div>
         			</div>
         		</div>		
				
				<% 	
				}
				/*
					}
					catch(JSONException je) {}
					
				}
				*/
				//get tujuan
				if(visi_misi_tujuan[2]!=null && !Checker.isStringNullOrEmpty(visi_misi_tujuan[2])) {
				%>
				<div class="accordion">
    				<div class="accordion-section">
        				<a class="accordion-section-title-center" href="#accordion-3">Tujuan</a>
         				<div id="accordion-3" class="accordion-section-content">
         					<%=visi_misi_tujuan[2] %>
         				</div>
         			</div>
         		</div>
				<%	
				}
				%>
				</br>
				</br>
				<%
					if(validUsr.isAllowTo("link1")>0) {
				%>
				<p align="center">
					<a href="goto.fwdLink?linkTo=forlap" target="inner_iframe" >Forlap Dikti</a>
				</p>	
				<%
					}
				
					if(validUsr.isAllowTo("link2")>0) {
			
				%>
				<p align="center">
					<a href="goto.fwdLink?linkTo=feeder_part1" target="inner_iframe">Feeder PDPT</a>
				</p>
				
				<%
					}
					if(am_i_stu && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
						
					}
					else {
				%>
				<p align="center">
					<a href="goto.fwdLink?linkTo=simlit" target="inner_iframe" >SIM-LITABMAS</a>
				</p>
				<%
				}
				%>
				<!--  p align="center">
					<a href="http://localhost/moodle/" target="inner_iframe" >lms</a>
				</p-->
				<%
				if(Checker.isThisMyRole(session, "ADMIN")) {
				%>
				<p align="center">
				
					<a href="sync.tableRuleAndDaftarUlang" target="inner_iframe">Sync Tabel Rule</a>
					<!--  a href="http://192.168.1.103/moodle/login/index.php" target="inner_iframe">Moodle</a -->
					
				</p>
				<p align="center">
				
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/idobjError.jsp" target="inner_iframe">Cek data</a>
					<!--  a href="http://192.168.1.103/moodle/login/index.php" target="inner_iframe">Moodle</a -->
					
				</p>
				<p align="center">
				
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/Monitoring/list_no_data_at_civitas.jsp" target="inner_iframe">CIVITAS VS EXT_CIVITAS</a><br>
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/Monitoring/delete_data_orphan_ext_civitas.jsp" target="inner_iframe">DELETE DATA ORPHAN EXT_CIVITAS</a><br>
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/Monitoring/sink_mk_penyetaraan.jsp" target="inner_iframe">SINK MK PENYETARAAN</a><br>
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/input_file/index_input_file.jsp" target="inner_iframe">EXCEL UPLOADER</a><br>
					<!--  a href="http://192.168.1.103/moodle/login/index.php" target="inner_iframe">Moodle</a -->
					
				</p>
				<%	
				}
				String paramCmd = "reqBukaKelas";
				if(!validUsr.iAmStu() && validUsr.isAllowTo(paramCmd)>0) {
				%>
				<p align="center">
					<a href="go.syncCuidTrnlm?cmd=<%=paramCmd %>&target_thsms=<%=Checker.getThsmsBukaKelas() %>" target="inner_iframe">Sync kelas & krs</a>
					<!--  a href="http://192.168.1.103/moodle/login/index.php" target="inner_iframe">Moodle</a -->
					
				</p>
				<%
				}
				%>
				<p>
				<!--  iframe id="glu" src="LeftFrame/index.html" seamless="seamless" width="100%" onload="resize_iframe()" name="left_inner_iframe" scrolling="no"></iframe -->
				<!--  iframe id="glu" src="LeftInnerFrame/home.jsp" seamless="seamless" width="100%" onload="resize_iframe()" name="left_inner_iframe" scrolling="no" height="1000px"></iframe -->
				</p>
				<%
				/*
				String visi = "";
				JSONArray jsoaVisi = null;
				try {
					jsoaVisi = Getter.readJsonArrayFromUrl("/v1/search_prodi/visi/"+validUsr.getKdpst());
				}
				catch(Exception e) {
					//ignore url not found
				}
					//System.out.println("jsoaVisi="+jsoaVisi.length());
				if(jsoaVisi!=null && jsoaVisi.length()>0) {
					JSONObject jobVisi = jsoaVisi.getJSONObject(0);
					try {
						visi = jobVisi.getString("VISI");
				*/
				%>
			<!-- Column 2 end -->
		</div>
	</div>
</div>
<div id="footer">
	<p>This page uses the <a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm">Perfect 'Left Menu' 2 Column Liquid Layout</a> by <a href="http://matthewjamestaylor.com">Matthew James Taylor</a>. View more <a href="http://matthewjamestaylor.com/blog/-website-layouts">website layouts</a> and <a href="http://matthewjamestaylor.com/blog/-web-design">web design articles</a>.</p>
</div>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/modernizr.js"></script>
	<script>
		(function($){
			//cache nav
			var nav = $("#topNav");
				
			//add indicator and hovers to submenu parents
			nav.find("li").each(function() {
				if ($(this).find("ul").length > 0) {
					$("<span>").text("").appendTo($(this).children(":first"));
					//show subnav on hover
					$(this).mouseenter(function() {
						$(this).find("ul").stop(true, true).slideDown();
					});
						
					//hide submenus on exit
					$(this).mouseleave(function() {
						$(this).find("ul").stop(true, true).slideUp();
					});
				}
			});
		})(jQuery);
	</script>
</body>
</html>
