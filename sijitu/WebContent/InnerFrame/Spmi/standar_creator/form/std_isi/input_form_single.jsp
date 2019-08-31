
<!DOCTYPE html>
<%@page import="org.codehaus.jackson.map.introspect.BasicClassIntrospector.GetterMethodFilter"%>
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
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
session.removeAttribute("v_err");
session.removeAttribute("v_target");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String atMenu = request.getParameter("atMenu");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String updated = request.getParameter("updated");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());

//Vector v = sdb.getListTitleJabatan();
Vector v_pihak = sdb.getListAvalablePihakTerkait_v1(false);

Vector v_group = sdb.getListAvalablePihakTerkait_v1(true);
String tkn_doc = request.getParameter("tkn_doc");
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
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" >
		<jsp:param name="atMenu" value="<%=atMenu %>"/>
		<jsp:param name="target_kdpst" value="<%=kdpst %>"/>
		<jsp:param name="target_nmpst" value="<%=nmpst %>"/>
		<jsp:param name="target_kdkmp" value="<%=kdkmp %>"/>

	</jsp:include>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br>
		
		<%
		if(!Checker.isStringNullOrEmpty(updated) && !updated.equalsIgnoreCase("0")) {
		%>
		<div style="text-align:center;font-size:1.5em">	
			<h2>DATA BERHASIL DIINPUT</h2>Harap Menunggu Sebentar Anda Sedang Dialihkan<br>
			<meta http-equiv="refresh" content="3; url=http:<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>">
		</div>
		<%	
		}
		else {
		%>	
		
		<form action="ins.insUsulanStdIsi" method="post">
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:2.5em">FORM USULAN STANDARISASI</th>
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
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.lightColorBlu()%>;color:#369;font-size:1.75em;font-weight:bold" >RASIONALE&nbsp/&nbspALASAN PENGAJUAN&nbsp/&nbsp MASALAH YG SERING TERJADI DI LAPANGAN</td>
				</tr>
				<tr>
					<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<textarea name="rasionale" style="width:100%;height:100px;border:none;rows:5" placeholder="Harap ceritakan permasalahan yang terjadi di lapangan, serta centang/sebutkan pihak dan dokumen terkait.&#13;&#10;[contoh: Mahasiswa selalu terlambat melakukan heregistrasi] &#13;&#10;Bila pihak atau dokumen terkait belum ada di list, harap tuliskan juga pihak/dokumen yang terkait agar bisa didata." required></textarea>
					</td>
				</tr>
						
				<tr>
					<td colspan="4" style="padding:5px 0px">
						<section class="gradient" style="text-align:center">
	            			<button style="padding: 5px 50px;font-size: 20px;">INSERT USULAN STANDAR</button>
        				</section>
					</td>		
				</tr>	
  			</tbody>
		</table>
		<br>
		</form>
<%
		}//end if else
%>		
	</div>
</div>		
</body>
</html>	