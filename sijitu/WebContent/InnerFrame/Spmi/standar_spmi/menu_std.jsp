<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String id_master = request.getParameter("id");
//System.out.println("id_master="+id_master);
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
SearchStandarMutu ssm = new SearchStandarMutu();
Vector v = ssm.getListMasterBookOfStandar();
ListIterator li = v.listIterator();
String menu = request.getParameter("menu");
//int at_page = Integer.parseInt(request.getParameter("at_page"));
int at_page = 1; //always starting at 1 bila aksed dari menu
String tmp_max_data_per_pg = request.getParameter("max_data_per_pg");
if(Checker.isStringNullOrEmpty(tmp_max_data_per_pg)) {
	tmp_max_data_per_pg=""+Constant.getMax_data_per_pg();
}	
int max_data_per_pg = Integer.parseInt(tmp_max_data_per_pg);
String backTo = request.getParameter("backTo");
String mode=request.getParameter("mode"); 
String id_tipe_std=request.getParameter("id_tipe_std"); 
String id_master_std=request.getParameter("id_master_std"); 
String at_menu = request.getParameter("at_menu");
int id_std = ssm.getIdStd(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std));
//System.out.println("id_tipe_std="+id_tipe_std);
//System.out.println("id_master_std="+id_master_std);
%>

<%
String keter_match_current_std="";
if(li.hasNext()) {
		
%>
<div id="header">
	<ul>
<%
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String id = st.nextToken(); //=id_master
		
		String keter = st.nextToken();
		String page_name = "home_"+keter.replace(" ", "_")+".jsp";
		if(!Checker.isStringNullOrEmpty(id_master) && id_master.equalsIgnoreCase(id)) {
			keter_match_current_std=new String(keter);
%>
			<!--  li><a href="<Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/< page_name.toLowerCase()%>?id=<id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active">keter.replace("STANDAR ", "STANDAR<br>")%> </a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar_nasional.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" >BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
				<%			
		}
	}
}

if(at_menu!=null && at_menu.equalsIgnoreCase("nama_std")) {
%>
		<li><a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=<%=at_menu %>&max_data_per_pg=<%=max_data_per_pg %>" class="active">STANDAR<span><%=keter_match_current_std.replace("STANDAR", "") %></span></a></li>
<%
}
else {
	%>
		<li><a href="go.getListAllStd?mode=<%=mode %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu=<%=at_menu %>&max_data_per_pg=<%=max_data_per_pg %>">STANDAR<span><%=keter_match_current_std.replace("STANDAR", "") %></span></a></li>
<%	
}
if(at_menu!=null && at_menu.equalsIgnoreCase("manual")) {
%>
		<li><a href="get.prepInfoMan_v1?am_i_terkait=false&am_i_pengawas=false&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_perencanaan_v1.jsp" class="active">MANUAL<span>STANDAR</span></a></li>
<%
}
else {
	%>
		<li><a href="get.prepInfoMan_v1?am_i_terkait=false&am_i_pengawas=false&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_perencanaan_v1.jsp">MANUAL<span>STANDAR</span></a></li>
<%	
}
%>
	</ul>
</div>


</head>
<body>
</body>
</html>