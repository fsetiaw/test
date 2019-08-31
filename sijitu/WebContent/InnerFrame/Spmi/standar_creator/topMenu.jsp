
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<div>
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	String atMenu = request.getParameter("atMenu");
	String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
	kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
	String kdpst = st.nextToken();
	String nmpst = st.nextToken();
	String kdkmp = st.nextToken();
	String mode = request.getParameter("mode");
	boolean cuma_back_aja = false;
	String backTo = request.getParameter("backTo");
	if(!Checker.isStringNullOrEmpty(backTo)) {
		cuma_back_aja = true;
	}
	
	boolean hidden = false;
	if(v_cf!=null && v_cf.size()>0) {	
	%>
	
<ul>

<%
	if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("edit_list_view")) {
	%>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">BACK<span>&nbsp</span></a></li>
	<!--  li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20"" target="inner_iframe">BACK1<span>&nbsp</span></a></li -->
	<%		
	}
	else if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("edit")) {
	%>	
	<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20"" target="inner_iframe">BACK<span>&nbsp</span></a></li>
	<%		
	}
	else {
%>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
<%
		if(validUsr.amI("KEPALA PENJAMINAN MUTU")) {
			if(atMenu!=null && atMenu.equalsIgnoreCase("edit_isi")) {
				hidden = true;
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list" target="inner_iframe" class="active">UBAH USULAN<span>JADI STANDAR</span></a></li>
<%
			}
			else {
%>
<!--  li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list" target="inner_iframe">UBAH USULAN<span>JADI STANDAR</span></a></li -->
<%		
			}
		}

		if(!cuma_back_aja && !hidden) {
			if(atMenu==null || Checker.isStringNullOrEmpty(atMenu)) {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" class="active">LIST USULAN<span>PROSES STANDARISASI</span></a></li>
<%			
			}
			else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">LIST USULAN<span>PROSES STANDARISASI</span></a></li>
<%
			}
			if(atMenu!=null && atMenu.equalsIgnoreCase("form_isi")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/input_form_single.jsp?atMenu=form_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" class="active">FORM USULAN<span>STANDARISI</span></a></li>
	<%
			}
			else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/input_form_single.jsp?atMenu=form_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">FORM USULAN<span>STANDARISI</span></a></li>
	<%		
			}
		
			
		}
	}	
	%>
	
	
</ul>
	<%
	
	}
	%>


</div>