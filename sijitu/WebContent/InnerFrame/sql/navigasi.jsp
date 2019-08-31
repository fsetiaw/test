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

/*
============common variable (ada di ReultSte.jsp & navigasi.jsp) 
*/

String norut_colnpm = request.getParameter("norut_col_npm");
int norut_col_npm = 2;//default 
if(!Checker.isStringNullOrEmpty(norut_colnpm)) {
	norut_col_npm =Integer.parseInt(norut_colnpm); 
}
int at_page = Integer.parseInt(request.getParameter("at_page"));
int max_pg_tampil = 10;
int max_data_per_pg = Integer.parseInt(request.getParameter("max_data_per_pg"));
double tot_data = 0;
Vector v_list_data = null;
/*
===========enf common variable (ada di ReultSte.jsp & navigasi.jsp)============== 
*/

String backto=request.getParameter("backto");
boolean ada_prev = false;
boolean ada_next = false;
int first_index = 0;
int last_index = 0;
int max_index = 0;

Vector v = (Vector)session.getAttribute("v");
if(v!=null && v.size()>5) {
	v_list_data = (Vector)v.clone();
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	v_list_data.remove(0);
	
	tot_data = v_list_data.size();
		
	
}
if(v_list_data!=null) {
	tot_data = v_list_data.size();
	max_index = (int)Math.ceil(tot_data/max_data_per_pg);
	////System.out.println("total="+tot_data/max_data_per_pg);
	////System.out.println("total ceil="+(int)Math.ceil(tot_data/max_data_per_pg));
}
if(at_page==1) {
	first_index = at_page;
	last_index = first_index+max_pg_tampil-1;
	if(tot_data-(max_pg_tampil*last_index)>0) {
		ada_next = true;
	}
}
else {
	first_index = Integer.parseInt(request.getParameter("first_index"));
	last_index = first_index+max_pg_tampil-1;
	if(tot_data-(max_pg_tampil*last_index)>0) {
		ada_next = true;
	}
	if(first_index==1 && at_page>(int)Math.ceil(0.6*max_pg_tampil)) {
		first_index = first_index+at_page-(int)Math.ceil(0.6*max_pg_tampil);
		last_index = first_index+max_pg_tampil-1;
	}
	else if(first_index!=1) {
		first_index = at_page-5; 
		if(first_index<1) {
			first_index=1;
		}
		last_index = first_index+max_pg_tampil-1;
	}
	
}
//String cmd = request.getParameter("cmd");




if(last_index>=max_index) {
	last_index = max_index;
	ada_next=false;
}
if(last_index>max_pg_tampil && last_index-first_index<max_pg_tampil) {
	first_index = last_index - max_pg_tampil +1;	
	//System.out.println("---last_index = "+last_index);
	//System.out.println("---max_pg_tampil = "+max_pg_tampil);
	//System.out.println("---first_index = "+first_index);
}
if(last_index<1) {
	last_index = 1;
}
if(first_index<1) {
	first_index = 1;
}
if(first_index>1) {
	ada_prev=true;
}
//System.out.println("first_index = "+first_index);
%>

</head>
<body>

<%
String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_v4.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
if(tot_data>0 && last_index>1) {
	if(ada_prev) {
		
		if(first_index>1) {
		%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=1&max_data_per_pg=<%=max_data_per_pg %>" style="font-weight:bold;font-size:1.6em" target="inner_iframe"><<</a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
		}
	%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=at_page-1 %>&max_data_per_pg=<%=max_data_per_pg %>" style="font-weight:bold;font-size:1.5em" target="inner_iframe"><</a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%
	}
//InnerFrame/sql/ResultSet_v4.jsp?at_page=1&max_data_per_pg=25";
	for(int i=first_index;i<=last_index;i++) {
		if(i==at_page) {
		%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=i %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.5em"><%=i %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
			<%	
		}
		else {
		%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=i %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe"><%=i %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
		}
	}
					
	
if(ada_next) {
		%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=at_page+1 %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.5em">></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%	
		if(last_index<max_index) {
			%>
	<a href="<%=url %>?backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=max_index %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.6em">>></a>&nbsp&nbsp&nbsp&nbsp&nbsp
				<%	
		}
}

}
%>

</body>
</html>