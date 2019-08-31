
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
//info = nmmay+"`"+tglay+"`"+tplay+"`"+llsay+"`"+hpeay+"`"+jobay+"`"+payay+"`"+nikay+"`"+rilay+"`"+nmmbu+"`"+tglbu+"`"+tplbu+"`"+llsbu+"`"+hpebu+"`"+jobbu+"`"+paybu+"`"+nikbu+"`"+rilbu+"`"+nmmwa+"`"+tglwa+"`"+tplwa+"`"+llswa+"`"+hpewa+"`"+jobwa+"`"+paywa+"`"+nikwa+"`"+hubwa+"`"+nmer1+"`"+hper1+"`"+hber1+"`"+nmer2+"`"+hper2+"`"+hber2;
String nmmay=null;
String tglay=null;
String tplay=null;
String llsay=null;
String hpeay=null;
String jobay=null;
String payay=null;
String nikay=null;
String rilay=null;
String nmmbu=null;
String tglbu=null;
String tplbu=null;
String llsbu=null;
String hpebu=null;
String jobbu=null;
String paybu=null;
String nikbu=null;
String rilbu=null;
String nmmwa=null;
String tglwa=null;
String tplwa=null;
String llswa=null;
String hpewa=null;
String jobwa=null;
String paywa=null;
String nikwa=null;
String hubwa=null;
String nmer1=null;
String hper1=null;
String hber1=null;
String nmer2=null;
String hper2=null;
String hber2=null;

if(!Checker.isStringNullOrEmpty(info)) {
	StringTokenizer st = new StringTokenizer(info,"`");
	nmmay=st.nextToken();
	tglay=st.nextToken();
	tplay=st.nextToken();
	llsay=st.nextToken();
	hpeay=st.nextToken();
	jobay=st.nextToken();
	payay=st.nextToken();
	nikay=st.nextToken();
	rilay=st.nextToken();
	nmmbu=st.nextToken();
	tglbu=st.nextToken();
	tplbu=st.nextToken();
	llsbu=st.nextToken();
	hpebu=st.nextToken();
	jobbu=st.nextToken();
	paybu=st.nextToken();
	nikbu=st.nextToken();
	rilbu=st.nextToken();
	nmmwa=st.nextToken();
	tglwa=st.nextToken();
	tplwa=st.nextToken();
	llswa=st.nextToken();
	hpewa=st.nextToken();
	jobwa=st.nextToken();
	paywa=st.nextToken();
	nikwa=st.nextToken();
	hubwa=st.nextToken();
	nmer1=st.nextToken();
	hper1=st.nextToken();
	hber1=st.nextToken();
	nmer2=st.nextToken();
	hper2=st.nextToken();
	hber2=st.nextToken();
}

boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));
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
if(validUsr.isUsrAllowTo("epri", npm, obj_lvl) || wajib_update_profile) {		
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
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA AYAH</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmmay).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nikay) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TEMPAT LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(tplay).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TANGGAL LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(tglay)) {
						tglay = Converter.formatDdSlashMmSlashYy(tglay);
					}
					out.print(Checker.pnn(tglay)); 
					%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hpeay) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PENDIDIKAN AKHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(llsay).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PEKERJAAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(jobay) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GAJI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(payay).toUpperCase() %></td>
				</tr>
			</tbody>
		</table>
		<br>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA IBU</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmmbu).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nikbu) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TEMPAT LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(tplbu).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TANGGAL LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(tglbu)) {
						tglbu = Converter.formatDdSlashMmSlashYy(tglbu);
					}
					out.print(Checker.pnn(tglbu)); 
					%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hpebu) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PENDIDIKAN AKHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(llsbu).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PEKERJAAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(jobbu) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GAJI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(paybu).toUpperCase() %></td>
				</tr>
			</tbody>
		</table>
		<br>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA WALI</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmmwa).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nikwa) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TEMPAT LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(tplwa).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TANGGAL LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(tglwa)) {
						tglwa = Converter.formatDdSlashMmSlashYy(tglwa);
					}
					out.print(Checker.pnn(tglwa)); 
					%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hpewa) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PENDIDIKAN AKHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(llswa).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PEKERJAAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(jobwa) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GAJI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(paywa).toUpperCase() %></td>
				</tr>
			</tbody>
		</table>
		<br>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA KONTAK EMERGENCY</th>
  				</tr>
  				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">KONTAK 1</th>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">KONTAK 2</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmer1).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmer2) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hper1).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hper2).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >HUBUNGAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hber1).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >HUBUNGAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(hber2).toUpperCase() %></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>		
</body>
</html>	