<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />

<style>
.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	//System.out.println("form penialaian akhir");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListNpmhsAndInfoNilai = (Vector) request.getAttribute("vListNpmhsAndInfoNilai");
	//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh+"`"+token_nilai-bobot;
	//(url+"?thsms="+thsms+"&idkmk="+idkmk+"&uniqueId="+uniqueId+"&kdkmk="+kdkmk+"&nakmk="+nakmk+"&shift="+shift)
	String thsms= request.getParameter("thsms");
	String idkmk=request.getParameter("idkmk");
	String uniqueId=request.getParameter("uniqueId");
	String kdkmk=request.getParameter("kdkmk");
	String nakmk=request.getParameter("nakmk");
	String shiftKelas=request.getParameter("shift");
	String bolehEdit=request.getParameter("bolehEdit");
	//System.out.println("bolehEdit="+bolehEdit);
	String nmmdos= request.getParameter("nmmdos");
	String npmdos= request.getParameter("npmdos");
	boolean editable = Boolean.parseBoolean(bolehEdit);
	boolean syDsnNya = false;
	if(npmdos.equalsIgnoreCase(validUsr.getNpm())) {
		syDsnNya = true;
	}
	String group_proses = request.getParameter("group_proses");
	
	String noKlsPll = request.getParameter("noKlsPll");
	String kode_kelas = request.getParameter("kode_kelas");
	String kode_gedung = request.getParameter("kode_gedung");
	String kode_kampus = request.getParameter("kode_kampus");
	String kode_gabung_kls = request.getParameter("kode_gabung_kls");
	String kode_gabung_kmp = request.getParameter("kode_gabung_kmp");
	String skstm = request.getParameter("skstm");
	String skspr = request.getParameter("skspr");
	String skslp = request.getParameter("skslp");
	
	
	String sta_limit = request.getParameter("sta_limit");
	String range_limit = request.getParameter("range_limit");
	String cmd = request.getParameter("cmd");
	//System.out.println("sta_limit1="+sta_limit);
	//System.out.println("range_limit1="+range_limit);
	//System.out.println("cmd1="+cmd);
	String startingThsms = request.getParameter("starting_thsms");
	String kelas_thsms = request.getParameter("kelas_thsms");

	//System.out.println("editable="+editable);
	//System.out.println("syDsnNya="+syDsnNya);
