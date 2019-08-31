
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
   
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String info = (String)request.getAttribute("info");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String cmd = request.getParameter("cmd");
String atMenu = request.getParameter("atMenu");

//info = nimhs+"`"+shift+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+stmhs+"`"+stpid+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+krklm+"`"+npm_pa+"`"+nmm_pa;
String nimhs=null;
String shift=null;
String tahun=null;
String smawl=null;
String btstu=null;
String assma=null;
String stpid=null;
String noprm=null;
String nokp1=null;
String nokp2=null;
String nokp3=null;
String nokp4=null;
String krklm=null;
String npm_pa=null;
String nmm_pa=null;
String sksdi=null;
String asnim=null;
String aspti=null;
String asjen=null;
String aspst=null;
String aspti_unlisted=null;
String aspti_kdpti=null;
String aspst_kdpst=null;
if(!Checker.isStringNullOrEmpty(info)) {
	StringTokenizer st = new StringTokenizer(info,"`");
	nimhs=st.nextToken();
	shift=st.nextToken();
	tahun=st.nextToken();
	smawl=st.nextToken();
	btstu=st.nextToken();
	assma=st.nextToken();
	stpid=st.nextToken();
	noprm=st.nextToken();
	nokp1=st.nextToken();
	nokp2=st.nextToken();
	nokp3=st.nextToken();
	nokp4=st.nextToken();
	krklm=st.nextToken();
	npm_pa=st.nextToken();
	nmm_pa=st.nextToken();
	sksdi=st.nextToken();
	asnim=st.nextToken();
	aspti=st.nextToken();
	asjen=st.nextToken();
	aspst=st.nextToken();
	aspti_unlisted=st.nextToken();
	aspti_kdpti=st.nextToken();
	aspst_kdpst=st.nextToken();
	if(Checker.isStringNullOrEmpty(aspti) && !Checker.isStringNullOrEmpty(aspti_unlisted)) {
		aspti = aspti_unlisted;
	}
}
%>




<style>
.table { 
	border: 1px solid #2980B9;
	background:<%=Constant.lightColorBlu()%>;
	 
}
.table thead > tr > th { 
	border-bottom: none;
	background: <%=Constant.darkColorBlu()%>;
	color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<jsp:include page="../InnerMenu.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<%
if(validUsr.iAmStu()) {
	out.print("<br>");
}			
if(validUsr.getObjNickNameGivenObjId().contains("OPERATOR")) {
			%>
	<jsp:include page="../litle_civitas_info.jsp" flush="true" />
			<%			
}		
if(validUsr.isUsrAllowTo("epri", npm, obj_lvl)) {		
%>
		<table  width="90%">
			<tbody>
  				<tr>
  					<td align="left">
  						<a href="get.profile_v1?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=edit&atMenu=<%=atMenu%>">
						<img border="0" alt="EDIT" src="<%=Constants.getRootWeb() %>/images/edit.jpg" width="75" height="30">
						</a>
  					</td>
  				</tr>
  			</tbody>	
		</table>




<%
}
%>

		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA KEMAHASISWAAN</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NPM / NIM</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(npm).toUpperCase() %> / <%=Checker.pnn(nimhs).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SHIFT KULIAH</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(shift) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TAHUN MASUK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(tahun).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TIPE PENDAFTARAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Converter.printKetStatusPindahanOrBaru(stpid) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SEMESTER AWAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(smawl) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >BATAS STUDI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(btstu).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PEMBIMBING AKADEMIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmm_pa) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KURIKULUM</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%=Converter.printKrklm(krklm) %></td>
				</tr>
<%
if(!Checker.isStringNullOrEmpty(stpid) && stpid.equalsIgnoreCase("P")) {
%>
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">RIWAYAT STUDI</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA PERGURUAN TINGGI</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(aspti).toUpperCase() %></td>
				</tr>
				<tr>	
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PROGRAM STUDI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(aspst).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >JENJANG STUDI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(asjen)) {
						out.print(Converter.getDetailKdjen(asjen)) ;
					}
					%></td>
				</tr>
				<tr>	
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NPM ASAL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(asnim).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SKS TRANSFER</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(sksdi) %></td>
				</tr>
			</tbody>	
<%	
}
%>
			</tbody>
		</table>
		<br>
	</div>
</div>		
</body>
</html>	