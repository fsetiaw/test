<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
tr:hover td { 
	background:#82B0C3;
}

</style>
<script>
	jQuery(function() {
	 	jQuery('*[id^=norut_]').hover(function(){
	   		jQuery(this).find("div.info").show();
	   		jQuery(this).find("div.info0").hide();
	    },function(){
	   		jQuery(this).find("div.info").hide();
	   		jQuery(this).find("div.info0").show();
	    });
	 });
	 </script>
<%
//System.out.println("lanjut");
/*
============common variable (ada di ReultSte.jsp & navigasi.jsp) 
*/
int at_page = Integer.parseInt(request.getParameter("at_page"));
String mode = request.getParameter("mode");
int max_pg_tampil = 10;
int max_data_per_pg = Integer.parseInt(request.getParameter("max_data_per_pg"));
int tot_data = 0;
Vector v_list_data = null;
/*
===========enf common variable (ada di ReultSte.jsp & navigasi.jsp)============== 
*/
String target_thsms = Checker.getThsmsNow();
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= (Vector)request.getAttribute("v");

if(v==null || (v!=null && v.size()<1)) {
	v= (Vector)session.getAttribute("v");
}
else {
	session.setAttribute("v",v);	
}
String tipe_ujian = (String)session.getAttribute("tmp");
//System.out.println(); 
if(v!=null && v.size()>5) {
	v_list_data = (Vector)v.clone();
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	
	tot_data = v_list_data.size();
		
	
}
%>
</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_vKartuUjian.jsp" flush="true" />
	
</div>
<div class="colmask fullpage">
<div class="col1">

	<section style="text-align:center">	
		<jsp:include page="navigasi_kartu.jsp" flush="true" />
	</section>
<br />

<!-- Column 1 start -->
<div style="text-align:center">
<%
StringTokenizer st = null;
if(v!=null && v.size()>4) {//4 baris pertama == overhead
%>


<%
	ListIterator li = v.listIterator();
	if(li.hasNext()) {
		//int i = 1;
		String col_type = (String)li.next();
		//String tkn_header = (String)li.next();
		String tkn_align = (String)li.next();
		String tkn_width = (String)li.next();
		String tabel_width = (String)li.next();
		String tkn_header = (String)li.next();
		StringTokenizer stw = new StringTokenizer(tkn_width,"`");
		StringTokenizer sta = new StringTokenizer(tkn_align,"`");
		StringTokenizer sth = new StringTokenizer(tkn_header,"`");
%>

<%
if(Checker.isStringNullOrEmpty(tabel_width)) {
%>
<table class="table" align="center">
<%	
}
else {
%>
<table class="table" style="width:<%=tabel_width%>;border:1px solid #369;" align="center">
<%
}
%>
	<thead>
		<tr>
		<th style="vertical-align:middle;padding:0 5px;height:35px;width:5%;border:1px solid #8AA2A4">
			NO
		</th>
<%
		while(sth.hasMoreTokens()) {
			String lebar = stw.nextToken();
			String teks_align = sta.nextToken();
%>		
			<th style="vertical-align:middle;padding:0 5px;width:<%=lebar%>%;text-align:<%=teks_align%>;border:1px solid #8AA2A4">
				<%=sth.nextToken() %>
			</th>
<%
		}

%>		
		</tr>
	</thead>
	<tbody>
<%
		boolean first = true;
		li = v_list_data.listIterator();
		int no_urut = 0;
		for(int i=(at_page*max_data_per_pg)-max_data_per_pg; i<at_page*max_data_per_pg && i<tot_data;i++) {
		//while(li.hasNext()) {
			no_urut = i+1;
			stw = new StringTokenizer(tkn_width,"`");
			sta = new StringTokenizer(tkn_align,"`");
			sth = new StringTokenizer(tkn_header,"`");
			String brs = (String)v_list_data.elementAt(i);
			//System.out.println("baris="+brs);
			//StringTokenizer stt = new StringTokenizer()
			String target_npmhs = Tool.getTokenKe(brs, 2, "`");
			//System.out.println("target_npmhs="+target_npmhs);
			String url_ext = "";
			if(!Checker.isStringNullOrEmpty(target_npmhs)&&Checker.isStringNullOrEmpty(mode)) {
				url_ext = AddHocFunction.returnUrlExtForPassingParamForProfileCivitasr(target_npmhs);
			}
			
			
			//System.out.println("url_ext="+url_ext);
			if(false){
		%>
		<tr id="norut_<%=no_urut%>" onclick="window.open('go.search?mode=dobel&info_target=<%=brs.replace("`","~")%>','_blank','width=850,height=600')">
		<%					
			}
			else if(!Checker.isStringNullOrEmpty(target_npmhs)) { //kita pake yg ini
				while(brs.contains("'")) {
					brs = brs.replace("'","~"); //nama yg pake ' bermasalah
				}
%>
		<tr id="norut_<%=no_urut%>" onclick="window.open('file.downloadKartu<%=tipe_ujian.substring(0,1).toUpperCase() %><%=tipe_ujian.substring(1,tipe_ujian.length()).toLowerCase() %>_v2?targetUjian=<%=tipe_ujian.toUpperCase() %>&brs=<%=brs%>&thsms=<%=target_thsms %>','_self','width=850,height=600')">
<%			
			}
			else {
%>
		<tr id="norut_<%=no_urut%>">
<%				
			}
			if(first) {
				first = false;
				
		%>		
			<td style="vertical-align:middle;height:35px;padding:0 5px;width:5%;border:1px solid #8AA2A4">
				<%=no_urut %>
			</td>
	<%	
			}
			brs = (String)v_list_data.elementAt(i);
			//System.out.println("brs=="+brs);
			
			st = new StringTokenizer(brs,"`");
			while(st.hasMoreTokens()) {
				String lebar = stw.nextToken();
				String teks_align = sta.nextToken();
			%>		
			<td style="vertical-align:middle;height:35px;padding:0 5px;width:<%=lebar%>%;text-align:<%=teks_align%>;border:1px solid #8AA2A4">
				
				
			<%
				String tmp = st.nextToken();
				if(!st.hasMoreTokens()) {
			%>		
				<div class="info0"> <%=tmp %></div>
				<div class="info" style="display:none;font-weight:bold"> CETAK KARTU <%=tipe_ujian.toUpperCase() %></div>
			<%
				}
				else {
			%>		
				<%=tmp %>

			<%
				}
			%>	
			</td>
			
<%
			}
			%>
		</tr>
			<%
			first = true;
		}
		//while(li.hasNext());
%>	
	</tbody>
</table>
<%
	}
}
else {
%>
	<h2 align="center">0 Result Set</h2>
<%
}
%>
</br/>
</div>
</div>
</div>
</body>
</html>