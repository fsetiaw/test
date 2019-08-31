<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<style>
.table { 
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>; 
//table-layout: fixed;
}
.table thead > tr > th { 
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; word-wrap:break-word;}
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; word-wrap:break-word;}
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px ;word-wrap:break-word;}
/*
.table tr:hover td { background:#82B0C3;word-wrap:break-word; }
.table thead:hover td { background:#82B0C3;word-wrap:break-word; } <thead> yg jd anchor
*/
</style>
<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
<%
	//System.out.println("anehh");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage"); //callerPage & backTo agak rancu
	String fwdPage = request.getParameter("fwdPage"); //forward mode 
	String backTo = request.getParameter("backTo"); //callerPage & backTo agak rancu
	
	String thisPage = request.getRequestURI();
	String cmd = ""+request.getParameter("cmd");
	//System.out.println("cmd="+cmd);
	String atMenu = ""+request.getParameter("atMenu");
	//System.out.println("atMenu="+atMenu);
	String scope = request.getParameter("scope");
	//System.out.println("scope="+scope);
	String uri = request.getRequestURI();
	String redirectForOnlyOneScope="";
	String lockedMsg = (String)session.getAttribute("lockedMsg");	//only for requestBukakelas	
	String scopeType = ""+request.getParameter("scopeType");
	/*
		VARIABLE DIBAWAH INI BERASAL DARI /InnerFrame/innerMenu digunakan untuk masking Bahan Ajar
	*/
			
	String id_obj_mask=""+request.getParameter("id_obj");
	String nmm_mask=""+request.getParameter("nmm");
	String npm_mask=""+request.getParameter("npm");
	String obj_lvl_mask=""+request.getParameter("obj_lvl");
	String kdpst_mask=""+request.getParameter("kdpst");
	//System.out.println("cmd="+cmd);
	//System.out.println("nmm="+nmm_mask);
	//System.out.println("backTo at setTargetKdpst="+backTo);
	//System.out.println("fwdPage at setTargetKdpst="+fwdPage);
//System.out.println("scope at setTargetKdpst="+scope);
	//System.out.println("ur at isetTargetKdpst="+uri);
	//System.out.println("<br/>atMenu at setTargetKdpst="+atMenu);
	
	Vector vSc = null;
	if(scope.contains("kurikulum")||scope.contains("kelas")||scope.contains("Kelas")||scopeType.equalsIgnoreCase("prodiOnly")) {
		vSc = validUsr.getScopeUpd7des2012ProdiOnly(scope);
		//System.out.println("scope prodi only");
		//System.out.println("vSc.size()="+vSc.size());
	}
	else {
		vSc = validUsr.getScopeUpd7des2012(scope);
	}
%>

</head>
<%
	if(vSc!=null && vSc.size()==1) {
%>
<body onload="submitform()">
<%
	
	}
	else {
%>
<body>
<%
	}
