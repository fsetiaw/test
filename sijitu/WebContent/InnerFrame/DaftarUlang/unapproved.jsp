
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
Vector v_list_unapproved = (Vector) request.getAttribute("v_noapr");
request.removeAttribute("v_noapr");
//System.out.println("v_list_unapproved="+v_list_unapproved.size());
String at_kmp = request.getParameter("at_kmp");
//System.out.println("at_menu_kmp="+at_kmp);

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
<jsp:include page="InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<br/>
        
		<!-- Column 1 start -->
		<%
if(at_kmp!=null) {
	if(v_list_unapproved!=null && v_list_unapproved.size()>0) {
		ListIterator li = v_list_unapproved.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			StringTokenizer st  = new StringTokenizer(brs,"`");
			
			if(brs.startsWith(at_kmp)) {
				if(st.countTokens()==1) {
					out.print("<center><h2>0 MHS</h2></center>");
				}
				else {
					//out.print(brs+"<br/>");
					StringTokenizer stt = new StringTokenizer(brs,"`");
					String nmkmp = stt.nextToken(); //tkn1 = info kmp	
%>
		
<center>
		<table class="table" align="center" width="80%">	
			<thead>
				<tr>
        			<th colspan="3" style="text-align: center; padding: 0px 10px">LIST MAHASISWA YANG MENUNGGU PERSETUJUAN</th>
        		</tr>	
        		<tr>
          			<th width="5%">NO</th>
          			<th width="50%">PRODI</th>
          			<th width="45%"><%=Converter.npmAlias() %></th>
        		</tr>
      		</thead>
      		<tbody>
      		<%
      		int i =1;
      		while(stt.hasMoreTokens()) {
      			
      			String info_mhs = stt.nextToken();
      			StringTokenizer st2 = new StringTokenizer(info_mhs,",");
      			String idobj = st2.nextToken();
      			String npmhs = st2.nextToken();
      			String kdpst = st2.nextToken();
      		
      		%>
				<tr>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %>.</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getNamaKdpst(kdpst) %></td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=npmhs %></td>
					
				</tr>
     		<%
     		}
     		%>
      		</tbody>
		</table>
		</br/>
		</center>
<%					
				}
				
			}
		}
	}		
		
		%>
		

<%
}
%>
 

		
	</div>
</div>		
</body>
</html>	