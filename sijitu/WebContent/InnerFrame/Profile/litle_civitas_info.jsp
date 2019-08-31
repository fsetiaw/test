<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String stm = (String)session.getAttribute("status_akhir_mahasiswa");
Vector v= null;
//SearchDbUa sdbu = new SearchDbUa(validUsr.getNpm());
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst"); 
%>

</head>
<body>

<div class="colmask fullpage">
	<div class="col1">
<%	
String stm = (String)session.getAttribute("status_akhir_mahasiswa"); //status mahasiswa bukan status user yg login
String malaikat = (String) session.getAttribute("status_malaikat");

//String malaikat = (String)session.getAttribute("status_malaikat");
boolean keluar = false;
boolean dropout = false;
boolean non_aktif = false;
boolean lulus = false;
if(stm!=null && stm.equalsIgnoreCase("K")) {
	keluar = true;
}
if(stm!=null && stm.equalsIgnoreCase("D")) {
	dropout = true;
}
if(stm!=null && stm.equalsIgnoreCase("N")) {
	non_aktif = true;
}
if(stm!=null && stm.equalsIgnoreCase("L")) {
	lulus = true;
}
	%>
		<!-- Column 1 start -->
		<br />
		<div class="container-fluid">
			<div class="row">
    
   
    <center>
    <table width="95%">
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">NAMA</td>
    		<td>:</td>
    		<%
    		if(malaikat!=null && malaikat.equalsIgnoreCase("true")) {
    			%>
        	<td style="font-size:.8em;font-weight:bold;color:red"><%=nmm.toUpperCase() %></td>
        		<%
    		}
    		else {
    		%>
    		<td style="font-size:.8em;font-weight:bold"><%=nmm.toUpperCase() %></td>
    		<%
    		}
    		%>
    		<td style="font-size:.8em;font-weight:bold">STATUS</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    <%	
    	if(stm.equalsIgnoreCase("K")) {
    %>
    [KELUAR]
    <%	
    	}
    	else if(stm.equalsIgnoreCase("D")) {
    	%>
    [D.O.]
        <%		
    	} 
		else if(stm.equalsIgnoreCase("L")) {
		%>
	[LULUS]
	    <%		
    	}
		else if(stm.equalsIgnoreCase("N")) {
		%>
	[TIDAK AKTIF]
	    <%	  	
    	}
		else if(stm.equalsIgnoreCase("P")) {
			%>
		[PINDAH PRODI]
		    <%	  	
	    	}
    %>
    		</td>
    	</tr>
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">NPM</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold;"><%=npm %></td>
        	<td style="font-size:.8em;font-weight:bold">PRODI</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%=Converter.getDetailKdpst_v1(kdpst)%></td>
    	</tr>	
    	</tr>
    	<%
    	
    	if(validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {	
    		String info = validUsr.getUsrnameAndPwd(npm);
    		if(info!=null) {
    			StringTokenizer st = new StringTokenizer(info,"`");
    	%>
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">USER</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
    		</td>
    		<td style="font-size:.8em;font-weight:bold">PWD</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
			</td>
    	</tr>
    	<%
    		}
    	}
    	
    	%>
    </table>
    </center>
    <%
    
%>	
	</div>
</div>	

</body>
</html>