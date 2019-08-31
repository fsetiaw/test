
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
	String kdpst = request.getParameter("target_kdpst");
	String nmpst = request.getParameter("target_nmpst");
	String kdkmp = request.getParameter("target_kdkmp");

	
	if(v_cf!=null && v_cf.size()>0) {	
	%>
	
<ul>	
<%
		if(atMenu!=null && atMenu.equalsIgnoreCase("spmi")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp?atMenu=spmi" target="inner_iframe" class="active">HOME<span>&nbsp</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp?atMenu=spmi" target="inner_iframe">HOME<span>&nbsp</span></a></li>
	<%		
		}
		if(atMenu!=null && atMenu.equalsIgnoreCase("bijak")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kebijakan/kebijakanSpmi.jsp?atMenu=bijak" target="inner_iframe" class="active">KEBIJAKAN<span>SPMI</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kebijakan/kebijakanSpmi.jsp?atMenu=bijak" target="inner_iframe">KEBIJAKAN<span>SPMI</span></a></li>
	<%		
		}
		if(atMenu!=null && atMenu.equalsIgnoreCase("monitor")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Monitoring/indexMonitor.jsp?atMenu=monitor" target="inner_iframe" class="active">MONITOR<span>SPMI</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Monitoring/indexMonitor.jsp?atMenu=monitor" target="inner_iframe">MONITOR<span>SPMI</span></a></li>
	<%		
		}	

		///ToUnivSatyagama/WebContent/InnerFrame/Spmi/standar_creator/indexCreator.jsp
		if(atMenu!=null && atMenu.equalsIgnoreCase("design")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst=<%=kdpst%>&nmpst=<%=nmpst%>&kdkmp=<%=kdkmp%>" target="inner_iframe" class="active">USULAN STANDAR<span>BARU</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst=<%=kdpst%>&nmpst=<%=nmpst%>&kdkmp=<%=kdkmp%>" target="inner_iframe">USULAN STANDAR<span>BARU</span></a></li>
	<%		
		}	
		if(atMenu!=null && atMenu.equalsIgnoreCase("form_isi")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/form/std_isi/input_form.jsp?atMenu=form_isi&kdpst=<%=kdpst%>&nmpst=<%=nmpst%>&kdkmp=<%=kdkmp%>" target="inner_iframe" class="active">FORM INPUT<span>STANDAR ISI</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/form/std_isi/input_form.jsp?atMenu=form_isi&kdpst=<%=kdpst%>&nmpst=<%=nmpst%>&kdkmp=<%=kdkmp%>" target="inner_iframe">FORM INPUT<span>STANDAR ISI</span></a></li>
	<%		
		}	
	%>
	
	<!-- li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/indexBos.jsp" target="inner_iframe">BUKU<span>STANDAR</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Penetapan/penetapanSpmi.jsp" target="inner_iframe">MANUAL<span>PENETAPAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Pelaksanaan/pelaksanaanSpmi.jsp" target="inner_iframe">MANUAL<span>PELAKSANAAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kontrol/controlSpmi.jsp" target="inner_iframe">MANUAL<span>PENGENDALIAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Evaluasi/peningkatanSpmi.jsp" target="inner_iframe">MANUAL<span>PENINGKATAN STD</span></a></li  -->
	
</ul>
	<%
	
	}
	%>


</div>