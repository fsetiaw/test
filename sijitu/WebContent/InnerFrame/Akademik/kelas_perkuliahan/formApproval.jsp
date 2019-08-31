<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />

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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String target_thsms = request.getParameter("target_thsms");
	
	
	
	Vector v =  (Vector)request.getAttribute("v");
	Vector v_scope_id_combine = (Vector)request.getAttribute("v_scope_id_combine");
	Vector v_approval = (Vector)request.getAttribute("v_approval");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
	<jsp:include page="innerMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

<!-- Column 1 start -->
<%
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
%>
<center>

	<table class="table">
	<thead>
		<tr>
  			<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST PENGAJUAN KELAS PERKULIAHAN <%=Checker.getThsmsHeregistrasi() %></th>
  		</tr>
  		<tr>
  			<th width="5%">NO</th>
  			<th width="20%">PRODI</th>
 	 		<th width="20%">LOKASI</th>
  			<th width="55%">STATUS PERSETUJUAN</th>
  		</tr>
  	</thead>
  	<tbody>
  	<%
  	int i = 1;
  	
  	while(li.hasNext()) {
  		
  		//boolean ada_riwayat = false;
  		ListIterator lit = null;
  		Vector v_hist = null;
  		String brs = null;
  		Object ob = (Object)li.next();
  		if(ob instanceof String) {
  			brs = new String((String)ob);
  		}
  		else if(ob instanceof Vector) {
  			//ada_riwayat = true;
  			v_hist = new Vector((Vector)ob);
  			lit = v_hist.listIterator();
  			brs = (String)lit.next();
  		}
  		
  		StringTokenizer st = new StringTokenizer(brs,"`");
  		String id = st.nextToken();
		String kdpst = st.nextToken();
		String kmp = st.nextToken();
		String locked = st.nextToken();
		String passed = st.nextToken();
		String reject = st.nextToken();
		String list_job_approvee = st.nextToken();
		String list_id_approvee = st.nextToken();
		boolean complete = true;
		if(list_id_approvee.contains("null")) {
			complete = false;
		}
		String current_job_approvee = st.nextToken();
		String current_id_approvee = st.nextToken();
		
  	%>
		<tr>
	<%
		if(current_id_approvee.equalsIgnoreCase(""+validUsr.getIdObj()) || !complete) {
		%>	
			<td rowspan="2" align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %></td>
<%		
		}
		else {
	%>	
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %></td>
	<%
		}
	%>		
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(kdpst) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getNamaKampus(kmp) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px">
			Syarat Persetujuan:<br/>
			<%= list_job_approvee%><br/>
			<%
			if(v_hist!=null && v.size()>0) {
				ListIterator lih = v_hist.listIterator();
				%>
				Riwayat Persetujuan : <br/>
				<%	
				while(lih.hasNext()) {
					String baris = (String)lih.next();
					out.print(baris+"<br/>");
				}
				
			}
			
			
			%></td>
		</tr>
	<%
		if(!complete) {
%>
		<tr>	
			<td colspan="3" align="center" style="vertical-align: middle; padding: 5px 5px;font-weight:bold;color:red">APPROVEE ID BELUM DIISI HARAP HUBUNGI ADMIN</td>
		</tr>	
<%			
		}
		else if(current_id_approvee.equalsIgnoreCase(""+validUsr.getIdObj())) {
	%>
		<tr>
			<form action="go.approvalKelasKuliah">
			<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
			<input type="hidden" name="info" value="<%=brs %>"/>
			
			<td colspan="3" align="center" style="vertical-align: middle; padding: 5px 5px">
			<textarea title="Harap diisi alasan penolakan" name="alasan" placeholder="Alasan Penolakan" style="width:99%;rows:3;" required></textarea>
			<br/>
			<center>
			<section class="gradient">
				<div style="text-align:center">
					<button name="verdict" value="tolak" class="button1" type="submit" style="padding: 5px 50px;font-size: 20px;">TOLAK PERMOHONAN</button>
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
					<button name="verdict" value="terima" formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">TERIMA PERMOHONAN</button>
				</div>
				
			</section>
				
			</center>
			</td>
			</form>	
		</tr>	
	<%		
		}
	}
	%>	
  	</tbody>
	</table>

</center>
<%
}
%>
</br/>
</div>
</div>	
		<%


		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>