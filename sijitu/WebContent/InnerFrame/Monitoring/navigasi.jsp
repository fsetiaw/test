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
//System.out.println("at navigasi");
Vector v_list_mhs = (Vector)session.getAttribute("v_list_mhs");
String cmd = request.getParameter("cmd");
session.removeAttribute("v_list_mhs");
int tot_data = 0;
if(v_list_mhs!=null) {
	/*
	max_target_data_size  adalah search_range+1
	dan juga pertimbangkan offset = (at_hal-1)* limit_perpage
	*/
	tot_data = v_list_mhs.size();
	
}
//System.out.println("tot_data="+tot_data);
double limit_per_page = Integer.parseInt(request.getParameter("limit_per_page")) ;
//System.out.println("limit_per_page="+limit_per_page);
int at_hal = Integer.parseInt(request.getParameter("at_hal"));
//System.out.println("at_hal="+at_hal);
int search_range = Integer.parseInt(request.getParameter("search_range"));
//System.out.println("search_range="+search_range);
int starting_page_shown = Integer.parseInt(request.getParameter("starting_page_shown"));
//System.out.println("starting_page_shown="+starting_page_shown);
double ending_page_shown = Double.parseDouble(request.getParameter("ending_page_shown"));
//System.out.println("ending_page_shown="+ending_page_shown);
String starting_smawl = request.getParameter("starting_smawl");
String nav = request.getParameter("nav");
//System.out.println("nav="+nav);
String prev = request.getParameter("prev");
//System.out.println("prev="+prev);
String next = request.getParameter("next");
//System.out.println("next="+next);
boolean ada_prev = Boolean.parseBoolean(prev);
//System.out.println("ada_prev="+ada_prev);
boolean ada_next = Boolean.parseBoolean(next);
//System.out.println("ada_next="+ada_next);
boolean proses_next = false;
boolean proses_prev = false;












//go.getListMhsProfilIncomplete?starting_smawl=&limit_per_page=&at_hal=&search_range=&cmd=&atMenu=index"



%>

</head>
<body>

<%
double range_design_tot_hal_shown= search_range/limit_per_page;
//====calc ending page shown===========
if(ending_page_shown==0) {
	ending_page_shown = (starting_page_shown-1)+range_design_tot_hal_shown;
}
else if(at_hal>ending_page_shown) {
	// > yg diklik (next)
	starting_page_shown = at_hal;
	ending_page_shown = (starting_page_shown-1)+range_design_tot_hal_shown;
	proses_next=true;
}
else if(at_hal<starting_page_shown) {
	ending_page_shown = at_hal;
	starting_page_shown = (int)((ending_page_shown+1)-range_design_tot_hal_shown);
	proses_prev=true;
	ada_next=true;
}
//====end calc ending page shown===========

//====calc ada_prev===========
if(starting_page_shown>1) {
	ada_prev = true;
}
else {
	ada_prev = false;
}
//====end calc ada_next===========

if(at_hal==0||at_hal==1) {
	//initialize : panggilan pertama dari home
	if(tot_data>search_range) {
		ada_next = true;
	}
	else {
		//cek berapa hal yg akan tampil
		ending_page_shown = Math.ceil(tot_data/limit_per_page);
	}
	at_hal=1;
}
else if(proses_next){
	if(tot_data>search_range) {
		ada_next = true;
	}
	else {
		//cek berapa hal yg akan tampil
		ending_page_shown = Math.ceil(tot_data/limit_per_page)+(starting_page_shown-1);
		//ending_page_shown = (starting_page_shown-1)+range_design_tot_hal_shown;
		ada_next = false;
	}
}


//System.out.println("tot_data = "+tot_data);
//System.out.println("at hal = "+at_hal);
//System.out.println("ada prev = "+ada_prev);
//System.out.println("ada next = "+ada_next);
//System.out.println("start hal shown= "+starting_page_shown);
//System.out.println("ending hal shown= "+ending_page_shown);

if(ada_prev) {
	%>
	<a href="#" onclick="(function(){
		//scroll(0,0);
		parent.scrollTo(0,0);
 		var x = document.getElementById('wait');
 		var y = document.getElementById('main');
 		x.style.display = 'block';
 		y.style.display = 'none';
 		location.href='go.<%=nav %>?prev=<%=ada_prev %>&next=<%=ada_next %>&starting_page_shown=<%=(int)starting_page_shown %>&ending_page_shown=<%=(int)ending_page_shown %>&nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= (int)limit_per_page%>&at_hal=<%= (int)(starting_page_shown-1)%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index'})()"
 		style="font-weight:bold;font-size:1.5em" target="inner_iframe"><</a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%	
}
int tmp_hal = starting_page_shown;
for(;tmp_hal<=ending_page_shown;tmp_hal++) {
	
	if(tmp_hal==at_hal) {
		%>
		<a href="#" onclick="(function(){
		//scroll(0,0);
		parent.scrollTo(0,0);
 		var x = document.getElementById('wait');
 		var y = document.getElementById('main');
 		x.style.display = 'block';
 		y.style.display = 'none';
 		location.href='go.<%=nav %>?prev=<%=ada_prev %>&next=<%=ada_next %>&starting_page_shown=<%=(int)starting_page_shown %>&ending_page_shown=<%=(int)ending_page_shown %>&nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= (int)limit_per_page%>&at_hal=<%= (int)tmp_hal%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index'})()"
 		style="font-weight:bold;font-size:1.5em" target="inner_iframe"><%=tmp_hal %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%			
	}
	else {
	%>
		<a href="#" onclick="(function(){
		//scroll(0,0);
		parent.scrollTo(0,0);
 		var x = document.getElementById('wait');
 		var y = document.getElementById('main');
 		x.style.display = 'block';
 		y.style.display = 'none';
 		location.href='go.<%=nav %>?prev=<%=ada_prev %>&next=<%=ada_next %>&starting_page_shown=<%=(int)starting_page_shown %>&ending_page_shown=<%=(int)ending_page_shown %>&nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= (int)limit_per_page%>&at_hal=<%= (int)tmp_hal%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index'})()"
 		target="inner_iframe"><%=tmp_hal %></a>&nbsp&nbsp&nbsp&nbsp&nbsp
	<%	
	}
}
	
if(ada_next) {
		%>
	<a href="#" onclick="(function(){
		//scroll(0,0);
		parent.scrollTo(0,0);
 		var x = document.getElementById('wait');
 		var y = document.getElementById('main');
 		x.style.display = 'block';
 		y.style.display = 'none';
 		location.href='go.<%=nav %>?prev=<%=ada_prev %>&next=<%=ada_next %>&starting_page_shown=<%=(int)starting_page_shown %>&ending_page_shown=<%=(int)ending_page_shown %>&nav=<%=nav %>&starting_smawl=<%= starting_smawl%>&limit_per_page=<%= (int)limit_per_page%>&at_hal=<%= (int)ending_page_shown+1%>&search_range=<%= search_range%>&cmd=<%= cmd%>&atMenu=index'})()"
	 	style="font-weight:bold;font-size:1.5em" target="inner_iframe">></a>&nbsp&nbsp&nbsp&nbsp&nbsp
		<%	
}


%>

</body>
</html>	