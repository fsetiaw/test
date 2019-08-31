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
	String target_thsms = request.getParameter("thsms");
	//String target_kdpst = request.getParameter("kdpst");
	//Vector v_dos = Getter.getListDosen_v1();
	ListIterator lid = null;
	//String thsms = request.getParameter("thsms");
	Vector v_list = (Vector)request.getAttribute("v");
	  //v = sdd.getListInfoTrakd(thsms);
	//isert kelas ke trakd_epsbed  
	
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
	okay2 <%=target_thsms %>
	<br>
<%

if(v_list!=null && v_list.size()>0) {
	int i =0;
	ListIterator li = v_list.listIterator();
	if(li.hasNext()) {
		i++;
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String prev_nik=st.nextToken();
		String prev_nmmhs=st.nextToken();
		String prev_tglhr=st.nextToken();
		String prev_kdpst=st.nextToken();
		String prev_npmhs=st.nextToken();
		String nm_prodi = Converter.getNamaKdpst(prev_kdpst);
		out.println(i+". "+brs.replace("`"+prev_kdpst+"`", "`"+nm_prodi+"`")+"<br>");
		while(li.hasNext()) {
			i++;
			brs = (String)li.next();
			st = new StringTokenizer(brs,"`");
			String nik=st.nextToken();
			String nmmhs=st.nextToken();
			String tglhr=st.nextToken();
			String kdpst=st.nextToken();
			nm_prodi = Converter.getNamaKdpst(kdpst);
			if((prev_nik.equalsIgnoreCase(nik) && !Checker.isStringNullOrEmpty(prev_nik))||prev_nmmhs.equalsIgnoreCase(nmmhs)||prev_tglhr.equalsIgnoreCase(tglhr)) {
			%>
				<p style="font-weight:bold;font-size:1.5em"><%=i+". "+brs.replace("`"+kdpst+"`", "`"+nm_prodi+"`") %></p>
			<%
			}
			else {
				out.println(i+". "+brs.replace("`"+kdpst+"`", "`"+nm_prodi+"`")+"<br>");
			}
			prev_nik = new String(nik);
			prev_nmmhs = new String(nmmhs);
			prev_tglhr = new String(tglhr);

		}
		
		
		
	}
}
%>	
	</div>
</div>	
	
</body>
</html>