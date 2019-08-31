<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>

<%@ page import="beans.dbase.krklm.SearchDbKrklm" %>
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
	String target_kdpst = request.getParameter("kdpst");
	//Vector v_dos = Getter.getListDosen_v1();
	ListIterator lid = null;
	//String thsms = request.getParameter("thsms");
	SearchDbDsn sdd = new SearchDbDsn();
	SearchDbTrnlm sdt = new SearchDbTrnlm();
	Vector v = (Vector)session.getAttribute("v_kur_npm");
	session.removeAttribute("v_kur_npm");
	  //v = sdd.getListInfoTrakd(thsms);
	//isert kelas ke trakd_epsbed  
	
	////System.out.println("1");

	////System.out.println("6");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
	<jsp:include page="innerMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">

	<br>
<%
int i = 0;
if(v!=null && v.size()>0) {
	out.print("total="+v.size()+"<br><br>");
	String list_npm = "";
	ListIterator li = v.listIterator();
	boolean first = true;
	String prev = null;
	while(li.hasNext()) {
		
		
		String brs = (String)li.next();
		//System.out.println(brs+"<br>");
		StringTokenizer st = new StringTokenizer(brs,"`");
		if(first) {
			first = false;
			i++;
			String kur = st.nextToken();
			//kur.replaceAll("\\s+","");
			String npm = st.nextToken();
			prev = new String(kur);
			list_npm = list_npm+"`"+npm;
		}
		else {
			
			String kur = st.nextToken();
			//kur.replaceAll("\\s+","");
			String npm = st.nextToken();
			
			if(prev.equalsIgnoreCase(kur)) {
				i++;
				list_npm = list_npm+"`"+npm;
			}
			else {
				out.print("Kurikulum "+prev+" ["+SearchDbKrklm.getNamaDanSksttKrklm(Integer.parseInt(prev))+"] = "+i+"<br>");
				out.print("["+list_npm+"]<br><br>");
				i=1;
				prev = new String(kur);
				list_npm = new String(npm);
			}
		}
		if(!li.hasNext()) {
			out.print("Kurikulum "+prev+" ["+SearchDbKrklm.getNamaDanSksttKrklm(Integer.parseInt(prev))+"] = "+i+"<br>");
			out.print("["+list_npm+"]<br><br>");
		}
		//out.print(i+". "+brs+"<br>");
	}
}
%>	
	</div>
</div>	
	
</body>
</html>