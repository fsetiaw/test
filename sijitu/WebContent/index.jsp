<!DOCTYPE html>
<head>
<%
//System.out.println("sini dong");
%>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.awt.Toolkit" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.awt.Dimension" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	

	<title>CG2 MUTU</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	<link rel="icon" href="/favicon.ico">

	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/modernizr.js"></script>
  	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	<script type="text/javascript">
    
	
	if (top.location!= self.location) {
        top.location = self.location.href
                   //or you can use your logout page as
                   //top.location='logout.jsp' here...
    }
	</script>
	<%
	
	System.out.println("sampe12");
	String lebar  = (String)session.getAttribute( "lebar" );
	String panjang  = (String)session.getAttribute( "panjang" );
	System.out.println(panjang+" x "+lebar);
	
	Vector vTmp = null;
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("validUsr sini2= "+validUsr.toString());
	String[]visi_misi_tujuan = Getter.getVisiMisiTujuan(validUsr.getKdpst());
	Vector v_job = (Vector) session.getAttribute("v_jabatan");
	String hide_menu = request.getParameter("hide_menu");
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
	String target = null;
	String uri = null;
	String url = null; 
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
	session.removeAttribute("v_header");
	session.removeAttribute("v_body");
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
	<script>
	function alertsize(pixels){
    	pixels+=32;
    	document.getElementById('myiframe').style.height=pixels+"px";
	}
	</script>
	<script language="JavaScript">
	<%

	%>
	
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
		//document.getElementById("glu").style.height=parseInt(height-
		//document.getElementById("glu").offsetLeft-600)+"px";
<%
/*
		try {
			if(panjang!=null && Integer.parseInt(panjang)>0) {
%>				
				document.getElementById("glu").style.width=<%=Integer.parseInt(panjang)-225 %>+"px";
				document.getElementById("glu").style.height=<%=Integer.parseInt(panjang)-250%>+"px"; //biar bujur sangkar - asumsi mobile kalo landscape ke portrait ngga ngaruh
<%				
			}
		}
		catch (Exception e) {}
		try {
			if(lebar!=null && Integer.parseInt(lebar)>0) {
				if(Integer.parseInt(lebar)>Integer.parseInt(panjang)) {
%>				
					document.getElementById("glu").style.width=<%=Integer.parseInt(lebar)-225 %>+"px";
					document.getElementById("glu").style.height=<%=Integer.parseInt(lebar)-250%>+"px"; //biar bujur sangkar - asumsi mobile kalo landscape ke portrait ngga ngaruh
<%	
				}
			}
		}
		catch (Exception e) {}
		*/
%>			
	}
	// this will resize the iframe every
	// time you change the size of the window.
	window.onresize=resize_iframe; 
	//Instead of using this you can use: 
	//	<BODY onresize="resize_iframe()">
	<%

	%>

	
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
	<script>	
	   	$(document).on("click", "#home1", function() {
        	$.ajax({
        		url: 'index.jsp',
        		type: 'POST',
        		data: {},
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        //top.location.href = "get.notifications";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
        
    </script>	
	
	<script>
		function myFunction() {
			$("#wait").show();
			
			
			//$("#main").hide();
			
			//onload="scroll(0,0);resize_iframe()"
			$(document).ready(function() {
				$("#wait").remove();
				scroll(0,0);
				resize_iframe();
				//$("#main").show();
			});
		}
		
			
	</script>
		<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
<%
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");//HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    
//if(hide_menu==null || !hide_menu.equalsIgnoreCase("true")) {
%>
	<div id="header">

	<ul>
		<%
		
		
		
		Vector vSpm = validUsr.getScopeUpd7des2012("hasSpmiMenu");
		if(!am_i_stu && vSpm!=null && vSpm.size()>0) {
			%>
			
			<!-- li><a href="goto.tampleteRouteBasedOnScopeKdpst?atMenu=spmi&fwdTo=InnerFrame/Spmi/indexSpmi.jsp" target="inner_iframe">PENJAMINAN<span>MUTU</span></a></li -->
			<li><a href="goto.tampleteRouteBasedOnScopeKdpst?atMenu=spmi&fwdTo=InnerFrame/Spmi/home_spmi.jsp" target="inner_iframe" >HOME<span>&nbsp</span></a></li>
			<%
		}
		vSpm = validUsr.getScopeUpd7des2012("sdm");
		if(!am_i_stu && vSpm!=null && vSpm.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/sdm/home_sdm.jsp" target="inner_iframe" >STRUKTUAL ORGANISASI &<span>SUMBER DAYA MANUSIA</span></a></li>
			<%
		}
		
		
	%>	
		<li><a href="goto.adjustDbSpmi"  target="inner_iframe" >ADJUST<span>DB</span></a></li>
	</ul>
	<p id="layoutdims">Selamat Datang, <%=validUsr.getFullname().toLowerCase() %> | <a href="<%=Constants.getRootWeb() %>/InnerFrame/Edit/editUsrPwd.jsp" target="inner_iframe" title="edit usr/pwd">ubah usr/password</a> | 
	
	<a href="<%=Constants.getRootWeb() %>/Logout/go.logout" title="logout">logout</a></li>

	</div>
<%
//}//end hide menu
%>
	<div class="colmask fullpage">
		<div class="col1">
		<!-- Main Column 1 start -->
			<div class="scroll-wrapper">
			
				
			<%
			if(id_obj!=null && nmm!=null && npm!=null&&obj_lvl!=null && kdpst!=null) {
			%>
				<iframe id="glu"  onload="myFunction()" src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" frameborder="0" seamless="seamless" width="100%" name="inner_iframe" overflow-x="hidden"></iframe>
			<%
			}
			else if(am_i_stu && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
				target = Constants.getRootWeb()+"/InnerFrame/Tamplete/home_versi_pengumuman_upd_profile.jsp";
				uri = request.getRequestURI();
				//System.out.println(target+" / "+uri);
				url = PathFinder.getPath(uri, target);
			
			%>
				<iframe id="glu"  onload="myFunction()" src="<%=url %>" seamless="seamless" width="100%"  name="inner_iframe" frameborder="0" overflow-x="hidden"></iframe>
			<%
			}	
			else {
			%>
				<!--  iframe id="glu" src="InnerFrame/home.jsp" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe -->
				<iframe id="glu"  onload="myFunction()" src="get.notifications" seamless="seamless" width="100%"  name="inner_iframe" frameborder="0" overflow-x="hidden"></iframe>
				
			<%
			}
			%>
				
			</div>		
			<!-- Main Column 1 end -->
		</div>
		
	</div>
	<!--/div>  end id main -->		
</div>
<!--  div id="footer">
	<p>This page uses the <a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm">Perfect 'Left Menu' 2 Column Liquid Layout</a> by <a href="http://matthewjamestaylor.com">Matthew James Taylor</a>. View more <a href="http://matthewjamestaylor.com/blog/-website-layouts">website layouts</a> and <a href="http://matthewjamestaylor.com/blog/-web-design">web design articles</a>.</p>
</div-->

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
