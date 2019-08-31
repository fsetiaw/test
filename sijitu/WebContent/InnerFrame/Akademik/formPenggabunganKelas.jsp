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

<%
/*
* viewKurikulumStdTamplete (based on)
*/
	String tmp ="";
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String hak_akses = validUsr.getHakAkses("reqGabungKelasFak");
	boolean read_only = false;
	if(hak_akses!=null && hak_akses.contains("`")) {
		if(hak_akses.contains("r")&&!hak_akses.contains("i")&&!hak_akses.contains("e")&&!hak_akses.contains("d")) {
			read_only = true;
		}
	}
	//String listMkNonKelompok = request.getParameter("listMkNonKelompok");
	//String listMkKelompok = request.getParameter("listMkKelompok");
	String listMkNonKelompok = (String)session.getAttribute("listMkNonKelompok");
	String listMkKelompok = (String)session.getAttribute("listMkKelompok");
	if(listMkNonKelompok!=null) {
		listMkNonKelompok=listMkNonKelompok.replace("tandaDan", "&");
	}
	if(listMkKelompok!=null) {
		listMkKelompok=listMkKelompok.replace("tandaDan", "&");
	}
	int i=0;
	String thsmsTarget = request.getParameter("thsmsTarget");
%>

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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; vertical-align: middle; }
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; word-wrap:break-word;}
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; word-wrap:break-word;}
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px ;word-wrap:break-word;vertical-align: middle;}

input[type=checkbox]
{
  /* Double-sized Checkboxes */
  -ms-transform: scale(2); /* IE */
  -moz-transform: scale(2); /* FF */
  -webkit-transform: scale(2); /* Safari and Chrome */
  -o-transform: scale(2); /* Opera */
  padding: 10px;
}

