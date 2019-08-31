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
//System.out.println("atpage="+at_page);
//System.out.println("maxdataperpg="+max_data_per_pg);
%>

	</table>
<%
if(Checker.isStringNullOrEmpty(menu) || !menu.equalsIgnoreCase("none")) {
%>
<div id="header">
	<ul>
		<li>
		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" >
			<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/home_black.png" width="22px" height="17px">
        </a>
		</li>
<%
if(!Checker.isStringNullOrEmpty(backTo)&&(backTo.contains("index_std_belum_aktif.jsp")||backTo.contains("index_std_sudah_aktif.jsp"))) {
	
	%>	
			<li><a href="<%=backTo %>?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK<span><b style="color:#eee">---</b></span></a></li>
		<%
}
else {
	if(!Checker.isStringNullOrEmpty(backTo)&&backTo.equalsIgnoreCase("home_standar_nasional.jsp")) {
		%>	
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/<%=backTo %>?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id_master %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK<span><b style="color:#eee">---</b></span></a></li>
		<%
	}
	else {
			%>	
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>
		<%	
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String id = st.nextToken(); //=id_master
		String keter = st.nextToken();
		String page_name = "home_"+keter.replace(" ", "_")+".jsp";
		if(!Checker.isStringNullOrEmpty(id_master) && id_master.equalsIgnoreCase(id)) {
			if(keter.contains("TENTUKAN")||keter.contains("tentukan")) { //untuk tak bertuan fix di 42 aja
				%>
				<!--  li><a href="<Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/< page_name.toLowerCase()%>?id=<id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active">keter.replace("STANDAR ", "STANDAR<br>")%> </a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar_unknown.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active"><%=keter.replace("STANDAR ", "STANDAR<br>")%> </a></li>
					<%
			}
			else {
					%>
			<!--  li><a href="<Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/< page_name.toLowerCase()%>?id=<id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active">keter.replace("STANDAR ", "STANDAR<br>")%> </a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar_nasional.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" class="active"><%=keter.replace("STANDAR ", "STANDAR<br>")%> </a></li>
				<%
			}	
		}
		else {
			if(keter.contains("TENTUKAN")||keter.contains("tentukan")) { //untuk tak bertuan fix di 42 aja
				%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar_unknown.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><%=keter.replace("STANDAR ", "STANDAR<br>") %> </a></li>
			<%		
			}
			else {
		%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar_nasional.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><%=keter.replace("STANDAR ", "STANDAR<br>") %> </a></li>
		<%		
			}
		}
	}
}

%>
	</ul>
</div>

<!--  div class="colmask fullpage">
	<div class="col1">
	</div>
</div-->		
<%
}
%>
</head>
<body>
</body>
</html>