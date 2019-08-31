<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
%>
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
		<li>
			<a href="#"  onclick="(function(){
					//scroll(0,0);
					parent.scrollTo(0,0);
 					var x = document.getElementById('wait');
 					var y = document.getElementById('main');
 					x.style.display = 'block';
 					y.style.display = 'none';
 					location.href='get.notifications'})()"
			 target="inner_iframe">BACK<span>&nbsp</span></a>
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br>
		<!-- Column 1 start -->
		<br>
		
		<div style="text-align:center">
            <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/facepalm_itachi_small.png" alt="Gubraaak !@?!?">
            <h2>Maaf akan ada kegiatan MAINTENANCE pada tgl 10 - 11 Feb 2019</h2>
        </div>
		
		<!-- Column 1 start -->
	</div>
</div>	
</div>	
</body>
</html>