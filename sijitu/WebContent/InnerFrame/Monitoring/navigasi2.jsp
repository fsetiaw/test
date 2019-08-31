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
Vector v_list_mhs = (Vector)session.getAttribute("v_list_mhs");
String cmd = request.getParameter("cmd");
session.removeAttribute("v_list_mhs");
String limit_per_page = request.getParameter("limit_per_page");
//String at_hal = request.getParameter("init_hal");
String search_range = request.getParameter("search_range");
String starting_smawl = request.getParameter("starting_smawl");
String nav = request.getParameter("nav");
//go.getListMhsProfilIncomplete?starting_smawl=&limit_per_page=&init_hal=&search_range=&cmd=&atMenu=index"



%>

</head>
<body>

<%
if(v_list_mhs!=null) {
	ListIterator li = v_list_mhs.listIterator();
	String brs_info = (String)li.next();
	StringTokenizer st = new StringTokenizer(brs_info,"`");
	boolean ada_prev = Boolean.parseBoolean(st.nextToken());
	String at_hal = st.nextToken();
	boolean ada_next = Boolean.parseBoolean(st.nextToken());
	int tot_row_remain = 0;
	try {
		tot_row_remain = Integer.parseInt(st.nextToken());
	}
	catch(Exception e) {}
	
	st = new StringTokenizer(at_hal,"/");
	at_hal = st.nextToken();
	String tot_hal = st.nextToken();
	//if(tot_row_remain)
	System.out.println("tt_row = "+tot_row_remain);
	System.out.println("at_hal = "+at_hal);
	System.out.println("search_range = "+search_range);
	
	int sta_pg = (Integer.parseInt(at_hal)-3)+1;
	
	System.out.println("sta_pg = "+sta_pg);
	if((tot_row_remain)<Integer.parseInt(search_range)+1) {
		//int tot_hal_needed_after_at_hal  = (int)Math.ceil((double)tot_row_remain/Integer.parseInt(limit_per_page));
		int tot_hal_needed_after_at_hal  = tot_row_remain/Integer.parseInt(limit_per_page);
		System.out.println("tot_hal_needed_after_at_hal="+tot_hal_needed_after_at_hal);
		int max_hal = tot_hal_needed_after_at_hal+Integer.parseInt(at_hal);
		System.out.println("max_hal="+max_hal);
		tot_hal = ""+(max_hal-sta_pg);
		System.out.println("tot_hal="+tot_hal);
		//int end_hal = Integer.parseInt(tot_hal)+sta_pg-1;
		int end_hal = Integer.parseInt(tot_hal)+sta_pg;
		
		System.out.println("end_hal="+end_hal);
		if(end_hal<max_hal) {
			ada_next = true;
		}
		else {
			ada_next = false;
			if(end_hal>max_hal) {
				end_hal = max_hal;
				sta_pg = end_hal-Integer.parseInt(tot_hal)+1;
				
			}
		}
		
		if(sta_pg<0) {
			sta_pg = 1;
			tot_hal = ""+(sta_pg+tot_hal_needed_after_at_hal);
			if(Integer.parseInt(tot_hal)>(Integer.parseInt(search_range)/Integer.parseInt(limit_per_page))) {
				tot_hal = ""+(Integer.parseInt(search_range)/Integer.parseInt(limit_per_page));
				ada_next=true;
				end_hal = Integer.parseInt(tot_hal);
			}
			if(Integer.parseInt(tot_hal)<Integer.parseInt(at_hal)) {
				tot_hal = ""+at_hal;
			}
		}
		System.out.println("sta_pg="+sta_pg);
		System.out.println("tot_hal="+tot_hal);
		//ada_next = false;
	}
	out.print("Page:&nbsp&nbsp");
	if(sta_pg<=0) {
		sta_pg = 1;
	}
	if(ada_prev) {
	%>
		<a href="go.<%=nav %>?nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= limit_per_page%>&init_hal=1&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index" style="font-weight:bold;font-size:1.5em" target="inner_iframe"><<</a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%	
	}
	for(int i=0;i<Integer.parseInt(tot_hal);i++) {
		if((""+sta_pg).equalsIgnoreCase(at_hal)) {
		%>
		<a href="go.<%=nav %>?nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= limit_per_page%>&init_hal=<%= sta_pg%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index" style="font-weight:bold;font-size:1.5em" target="inner_iframe"><%=sta_pg++ %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%			
		}
		else {
	%>
		<a href="go.<%=nav %>?nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= limit_per_page%>&init_hal=<%= sta_pg%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index" target="inner_iframe"><%=sta_pg++ %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%	
		}
	}
	
	if(ada_next) {
		%>
		<a href="go.<%=nav %>?nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= limit_per_page%>&init_hal=<%= sta_pg%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index" style="font-weight:bold;font-size:1.5em" target="inner_iframe">></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%	
	}
}



%>

</body>
</html>