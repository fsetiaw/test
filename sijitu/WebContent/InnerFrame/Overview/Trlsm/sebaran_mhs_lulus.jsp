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
String mhs_lls_trlsm = request.getParameter("mhs_lls_trlsm");
String mhs_lls_wip = request.getParameter("mhs_lls_wip");
String mhs_lls = request.getParameter("mhs_lls");
String show_angel = request.getParameter("show_angel");

Vector v_scope_kdpst_list_distinct_npm_info_kelulusan=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan");
Vector v_scope_kdpst_list_distinct_npm_info_kelulusan_wip=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan_wip");
Vector v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm");

if(v_scope_kdpst_list_distinct_npm_info_kelulusan!=null) {
	try {
		v_scope_kdpst_list_distinct_npm_info_kelulusan = Tool.removeDuplicateFromVector(v_scope_kdpst_list_distinct_npm_info_kelulusan);
		
	}catch(Exception e) {}
	
}

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
int norut=1;
if((v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm==null || v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm.size()<1)&&(v_scope_kdpst_list_distinct_npm_info_kelulusan==null || v_scope_kdpst_list_distinct_npm_info_kelulusan.size()<1)&&(v_scope_kdpst_list_distinct_npm_info_kelulusan_wip==null || v_scope_kdpst_list_distinct_npm_info_kelulusan_wip.size()<1)) {
	out.print("<h1>TOTAL DATA :  0</h1>");
}
else {
	
%>
	<table class="table">
	<thead>
		<tr>
			<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">SEBARAN PENGAJUAN KELULUSAN <%=target_thsms %><br><%=Converter.getNamaKampus(target_kdkmp) %> </th>
		</tr>
		<tr>
			<th width="5%">NO</th>
			<th width="55%">PRODI</th>
			<th width="20%">TOTAL PENGAJUAN</th>
			<th width="20%">PROSES PERSETUJUAN</th>
		</tr>
	</thead>
	<tbody>
<%
	int total_req = 0,total_wip = 0;
	Vector v_scope_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj("s");
	Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
	ListIterator lisc = v_scope_kdpst.listIterator();
	while(lisc.hasNext()) {
		String brs=(String)lisc.next();
		if(brs.startsWith(target_kdkmp+"`")) {
			StringTokenizer st = new StringTokenizer(brs,"`");
			String kdkmp = st.nextToken();
			while(st.hasMoreTokens()) {
				String kdpst_row = st.nextToken();
				//hitung mhs aktif sesuai kampus && kdptst
				int tot_req = 0, tot_wip=0;
				
				//hitung pengajuan
				if(v_scope_kdpst_list_distinct_npm_info_kelulusan!=null && v_scope_kdpst_list_distinct_npm_info_kelulusan.size()>0) {
					ListIterator li_krs = v_scope_kdpst_list_distinct_npm_info_kelulusan.listIterator();
					boolean match_kampus = false;
					while(li_krs.hasNext() && !match_kampus) {
						String baris = (String)li_krs.next();
						if(baris.startsWith(target_kdkmp+"`")) {
							match_kampus = true;
							//JST`65101`61101`65001~61101,6110115...
							StringTokenizer stt = new StringTokenizer(baris,"~");
							//System.out.println("baris=="+baris);
							if(stt.hasMoreTokens()&&stt.countTokens()>1) {
								String tkn_kmp_kdpst = stt.nextToken();
								String tkn_kdpst_npm = stt.nextToken();
								stt = new StringTokenizer(tkn_kdpst_npm,",");
								boolean match_prodi = false;
								while(stt.hasMoreTokens()&& !match_prodi) {
									//KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED
									String prodi = stt.nextToken();
									String npm = stt.nextToken();
									String nmmhs = stt.nextToken();
									String smawl = stt.nextToken();
									String locked = stt.nextToken();
									if(prodi.equalsIgnoreCase(kdpst_row)) {
										match_prodi = true;//token pertama yg math, karena sudah berurutan
									}
									if(match_prodi) {
										tot_req=1;//match token pertama ketemu diatas
										total_req++;
										//hitung wip
										if(match_prodi && (locked.equalsIgnoreCase("false")||locked.equalsIgnoreCase("0"))) {
											tot_wip++;
											total_wip++;
										}
										while(stt.hasMoreTokens() && match_prodi) {
											prodi = stt.nextToken();
											npm = stt.nextToken();
											nmmhs = stt.nextToken();
											smawl = stt.nextToken();
											locked = stt.nextToken();
											if(!prodi.equalsIgnoreCase(kdpst_row)) {
												match_prodi = false;//cek mpe pergantian token kdpst laen
											}
											if(match_prodi) {
												tot_req++;
												total_req++;
											}
											//hitung wip
											if(match_prodi && (locked.equalsIgnoreCase("false")||locked.equalsIgnoreCase("0"))) {
												tot_wip++;
												total_wip++;
											}
										}
									}
								}
							}
							
						}
					}
				}
				

%>
		<tr onclick="location.href='go.executeSql_mhs_lulus?angel_included=<%=show_angel %>&tot_trlsm=<%=mhs_lls_trlsm %>&tot_wip=<%=tot_wip %>&tot_req=<%=tot_req %>&target_kdkmp=<%=target_kdkmp %>&target_thsms=<%=target_thsms %>&target_kdpst=<%=kdpst_row%>'">
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
			<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(kdpst_row) %></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=tot_req%></td>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=tot_wip%></td>
			<%%>
		</tr>
<%
			}
		}	
	}
%>		
		<tr>
			<thead>
			<th colspan="2" style="text-align: left; padding: 0px 20px;font-size:1.25em">TOTAL </th>
			<th colspan="1" style="text-align: center; padding: 0px 10px;font-size:1.25em"><%=total_req++ %> </th>
			<th colspan="1" style="text-align: center; padding: 0px 10px;font-size:1.25em"><%=total_wip++ %> </th>
			</thead>
		</tr>
		
					<%
}
%>
  	</tbody>
	</table>

</center>

</br/>
</div>
</div>	

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>