%>
<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
	<ul>
		<li>
		<a href="#" onclick="(function(){
				//scroll(0,0);
 				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='cek.classPoolDenganNilaiTunda?starting_thsms=<%=startingThsms %>&cmd=<%=cmd %>&atMenu=index&from=home&sta_limit=<%=sta_limit %>&range_limit=<%=range_limit %>'})()" target="_self">
				BACK <span><b style="color:#eee">&nbsp</b>
			</span></a></li>
	</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
	
	<br />
		<!--  iframe name="hidden" src="hidden_comet" frameborder="0" height="0" width="100%"><iframe> <br/>
    	<iframe name="counter" src="count.html" frameborder="0" height="100%" width="100%"><iframe -->
		<!-- Column 1 start -->
		<%
		
		if(vListNpmhsAndInfoNilai!=null && vListNpmhsAndInfoNilai.size()>0) {
			//tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl;
			ListIterator li = vListNpmhsAndInfoNilai.listIterator();
			String brs = (String)li.next();
			//System.out.println("info brs="+brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh;
			//System.out.println("tknCivitasInfo=="+brs);
			String kdpst = st.nextToken();
			String npmhs = st.nextToken();
			String nimhs = st.nextToken();
			String nmmhs = st.nextToken();
			String shiftMhs = st.nextToken();
			String kdjek = st.nextToken();
			String smawl = st.nextToken();
			st.nextToken();//npmhs
			String nilai = st.nextToken();
			String nlakh = st.nextToken();
			String nlakhByDsn = st.nextToken();
			String bobot = st.nextToken();
			String value = "";
			if(Checker.isStringNullOrEmpty(nilai) || (nilai!=null && Double.parseDouble(nilai)<1)) {
				if(!Checker.isStringNullOrEmpty(nlakh)) {
					value = new String(nlakh);
				}
			}
			else {
				
				value=new String(nilai);
			}
			//String nilai_bobot = st.nextToken();
			
			//String list_nilai_bobot = new String(Getter.getAngkaPenilaian(thsms, kdpst));
			//System.out.println("list_nilai_bobot=="+list_nilai_bobot);
			int i=1;
			
		%>
		<jsp:include page="hamburger_menu.jsp" flush="true" />
		<br>
		<table class="table">
		<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px" -->
		<%
	     	//if(editable) {
	     	if(false) {
	    %>
	    
	    <form action="proses.updateNilaiMk" method="POST">
	    
		<input type="hidden" name="info" value="<%=brs %>" />
	   	<input type="hidden" name=syDsnNya value="<%=syDsnNya %>" />
	    <input type="hidden" name="thsms" value="<%=thsms %>" />
	    <input type="hidden" name="idkmk" value="<%=idkmk %>" />
	    <input type="hidden" name="uniqueId" value="<%=uniqueId %>" />
	    <input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
	    <input type="hidden" name="nakmk" value="<%=nakmk %>" />
	    <input type="hidden" name="npmdos" value="<%=npmdos %>" />
	    <input type="hidden" name="nmmdos" value="<%=nmmdos %>" />
	    <input type="hidden" name="shiftKelas" value="<%=shiftKelas %>" />
	    <input type="hidden" name="group_proses" value="<%=""+group_proses %>" />
		<input type="hidden" name="kdpst" value="<%=kdpst%>" />
	    <input type="hidden" name="npmhs" value="<%=npmhs%>" />
	    <input type="hidden" name="bolehEdit" value="<%=bolehEdit%>" />
	    <input type="hidden" name="noKlsPll" value="<%=noKlsPll%>" />
	    <input type="hidden" name="kode_kelas" value="<%=kode_kelas%>" />
	    <input type="hidden" name="kode_gedung" value="<%=kode_gedung%>" />
	    <input type="hidden" name="kode_kampus" value="<%=kode_kampus%>" />
	    <input type="hidden" name="kode_gabung_kls" value="<%=kode_gabung_kls%>" />
	    <input type="hidden" name="kode_gabung_kmp" value="<%=kode_gabung_kmp%>" />
	    <input type="hidden" name="skstm" value="<%=skstm%>" />
	    <input type="hidden" name="skspr" value="<%=skspr%>" />
	    <input type="hidden" name="skslp" value="<%=skslp%>" />
	    
	    <%
	    	}
	    %>
			<tr>
	       		<td style="background:<%=Constant.lightColorGrey() %>;text-align:center;color:#369;border-top: 1px solid #369" colspan="5"><label><h2>FORM PENILAIAN</h2></td>
	       	</tr>
	       	<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><h2><%=nakmk %>(<%=kdkmk %>)<br>[KELAS <%=kelas_thsms %>]</h2><br/><B> <%=shiftMhs %> <br/><%=nmmdos%></B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NO.</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NPM</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA MAHASISWA</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NILAI AKHIR<br>(Angka : 0 s/d 100)</B> </label></td>
	       	</tr>
	       	<tr>
	       		<td style="color:#000;text-align:center;vertical-align:middle"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center;vertical-align:middle"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:left;padding:0 0 0 10px;vertical-align:middle"><label><%=nmmhs %></label></td>
	       		<td style="background-color:#fff;color:#000;text-align:center;vertical-align:middle"><label>
	       		<%
	       		//if(editable) {
		     	if(false) {
	       			if(syDsnNya || (nlakhByDsn.equalsIgnoreCase("false"))) {
	       			//<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl" selected="selected"><%=nilaiTbbnl </option>		
	       		%>
	       			<input type="text" style="font-weight:bold;font-size:1.2em;text-align:center;border:none;width:100%;height:30px" name="nlakh" value="<%=value %>" />
	       		<%
	       			}
	       			else {
	       				out.print(value);
%>
		       		 <input type="hidden" style="border:none;width:100%;height:30px" name="nlakh" value="<%=value %>"/>	
		       		<%	
	       			}
	       		}
	       		else {	 
	       			out.print(nlakh);
	       		}
	       		%>
	       		</label></td>
	       	
	       	</tr>	
	       	
		<%	
			while(li.hasNext()) {
				//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh;
				brs = (String)li.next();
				//System.out.println("info brs2="+brs);
				st = new StringTokenizer(brs,"`");
				kdpst = st.nextToken();
				npmhs = st.nextToken();
				nimhs = st.nextToken();
				nmmhs = st.nextToken();
				shiftMhs = st.nextToken();
				kdjek = st.nextToken();
				smawl = st.nextToken();
				st.nextToken();//npmhs
				nilai = st.nextToken();
				nlakh = st.nextToken();
				nlakhByDsn = st.nextToken();
				bobot = st.nextToken();
				value = "";
				if(Checker.isStringNullOrEmpty(nilai) || (nilai!=null && Double.parseDouble(nilai)<1)) {
					if(!Checker.isStringNullOrEmpty(nlakh)) {
						value = new String(nlakh);
					}
				}
				else {
					value=new String(nilai);
				}
				//nilai_bobot = st.nextToken();
		%>	
			<tr>
				<input type="hidden" name="kdpst" value="<%=kdpst%>" />
				<input type="hidden" name="npmhs" value="<%=npmhs%>" />
	       		<td style="color:#000;text-align:center;vertical-align:middle"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center;vertical-align:middle"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:left;padding:0 0 0 10px;vertical-align:middle"><label><%=nmmhs %></label></td>
	       		<td style="background-color:#fff;color:#000;text-align:center;vertical-align:middle"><label>
	       		<%
	       		//if(editable) {
		     	if(false) {
	       		%>
	       			<input type="hidden" name="info" value="<%=brs %>" />
	       		<%	
       				if(syDsnNya || (nlakhByDsn.equalsIgnoreCase("false"))) {
       			//<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl" selected="selected"><%=nilaiTbbnl </option>		
       		%>
       			<input type="text" style="font-weight:bold;font-size:1.2em;text-align:center;border:none;width:100%;height:30px" name="nlakh" value="<%=value %>" />
       		<%
       				}
       				else {
       					out.print(value);
%>
	       		 <input type="hidden" style="border:none;width:100%;height:30px" name="nlakh" value="<%=value %>"/>	
	       		<%	
       				}
	       		}	
	       		else {	 
	       			out.print(nlakh);
	       		}
	       		%>
	       		</label></td>
	    
	       	</tr>
		<%		
			}
			//if(editable) {
     		if(false) {
			%>
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="5">
	       		<section class="gradient">
					<div style="text-align:center">
						<button style="padding: 5px 50px;font-size: 20px;">Update Data</button>
					</div>
				</section>
	       		</td>
	       	</tr>	
			</form>	
			<%	
			}
		%>
		</table>
		<%
		}
		else {
		%>
		<h3 style="text-align=center">JUMLAH MAHASISWA DALAM LIST = 0 mhs </h3>
		<%
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>
</div>
			
</body>
</html>