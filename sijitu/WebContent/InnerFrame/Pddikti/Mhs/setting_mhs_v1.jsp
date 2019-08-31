<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.*" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="beans.dbase.dosen.*" %>
	<%@ page import="beans.dbase.trlsm.*" %>
	<%@ page import="beans.dbase.trnlm.*" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
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
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;padding:0 5px }

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
	System.out.println("target_thsms = "+target_thsms);
	Vector v_dos = Getter.getListDosen_v1();
	ListIterator lid = null;
	//String thsms = request.getParameter("thsms");
	SearchDbDsn sdd = new SearchDbDsn();
	UpdateDbTrnlm udt = new UpdateDbTrnlm();
	SearchDbTrnlm sdt = new SearchDbTrnlm();
	Vector v = null;
	  //v = sdd.getListInfoTrakd(thsms);
	//isert kelas ke trakd_epsbed  
	v = sdt.getListKelasKuliahForFeeder_v1(target_thsms);
	System.out.println("1");
	System.out.println(v.size());
	v = sdt.addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed(v, target_thsms);
	System.out.println("2");
	System.out.println(v.size());
	udt.updateTrnlmKelasEpsbed(v, target_thsms);
	System.out.println("3");
	//System.out.println("1");

	//System.out.println("6");
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

<h2 align="center">ABSEN KELAS PERKULIAHAN <%=target_thsms %></h2><br>
<%
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
%>
	<form action="go.updateTrakdEpsbed" method="post">
	<input type="hidden" name="target_thsms" value="<%=target_thsms %>">
	<table class="table">
		<thead style="background:#369;color:white">
			<tr>
			<th>
			No.
			</th>
			<th style="text-align:left">
			Prodi
			</th>
			<th>
			Kode MK
			</th>
			<th>
			Nama MK
			</th>
			<th>
			Tot Mhs / Tot Kls
			</th>
			<th>
			Kode KLS
			</th>
			<th>
			Nama Dosen
			</th>
			
			
			</tr>
		</thead>	
		<tbody>
<%	
	int norut=1;
	while(li.hasNext()) {
		String brs = (String)li.next();
		Vector v_mhs = (Vector)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
		String kdpst = st.nextToken();
		String kdkmk = st.nextToken();
		String ttmhs = st.nextToken();
		String nakmk = st.nextToken();
		String sksmk = st.nextToken();
		String ttkls = st.nextToken();
		String kode_kls = st.nextToken();
		String nodos = st.nextToken();
		String nmdos = st.nextToken();
		String surat = st.nextToken();
		//if(Integer.parseInt(ttmhs)<7) {
		//out.print(brs+"<br>");
		//}
		if(norut%2==0) {
			%>
			<tr>
				<input type="hidden" name="info_kls" value="<%=kdpst %>`<%=kdkmk %>`<%=kode_kls %>`<%=ttmhs %>`<%=ttkls %>">
				<td align="center" rowspan="2" style="background-color:<%=Constant.mildColorBlu() %>;padding-top: 5px">
					<%=norut++ %>
				</td>
				<td style="background-color:<%=Constant.mildColorBlu() %>">
					<%=Converter.getNamaKdpst(kdpst)+" ["+kdpst+"]" %>
				</td>
				<td align="center" style="background-color:<%=Constant.mildColorBlu() %>">
					<%=kdkmk %>
				</td>
				<td style="background-color:<%=Constant.mildColorBlu() %>">
					<%=nakmk %>
				</td>
				<td align="center" style="background-color:<%=Constant.mildColorBlu() %>">
					<%=ttmhs %>/<%=ttkls %>
				</td>
				<td align="center" style="background-color:<%=Constant.mildColorBlu() %>">
					<%=kode_kls%>
				</td>
				<td align="center" style="background-color:<%=Constant.mildColorBlu() %>;padding:0 0">
					<%=nmdos %></td>
			</tr>
			<tr>
				<td colspan="2">DAFTAR MAHASISWA</td>
				<td colspan="4" style="background-color:white;">
				<%
				int counter = 0;
				ListIterator litmp = v_mhs.listIterator();
				while(litmp.hasNext()) {
					counter++;
					String npmhs = (String)litmp.next();
					out.print(counter+". "+npmhs+"<br>");
				}
				%>
				</td>
			</tr>
		<%	
		}
		else {
		%>
			<tr>
				<input type="hidden" name="info_kls" value="<%=kdpst %>`<%=kdkmk %>`<%=kode_kls %>`<%=ttmhs %>`<%=ttkls %>">
				<td align="center" rowspan="2" style="padding-top: 5px">
					<%=norut++ %>
				</td>
				<td>
					<%=Converter.getNamaKdpst(kdpst)+" ["+kdpst+"]" %>
				</td>
				<td align="center">
					<%=kdkmk %>
				</td>
				<td>
					<%=nakmk %>
				</td>
				<td align="center">
					<%=ttmhs %>/<%=ttkls %>
				</td>
				<td align="center">
					<%=kode_kls%>
				</td>
				<td align="center" style="padding:0 0"><%=nmdos %></td>
			</tr>
			<tr>
				<td colspan="2">DAFTAR MAHASISWA</td>
				<td colspan="4" style="background-color:white;">
				<%
				int counter = 0;
				ListIterator litmp = v_mhs.listIterator();
				while(litmp.hasNext()) {
					counter++;
					String npmhs = (String)litmp.next();
					out.print(counter+". "+npmhs+"<br>");
				}
				%>
				</td>
			</tr>
		<%
		}
	}
}
%>
		</tbody>	
	</table>	
	<br>
	<center><input type="submit" value="UPDATE DOSEN" style="width:200px;height:35px" /></center>
</form>	
</body>
</html>