%>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
	<ul>
	<%
	//String backTo= request.getParameter("backTo");
	if(backTo!=null) {
		backTo = backTo.replace("Titik", ".");
		backTo = backTo.replace("TandaTanya", "?");
		backTo = backTo.replace("SamaDgn", "=");
	}
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage)) {
	%>
		<li><a href="<%=callerPage %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
	<%
	}
	else if(backTo!=null && !Checker.isStringNullOrEmpty(backTo)){
		String backToTarget=backTo.replace("Titik", ".").replace("TandaTanya", "?").replace("SamaDgn", "=");
		String backForParam=backTo.replace(".", "Titik").replace("?", "TandaTanya").replace("=", "SamaDgn");
		%>
		<li><a  href="#" onclick="(function(){
					//scroll(0,0);
					parent.scrollTo(0,0);
 					var x = document.getElementById('wait');
 					var y = document.getElementById('main');
 					x.style.display = 'block';
 					y.style.display = 'none';
 					location.href='<%=backToTarget %>?backTo=<%=backForParam%>'})()"
		  	target="_self"
		  	>BACK<span>&nbsp</span></a></li>
	<%

	}
	else {
	%>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span>&nbsp</span></a></li>
		<%
	}
	
	

	%>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
	
		<br />
		<!-- Column 1 start -->

		<%
		/*
		* router - redirection buat scop1 dan >1
		*/
		
		if(vSc!=null && vSc.size()==1) {
		//if(false) {	
		%>
			<script>
			//var auto_refresh = setInterval(function() { submitform(); }, 500);

			function submitform()
			{
  			//alert('test');
  				document.getElementById("singleOpt").submit();
			}
			</script>
		
		<%	
		}
		//System.out.println("fwdPage is "+fwdPage);
		//System.out.println("vsc is "+vSc.size());
		if(fwdPage!=null && !Checker.isStringNullOrEmpty(fwdPage)) {
			//System.out.println("sudah betul1");
			//System.out.println("fwdPage="+fwdPage);
			%>
			<form action="<%=fwdPage %>" target="_self" id="singleOpt">
			<%
		}
		else {
			if(cmd!=null && (cmd.equalsIgnoreCase("viewKurikulum") || cmd.equalsIgnoreCase("bukaKelas")  || cmd.equalsIgnoreCase("mba"))) {
				
				//System.out.println("sudah betul2");
				if(vSc.size()>0) { // bila operator punya scope >1 prodi
					//System.out.println("disini - 1 -");
					//System.out.println("sudah betul3");
			%>
			<form action="go.getListKurikulum?callerPage=<%=callerPage %>" target="_self" id="singleOpt">
				<input type="hidden" name="id_obj" value="<%=id_obj_mask %>" />
				<input type="hidden" name="nmm" value="<%=nmm_mask %>" />
				<input type="hidden" name="npm" value="<%=npm_mask %>" />
				<input type="hidden" name="obj_lvl" value="<%=obj_lvl_mask %>" />
				<input type="hidden" name="kdpst" value="<%=kdpst_mask %>" />
	
			<%	
				}
				else {
					//System.out.println("sudah betul4");
				
					//System.out.println("ERROR setTargetKdpst line 148: ngga boleh bisa kesisni");
				}
			}
			else {
				if(cmd!=null && cmd.equalsIgnoreCase("viewMhsPerKelas")) {
					
					//System.out.println("sudah betul5");
					if(vSc.size()>0) { // bila operator punya scope >1 prodi
						//System.out.println("sudah betul6");
			%>
			<form action="go.prepMhsPerKelas?callerPage=<%=callerPage %>" target="_self" id="singleOpt">
			<%
					}
				}
				else {	
					if(vSc.size()>0) { // bila operator punya scope >0 prodi
						//System.out.println("sudah betul7");
			%>
			<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/dashAkademi.jsp" target="_self" id="singleOpt">
			<%
					}
				}
			}
		}
	
		if(cmd!=null && cmd.equalsIgnoreCase("bukaKelas")) {
			vSc=(Vector)session.getAttribute("vScopeBukaKelas");
			//System.out.println("vSc="+vSc.size());
			session.removeAttribute("lockedMsg");
			session.removeAttribute("vScopeBukaKelas");
			if(vSc==null) {
				vSc = new Vector();
			}
		//liSc = vSc.listIterator();
		}

		if(vSc!=null && vSc.size()>0) {
			ListIterator liSc = vSc.listIterator();
			String baris = (String)liSc.next();
			String pemisah = Checker.getSeperatorYgDigunakan(baris);
			
			StringTokenizer st = new StringTokenizer(baris);
			String tmp = st.nextToken();
			
				%>
				<input type="hidden" name="cmd" value="<%=cmd%>" /> 
				<input type="hidden" name="atMenu" value="<%=atMenu%>" /> 
				<input type="hidden" name="scope" value="<%=scope%>" /> 
				<%
				if(callerPage!=null && Checker.isStringNullOrEmpty(callerPage)) {
				%>
				<input type="hidden" name="callerPage" value="<%=callerPage%>" /> 
				<%	
				}
				else {
				%>
				<input type="hidden" name="callerPage" value="<%=thisPage%>" /> 
				<%
				}
				%>
			<%
			//scope lebih dari satu jadi ada pilihan
			
			if(vSc!=null && vSc.size()>0) {
			//if(vSc.size()>0) {
			
			%>
			<center>		
			<table class="table" align="center" border="1" style="background:#369;color:#000;width:50%;">
				<tr>
					<td style="color:#fff;background:#369;text-align:center;font-size:1.75em" padding-left="2px"><b>PILIH PROGRAM STUDI</b></td>
				</tr>	
				<tr>	
						<td style="text-align:center">
							<select name="kdpst_nmpst" style="height:35px;text-align-last:center;border:none;width:100%">
				<%
				liSc = vSc.listIterator();
				while(liSc.hasNext()){
					baris = (String)liSc.next();
					//System.out.println("baris="+baris);
					//pemisah = Checker.getSeperatorYgDigunakan(baris);
					//System.out.println("baris2="+baris);
					//System.out.println("pemisah="+pemisah);
					st = new StringTokenizer(baris);
					String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmpst = st.nextToken().replace("MHS_", "").replace("_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				String kampusDom = null; 
    				String ket_jen = null; 
    				if(st.hasMoreTokens()) {
    					kampusDom = st.nextToken();
    				}
    				else {
    					kampusDom = null;
    				}
    				String kodeKampus = null; 
    				//peralihan ada yg ngga ada tkn kodekampus
    				if(st.hasMoreTokens()) {
    					kodeKampus = st.nextToken();
    				}
    				else {
    					kodeKampus = null;
    				}
    				if(st.hasMoreTokens()) {
    					ket_jen = st.nextToken();
    				}
    				else {
    					ket_jen = null;
    				}
					
					
					//String idObj = st.nextToken();
					//String kdpst = st.nextToken();
					//String nmpst = st.nextToken();
					nmpst = nmpst.replaceAll("MHS", "");
					nmpst = nmpst.replaceAll("_", " ");
					//String objLv = st.nextToken();
					%>
									<option value="<%=kdpst %>,<%=nmpst %>,<%=kodeKampus %>"><%=nmpst %> <%=ket_jen %></option>
						<%
				}
				%>
								</select>
							</td>
						</tr>	
				<%
				if(false) {
				//if(cmd.equalsIgnoreCase("bukaKelas")) {
					//penambahan kelas sekarang sudah pindah 
				%>
						<tr>	
							<td align="left"  style="padding-left:2px">KELAS TAMBAHAN : <input type="checkbox" name="kelasTambahan" value="yes"></td>
						</tr>		
				<%	
				}
				%>			
						<tr>
							<td style="border:2px solid #369;padding:5px 5px;background:<%=Constant.lightColorBlu() %>">
							<section class="gradient">
								<div style="text-align:right">
									<button  type="submit" style="padding: 5px 50px;font-size: 20px;">NEXT</button>
								</div>
							</section>
							</td>
						</tr>
					</table>
				<%
			}
			else {
				if(vSc!=null && vSc.size()==1) {
					liSc = vSc.listIterator();
					baris = (String)liSc.next();
					
					st = new StringTokenizer(baris);
					String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmpst = st.nextToken().replace("MHS_", "").replace("_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				String kampusDom = null; 
    				String ket_jen = null; 
    				if(st.hasMoreTokens()) {
    					kampusDom = st.nextToken();
    				}
    				else {
    					kampusDom = null;
    				}
    				String kodeKampus = null; 
    				//peralihan ada yg ngga ada tkn kodekampus
    				if(st.hasMoreTokens()) {
    					kodeKampus = st.nextToken();
    				}
    				else {
    					kodeKampus = null;
    				}
    				if(st.hasMoreTokens()) {
    					ket_jen = st.nextToken();
    				}
    				else {
    					ket_jen = null;
    				}
    				
					//String idObj = st.nextToken();
					//String kdpst = st.nextToken();
					//String nmpst = st.nextToken();
					nmpst = nmpst.replaceAll("MHS", "");
					nmpst = nmpst.replaceAll("_", " ");
					//String objLv = st.nextToken();
				%>
				 <input type="hidden" name="kdpst_nmpst" value="<%=kdpst %>,<%=nmpst %>,<%=kodeKampus %>" /> 
				<%
				}
			}
			
				
			%>	
				
			</form>
			</center>	
			<%
			//}
			//if(vSc!=null && vSc.size()>0 && !Checker.isStringNullOrEmpty(lockedMsg)) {
			if(true) {
				//edit disini edit dibawah juga 
				//dibagian vscope==1
		%>	
			<br/>
			<p style="text-align:center;font-weight:bold;font-size:1.5em">
				<%= lockedMsg%>
			</p>
			<p style="text-align:center;font-style:italic;color:red;font-weight:bold">
				UNTUK PERUBAHAN/PENAMBAHAN KELAS PERKULIAHAN DAN DOSEN AJAR <br>HARAP GUNAKAN MENU 'PERUBAHAN/PENAMBAHAN KELAS PERKULIAHAN DAN DOSEN AJAR' PADA LAMAN MUKA
			</p>
		<%
			}
		}
		%>
		</div>
	</div>
</div>		
</body>
</html>