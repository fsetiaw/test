<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
v = (Vector)session.getAttribute("v");
String tkn_header = (String)session.getAttribute("tkn_header");

//System.out.println(kdpst);
//System.out.println(nmpst);
%>

</head>
<body>
<div id="header">
<ul>
<%
//dibawah ini default back - jadi kalo mo cutom harus pass parameter (sekarang  belum ditentukan parameternya);
%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		
		//int norut=1;
		if(v!=null && v.size()>0) {
			int i=1;	
			ListIterator li = v.listIterator();
		%>
		<table align="center" border="1px" bordercolor="369" style="background:#d9e1e5;color:#000;width:660px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><B> 
        		DATA YG BELUM BERHASIL DIPERBAIKI
        		</B> </td>
        	</tr>
        	<tr>
        	<%
        	//StringTokenizer st = new StringTokenizer(tkn_header,"`");
        	%>
        		<td style="background:#369;color:#fff;text-align:center;width:10px"><B>NO.</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:150px"><B>NPM</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:300px"><B>NAMA</B> </td>
        	</tr>
        <%
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String npmhs = st.nextToken();
        		String value = st.nextToken();
        	
        %>			
        			<tr>
        				
        				<td style="color:#000;text-align:center;"><B><%=i++ %></B> </td>
        				<td style="color:#000;text-align:center;"><B><%=npmhs %></B> </td>
        				<td style="color:#000;text-align:left;"><B><%=value %></B> </td>
        				
        			</tr>
        <%
        	}
        %>			
        </table>
        <%
		}
		else {
		%>
			<h2 align="center">Perbaikan Data Telah Selesai </h2>
		<%	
		}
        %>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>