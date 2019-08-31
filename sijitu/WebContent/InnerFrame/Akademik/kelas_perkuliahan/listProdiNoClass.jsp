<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.notification.SearchDbMainNotification" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />


<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String cmd = request.getParameter("cmd");
	String atMenu = request.getParameter("atMenu");
	

	String target_thsms = request.getParameter("target_thsms"); 
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	String errMsg = request.getParameter("errMsg");
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String kdpst_nmpst = null;
	String backward2 = null;
	String scope = request.getParameter("scope");
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	String msg = request.getParameter("msg");
	String pb = request.getParameter("pb");
	String alihkan =request.getParameter("alihkan");
	Vector v_list_prodi_no_pengajuan_kelas = (Vector)session.getAttribute("v_list_prodi_no_pengajuan_kelas"); //PST`57301`MANAJEMEN INFORMATIKA`D`117
	//request.removeAttribute("v_list_prodi_no_class");
	//System.out.println("v_list_prodi_no_pengajuan_kelas="+v_list_prodi_no_pengajuan_kelas.size());
	
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
<ul>
	<li><a  href="#" onclick="(function(){
		//scroll(0,0);
		parent.scrollTo(0,0);
 		var x = document.getElementById('wait');
 		var y = document.getElementById('main');
 		x.style.display = 'block';
 		y.style.display = 'none';
 		location.href='get.notifications'})()"
		target="_self"
		>BACK<span>&nbsp</span></a>
	</li>
</ul>

 
 
 	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%

		
		//msg
		if(v_list_prodi_no_pengajuan_kelas!=null && v_list_prodi_no_pengajuan_kelas.size()>0) {
			ListIterator li = v_list_prodi_no_pengajuan_kelas.listIterator();
			String thsms_kelas = Checker.getThsmsBukaKelas();
			%>
			<div style="font-size:1.5em;font-style:bold;text-align:left;padding:0 0 0 50px">
			Harap melakukan pengajuan kelas perkuliahan untuk thn/sms <%=Converter.convertThsmsKeterOnlyFromatThnAkademik(thsms_kelas) %><br/> 
		 	untuk program studi berikut: 	
		 	</div>
		 	<form action="proses.noPerkuliahan" methode="POST">
		 	<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
			<%
			int i = 1;
			do {
				String brs = (String)li.next();
				//id_obj+"~"+kdpst+"~"+nmpst+"~"+obj_lvl+"~"+kdjen+"~"+kdkmp+"~"+kmpdom+"~"+ket_jen;
				//System.out.println("barr="+brs);
				StringTokenizer st = new StringTokenizer(brs,"~");
				String idobj = st.nextToken();
				String kdpst = st.nextToken();
				String nmpst = st.nextToken();
				st.nextToken();
				String kdjen = st.nextToken();
				String kmp = st.nextToken();
				
			%>
				<div style="font-size:1.2em;font-style:italic;text-align:left;padding:5px 5px 5px 60px">
				<%=i++ %>.&nbsp<input style="padding:5px 5px" type="checkbox" name="noclass" value="<%=idobj+"`"+kmp+"`"+kdpst %>"/> <%=Converter.getNamaKdpst(kdpst) %> untuk jenjang <%=Converter.getDetailKdjen(kdjen) %> @ Kampus <%=kmp %><br/>
				</div>
			<%	
			}
			while(li.hasNext());
			%>
			
			</form>
			<center>
<section class="gradient">
<div style="text-align:center">
<button style="padding: 5px 50px;height:50px;" 
	onclick="(function(){
    	//scroll(0,0);
		parent.scrollTo(0,0);
        var x = document.getElementById('wait');
        var y = document.getElementById('main');
        x.style.display = 'block';
        y.style.display = 'none';
        	
        location.href='get.listScope?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=get.notifications'})()">
		Lanjut Ke Pengajuan Kelas Perkuliahan</button>
</div>
</section>

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
</div>	
</body>
</html>