</style>
<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
<!--   include file="IndexAkademikPengajuanSubMenu.jsp" % -->
	<ul>
		<li><a href="#" onclick="(function(){
				//scroll(0,0);
				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='get.notifications'})()"
		 		target="_self">BACK <span><b style="color:#eee">&nbsp</b></span>
		 		</a>
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		
		<br>
		<center>
		<!-- Column 1 start -->

		<%
		try {
		if(listMkNonKelompok!=null || listMkKelompok!=null) {
			//System.out.println("start");
		%>
		<form action="go.gabungKelas" method="POST">
		<input type="hidden" name="thsmsTarget" value="<%=thsmsTarget %>" />
		<table class="table" style="width:90%">
        	<tr>
	    		<td style="background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="9"><label><B>DAFTAR KELAS PERKULIAHAN</B> </label></td>
	    	</tr>
    		<tr>
    			<td style="background:#369;color:#fff;text-align:center;width:5%">NO</td>
    			<td style="background:#369;color:#fff;text-align:center;width:20%">PRODI</td>
    			<td style="background:#369;color:#fff;text-align:center;width:30%">MATAKULIAH</td>
    			<td style="background:#369;color:#fff;text-align:center;width:10%">KODE MK</td>
    			<td style="background:#369;color:#fff;text-align:center;width:5%">KAMPUS</td>
    			<td style="background:#369;color:#fff;text-align:center;width:15%">SHIFT</td>
    			<td style="background:#369;color:#fff;text-align:center;width:5%">KLS PLL</td>
    			<!--  td style="background:#369;color:#fff;text-align:center;width:50px"># MHS</td -->
    			<td style="background:#369;color:#fff;text-align:center;width:5%">GROUP</td>
    			<td style="background:#369;color:#fff;text-align:center;width:5%">KELAS INTI</td>
    		</tr>
		<%	
			if(listMkKelompok!=null) {
			//if(false) {
				//kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1);
				StringTokenizer st = new StringTokenizer(listMkKelompok,"$");
				
				while(st.hasMoreTokens()) {
					//System.out.println("-listMkKelompok- "+listMkKelompok);
					//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
					String kodeGabungan=st.nextToken();
					String idkmk1=st.nextToken();
					
					//System.out.println(kodeGabungan+"--"+idkmk1);
					String idkur1=st.nextToken();
					String kdkmk1=st.nextToken();
					String nakmk1=st.nextToken();
					//System.out.println("nakmk="+nakmk1);
					String thsms1=st.nextToken();
					//tmp = ""+idkmk1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1;
					String kdpst1=st.nextToken();
					String shift1=st.nextToken();
					String norutKlsPll1=st.nextToken();
					String initNpmInput1=st.nextToken();
					String latestNpmUpdate1=st.nextToken();
					String latesStatusInfo1=st.nextToken();
					String currAvailStatus1=st.nextToken();
					String locked1=st.nextToken();
					String npmdos1=st.nextToken();
					String nodos1=st.nextToken();
					String npmasdos1=st.nextToken();
					String noasdos1=st.nextToken();
					String canceled1=st.nextToken();
					String kodeKelas1=st.nextToken();
					String kodeRuang1=st.nextToken();
					String kodeGedung1=st.nextToken();
					String kodeKampus1=st.nextToken();
					String tknHrTime1=st.nextToken();
					String nmdos1=st.nextToken();
					String nmasdos1=st.nextToken();
					String enrolled1=st.nextToken();
					String maxEnrolled1=st.nextToken();
					String minEnrolled1=st.nextToken();
					String subKeterKdkmk1=st.nextToken();
					String initReqTime1=st.nextToken();
					String tknNpmApr1=st.nextToken();
					String tknAprTime1=st.nextToken();
					String targetTtmhs1=st.nextToken();
					String passed1=st.nextToken();
					String rejected1=st.nextToken();
					String konsen1=st.nextToken();
					String nmpst1=st.nextToken();
					String cuid1=st.nextToken();
					String listMhs=st.nextToken();
					//String listMhsHer=st.nextToken();
					
					
					
					int ttmhs1=0;
					if(listMhs!=null && !Checker.isStringNullOrEmpty(listMhs)) {
						StringTokenizer stt = new StringTokenizer(listMhs,",");
						ttmhs1 = stt.countTokens();
					}
					//int ttmhsher1=0;
					//if(listMhsHer!=null && !Checker.isStringNullOrEmpty(listMhsHer)) {
					//	StringTokenizer stt = new StringTokenizer(listMhsHer,",");
					//	ttmhsher1 = stt.countTokens();
					//}
					//String ttmhs1=st.nextToken();
					//tmp=(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1+"$"+ttmhs1);
			%>
			<tr>
    			<td style="text-align:center"><%=++i %></td>
    			<td style="text-align:center;">
    		
    			<%
    			out.print(nmpst1);
    			if(!Checker.isStringNullOrEmpty(konsen1)) {
    				out.print("<br/>Konsentrasi<br/>"+konsen1.toUpperCase());
    			}
    			%></td>
    			<td style="text-align:left;padding:0 0 0 10px"><%=nakmk1+"<br/>Dosen: "+nmdos1 %></td>
    			<td style="text-align:center;"><%=kdkmk1 %></td>
    			<td style="text-align:center;"><%=kodeKampus1 %></td>
    			<td style="text-align:center;"><%=shift1 %></td>
    			<td style="text-align:center;"><%=norutKlsPll1 %></td>
    			<!--  td style="text-align:center;"><a href="go.prepForTampleteListMhs?listNpmhs=<%=listMhs%>"><%=ttmhs1 %></a></td-->
    			<td style="text-align:center;background:#fff">
					<input type="hidden" name="infoGroup" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" />
				<%
					if(read_only) {
				%>	
					<%=Checker.pnn(kodeGabungan) %>
				<%	
					}
					else {
						if(kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan)) {
				%>
					<input type="text" name="kodeGroup" maxlength="3" value="<%=kodeGabungan %>" style="border:none;height:50px;width:100%;text-align:center"/>
				<%	
						}
						else {
				%>
					<input type="text" name="kodeGroup" maxlength="3" style="border:none;height:50px;width:100%;text-align:center"/>
				<%
						}
					}
				%>	
				</td>	
    			<td style="text-align:center;">
    			<%
    				if(read_only) {
    					if(canceled1.equalsIgnoreCase("false")) {
    			%>	
    						&#9745;
    			<%
						}
    					else {
    			%>	
    					&#9744;
    			<%	
    					}	
    				}
    				else {
    					if(canceled1.equalsIgnoreCase("false")) {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" checked>
    			<%
						}
    					else {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>">
    			<%	
    					}
    				}	
    			//System.out.println("done");
    			%>
				</td>
    		</tr>
			<%		
				}
			}
			//System.out.println("end");
			//System.out.println("start");
			if(listMkNonKelompok!=null) {
			//if(false) {
				
			//kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1);
				StringTokenizer st = new StringTokenizer(listMkNonKelompok,"$");
				while(st.hasMoreTokens()) {
					//System.out.println("-listMkNonKelompok- "+listMkNonKelompok);
					String kodeGabungan=st.nextToken();
					String idkmk1=st.nextToken();
					String idkur1=st.nextToken();
					String kdkmk1=st.nextToken();
					String nakmk1=st.nextToken();
					String thsms1=st.nextToken();
					String kdpst1=st.nextToken();
					String shift1=st.nextToken();
					String norutKlsPll1=st.nextToken();
					String initNpmInput1=st.nextToken();
					String latestNpmUpdate1=st.nextToken();
					String latesStatusInfo1=st.nextToken();
					String currAvailStatus1=st.nextToken();
					String locked1=st.nextToken();
					String npmdos1=st.nextToken();
					String nodos1=st.nextToken();
					String npmasdos1=st.nextToken();
					String noasdos1=st.nextToken();
					String canceled1=st.nextToken();
					String kodeKelas1=st.nextToken();
					String kodeRuang1=st.nextToken();
					String kodeGedung1=st.nextToken();
					String kodeKampus1=st.nextToken();
					String tknHrTime1=st.nextToken();
					String nmdos1=st.nextToken();
					String nmasdos1=st.nextToken();
					String enrolled1=st.nextToken();
					String maxEnrolled1=st.nextToken();
					String minEnrolled1=st.nextToken();
					String subKeterKdkmk1=st.nextToken();
					String initReqTime1=st.nextToken();
					String tknNpmApr1=st.nextToken();
					String tknAprTime1=st.nextToken();
					String targetTtmhs1=st.nextToken();
					String passed1=st.nextToken();
					String rejected1=st.nextToken();
					String konsen1=st.nextToken();
					String nmpst1=st.nextToken();
					String cuid1=st.nextToken();
					String listMhs=st.nextToken();
					//String listMhsHer=st.nextToken();
					int ttmhs1=0;
					if(listMhs!=null && !Checker.isStringNullOrEmpty(listMhs)) {
						StringTokenizer stt = new StringTokenizer(listMhs,",");
						ttmhs1 = stt.countTokens();
					}
					//int ttmhsher1=0;
					//if(listMhsHer!=null && !Checker.isStringNullOrEmpty(listMhsHer)) {
					//	StringTokenizer stt = new StringTokenizer(listMhsHer,",");
					//	ttmhsher1 = stt.countTokens();
					//}

			%>
			<tr>
				<td style="text-align:center;"><%=++i %></td>
				<td style="text-align:center;">
				
				<%
				
    			out.print(nmpst1);
    			if(!Checker.isStringNullOrEmpty(konsen1)) {
    				out.print("<br/>Konsentrasi<br/>"+konsen1.toUpperCase());
    			}
    			%></td>
				<td style="text-align:left;padding:0 0 0 10px"><%=nakmk1+"<br/>Dosen: "+nmdos1 %></td>
				<td style="text-align:center;"><%=kdkmk1 %></td>
				<td style="text-align:center;"><%=kodeKampus1 %></td>
				<td style="text-align:center;"><%=shift1 %></td>
				<td style="text-align:center;"><%=norutKlsPll1 %></td>
				<!--  td style="text-align:center;"><a href="go.prepForTampleteListMhs?listNpmhs=<%=listMhs%>"><%=ttmhs1 %></a></td -->
				<td style="text-align:center;background:#fff">
					<input type="hidden" name="infoGroup" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" />
				<%
					if(read_only) {
					%>	
						<%=Checker.pnn(kodeGabungan) %>
					<%	
					}
					else {
						if(kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan)) {
				%>
					<input type="text" name="kodeGroup" maxlength="3" value="<%= kodeGabungan %>" style="border:none;height:50px;width:100%;text-align:center"/>
				<%	
						}
						else {
				%>
					<input type="text" name="kodeGroup" maxlength="3" style="border:none;height:50px;width:100%;text-align:center"/>
				<%
						}
					}	
				%>	
					
				</td>
				<td style="text-align:center;">
				<%
					if(read_only) {
						if(canceled1.equalsIgnoreCase("false") && (kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan))) {
    			%>	
    					&#9745;
    			<%
						}
    					else {
    			%>	
    					&#9744;
    			<%	
    					}	
    				}
					else {
    					if(canceled1.equalsIgnoreCase("false") && (kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan))) {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" checked>
    			<%
						}
    					else {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>">
    			<%	
    					}
					}
    			%>
				</td>
			</tr>
			<%		
				}
			}
		%>
		</table>
		<br/>
		<div align="center">
		<%
			//System.out.println("selesai");
			if(!read_only) {
		%>
		<!--  input type="submit" value="UPDATE GABUNG KELAS" name="perintah" style="width:500px;"/><br/><br/ -->
		<section class="gradient">
			<div style="text-align:center">
				<button onclick="(function(){
					//scroll(0,0);
					parent.scrollTo(0,0);
 					var x = document.getElementById('wait');
 					var y = document.getElementById('main');
 					x.style.display = 'block';
 					y.style.display = 'none';
 					document.getElementsByTagName('form').submit()})()"
				 type="submit" value="UPDATE GABUNG KELAS" name="perintah" style="padding: 5px 50px;font-size: 20px;">UPDATE PENGGABUNG KELAS</button>
			</div>
		</section>
		
		
		<%
			}
		
			if(validUsr.isAllowTo("!reqGabungKelasFak")>0) {
		%>		
		<input type="submit" value="PROSES PENGGABUNGAN SELESAI & AJUKAN PENGGABUNGAN" name="perintah" style="width:500px;"/>
		<%
			}
		%>
		</div>
		</form>
		
		<%	
		}
		}
		catch (Exception e){
			//System.out.println("tmp="+tmp);
		}
		%>	
		</center>
		<!-- Column 1 start -->
	</div>
</div>
</div>			
</body>
</html>