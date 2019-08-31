<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<style>
.table { 
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>; 
//table-layout: fixed;
}
.table thead > tr > th { 
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; word-wrap:break-word;}
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; word-wrap:break-word;}
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px ;word-wrap:break-word;}
/*
.table tr:hover td { background:#82B0C3;word-wrap:break-word; }
.table thead:hover td { background:#82B0C3;word-wrap:break-word; } <thead> yg jd anchor
*/
</style>
<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//	String callerPage = request.getParameter("callerPage");
	//System.out.println("@settargetkurikul");
	String thisPage = request.getRequestURI();
	//String cmd = ""+request.getParameter("cmd");
	Vector vListKurikulum = (Vector) request.getAttribute("vListKurikulum");
	//System.out.println("vListKurikulum size ="+vListKurikulum.size());
	String kdpst_nmpst = ""+request.getAttribute("kdpst_nmpst");
	//System.out.println(kdpst_nmpst);
	String thsms_buka_kelas = (String)session.getAttribute("thsms_buka_kelas");
	request.removeAttribute("kdpst_nmpst");
	String cmd = ""+request.getParameter("cmd");
	String atMenu = ""+request.getParameter("atMenu");
	String scope = request.getParameter("scope");
	String kelasTambahan = (String)request.getParameter("kelasTambahan");
	boolean no_edit = true;
	if(kelasTambahan!=null && kelasTambahan.equalsIgnoreCase("yes")) {
		no_edit = false;
	}
	String kodeKampus = (String)request.getParameter("kodeKampus");
	
	String id_obj_mask = request.getParameter("id_obj");
	String nmm_mask = request.getParameter("nmm");
	String npm_mask = request.getParameter("npm");
	String obj_lvl_mask = request.getParameter("obj_lvl");
	String kdpst_mask = request.getParameter("kdpst");
	
%>


</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">

	<%
	Vector vSc = validUsr.getScopeUpd7des2012("hasAkademikMenu");
	boolean match = false;
	if(vSc!=null && vSc.size()>0) {
		//System.out.println("vScin");
		if(cmd.equalsIgnoreCase("viewKurikulum")) {
			match = true;
	%>
	<ul>
		<li><a href="get.listScope?scope=allowViewKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=viewKurikulum&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a></li>
	</ul>
	<%		
		}
		else if(!match && cmd.equalsIgnoreCase("mba")) {
			match = true;
			
	%>
	<ul>
		<li><a href="get.listScope?scope=mba&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a></li>
	</ul>
	<%
		}
		else if(!match && cmd.equalsIgnoreCase("bukaKelas")) {
			match = true;
			
			
	%>
	<ul>
	
		<li>
			<!--  a href="get.listScope?cmd=<%=cmd %>&atMenu=<%=atMenu %>&scope=<%=scope %>&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=bukaKelas&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a-->
			<%
			if(kelasTambahan!=null && kelasTambahan.equalsIgnoreCase("yes")) {
				
				%>
			<a href="get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=<%=scope %>&backTo=get.notifications" target="_self">BACK<span>&nbsp</span></a>
			<%	
			}
			else {
			%>
			<a href="get.listScope?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=get.notifications" target="_self">BACK<span>&nbsp</span></a>
			<%
			}
			%>
		</li>
	</ul>
	<%
		}
		else if(!match) {
	%>
	<ul>
		<li><a href="get.listScope?callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=viewKurikulum" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
	<%					
				
				
		}
	}	
	%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		
		<br>
		<!-- Column 1 start -->

		<%
		//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt);
		if(vListKurikulum.size()>0) {
			ListIterator liSc = vListKurikulum.listIterator();
			
			//if(!tmp.equalsIgnoreCase("own")){
			if(vSc.size()>0) {
				//System.out.println("00.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			
		%>
			<center>
			<form action="go.prepKurikulumForViewing" target="_self" >
			<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
			<input type="hidden" name="cmd" value="<%=cmd %>" />
			<input type="hidden" name="atMenu" value="<%=atMenu %>" />
			<input type="hidden" name="scope" value="<%=scope %>" />
			<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />
			<input type="hidden" name="id_obj" value="<%=id_obj_mask %>" />
			<input type="hidden" name="nmm" value="<%=nmm_mask %>" />
			<input type="hidden" name="npm" value="<%=npm_mask %>" />
			<input type="hidden" name="obj_lvl" value="<%=obj_lvl_mask %>" />
			<input type="hidden" name="kdpst" value="<%=kdpst_mask %>" />
 			<table class="table" align="center" border="1" style="background:#369;color:#000;width:50%;">	
				<tr>
					<td style="color:#fff;background:#369;text-align:center;font-size:1.75em" padding-left="2px"><b>PILIH KURIKULUM</b></td>
				</tr>	
				<tr>	
					<td style="text-align:center">
						<select name="infoKur" style="height:35px;text-align-last:center;border:none;width:100%">
						
				<%
				while(liSc.hasNext()) {
					String baris = (String)liSc.next();
					//System.out.println("00baris="+baris);
					StringTokenizer st = new StringTokenizer(baris,"#&");
					//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
					String idkur = st.nextToken();
					String nmkur = st.nextToken();
					String stkur = st.nextToken();
					String start = st.nextToken();
					if(start.equalsIgnoreCase("null")) {
						start = "N/A";
					}
					String ended = st.nextToken();
					if(ended.equalsIgnoreCase("null")) {
						ended = "N/A";
					}
					String targt = st.nextToken();
					String skstt = st.nextToken();
					String smstt = st.nextToken();
				%>
							<option value="<%=idkur+"#&"+nmkur+"#&"+skstt+"#&"+smstt+"#&"+start+"#&"+ended+"#&"+targt%>"><%=nmkur %>(<%=skstt %> sks / <%=smstt %> sms) (<%=start %> s/d <%=ended %>)</option>
				<%	
				}	
				%>
						</select>
					</td>
				</tr>	
				<%
				
				if(cmd.equalsIgnoreCase("bukaKelas")&&kelasTambahan.equalsIgnoreCase("yes")) {
				%>
				<tr>	
					<td align="center" style="padding-left:2px"><input type="hidden" name="kelasTambahan" value="<%=kelasTambahan%>"></td>
				</tr>		
				<%	
				}
				
				%>
				<tr>
					<td style="border:2px solid #369;padding:5px 5px;background:<%=Constant.lightColorBlu() %>">
						<section class="gradient">
							<div style="text-align:right">
								<button  type="submit" style="padding: 5px 50px;font-size: 20px;">NEXT</button>
							</div>
						</section>
					</td>
				</tr>
			</table>
			</center>
			</form>	
		<%
			}
			else {
				//jsp ini kepanggil onli vectr > 1, semua diatur di servlet Get.ListKurikulum.java
				//System.out.println("ada error di ListKurikulum untuk Vector = null / size=0 / size=1");
						
			}
		}	
		%>
		
	</div>
</div>	
</div>	
</body>
</html>