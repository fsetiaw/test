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
int at_page = Integer.parseInt(request.getParameter("at_page"));
int max_pg_tampil = 10;
int max_data_per_pg = Integer.parseInt(request.getParameter("max_data_per_pg"));
double tot_data = 0;

//System.out.println("at_page="+at_page);
//System.out.println("max_data_per_pg="+max_data_per_pg);
//System.out.println("tot_data="+tot_data);
//System.out.println("max_pg_tampil="+max_pg_tampil);
Vector v_list_data = null;
/*
===========enf common variable (ada di ReultSte.jsp & navigasi.jsp)============== 
*/
String mode = request.getParameter("mode");
String backTo = request.getParameter("backTo"); //ini untuk backto dari  standar yang belum aktif /aktif
String id_master_title = request.getParameter("id");
String id_tipe_std = request.getParameter("id_tipe_std");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String backto=request.getParameter("backto");
boolean ada_prev = false;
boolean ada_next = false;
int first_index = 0;
int last_index = 0;
int max_index = 0;

Vector v = (Vector)session.getAttribute("v");
if(v!=null && v.size()>0) {
	v_list_data = (Vector)v.clone();
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
String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
//System.out.println(target);
String uri = request.getRequestURI();
//System.out.println(uri);
String url = PathFinder.getPath_v2(uri, target);
//System.out.println(url);
if(tot_data>0 && last_index>1) {
	if(ada_prev) {
		
		if(first_index>1) {
			//mode=view&&at_page=<at_page>&max_data_per_pg=<max_data_per_pg 
		%>
		
		
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=1&max_data_per_pg=<%=max_data_per_pg %>" style="font-weight:bold;font-size:1.6em" target="inner_iframe"><<</a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
		}
	%>
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=at_page-1 %>&max_data_per_pg=<%=max_data_per_pg %>" style="font-weight:bold;font-size:1.5em" target="inner_iframe"><</a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%
	}
//InnerFrame/sql/ResultSet_v4.jsp?at_page=1&max_data_per_pg=25";
	for(int i=first_index;i<=last_index;i++) {
		if(i==at_page) {
		%>
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=i %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.5em"><%=i %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
			<%	
		}
		else {
		%>
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=i %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe"><%=i %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
		}
	}
					
	
if(ada_next) {
		%>
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=at_page+1 %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.5em">></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%	
		if(last_index<max_index) {
			%>
	<a href="<%=url %>?backTo=<%=backTo %>&mode=<%=mode %>&id=<%=id_master_title %>&id_tipe_std=<%=id_tipe_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&backto=<%=backto %>&first_index=<%=first_index %>&at_page=<%=max_index %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" style="font-weight:bold;font-size:1.6em">>></a>&nbsp&nbsp&nbsp&nbsp&nbsp
				<%	
		}
}

}
%>

</body>
</html>