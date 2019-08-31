<!DOCTYPE html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>

<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<% 

Vector v_mgs_hist = (Vector)session.getAttribute("v_mgs_hist");
//session.removeAttribute("v_mgs_hist"); 
String cmd = request.getParameter("cmd");
String limit_per_page = request.getParameter("limit_per_page");
//String sta_pg = request.getParameter("sta_pg");
String offset = request.getParameter("offset");
String nu_offset_val = new String(offset);
String nav = request.getParameter("nav");
boolean ada_prev=false;
boolean ada_next=false;
//go.getListMhsProfilIncomplete?starting_smawl=&limit_per_page=&init_hal=&search_range=&cmd=&atMenu=index&from=home"
//int at_hal = 1;
//System.out.println(v_mgs_hist.size());


%>

</head>
<body>
	<table width="90%">
		<tr>
			<td style="text-align:left">
<%
if(v_mgs_hist!=null && v_mgs_hist.size()>0) {
	if(Integer.parseInt(offset)>0) {
		ada_prev=true;
		if(offset.equalsIgnoreCase("0")) {
			nu_offset_val = new String(limit_per_page);
		}
		else {
			double test_value = Double.parseDouble(offset)/Double.parseDouble(limit_per_page);
			int at_page = ((int)test_value);
			//offset untuk next page = at_page * limit_per_page
			nu_offset_val=""+(Integer.parseInt(limit_per_page)*(at_page-1));		
		}
		//double test_value = Double.parseDouble(offset)/Double.parseDouble(limit_per_page);
		//at_hal = 
	}
	if(ada_prev) {
		%>
				<a href="go.<%=nav %>?nav=<%=nav %>&limit_per_page=<%= limit_per_page%>&offset=<%= nu_offset_val%>&cmd=<%= cmd%>" style="font-weight:bold;font-size:1.5em" target="inner_iframe">Prev  <</a>
		<%	
	}
	else {
		%>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
	}
	%>
			</td>
			<td style="text-align:center;font-weight:bold;font-size:1.5em;color:#369">
				<%
				if(offset.equalsIgnoreCase("0")) {
					out.print("Hal. 1");
				}
				else {
					out.print("Hal. "+((int)(Double.parseDouble(offset)/Double.parseDouble(limit_per_page))+1));
				}
				%>

			</td>
			<td style="text-align:right">
	<%
	if(v_mgs_hist.size()>Integer.parseInt(limit_per_page)) {
		ada_next = true;
		if(offset.equalsIgnoreCase("0")) {
			nu_offset_val = new String(limit_per_page);
		}
		else {
			double test_value = Double.parseDouble(offset)/Double.parseDouble(limit_per_page);
			int at_page = ((int)test_value);
			//offset untuk next page = at_page * limit_per_page
			nu_offset_val=""+(Integer.parseInt(limit_per_page)*(at_page+1));		
		}
	}
	if(ada_next) {
		%>
				<a href="go.<%=nav %>?nav=<%=nav %>&limit_per_page=<%= limit_per_page%>&offset=<%= nu_offset_val%>&cmd=<%= cmd%>" style="font-weight:bold;font-size:1.5em" target="inner_iframe">>  Next</a>
		<%	
	}
	else {
			%>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<%
	}
}


%>
			</td>
		</tr>
	</table>
</body>
</html>