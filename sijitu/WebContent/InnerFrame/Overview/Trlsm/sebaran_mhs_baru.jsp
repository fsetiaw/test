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

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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
tr:hover td { background:#82B0C3 }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String target_thsms = request.getParameter("target_thsms");
String target_kdkmp = request.getParameter("kdkmp");
Vector v_scope_kdpst_list_distinct_npm_mhs_baru = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_mhs_baru");
session.removeAttribute("v_header");
session.removeAttribute("v_body");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b>&nbsp</b></span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<br>
<center>
<%
if(v_scope_kdpst_list_distinct_npm_mhs_baru==null || v_scope_kdpst_list_distinct_npm_mhs_baru.size()<1) {
	out.print("<h1>TOTAL DATA :  0</h1>");
}
else {
	ListIterator li_nube = v_scope_kdpst_list_distinct_npm_mhs_baru.listIterator();
%>
	<table class="table">
	<thead>
		<tr>
			<th colspan="5" style="text-align: center; padding: 0px 10px;font-size:1.5em">SEBARAN MAHASISWA BARU <%=target_thsms %><br><%=Converter.getNamaKampus(target_kdkmp) %> </th>
		</tr>
		<tr>
			<th width="5%">NO</th>
			<th width="45%">PRODI</th>
			<th width="20%">BARU / PINDAHAN</th>
			<th width="20%">PRIA / WANITA</th>
			<th width="10%">TOTAL</th>
		</tr>
	</thead>
	<tbody>
<%
int norut=1;
int tot_mhs = 0;
while(li_nube.hasNext()) {
	String nube=(String)li_nube.next();
	//System.out.println("nube="+nube);
	//hitung mhs baru & ppindahan
			
			StringTokenizer st = new StringTokenizer(nube,"~");
			if(st.countTokens()>1) {
				String info_kdkmp = st.nextToken();
				if(info_kdkmp.startsWith(target_kdkmp+"`")) {
					int mhs_baru=0;
					int mhs_baru_pindahan=0;
					int mhs_baru_laki=0;
					int mhs_baru_perem=0;
					boolean first = true;
					String prev_kdpst = null;
					st = new StringTokenizer(st.nextToken(),",");
					while(st.hasMoreTokens()) {
						tot_mhs++;
						String kdpst = st.nextToken();
						String npmhs = st.nextToken();
						String stpid = st.nextToken();
						String kdjek = st.nextToken();
						
						
						
						if(first) {
							first=false;
							prev_kdpst=new String(kdpst);
							if(stpid.equalsIgnoreCase("B")) {
								mhs_baru++;
							}
							else {
								mhs_baru_pindahan++;
							}
							if(kdjek.equalsIgnoreCase("L")) {
								mhs_baru_laki++;
							}
							else {
								mhs_baru_perem++;
							}
						}
						else {
							if(!kdpst.equalsIgnoreCase(prev_kdpst)) {
								//ganti prodi
								%>	
		<tr onclick="location.href='go.executeSql_mhs_baru?mhs_perem=<%=mhs_baru_perem %>&mhs_laki=<%=mhs_baru_laki %>&mhs_pindah=<%=mhs_baru_pindahan %>&mhs_baru=<%=mhs_baru %>&target_kdkmp=<%=target_kdkmp %>&target_thsms=<%=target_thsms %>&target_kdpst=<%=prev_kdpst%>'">
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
			<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(prev_kdpst) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru %>/<%= mhs_baru_pindahan%></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru_laki %>/<%= mhs_baru_perem%></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru_laki + mhs_baru_perem%></td>
		</tr>
		
											<%	
								//prep data prodi baru	
								prev_kdpst = new String(kdpst);
								mhs_baru=0;
								mhs_baru_pindahan=0;
								mhs_baru_laki=0;
								mhs_baru_perem=0;
								if(stpid.equalsIgnoreCase("B")) {
									mhs_baru++;
								}
								else {
									mhs_baru_pindahan++;
								}
								if(kdjek.equalsIgnoreCase("L")) {
									mhs_baru_laki++;
								}
								else {
									mhs_baru_perem++;
								}
							}
							else {
								//prodi sama
								if(stpid.equalsIgnoreCase("B")) {
									mhs_baru++;
								}
								else {
									mhs_baru_pindahan++;
								}
								if(kdjek.equalsIgnoreCase("L")) {
									mhs_baru_laki++;
								}
								else {
									mhs_baru_perem++;
								}
							}
						}
						if(!st.hasMoreTokens()) {
							//data terakhir tutup tabel 
		%>	
		<tr onclick="location.href='go.executeSql_mhs_baru?mhs_perem=<%=mhs_baru_perem %>&mhs_laki=<%=mhs_baru_laki %>&mhs_pindah=<%=mhs_baru_pindahan %>&mhs_baru=<%=mhs_baru %>&target_kdkmp=<%=target_kdkmp %>&target_thsms=<%=target_thsms %>&target_kdpst=<%=prev_kdpst%>'">
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
			<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(kdpst) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru %>/<%= mhs_baru_pindahan%></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru_laki %>/<%= mhs_baru_perem%></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=mhs_baru_laki + mhs_baru_perem%></td>
		</tr>
		<tr>
			<thead>
			<th colspan="4" style="text-align: left; padding: 0px 20px;font-size:1.25em">TOTAL MAHASISWA BARU </th>
			<th colspan="1" style="text-align: center; padding: 0px 10px;font-size:1.25em"><%=tot_mhs %> </th>
			</thead>
		</tr>
		
					<%
						}
						
					}
				}
				
			}

}
%>
  	</tbody>
	</table>
		<%
}

		%>
</center>

</br/>
</div>
</div>	

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>