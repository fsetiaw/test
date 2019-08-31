
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
if(info!=null) {
	info = info.replace("``", "`null`");
}
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String cmd = request.getParameter("cmd");
String atMenu = request.getParameter("atMenu");
String nmmhs=null;
String tplhr=null;
String tglhr=null;
String kdjek=null;
String nisn=null;
String warganegara=null;
String niktp=null;
String nosim=null;
String paspo=null;
String angel=null;
String sttus=null;
String email=null;
String nohpe=null;
String almrm=null;
String no_rt=null;
String no_rw=null;
String prorm=null;
String proid=null;
String kotrm=null;
String kotid=null;
String kecrm=null;
String kecid=null;
String kelrm=null;
String dusun=null;
String posrm=null;
String telrm=null;
String nglhr=null;
String agama=null;

if(!Checker.isStringNullOrEmpty(info)) {
	StringTokenizer st = new StringTokenizer(info,"`");
	nmmhs=st.nextToken();
	tplhr=st.nextToken();
	tglhr=st.nextToken();
	kdjek=st.nextToken();
	nisn=st.nextToken();
	warganegara=st.nextToken();
	niktp=st.nextToken();
	nosim=st.nextToken();
	paspo=st.nextToken();
	angel=st.nextToken();
	sttus=st.nextToken();
	email=st.nextToken();
	nohpe=st.nextToken();
	almrm=st.nextToken();
	no_rt=st.nextToken();
	no_rw=st.nextToken();
	prorm=st.nextToken();
	proid=st.nextToken();
	kotrm=st.nextToken();
	kotid=st.nextToken();
	kecrm=st.nextToken();
	kecid=st.nextToken();
	kelrm=st.nextToken();
	dusun=st.nextToken();
	posrm=st.nextToken();
	telrm=st.nextToken();
	nglhr=st.nextToken();
	agama=st.nextToken();
}

boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));

//boolean mhs_yg_edit = false;
//if(validUsr.iAmStu() && !validUsr.sudahUpdateDataNikDanIbuKandung()) {
//	mhs_yg_edit = true;
//}
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
<jsp:include page="InnerMenu.jsp" flush="true" />
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
	<jsp:include page="litle_civitas_info.jsp" flush="true" />
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
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA PRIBADI</th>
  				</tr>
  				<!--  tr>
  					<th width="5%">NO</th>
  					<th width="20%">PRODI</th>
  					<th width="20%">LOKASI</th>
  					<th width="55%">STATUS PERSETUJUAN</th>
  				</tr-->
  			</thead>
  			<tbody>
  				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmmhs).toUpperCase() %></td>
					<td colspan="2" rowspan="4" align="center" style="vertical-align: middle; padding: 0px 5px">
						<img src="show.passPhoto?picfile=<%=npm %>.jpg&npmhs=<%=npm %>" class="img-thumbnail" alt="Cinque Terre" width="204" height="136">
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GENDER</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(kdjek).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >STATUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(sttus).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >AGAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(agama).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KOTA KELAHIRAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(tplhr).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TANGGAL LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
					<%
					if(!Checker.isStringNullOrEmpty(tglhr)) {
						out.print(Converter.formatDdSlashMmSlashYy(tglhr));
					}
					
					%></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NEGARA KELAHIRAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nglhr).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KEWARGANEGARAAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(warganegara).toUpperCase() %></td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">KETERANGAN DOMISILI</th>
				</tr>
				</thead>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >ALAMAT RUMAH</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(almrm).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RT</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(no_rt) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RW</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(no_rw) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KELURAHAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(kelrm).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KECAMATAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(kecrm).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >DESA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn("") %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >DUSUN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(dusun).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KOTA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(kotrm).toUpperCase() %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PROVINSI</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(prorm).toUpperCase() %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KODE POS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(posrm) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TELP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(telrm) %></td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">INFO KONTAK & IDENTIFIKASI</th>
				</tr>
				</thead>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NIK</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(niktp) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NISN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nisn) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SIM</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nosim) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PASPOR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(paspo) %></td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nohpe) %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >EMAIL</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(email) %></td>
				</tr>
				
  			</tbody>
		</table>
		<br>
		
	</div>
</div>		
</body>
</html>	